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

    String description;
    List<GameRating> list = new ArrayList<GameRating>();
    GameRating gameRating;
    boolean inGameRatingRow = false;
    boolean inReviewerRow = false;
    boolean inDateRow = false;
    boolean inScoreRow = false;
    boolean inDescriptionRow = false;

    public List<GameRating> getList() {
        return list;
    }

    public void setList(List<GameRating> list) {
        this.list = list;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atrbts) throws SAXException {
        if (qName.equalsIgnoreCase("description")) {
            inDescriptionRow = true;
        } else if (qName.equalsIgnoreCase("tr")) {
            gameRating = new GameRating();
            gameRating.setId(list.size());
            inGameRatingRow=true;
        } else if (qName.equalsIgnoreCase("a")) {
            inReviewerRow = true;
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
        if (inDescriptionRow) {
            description = new String(ch, start, length);
            inDescriptionRow = false;
        } else if (inGameRatingRow && inReviewerRow) {
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
