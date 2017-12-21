package service;

import entity.User;
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
@Path("user")
public class UserFacadeREST extends AbstractFacade<User> {
    @PersistenceContext(unitName = "AgendaPU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }

    @POST
    @Path("create")
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(User entity) {
        super.create(entity);
    }

    @PUT
    @Path("edit/{email}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("email") String email, User entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("remove/{email}")
    public void remove(@PathParam("email") String email) {
        super.remove(super.find(email));
    }

    @GET
    @Path("find/{email}")
    @Produces({MediaType.APPLICATION_XML})
    public User find(@PathParam("email") String email) {
        return super.find(email);
    }

    @GET
    @Path("findall")
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<User> findAll() {
        return super.findAll();
    }

    @GET
    @Path("range/{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
