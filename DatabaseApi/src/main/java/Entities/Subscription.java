package Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
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

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
        private Set<Meal> meals = new HashSet<Meal>();

//    @ManyToOne
//    private Users user;

    @ElementCollection
    @Column(name="days")
    private List<Integer> days;

    @Column(name="delivery")
    private Boolean delivery;

    @Column(name="time")
    private Time time;

    public Subscription(Set<Meal> meals, List<Integer> days, Boolean delivery, Time time) {
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
        this.meals = meals;
    }
}
