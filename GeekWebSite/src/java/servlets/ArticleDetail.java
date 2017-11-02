/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.ArticleDAO;
import entities.Article;
import entities.ArticleList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilities.Const;

/**
 *
 * @author hoanglong
 */
public class ArticleDetail extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("articleId");

        try {
            Article article = ArticleDAO.findArticleById(Integer.parseInt(id));
            if (article.getLink().contains("gamek")) {
                if (article.getDescription().contains("<span class=\"IMSNoChangeStyle\" style=\"font-size: 22px;\"><strong>")) {
                    String removedLink = article.getDescription().substring(article.getDescription().indexOf("<span class=\"IMSNoChangeStyle\" style=\"font-size: 22px;\"><strong>"),
                            article.getDescription().indexOf("</a></strong></span>"));

                    article.setDescription(article.getDescription().replace(removedLink, ""));
                }

                if (article.getDescription().contains("<div class=\"VCSortableInPreviewMode link-content-footer\"")) {
                    String removedLink = article.getDescription().substring(article.getDescription().indexOf("<div class=\"VCSortableInPreviewMode link-content-footer\""),
                            article.getDescription().lastIndexOf("</a></div></div>"));

                    article.setDescription(article.getDescription().replace(removedLink, ""));
                }

            }

            String articlesString = utilities.Utilities.marshallerToString(article);
            articlesString = articlesString.replace("standalone=\"yes\"", "");

            request.setAttribute("ARTICLE_DETAIL", articlesString);

        } catch (Exception e) {
            log(e.getMessage());

        }finally {
            RequestDispatcher rd = request.getRequestDispatcher(Const.articleDetailPage);
            rd.forward(request, response);
            out.close();
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
