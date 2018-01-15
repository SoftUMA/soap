package servlets;

import entity.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.UserService;
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
            System.out.println(UserService.getInstance().find(email));
            if ((u = UserService.getInstance().find(email)) == null)
            	UserService.getInstance().create(u = new User(email, name, surname, Properties.ROLE_USER));

            request.getSession().setAttribute(Properties.USER_SELECTED, u);
        } else if (checkLogoutParam(logout))
            request.getSession().setAttribute(Properties.USER_SELECTED, null);

        response.sendRedirect("index.jsp");
    }

    private boolean checkLoginParams(String email, String name, String surname) {
        return email != null && name != null && surname != null;
    }

    private boolean checkLogoutParam(String logout) {
        return logout != null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "User CRUD Servlet";
    }
}
