/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import entities.Article;
import entities.Game;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author hoanglong
 */
public class GameRankingHandler extends DefaultHandler {

    List<Game> list = new ArrayList<Game>();
    Game game;
    boolean inGameRow = false;
    boolean inNameRow = false;
    boolean inPlatformRow = false;
    boolean inTotalVoteRow = false;
    boolean inPublisherAndReleaseDateRow = false;
    boolean inOverallScoreRow = false;

    public List<Game> getList() {
        return list;
    }

    public void setList(List<Game> list) {
        this.list = list;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atrbts) throws SAXException {
        if (qName.equalsIgnoreCase("tr")) {
            inGameRow = true;
            game = new Game();
            game.setId(list.size());
        } else if (qName.equalsIgnoreCase("td")) {
            inPlatformRow = true;
        } else if (qName.equalsIgnoreCase("img")) {
            String thumbnail = atrbts.getValue("src");
            game.setThumbnail(thumbnail.trim());
            inPlatformRow = false;
        } else if (qName.equalsIgnoreCase("br") && atrbts.getValue("clear") == null) {
            inPublisherAndReleaseDateRow = true;
            inPlatformRow = false;

        } else if (qName.equalsIgnoreCase("br") && atrbts.getValue("clear").equals("left")) {
            inTotalVoteRow = true;
            inPlatformRow = false;

        } else if (qName.equalsIgnoreCase("b")) {
            inOverallScoreRow = true;
            inPlatformRow = false;
        } else if (qName.equalsIgnoreCase("a")) {
            String link = atrbts.getValue("href");
            game.setLink(("https://www.gamerankings.com" + link).trim());
            inNameRow = true;
            inPlatformRow = false;

        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("tr")) {
            inGameRow = false;
            list.add(game);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (inGameRow && inNameRow) {
            String name = new String(ch, start, length);
            game.setName(name.trim());
            inNameRow = false;
        } else if (inGameRow && inPlatformRow) {
            String platform = new String(ch, start, length);
            game.setPlatform(platform.trim());
            inPlatformRow = false;
        } else if (inGameRow && inOverallScoreRow) {
            String score = new String(ch, start, length);
            game.setOverallScore(score.trim());
            inOverallScoreRow = false;
        } else if (inGameRow && inPublisherAndReleaseDateRow) {
            String pubAndDate = new String(ch, start, length);
            game.setPulisherAndReleaseDate(pubAndDate.trim());
            inPublisherAndReleaseDateRow = false;
        } else if (inGameRow && inTotalVoteRow) {
            String vote = new String(ch, start, length);
            game.setTotalVote(vote.replace(" Reviews", " đánh giá").trim());
            inTotalVoteRow = false;
        }
    }

}
