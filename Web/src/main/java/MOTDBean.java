import Entities.Meal;
import Entities.Menu;
import Services.Manager;
import Services.SessionManager;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@Named
@SessionScoped
public class MOTDBean implements Serializable{

    @EJB(lookup="java:global/Database/ManagerImpl")
    private Manager manager;

    @EJB(lookup="java:global/Database/SessionManagerImpl")
    private SessionManager sessionManagerBean;

    private String chosenMOTD;
    private String newId;
    private String mealId;
    private String price;
    private Menu menu = new Menu();


    public List<Menu> getAllMenus() {
        System.out.println("Wchodze chociz do tej metody");
        return manager.getAllMenus();
    }

    public List<Meal> getAllMeals() {
        return sessionManagerBean.getAllMeals();
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setUpdateMenu() {
        this.menu = manager.getMenuById(Integer.parseInt(newId));
        System.out.println("to menu: ");
    }


    public void setMOTD() {
        manager.defineOOTD(menu, Integer.parseInt(mealId));
        menu = new Menu();
    }

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setMOTDPrice() {
        manager.changePriceOOTD(menu, Double.parseDouble(price));
    }
}
