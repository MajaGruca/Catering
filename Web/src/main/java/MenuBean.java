import Entities.Meal;
import Entities.Menu;
import Services.Manager;
import Services.SessionManager;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ManagedBean
@Named
@SessionScoped
public class MenuBean implements Serializable{

    @EJB(lookup="java:global/Database/ManagerImpl")
    private Manager manager;

    @EJB(lookup="java:global/Database/SessionManagerImpl")
    private SessionManager sessionManagerBean;

    private static String [] chosenMeals;
    private String [] chosenMenus;
    private static String [] chosenMeals2;
    private String [] MealsToChoose;
    private static List<String> cm = new ArrayList<String>();
    private String dailyMeal;
    private String chosenMOTD;
    private String newId;
    private static Boolean active;
    private static Menu menu = new Menu();


    public List<Menu> getAllMenus() {
        return manager.getAllMenus();
    }

    public void addMenu() {
        System.out.println("add menu");
        Set<Meal> resultSet = null;
        if (chosenMeals2!=null) {
            System.out.println("add menu 1 ");
        if (chosenMeals2.length != 0) {
            System.out.println("add menu 2 " + chosenMeals2[0]);
            resultSet = getMealSet(chosenMeals2);
            menu.setMeal(resultSet);
            System.out.println(menu.getMeal().iterator().next().getName());
        }

            System.out.println("add menu " + menu.getName());
        }
        System.out.println("Po managerze " + menu.getMeal().iterator().next().getName());
        manager.createMenu(menu, resultSet);
        menu = new Menu();
    }

    public List<Meal> getMealsNotInMenu() {
        if (menu.getId() == 0) return new ArrayList<Meal>();
        return manager.getMealsNotInMenu(menu);
    }

    public void updateMealsMenu() {
        if (menu.getName()!= null) {
            System.out.println("Pierwsze meale " + menu.getMeal());
            menu.setMeal(addTwoSets(menu.getMeal(), getMealSet(chosenMeals)));
            manager.updateMenu(menu);
        }
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public String[] getChosenMenus() {
        return chosenMenus;
    }

    public void setChosenMenus(String[] chosenMenus) {
        this.chosenMenus = chosenMenus;
    }


    public String[] getChosenMeals2() {
        return chosenMeals2;
    }

    public void setChosenMeals2(String[] chosenMeals2) {
        this.chosenMeals2 = chosenMeals2;
    }

    public String getDailyMeal() {
        return dailyMeal;
    }

    public void setDailyMeal(String dailyMeal) {
        this.dailyMeal = dailyMeal;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        MenuBean.menu = menu;
    }

    public void setUpdateMenu() {
        MenuBean.menu = manager.getMenuById(menu.getId());
        MenuBean.active = menu.getActive();
        System.out.println("to menu: ");
    }

    public Set<Meal> getMealSet(String[] list) {
        Set<Meal> meals = new HashSet<Meal>();
        for (String x : list){
            meals.add(sessionManagerBean.getMealById(Integer.parseInt(x)));
            System.out.println("Meal: " +  x);
        }
        chosenMeals2 = null;
        return meals;
    }

    public int getMealId(Set<Meal> list, String name) {
        for (Meal x : list){
            if (x.getName().equals(name)) return x.getId();
        }
        return 0;
    }

    public String[] getMealsToChoose() {
        return MealsToChoose;
    }

    public void setMealsToChoose(String[] mealsToChoose) {
        MealsToChoose = mealsToChoose;
    }

    public static Set<Meal> addTwoSets(Set<Meal> one, Set<Meal> two) {
        Set<Meal> newSet = new HashSet<Meal>(one);
        newSet.addAll(two);
        return newSet;
    }

    public void updateActiveMenu() {
        if(menu.getName()!=null) {
            menu.setActive(active);
            manager.updateMenu(menu);
        }
    }

    public void setMOTD() {
        manager.defineOOTD(menu, menu.getId());
    }

    public void setMOTDPrice() {
        manager.changePriceOOTD(menu, menu.getDaily_meal_price());
    }

    public String getChosenMOTD() {
        return chosenMOTD;
    }

    public void setChosenMOTD(String chosenMOTD) {
        this.chosenMOTD = chosenMOTD;
    }

    public List<Meal> getMealsFromMenu()
    {
        List<Meal> result = new ArrayList<Meal>();
        if (menu == null)
            return manager.getMealsFromMenu(menu);
        else return result;
    }

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public List<Meal> getAllMeals() {
        return sessionManagerBean.getAllMeals();
    }

    public List<String> getCm() {
        return cm;
    }

    public void setCm(List<String> cm) {
        MenuBean.cm = cm;
    }

    public String[] getChosenMeals() {
        return chosenMeals;
    }

    public void setChosenMeals(String[] chosenMeals) {
        this.chosenMeals = chosenMeals;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
