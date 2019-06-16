package Services;

import DAO.TransactionDAO;
import DAO.UserDAO;
import Entities.Transaction;
import Entities.Users;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Set;

@Stateless
@Remote(Employee.class)
public class EmployeeImpl implements Employee {

    public void prepareMeal(Transaction transaction) {
        transaction.setStatus("ReadyForCollection");
        TransactionDAO.updateTransactionStatus(transaction);
    }

    public Set<Transaction> generateBill(Users user) {
        return TransactionDAO.getAllUsersTransactions(user);
    }

    public void orderDelivery(Transaction transaction) {
        if(transaction.getDelivery())
            transaction.setStatus("ReadyForDelivery");
        TransactionDAO.updateTransactionStatus(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return TransactionDAO.getAllTransactions();
    }

    public List<Users> getAllClients() {
        return UserDAO.getAllClients();
    }

    public void addTransaction(Transaction t) {
        TransactionDAO.addTr(t);
    }

//    public void addTransaction(Transaction t) {
////        TransactionDAO.addTransaction(t);
////    }


}
