/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import entities.Article;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author hoanglong
 */
public class ArticleHandler extends DefaultHandler {

    List<Article> list = new ArrayList<Article>();
    Article article;
    boolean inArticleRow = false;
    boolean inTitleRow = false;
    boolean inLinkRow = false;
    boolean inOverviewRow = false;
    boolean inPubDateRow = false;
    boolean inAuthorRow = false;
    boolean inThumbnailRow = false;

    public List<Article> getList() {
        return list;
    }

    public void setList(List<Article> list) {
        this.list = list;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atrbts) throws SAXException {
        if (qName.equalsIgnoreCase("item")) {
            inArticleRow = true;
            article = new Article();
            article.setId(list.size());
        }
        if (inArticleRow) {
            if (qName.equalsIgnoreCase("title")) {
                inTitleRow = true;
            } else if (qName.equalsIgnoreCase("link")) {
                inLinkRow = true;
            } else if (qName.equalsIgnoreCase("description")) {
                inOverviewRow = true;
            } else if (qName.equalsIgnoreCase("pubDate")) {
                inPubDateRow = true;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("item")) {
            inArticleRow = false;
            list.add(article);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (inArticleRow && inTitleRow) {
            String title = new String(ch, start, length);
            article.setTitle(title.trim());
            inTitleRow = false;
        } else if (inArticleRow && inLinkRow) {
            String link = new String(ch, start, length);
            article.setLink(link.trim());
            inLinkRow = false;
        } else if (inArticleRow && inOverviewRow) {
            String description = new String(ch, start, length);
            article.setOverview(description.trim());
            inOverviewRow = false;
        } else if (inArticleRow && inPubDateRow) {
            String pubDate = new String(ch, start, length);
            article.setPubDate(pubDate.trim());
            inPubDateRow = false;
        }
//        else if (inArticleRow && inAuthorRow) {
//            String author = new String(ch, start, length);
//            article.setAuthor(author);
//            inAuthorRow = false;
//        }
    }

}
