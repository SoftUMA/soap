/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Preferences;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Jersey REST client generated for REST resource:PreferencesFacadeREST [preferences]<br>
 * USAGE:
 * <pre>
 *        PreferencesREST client = new PreferencesREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author neko250
 */
public class PreferencesREST {
    private static PreferencesREST instance;

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/Agenda/rest";

    public static PreferencesREST getInstance() {
        if (instance == null)
            instance = new PreferencesREST();
        return instance;
    }

    private PreferencesREST() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("preferences");
    }

    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(MediaType.TEXT_PLAIN).get(String.class);
    }

    public void edit(Object requestEntity, String user, Integer event) throws ClientErrorException {
        // webTarget.path("edit/" + user + "/" + event).request(MediaType.APPLICATION_XML).put(Entity.entity(requestEntity, MediaType.APPLICATION_XML));
        remove(user, event);
        create(requestEntity);
    }

    public Preferences find(String user, Integer event) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("find/" + user + "/" + event);
        Response r = resource.request(MediaType.APPLICATION_XML).get(Response.class);

        if (r.getStatus() == 200) {
            GenericType<Preferences> type = new GenericType<Preferences>() { };
            return r.readEntity(type);
        }

        return null;
    }

    public List<Preferences> findRange(Integer from, Integer to) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("range/" + from + "/" + to);
        Response r = resource.request(MediaType.APPLICATION_XML).get(Response.class);

        if (r.getStatus() == 200) {
            GenericType<ArrayList<Preferences>> type = new GenericType<ArrayList<Preferences>>() { };
            return r.readEntity(type);
        }

        return null;
    }

    public void create(Object requestEntity) throws ClientErrorException {
        webTarget.path("create").request(MediaType.APPLICATION_XML).post(Entity.entity(requestEntity, MediaType.APPLICATION_XML));
    }

    public List<Preferences> findAll() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("findall");
        Response r = resource.request(MediaType.APPLICATION_XML).get(Response.class);

        if (r.getStatus() == 200) {
            GenericType<ArrayList<Preferences>> type = new GenericType<ArrayList<Preferences>>() { };
            return r.readEntity(type);
        }

        return null;
    }

    public void remove(String user, Integer event) throws ClientErrorException {
        webTarget.path("remove/" + user + "/" + event).request().delete();
    }

    public void close() {
        client.close();
    }

}
