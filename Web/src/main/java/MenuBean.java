import Entities.Meal;
import Entities.Menu;
import Services.Manager;
import Services.SessionManager;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ManagedBean
@Named
@ApplicationScoped
public class MenuBean implements Serializable{

    @EJB(lookup="java:global/Database/ManagerImpl")
    private Manager manager;

    @EJB(lookup="java:global/Database/SessionManagerImpl")
    private SessionManager sessionManagerBean;

    private static String [] chosenMeals;
    private String [] chosenMenus;
    private static String [] chosenMeals2;
    private String [] MealsToChoose;
    private String dailyMeal;
    private static Boolean active;
    private static Menu menu = new Menu();
    private static boolean edit = false;


    public List<Menu> getAllMenus() {
        return manager.getAllMenus();
    }

    public void addMenu() {
        if(MenuBean.menu.getName() != null) {
            Set<Meal> resultSet = new HashSet<>();
                if (chosenMeals2 != null) {
                    resultSet = getMealSet(chosenMeals2);
                    chosenMeals2 = null;
                    menu.setMeal(resultSet);
                } else {
                    menu.setMeal(resultSet);
            }
            manager.createMenu(menu, resultSet);
        }
    }

    public Menu getActiveMenu(){
        List<Menu> menus = this.getAllMenus();
        for(Menu m : menus)
        {
            if(m.getActive())
                return m;
        }
        return null;
    }

    public List<Meal> getMealsNotInMenu() {
        if (menu.getId() == 0) return new ArrayList<Meal>();
        return manager.getMealsNotInMenu(menu);
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
        return meals;
    }

    public int getMealId(Set<Meal> list, String name) {
        for (Meal x : list){
            if (x.getName().equals(name)) return x.getId();
        }
        return 0;
    }

    public List<String> getMealsNames(Set<Meal> list) {
        if (list.size() != 0) return Helper.getMealsNames(list);
        return new ArrayList<>();
    }

    public String getMealName (int id)
    {
        if(id != 0) return sessionManagerBean.getMealById(id).getName();
        else return "";
    }
    public List<Meal> getMealsFromMenu()
    {
        List<Meal> result = new ArrayList<Meal>();
        if (menu == null)
            return manager.getMealsFromMenu(menu);
        else return result;
    }
    public void edit(Menu menu) {
        MenuBean.menu = menu;
        List<String> tmp = new ArrayList<>();
        for (Meal x : menu.getMeal()) {
            tmp.add(String.valueOf(x.getId()));
        }
        chosenMeals = tmp.stream().toArray(String[]::new);
    }
    public void remove(Menu menu) {
        manager.removeMenu(menu);
    }

    public void updateMenu() {
        addSubscriptionDetails();
    }

    public void addSubscriptionDetails() {
        if (menu.getName() != null && edit == true) {
            Set<Meal> newSet = new HashSet<>();
            newSet = getMealsSet(chosenMeals);
            menu.setMeal(newSet);
            manager.updateMenu(menu);
            menu = new Menu();
            edit = false;
        }
    }
    public List<Meal> getAllMeals() {
        return sessionManagerBean.getAllMealsFromUser(Helper.getCurrUser());
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


    public Set<Meal> getMealsSet(String[] list) {
        Set<Meal> meals = new HashSet<Meal>();
        if (list != null) {
            for (String x : list) {
                meals.add(sessionManagerBean.getMealById(Integer.parseInt(x)));
                System.out.println("meal: " + Integer.parseInt(x));
            }
            chosenMeals = null;
        }
        return meals;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
        if (edit) updateMenu();
    }
}
