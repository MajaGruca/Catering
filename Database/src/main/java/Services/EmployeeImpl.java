package Services;

import DAO.MealDAO;
import DAO.TransactionDAO;
import DAO.UserDAO;
import Entities.Meal;
import Entities.Transaction;
import Entities.Users;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@Remote(Employee.class)
public class EmployeeImpl implements Employee {

    public void prepareMeal(Transaction transaction) {
        if(transaction.getDelivery())
            transaction.setStatus("ReadyForDelivery");
        else
            transaction.setStatus("ReadyForCollection");
        TransactionDAO.updateTransactionStatus(transaction);
    }

    public void generateBill(Users user) {
        TransactionDAO.getAllUsersTransactions(user);
    }

    public void orderDelivery(Users user) {

    }

    public List<Transaction> getAllTransactions() {
        return TransactionDAO.getAllTransactions();
    }
}
