package service;

import entity.Event;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("event")
public class EventFacadeREST extends AbstractFacade<Event> {
    @PersistenceContext(unitName = "AgendaPU")
    private EntityManager em;

    public EventFacadeREST() {
        super(Event.class);
    }

    @POST
    @Path("create")
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Event entity) {
        super.create(entity);
    }

    @PUT
    @Path("edit/{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Event entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("remove/{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("find/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Event find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("findall")
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Event> findAll() {
        return super.findAll();
    }

    @GET
    @Path("range/{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Event> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
