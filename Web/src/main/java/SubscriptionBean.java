import Entities.Category;
import Entities.Meal;
import Entities.Menu;
import Entities.Subscription;
import Services.SessionManager;
import Services.SiteClient;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Time;
import java.util.*;

@ManagedBean
@Named
@RequestScoped
public class SubscriptionBean implements Serializable{

    @EJB(lookup="java:global/Database/SessionManagerImpl")
    private SessionManager sessionManagerBean;

    @EJB(lookup="java:global/Database/SiteClientImpl")
    private SiteClient client;

    private String [] days;
    private String [] chosenMeals;
    private static Subscription subscripton = new Subscription();
    Map<String, Integer> results = new HashMap<>();


    public List<Meal> getAllMeals() {
        return sessionManagerBean.getAllMeals();
    }
    @PostConstruct
    public void init(){
        results.put("poniedziałek", 1);
        results.put("wtorek", 2);
        results.put("środa", 3);
        results.put("czwartek", 4);
        results.put("piątek", 5);
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

    public void logout()
    {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession httpSession = (HttpSession)ec.getSession(false);
        httpSession.invalidate();
        try {
            ec.redirect(ec.getRequestContextPath() + "/login.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getChosenMeals() {
        return chosenMeals;
    }

    public void setChosenMeals(String[] chosenMeals) {
        this.chosenMeals = chosenMeals;
    }

    public Subscription getSubscripton() {
        return subscripton;
    }

    public void setSubscripton(Subscription subscripton) {
        this.subscripton = subscripton;
    }

    public String[] getDays() {
        return days;
    }

    public void setDays(String[] days) {
        this.days = days;
    }

    public List<String> getAllDays() {
        List<String> result = new ArrayList<>();
        result.add("poniedziałek");
        result.add("wtorek");
        result.add("środa");
        result.add("czwartek");
        result.add("piątek");
        return result;
    }

    public Set<String> changeIntToDays(Set<Integer> list) {
        Set<String> result = new HashSet<>();
        for (Integer x : list) {
            result.add(getKey(results, x));
            }
        return result;
        }

    public static <K, V> K getKey(Map<K, V> map, V value) {
        return map.keySet()
                .stream()
                .filter(key -> value.equals(map.get(key)))
                .findFirst().get();
    }

    public List<String> getMealsNames(Set<Meal> list) {
        return Helper.getMealsNames(list);
    }

    public Set<Integer> changeDaysToInt(String [] list) {
        Set<Integer> resultList = new HashSet<>();

        for (String x : list) {
            if (results.containsKey(x)) {
                resultList.add(results.get(x));
            }
        }
        days = null;
        return resultList;
    }

    public void addSubscription() {
        addSubscriptionDetails();
        client.addSubscription(subscripton);
        SubscriptionBean.subscripton = new Subscription();
    }

    public List<Subscription> getAllSubscriptions() {
        return client.getAllSubscriptions();
    }

    public void edit(Subscription sub) {
        subscripton = sub;
        List<String> tmp = new ArrayList<>();
        for (Meal x : subscripton.getMeals()) {
            tmp.add(String.valueOf(x.getId()));
        }
        chosenMeals = tmp.stream().toArray(String[]::new);
        days = changeIntToDays(subscripton.getDays()).stream().toArray(String[]::new);
    }

    public void updateSubscription() {
        addSubscriptionDetails();
        subscripton = new Subscription();
    }

    public void addSubscriptionDetails() {
        subscripton.setDays(changeDaysToInt(days));
        subscripton.setMeals(getMealsSet(chosenMeals));
        client.updateSubscription(subscripton);
        days = null;
    }
    public void remove(Subscription sub) {
        client.cancelSubscribtion(sub);
    }

}
