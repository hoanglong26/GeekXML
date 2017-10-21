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
@Table(name = "Image")
@XmlRootElement(namespace = Const.imageNamespace)
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "Image.findAll", query = "SELECT i FROM Image i")
    ,
    @NamedQuery(name = "Image.findById", query = "SELECT i FROM Image i WHERE i.id = :id")
    ,
    @NamedQuery(name = "Image.findByAltImg", query = "SELECT i FROM Image i WHERE i.altImg = :altImg")
    ,
    @NamedQuery(name = "Image.findByType", query = "SELECT i FROM Image i WHERE i.type = :type")})
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    @XmlElement(namespace = Const.imageNamespace)
    private Integer id;
    @Column(name = "Link")
    @XmlElement(namespace = Const.imageNamespace)
    private String link;
    @Column(name = "AltImg")
    @XmlElement(namespace = Const.imageNamespace)
    private String altImg;
    @Column(name = "Type")
    @XmlElement(namespace = Const.imageNamespace)
    private String type;
    @JoinColumn(name = "ArticleId", referencedColumnName = "ID")
    @ManyToOne(cascade = CascadeType.ALL)
    @XmlTransient
    private Article articleId;

    public Image() {
    }

    public Image(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAltImg() {
        return altImg;
    }

    public void setAltImg(String altImg) {
        this.altImg = altImg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Article getArticleId() {
        return articleId;
    }

    public void setArticleId(Article articleId) {
        this.articleId = articleId;
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
        if (!(object instanceof Image)) {
            return false;
        }
        Image other = (Image) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.Image[ id=" + id + " ]";
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
