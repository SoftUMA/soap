/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceRef;
import util.Properties;
import ws.*;

/**
 *
 * @author Asde
 */
@WebServlet(name = "EventCRUD", urlPatterns = {"/EventCRUD"})
public class EventCRUD extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/CategoryWS/CategoryWS.wsdl")
    private CategoryWS_Service categoryService;
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/UserWS/UserWS.wsdl")
    private UserWS_Service userService;
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/EventWS/EventWS.wsdl")
    private EventWS_Service eventService;

    private EventWS eventPort;
    private UserWS userPort;
    private CategoryWS categoryPort;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String sessionUser;
        if (session.getAttribute(Properties.USER_SELECTED) == null)
            session.setAttribute(Properties.USER_SELECTED, sessionUser = Properties.USER_GUEST);
        else
            sessionUser = (String) session.getAttribute(Properties.USER_SELECTED);
        User user = null;
        if (!sessionUser.equals(Properties.USER_GUEST))
            user = findUser(sessionUser);

        int opcode = request.getParameter(Properties.PARAM_OPCODE) != null ? Integer.parseInt(request.getParameter(Properties.PARAM_OPCODE)) : -1;
        int id = request.getParameter(Properties.PARAM_ID) != null ? Integer.parseInt(request.getParameter(Properties.PARAM_ID)) : -1;
        String name = request.getParameter(Properties.PARAM_NAME);
        String description = request.getParameter(Properties.PARAM_DESCRIPTION);
        String image = request.getParameter(Properties.PARAM_IMAGE);
        String date = request.getParameter(Properties.PARAM_DATE);
        String address = request.getParameter(Properties.PARAM_ADDRESS);
        String price = request.getParameter(Properties.PARAM_PRICE);
        String shopUrl = request.getParameter(Properties.PARAM_SHOPURL);
        String category = request.getParameter(Properties.PARAM_CATEGORY);
        String keywords = request.getParameter(Properties.PARAM_KEYWORDS);
        String free = request.getParameter(Properties.PARAM_FREE);
        String own = request.getParameter(Properties.PARAM_OWN);

        switch (opcode) {
            case Properties.OP_CREATE:
                createEvent(name, user, description, date, price, address, shopUrl, image, category);
                break;
            case Properties.OP_EDIT:
                editEvent(id, name, user, description, date, price, address, shopUrl, image, category);
                break;
            case Properties.OP_DELETE:
                removeEvent(id, user);
                break;
            case Properties.OP_APPROVE:
                approveEvent(id, user);
                break;
            case Properties.OP_FILTER:
                filterEvent(user, keywords, category, free, own);
                break;
            default:
                System.err.println("Error @ opcode");
                break;
        }

        RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
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
    }

    private void createEvent(Event event) {
        eventPort = eventService.getEventWSPort();
        eventPort.createEvent(event);
    }

    private void editEvent(Event event) {
        eventPort = eventService.getEventWSPort();
        eventPort.editEvent(event);
    }

    private void removeEvent(Event event) {
        eventPort = eventService.getEventWSPort();
        eventPort.removeEvent(event);
    }

    private Event findEvent(Object id) {
        eventPort = eventService.getEventWSPort();
        return eventPort.findEvent(id);
    }

    private User findUser(Object id) {
        userPort = userService.getUserWSPort();
        return userPort.findUser(id);
    }

    private Category findCategory(Object id) {
        categoryPort = categoryService.getCategoryWSPort();
        return categoryPort.findCategory(id);
    }

    private void createEvent(String name, User author, String desc, String date, String price, String address, String shopUrl, String image, String category) {
        if (!checkCreateParams(name, desc, date, price, address, shopUrl, image, category) || author.getEmail().equals(Properties.USER_GUEST))
            return;

        // Date Parser
        StringTokenizer st;
        st = new StringTokenizer(date, "~");
        String startDate = st.nextToken();
        startDate = startDate.trim();
        startDate += ":00.000";
        String endDate = st.nextToken();
        endDate = endDate.trim();
        endDate += ":00.000";

        double price_ = Double.parseDouble(price);
        int id = (name + author.getEmail() + startDate + endDate).hashCode();
        int approved = author.getRole() == Properties.ROLE_SUPER || author.getRole() == Properties.ROLE_EDITOR ? 1 : 0;
        Event refereeEvent = new Event();
        Category category_ = findCategory(category);

        // Setting new Event
        refereeEvent.setId(id);
        refereeEvent.setName(name);
        refereeEvent.setAuthor(author);
        refereeEvent.setDescription(desc);
        refereeEvent.setImage(image);
        refereeEvent.setCategory(category_);
        refereeEvent.setStartDate(startDate);
        refereeEvent.setEndDate(endDate);
        refereeEvent.setAddress(address);
        refereeEvent.setPrice(price_);
        refereeEvent.setShopUrl(shopUrl);
        refereeEvent.setApproved(approved);
        createEvent(refereeEvent);
    }

    private void editEvent(int id, String name, User user, String desc, String date, String price, String address, String shopUrl, String image, String category) {
        if (!checkEditParams(name, desc, date, price, address, shopUrl, image, category))
            return;

        Event refereeEvent = findEvent(id);
        if (refereeEvent != null && (refereeEvent.getAuthor().equals(user) || user.getRole() == Properties.ROLE_EDITOR)) {
            double price_ = Double.parseDouble(price);
            Category category_ = findCategory(category);

            // Date Parser
            StringTokenizer st;
            st = new StringTokenizer(date, "~");
            String startDate = st.nextToken();
            startDate = startDate.trim();
            startDate += ":00.000";
            String endDate = st.nextToken();
            endDate = endDate.trim();
            endDate += ":00.000";

            // Setting Event
            refereeEvent.setName(name);
            refereeEvent.setDescription(desc);
            refereeEvent.setImage(image);
            refereeEvent.setCategory(category_);
            refereeEvent.setStartDate(startDate);
            refereeEvent.setEndDate(endDate);
            refereeEvent.setAddress(address);
            refereeEvent.setPrice(price_);
            refereeEvent.setShopUrl(shopUrl);
            editEvent(refereeEvent);
        }
    }

    private void removeEvent(int id, User user) {
        Event refereeEvent = findEvent(id);
        if (refereeEvent != null && (refereeEvent.getAuthor().equals(user) || user.getRole() == Properties.ROLE_EDITOR))
            removeEvent(refereeEvent);
    }

    private void approveEvent(int id, User user) {
        Event refereeEvent = findEvent(id);
        if (refereeEvent != null && user.getRole() == Properties.ROLE_EDITOR) {
            refereeEvent.setApproved(1);
            editEvent(refereeEvent);
        }
    }

    private void filterEvent(User user, String keywords, String category, String free, String own) {
        if (!checkFilterParams(keywords, category, free, own))
            return;
    }

    private boolean checkCreateParams(String name, String desc, String date, String price, String address, String shopUrl, String image, String category) {
        return name != null && desc != null && date != null && price != null && address != null && shopUrl != null && image != null && category != null;
    }

    private boolean checkEditParams(String name, String desc, String date, String price, String address, String shopUrl, String image, String category) {
        return name != null && desc != null && date != null && price != null && address != null && shopUrl != null && image != null && category != null;
    }

    private boolean checkFilterParams(String keywords, String category, String free, String own) {
        return keywords != null && category != null && free != null && own != null;
    }
}
