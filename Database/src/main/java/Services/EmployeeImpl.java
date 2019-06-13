package Services;

import Entities.Meal;
import Entities.Users;

import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless
@Remote(Employee.class)
public class EmployeeImpl implements Employee {

    public void prepareMeal(Meal meal) {

    }

    public void generateBill(Users user) {

    }

    public void orderDelivery(Users user) {

    }
}
