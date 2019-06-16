package DAO;

import Entities.Category;
import Entities.Meal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class MealDAO {
    private static EntityManagerFactory factory;
    private static EntityManager em;

    private static void init() {
        if (factory==null)
            factory = Persistence.createEntityManagerFactory("DataSource");
        if (em==null)
            em = factory.createEntityManager();
    }

    public static Meal getMealbyId(int id) {
        init();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<Meal> results = new LinkedList<Meal>();
        try {
            TypedQuery<Meal> query =
                    em.createQuery("SELECT c FROM Meal c where c.id=:id", Meal.class).setParameter("id", id);
            results = query.getResultList();
        } catch (Exception e) {
            System.err.println("Error when trying to retrieve data from database: " + e);
        }
        return results.get(0);
    }

    public static Meal getMealbyName(String name) {
        init();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<Meal> results = new LinkedList<Meal>();
        try {
            TypedQuery<Meal> query =
                    em.createQuery("SELECT c FROM Meal c where c.name=:name", Meal.class).setParameter("name", name);
            results = query.getResultList();
        } catch (Exception e) {
            System.err.println("Error when trying to retrieve data from database: " + e);
        }
        return results.get(0);
    }

    public static List<Meal> getMealFromMenu(int menuid) {
        init();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<Meal> results = new LinkedList<Meal>();
        try {
//            TypedQuery<Meal> query =
//                    em.createQuery("select m from Meal m JOIN pos n ON m.meal_id = n.meal_meal_id where n.menus_menu_id=:id", Meal.class).setParameter("id", menuid);
//            results = query.getResultList();
        } catch (Exception e) {
            System.err.println("Error when trying to retrieve data from database: " + e);
        }
        return results;
    }

    public static void updateMeal(Meal meal) {
        init();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        Meal old_meal = getMealbyId(meal.getId());
        try {
            em.getTransaction().begin();
            if (meal.getMenu() != old_meal.getMenu()) old_meal.setMenu(meal.getMenu());
            if (meal.getCategory() != old_meal.getCategory()) old_meal.setCategory(meal.getCategory());
            if (!meal.getName().equals(old_meal.getName())) old_meal.setName(meal.getName());
            if (!meal.getDescription().equals(old_meal.getDescription())) old_meal.setDescription(meal.getDescription());
            if (!meal.getOwner().equals(old_meal.getOwner())) old_meal.setOwner(meal.getOwner());
            if (meal.getWeight() != old_meal.getWeight()) old_meal.setWeight(meal.getWeight());
            if (meal.getPrice() != old_meal.getPrice()) old_meal.setPrice(meal.getPrice());
            em.merge(old_meal);
            em.flush();
            System.out.println("updated meal " + old_meal.getId());
        }
        catch(Exception e) {
            System.err.println("Blad przy dodawaniu meal: " + e);
        }
    }

    public static void addMeal(Meal Meal) {
        init();
        try {
            em.getTransaction().begin();
            em.persist(Meal);
            em.flush();
            System.out.println("menu.getmeals " + Meal.getMenu().iterator().next().getId());
        }
        catch(Exception e) {
            System.err.println("Blad przy dodawaniu posilku: " + e);
        }
    }

    public static List<Meal> getAllMeals() {
        init();
        List<Meal> results = new LinkedList<Meal>();
        try {
            TypedQuery<Meal> query =
                    em.createQuery("SELECT c FROM Meal c", Meal.class);
            results = query.getResultList();
        } catch (Exception e) {
            System.err.println("Error when trying to retrieve data from database: " + e);
        }
        return results;
    }

    public static List<Meal> getAllMealsFromUser(String user) {
        init();
        List<Meal> results = new LinkedList<Meal>();
        try {
            TypedQuery<Meal> query =
                    em.createQuery("SELECT c FROM Meal c where c.owner=:user", Meal.class).setParameter("user", user);
            results = query.getResultList();
        } catch (Exception e) {
            System.err.println("Error when trying to retrieve data from database: " + e);
        }
        return results;
    }

    public static void addMealToCategories(Meal meal, Set<Category> category) {
        init();
        for (int i=0; i<category.size(); i++) {
            addMealToCategory(meal, category.iterator().next());
        }
    }

    public static void addMealToCategory(Meal meal, Category category) {
        init();
        Set<Category> categoriesList = new HashSet<>();
        Set<Meal> mealsList = new HashSet<>();
        try {
            em.getTransaction().begin();
            if (meal.getCategory() == null) {
                categoriesList.add(category);
                mealsList.add(meal);
                meal.setCategory(categoriesList);
                category.setMeal(mealsList);
            }
            else {
                meal.getCategory().add(category);
                category.getMeal().add(meal);
            }
//            em.merge(meal);
            em.merge(category);
            em.flush();
            System.out.println("Zapisano w bazie: " + meal.getName() + category.getName());
        }
        catch(Exception e) {
            System.err.println("Blad przy dodawaniu rekordu: " + e);
        }
    }
}
