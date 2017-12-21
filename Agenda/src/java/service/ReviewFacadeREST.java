package service;

import entity.Review;
import entity.ReviewPK;
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
@Path("review")
public class ReviewFacadeREST extends AbstractFacade<Review> {
    @PersistenceContext(unitName = "AgendaPU")
    private EntityManager em;

    public ReviewFacadeREST() {
        super(Review.class);
    }

    @POST
    @Path("create")
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Review entity) {
        super.create(entity);
    }

    @PUT
    @Path("edit/{user}/{event}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("user") String user, @PathParam("event") Integer event, Review entity) {
        ReviewPK key = new ReviewPK(event, user);
        super.edit(entity);
    }

    @DELETE
    @Path("remove/{user}/{event}")
    public void remove(@PathParam("user") String user, @PathParam("event") Integer event) {
        ReviewPK key = new ReviewPK(event, user);
        super.remove(super.find(key));
    }

    @GET
    @Path("find/{user}/{event}")
    @Produces({MediaType.APPLICATION_XML})
    public Review find(@PathParam("user") String user, @PathParam("event") Integer event) {
        ReviewPK key = new ReviewPK(event, user);
        return super.find(key);
    }

    @GET
    @Path("findall")
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Review> findAll() {
        return super.findAll();
    }

    @GET
    @Path("range/{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Review> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
