package Services;

import Entities.Category;
import Entities.Meal;

import javax.ejb.Remote;
import java.util.List;
import java.util.Set;

@Remote
public interface SessionManager {
    Meal getMeal(int id);
    void addMeal(Meal meal);
    List<Meal> getAllMeals();
    List<Category> getAllCategories();
    void addCategory(Category category);
    void addMealToMenu(Meal meal);
    void addMealToCategories(Meal meal, Set<Category> category);
    void addMealToCategory(Meal meal, Category category);
    Category getCategoryByName(String name);
    Category getCategoryById(int id);
    Meal getMealById(int id);
    List<Meal> getAllMealsFromMenu(int menuid);
    Meal getMealByName(String name);
}
