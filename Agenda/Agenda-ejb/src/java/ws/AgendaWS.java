/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import bean.CategoryFacade;
import bean.EventFacade;
import bean.PreferencesFacade;
import bean.UserFacade;
import entity.Category;
import entity.Event;
import entity.User;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.jws.Oneway;

/**
 *
 * @author USUARIO
 */
@WebService(serviceName = "AgendaWS")
@Stateless()
public class AgendaWS {

    /**
     * This is a sample web service operation
     */
      
   @EJB
    private EventFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "createevent")
    @Oneway
    public void create(@WebParam(name = "entity") Event entity) {
        ejbRef.create(entity);
    }

    @WebMethod(operationName = "editevent")
    @Oneway
    public void editevent(@WebParam(name = "entity") Event entity) {
        ejbRef.edit(entity);
    }

    @WebMethod(operationName = "removeevent")
    @Oneway
    public void removeevent(@WebParam(name = "entity") Event entity) {
        ejbRef.remove(entity);
    }

    @WebMethod(operationName = "findevent")
    public Event findevent(@WebParam(name = "id") Object id) {
        return ejbRef.find(id);
    }

    @WebMethod(operationName = "findAllevent")
    public List<Event> findAllevent() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findRangeevent")
    public List<Event> findRangeevent(@WebParam(name = "range") int[] range) {
        return ejbRef.findRange(range);
    }

    @WebMethod(operationName = "countevent")
    public int countevent() {
        return ejbRef.count();
    }
    
    
    @EJB
    private UserFacade UserEjRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "createusers")
    @Oneway
    public void createuser(@WebParam(name = "entity") User entity) {
        UserEjRef.create(entity);
    }

    @WebMethod(operationName = "editusers")
    @Oneway
    public void edituser(@WebParam(name = "entity") User entity) {
        UserEjRef.edit(entity);
    }

    @WebMethod(operationName = "removeusers")
    @Oneway
    public void removeuser(@WebParam(name = "entity") User entity) {
        UserEjRef.remove(entity);
    }

    @WebMethod(operationName = "findusers")
    public User findUseruser(@WebParam(name = "id") Object id) {
        return UserEjRef.find(id);
    }

    @WebMethod(operationName = "findAllusers")
    public List<User> findAllUser() {
        return UserEjRef.findAll();
    }

    @WebMethod(operationName = "findRangeusers")
    public List<User> findRangeUsers(@WebParam(name = "range") int[] range) {
        return UserEjRef.findRange(range);
    }

    @WebMethod(operationName = "countusers")
    public int countUsers() {
        return UserEjRef.count();
    }
    @EJB
    private PreferencesFacade PreferencesejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "createpreferences")
    @Oneway
    public void createpreferences(@WebParam(name = "entity") entity.Preferences entity) {
        PreferencesejbRef.create(entity);
    }

    @WebMethod(operationName = "editpreferences")
    @Oneway
    public void editpreferences(@WebParam(name = "entity") entity.Preferences entity) {
        PreferencesejbRef.edit(entity);
    }

    @WebMethod(operationName = "removepreferences")
    @Oneway
    public void removepreferences(@WebParam(name = "entity") entity.Preferences entity) {
        PreferencesejbRef.remove(entity);
    }

    @WebMethod(operationName = "findpreferences")
    public entity.Preferences findPreference(@WebParam(name = "id") Object id) {
        return PreferencesejbRef.find(id);
    }

    @WebMethod(operationName = "findAllpreferences")
    public List<entity.Preferences> findAllPreferences() {
        return PreferencesejbRef.findAll();
    }

    @WebMethod(operationName = "findRangepreferences")
    public List<entity.Preferences> findRangePreferences(@WebParam(name = "range") int[] range) {
        return PreferencesejbRef.findRange(range);
    }

    @WebMethod(operationName = "countpreferences")
    public int countPreferences() {
        return ejbRef.count();
    }
    @EJB
    private CategoryFacade CategoryejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "createcategory")
    @Oneway
    public void createcategory(@WebParam(name = "entity") Category entity) {
        CategoryejbRef.create(entity);
    }

    @WebMethod(operationName = "editcategory")
    @Oneway
    public void editcategory(@WebParam(name = "entity") Category entity) {
        CategoryejbRef.edit(entity);
    }

    @WebMethod(operationName = "removecategory")
    @Oneway
    public void removecategory(@WebParam(name = "entity") Category entity) {
        CategoryejbRef.remove(entity);
    }

    @WebMethod(operationName = "findcategory")
    public Category findCategory(@WebParam(name = "id") Object id) {
        return CategoryejbRef.find(id);
    }

    @WebMethod(operationName = "findAllcategory")
    public List<Category> findAllCategory() {
        return CategoryejbRef.findAll();
    }

    @WebMethod(operationName = "findRangecategory")
    public List<Category> findRangeCategory(@WebParam(name = "range") int[] range) {
        return CategoryejbRef.findRange(range);
    }

    @WebMethod(operationName = "countcategory")
    public int countCategory() {
        return CategoryejbRef.count();
    }
      @WebMethod(operationName = "eventByuser")
    public Collection<Event> eventByuser(User u) {
        return u.getEventCollection();
    }
    
}
