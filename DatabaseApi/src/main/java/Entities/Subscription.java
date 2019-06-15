package Entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

    @ManyToMany(targetEntity = Meal.class, cascade={CascadeType.MERGE,CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinTable(name = "subscription_meals",  joinColumns = {
            @JoinColumn(name = "subscription_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "meal_id",
                    nullable = false, updatable = false) })
    private Set<Meal> meals = new HashSet<Meal>();

    @ElementCollection
    @Column(name="days")
    private Set<Integer> days;

    @Column(name="delivery")
    private Boolean delivery;

    @Column(name="time")
    private Date time;

    public Subscription(Set<Meal> meals, Set<Integer> days, Boolean delivery, Date time) {
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
        if (this.meals != null) this.meals.clear();
        if (meals != null) {
            this.meals = meals;
        }
    }

    public Set<Integer> getDays() {
        return days;
    }

    public void setDays(Set<Integer> days) {
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
