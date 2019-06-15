import Entities.Meal;
import Entities.Transaction;
import Services.Employee;
import Services.SessionManager;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@ManagedBean
@Named
@SessionScoped
public class TransactionBean implements Serializable {

    @EJB(lookup="java:global/Database/EmployeeImpl")
    private Employee employee;

    @EJB(lookup="java:global/Database/SessionManagerImpl")
    private SessionManager sessionManagerBean;
    private static Transaction transaction = new Transaction();
    private String [] chosenMeals;


    public List<Meal> getAllMeals() {
        return sessionManagerBean.getAllMeals();
    }

    List<Transaction> getAllTransactions() {
       return employee.getAllTransactions();
    }

    public void addTransaction() {
        if (transaction.getId() > 0) {
            Date d = new Date();
            Double price = 0.0;
            transaction.setDate(d);
            transaction.setTime(new Time(d.getTime()));
            for (Meal x : transaction.getMeals()) {
                price += x.getPrice();
            }
            transaction.setPrice(price);
            employee.addTransaction(transaction);
            transaction = new Transaction();
        }
    }

    public static Transaction getTransaction() {
        return transaction;
    }

    public static void setTransaction(Transaction transaction) {
        TransactionBean.transaction = transaction;
    }
}
