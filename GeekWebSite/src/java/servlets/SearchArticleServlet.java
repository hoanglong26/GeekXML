/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.ArticleDAO;
import entities.Article;
import entities.ArticleList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hoanglong
 */
public class SearchArticleServlet extends HttpServlet {

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
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String query = buffer.toString();

        query = query.toLowerCase();
        query = query.trim();
        try {
            ArticleList searchArticles = new ArticleList();
            List<Article> listByTitle = ArticleDAO.findArticleByTitle(query);
            List<Article> listByDescription = ArticleDAO.findArticleByDescription(query);
            List<Article> joinedList = new ArrayList();

            if (listByDescription != null) {
                joinedList.addAll(listByDescription);
            }
            if (listByTitle != null) {
                joinedList.addAll(listByTitle);
            }
            searchArticles.setArticleList(joinedList);
            String searchArticlesString = utilities.Utilities.marshallerToString(searchArticles);
            searchArticlesString = searchArticlesString.replace("standalone=\"yes\"", "");

            out.print(searchArticlesString);
        } catch (Exception e) {
//            e.printStackTrace();
            log(e.getMessage());
        } finally {
//            RequestDispatcher rd = request.getRequestDispatcher(Const.rankingPage);
//            rd.forward(request, response);

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
