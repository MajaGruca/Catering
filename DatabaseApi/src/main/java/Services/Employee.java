package Services;

import Entities.Meal;
import Entities.Transaction;
import Entities.Users;

import javax.ejb.Remote;
import java.util.List;
import java.util.Set;

@Remote
public interface Employee {
    void prepareMeal(Transaction transaction);
    Set<Transaction> generateBill(Users user);
    void orderDelivery(Transaction t);
    List<Transaction> getAllTransactions();
    void addTransaction(Transaction t);
    List<Users> getAllClients();
}
