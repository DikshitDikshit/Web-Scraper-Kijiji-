/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entity.Item;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.CategoryLogic;
import logic.ImageLogic;
import logic.ItemLogic;

/**
 *
 * @author User
 */
@WebServlet(name = "CreateItem", urlPatterns = {"/CreateItem"})
public class CreateItem extends HttpServlet {
private String errorMessage = null;
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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Create Item</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<div style=\"text-align: center;\">");
            out.println("<div style=\"display: inline-block; text-align: left;\">");
            out.println("<form method=\"post\">");
            out.println("Item ID:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>",ItemLogic.ID);
            out.println("Image ID:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>",ItemLogic.IMAGE_ID);
            out.println("URL:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>",ItemLogic.URL);
            out.println("Category ID:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>",ItemLogic.CATEGORY_ID);
            out.println("Price:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>",ItemLogic.PRICE);
            out.println("Title:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>",ItemLogic.TITLE);
            out.println("Date:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>",ItemLogic.DATE);
            out.println("Loction:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>",ItemLogic.LOCATION);
            out.println("Description:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>",ItemLogic.DESCRIPTION);
            out.println("<br>");
            out.println("<input type=\"submit\" name=\"view\" value=\"Add and View\">");
            out.println("<input type=\"submit\" name=\"add\" value=\"Add\">");
            out.println("</form>");
            if(errorMessage!=null&&!errorMessage.isEmpty()){
                out.println("<p color=red>");
                out.println("<font color=red size=4px>");
                out.println(errorMessage);
                out.println("</font>");
                out.println("</p>");
            }
            out.println("<pre>");
            out.println("Submitted keys and values:");
            out.println(toStringMap(request.getParameterMap()));
            out.println("</pre>");
            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    private String toStringMap(Map<String, String[]> values) {
        StringBuilder builder = new StringBuilder();
        values.forEach((k, v) -> builder.append("Key=").append(k)
                .append(", ")
                .append("Value/s=").append(Arrays.toString(v))
                .append(System.lineSeparator()));
        return builder.toString();
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
        try{
        ItemLogic iLogic = new ItemLogic();
        CategoryLogic category = new CategoryLogic();
        String id = request.getParameter(ItemLogic.ID);
        
        if(iLogic.getWithId(Integer.parseInt(id))==null){
            
            Item item = iLogic.createEntity(request.getParameterMap());
            
            item.setCategory(category.getWithId(Integer.parseInt(request.getParameter(ItemLogic.CATEGORY_ID))));
            item.setImage(new ImageLogic().getWithId(Integer.parseInt(request.getParameter(ItemLogic.IMAGE_ID))));
            iLogic.add(item);
            
        }else{
            errorMessage = "Username: \"" + id + "\" already exists";
        }
        if(request.getParameter("add")!=null){
            processRequest(request, response);
        }else if(request.getParameter("view")!=null){
            response.sendRedirect("ItemTable");
        }}catch(Exception e){
            
        }
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "yo";
    }// </editor-fold>

}
