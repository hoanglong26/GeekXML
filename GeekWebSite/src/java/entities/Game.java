/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import utilities.Const;

/**
 *
 * @author hoanglong
 */
@Entity
@Table(name = "Game")
@XmlRootElement(namespace = Const.gameNamespace)
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g")
    ,
    @NamedQuery(name = "Game.findById", query = "SELECT g FROM Game g WHERE g.id = :id")
    ,
    @NamedQuery(name = "Game.findByName", query = "SELECT g FROM Game g WHERE g.name LIKE :name")
    ,
    @NamedQuery(name = "Game.findByThumbnail", query = "SELECT g FROM Game g WHERE g.thumbnail = :thumbnail")})
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    @XmlElement(namespace = Const.gameNamespace)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Name")
    @XmlElement(namespace = Const.gameNamespace)
    private String name;
    @Column(name = "Link")
    @XmlElement(namespace = Const.gameNamespace)
    private String link;
    @Column(name = "PulisherAndReleaseDate")
    @XmlElement(namespace = Const.gameNamespace)
    private String pulisherAndReleaseDate;
    @Column(name = "Platform")
    @XmlElement(namespace = Const.gameNamespace)
    private String platform;
    @Column(name = "OverallScore")
    @XmlElement(namespace = Const.gameNamespace)
    private String overallScore;
    @Column(name = "TotalVote")
    @XmlElement(namespace = Const.gameNamespace)
    private String totalVote;
    @Column(name = "Description")
    @XmlElement(namespace = Const.gameNamespace)
    private String description;
    @Column(name = "Thumbnail")
    @XmlElement(namespace = Const.gameNamespace)
    private String thumbnail;
    @OneToMany(mappedBy = "gameId", cascade = CascadeType.ALL)
    @XmlElement(namespace = Const.gameNamespace)
    private List<GameRating> gameRating = new ArrayList<GameRating>();

    public Game() {
    }

    public Game(Integer id) {
        this.id = id;
    }

    public Game(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Game(String name, String releaseDate, String pulisher, String developer, String thumbnail, List<GameRating> gameRatingList) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.gameRating = gameRatingList;
    }
    
    public void addRating(GameRating rating) {
        rating.setGameId(this);
        gameRating.add(rating);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @XmlTransient
    public List<GameRating> getGameRatingList() {
        return gameRating;
    }

    public void setGameRatingList(List<GameRating> gameRatingList) {
        this.gameRating = gameRatingList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.Game[ id=" + id + " ]";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPulisherAndReleaseDate() {
        return pulisherAndReleaseDate;
    }

    public void setPulisherAndReleaseDate(String pulisherAndReleaseDate) {
        this.pulisherAndReleaseDate = pulisherAndReleaseDate;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(String overallScore) {
        this.overallScore = overallScore;
    }

    public String getTotalVote() {
        return totalVote;
    }

    public void setTotalVote(String totalVote) {
        this.totalVote = totalVote;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
