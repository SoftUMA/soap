package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import me.xdrop.fuzzywuzzy.*;
import util.Properties;
import entity.*;
import service.EventService;
import service.CategoryService;
import util.Coordinates;

/**
 * Servlet implementation class EventCRUD
 */
@WebServlet("/EventCRUD")
public class EventCRUD extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object tmp;
        User user = null;
        if ((tmp = request.getSession().getAttribute(Properties.USER_SELECTED)) != null)
            user = (User) tmp;

        int opcode = request.getParameter(Properties.PARAM_OPCODE) != null ? Integer.parseInt(request.getParameter(Properties.PARAM_OPCODE)) : -1;
        int id = request.getParameter(Properties.PARAM_ID) != null ? Integer.parseInt(request.getParameter(Properties.PARAM_ID)) : -1;
        String name = request.getParameter(Properties.PARAM_NAME);
        String description = request.getParameter(Properties.PARAM_DESCRIPTION);
        String image = request.getParameter(Properties.PARAM_IMAGE);
        String tag = request.getParameter(Properties.PARAM_TAG);
        String date = request.getParameter(Properties.PARAM_DATE);
        String address = request.getParameter(Properties.PARAM_ADDRESS);
        String price = request.getParameter(Properties.PARAM_PRICE);
        String shopUrl = request.getParameter(Properties.PARAM_SHOPURL);
        String category = request.getParameter(Properties.PARAM_CATEGORY);
        String latitude_ = request.getParameter(Properties.PARAM_LATITUDE);
        String longitude_ = request.getParameter(Properties.PARAM_LONGITUDE);
        String radius_ = request.getParameter(Properties.PARAM_RADIUS);
        String keywords = request.getParameter(Properties.PARAM_KEYWORDS);
        String free = request.getParameter(Properties.PARAM_FREE);
        String own = request.getParameter(Properties.PARAM_OWN);

        switch (opcode) {
            case Properties.OP_CREATE:
                createEvent(name, user, description, date, price, address, shopUrl, image, category);
                break;
            case Properties.OP_EDIT:
                editEvent(id, name, user, description, date, price, address, shopUrl, image, tag, category);
                break;
            case Properties.OP_DELETE:
                removeEvent(id, user);
                break;
            case Properties.OP_APPROVE:
                approveEvent(id, user);
                break;
            case Properties.OP_FILTER:
                response.setContentType("text/html;charset=UTF-8");

                double radius = Properties.DEFAULT_RADIUS;
                double latitude = Properties.DEFAULT_LATITUDE;
                double longitude = Properties.DEFAULT_LONGITUDE;
                try {
                    radius = Double.parseDouble(radius_);
                } catch (NullPointerException | NumberFormatException ex) { }
                try {
                    latitude = Double.parseDouble(latitude_);
                } catch (NullPointerException | NumberFormatException ex) { }
                try {
                    longitude = Double.parseDouble(longitude_);
                } catch (NullPointerException | NumberFormatException ex) { }

                radius *= 1000;

                try (PrintWriter out = response.getWriter()) {
                    Coordinates userCoords = new Coordinates(latitude, longitude);
                    out.print(filterEvent(user, userCoords, radius, keywords, category, free, own));
                }

                return;
            default:
                System.err.println("Error @ opcode");
                break;
        }

        RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }

    // ================================================
    // Final web services calls

    /**
     * Calls the API event creation method
     * @param event the event to create
     */
    private void createEvent(Event event) {
        EventService.getInstance().create(event);
    }

    /**
     * Calls the API event edition method
     * @param event the event to edit
     */
    private void editEvent(Event event) {
    	EventService.getInstance().edit(event, event.getId());
    }

    /**
     * Calls the API event removal method
     * @param event the event to remove
     */
    private void removeEvent(Event event) {
    	EventService.getInstance().remove(event.getId());
    }

    /**
     * Calls the API event dump method
     * @return a containing every event in the database
     */
    private List<Event> findAllEvents() {
        return EventService.getInstance().findAll();
    }

    /**
     * Calls the API event search method
     * @param id the <code>id</code> of the event to search
     * @return the found event, <code>null</code> if no event was found
     */
    private Event findEvent(Integer id) {
        return EventService.getInstance().find(id);
    }

    /**
     * Calls the API category search method
     * @param name the <code>name</code> of the category to search
     * @return the found category, <code>null</code> if no category was found
     */
    private Category findCategory(String name) {
        return CategoryService.getInstance().find(name);
    }

    // ================================================
    // Actions processing

    /**
     * Process event creation
     * @param name event name
     * @param author event author
     * @param desc event description
     * @param date event start and end dates
     * @param price event price
     * @param address event address
     * @param shopUrl event ticket shopping URL
     * @param image event image URL
     * @param category event category
     */
    private void createEvent(String name, User author, String desc, String date, String price, String address, String shopUrl, String image, String category) {
        if (!checkCreateParams(name, author, desc, date, price, address, shopUrl, image, category))
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

        Long id = (long) (name + author.getEmail() + startDate + endDate).hashCode();
        String approved = author.getRole().equals(Properties.ROLE_SUPER) || author.getRole().equals(Properties.ROLE_EDITOR) ? "1" : "0";
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
        refereeEvent.setPrice(price);
        refereeEvent.setShopUrl(shopUrl);
        refereeEvent.setApproved(approved);
        createEvent(refereeEvent);
    }

    /**
     * Process event edition
     * @param id id of the event to edit
     * @param name new event name
     * @param user editing user
     * @param desc new event description
     * @param date new event start and end dates
     * @param price new event price
     * @param address new event address
     * @param shopUrl new event ticket shopping URL
     * @param image new event image URL
     * @param tag tag to search the new event image by
     * @param category new event category
     */
    private void editEvent(int id, String name, User user, String desc, String date, String price, String address, String shopUrl, String image, String tag, String category) {
        if (!checkEditParams(name, user, desc, date, price, address, shopUrl, image, tag, category))
            return;

        Event refereeEvent = findEvent(id);
        if (refereeEvent != null && (refereeEvent.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().equals(Properties.ROLE_EDITOR))) {
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
            refereeEvent.setCategory(category_);
            refereeEvent.setStartDate(startDate);
            refereeEvent.setEndDate(endDate);
            refereeEvent.setAddress(address);
            refereeEvent.setPrice(price);
            refereeEvent.setShopUrl(shopUrl);
            if (tag.length() != 0)
                refereeEvent.setImage(image);

            editEvent(refereeEvent);
        }
    }

    /**
     * Process event removal
     * @param id id of the event to remove
     * @param user removing user
     */
    private void removeEvent(int id, User user) {
        Event refereeEvent = findEvent(id);
        if (refereeEvent != null && (refereeEvent.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().equals(Properties.ROLE_EDITOR)))
            removeEvent(refereeEvent);
    }

    /**
     * Process event approval
     * @param id id of the event to approve
     * @param user approving user
     */
    private void approveEvent(int id, User user) {
        Event refereeEvent = findEvent(id);
        if (refereeEvent != null && user.getRole().equals(Properties.ROLE_EDITOR)) {
            refereeEvent.setApproved("1");
            editEvent(refereeEvent);
        }
    }

    // ================================================
    // Event filtering

    /**
     * Filters events based on various characteristics
     * @param user filtering user
     * @param userCoords filtering user's browser coordinates
     * @param radius radius to include events from, centered on <code>userCoords</code>
     * @param keywords search field text
     * @param category category to filter by
     * @param free <code>1</code> to get only free events, <code>0</code> otherwise
     * @param own <code>1</code> to get only events created by <code>user</code>, <code>0</code> otherwise
     * @return a string containing the HTML code with all the filtered events
     */
    private String filterEvent(User user, Coordinates userCoords, double radius, String keywords, String category, String free, String own) {
        if (!checkFilterParams(keywords, category, free, own))
            return "";

        String answer = "";
        Map<Long, Event> filteredEvents;
        List<Event> events = findAllEvents();

        if (events != null && !events.isEmpty()) {
            filteredEvents = new HashMap<>();

            if (own.equals("1")) {
                for (Event e : events)
                    if (e.getAuthor().equals(user) && inRadius(userCoords, e, radius))
                        filteredEvents.put(e.getId(), e);
            } else {
                for (Event e : events)
                    if (inRadius(userCoords, e, radius) && filtersMatch(e, free, category, keywords))
                        filteredEvents.put(e.getId(), e);
            }

            answer = buildJSON(filteredEvents.values(), user);
        }

        return answer;
    }

    /**
     * Builds a JSON containing both the info needed to build the map and the HTML code containing the event cards
     * @param events collection of events to build the JSON
     * @param user the user request the events filtering
     * @return a String containing the JSON with all the filtered events info
     */
    private String buildJSON(Collection<Event> events, User user) {
        StringBuilder sb = new StringBuilder("");

        sb.append("{\n");
        sb.append(buildMapInfo(events, user)).append(",\n");
        sb.append(buildCards(events, user)).append("\n");
        sb.append("}");

        return sb.toString();
    }

    /**
     * Builds the information needed to build the map on the user's browser
     * @param events collection of events to build the map info
     * @param user the user requesting the events filtering
     * @return structured events information
     */
    private String buildMapInfo(Collection<Event> events, User user) {
        StringBuilder sb = new StringBuilder("");
        boolean first = true;

        sb.append("  \"map\": [");
        for (Event e : events) {
            if (first) first = false;
            else sb.append(",");
            sb.append("{\n");
            sb.append("    \"title\": \"").append(e.getName()).append("\",\n");
            sb.append("    \"desc\": \"").append(e.getDescription()).append("\",\n");
            Coordinates coords = new Coordinates(e.getAddress());
            sb.append("    \"lat\": ").append(coords.getLatitude()).append(",\n");
            sb.append("    \"lng\": ").append(coords.getLongitude()).append(",\n");
            sb.append("    \"own\": ").append(e.getAuthor().equals(user) ? "true" : "false").append("\n");
            sb.append("  }");
        }
        sb.append("]");

        return sb.toString();
    }

    /**
     * Builds the HTML code to embed in the DOM from a collection of events
     * @param events collection of events to build the HTML code
     * @param user the user requesting the events filtering
     * @return HTML code with the cards to embed in the DOM
     */
    private String buildCards(Collection<Event> events, User user) {
        StringBuilder sb = new StringBuilder("");

        sb.append("  \"cards\": \"");
        for (Event e : events) {
            sb.append("<div class=\'card");

            if (e.getApproved().equals("0") && (user.equals(e.getAuthor()) || user.getRole().equals(Properties.ROLE_EDITOR)))
                sb.append(" border-danger");
            else
                sb.append(" border-dark");

            sb.append(" wow zoomIn\' data-wow-delay=\'0.5s\'><img class=\'card-img-top rounded\' src=\'");
            sb.append(e.getImage());
            sb.append("\' alt=\'");
            sb.append(e.getName());
            sb.append("\' data-toggle=\'modal\' data-target=\'#eventModal");
            sb.append(e.getId());
            sb.append("\' style=\'cursor: pointer;\'><div class=\'card-body\'><h4 class=\'card-title\'>");
            sb.append(e.getName());
            sb.append("</h4>");

            if (e.getApproved().equals("0") && (user.equals(e.getAuthor()) || user.getRole().equals(Properties.ROLE_EDITOR)))
                sb.append("<p class=\'card-text text-danger\'>Revisión pendiente</p>");

            sb.append("<p class=\'card-text\'>");

            if (e.getDescription().length() < 80)
                sb.append(e.getDescription());
            else
                sb.append(e.getDescription().substring(0, 80)).append("...");

            sb.append("</p><button type=\'button\' class=\'btn btn-warning\' data-toggle=\'modal\' data-target=\'#eventModal");
            sb.append(e.getId());
            sb.append("\'>Ver evento</button></div></div>");
        }
        sb.append("\"");

        return sb.toString();
    }

    /**
     * Checks if a event is within the radius of a user's coordinates
     * @param user user's browser coordinates
     * @param event event coordinates
     * @param radius radius to check in
     * @return <code>true</code> if the event is within the radius, centered on the user coordinates, <code>false</code> otherwise
     */
    private boolean inRadius(Coordinates user, Event event, double radius) {
        return user.inRadius(new Coordinates(event.getAddress()), radius);
    }

    /**
     * Check if a event matches the requested filters
     * @param event event to check upon
     * @param free <code>1</code> to get only free events, <code>0</code> otherwise
     * @param category category to filter by
     * @param keywords search field text
     * @return <code>true</code> if the event matches the filters, <code>false</code> otherwise
     */
    private boolean filtersMatch(Event event, String free, String category, String keywords) {
        return freeRequested(event, free) || categoryMatches(event, category) || keywordsMatch(event, keywords);
    }

    /**
     * Checks if the user request only free events
     * @param event event to check upon
     * @param free <code>1</code> to get only free events, <code>0</code> otherwise
     * @return <code>true</code> if the user requested only free events, <code>false</code> otherwise
     */
    private boolean freeRequested(Event event, String free) {
        return free.equals("1") && event.getPrice().equals("0");
    }

    /**
     * Checks if the event matches the requested category
     * @param event event to check upon
     * @param category category to check
     * @return <code>true</code> if the event matches the category, <code>false</code> otherwise
     */
    private boolean categoryMatches(Event event, String category) {
        return event.getCategory().getName().equals(category);
    }

    /**
     * Checks if the event matches the search field text
     * @param event event to check upon
     * @param keywords search field text
     * @return <code>true</code> if the event matches the keywords, <code>false</code> otherwise
     */
    private boolean keywordsMatch(Event event, String keywords) {
        return keywords.length() > 0
               && (FuzzySearch.tokenSetPartialRatio(keywords, event.getName()) >= 90
                   || FuzzySearch.tokenSetPartialRatio(keywords, event.getDescription()) >= 75
                   || FuzzySearch.tokenSetPartialRatio(keywords, event.getAddress()) >= 90
                   || FuzzySearch.tokenSetPartialRatio(keywords, event.getAuthor().getName()) >= 80
                   || FuzzySearch.tokenSetPartialRatio(keywords, event.getStartDate().substring(0, event.getStartDate().length() - 13)) >= 80
                   || FuzzySearch.tokenSetPartialRatio(keywords, event.getEndDate().substring(0, event.getEndDate().length() - 13)) >= 80);
    }

    // ================================================
    // Params checking

    /**
     * Checks if the event creation params are OK
     * @param name event name
     * @param author event author
     * @param desc event description
     * @param date event start and end dates
     * @param price event price
     * @param address event address
     * @param shopUrl event ticket shopping URL
     * @param image event image URL
     * @param category event category
     * @return <code>true</code> if the params are OK, <code>false</code> otherwise
     */
    private boolean checkCreateParams(String name, User author, String desc, String date, String price, String address, String shopUrl, String image, String category) {
        return name != null && author != null && desc != null && date != null && price != null && address != null && shopUrl != null && image != null && category != null && !category.equals(Properties.NIL_CATEGORY);
    }

    /**
     * Checks if the event edition params are OK
     * @param name new event name
     * @param user editing user
     * @param desc new event description
     * @param date new event start and end dates
     * @param price new event price
     * @param address new event address
     * @param shopUrl new event ticket shopping URL
     * @param image new event image URL
     * @param tag tag to search the new event image by
     * @param category new event category
     * @return <code>true</code> if the params are OK, <code>false</code> otherwise
     */
    private boolean checkEditParams(String name, User user, String desc, String date, String price, String address, String shopUrl, String image, String tag, String category) {
        return name != null && user != null && desc != null && date != null && price != null && address != null && shopUrl != null && image != null && tag != null && category != null && !category.equals(Properties.NIL_CATEGORY);
    }

    /**
     * Checks if the event filtering params are OK
     * @param keywords search field text
     * @param category event category
     * @param free search for free events ?
     * @param own search for events owned by the user ?
     * @return <code>true</code> if the params are OK, <code>false</code> otherwise
     */
    private boolean checkFilterParams(String keywords, String category, String free, String own) {
        return keywords != null && category != null && free != null && own != null;
    }

    // ================================================
    // Servlet GET & POST processing methods

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

    // ================================================
    // Servlet information

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Event CRUD Servlet";
    }

}
