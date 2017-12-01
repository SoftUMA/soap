package ws;

import beans.CategoryFacade;
import entities.Category;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "CategoryWS")
@Stateless()
public class CategoryWS {

    @EJB
    private CategoryFacade categoryEJB;

    @WebMethod(operationName = "createCategory")
    @Oneway
    public void createCategory(@WebParam(name = "category") Category category) {
        categoryEJB.create(category);
    }

    @WebMethod(operationName = "editCategory")
    @Oneway
    public void editCategory(@WebParam(name = "category") Category category) {
        categoryEJB.edit(category);
    }

    @WebMethod(operationName = "removeCategory")
    @Oneway
    public void removeCategory(@WebParam(name = "category") Category category) {
        categoryEJB.remove(category);
    }

    @WebMethod(operationName = "findCategory")
    public Category findCategory(@WebParam(name = "id") Object id) {
        return categoryEJB.find(id);
    }

    @WebMethod(operationName = "findAllCategories")
    public List<Category> findAllCategories() {
        return categoryEJB.findAll();
    }

    @WebMethod(operationName = "findRangeCategories")
    public List<Category> findRangeCategories(@WebParam(name = "range") int[] range) {
        return categoryEJB.findRange(range);
    }

    @WebMethod(operationName = "countCategories")
    public int countCategories() {
        return categoryEJB.count();
    }

}
