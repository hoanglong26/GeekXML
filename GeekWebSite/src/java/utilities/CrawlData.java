/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import dao.ArticleDAO;
import dao.GameDAO;
import dao.GameRatingDAO;
import dao.ImageDAO;
import entities.Article;
import entities.Game;
import entities.GameList;
import entities.GameRating;
import entities.Image;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.ResourceBundle;
import javax.servlet.ServletContext;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import servlets.DispatcherServlet;

/**
 *
 * @author hoanglong
 */
public class CrawlData {

    ServletContext srvlContext;

    List<Game> gameList = null;
    List<Article> articleList = null;

    public CrawlData(ServletContext srvlContext) {
        this.srvlContext = srvlContext;
    }

    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    private BufferedReader getBufferedReaderFromHtml(String uri) {
        BufferedReader in = null;
        try {
            URL url = new URL(uri);
            URLConnection yc = url.openConnection();
            yc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            InputStream is = yc.getInputStream();
            in = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return in;
    }

    public void saxParserForArticle(String uri) {
        String line = "";
        String document = "<root>";
        try {
            URL url = new URL(uri);
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("User-Agent",
                    "Chrome");

            BufferedReader in = getBufferedReaderFromHtml(uri);

            boolean inArticleRow = false;

            while ((line = in.readLine()) != null) {
                //replace unnecessary and special character
                line = line.replaceAll("&gt;", ">");
                line = line.replaceAll("&lt;", "<");
                line = line.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                if (line.contains("<channel>")) {
                    inArticleRow = true;
                }

                if (inArticleRow) {
                    document += line;
                    if (line.contains("</channel>")) {
                        inArticleRow = false;
                    }
                }
            }
            document += "</root>";

            //get list of article
            InputStream is = new ByteArrayInputStream(document.getBytes("UTF-8"));
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            ArticleHandler articleHandler = new ArticleHandler();
            saxParser.parse(is, articleHandler);
            articleList = articleHandler.getList();

            String realPath = srvlContext.getRealPath("/");;
            String articleDataFilepath = realPath + "WEB-INF/ArticleData.xsd";
            String imageDataFilepath = realPath + "WEB-INF/ImageData.xsd";

//            ArticleDAO.deleteAll();
            for (Article item : articleList) {
                //fix the format of publish date
                String datePosted = item.getPubDate().substring(0, item.getPubDate().indexOf("T"));
                String timePosted = item.getPubDate().substring(item.getPubDate().indexOf("T") + 1, item.getPubDate().indexOf("+"));
                item.setPubDate("Đăng vào " + datePosted + " " + timePosted);

                //get author and content of article by using article link
                Article detail = saxParserForArticleDetail(item);
                if (detail.getAuthor() != null && detail.getDescription() != null) {
                    item.setAuthor(detail.getAuthor().trim());
                    item.setDescription(detail.getDescription().trim());

                    //get overview and thumbnail from CDATA of "description" tag 
                    Article overview = saxParserForArticleOverview(item.getOverview());
                    item.setOverview(overview.getOverview().trim());
                    item.setThumbnail(overview.getThumbnail().trim());

                    //validate by using schema
                    boolean isValidArticle = Utilities.validateXMLBeforeSaveToDatabase(Utilities.marshallerToString(item), articleDataFilepath);
                    if (isValidArticle) {

                        //save image of each article
                        for (Image aImage : detail.getImageList()) {
//                            aImage.setArticleId(item);
//                            String[] tmp = item.getLink().split("/");
//                            String fileName = "\\" + tmp[tmp.length - 1];
//                            String fileName = "\\" + item.getTitle().substring(0, 20);
//                            fileName = fileName.replaceAll("\"", "");
//                            fileName = fileName.replaceAll(":", "");
//                            System.out.println(Utilities.downloadImage("./GeekWebsite/ArticleImage", fileName.substring(0, 30), aImage.getId() + "", aImage.getLink()));

                            boolean isValidImage = Utilities.validateXMLBeforeSaveToDatabase(Utilities.marshallerToString(aImage), imageDataFilepath);
                            if (isValidImage) {
                                item.addImage(aImage);
                            }
                        }

                        ArticleDAO.createArticle(item);

                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Article saxParserForArticleDetail(Article article) {
        Article result = new Article();
        String line = "";
        String document = "<root>";

        try {
            URL url = new URL(article.getLink());
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("User-Agent",
                    "Chrome");

            BufferedReader in = getBufferedReaderFromHtml(article.getLink());

            boolean inArticleRow = false;
            boolean inAuthorRow = false;

            while ((line = in.readLine()) != null) {
                line = line.trim();
                line = line.replaceAll("&", "&amp;");

                if (line.contains("<div") && line.contains("class=\"content-detail\"")) {
                    inArticleRow = true;
                    document += "<content>";
                }

                if (line.contains("<p") && line.contains("align=\"right\"")) {
                    inArticleRow = false;
                    document += "</content>";
                    inAuthorRow = true;
                    document += "<author>";
                }
                if (inArticleRow) {
                    if (line.contains("<img")) {
                        String removePart = line.substring(line.indexOf("alt"), line.indexOf("src"));
                        line = line.replace(removePart, "");

                        line = line.replace(" \"=\"\"", "");
                        if (line.contains("jpg\">")) {
                            line = line.replace("jpg\">", "jpg\" />");
                        } else {
                            String tmpline = new StringBuilder(line).insert(line.indexOf("</") - 1, " /").toString();
                            line = tmpline;
                        }
                    }

                    document += line;
                }

                if (inAuthorRow) {
                    document += line;
                    if (line.contains("</p>")) {
                        inAuthorRow = false;
                        document += "</author>";
                    }
                }
            }
            document += "</root>";
            in.close();

            document = document.replaceAll("<br>", "<br />");
            document = document.replaceAll("<hr>", "<hr />");
            document = document.replace("<=>", "đối đầu");

            while (document.contains("<script")) {
                int scriptBeginTag = document.indexOf("<script");
                int scriptEndTag = document.indexOf("</script>");
                if (scriptBeginTag <= scriptEndTag) {
                    String javascript = document.substring(scriptBeginTag, scriptEndTag + 9);
                    document = document.replace(javascript, "");
                }

            }

            document = document.replace("</script>", "");
            document = document.replace("<meta charset=\"utf-8\">", "");
            String content = document.substring(document.indexOf("<content>") + 9, document.indexOf("</content>"));

            document = document.replace("\n", "");
            document = document.replace("\t", "");

            InputStream is = new ByteArrayInputStream(document.getBytes("UTF-8"));
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            ArticleDetailHandler articleDetailHandler = new ArticleDetailHandler();
            saxParser.parse(is, articleDetailHandler);

            result.setDescription(content);
            result.setAuthor(articleDetailHandler.getAuthor().replace("null", ""));
            result.setImageList(articleDetailHandler.getList());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(document.indexOf("<script"));
            System.out.println(document.indexOf("</script>"));
            System.out.println(article.getTitle());
        }
        return result;
    }

    public Article saxParserForArticleOverview(String overview) {
        Article result = new Article();
        String document = "<root>";
        try {
            overview = overview.replaceAll("</br>", "<br/>");
            overview = overview.replaceAll("width=130 height=100", "width=\"130\" height=\"100\"");

            document += overview;

            document += "</root>";

            InputStream is = new ByteArrayInputStream(document.getBytes("UTF-8"));
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            ArticleOverviewHandler articleOverviewHandler = new ArticleOverviewHandler();
            saxParser.parse(is, articleOverviewHandler);

            result.setThumbnail(articleOverviewHandler.getImgLink());
            result.setOverview(articleOverviewHandler.getOverView());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public void saxParserForGameRanking(String uri, int pageNum) {
        //refresh DB
//        GameRatingDAO.deleteAll();
//        GameDAO.deleteAll();

        for (int i = 0; i <= pageNum; i++) {
            try {
                uri += i;
                URL url = new URL(uri);
                URLConnection conn = url.openConnection();
                conn.addRequestProperty("User-Agent",
                        "Chrome");

                BufferedReader in = getBufferedReaderFromHtml(uri);

                String line;

                String document = "<root>";
                boolean inGameRow = false;
                while ((line = in.readLine()) != null) {
                    line = line.trim();
                    line = line.replaceAll("&", "&amp;");

                    if (line.contains("<tr")) {
                        inGameRow = true;
                        document += "<item>";
                    }

                    if (inGameRow) {
                        document += line;
                        if (line.contains("</tr>")) {
                            inGameRow = false;
                            document += "</item>";
                        }
                    }
                }
                document += "</root>";
                in.close();
                document = document.replace("\n", "");
                document = document.replace("\t", "");

                //get game list
                InputStream is = new ByteArrayInputStream(document.getBytes("UTF-8"));
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                GameRankingHandler gamerankingHandler = new GameRankingHandler();
                is.reset();
                saxParser.parse(is, gamerankingHandler);
                gameList = gamerankingHandler.getList();

                String realPath = srvlContext.getRealPath("/");;
                String gameDataFilepath = realPath + "WEB-INF/GameData.xsd";
                String gameRatingDataFilepath = realPath + "WEB-INF/GameRatingData.xsd";

                for (Game item : gameList) {

                    //get game detail by game link
                    Game tmp = saxParserForGameDetail(item);
                    String name = item.getName().replaceAll("'", "''");
                    item.setName(name.trim());
                    item.setDescription(tmp.getDescription().replaceAll("'", "''").trim());
//                GameDAO.createGame2(item);

                    //validate game data by using schema
                    boolean isValidGame = Utilities.validateXMLBeforeSaveToDatabase(Utilities.marshallerToString(item), gameDataFilepath);
                    if (isValidGame) {
                        for (GameRating aRating : tmp.getGameRatingList()) {
                            aRating.setGameId(item);

                            //validate game rating data by using schema
                            boolean isValidRating = Utilities.validateXMLBeforeSaveToDatabase(Utilities.marshallerToString(aRating), gameRatingDataFilepath);
                            if (isValidRating) {
                                GameRatingDAO.createGameRating(aRating);
                            }
                        }
                    }

                }

            } catch (Exception e) {
                //e.printStackTrace();
            }
        }

    }

    public Game saxParserForGameDetail(Game aGame) {
        Game result = new Game();

        try {
            URL url = new URL(aGame.getLink());
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("User-Agent",
                    "Chrome");

            BufferedReader in = getBufferedReaderFromHtml(aGame.getLink());

            String line;

            String document = "<root>";
            boolean inDescriptionRow = false;
            boolean inReviewRow = false;

            while ((line = in.readLine()) != null) {
                line = line.trim();
                line = line.replaceAll("&", "&amp;");

                if (line.contains("<div") && line.contains("class=\"details\"")
                        && !line.contains("style=\"text-align:center\"")) {
                    inDescriptionRow = true;
                    document += "<description>";
                }

                if (line.contains("<tbody")) {
                    inReviewRow = true;
                    document += "<ratings>";
                }

                if (inDescriptionRow) {
                    document += line;
                    if (line.contains("</div>")) {
                        inDescriptionRow = false;
                        document += "</description>";
                    }
                }

                if (inReviewRow) {
                    if (line.contains("out of")) {
                        line = line.replace("td", "rating");
                    }

                    if (line.indexOf("<td>") != -1 && line.indexOf("</td>") != -1) {
                        //get date string and check valid
                        String dateString = line.substring(line.indexOf("<td>") + 4, line.indexOf("</td>"));
                        if (dateString.matches("^\\d{2}\\/\\d{2}\\/\\d{2}$")) {
                            line = line.replace("td", "date");
                        }
                    }

                    if (line.contains(">Review")) {
                        line = "";
                    }

                    document += line;
                    if (line.contains("</tbody>")) {
                        inReviewRow = false;
                        document += "</ratings>";
                    }
                }
            }
            document += "</root>";
            in.close();
            document = document.replace("\n", "");
            document = document.replace("\t", "");

            InputStream is = new ByteArrayInputStream(document.getBytes("UTF-8"));
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GameDetailHandler gameDetailHandler = new GameDetailHandler();
            is.reset();
            saxParser.parse(is, gameDetailHandler);
            result.setDescription(gameDetailHandler.getDescription());
            result.setGameRatingList(gameDetailHandler.getList());
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return result;

    }

}
