package DAO;

import Entities.Meal;
import Entities.Menu;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

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
        if (results.size() > 0) return results.get(0);
        return new Menu();
    }

    public static void addMenu(Menu menu, Set<Meal> meal) {
        init();
        System.out.println("Jestem w addmenu: ");
        if (menu.getDaily_meal_price() == null) {
            menu.setDaily_meal_price(0.0);
        }
        Set<Menu> tmp = new HashSet<>();
        try {
            em.getTransaction().begin();
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
            Menu old_menu = em.find(Menu.class, menu.getId());
            old_menu.getMeal().clear();
            em.remove(old_menu);
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
        try {
            em.getTransaction().begin();
            em.persist(meal);
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
            if (menu.getDaily_meal()!= old_menu.getDaily_meal() && menu.getDaily_meal() != 0) old_menu.setDaily_meal(menu.getDaily_meal());
            if (menu.getDaily_meal_price()!= old_menu.getDaily_meal_price() && menu.getDaily_meal_price() != null) old_menu.setDaily_meal_price(menu.getDaily_meal_price());
            if (!menu.getMeal().equals(old_menu.getMeal()) && menu.getMeal() != null) {
              //  old_menu.setMeal(menu.getMeal());
                for (Meal x : old_menu.getMeal()) {
                    if (!menu.getMeal().contains(x)) old_menu.getMeal().remove(x);
                    if (old_menu.getDaily_meal() == x.getId()) old_menu.setDaily_meal(0);
                }
                for (Meal x : menu.getMeal()) {
                    if (!old_menu.getMeal().contains(x)) old_menu.getMeal().add(x);
            } }

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
                    em.createQuery("SELECT DISTINCT c FROM Menu c", Menu.class);
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
            Menu old_menu = em.find(Menu.class, menu.getId());
            old_menu.setDaily_meal(id);
            em.merge(old_menu);
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
            Menu old_menu = em.find(Menu.class, menu.getId());
            old_menu.setDaily_meal_price(price);
            em.merge(old_menu);
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

    public static void archivizeMenu(Menu menu) {
        init();
        //System.out.println("Jestem w addmenu: " + menu.getMeal().iterator().next().getName());
        menu.setActive(false);
        try {
            em.getTransaction().begin();
            em.persist(menu);
            em.flush();
            System.out.println("Zapisano w bazie: Archiwizacja menu " + menu.getId());
        } catch (Exception e) {
            System.err.println("Blad przy archiwizacji menu: " + e);
        }
    }

    public static Menu returnActiveMenu() {
        init();
        List<Menu> results = new LinkedList<>();
        try {
            TypedQuery<Menu> query =
                    em.createQuery("SELECT DISTINCT c FROM Menu c where c.active=true", Menu.class);
            results = query.getResultList();
        } catch (Exception e) {
            System.err.println("Error when trying to retrieve data from database: " + e);
        }
        if (results.size() > 0) return results.get(0);
        else return null;
    }
}
