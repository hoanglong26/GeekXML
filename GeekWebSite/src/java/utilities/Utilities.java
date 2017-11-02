/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import static java.rmi.server.LogStream.log;
import java.text.Normalizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.InputSource;

/**
 *
 * @author hoanglong
 */
public class Utilities {

    private static EntityManagerFactory emf;
    EntityManager em = null;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("GeekWebSitePU");
        } catch (Exception e) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
//            e.printStackTrace();
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static <T> String marshallerToString(T object) {
        try {
            JAXBContext jaxb = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxb.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            StringWriter sw = new StringWriter();
            marshaller.marshal(object, sw);
            return sw.toString();
        } catch (Exception ex) {
            log(ex.getMessage());

//            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
//            ex.printStackTrace();
        }
        return null;
    }

    public static boolean validateXMLBeforeSaveToDatabase(String xmlData, String schemaPath) {
        try {
            SchemaFactory sf = SchemaFactory.newInstance(
                    XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(schemaPath));
            Validator valid = schema.newValidator();
            InputSource is = new InputSource(new StringReader(xmlData));
            valid.validate(new SAXSource(is));
            return true;
            //saveToDatabase(articleList);
        } catch (Exception e) {
            log(e.getMessage());

//            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, e);
//            e.printStackTrace();
            return false;
        }
    }
}
