import Entities.Meal;
import Services.SessionManager;

import javax.ejb.EJB;
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

}
