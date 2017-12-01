package ws;

import beans.EventFacade;
import entities.Event;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "EventWS")
@Stateless()
public class EventWS {

    @EJB
    private EventFacade eventEJB;

    @WebMethod(operationName = "createEvent")
    @Oneway
    public void createEvent(@WebParam(name = "event") Event event) {
        eventEJB.create(event);
    }

    @WebMethod(operationName = "editEvent")
    @Oneway
    public void editEvent(@WebParam(name = "event") Event event) {
        eventEJB.edit(event);
    }

    @WebMethod(operationName = "removeEvent")
    @Oneway
    public void removeEvent(@WebParam(name = "event") Event event) {
        eventEJB.remove(event);
    }

    @WebMethod(operationName = "findEvent")
    public Event findEvent(@WebParam(name = "id") Object id) {
        return eventEJB.find(id);
    }

    @WebMethod(operationName = "findAllEvents")
    public List<Event> findAllEvents() {
        return eventEJB.findAll();
    }

    @WebMethod(operationName = "findRangeEvents")
    public List<Event> findRangeEvents(@WebParam(name = "range") int[] range) {
        return eventEJB.findRange(range);
    }

    @WebMethod(operationName = "countEvents")
    public int countEvents() {
        return eventEJB.count();
    }

}
