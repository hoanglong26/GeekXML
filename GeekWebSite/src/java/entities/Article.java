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
@Table(name = "Article")
@XmlRootElement(namespace = Const.articleNamespace)
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "Article.findAll", query = "SELECT a FROM Article a")
    ,
    @NamedQuery(name = "Article.findById", query = "SELECT a FROM Article a WHERE a.id = :id")
    ,
    @NamedQuery(name = "Article.findByTitle", query = "SELECT a FROM Article a WHERE a.title LIKE :title")
    ,
    @NamedQuery(name = "Article.findByLink", query = "SELECT a FROM Article a WHERE a.link = :link")
    ,
    @NamedQuery(name = "Article.findByDescription", query = "SELECT a FROM Article a WHERE a.description = :description")
    ,
    @NamedQuery(name = "Article.findByPubDate", query = "SELECT a FROM Article a WHERE a.pubDate = :pubDate")
    ,
    @NamedQuery(name = "Article.findByThumbnail", query = "SELECT a FROM Article a WHERE a.thumbnail = :thumbnail")
    ,
    @NamedQuery(name = "Article.findByOverview", query = "SELECT a FROM Article a WHERE a.overview = :overview")})
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    @XmlElement(namespace = Const.articleNamespace)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Title")
    @XmlElement(namespace = Const.articleNamespace)
    private String title;
    @Column(name = "Link")
    @XmlElement(namespace = Const.articleNamespace)
    private String link;
    @Column(name = "Description")
    @XmlElement(namespace = Const.articleNamespace)
    private String description;
    @Column(name = "PubDate")
    @XmlElement(namespace = Const.articleNamespace)
    private String pubDate;
    @Column(name = "Thumbnail")
    @XmlElement(namespace = Const.articleNamespace)
    private String thumbnail;
    @Column(name = "Overview")
    @XmlElement(namespace = Const.articleNamespace)
    private String overview;
    @Column(name = "Author")
    @XmlElement(namespace = Const.articleNamespace)
    private String author;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "articleId")
    @XmlTransient
    private List<Image> imageList = new ArrayList<Image>();

    public Article() {
    }

    public Article(Integer id) {
        this.id = id;
    }

    public Article(Integer id, String title) {
        this.id = id;
        this.title = title;
    }
    
    public void addImage(Image img) {
        img.setArticleId(this);
        imageList.add(img);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @XmlTransient
    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
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
        if (!(object instanceof Article)) {
            return false;
        }
        Article other = (Article) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.Article[ id=" + id + " ]";
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
