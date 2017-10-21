/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import entities.Game;
import entities.GameRating;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author hoanglong
 */
public class ArticleOverviewHandler extends DefaultHandler {

    String imgLink;
    String overView;

    boolean inOverviewRow = false;

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes atrbts) throws SAXException {
        if (qName.equalsIgnoreCase("img")) {
            String thumbnail = atrbts.getValue("src");
            imgLink = thumbnail;
        } else if (qName.equalsIgnoreCase("br")) {
            inOverviewRow = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("root")) {
            inOverviewRow = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (inOverviewRow) {
            overView = new String(ch, start, length);
        }
    }
}
