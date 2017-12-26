/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.User;
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
 * Jersey REST client generated for REST resource:UserFacadeREST [user]<br>
 * USAGE:
 * <pre>
 *        UserREST client = new UserREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author neko250
 */
public class UserREST {
    private static UserREST instance;

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/Agenda/rest";

    public static UserREST getInstance() {
        if (instance == null)
            instance = new UserREST();
        return instance;
    }

    private UserREST() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("user");
    }

    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(MediaType.TEXT_PLAIN).get(String.class);
    }

    public void edit(Object requestEntity, String email) throws ClientErrorException {
        // webTarget.path("edit/" + email).request(MediaType.APPLICATION_XML).put(Entity.entity(requestEntity, MediaType.APPLICATION_XML));
        remove(email);
        create(requestEntity);
    }

    public User find(String email) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("find/" + email);
        Response r = resource.request(MediaType.APPLICATION_XML).get(Response.class);

        if (r.getStatus() == 200) {
            GenericType<User> type = new GenericType<User>() { };
            return r.readEntity(type);
        }

        return null;
    }

    public List<User> findRange(Integer from, Integer to) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("findrange/" + from + "/" + to);
        Response r = resource.request(MediaType.APPLICATION_XML).get(Response.class);

        if (r.getStatus() == 200) {
            GenericType<ArrayList<User>> type = new GenericType<ArrayList<User>>() { };
            return r.readEntity(type);
        }

        return null;
    }

    public void create(Object requestEntity) throws ClientErrorException {
        webTarget.path("create").request(MediaType.APPLICATION_XML).post(Entity.entity(requestEntity, MediaType.APPLICATION_XML));
    }

    public List<User> findAll() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("findall");
        Response r = resource.request(MediaType.APPLICATION_XML).get(Response.class);

        if (r.getStatus() == 200) {
            GenericType<ArrayList<User>> type = new GenericType<ArrayList<User>>() { };
            return r.readEntity(type);
        }

        return null;
    }

    public void remove(String email) throws ClientErrorException {
        webTarget.path("remove/" + email).request().delete();
    }

    public void close() {
        client.close();
    }

}
