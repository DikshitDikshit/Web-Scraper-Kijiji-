/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import common.FileUtility;
import common.ValidationException;
import entity.Category;
import entity.Image;
import entity.Item;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.CategoryLogic;
import logic.ImageLogic;
import logic.ItemLogic;
import scraper.kijiji.Kijiji;
import scraper.kijiji.KijijiItem;

/**
 *
 * @author Dikshit Dikshit
 */
@WebServlet(name = "KijijiView", urlPatterns = {"/Kijiji"})
public class KijijiView extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            ItemLogic logic = new ItemLogic();
            List<Item> entities = logic.getAll();
            out.println("<!DOCTYPE html>");

            out.println("<html>");

            out.println("<head>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href='style\\KijijiStyle.css'>");

            out.println("<title>Servlet KijijiView</title>");
            out.println("</head>");
            out.println("<body>");

            out.println("<h1>Servlet KijijiView at " + request.getContextPath() + "</h1>");
            out.println("<div class=\"center-column\">");
            for (Item e : entities) {
                out.println("<div class=\"item\">");
                out.println("<div class=\"image\">");
                out.printf("<img src=\"image/%s.jpg\" style=\"max-width: 250px; max-height: 200px;\" />", e.getId());
                out.println("</div>");
                out.println("<div class=\"details\">");
                out.println("<div class=\"title\">");
                out.printf("<a href=\"%s\" target=\"_blank\">%s</a>", e.getUrl(), e.getTitle());
                out.println("</div>");
                out.println("<div class=\"price\">");
                if (e.getPrice() != null) {
                    out.println("$" + e.getPrice());
                } else {
                    out.println("Price not mentioned(Please Contact)");
                }
                out.println("</div>");
                out.println("<div class=\"date\">");
                out.println(e.getDate());
                out.println("</div>");
                out.println("<div class=\"location\">");
                out.println(e.getLocation());
                out.println("</div>");
                out.println("<div class=\"description\">");
                out.println(e.getDescription());
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");

                out.println("</body>");
                out.println("</html>");

            }
        }
    }

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
        String path = System.getProperty("user.home") + "/KijijiImages/";
        new File(path).mkdir();
        ItemLogic itemLogic = new ItemLogic();
        Kijiji kijiji = new Kijiji();
        for (int j = 0; j < new CategoryLogic().getAll().size(); j++) {
            Category category = new CategoryLogic().getWithId(j + 1);
            kijiji = kijiji.downloadPage(category.getUrl()).findAllItems();
            kijiji.proccessItems(i
                    -> {
                Image image;
                Item item;

                Map<String, String[]> itemMap = new HashMap<>();
                Map<String, String[]> imagemMap = new HashMap<>();
                if (new ItemLogic().getWithId(Integer.parseInt(i.getId())) == null) {
                    if (new ImageLogic().getWithPath(path + i.getId() + ".jpg") == null) {
                        FileUtility.downloadAndSaveFile(i.getImageUrl(), path, i.getId() + ".jpg");
                        imagemMap.put(ImageLogic.URL, new String[]{i.getImageUrl()});
                        imagemMap.put(ImageLogic.NAME, new String[]{i.getImageName()});
                        imagemMap.put(ImageLogic.PATH, new String[]{path + i.getId() + ".jpg"});
                        try {
                            image = new ImageLogic().createEntity(imagemMap);
                            new ImageLogic().add(image);

                            itemMap.put(ItemLogic.ID, new String[]{i.getId()});
                            itemMap.put(ItemLogic.URL, new String[]{i.getUrl()});
                            itemMap.put(ItemLogic.DATE, new String[]{i.getDate()});
                            itemMap.put(ItemLogic.TITLE, new String[]{i.getTitle()});
                            itemMap.put(ItemLogic.PRICE, new String[]{i.getPrice()});
                            itemMap.put(ItemLogic.LOCATION, new String[]{i.getLocation()});
                            itemMap.put(ItemLogic.DESCRIPTION, new String[]{i.getDescription()});

                            item = itemLogic.createEntity(itemMap);
                            item.setCategory(category);
                            item.setImage(image);
                            itemLogic.add(item);
                        } catch (ValidationException e) {
                            
                        }
                    }
                }
            });
        }
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
        return "Kijiji view";
    }// </editor-fold>

}