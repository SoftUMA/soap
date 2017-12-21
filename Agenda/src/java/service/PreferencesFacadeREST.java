package service;

import entity.Preferences;
import entity.PreferencesPK;
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
@Path("preferences")
public class PreferencesFacadeREST extends AbstractFacade<Preferences> {
    @PersistenceContext(unitName = "AgendaPU")
    private EntityManager em;

    public PreferencesFacadeREST() {
        super(Preferences.class);
    }

    @POST
    @Path("create")
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Preferences entity) {
        super.create(entity);
    }

    @PUT
    @Path("edit/{user}/{event}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("user") String user, @PathParam("event") Integer event, Preferences entity) {
        PreferencesPK key = new PreferencesPK(user, event);
        super.edit(entity);
    }

    @DELETE
    @Path("remove/{user}/{event}")
    public void remove(@PathParam("user") String user, @PathParam("event") Integer event) {
        PreferencesPK key = new PreferencesPK(user, event);
        super.remove(super.find(key));
    }

    @GET
    @Path("find/{user}/{event}")
    @Produces({MediaType.APPLICATION_XML})
    public Preferences find(@PathParam("user") String user, @PathParam("event") Integer event) {
        PreferencesPK key = new PreferencesPK(user, event);
        return super.find(key);
    }

    @GET
    @Path("findall")
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Preferences> findAll() {
        return super.findAll();
    }

    @GET
    @Path("range/{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Preferences> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
