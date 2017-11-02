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
public class GameDetailHandler extends DefaultHandler {

    List<GameRating> list = new ArrayList<GameRating>();
    GameRating gameRating;
    boolean inGameRatingRow = false;
    boolean inReviewerRow = false;
    boolean inDateRow = false;
    boolean inScoreRow = false;

    public List<GameRating> getList() {
        return list;
    }

    public void setList(List<GameRating> list) {
        this.list = list;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atrbts) throws SAXException {
        if (qName.equalsIgnoreCase("tr")) {
            gameRating = new GameRating();
            gameRating.setId(list.size());
            inGameRatingRow = true;
        } else if (qName.equalsIgnoreCase("a")) {
            if (atrbts.getValue("target") != null && atrbts.getValue("target").equals("_new")) {
                gameRating.setLinkRating(atrbts.getValue("href"));
            } else {
                inReviewerRow = true;
            }
        } else if (qName.equalsIgnoreCase("date")) {
            inDateRow = true;
        } else if (qName.equalsIgnoreCase("rating")) {
            inScoreRow = true;
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("tr")) {
            inGameRatingRow = false;
            list.add(gameRating);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (inGameRatingRow && inReviewerRow) {
            String reviewer = new String(ch, start, length);
            gameRating.setReviewer(reviewer);
            inReviewerRow = false;
        } else if (inGameRatingRow && inScoreRow) {
            String score = new String(ch, start, length);
            gameRating.setScore(score.replace("out of", "trên"));
            inScoreRow = false;
        } else if (inGameRatingRow && inDateRow) {
            String date = new String(ch, start, length);
            gameRating.setReviewedDate(date);
            inDateRow = false;
        }
    }
}
