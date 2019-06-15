package Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Subscription")
public class Subscription implements Serializable {

    @Column(name = "subscription_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "meal_id")
    private Set<Meal> meals = new HashSet<Meal>();

    @ElementCollection
    @Column(name="days")
    private List<Integer> days;

    @Column(name="delivery")
    private Boolean delivery;

    @Column(name="time")
    private Date time;

    public Subscription(Set<Meal> meals, List<Integer> days, Boolean delivery, Date time) {
        this.meals = meals;
        this.days = days;
        this.delivery = delivery;
        this.time = time;
    }

    public Subscription() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals.clear();
        if (meals != null) {
            this.meals.addAll(meals);
        }
    }

    public List<Integer> getDays() {
        return days;
    }

    public void setDays(List<Integer> days) {
        this.days = days;
    }

    public Boolean getDelivery() {
        return delivery;
    }

    public void setDelivery(Boolean delivery) {
        this.delivery = delivery;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
