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
        c.setName("categoria1");
        c.setEventCollection(new ArrayList<>());
        Ofyservice.ofy().save().entity(c);
    }
    public static Objectify ofy(){
        return ObjectifyService.ofy();
    }
}