package Services;

import DAO.TransactionDAO;
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

    public void addTransaction(Transaction t) {
        TransactionDAO.addTransaction(t);
    }

//    public void addTransaction(Transaction t) {
////        TransactionDAO.addTransaction(t);
////    }


}
