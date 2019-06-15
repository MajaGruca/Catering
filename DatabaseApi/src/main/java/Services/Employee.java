package Services;

import Entities.Meal;
import Entities.Transaction;
import Entities.Users;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface Employee {
    void prepareMeal(Transaction transaction);
    void generateBill(Users user);
    void orderDelivery(Users user);
    List<Transaction> getAllTransactions();
}
