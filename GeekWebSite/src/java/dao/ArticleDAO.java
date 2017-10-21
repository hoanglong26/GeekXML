/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Article;
import entities.Game;
import entities.Image;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import utilities.Utilities;

/**
 *
 * @author hoanglong
 */
public class ArticleDAO {

    static List<Article> articles = new ArrayList();

    public static List<Article> getArticles() {
        return articles;
    }

    public static int createArticle(Article article) {
        EntityManager em = Utilities.getEntityManager();

        try {

            em.getTransaction().begin();
            em.persist(article);
            em.getTransaction().commit();
        } catch (Exception e) {

        } finally {
            em.close();
        }

        return article.getId();
    }

    public static List<Article> getAllArticle() {
        EntityManager em = Utilities.getEntityManager();
        try {
            TypedQuery<Article> query = em.createNamedQuery(
                    "Article.findAll", Article.class);

            List<Article> result = query.getResultList();
            if (!result.isEmpty()) {
                articles = result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return articles;
    }

    public static List<Article> findArticleByTitle(String title) {
        EntityManager em = Utilities.getEntityManager();
        List<Article> articleList = null;
        try {
            TypedQuery<Article> query = em.createNamedQuery(
                    "Article.findByTitle",
                    Article.class);
            query.setParameter("title", "%" + title + "%");

            List<Article> result = query.getResultList();
            if (!result.isEmpty()) {
                articleList = result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return articleList;
    }

    public static void deleteAll() {
        EntityManager em = Utilities.getEntityManager();
        String query = "DELETE FROM [GeekDB].[dbo].[Article]";
        try {
            em.getTransaction().begin();
            em.createNativeQuery(query).executeUpdate();
            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

    }
}
