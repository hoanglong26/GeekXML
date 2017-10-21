/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import entities.Game;
import entities.GameRating;
import entities.Image;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author hoanglong
 */
public class ArticleDetailHandler extends DefaultHandler {

    String author;
    List<Image> list = new ArrayList<Image>();
    Image image;

    boolean inAuthorRow = false;

    public List<Image> getList() {
        return list;
    }

    public void setList(List<Image> list) {
        this.list = list;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atrbts) throws SAXException {
        if (qName.equalsIgnoreCase("author")) {
            inAuthorRow = true;
        } else if (qName.equalsIgnoreCase("img")) {
            image = new Image();
            image.setId(list.size());
            String link = atrbts.getValue("src");
            image.setAltImg(link.trim());
            image.setLink(link.trim());
            image.setType("hinh minh hoa");
            list.add(image);
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("author")) {
            inAuthorRow = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (inAuthorRow) {
            author += new String(ch, start, length);
            inAuthorRow = false;
        }

    }
}
