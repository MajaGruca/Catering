import DAO.MenuDAO;
import Entities.Meal;
import Entities.Menu;
import Entities.Transaction;
import Entities.Users;
import Services.Employee;
import Services.Manager;
import Services.SessionManager;
import Services.SiteClient;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Time;
import java.util.*;

@ManagedBean
@Named
@SessionScoped
public class TransactionBean implements Serializable {

    @EJB(lookup="java:global/Database/EmployeeImpl")
    private Employee employee;

    @EJB(lookup="java:global/Database/SessionManagerImpl")
    private SessionManager sessionManagerBean;

    @EJB(lookup="java:global/Database/SiteClientImpl")
    private SiteClient client;

    @EJB(lookup="java:global/Database/ManagerImpl")
    private Manager manager;

    private static Transaction transaction = new Transaction();
    private String [] chosenMeals;
    private Users user;
    private static List<String> deliveryOptions = new ArrayList<>();
    private static List<String> nonDeliveryOptions = new ArrayList<>();
    private List<String> editDelivery = new ArrayList<>();

    @PostConstruct
    public void init() {
        nonDeliveryOptions.add("Przygotowywane");
        nonDeliveryOptions.add("Wydane");
        deliveryOptions.add("Przygotowywane");
        deliveryOptions.add("Gotowe");
        deliveryOptions.add("Dostarczane");
        deliveryOptions.add("Dostarczone");
    }
    public List<Users> getAllUsers() { return employee.getAllClients();}

    public String[] getChosenMeals() {
        return chosenMeals;
    }

    public Set<Meal> getMealsFromMenu()
    {
        Menu m = MenuDAO.getCurrentMenu();
        return m.getMeal();
    }

    public void setChosenMeals(String[] chosenMeals) {
        this.chosenMeals = chosenMeals;
    }

    public List<Meal> getAllMeals() {
        return sessionManagerBean.getAllMealsFromUser(Helper.getCurrUser());
    }

    public List<Transaction> getAllTransactions() {
       return employee.getAllTransactions();
    }

    public List<Transaction> getAllTransactionsForDelivery() {
        return employee.getTransactionsToDelivery();
    }

    public Set<Meal> getMealsSet(String[] list) {
        Set<Meal> meals = new HashSet<Meal>();
        for (String x : list) {
            meals.add(sessionManagerBean.getMealById(Integer.parseInt(x)));
            System.out.println("meal: " + Integer.parseInt(x));
        }
        chosenMeals = null;
        return meals;
    }

    public void addTransaction() {
        if (chosenMeals != null) {
            Date d = new Date();
            Double price = 0.0;
            transaction.setMeals(getMealsSet(chosenMeals));
            transaction.setDate(d);
            transaction.setTime(new Time(d.getTime()));
            for (Meal x : transaction.getMeals()) {
                price += x.getPrice();
            }
            transaction.setPrice(price);
            Users curr = sessionManagerBean.getUserByName(Helper.getCurrUser());
            client.addTransaction(curr, transaction);
            transaction = new Transaction();
        }
    }

    public Set<Transaction> generateBill(Users user)
    {
        return employee.generateBill(user);
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        TransactionBean.transaction = transaction;
    }

    public Users getUser() {
        return user;
    }

    public String setUser(Users user) {
        this.user = user;
        return "/bill";
    }

    public List<Meal> getAllMealsFromTransaction() {
        return manager.getAllMealsFromTransaction();
    }

    public int countMealsFromTransaction(String name) {
        return manager.getAllMealsFromTransaction(name);
    }

    public void edit(Transaction tr) {
        setTransaction(tr);
        if (tr.getDelivery()) editDelivery = deliveryOptions;
        else editDelivery = nonDeliveryOptions;
    }

    public List<String> getEditDelivery() {
        return editDelivery;
    }

    public void setEditDelivery(List<String> editDelivery) {
        this.editDelivery = editDelivery;
    }

    public void updateTransaction() {
        employee.updateTransactionStatus(transaction, transaction.getStatus());
        transaction = new Transaction();
        editDelivery = new ArrayList<>();
    }

    public List<String> getMealsNames (Set<Meal> meals) {
        return Helper.getMealsNames(meals);
    }

}
