package service;
import java.util.ArrayList;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import entity.Category;
import entity.Event;
import entity.User;
public class Ofyservice {
    static {
        ObjectifyService.register(Event.class);
        ObjectifyService.register(Category.class);        
        ObjectifyService.register(User.class);
        Category c = new Category();  
		c.setName("e-sports");
		c.setEventCollection(new ArrayList<>());
		Ofyservice.ofy().save().entity(c).now();
		c= new Category();
		c.setName("cine");
		c.setEventCollection(new ArrayList<>());
		Ofyservice.ofy().save().entity(c).now();
		c= new Category();
		c.setName("Música");
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
        Ofyservice.ofy().save().entity(c);
    }
    public static Objectify ofy(){
        return ObjectifyService.ofy();
    }
}