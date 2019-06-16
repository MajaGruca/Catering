package Services;

import Entities.*;

import javax.ejb.Remote;
import java.sql.Time;
import java.util.List;

@Remote
public interface SiteClient {
    void odrerMeal(Meal meal, Integer day, Boolean delivery, Time time);
    void changeOrder(int id);
    void cancelOrder(int id);
    Menu checkMenu();
    void addSubscription(Subscription subscription);
    void cancelSubscribtion(Subscription subscription);
    List<Subscription> getAllSubscriptions();
    void updateSubscription(Subscription sub);
    void updateUser(Users user);
    void addTransaction(Users user, Transaction transaction);
    void addSubscription(Users user, Subscription subscription);
    Subscription getSubscriptionById(int id);
    Menu getActiveMenu();
}
