package Services;

import DAO.MenuDAO;
import DAO.SubscriptionDAO;
import Entities.Meal;
import Entities.Menu;
import Entities.Subscription;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Stateless
@Remote(SiteClient.class)
public class SiteClientImpl implements SiteClient {


    public void odrerMeal(Meal meal, Integer day, Boolean delivery, Time time) {
        Set<Meal> lmeal = null;
        lmeal.add(meal);
        List<Integer> days = new LinkedList<Integer>();
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

    public void addSubscribtion(Subscription subscription) {
        SubscriptionDAO.addSubscribtion(subscription);
    }

    public void cancelSubscribtion(Subscription subscription) {
        SubscriptionDAO.removeSubscription(subscription);
    }
}
