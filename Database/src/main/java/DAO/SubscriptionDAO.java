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

    public static void addSubscription(Subscription sub) {
        init();
        try {
            em.getTransaction().begin();
            em.persist(sub);
            em.flush();
            System.out.println("Zapisano w bazie: Sub " + sub.getId());
        }
        catch(Exception e) {
            System.err.println("Blad przy dodawaniu rekordu: " + e);
        }

    }

    public static void updateSubscribtion(Subscription sub) {
        init();
        try {
            em.getTransaction().begin();
            Subscription old_sub = em.find(Subscription.class, sub.getId());
            if (old_sub.getMeals() != sub.getMeals() && sub.getMeals()!= null) old_sub.setMeals(sub.getMeals());
            if (old_sub.getDays() != sub.getDays() && sub.getDays()!= null) old_sub.setDays(sub.getDays());
            if (old_sub.getDelivery() != sub.getDelivery() && sub.getDelivery() != null) old_sub.setDelivery(sub.getDelivery());
            em.merge(old_sub);
            em.flush();
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
            Subscription old_sub = em.find(Subscription.class, sub.getId());
            old_sub.getMeals().clear();
            em.remove(old_sub);
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
    
    public static List<Subscription> getAllSubscriptions() {
        init();
        List<Subscription> results = new LinkedList<Subscription>();
        try {
            TypedQuery<Subscription> query =
                    em.createQuery("SELECT DISTINCT c FROM Subscription c JOIN FETCH c.meals JOIN FETCH c.days", Subscription.class);
            results = query.getResultList();
        } catch (Exception e) {
            System.err.println("Error when trying to retrieve data from database: " + e);
        }
        return results;
    }
}
