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
		
	}

	public void remove(Long long1) {
		// TODO Auto-generated method stub
		
	}

	public List<Event> findAll() {
		// TODO Auto-generated method stub
		return Ofyservice.ofy().load().type(Event.class).list();
	}

	public Event find(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
}
