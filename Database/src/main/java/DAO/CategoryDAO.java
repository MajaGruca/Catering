package DAO;
import Entities.Category;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.LinkedList;
import java.util.List;

public class CategoryDAO {
    private static EntityManagerFactory factory;
    private static EntityManager em;

    private static void init() {
        if (factory==null)
            factory = Persistence.createEntityManagerFactory("DataSource");
        if (em==null)
            em = factory.createEntityManager();
    }

    public static Category getCategorybyId(int id) {
        init();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<Category> results = new LinkedList<Category>();
        try {
            TypedQuery<Category> query =
                    em.createQuery("SELECT c FROM Category c where c.id=:id", Category.class).setParameter("id", id);
            results = query.getResultList();
        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu danych: " + e);
        }
        return results.get(0);
    }

    public static Category getCategorybyName(String name) {
        init();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<Category> results = new LinkedList<Category>();
        try {
            TypedQuery<Category> query =
                    em.createQuery("SELECT c FROM Category c where c.name=:name", Category.class).setParameter("name", name);
            results = query.getResultList();
        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu danych: " + e);
        }
        return results.get(0);
    }

    public static void addCategory(Category cat) {
        init();
        try {
            em.getTransaction().begin();
            em.persist(cat);
            em.flush();
            em.getTransaction().commit();
            System.out.println("Zapisano w bazie: " + cat.getName());
        }
        catch(Exception e) {
            System.err.println("Blad przy dodawaniu rekordu: " + e);
        }
    }

    public static void removeCategory(Category cat) {
        init();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("DataSource");
        EntityManager em = factory.createEntityManager();

        try {
            em.getTransaction().begin();
            em.remove(cat);
            em.getTransaction().commit();
            em.flush();
            System.out.println("Usunieto z bazy: " + cat.getName());
        }
        catch(Exception e) {
            System.err.println("Blad przy usuwaniu rekordu: " + e);
        }
    }

    public static List<Category> getAllCategories() {
        init();
        List<Category> result = null;
        try{
            TypedQuery<Category> q = em.createQuery("SELECT c FROM Category c", Category.class);
            result = q.getResultList();
        }catch (Exception e){
            System.err.println("Blad przy pobieraniu danych: " + e);
        }
        return result;
    }
}
