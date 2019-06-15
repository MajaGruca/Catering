package DAO;

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
import java.util.LinkedList;
import java.util.List;

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
//        CriteriaQuery<Users> query = cb.createQuery(Users.class);
//        Root<Users> hh = query.from(Users.class);
////        query.where(cb.equal(hh.get("id"), id));
//        query.select(hh).where(cb.equal(hh.get("id"), id));
        List<Users> results = new LinkedList<Users>();
        try {
//            TypedQuery<Users> q = em.createQuery(query);
//            Userss = q.getResultList();
            TypedQuery<Users> query =
                    em.createQuery("SELECT c FROM Users c where c.id=:id", Users.class).setParameter("id", id);
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

    private static void init() {
        if (factory==null)
            factory = Persistence.createEntityManagerFactory("DataSource");
        if (em==null)
            em = factory.createEntityManager();
    }
}
