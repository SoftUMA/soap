/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceRef;
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

    // Reference to Service at Generated Sources
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/EventWS/EventWS.wsdl")
    private EventWS_Service eventService;

    // Reference to an explicit WS
    private EventWS eventPort;
    private UserWS userPort;
    private CategoryWS categoryPort;

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
        
        HttpSession session = request.getSession();
        
        int opcode = request.getParameter("opcode") != null ? Integer.parseInt(request.getParameter("opcode")) : -1;
        int id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : -1;
        StringTokenizer st;
        
        String name;
        String author;
        String description;
        String image;
        String category;
        String startDate;
        String endDate;
        String address;
        double price;
        String shopurl;
        int approved;
        
        Event refereeEvent;
        User sessionUser = findUser(session.getAttribute(Properties.USER_SELECTED));
        Category eventCategory;
        
        switch (opcode) {
            case 0:
                if (id == -1) {
                    // Create Event Code
                    // Event information from formulary
                    name = request.getParameter("name");
                    author = request.getParameter("author");
                    description = request.getParameter("description");
                    image = request.getParameter("image");
                    category = request.getParameter("category");

                    // Date Parser
                    st = new StringTokenizer(request.getParameter("date"), "~");
                    
                    startDate = st.nextToken();
                    startDate = startDate.trim();
                    startDate += ":00.000";
                    
                    endDate = st.nextToken();
                    endDate = endDate.trim();
                    endDate += ":00.000";
                    
                    address = request.getParameter("address");
                    price = Double.parseDouble(request.getParameter("price"));
                    shopurl = request.getParameter("shopurl");
                    
                    // Obtaining hash for Event ID
                    id = (name + author + startDate + endDate).hashCode();
                    
                    if (sessionUser.getRole() == Properties.ROLE_SUPER || sessionUser.getRole() == Properties.ROLE_EDITOR)
                        approved = 1;
                    else 
                        approved = 0;
                    
                    // New Event to insert
                    refereeEvent = new Event();

                    // Retrieving session User and Category of the new Event
                    sessionUser = (User) session.getAttribute("role");
                    
                    eventCategory = findCategory(request.getParameter("category"));

                    // Setting new Event
                    refereeEvent.setId(id);
                    refereeEvent.setName(name);
                    refereeEvent.setAuthor(sessionUser);
                    refereeEvent.setDescription(description);
                    refereeEvent.setImage(image);
                    refereeEvent.setCategory(eventCategory);
                    refereeEvent.setStartDate(startDate);
                    refereeEvent.setEndDate(endDate);
                    refereeEvent.setAddress(address);
                    refereeEvent.setPrice(price);
                    refereeEvent.setShopUrl(shopurl);
                    refereeEvent.setApproved(approved);
                    
                    // Creating into database
                    createEvent(refereeEvent);
                } else 
                    System.err.println("Error en el id del evento.");
                
                break;
            case 1:
                // Edit Event Code

                name = request.getParameter("name");
                description = request.getParameter("description");
                image = request.getParameter("image");
                category = request.getParameter("category");

                // Date Parser
                st = new StringTokenizer(request.getParameter("date"), "~");
                
                startDate = st.nextToken();
                startDate = startDate.trim();
                startDate += ":00.000";
                
                endDate = st.nextToken();
                endDate = endDate.trim();
                endDate += ":00.000";
                address = request.getParameter("address");
                price = Double.parseDouble(request.getParameter("price"));
                shopurl = request.getParameter("shopurl");
                
                // Event to modify
                refereeEvent = findEvent(id);
                
                eventCategory = findCategory(request.getParameter("category"));

                // Setting Event
                refereeEvent.setName(name);
                refereeEvent.setDescription(description);
                refereeEvent.setImage(image);
                refereeEvent.setCategory(eventCategory);
                refereeEvent.setStartDate(startDate);
                refereeEvent.setEndDate(endDate);
                refereeEvent.setAddress(address);
                refereeEvent.setPrice(price);
                refereeEvent.setShopUrl(shopurl);
                refereeEvent.setApproved(approved);
                
                // Editing into database
                editEvent(refereeEvent);
                break;
            case 2:
                // Delete Event Code

                // Deleting from database
                removeEvent(findEvent(id));
                break;
            case 3:
                if (sessionUser.getRole() == Properties.ROLE_SUPER || sessionUser.getRole() == Properties.ROLE_EDITOR) {
                    // Event to approve
                    refereeEvent = findEvent(id);

                    // Setting Event
                    refereeEvent.setApproved(1);

                    // Editing into database
                    editEvent(refereeEvent);
                }
                
                break;
            default:
                System.err.println("Error en OPcode");
                break;
        }
        
        RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/index.jsp");
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

    private void createEvent(Event event) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        eventPort = eventService.getEventWSPort();
        eventPort.createEvent(event);
    }
    
    private void editEvent(Event event) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        eventPort = eventService.getEventWSPort();
        eventPort.editEvent(event);
    }
    
    private void removeEvent(Event event) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        eventPort = eventService.getEventWSPort();
        eventPort.removeEvent(event);
    }
    
    private Event findEvent(Object id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        eventPort = eventService.getEventWSPort();
        return eventPort.findEvent(id);
    }
    
    private User findUser(Object id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        userPort = userService.getUserWSPort();
        return userPort.findUser(id);
    }
    
    private Category findCategory(Object id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        categoryPort = categoryService.getCategoryWSPort();
        return categoryPort.findCategory(id);
    }
}
