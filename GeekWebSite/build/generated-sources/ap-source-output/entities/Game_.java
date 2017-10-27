package entities;

import entities.GameRating;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-28T02:49:22")
@StaticMetamodel(Game.class)
public class Game_ { 

    public static volatile SingularAttribute<Game, String> thumbnail;
    public static volatile ListAttribute<Game, GameRating> gameRating;
    public static volatile SingularAttribute<Game, String> totalVote;
    public static volatile SingularAttribute<Game, String> overallScore;
    public static volatile SingularAttribute<Game, String> name;
    public static volatile SingularAttribute<Game, String> link;
    public static volatile SingularAttribute<Game, String> pulisherAndReleaseDate;
    public static volatile SingularAttribute<Game, String> description;
    public static volatile SingularAttribute<Game, Integer> id;
    public static volatile SingularAttribute<Game, String> platform;

}