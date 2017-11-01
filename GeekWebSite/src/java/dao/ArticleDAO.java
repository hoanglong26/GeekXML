/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Article;
import entities.ArticleList;
import static java.rmi.server.LogStream.log;
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
//            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
            log(e.getMessage());
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
//            e.printStackTrace();
//            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
            log(e.getMessage());

        } finally {
            em.close();
        }
        return articles;
    }

    public static ArticleList getArticleByRange(int startFrom, int maxResult) {
        EntityManager em = Utilities.getEntityManager();
        ArticleList list = new ArticleList();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(Article.class);
            Root<Article> p = cq.from(Article.class);
            cq.orderBy(cb.desc(p.get("pubDate")));
            cq.select(p);
            Query query = em.createQuery(cq);
            query.setFirstResult(startFrom);
            query.setMaxResults(maxResult);
            List<Article> articleList = query.getResultList();

            for (Article item : articleList) {
                if (item.getThumbnail().contains("genknews")) {
                    if (item.getImageList().size() > 0) {
                        String newThumbnail = item.getImageList().get(0).getLink();
                        item.setThumbnail(newThumbnail);
                    }
                }
            }
            list.setArticleList(articleList);
        } catch (Exception e) {
//            e.printStackTrace();
//            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
            log(e.getMessage());

        } finally {
            em.close();
        }
        return list;
    }

    public static Article findArticleById(int id) {
        EntityManager em = Utilities.getEntityManager();
        Article result = null;
        try {
            TypedQuery<Article> query = em.createNamedQuery(
                    "Article.findById",
                    Article.class);
            query.setParameter("id", id);

            result = query.getSingleResult();
        } catch (Exception e) {
//            e.printStackTrace();
//            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
            log(e.getMessage());

        } finally {
            em.close();
        }
        return result;
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

                for (Article item : articleList) {
                    if (item.getThumbnail().contains("genknews")) {
                        if (item.getImageList().size() > 0) {
                            String newThumbnail = item.getImageList().get(0).getLink();
                            item.setThumbnail(newThumbnail);
                        }
                    }
                }

            }
        } catch (Exception e) {
//            e.printStackTrace();
//            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
            log(e.getMessage());

        } finally {
            em.close();
        }
        return articleList;
    }

    public static List<Article> findArticleByDescription(String str) {
        EntityManager em = Utilities.getEntityManager();
        List<Article> articleList = null;
        try {
            TypedQuery<Article> query = em.createNamedQuery(
                    "Article.findByDescription",
                    Article.class);
            query.setParameter("description", "%" + str + "%");

            List<Article> result = query.getResultList();
            if (!result.isEmpty()) {
                articleList = result;

                for (Article item : articleList) {
                    if (item.getThumbnail().contains("genknews")) {
                        if (item.getImageList().size() > 0) {
                            String newThumbnail = item.getImageList().get(0).getLink();
                            item.setThumbnail(newThumbnail);
                        }
                    }
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
//            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
            log(e.getMessage());

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
//            e.printStackTrace();
//            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
            log(e.getMessage());

        } finally {
            em.close();
        }

    }
}
