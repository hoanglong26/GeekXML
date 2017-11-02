package entities;

import entities.Image;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-03T01:59:54")
@StaticMetamodel(Article.class)
public class Article_ { 

    public static volatile SingularAttribute<Article, String> overview;
    public static volatile SingularAttribute<Article, String> thumbnail;
    public static volatile SingularAttribute<Article, String> author;
    public static volatile SingularAttribute<Article, String> link;
    public static volatile SingularAttribute<Article, String> description;
    public static volatile SingularAttribute<Article, Integer> id;
    public static volatile SingularAttribute<Article, String> title;
    public static volatile SingularAttribute<Article, String> pubDate;
    public static volatile ListAttribute<Article, Image> imageList;

}