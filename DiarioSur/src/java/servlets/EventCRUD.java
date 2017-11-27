/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
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
            throws ServletException, IOException{
        
        HttpSession session = request.getSession();
        
        int OPcode;
        int eventId;
        
        // Retrieving information about what to do.
        if (request.getParameter("opcode") != null)
             OPcode = Integer.parseInt(request.getParameter("opcode"));
        else
            OPcode = -1;
        
        if (request.getParameter("id") != null)
            eventId = Integer.parseInt(request.getParameter("id"));
        else
            eventId = -1;
        
        switch(OPcode){
            case 0:
                // Create Event Code
                
                // Event information from formulary
                int id = 0;
                String name =  request.getParameter("name");
                String author = request.getParameter("author");
                String description = request.getParameter("description");
                String image = request.getParameter("image");
                String category = request.getParameter("category");
                // TODO startDate
                // TODO endDate
                String address = request.getParameter("address");
                double price = Double.parseDouble(request.getParameter("price"));
                String shopUrl = request.getParameter("shopurl");
                // TODO checkUser
                int approved = Integer.parseInt(request.getParameter("approved"));
                
                // New Event to insert
                Event newEvent = new Event();
                
                // Retrieving session User and Category of the new Event
                User sessionUser = (User)session.getAttribute("role");
                
                Category eventCategory = findCategory(request.getParameter("category"));
                
                // Setting new Event
                newEvent.setId(id);
                newEvent.setName(name);
                newEvent.setAuthor(sessionUser);
                newEvent.setDescription(description);                
                newEvent.setImage(image);
                newEvent.setCategory(eventCategory);
                newEvent.setStartDate("1111-22-33 00:00:00.000");
                newEvent.setEndDate("2222-33-44 00:00:00.000");
                newEvent.setAddress(address);
                newEvent.setPrice(price);
                newEvent.setShopUrl(shopUrl);
                newEvent.setApproved(approved);
                
                createEvent(newEvent);               
                break;
            case 1:
                // Edit Event Code
                
                
                break;
            case 2:
                // Delete Event Code
                
                
                break;
            case -1:
                System.err.println("Error en OPcode");
                break;
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

    private void createEvent(ws.Event event) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        eventPort = eventService.getEventWSPort();
        eventPort.createEvent(event);
    }

    private void editEvent(ws.Event event) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        eventPort = eventService.getEventWSPort();
        eventPort.editEvent(event);
    }

    private void removeEvent(ws.Event event) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        eventPort = eventService.getEventWSPort();
        eventPort.removeEvent(event);
    }

    private Event findEvent(java.lang.Object id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        eventPort = eventService.getEventWSPort();
        return eventPort.findEvent(id);
    }

    private User findUser(java.lang.Object id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        userPort = userService.getUserWSPort();
        return userPort.findUser(id);
    }

    private Category findCategory(java.lang.Object id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        categoryPort = categoryService.getCategoryWSPort();
        return categoryPort.findCategory(id);
    }

}
