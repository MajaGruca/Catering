package DAO;

import Entities.Meal;
import Entities.Menu;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@PersistenceContext(type = PersistenceContextType.EXTENDED)
public class MenuDAO {

    private static EntityManagerFactory factory;
    private static EntityManager em;

    private static void init() {
//        if (factory==null)
            factory = Persistence.createEntityManagerFactory("DataSource");
//        if (em==null)
            em = factory.createEntityManager();
    }

    public static Menu getCurrentMenu() {
        init();
        List<Menu> results = new LinkedList<Menu>();
        try {
            TypedQuery<Menu> query =
                    em.createQuery("SELECT m FROM Menu m where m.active=true", Menu.class);
            results = query.getResultList();
        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu danych: " + e);
        }
        return results.get(0);
    }

    public static Menu getMenubyId(int id) {
        init();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<Menu> results = new LinkedList<Menu>();
        try {
            TypedQuery<Menu> query =
                    em.createQuery("SELECT m FROM Menu m where m.id=:id", Menu.class).setParameter("id", id);
            results = query.getResultList();
        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu danych: " + e);
        }
        return results.get(0);
    }

    public static void addMenu(Menu menu, Set<Meal> meal) {
        init();
        System.out.println("Jestem w addmenu: " + menu.getMeal().iterator().next().getName());
        menu.setMeal(meal);
        Set<Menu> tmp = new HashSet<>();
        try {
            em.getTransaction().begin();
//            for (Meal x : meal) {
//                if (x.getMenu()!= null){
//                    tmp = x.getMenu();
//                    tmp.add(menu);
//                    x.setMenu(tmp);
//                    tmp = null;
//                }
//                else {
//                    tmp.add(menu);
//                    x.setMenu(tmp);
//                    tmp = null;
//                }
//                em.merge(x);
//            }
            em.persist(menu);
            em.flush();
            System.out.println("Zapisano w bazie: Menu " + menu.getId() + " " + menu.getMeal());
        }
        catch(Exception e) {
            System.err.println("Blad przy dodawaniu rekordu: " + e);
        }
    }

    public static void removeMenu(Menu menu) {
        init();
        try {
            em.getTransaction().begin();
            em.remove(menu);
            em.getTransaction().commit();
            em.flush();
            System.out.println("Usunieto z bazy: Menu " + menu.getId());
        }
        catch(Exception e) {
            System.err.println("Blad przy usuwaniu rekordu: " + e);
        }
    }

    public static Set<Meal> getAllMeals(Menu menu) {
        init();
        Set<Meal> result = menu.getMeal();
        return result;
    }

    public static void addMeal(Meal meal) {
        init();
//        Set<Meal> result = new HashSet<>();
//        Set<Menu> result2 = new HashSet<>();
//        if (menu.getMeals() == null) {
//            result.add(meal);
//            result2.add(menu);
//
//        } else {
//            result = menu.getMeals();
//            result2 = meal.getMenus();
//            result.add(meal);
//            result2.add(menu);
 //       }
        try {
            em.getTransaction().begin();
//            menu.setMeals(result);
//            meal.setMenus(result2);
            em.persist(meal);
//            em.merge(menu);
            em.flush();
            System.out.println("menu.getmeals " + meal.getId());
        }
        catch(Exception e) {
            System.err.println("Blad przy dodawaniu posilku: " + e);
        }
    }

    public static void updateMenu(Menu menu) {
        init();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        System.out.println("Id: " + menu.getId());
        try {
            em.getTransaction().begin();
            Menu old_menu = em.find(Menu.class, menu.getId());
            if (!menu.getName().equals(old_menu.getName())) old_menu.setName(menu.getName());
            if (!menu.getActive().equals(old_menu.getActive())) old_menu.setActive(menu.getActive());
            if (!menu.getMeal().equals(old_menu.getMeal())) old_menu.setMeal(menu.getMeal());
            em.merge(old_menu);
            em.flush();
            System.out.println("updated Menu " + " " + menu.getId() + " " + old_menu.getId());
        }
        catch(Exception e) {
            System.err.println("Blad przy dodawaniu Menu: " + e);
        }
    }

    public static List<Menu> getAllMenus() {
        init();
        List<Menu> results = new LinkedList<>();
        try {
            TypedQuery<Menu> query =
                    em.createQuery("SELECT c FROM Menu c", Menu.class);
            results = query.getResultList();
        } catch (Exception e) {
            System.err.println("Error when trying to retrieve data from database: " + e);
        }
        return results;
    }
    
    public static void removeMeal(Menu menu, Meal meal) {
        init();
        Set<Meal> result = menu.getMeal();
        result.remove(meal);
        try {
            em.getTransaction().begin();
            menu.setMeal(result);
            em.getTransaction().commit();
            em.flush();
        }
        catch(Exception e) {
            System.err.println("Blad przy usuwaniu posilku: " + e);
        }
    }

    public static void updateOOTD(Menu menu, int id) {
        init();
        try {
            em.getTransaction().begin();
            menu.setDaily_meal(id);
            em.merge(menu);
            em.getTransaction().commit();
            em.flush();
        }
        catch(Exception e) {
            System.err.println("Blad przy dodawaniu oferty dnia: " + e);
        }
    }

    public static void updateOOTDprice(Menu menu, Double price) {
        init();
        try {
            em.getTransaction().begin();
            menu.setDaily_meal_price(price);
            em.merge(menu);
            em.getTransaction().commit();
            em.flush();
        }
        catch(Exception e) {
            System.err.println("Blad przy zmianie ceny oferty dnia: " + e);
        }
    }

    public static List<Meal> getMealsNotInMenu (Menu menu) {
        List<Meal> results = new LinkedList<>();
        try {
            TypedQuery<Meal> query =
                    em.createQuery("SELECT p FROM Meal p WHERE p NOT IN (SELECT c FROM Menu p JOIN p.meal c WHERE p.id = :id)", Meal.class).setParameter("id", menu.getId());
            results = query.getResultList();
        } catch (Exception e) {
            System.err.println("Error when trying to retrieve data from database: " + e);
        }
        return results;
    }

    public static List<Meal> getMealsFromMenu (Menu menu) {
        List<Meal> results = new LinkedList<>();
        try {
//            TypedQuery<Meal> query =
//                    em.createQuery("SELECT p FROM Meal p JOIN p.menus c WHERE c.id = :id", Meal.class).setParameter("id", menu.getId());
//            results = query.getResultList();
        } catch (Exception e) {
            System.err.println("Error when trying to retrieve data from database: " + e);
        }
        return results;
    }

    public static Set<Meal> addTwoSets(Set<Meal> one, Set<Meal> two) {
        Set<Meal> newSet = new HashSet<Meal>(one);
        newSet.addAll(two);
        return newSet;
    }

}
