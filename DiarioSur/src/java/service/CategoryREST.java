/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Category;
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
 * Jersey REST client generated for REST resource:CategoryFacadeREST [category]<br>
 * USAGE:
 * <pre>
 *        CategoryREST client = new CategoryREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author neko250
 */
public class CategoryREST {
    private static CategoryREST instance;

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/Agenda/rest";

    public static CategoryREST getInstance() {
        if (instance == null)
            instance = new CategoryREST();
        return instance;
    }

    private CategoryREST() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("category");
    }

    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(MediaType.TEXT_PLAIN).get(String.class);
    }

    public void edit(Object requestEntity, String name) throws ClientErrorException {
        // webTarget.path("edit/" + name).request(MediaType.APPLICATION_XML).put(Entity.entity(requestEntity, MediaType.APPLICATION_XML));
        remove(name);
        create(requestEntity);
    }

    public Category find(String name) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("find/" + name);
        Response r = resource.request(MediaType.APPLICATION_XML).get(Response.class);

        if (r.getStatus() == 200) {
            GenericType<Category> type = new GenericType<Category>() { };
            return r.readEntity(type);
        }

        return null;
    }

    public List<Category> findRange(Integer from, Integer to) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("range/" + from + "/" + to);
        Response r = resource.request(MediaType.APPLICATION_XML).get(Response.class);

        if (r.getStatus() == 200) {
            GenericType<ArrayList<Category>> type = new GenericType<ArrayList<Category>>() { };
            return r.readEntity(type);
        }

        return null;
    }

    public void create(Object requestEntity) throws ClientErrorException {
        webTarget.path("create").request(MediaType.APPLICATION_XML).post(Entity.entity(requestEntity, MediaType.APPLICATION_XML));
    }

    public List<Category> findAll() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("findall");
        Response r = resource.request(MediaType.APPLICATION_XML).get(Response.class);

        if (r.getStatus() == 200) {
            GenericType<ArrayList<Category>> type = new GenericType<ArrayList<Category>>() { };
            return r.readEntity(type);
        }

        return null;
    }

    public void remove(String name) throws ClientErrorException {
        webTarget.path("remove/" + name).request().delete();
    }

    public void close() {
        client.close();
    }

}
