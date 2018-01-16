package service;

import java.util.ArrayList;
import java.util.List;

import entity.Category;

public class CategoryService {

	 private static CategoryService instance;

	    private static final String BASE_URI = "http://localhost:8080/Agenda/rest";

	    public static CategoryService getInstance() {
	        if (instance == null)
	            instance = new CategoryService();
	        	if(Ofyservice.ofy().load().type(Category.class).list().size()==0) {
	        		Category c= new Category();
	        		c.setName("e-sports");
	        		c.setEventCollection(new ArrayList<>());
	        		Ofyservice.ofy().save().entity(c).now();
	        		c= new Category();
	        		c.setName("cine");
	        		c.setEventCollection(new ArrayList<>());
	        		Ofyservice.ofy().save().entity(c).now();
	        		c= new Category();
	        		c.setName("M�sica");
	        		c.setEventCollection(new ArrayList<>());
	        		Ofyservice.ofy().save().entity(c).now();
	        		c= new Category();
	        		c.setName("Deportes");
	        		c.setEventCollection(new ArrayList<>());
	        		Ofyservice.ofy().save().entity(c).now();
	        		c= new Category();
	        		c.setName("Cultura");
	        		c.setEventCollection(new ArrayList<>());
	        		Ofyservice.ofy().save().entity(c).now();	        		
	        		System.out.println("creando categorias...");
	        	}
	        return instance;
	    }

	    private CategoryService() {
	    	super();
	    }

	    public List<Category> findAll(){
	    	return Ofyservice.ofy().load().type(Category.class).list();
	    }
		public Category find(String name) {
			// TODO Auto-generated method stub
			return Ofyservice.ofy().load().type(Category.class).filter("name", name).list().get(0);
		}

}
