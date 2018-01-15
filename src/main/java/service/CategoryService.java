package service;

import entity.Category;

public class CategoryService {

	 private static CategoryService instance;

	    private static final String BASE_URI = "http://localhost:8080/Agenda/rest";

	    public static CategoryService getInstance() {
	        if (instance == null)
	            instance = new CategoryService();
	        return instance;
	    }

	    private CategoryService() {
	    	super();
	    }

		public Category find(String name) {
			// TODO Auto-generated method stub
			return null;
		}

}
