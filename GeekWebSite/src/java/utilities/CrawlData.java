/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import dao.ArticleDAO;
import dao.GameDAO;
import dao.GameRatingDAO;
import entities.Article;
import entities.Game;
import entities.GameRating;
import entities.Image;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import static java.rmi.server.LogStream.log;
import java.text.SimpleDateFormat;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

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
//            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
//            ex.printStackTrace();
            log(ex.getMessage());

        } catch (IOException ex) {
//            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
//            ex.printStackTrace();
            log(ex.getMessage());

        }

        return in;
    }

    public void saxParserForICTNewsHomepage(String uri, String[] categories) {
        String line = "";
        String document = "<root>";
        try {
            URL url = new URL(uri);
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("User-Agent",
                    "Chrome");

            BufferedReader in = getBufferedReaderFromHtml(uri);

            while ((line = in.readLine()) != null) {
                if (line.contains("/rss.ict")) {
                    document += line;
                    break;
                }
            }

            document += "</root>";

            InputStream is = new ByteArrayInputStream(document.getBytes("UTF-8"));
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            ICTHomepageHandler ictHomepageHandler = new ICTHomepageHandler();
            saxParser.parse(is, ictHomepageHandler);
            String rssPage = "http://ictnews.vn" + ictHomepageHandler.getRssPage();

            saxParserForICTNewsRsspage(rssPage, categories);
        } catch (Exception e) {
            log(e.getMessage());

        }
    }

    public void saxParserForICTNewsRsspage(String uri, String[] categories) {
        String line = "";
        String document = "<root>";
        try {
            URL url = new URL(uri);
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("User-Agent",
                    "Chrome");

            BufferedReader in = getBufferedReaderFromHtml(uri);

            while ((line = in.readLine()) != null) {
                if (line.contains("rss") && !line.contains("<i") && !line.contains("style")) {
                    for (int i = 0; i < categories.length; i++) {
                        if (line.contains(categories[i])) {
                            document += line;
                        }
                    }
                }
            }

            document += "</root>";

            InputStream is = new ByteArrayInputStream(document.getBytes("UTF-8"));
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            ICTRssPageHandler ictRsspageHandler = new ICTRssPageHandler();
            saxParser.parse(is, ictRsspageHandler);
            List<String> rssList = ictRsspageHandler.getRssList();

            for (String rssPage : rssList) {
                saxParserForArticle(rssPage);
            }
        } catch (Exception e) {
            log(e.getMessage());
        }
    }

    public void saxParserForGamekHomepage(String uri, String[] categories) {
        String line = "";
        String document = "<root>";
        try {
            URL url = new URL(uri);
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("User-Agent",
                    "Chrome");

            BufferedReader in = getBufferedReaderFromHtml(uri);

            while ((line = in.readLine()) != null) {
                if (line.contains("/rss.chn") && line.contains("class=\"facebook\"") && line.contains("class=\"youtube\"")) {
                    document += line;
                    break;
                }
            }

            document += "</root>";

            InputStream is = new ByteArrayInputStream(document.getBytes("UTF-8"));
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GamekHomepageHandler gamekHomepageHandler = new GamekHomepageHandler();
            saxParser.parse(is, gamekHomepageHandler);
            String rssPage = "http://gamek.vn" + gamekHomepageHandler.getRssPage();

            saxParserForGamekRsspage(rssPage, categories);
        } catch (Exception e) {
            log(e.getMessage());

        }
    }

    public void saxParserForGamekRsspage(String uri, String[] categories) {
        String line = "";
        String document = "<root>";
        try {
            URL url = new URL(uri);
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("User-Agent",
                    "Chrome");

            BufferedReader in = getBufferedReaderFromHtml(uri);

            while ((line = in.readLine()) != null) {
                if (line.contains("RSS") && line.contains("<li><a") && line.contains("target=\"_blank\"")) {
                    for (int i = 0; i < categories.length; i++) {
                        if (line.contains(categories[i])) {
                            document += line;
                        }
                    }
                }
            }

            document += "</root>";

            InputStream is = new ByteArrayInputStream(document.getBytes("UTF-8"));
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GamekRssPageHandler gamekRsspageHandler = new GamekRssPageHandler();
            saxParser.parse(is, gamekRsspageHandler);
            List<String> rssList = gamekRsspageHandler.getRssList();

            for (String rssPage : rssList) {
                saxParserForArticle(rssPage);
            }
        } catch (Exception e) {
            log(e.getMessage());
        }
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
                line = line.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
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

            String realPath = srvlContext.getRealPath("/");
            String articleDataFilepath = realPath + "WEB-INF/ArticleData.xsd";
            String imageDataFilepath = realPath + "WEB-INF/ImageData.xsd";

//            ArticleDAO.deleteAll();
            for (Article item : articleList) {
                if (uri.contains("ictnews")) {
                    //fix the format of publish date
                    String datePosted = item.getPubDate().substring(0, item.getPubDate().indexOf("T"));
                    String timePosted = item.getPubDate().substring(item.getPubDate().indexOf("T") + 1, item.getPubDate().indexOf("+"));
                    item.setPubDate("Đăng vào " + datePosted + " " + timePosted);
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
                    Date date = sdf.parse(item.getPubDate());
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    item.setPubDate("Đăng vào " + sdf2.format(date));
                }

                //get author and content of article by using article link
                Article detail = null;
                if (uri.contains("ictnews")) {
                    detail = saxParserForICTNewsArticleDetail(item);
                } else {
                    detail = saxParserForGameKArticleDetail(item);
                }

                if (detail != null) {
                    if (detail.getAuthor() != null && detail.getDescription() != null) {
                        item.setAuthor(detail.getAuthor().trim());
                        item.setDescription(detail.getDescription().trim());

                        //get overview and thumbnail from CDATA of "description" tag 
                        Article overview = saxParserForArticleOverview(item.getOverview(), item.getTitle());
                        if (overview != null) {
                            item.setOverview(overview.getOverview().trim());
                            item.setThumbnail(overview.getThumbnail().trim());

                            //validate by using schema
                            boolean isValidArticle = Utilities.validateXMLBeforeSaveToDatabase(Utilities.marshallerToString(item), articleDataFilepath);
                            if (isValidArticle) {

                                //save image of each article
                                for (Image aImage : detail.getImageList()) {
                                    boolean isValidImage = Utilities.validateXMLBeforeSaveToDatabase(Utilities.marshallerToString(aImage), imageDataFilepath);
                                    if (isValidImage) {
                                        item.addImage(aImage);
                                    }
                                }
                                ArticleDAO.createArticle(item);
                            }
                        }

                    }
                }

            }
        } catch (Exception e) {
//            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
//            e.printStackTrace();
            log(e.getMessage());
        }
    }

    public Article saxParserForICTNewsArticleDetail(Article article) {
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
                        if (line.indexOf("alt") < line.indexOf("src")) {
                            String removePart = line.substring(line.indexOf("alt"), line.indexOf("src"));
                            line = line.replace(removePart, "");
                        }
                        line = line.replace(" \"=\"\"", "");
                        if (line.contains("jpg\">") || line.contains("png\">") || line.contains("gif\">")) {
                            line = line.replace("jpg\">", "jpg\" />");
                            line = line.replace("png\">", "png\" />");
                            line = line.replace("gif\">", "gif\" />");

                        } else {
                            String tmpline = new StringBuilder(line).insert(line.indexOf("</") - 1, " /").toString();
                            line = tmpline;
                        }
                    }

                    if (line.contains("bs_mobileinpage")) {
                        line += "</p>";
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
//            e.printStackTrace();
//            System.out.println(document.indexOf("<script"));
//            System.out.println(document.indexOf("</script>"));
//            System.out.println(article.getTitle());
//            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
            log(e.getMessage());
            return null;

        }
        return result;
    }

    public Article saxParserForGameKArticleDetail(Article article) {
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
            boolean inVideoRow = false;

            while ((line = in.readLine()) != null) {
                line = line.trim();
                line = line.replaceAll("&", "&amp;");

                if (line.contains("magazine")) {
                    return null;
                }

                if (line.contains("<div") && line.contains("class=\"rightdetail\"") && !line.contains("id=\"ShareDetail\"")) {
                    inArticleRow = true;
                    document += "<content>";
                }

                if (line.contains("<p class=\"mgt15\">")) {
                    inAuthorRow = true;
                    document += "<author>";
                }

                if (line.contains("<input") && line.contains("id=\"hidCatId\"")) {
                    inArticleRow = false;
                    document += "</content>";

                }

                //don't record the line if it is video
                if (inArticleRow) {

                    //remove ref link
                    if (line.contains("<span class=\"IMSNoChangeStyle\" style=\"font-size: 22px;\"><strong>")) {
                        line = "";
                    }

                    if (line.contains("<div class=\"VCSortableInPreviewMode link-content-footer\"")) {
                        line = "";
                    }

                    if (line.contains("type=\"VideoStream\"")) {
                        inVideoRow = true;
                    }

                    if (!inVideoRow && line.contains("<img")) {
                        if (line.contains("alt") && line.contains("src")) {
                            if (line.indexOf("alt") < line.indexOf("src")) {
                                String removePart = line.substring(line.indexOf("alt"), line.indexOf("src"));
                                line = line.replace(removePart, "");
                            }

                            if (line.indexOf("<div><img") == 0) {
                                line = line.replace("></div>", " /></div>");
                            }

                            line = line.replace("contenteditable=\"false\">", "contenteditable=\"false\" />");
                        }

                        line = line.replace(" \"=\"\"", "");

                    }

                    if (line.contains("jpg\">")) {
                        line = line.replace("jpg\">", "jpg\" />");
                    }

                    if (line.contains("png\">")) {
                        line = line.replace("png\">", "png\" />");
                    }

                    if (line.contains("gif\">")) {
                        line = line.replace("gif\">", "gif\" />");
                    }

                    if (line.contains("class=\"NLPlaceholderShow\"")) {
                        inVideoRow = false;
                        document += line;
                        line = "";
                        String removeVideo = document.substring(document.indexOf("type=\"VideoStream\""), document.length());
                        document = document.replace(removeVideo, "><div>");
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
//            e.printStackTrace();
//            System.out.println(document.indexOf("<script"));
//            System.out.println(document.indexOf("</script>"));
//            System.out.println(article.getTitle());
//            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
            log(e.getMessage());

            return null;

        }
        return result;
    }

    public Article saxParserForArticleOverview(String overview, String articleName) {
        Article result = new Article();
        String document = "<root>";
        try {
            overview = overview.replaceAll("</br>", "<br/>");
            overview = overview.replaceAll("width=130 height=100", "width=\"130\" height=\"100\"");
            overview = overview.replaceAll("“", "&quot;");
            overview = overview.replaceAll("”", "&quot;");
            overview = overview.replaceAll("&amp;", " và ");
//            overview = removeQuote(overview);

            if (articleName.contains("\"")) {
                String tmpTitle = articleName.replace("\"", "'");
                overview = overview.replaceAll(articleName, tmpTitle);
            }

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
//            e.printStackTrace();
//            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
            log(e.getMessage());
            return null;
        }
        return result;

    }

    public void saxParserForGameRanking(String uri, int pageNum) {
        //refresh DB
        GameRatingDAO.deleteAll();
        GameDAO.deleteAll();
        GameDAO.resetIdent();
        GameRatingDAO.resetIdent();
        String originalUrl = uri;
        String gameName = "";
        for (int i = 0; i <= pageNum; i++) {
            try {
                uri = originalUrl;
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
                    gameName = name.trim();

                    //validate game data by using schema
                    boolean isValidGame = Utilities.validateXMLBeforeSaveToDatabase(Utilities.marshallerToString(item), gameDataFilepath);
                    if (isValidGame) {
                        for (GameRating aRating : tmp.getGameRatingList()) {
//                            aRating.setGameId(item);
                            if (aRating.getScore() == null) {
                                aRating.setScore("Chưa đánh giá");
                            }
                            //validate game rating data by using schema
                            boolean isValidRating = Utilities.validateXMLBeforeSaveToDatabase(Utilities.marshallerToString(aRating), gameRatingDataFilepath);
                            if (isValidRating) {
                                item.addRating(aRating);
                            }
                        }
                        GameDAO.createGame(item);
                    }

                }

            } catch (Exception e) {
//                e.printStackTrace();
//                System.out.println(pageNum);
//                System.out.println(gameName);
//                Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
                log(e.getMessage());

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
            boolean inReviewRow = false;

            while ((line = in.readLine()) != null) {
                line = line.trim();
                line = line.replaceAll("&", "&amp;");

                if (line.contains("<div") && line.contains("class=\"details\"")
                        && !line.contains("style=\"text-align:center\"")) {
                    line = "";
                }

                if (line.contains("<tbody")) {
                    inReviewRow = true;
                    document += "<ratings>";
                }

                if (inReviewRow) {

                    if (line.contains("<td>") && line.contains("</td>")) {
                        String infoString = line.substring(line.indexOf("<td>") + 4, line.indexOf("</td>"));

                        //get review link
                        if (infoString.contains("Review")) {
                            line = line.replace("td", "linkRating");
                        }

                        //get rating string
                        if (infoString.contains("out of") || infoString.matches("[A-D][+-]?|A|B|C|D")) {
                            line = line.replace("td", "rating");
                        }

                        //get date string and check valid
                        if (infoString.matches("^\\d{2}\\/\\d{2}\\/\\d{2}$")) {
                            line = line.replace("td", "date");
                        }
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
            result.setGameRatingList(gameDetailHandler.getList());
        } catch (Exception e) {
//            e.printStackTrace();
//            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
            log(e.getMessage());

        }
        return result;
    }
}
