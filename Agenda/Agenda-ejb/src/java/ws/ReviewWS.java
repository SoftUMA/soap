package ws;

import beans.ReviewFacade;
import entities.Review;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "ReviewWS")
@Stateless()
public class ReviewWS {

    @EJB
    private ReviewFacade reviewEJB;

    @WebMethod(operationName = "createReview")
    @Oneway
    public void createReview(@WebParam(name = "review") Review review) {
        reviewEJB.create(review);
    }

    @WebMethod(operationName = "editReview")
    @Oneway
    public void editReview(@WebParam(name = "review") Review review) {
        reviewEJB.edit(review);
    }

    @WebMethod(operationName = "removeReview")
    @Oneway
    public void removeReview(@WebParam(name = "review") Review review) {
        reviewEJB.remove(review);
    }

    @WebMethod(operationName = "findReview")
    public Review findReview(@WebParam(name = "id") Object id) {
        return reviewEJB.find(id);
    }

    @WebMethod(operationName = "findAllReviews")
    public List<Review> findAllReviews() {
        return reviewEJB.findAll();
    }

    @WebMethod(operationName = "findRangeReviews")
    public List<Review> findRangeReviews(@WebParam(name = "range") int[] range) {
        return reviewEJB.findRange(range);
    }

    @WebMethod(operationName = "countReviews")
    public int countReviews() {
        return reviewEJB.count();
    }

}
