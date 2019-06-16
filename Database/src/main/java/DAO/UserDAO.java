package DAO;

import Entities.Subscription;
import Entities.Transaction;
import Entities.Users;
import Exceptions.InvalidLoginCredentialsException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class UserDAO {
    private static EntityManagerFactory factory;
    private static EntityManager em;

    public static Users getUsers(String login, String password) throws InvalidLoginCredentialsException {
        init();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Users> query = cb.createQuery(Users.class);
        Root<Users> hh = query.from(Users.class);
        List<Predicate> predicates = new LinkedList<Predicate>();

        predicates.add(cb.equal(hh.get("name"), login));
        predicates.add(cb.equal(hh.get("password"), password));

        query.where(predicates.toArray(new Predicate[] {}));

        List<Users> Userss = new LinkedList<Users>();
        try {
            TypedQuery<Users> q = em.createQuery(query);
            Userss = q.getResultList();
        } catch (Exception e) {
            System.err.println("Error when trying to retrieve data from database: " + e);
        }

        if (Userss == null || Userss.size() != 1)
            throw new InvalidLoginCredentialsException();

        return Userss.get(0);
    }

    public static Users getUsersbyId(int id) {
        init();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<Users> results = new LinkedList<Users>();
        try {
            TypedQuery<Users> query =
                    em.createQuery("SELECT c FROM Users c where c.id=:id", Users.class).setParameter("id", id);
            results = query.getResultList();
        } catch (Exception e) {
            System.err.println("Error when trying to retrieve data from database: " + e);
        }
        return results.get(0);
    }

    public static Users getUsersbyName(String name) {
        init();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<Users> results = new LinkedList<Users>();
        try {
            TypedQuery<Users> query =
                    em.createQuery("SELECT c FROM Users c where c.name=:name", Users.class).setParameter("name", name);
            results = query.getResultList();
        } catch (Exception e) {
            System.err.println("Error when trying to retrieve data from database: " + e);
        }
        return results.get(0);
    }

    public static List<Users> getAllClients() {
        init();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<Users> results = new LinkedList<Users>();
        try {
            TypedQuery<Users> query =
                    em.createQuery("SELECT c FROM Users c where c.role LIKE :role", Users.class).setParameter("role", "Client");
            results = query.getResultList();
        } catch (Exception e) {
            System.err.println("Error when trying to retrieve data from database: " + e);
        }
        return results;
    }

    public static void updateUser(Users user) {
        init();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        Users old_user = getUsersbyId(user.getId());
        try {
            em.getTransaction().begin();
            if (user.getSubscriptions() != old_user.getSubscriptions()) old_user.setSubscriptions(user.getSubscriptions());
            if (user.getTransactions() != old_user.getTransactions()) old_user.setTransactions(user.getTransactions());
            em.merge(old_user);
            em.flush();
            System.out.println("updated user " + old_user.getId());
        }
        catch(Exception e) {
            System.err.println("Blad przy dodawaniu user: " + e);
        }
    }

    public static void addUser(Users user) {
        init();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.flush();
            System.out.println("added user " + user.getId());
        }
        catch(Exception e) {
            System.err.println("Blad przy dodawaniu user: " + e);
        }
    }
    
    public static void addSubscription(Users user, Subscription subscription) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        Users old_user = getUsersbyId(user.getId());
        Set<Subscription> tmp = new HashSet<>();
        try {
            em.getTransaction().begin();
            em.persist(subscription);
            if (old_user.getSubscriptions().size() == 0) {
                tmp.add(subscription);
                old_user.getSubscriptions().clear();
                old_user.getSubscriptions().addAll(tmp);
            } else {
                tmp.addAll(old_user.getSubscriptions());
                tmp.add(subscription);
                old_user.getSubscriptions().clear();
                old_user.getSubscriptions().addAll(tmp);
            }
            em.merge(old_user);
            em.flush();
            System.out.println("updated user " + old_user.getId());
        }
        catch(Exception e) {
            System.err.println("Blad przy dodawaniu user: " + e);
        }
    }

    public static void addTransaction(Users user, Transaction transaction) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        Users old_user = getUsersbyId(user.getId());
        Set<Transaction> tmp = new HashSet<>();
        try {
            em.getTransaction().begin();
            em.persist(transaction);
            if (old_user.getTransactions().size() == 0) {
                tmp.add(transaction);
                old_user.getTransactions().clear();
                old_user.getTransactions().addAll(tmp);
            } else {
                tmp.addAll(old_user.getTransactions());
                tmp.add(transaction);
                old_user.getTransactions().clear();
                old_user.getTransactions().addAll(tmp);
            }
            em.merge(old_user);
            em.flush();
            System.out.println("updated user " + old_user.getId());
        }
        catch(Exception e) {
            System.err.println("Blad przy dodawaniu user: " + e);
        }
    }
    
    private static void init() {
        if (factory==null)
            factory = Persistence.createEntityManagerFactory("DataSource");
        if (em==null)
            em = factory.createEntityManager();
    }
}
