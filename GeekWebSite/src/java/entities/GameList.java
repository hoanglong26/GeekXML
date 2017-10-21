/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import utilities.Const;

/**
 *
 * @author hoanglong
 */
@XmlRootElement(name = "gameList", namespace = Const.gameNamespace)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "games", propOrder = "game")
public class GameList {

    @XmlElement(namespace = Const.gameNamespace)
    private List<Game> game;

    public List<Game> getGameList() {
        return game;
    }

    public void setGameList(List<Game> game) {
        this.game = game;
    }

}
