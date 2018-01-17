package service;

import java.util.List;

import entity.Category;
import entity.Event;
import com.googlecode.objectify.*;


public class EventService {

    private static EventService instance;
    public static EventService getInstance() {
        if (instance == null)
            instance = new EventService();
        return instance;
    }
    
    private EventService() {
       super();
    }

	public void create(Event event) {
		// TODO Auto-generated method stub
		Ofyservice.ofy().save().entity(event).now();	

	}

	public void edit(Event event, Long long1) {
		// TODO Auto-generated method stub
		Ofyservice.ofy().save().entity(event).now();	
		
	}

	public void remove(Event event) {
		// TODO Auto-generated method stub
		System.out.println("eliminando...");
		Ofyservice.ofy().delete().entity(event).now();
	}

	public List<Event> findAll() {
		// TODO Auto-generated method stub
		System.out.println(Ofyservice.ofy().load().type(Event.class).list());
		return Ofyservice.ofy().load().type(Event.class).list();
	}

	public Event find(Long id) {
		// TODO Auto-generated method stub
		return Ofyservice.ofy().load().type(Event.class).filter("id", id).list().get(0);
	}
}
