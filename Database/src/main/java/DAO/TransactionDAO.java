package DAO;

import Entities.Meal;
import Entities.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.LinkedList;
import java.util.List;

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
}
