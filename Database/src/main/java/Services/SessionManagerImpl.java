package Services;

import DAO.CategoryDAO;
import DAO.MealDAO;
import DAO.MenuDAO;
import DAO.UserDAO;
import Entities.Category;
import Entities.Meal;
import Entities.Menu;
import Entities.Users;

import javax.ejb.Stateless;
import javax.xml.registry.infomodel.User;
import java.util.List;
import java.util.Set;

@Stateless
public class SessionManagerImpl implements SessionManager {

    public Meal getMeal(int id) {
        return MealDAO.getMealbyId(id);
    }

    public void addMeal(Meal meal) {
        MealDAO.addMeal(meal);
    }

    public List<Meal> getAllMeals() {
        return MealDAO.getAllMeals();
    }

    public List<Category> getAllCategories() {
        return CategoryDAO.getAllCategories();
    }

    public void addCategory(Category category) {
        CategoryDAO.addCategory(category);
    }

    public void addMealToMenu(Meal meal) {
        MenuDAO.addMeal(meal);
    }

    public void addMealToCategories(Meal meal, Set<Category> category) {
        MealDAO.addMealToCategories(meal, category);
    }

    public void addMealToCategory(Meal meal, Category category) {
        MealDAO.addMealToCategory(meal, category);
    }

    public Category getCategoryByName(String name) {
        return CategoryDAO.getCategorybyName(name);
    }

    public Category getCategoryById(int id) {
        return CategoryDAO.getCategorybyId(id);
    }

    public Meal getMealById(int id) {
        return MealDAO.getMealbyId(id);
    }

    public List<Meal> getAllMealsFromMenu(int menuid) {
        return null;
    }

    public Meal getMealByName(String name) {
        return MealDAO.getMealbyName(name);
    }

    public Menu getMenuByName(String name) {
        return null;
    }

    public Users getUserByName(String name) {
        return UserDAO.getUsersbyName(name);
    }

    public List<Meal> getAllMealsFromUser(String user) {
        return MealDAO.getAllMealsFromUser(user);
    }
}
