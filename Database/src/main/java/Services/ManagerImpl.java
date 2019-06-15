package Services;

import DAO.MenuDAO;
import Entities.Meal;
import Entities.Menu;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Set;

@Stateless
@Remote(Manager.class)
public class ManagerImpl implements Manager {
    public void createMenu(Menu menu, Set<Meal> meal) {
        MenuDAO.addMenu(menu, meal);
    }

    public void addMealToMenu(Meal meal) {
        MenuDAO.addMeal(meal);
    }

    public void removeMealFromMenu(Menu menu, Meal meal) {
        MenuDAO.removeMeal(menu,meal);
    }

    public void removeMenu(Menu menu) {
        MenuDAO.removeMenu(menu);
    }

    public void archivizeMenu(Menu menu) { MenuDAO.archivizeMenu(menu); }

    public void defineOOTD(Menu menu, int id) {
        MenuDAO.updateOOTD(menu,id);
    }

    public void changePriceOOTD(Menu menu, Double price) {
        MenuDAO.updateOOTDprice(menu, price);
    }

    public Menu getMenuById(int id) {
        return MenuDAO.getMenubyId(id);
    }

    public List<Menu> getAllMenus() {
        return MenuDAO.getAllMenus();
    }

    public List<Meal> getMealsNotInMenu(Menu menu) {
        return MenuDAO.getMealsNotInMenu(menu);
    }

    public void updateMenu(Menu menu) {
        MenuDAO.updateMenu(menu);
    }

    public List<Meal> getMealsFromMenu(Menu menu) {
        return MenuDAO.getMealsFromMenu(menu);
    }
}
