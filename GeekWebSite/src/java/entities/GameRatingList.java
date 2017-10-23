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
@XmlRootElement(name = "gameRatingList", namespace = Const.gameRatingNamespace)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ratings", propOrder = "rating")
public class GameRatingList {

    @XmlElement(namespace = Const.gameNamespace)
    private List<GameRating> rating;

    public List<GameRating> getGameList() {
        return rating;
    }

    public void setGameList(List<GameRating> rating) {
        this.rating = rating;
    }

}
