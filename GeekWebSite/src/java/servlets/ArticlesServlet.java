/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.ArticleDAO;
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
public class ArticlesServlet extends HttpServlet {

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
//        String from = request.getParameter("from");
//        String maxResult = request.getParameter("maxResult");

        try {
            ArticleList newestArticles = ArticleDAO.getArticleByRange(0, 6);
            String newestArticlesString = utilities.Utilities.marshallerToString(newestArticles);
            newestArticlesString = newestArticlesString.replace("standalone=\"yes\"", "");
            
            request.setAttribute("LAST_ARTICLE", newestArticles.getArticleList().get(0).getId());
            request.setAttribute("NEWEST_ARTICLES", newestArticlesString);

//            ArticleList otherArticles = ArticleDAO.getArticleByRange(6, 10);
//            String otherArticlesString = utilities.Utilities.marshallerToString(otherArticles);
//            otherArticlesString = otherArticlesString.replace("standalone=\"yes\"", "");
//
//            request.setAttribute("OTHER_ARTICLES", otherArticlesString);

        }catch (Exception e) {
            log(e.getMessage());

        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(Const.articlesPage);
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
