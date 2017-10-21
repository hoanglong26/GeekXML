/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.GameRating;
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
public class ImageDAO {

    static List<Image> images = new ArrayList();

    public static List<Image> getImages() {
        return images;
    }

    public static int createImage(Image aImage) {
        EntityManager em = Utilities.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(aImage);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                em.getTransaction().begin();
                em.merge(aImage);
                em.getTransaction().commit();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

//            try {
//                String query = "INSERT INTO [GeekDB].[dbo].[Image]\n"
//                        + "           ([AltImg]\n"
//                        + "           ,[Type]\n"
//                        + "           ,[ArticleId]\n"
//                        + "           ,[Link])"
//                        + "     VALUES\n"
//                        + "('" + aImage.getAltImg() + "',\n"
//                        + "'" + aImage.getType() + "',\n"
//                        + "" + aImage.getArticleId().getId() + ",\n"
//                        + "'" + aImage.getLink() + "')";
//                em.getTransaction().begin();
//                em.createNativeQuery(query).executeUpdate();
//                em.getTransaction().commit();
//            } catch (Exception ex) {
//                
//            }
        } finally {
            em.close();
        }

        return aImage.getId();
    }

    public static List<Image> getAllImage() {
        EntityManager em = Utilities.getEntityManager();
        try {
            TypedQuery<Image> query = em.createNamedQuery(
                    "Image.findAll", Image.class);

            List<Image> result = query.getResultList();
            if (!result.isEmpty()) {
                images = result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return images;
    }
}
