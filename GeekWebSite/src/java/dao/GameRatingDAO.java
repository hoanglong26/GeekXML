/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Article;
import entities.Game;
import entities.GameRating;
import static java.rmi.server.LogStream.log;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import utilities.Utilities;

/**
 *
 * @author hoanglong
 */
public class GameRatingDAO {

    static List<GameRating> gameRatings = new ArrayList();

    public static List<GameRating> getGameRatings() {
        return gameRatings;
    }

    public static int createGameRating(GameRating aGameRating) {
        EntityManager em = Utilities.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(aGameRating);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().begin();
            em.merge(aGameRating);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return aGameRating.getId();
    }

    public static List<GameRating> getAllGameRating() {
        EntityManager em = Utilities.getEntityManager();
        try {
            TypedQuery<GameRating> query = em.createNamedQuery(
                    "GameRating.findAll", GameRating.class);

            List<GameRating> result = query.getResultList();
            if (!result.isEmpty()) {
                gameRatings = result;
            }
        } catch (Exception e) {
//            e.printStackTrace();
            log(e.getMessage());

//            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            em.close();
        }
        return gameRatings;
    }

    public static void deleteAll() {
        EntityManager em = Utilities.getEntityManager();
        String query = "DELETE FROM [GeekDB].[dbo].[GameRating]";
        try {
            em.getTransaction().begin();
            em.createNativeQuery(query).executeUpdate();
            em.getTransaction().commit();

        } catch (Exception e) {
//            e.printStackTrace();
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            em.close();
        }
    }

    public static void resetIdent() {
        EntityManager em = Utilities.getEntityManager();
        String query = "DBCC CHECKIDENT (GameRating, RESEED, 0)";
        try {
            em.getTransaction().begin();
            em.createNativeQuery(query).executeUpdate();
            em.getTransaction().commit();

        } catch (Exception e) {
//            e.printStackTrace();
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            em.close();
        }

    }
}
