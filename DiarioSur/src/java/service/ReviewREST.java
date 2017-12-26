/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Review;
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
 * Jersey REST client generated for REST resource:ReviewFacadeREST [review]<br>
 * USAGE:
 * <pre>
 *        ReviewREST client = new ReviewREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author neko250
 */
public class ReviewREST {
    private static ReviewREST instance;

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/Agenda/rest";

    public static ReviewREST getInstance() {
        if (instance == null)
            instance = new ReviewREST();
        return instance;
    }

    private ReviewREST() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("review");
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

    public Review find(String user, Integer event) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("find/" + user + "/" + event);
        Response r = resource.request(MediaType.APPLICATION_XML).get(Response.class);

        if (r.getStatus() == 200) {
            GenericType<Review> type = new GenericType<Review>() { };
            return r.readEntity(type);
        }

        return null;
    }

    public List<Review> findRange(Integer from, Integer to) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("range/" + from + "/" + to);
        Response r = resource.request(MediaType.APPLICATION_XML).get(Response.class);

        if (r.getStatus() == 200) {
            GenericType<ArrayList<Review>> type = new GenericType<ArrayList<Review>>() { };
            return r.readEntity(type);
        }

        return null;
    }

    public void create(Object requestEntity) throws ClientErrorException {
        webTarget.path("create").request(MediaType.APPLICATION_XML).post(Entity.entity(requestEntity, MediaType.APPLICATION_XML));
    }

    public List<Review> findAll() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("findall");
        Response r = resource.request(MediaType.APPLICATION_XML).get(Response.class);

        if (r.getStatus() == 200) {
            GenericType<ArrayList<Review>> type = new GenericType<ArrayList<Review>>() { };
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
