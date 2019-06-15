import Entities.Transaction;
import Services.Employee;

import javax.ejb.EJB;
import java.util.List;

public class TransactionBean {

    @EJB(lookup="java:global/Database/EmployeeImpl")
    private Employee employee;

    List<Transaction> getAllTransactions() {
       return employee.getAllTransactions();
    }
}
