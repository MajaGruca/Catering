package DAO;

import Entities.Subscription;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.LinkedList;
import java.util.List;

public class SubscriptionDAO {

    private static EntityManagerFactory factory;
    private static EntityManager em;

    private static void init() {
        if (factory==null)
            factory = Persistence.createEntityManagerFactory("DataSource");
        if (em==null)
            em = factory.createEntityManager();
    }

    public static void addSubscribtion(Subscription sub) {
        init();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("DataSource");
        EntityManager em = factory.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(sub);
            em.flush();
            em.getTransaction().commit();
            System.out.println("Zapisano w bazie: Sub " + sub.getId());
        }
        catch(Exception e) {
            System.err.println("Blad przy dodawaniu rekordu: " + e);
        }
    }

    public static void removeSubscription(Subscription sub) {
        init();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("DataSource");
        EntityManager em = factory.createEntityManager();

        try {
            em.getTransaction().begin();
            em.remove(sub);
            em.getTransaction().commit();
            System.out.println("Usunieto z bazy: Sub " + sub.getId());
        }
        catch(Exception e) {
            System.err.println("Blad przy usuwaniu rekordu: " + e);
        }
    }

    public static Subscription getSubscriptionbyId(int id) {
        init();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<Subscription> results = new LinkedList<Subscription>();
        try {
            TypedQuery<Subscription> query =
                    em.createQuery("SELECT s FROM Subscription s where s.id=:id", Subscription.class).setParameter("id", id);
            results = query.getResultList();
        } catch (Exception e) {
            System.err.println("Blad przy pobieraniu danych: " + e);
        }
        return results.get(0);
    }
}
