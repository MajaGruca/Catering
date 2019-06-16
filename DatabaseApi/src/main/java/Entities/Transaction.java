package Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Transaction")
public class Transaction implements Serializable {

    @Column(name = "transaction_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(targetEntity = Meal.class, cascade={CascadeType.MERGE,CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinTable(name = "transaction_meals",  joinColumns = {
            @JoinColumn(name = "transaction_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "meal_id",
                    nullable = false, updatable = false) })
        private Set<Meal> meals = new HashSet<Meal>();

    @Column(name="price")
    private Double price;

    @Column(name="date")
    private Date date;

    @Column(name="delivery")
    private Boolean delivery;

    @Column(name="status")
    private String status;

    @Column(name="time")
    private Time time;

    public Transaction() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getDelivery() {
        return delivery;
    }

    public void setDelivery(Boolean delivery) {
        this.delivery = delivery;
    }

    public Transaction(Set<Meal> meals, Double price, Date date, Boolean delivery) {
        this.meals = meals;
        this.price = price;
        this.date = date;
        this.delivery = delivery;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }

    public Double sumMealPrices()
    {
        Double sum = 0.0;
        for(Meal m : meals)
        {
            sum += m.getPrice();
        }
        return sum;
    }
}
