package DAO;

import Entities.Meal;
import Entities.Transaction;
import Entities.Users;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TransactionDAO {

    private static EntityManagerFactory factory;
    private static EntityManager em;

    private static void init() {
        if (factory == null)
            factory = Persistence.createEntityManagerFactory("DataSource");
        if (em == null)
            em = factory.createEntityManager();
    }

    public static List<Transaction> getAllTransactions() {
        init();
        List<Transaction> results = new LinkedList<Transaction>();
        try {
            TypedQuery<Transaction> query =
                    em.createQuery("SELECT c FROM Transaction c", Transaction.class);
            results = query.getResultList();
        } catch (Exception e) {
            System.err.println("Error when trying to retrieve data from database: " + e);
        }
        return results;
    }

    public static Set<Transaction> getAllUsersTransactions(Users user) {
        init();

        List<Users> results = new LinkedList<Users>();
        Set<Transaction> trans = new HashSet<Transaction>();
        try {
            TypedQuery<Users> query =
                    em.createQuery("SELECT u FROM Users u WHERE u.id=:id", Users.class).setParameter("id", user.getId());
            results = query.getResultList();
            user = results.get(0);
            trans = user.getTransactions();

        } catch (Exception e) {
            System.err.println("Error when trying to retrieve data from database: " + e);
        }
        return trans;
    }

    public static void addTr(Transaction t) {
        init();
        try {
            em.getTransaction().begin();
            em.persist(t);
            em.flush();
            System.out.println("menu.gettransactions " +t.getId());
        }
        catch(Exception e) {
            System.err.println("Blad przy dodawaniu transakcji: " + e);
        }
    }

    public static void updateTransactionStatus(Transaction t)
    {
        init();
        try {
            em.getTransaction().begin();
            Transaction old_t = em.find(Transaction.class, t.getId());
            if (old_t.getStatus() != t.getStatus() && t.getStatus()!= null) old_t.setStatus(t.getStatus());
            em.merge(old_t);
            em.flush();
            System.out.println("Zapisano w bazie: Trans " + t.getId());
        }
        catch(Exception e) {
            System.err.println("Blad przy dodawaniu rekordu: " + e);
        }
    }

    public static List<Meal> getAllOfMeals() {
        init();
        List<Meal> results = new LinkedList<Meal>();
        try {
            TypedQuery<Meal> query =
                    em.createQuery("SELECT DISTINCT d FROM Transaction c JOIN c.meals d", Meal.class);
            results = query.getResultList();
        } catch (Exception e) {
            System.err.println("Error when trying to retrieve data from database: " + e);
        }
        return results;
    }

    public static int getAllOfMeals(String name) {
        init();
        Long results = Long.valueOf(0);
        try {
            TypedQuery<Long> query =
                    em.createQuery("SELECT count(d) FROM Transaction c JOIN c.meals d where d.name=:name", Long.class).setParameter("name", name);
            results = query.getSingleResult();
        } catch (Exception e) {
            System.err.println("Error when trying to retrieve data from database: " + e);
        }
        return Math.toIntExact(results);
    }
}
