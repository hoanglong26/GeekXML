/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author hoanglong
 */
public class GamekRssPageHandler extends DefaultHandler {

    List<String> rssList;

    public List<String> getRssList() {
        return rssList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atrbts) throws SAXException {
        if (qName.equalsIgnoreCase("a")) {
            if (rssList == null) {
                rssList = new ArrayList<String>();
            }
            String rssPage = atrbts.getValue("href");

            if (!rssList.contains(rssPage)) {
                rssList.add("http://gamek.vn"+rssPage);
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
