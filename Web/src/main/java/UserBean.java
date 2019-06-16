import Entities.Meal;
import Entities.Transaction;
import Entities.Users;
import Services.Employee;
import Services.Manager;
import Services.SessionManager;
import Services.SiteClient;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ManagedBean
@Named
@SessionScoped
public class UserBean implements Serializable {

    @EJB(lookup="java:global/Database/ManagerImpl")
    private Manager manager;
    private static Users user = new Users();

    public void addUser() {
        manager.addUser(user);
        user = new Users();
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        UserBean.user = user;
    }
}
