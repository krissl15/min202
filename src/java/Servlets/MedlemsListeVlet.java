/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Utilities.MemberTools;
import Utilities.RandomTools;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Doffen
 */
@WebServlet(name = "MedlemsListeVlet", urlPatterns = {"/MedlemsListeVlet"})
public class MedlemsListeVlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     * @throws javax.naming.NamingException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, NamingException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MedlemsListeVlet</title>");
            out.println("</head>");
            out.println("<body>");
            MemberTools memt = new MemberTools();

            if (request.isUserInRole("Foreleser")) { //Sjekk om brukeren er foreleser
                out.println("Registrerte medlemmer<br>");
                memt.printMembersByRole("RegistrertStudent", out); //priner registrerte studenter
                out.println("<br>Ikke registrerte medlemmer<br>");
                memt.printMembersByRole("UregistrertStudent", out); //printer uregistrerte
                out.println("<br>Assistenter<br>");
                  memt.printMembersByRole("Assistent",out);

                String change = request.getParameter("member"); //alle knappene heter det samme ("member")
                String aCheck = request.getParameter("addCheck");
                String rCheck = request.getParameter("removeCheck");

                if (change.contains("Registrer")) { //Sjekker om knappen er en "fjern" eller "Registrer

                    String name = change.substring(change.lastIndexOf(" ") + 1); // "name" blir siste ordet i valuen av knappen (change).
                    if (aCheck.contains(name)) { //checkbox check
                        memt.registerStudent(name, out); //registrert brukeren når knappen blir trykket
                        memt.addToModulKanal(name, out);
                        response.sendRedirect("MedlemsListeVlet"); //Oppdaterer siden ved å directe brukeren til samme side
                    }
                }

                if (change.contains("Fjern")) { //Sjekker om knappen er en "fjern" eller "Registrer
                    String name = change.substring(change.lastIndexOf(" ") + 1); // "name" blir siste ordet i valuen av knappen (change).
                    if (rCheck.contains(name)) {
                        memt.unRegister(name, out);
                        memt.removeFromModulKanal(name, out);
                        response.sendRedirect("MedlemsListeVlet");
                    }
                }
                
                 if(change.contains("Ta bort assistent")){ //Sjekker om knappen er en "fjern" eller "Registrer
                 String name = change.substring(change.lastIndexOf(" ")+1); // "name" blir siste ordet i valuen av knappen (change).
                 memt.unRegister(name, out);
                 response.sendRedirect("MedlemsListeVlet");
                 
             }
             
             if(change.contains("Assistent")){
                 String name = change.substring(change.lastIndexOf(" ")+1); // "name" blir siste ordet i valuen av knappen (change).
                 memt.registerAssistent(name, out);
                 response.sendRedirect("MedlemsListeVlet");
            }
                
                
                
                
                
            } else if (request.isUserInRole("RegistrertStudent")) { //Studenter ser kun registrerte brukere
                out.print("Registrerte brukere: <br><br>");
                memt.printRegisteredMembers(out);

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
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(MedlemsListeVlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MedlemsListeVlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(MedlemsListeVlet.class.getName()).log(Level.SEVERE, null, ex);
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
