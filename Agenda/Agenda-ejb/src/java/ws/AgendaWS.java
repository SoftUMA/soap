package ws;

import beans.CategoryFacade;
import beans.EventFacade;
import beans.PreferencesFacade;
import beans.ReviewFacade;
import beans.UserFacade;
import entities.Category;
import entities.Event;
import entities.Preferences;
import entities.Review;
import entities.User;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "AgendaWS")
@Stateless()
public class AgendaWS {
    @EJB
    private CategoryFacade categoryEJB;
    @EJB
    private UserFacade userEJB;
    @EJB
    private EventFacade eventEJB;
    @EJB
    private PreferencesFacade preferencesEJB;
    @EJB
    private ReviewFacade reviewEJB;

    @WebMethod(operationName = "createCategory")
    @Oneway
    public void createCategory(@WebParam(name = "category") Category category) {
        categoryEJB.create(category);
    }

    @WebMethod(operationName = "editCategory")
    @Oneway
    public void editCategory(@WebParam(name = "category") Category category) {
        categoryEJB.edit(category);
    }

    @WebMethod(operationName = "removeCategory")
    @Oneway
    public void removeCategory(@WebParam(name = "category") Category category) {
        categoryEJB.remove(category);
    }

    @WebMethod(operationName = "findCategory")
    public Category findCategory(@WebParam(name = "id") Object id) {
        return categoryEJB.find(id);
    }

    @WebMethod(operationName = "findAllCategories")
    public List<Category> findAllCategories() {
        return categoryEJB.findAll();
    }

    @WebMethod(operationName = "findRangeCategories")
    public List<Category> findRangeCategories(@WebParam(name = "range") int[] range) {
        return categoryEJB.findRange(range);
    }

    @WebMethod(operationName = "countCategories")
    public int countCategories() {
        return categoryEJB.count();
    }

    @WebMethod(operationName = "createUser")
    @Oneway
    public void createUser(@WebParam(name = "user") User user) {
        userEJB.create(user);
    }

    @WebMethod(operationName = "editUser")
    @Oneway
    public void editUser(@WebParam(name = "user") User user) {
        userEJB.edit(user);
    }

    @WebMethod(operationName = "removeUser")
    @Oneway
    public void removeUser(@WebParam(name = "user") User user) {
        userEJB.remove(user);
    }

    @WebMethod(operationName = "findUser")
    public User findUser(@WebParam(name = "id") Object id) {
        return userEJB.find(id);
    }

    @WebMethod(operationName = "findAllUsers")
    public List<User> findAllUsers() {
        return userEJB.findAll();
    }

    @WebMethod(operationName = "findRangeUsers")
    public List<User> findRangeUsers(@WebParam(name = "range") int[] range) {
        return userEJB.findRange(range);
    }

    @WebMethod(operationName = "countUsers")
    public int countUsers() {
        return userEJB.count();
    }

    @WebMethod(operationName = "createEvent")
    @Oneway
    public void createEvent(@WebParam(name = "event") Event event) {
        eventEJB.create(event);
    }

    @WebMethod(operationName = "editEvent")
    @Oneway
    public void editEvent(@WebParam(name = "event") Event event) {
        eventEJB.edit(event);
    }

    @WebMethod(operationName = "removeEvent")
    @Oneway
    public void removeEvent(@WebParam(name = "event") Event event) {
        eventEJB.remove(event);
    }

    @WebMethod(operationName = "findEvent")
    public Event findEvent(@WebParam(name = "id") Object id) {
        return eventEJB.find(id);
    }

    @WebMethod(operationName = "findAllEvents")
    public List<Event> findAllEvents() {
        return eventEJB.findAll();
    }

    @WebMethod(operationName = "findRangeEvents")
    public List<Event> findRangeEvents(@WebParam(name = "range") int[] range) {
        return eventEJB.findRange(range);
    }

    @WebMethod(operationName = "countEvents")
    public int countEvents() {
        return eventEJB.count();
    }

    @WebMethod(operationName = "createPreferences")
    @Oneway
    public void createPreferences(@WebParam(name = "preferences") Preferences preferences) {
        preferencesEJB.create(preferences);
    }

    @WebMethod(operationName = "editPreferences")
    @Oneway
    public void editPreferences(@WebParam(name = "preferences") Preferences preferences) {
        preferencesEJB.edit(preferences);
    }

    @WebMethod(operationName = "removePreferences")
    @Oneway
    public void removePreferences(@WebParam(name = "preferences") Preferences preferences) {
        preferencesEJB.remove(preferences);
    }

    @WebMethod(operationName = "findPreferences")
    public Preferences findPreferences(@WebParam(name = "id") Object id) {
        return preferencesEJB.find(id);
    }

    @WebMethod(operationName = "findAllPreferences")
    public List<Preferences> findAllPreferences() {
        return preferencesEJB.findAll();
    }

    @WebMethod(operationName = "findRangePreferences")
    public List<Preferences> findRangePreferences(@WebParam(name = "range") int[] range) {
        return preferencesEJB.findRange(range);
    }

    @WebMethod(operationName = "countPreferences")
    public int countPreferences() {
        return preferencesEJB.count();
    }
    
    @WebMethod(operationName = "createReview")
    @Oneway
    public void createReview(@WebParam(name = "review") Review review) {
        reviewEJB.create(review);
    }

    @WebMethod(operationName = "editReview")
    @Oneway
    public void editReview(@WebParam(name = "review") Review review) {
        reviewEJB.edit(review);
    }

    @WebMethod(operationName = "removeReview")
    @Oneway
    public void removeReview(@WebParam(name = "review") Review review) {
        reviewEJB.remove(review);
    }

    @WebMethod(operationName = "findReview")
    public Review findReview(@WebParam(name = "id") Object id) {
        return reviewEJB.find(id);
    }

    @WebMethod(operationName = "findAllReviews")
    public List<Review> findAllReviews() {
        return reviewEJB.findAll();
    }

    @WebMethod(operationName = "findRangeReviews")
    public List<Review> findRangeReviews(@WebParam(name = "range") int[] range) {
        return reviewEJB.findRange(range);
    }

    @WebMethod(operationName = "countReviews")
    public int countReviews() {
        return reviewEJB.count();
    }
}
