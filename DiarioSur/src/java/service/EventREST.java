/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Event;
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
 * Jersey REST client generated for REST resource:EventFacadeREST [event]<br>
 * USAGE:
 * <pre>
 *        EventREST client = new EventREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author neko250
 */
public class EventREST {
    private static EventREST instance;

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/Agenda/rest";

    public static EventREST getInstance() {
        if (instance == null)
            instance = new EventREST();
        return instance;
    }

    private EventREST() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("event");
    }

    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(MediaType.TEXT_PLAIN).get(String.class);
    }

    public void edit(Object requestEntity, Integer id) throws ClientErrorException {
        // webTarget.path("edit/" + id).request(MediaType.APPLICATION_XML).put(Entity.entity(requestEntity, MediaType.APPLICATION_XML));
        remove(id);
        create(requestEntity);
    }

    public Event find(Integer id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("find/" + id);
        Response r = resource.request(MediaType.APPLICATION_XML).get(Response.class);

        System.out.println("URL: " + resource.getUri());
        System.out.println("STATUS: " + r.getStatus());
        if (r.getStatus() == 200) {
            GenericType<Event> type = new GenericType<Event>() { };
            return r.readEntity(type);
        }

        return null;
    }

    public List<Event> findRange(Integer from, Integer to) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("range/" + from + "/" + to);
        Response r = resource.request(MediaType.APPLICATION_XML).get(Response.class);

        if (r.getStatus() == 200) {
            GenericType<ArrayList<Event>> type = new GenericType<ArrayList<Event>>() { };
            return r.readEntity(type);
        }

        return null;
    }

    public void create(Object requestEntity) throws ClientErrorException {
        webTarget.path("create").request(MediaType.APPLICATION_XML).post(Entity.entity(requestEntity, MediaType.APPLICATION_XML));
    }

    public List<Event> findAll() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("findall");
        Response r = resource.request(MediaType.APPLICATION_XML).get(Response.class);

        if (r.getStatus() == 200) {
            GenericType<ArrayList<Event>> type = new GenericType<ArrayList<Event>>() { };
            return r.readEntity(type);
        }

        return null;
    }

    public void remove(Integer id) throws ClientErrorException {
        webTarget.path("remove/" + id).request().delete();
    }

    public void close() {
        client.close();
    }

}
