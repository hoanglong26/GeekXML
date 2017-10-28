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
@XmlRootElement(name = "articleList", namespace = Const.articleNamespace)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "articles", propOrder = "article")
public class ArticleList {

    @XmlElement(namespace = Const.articleNamespace)
    private List<Article> article;

    public List<Article> getArticleList() {
        return article;
    }

    public void setArticleList(List<Article> article) {
        this.article = article;
    }

   

}
