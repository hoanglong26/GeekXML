/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author hoanglong
 */
public class GamekHomepageHandler extends DefaultHandler {

    String rssPage;

    public String getRssPage() {
        return rssPage;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atrbts) throws SAXException {
        if (qName.equalsIgnoreCase("a")) {
            if (atrbts.getValue("class") != null && atrbts.getValue("class").equals("rss")) {
                rssPage = atrbts.getValue("href");
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

    }
}
