package Services;

import Entities.*;

import javax.ejb.Remote;
import java.util.List;
import java.util.Set;

@Remote
public interface Manager {
    void createMenu(Menu menu, Set<Meal> meals);
    void addMealToMenu(Meal meal);
    void removeMealFromMenu(Menu menu, Meal meal);
    void removeMenu(Menu menu);
    void archivizeMenu(Menu menu);
    void defineOOTD(Menu menu, int id);
    void changePriceOOTD(Menu menu, Double price);
    Menu getMenuById(int id);
    List<Menu> getAllMenus();
    List<Meal> getMealsNotInMenu(Menu menu);
    void updateMenu(Menu menu);
    List<Meal> getMealsFromMenu(Menu menu);
    List<Meal> getAllMealsFromTransaction();
    int getAllMealsFromTransaction(String name);
}
