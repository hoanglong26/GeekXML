/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.ArticleDAO.articles;
import entities.Article;
import entities.Game;
import entities.GameList;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import utilities.Utilities;

/**
 *
 * @author hoanglong
 */
public class GameDAO {

    static List<Game> games = new ArrayList();

    public static List<Game> getGames() {
        return games;
    }

    public static int createGame(Game aGame) {
        EntityManager em = Utilities.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(aGame);
            em.getTransaction().commit();

        } catch (Exception e) {
//            e.printStackTrace();
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
//            System.out.println(aGame.getName());
        } finally {
            em.close();
        }

        return aGame.getId();
    }

    public static int createGame2(Game aGame) {
        EntityManager em = Utilities.getEntityManager();
        String query = "INSERT INTO [GeekDB].[dbo].[Game]\n"
                + "           ([Name]\n"
                + "           ,[Description]\n"
                + "           ,[PulisherAndReleaseDate]\n"
                + "           ,[Platform]\n"
                + "           ,[OverallScore]\n"
                + "           ,[Thumbnail]\n"
                + "           ,[TotalVote]\n"
                + "           ,[Link])\n"
                + "     VALUES\n"
                + "('" + aGame.getName() + "',\n"
                + "'" + aGame.getDescription() + "',\n"
                + "'" + aGame.getPulisherAndReleaseDate() + "',\n"
                + "'" + aGame.getPlatform() + "',\n"
                + "'" + aGame.getOverallScore() + "',\n"
                + "'" + aGame.getThumbnail() + "',\n"
                + "'" + aGame.getTotalVote() + "',\n"
                + "'" + aGame.getLink() + "')";
        try {
            em.getTransaction().begin();
            em.createNativeQuery(query).executeUpdate();
            em.flush();
            em.getTransaction().commit();

        } catch (Exception e) {
//            e.printStackTrace();
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            em.close();
        }

        return aGame.getId();
    }

    public static List<Game> getAllGame() {
        EntityManager em = Utilities.getEntityManager();
        try {
            TypedQuery<Game> query = em.createNamedQuery(
                    "Game.findAll", Game.class);

            List<Game> result = query.getResultList();
            if (!result.isEmpty()) {
                games = result;
            }
        } catch (Exception e) {
//            e.printStackTrace();
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            em.close();
        }
        return games;
    }

    public static GameList getGameRankingRange(int startFrom, int maxResult) {
        EntityManager em = Utilities.getEntityManager();
        GameList list = new GameList();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(Game.class);
            Root<Game> p = cq.from(Game.class);
            cq.orderBy(cb.desc(p.get("overallScore")));
            cq.select(p);
            Query query = em.createQuery(cq);
            query.setFirstResult(startFrom);
            query.setMaxResults(maxResult);
            list.setGameList(query.getResultList());
        } catch (Exception e) {
//            e.printStackTrace();
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            em.close();
        }
        return list;
    }

    public static List<Game> findGameByName(String name) {
        EntityManager em = Utilities.getEntityManager();
        List<Game> gameList = null;
        try {
            TypedQuery<Game> query = em.createNamedQuery(
                    "Game.findByName",
                    Game.class);
            query.setParameter("name", "%" + name + "%");

            List<Game> result = query.getResultList();
            if (!result.isEmpty()) {
                gameList = result;
            }
        } catch (Exception e) {
//            e.printStackTrace();
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            em.close();
        }
        return gameList;
    }

    public static Game getGameById(int id) {
        EntityManager em = Utilities.getEntityManager();
        Game game = null;

        try {
            TypedQuery<Game> query = em.createNamedQuery("Game.findById", Game.class);
            query.setParameter("id", id);

            game = query.getSingleResult();
        } catch (Exception e) {
//            e.printStackTrace();
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            em.close();
        }

        return game;
    }

    public static void deleteAll() {
        EntityManager em = Utilities.getEntityManager();
        String query = "DELETE FROM [GeekDB].[dbo].[Game]";
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
        String query = "DBCC CHECKIDENT (Game, RESEED, 0)";
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
