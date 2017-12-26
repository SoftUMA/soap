package servlets;

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
import javax.servlet.http.HttpSession;
import me.xdrop.fuzzywuzzy.*;
import util.Properties;
import entity.*;
import service.CategoryREST;
import service.EventREST;
import service.UserREST;

@WebServlet(name = "EventCRUD", urlPatterns = {"/EventCRUD"})
public class EventCRUD extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

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

                try (PrintWriter out = response.getWriter()) {
                    out.print(filterEvent(user, keywords, category, free, own));
                }

                return;
            default:
                System.err.println("Error @ opcode");
                break;
        }

        RequestDispatcher rd = this.getServletContext().getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }

    private void createEvent(Event event) {
        EventREST.getInstance().create(event);
    }

    private void editEvent(Event event) {
        EventREST.getInstance().edit(event, event.getId());
    }

    private void removeEvent(Event event) {
        EventREST.getInstance().remove(event.getId());
    }

    private List<Event> findAllEvents() {
        return EventREST.getInstance().findAll();
    }

    private Event findEvent(Integer id) {
        return EventREST.getInstance().find(id);
    }

    private User findUser(String email) {
        return UserREST.getInstance().find(email);
    }

    private Category findCategory(String name) {
        return CategoryREST.getInstance().find(name);
    }

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

        int id = (name + author.getEmail() + startDate + endDate).hashCode();
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

    private void removeEvent(int id, User user) {
        Event refereeEvent = findEvent(id);
        if (refereeEvent != null && (refereeEvent.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().equals(Properties.ROLE_EDITOR)))
            removeEvent(refereeEvent);
    }

    private void approveEvent(int id, User user) {
        Event refereeEvent = findEvent(id);
        if (refereeEvent != null && user.getRole().equals(Properties.ROLE_EDITOR)) {
            refereeEvent.setApproved("1");
            editEvent(refereeEvent);
        }
    }

    private String filterEvent(User user, String keywords, String category, String free, String own) {
        if (!checkFilterParams(keywords, category, free, own))
            return "";
        String answer = "";
        Map<Integer, Event> filteredEvents;
        List<Event> events = findAllEvents();

        if (events != null && !events.isEmpty()) {
            filteredEvents = new HashMap<>();
            if (own.equals("1")) {
                for (Event e : events)
                    if (e.getAuthor().equals(user))
                        filteredEvents.put(e.getId(), e);
            } else {
                for (Event e : events) {
                    if (!filteredEvents.containsKey(e.getId())
                        && (free.equals("1") && e.getPrice().equals("0")
                            || e.getCategory().getName().equals(category)
                            || (keywords.length() > 0
                                && (FuzzySearch.tokenSetPartialRatio(keywords, e.getName()) >= 90
                                    || FuzzySearch.tokenSetPartialRatio(keywords, e.getDescription()) >= 75
                                    || FuzzySearch.tokenSetPartialRatio(keywords, e.getAddress()) >= 90
                                    || FuzzySearch.tokenSetPartialRatio(keywords, e.getAuthor().getName()) >= 80
                                    || FuzzySearch.tokenSetPartialRatio(keywords, e.getStartDate().substring(0, e.getStartDate().length() - 13)) >= 80
                                    || FuzzySearch.tokenSetPartialRatio(keywords, e.getEndDate().substring(0, e.getEndDate().length() - 13)) >= 80)))) {
                        filteredEvents.put(e.getId(), e);
                    }
                }
            }

            answer = buildCards(filteredEvents.values(), user);
        }

        return answer;
    }

    private String buildCards(Collection<Event> events, User user) {
        StringBuilder sb = new StringBuilder("");

        for (Event e : events) {
            sb.append("<div class=\"card");

            if (e.getApproved().equals("0") && (user.getEmail().equals(e.getAuthor().getEmail()) || user.getEmail().equals(Properties.USER_EDITOR)))
                sb.append(" border-danger");
            else
                sb.append(" border-dark");

            sb.append(" wow zoomIn\" data-wow-delay=\"0.5s\"><img class=\"card-img-top rounded\" src=\"");
            sb.append(e.getImage());
            sb.append("\" alt=\"");
            sb.append(e.getName());
            sb.append("\" data-toggle=\"modal\" data-target=\"#eventModal");
            sb.append(e.getId());
            sb.append("\" style=\"cursor: pointer;\"><div class=\"card-body\"><h4 class=\"card-title\">");
            sb.append(e.getName());
            sb.append("</h4>");

            if (e.getApproved().equals("0") && (user.getEmail().equals(e.getAuthor().getEmail()) || user.getEmail().equals(Properties.USER_EDITOR))) {
                sb.append("<p class=\"card-text text-danger\">Revisión pendiente</p>");
            }

            sb.append("<p class=\"card-text\">");

            if (e.getDescription().length() < 80) {
                sb.append(e.getDescription());
            } else {
                sb.append(e.getDescription().substring(0, 80)).append("...");
            }

            sb.append("</p><button type=\"button\" class=\"btn btn-warning\" data-toggle=\"modal\" data-target=\"#eventModal");
            sb.append(e.getId());
            sb.append("\">Ver evento</button></div></div>");
        }

        return sb.toString();
    }

    private boolean checkCreateParams(String name, User author, String desc, String date, String price, String address, String shopUrl, String image, String category) {
        return name != null && author != null && desc != null && date != null && price != null && address != null && shopUrl != null && image != null && category != null && !category.equals(Properties.NIL_CATEGORY);
    }

    private boolean checkEditParams(String name, User user, String desc, String date, String price, String address, String shopUrl, String image, String tag, String category) {
        return name != null && user != null && desc != null && date != null && price != null && address != null && shopUrl != null && image != null && tag != null && category != null && !category.equals(Properties.NIL_CATEGORY);
    }

    private boolean checkFilterParams(String keywords, String category, String free, String own) {
        return keywords != null && category != null && free != null && own != null;
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
        return "Event CRUD Servlet";
    }
}
