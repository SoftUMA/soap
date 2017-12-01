package ws;

import beans.UserFacade;
import entities.User;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "UserWS")
@Stateless()
public class UserWS {

    @EJB
    private UserFacade userEJB;

    @WebMethod(operationName = "createUser")
    @Oneway
    public void createUser(@WebParam(name = "user") User user) {
        userEJB.create(user);
    }

    @WebMethod(operationName = "editUser")
    @Oneway
    public void editUser(@WebParam(name = "user") User user) {
        userEJB.edit(user);
    }

    @WebMethod(operationName = "removeUser")
    @Oneway
    public void removeUser(@WebParam(name = "user") User user) {
        userEJB.remove(user);
    }

    @WebMethod(operationName = "findUser")
    public User findUser(@WebParam(name = "id") Object id) {
        return userEJB.find(id);
    }

    @WebMethod(operationName = "findAllUsers")
    public List<User> findAllUsers() {
        return userEJB.findAll();
    }

    @WebMethod(operationName = "findRangeUsers")
    public List<User> findRangeUsers(@WebParam(name = "range") int[] range) {
        return userEJB.findRange(range);
    }

    @WebMethod(operationName = "countUsers")
    public int countUsers() {
        return userEJB.count();
    }

}
