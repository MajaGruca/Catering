package Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Menu")
public class Menu implements Serializable {

    @Column(name = "menu_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(targetEntity = Meal.class, cascade={CascadeType.MERGE,CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinTable(name = "menu_meals",  joinColumns = {
            @JoinColumn(name = "menu_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "meal_id",
                    nullable = false, updatable = false) })
    private Set<Meal> meal;

    @Column(name="menu_name")
    private String name;

    @Column(name="daily_meal")
    private int daily_meal;

    @Column(name="daily_meal_price")
    private Double daily_meal_price;

    public Menu() {
    }

    @Column(name="active")
    private Boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Meal> getMeal() {
        return meal;
    }

    public void setMeal(Set<Meal> meals) {
        if (this.meal != null) this.meal.clear();
        if (meals != null) {
            this.meal = meals;
        }
    }

    public int getDaily_meal() {
        return daily_meal;
    }

    public void setDaily_meal(int daily_meal) {
        this.daily_meal = daily_meal;
    }

    public Double getDaily_meal_price() {
        return daily_meal_price;
    }

    public void setDaily_meal_price(Double daily_meal_price) {
        this.daily_meal_price = daily_meal_price;
    }

    public Menu(Set<Meal> meals, int daily_meal, Double daily_meal_price, Boolean active) {
        this.meal = meals;
        this.daily_meal = daily_meal;
        this.daily_meal_price = daily_meal_price;
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Menu(Set<Meal> meals, String name, int daily_meal, Double daily_meal_price, Boolean active) {
        this.meal = meals;
        this.name = name;
        this.daily_meal = daily_meal;
        this.daily_meal_price = daily_meal_price;
        this.active = active;
    }
}
