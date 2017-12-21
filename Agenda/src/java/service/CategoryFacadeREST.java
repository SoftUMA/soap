package service;

import entity.Category;
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
@Path("category")
public class CategoryFacadeREST extends AbstractFacade<Category> {
    @PersistenceContext(unitName = "AgendaPU")
    private EntityManager em;

    public CategoryFacadeREST() {
        super(Category.class);
    }

    @POST
    @Path("create")
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Category entity) {
        super.create(entity);
    }

    @PUT
    @Path("edit/{name}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("name") String name, Category entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("remove/{name}")
    public void remove(@PathParam("name") String name) {
        super.remove(super.find(name));
    }

    @GET
    @Path("find/{name}")
    @Produces({MediaType.APPLICATION_XML})
    public Category find(@PathParam("name") String name) {
        return super.find(name);
    }

    @GET
    @Path("findall")
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Category> findAll() {
        return super.findAll();
    }

    @GET
    @Path("range/{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Category> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
