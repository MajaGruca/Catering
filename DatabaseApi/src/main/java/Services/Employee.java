package Services;

import Entities.Meal;
import Entities.Users;

import javax.ejb.Remote;

@Remote
public interface Employee {
    void prepareMeal(Meal meal);
    void generateBill(Users user);
    void orderDelivery(Users user);
}
