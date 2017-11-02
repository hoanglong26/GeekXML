/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.GameDAO;
import entities.GameList;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.afp.util.StringUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.xml.sax.SAXException;

/**
 *
 * @author hoanglong
 */
public class GameRankingDownloadServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
        String lastGameId = request.getParameter("lastGameId");
        GameList gameList = GameDAO.getGameRankingRange(0, Integer.parseInt(lastGameId));
//        String xmlString = StringUtils.transformToFOPString(utilities.Utilities.marshallerToString(gameList));

//        request.getAttribute(null)
        try {
            String xmlString = utilities.Utilities.marshallerToString(gameList);
            String path = getServletContext().getRealPath("/");
            String xsltPath = path + "content/xslt/gameRankingDownload.xsl";
            ByteArrayOutputStream outputFo = new ByteArrayOutputStream();
            methodTrAX(xsltPath, xmlString, outputFo, path);

            ByteArrayOutputStream outFile = new ByteArrayOutputStream();
            response.setContentType("application/pdf");

            FopFactory ff = FopFactory.newInstance();
            ff.setUserConfig(path + "WEB-INF/config.xml");
            FOUserAgent fua = ff.newFOUserAgent();

            Fop fop = ff.newFop(MimeConstants.MIME_PDF, fua, outFile);
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer trans = tff.newTransformer();

            Source src = new StreamSource(new ByteArrayInputStream(outputFo.toByteArray()));
            Result result = new SAXResult(fop.getDefaultHandler());
            trans.transform(src, result);
            byte[] content = outFile.toByteArray();
            response.setContentLength(content.length);
            response.getOutputStream().write(content);
            response.getOutputStream().flush();
        } catch (TransformerException ex) {
            log(ex.getMessage());
//            Logger.getLogger(GameRankingDownloadServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FOPException ex) {
            log(ex.getMessage());

//            Logger.getLogger(GameRankingDownloadServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            log(ex.getMessage());

//            Logger.getLogger(GameRankingDownloadServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//            out.close();
        }
    }

    private void methodTrAX(String xslPath, String xmlString, ByteArrayOutputStream output, String path) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            StreamSource xsltFile = new StreamSource(xslPath);
            Transformer trans = tf.newTransformer(xsltFile);
            trans.setParameter("pathFile", path);

            InputStream is = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));

            StreamSource xmlFile = new StreamSource(is);
            StreamResult htmlFile = new StreamResult(output);
            trans.transform(xmlFile, htmlFile);
        } catch (TransformerConfigurationException ex) {
            log(ex.getMessage());
//            Logger.getLogger(GameRankingDownloadServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            log(ex.getMessage());
//            Logger.getLogger(GameRankingDownloadServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
