import Entities.Meal;
import Services.SessionManager;

import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Helper {

    public static List<String> getMealsNames(Set<Meal> list) {
        List<String> result = new ArrayList<>();
        for (Meal x : list) {
            result.add(x.getName());
        }
        return result;
    }

    public static Set<Meal> addTwoSets(Set<Meal> one, Set<Meal> two) {
        Set<Meal> newSet = new HashSet<Meal>(one);
        newSet.addAll(two);
        return newSet;
    }


    public static String getCurrUser()
    {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        return ec.getRemoteUser();
    }

    public static Boolean userRoleManager()
    {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        return (ec.isUserInRole("Manager") || ec.isUserInRole("Admin"));
    }


    public static Boolean userRoleEmployee1()
    {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        return (ec.isUserInRole("Employee1") || ec.isUserInRole("Admin"));
    }

    public static Boolean userRoleEmployee2()
    {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        return (ec.isUserInRole("Employee2") || ec.isUserInRole("Admin"));
    }

    public static Boolean userRoleClient()
    {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        return (ec.isUserInRole("Client") || ec.isUserInRole("Admin"));
    }

    public static Boolean userRoleAdmin()
    {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        return ec.isUserInRole("Admin");
    }
}
