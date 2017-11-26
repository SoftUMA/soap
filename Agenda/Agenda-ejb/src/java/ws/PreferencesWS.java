/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import beans.PreferencesFacade;
import entities.Preferences;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author neko250
 */
@WebService(serviceName = "PreferencesWS")
@Stateless()
public class PreferencesWS {

    @EJB
    private PreferencesFacade preferencesEJB;

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
    
}
