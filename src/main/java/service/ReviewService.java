package service;

import java.util.List;
import entity.Review;

import com.googlecode.objectify.*;


public class ReviewService {

	private static ReviewService instance;
    public static ReviewService getInstance() {
        if (instance == null)
            instance = new ReviewService();
        return instance;
    }
    
    private ReviewService() {
       super();
    }
    
    public void create(Review review) {
		Ofyservice.ofy().save().entity(review).now();	
	}

	public void edit(Review review) {
		Ofyservice.ofy().save().entity(review).now();	
	}

	public void remove(Review review) {
		
		System.out.println("eliminando revision...");
		Ofyservice.ofy().delete().entity(review).now();
	}

	public List<Review> findAll() {
		System.out.println(Ofyservice.ofy().load().type(Review.class).list());
		return Ofyservice.ofy().load().type(Review.class).list();
	}

	public Review find(Long id) {
		return Ofyservice.ofy().load().type(Review.class).filter("id", id).list().get(0);
	
	}


	
}
