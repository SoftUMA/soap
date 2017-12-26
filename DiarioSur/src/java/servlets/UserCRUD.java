package servlets;

import entity.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.UserREST;
import util.Properties;

@WebServlet(name = "UserCRUD", urlPatterns = {"/UserCRUD"})
public class UserCRUD extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String logout = request.getParameter("logout");

        if (checkLoginParams(email, name, surname)) {
            User u;
            if ((u = UserREST.getInstance().find(email)) == null)
                UserREST.getInstance().create(u = new User(email, name, surname, Properties.ROLE_USER));

            request.getSession().setAttribute(Properties.USER_SELECTED, u);
        } else if (checkLogoutParam(logout))
            request.getSession().setAttribute(Properties.USER_SELECTED, null);

        response.sendRedirect("index.jsp");
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


    private boolean checkLoginParams(String email, String name, String surname) {
        return email != null && name != null && surname != null;
    }

    private boolean checkLogoutParam(String logout) {
        return logout != null;
    }
}
