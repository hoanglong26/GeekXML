/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
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
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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
            e.printStackTrace();
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static Document parseDOMFromFile(String fileXMLPath)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(fileXMLPath);

        return doc;
    }

    public static Document stringToDom(String xmlSource)
            throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlSource)));
    }

    public static <T> void marshallerToTransfer(T object, OutputStream os) {
        try {
            JAXBContext jaxb = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxb.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(object, os);
        } catch (JAXBException ex) {
        }
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
            ex.printStackTrace();
        }
        return null;
    }

    public static <T> void marshallerToFile(T object, File file) {
        try {
            JAXBContext jaxb = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxb.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
            e.printStackTrace();
            return false;
        }
    }

    public static String insertString(String str, int index, String value) {
        return str.substring(0, index) + value + str.substring(index);
    }

    public static String downloadImage(String realPath, String folderPath, String fileName, String uri) {
        String filePath = "";
        try {
            String extension = uri.substring(uri.lastIndexOf(".") + 1);
            filePath = folderPath + "\\" + fileName + "." + extension;
            filePath = covertStringToURL(filePath);

            URL url = new URL(uri);
            BufferedImage img = ImageIO.read(url);
            File file = new File(realPath + filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            ImageIO.write(img, extension, file);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        filePath = filePath.replaceAll("\\\\", "/");
        return realPath + folderPath + filePath;
    }

    public static String covertStringToURL(String str) {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "-").replaceAll("đ", "d");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
