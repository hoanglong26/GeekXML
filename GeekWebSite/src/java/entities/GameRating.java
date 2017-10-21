/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "GameRating")
@XmlRootElement(namespace = Const.gameRatingNamespace)
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "GameRating.findAll", query = "SELECT g FROM GameRating g")
    ,
    @NamedQuery(name = "GameRating.findById", query = "SELECT g FROM GameRating g WHERE g.id = :id")
    ,
    @NamedQuery(name = "GameRating.findByReviewedDate", query = "SELECT g FROM GameRating g WHERE g.reviewedDate = :reviewedDate")
    ,
    @NamedQuery(name = "GameRating.findByScore", query = "SELECT g FROM GameRating g WHERE g.score = :score")})
public class GameRating implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    @XmlElement(namespace = Const.gameRatingNamespace)
    private Integer id;
    @Column(name = "Score")
    @XmlElement(namespace = Const.gameRatingNamespace)
    private String score;
    @Column(name = "Reviewer")
    @XmlElement(namespace = Const.gameRatingNamespace)
    private String reviewer;
    @Column(name = "ReviewedDate")
    @XmlElement(namespace = Const.gameRatingNamespace)
    private String reviewedDate;
    @JoinColumn(name = "GameId", referencedColumnName = "ID")
    @ManyToOne(cascade = CascadeType.ALL)
    @XmlTransient
    private Game gameId;

    public GameRating() {
    }

    public GameRating(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReviewedDate() {
        return reviewedDate;
    }

    public void setReviewedDate(String reviewedDate) {
        this.reviewedDate = reviewedDate;
    }

    public Game getGameId() {
        return gameId;
    }

    public void setGameId(Game gameId) {
        this.gameId = gameId;
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
        if (!(object instanceof GameRating)) {
            return false;
        }
        GameRating other = (GameRating) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.GameRating[ id=" + id + " ]";
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

}
