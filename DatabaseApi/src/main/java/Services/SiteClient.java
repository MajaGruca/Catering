package Services;

import Entities.Meal;
import Entities.Menu;
import Entities.Subscription;

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
}
