package entities;

import entities.Game;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-19T00:36:49")
@StaticMetamodel(GameRating.class)
public class GameRating_ { 

    public static volatile SingularAttribute<GameRating, Game> gameId;
    public static volatile SingularAttribute<GameRating, String> score;
    public static volatile SingularAttribute<GameRating, String> reviewer;
    public static volatile SingularAttribute<GameRating, Integer> id;
    public static volatile SingularAttribute<GameRating, String> reviewedDate;

}