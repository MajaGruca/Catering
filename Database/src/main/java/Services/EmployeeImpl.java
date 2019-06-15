package Services;

import Entities.Meal;
import Entities.Transaction;
import Entities.Users;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Remote(Employee.class)
public class EmployeeImpl implements Employee {

    public void prepareMeal(Meal meal) {

    }

    public void generateBill(Users user) {

    }

    public void orderDelivery(Users user) {

    }

    public List<Transaction> getAllTransactions() {
        return null;
    }
}
