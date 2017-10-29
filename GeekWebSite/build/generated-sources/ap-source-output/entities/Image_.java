package entities;

import entities.Article;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-29T11:19:16")
@StaticMetamodel(Image.class)
public class Image_ { 

    public static volatile SingularAttribute<Image, String> altImg;
    public static volatile SingularAttribute<Image, String> link;
    public static volatile SingularAttribute<Image, Article> articleId;
    public static volatile SingularAttribute<Image, Integer> id;
    public static volatile SingularAttribute<Image, String> type;

}