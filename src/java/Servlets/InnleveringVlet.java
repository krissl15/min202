/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Utilities.ModuleTools;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Doffen
 */
@WebServlet(name = "InnleveringVlet", urlPatterns = {"/InnleveringVlet"})
public class InnleveringVlet extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet InnleveringVlet</title>");
            out.println("</head>");
            out.println("<body>");

            String n = request.getRemoteUser(); //navnet på brukeren
            String module = request.getParameter("deliverModule");
            String sModuleID = module.substring(module.lastIndexOf(" ") + 1); //siste ordet i knappen er nr. på modulen. 
            int moduleID = Integer.parseInt(sModuleID);
            ModuleTools mt = new ModuleTools();
            
            String my = mt.checkIfDelivered(n, moduleID, out);
            
            if(my == "Levert"){
                //SHIT SOM SKAL SKJE OM DEN ER LEVERT
            }else{
                String timeStamp = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()); //kanskje denne blir needed

            out.println("<form action=\"InnleveringVlet\" method=\"post\">\n"
                    + "                <input type=\"text\" name=\"deliveryText\" placeholder=\"Youtube link/svartekst\"> <br><br>\n"
                    + "					<input type=\"submit\" name=\"btnDeliver\" value=\"Lever\">\n"
                    + "            </form>  ");
            }
            
        

            
            out.println("</body>");
            out.println("</html>");
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(InnleveringVlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(InnleveringVlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
