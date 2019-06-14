import Entities.Category;
import Entities.Meal;
import Entities.Menu;
import Entities.Subscription;
import Services.Manager;
import Services.SessionManager;
import Services.SiteClient;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@Named
@RequestScoped
 public class SessionBean implements Serializable{

    @EJB(lookup="java:global/Database/SessionManagerImpl")
    private SessionManager sessionManagerBean;

    @EJB(lookup="java:global/Database/ManagerImpl")
    private Manager manager;


    private static Meal meal = new Meal();
    private Meal chosenMeal = null;
    private String [] days;
    private String categoryName;
    private String [] chosenCategories;
    private String [] chosenMenus;
    private String [] chosenMeals;
    private static Subscription subscripton = new Subscription();

    public Meal getMeal() {
        return meal;
    }

//    public void addMenu() {
//        System.out.println("add menu");
//        Menu menu = new Menu(null, 0, 0.0, true);
//        manager.createMenu(menu);
//    }

    public void addMeal() {

        System.out.println(FacesContext.getCurrentInstance().getExternalContext().isUserInRole("Manager"));
        System.out.println("add meal 1");
            System.out.println("add meal 2");
            if (meal != null) {
                System.out.println("add meal 3");
                System.out.println(meal.getName() + " " + meal.getCategory());
                if (chosenCategories.length != 0) {
                    meal.setCategory(getCategorySet(chosenCategories));
                }
//                if (chosenMenus.length != 0) meal.setMenus(getMenuSet(chosenMenus));
                sessionManagerBean.addMealToMenu(meal);
                meal = new Meal();
            }
        }

    public void addCategory() {
        System.out.println("add category");
        System.out.println(categoryName);
        Category category = new Category(categoryName, null);
        sessionManagerBean.addCategory(category);
        categoryName = "";
    }

    public List<Menu> getAllMenus() {
        return manager.getAllMenus();
    }
    public List<Category> getAllCategories() {
        System.out.println("get all categories");
        return sessionManagerBean.getAllCategories();
    }

    public Meal getMeal(int id) {
        return sessionManagerBean.getMeal(id);
    }

    public List<Meal> getAllMeals() {
        return sessionManagerBean.getAllMeals();
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String[] getChosenCategories() {
        return chosenCategories;
    }

    public void setChosenCategories(String[] chosenCategories) {
        System.out.println(chosenCategories.toString());
        this.chosenCategories = chosenCategories;
    }

    public String[] getChosenMenus() {
        return chosenMenus;
    }

    public void setChosenMenus(String[] chosenMenus) {
        this.chosenMenus = chosenMenus;
    }

    public Set<Category> getCategorySet(String[] list) {
        Set<Category> categories = new HashSet<Category>();
        for (String x : list){
            categories.add(sessionManagerBean.getCategoryById(Integer.parseInt(x)));
            System.out.println("category: " +  Integer.parseInt(x) + sessionManagerBean.getCategoryById(Integer.parseInt(x)).getId());
        }
        chosenCategories = null;
        return categories;
    }

    public Set<Menu> getMenuSet(String[] list) {
        Set<Menu> menus = new HashSet<Menu>();
        for (String x : list) {
            menus.add(manager.getMenuById(Integer.parseInt(x)));
            System.out.println("menu: " + Integer.parseInt(x));
        }
        chosenMenus = null;
        return menus;
    }

    public void logout()
    {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession httpSession = (HttpSession)ec.getSession(false);
        httpSession.invalidate();
        try {
            ec.redirect(ec.getRequestContextPath() + "/login.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getChosenMeals() {
        return chosenMeals;
    }

    public void setChosenMeals(String[] chosenMeals) {
        this.chosenMeals = chosenMeals;
    }

}
