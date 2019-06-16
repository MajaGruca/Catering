package Services;

import Entities.Meal;
import Entities.Transaction;
import Entities.Users;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface Employee {
    void prepareMeal(Transaction transaction);
    List<Transaction> generateBill(Users user);
    void orderDelivery(Transaction t);
    List<Transaction> getAllTransactions();
    void addTransaction(Transaction t);
    List<Users> getAllClients();
    void updateTransactionStatus(Transaction t, String status);
    void updateTransactionStatus(Transaction t);
    List<Transaction> getTransactionsToDelivery();
}
