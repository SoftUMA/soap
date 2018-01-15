package service;

import java.util.List;

import entity.User;

public class UserService {
    private static UserService instance;

    private static final String BASE_URI = "http://localhost:8080/Agenda/rest";

    public static UserService getInstance() {
        if (instance == null)
            instance = new UserService();
        return instance;
    }

    private UserService() {
    	super();
    }

	public User find(String email) {
		// TODO Auto-generated method stub
		List<User> users = Ofyservice.ofy().load().type(User.class).filter("email",email).list();
		if(!users.isEmpty()) {
			return users.get(0);
		}
			return null;
	}

	public void create(User user) {
		// TODO Auto-generated method stub
		Ofyservice.ofy().save().entity(user);
	}
}
