package service;
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
    }
    public static Objectify ofy(){
        return ObjectifyService.ofy();
    }
}