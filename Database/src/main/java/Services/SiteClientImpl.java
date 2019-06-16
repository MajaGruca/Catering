package Services;

import DAO.MenuDAO;
import DAO.SubscriptionDAO;
import DAO.TransactionDAO;
import DAO.UserDAO;
import Entities.*;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.sql.Time;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Stateless
@Remote(SiteClient.class)
public class SiteClientImpl implements SiteClient {

    public void odrerMeal(Meal meal, Integer day, Boolean delivery, Time time) {
        Set<Meal> lmeal = null;
        lmeal.add(meal);
        Set<Integer> days = new HashSet<>();
        days.add(day);
        Subscription sub = new Subscription(lmeal,days,delivery,time);
    }

    public void changeOrder(int id) {
        Subscription sub = SubscriptionDAO.getSubscriptionbyId(id);
    }

    public void cancelOrder(int id) {
        Subscription sub = SubscriptionDAO.getSubscriptionbyId(id);
        SubscriptionDAO.removeSubscription(sub);
    }

    public Menu checkMenu() {
        return MenuDAO.getCurrentMenu();
    }

    public void addSubscription(Subscription subscription) {
        SubscriptionDAO.addSubscription(subscription);
    }

    public void cancelSubscribtion(Subscription subscription) {
        SubscriptionDAO.removeSubscription(subscription);
    }

    public List<Subscription> getAllSubscriptions() {
        return SubscriptionDAO.getAllSubscriptions();
    }

    public void updateSubscription(Subscription sub) {
        SubscriptionDAO.updateSubscribtion(sub);
    }

    public void updateUser(Users user) {
        UserDAO.updateUser(user);
    }

    public void addTransaction(Users user, Transaction transaction) {
        UserDAO.addTransaction(user, transaction);
    }

    public void addSubscription(Users user, Subscription subscription) {
        UserDAO.addSubscription(user, subscription);
    }

    public Subscription getSubscriptionById(int id) { return SubscriptionDAO.getSubscriptionbyId(id); }
}
