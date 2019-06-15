package Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Meal")
public class Meal implements Serializable {

    @Column(name = "meal_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "owner")
    private String owner;

    @ManyToMany(targetEntity = Category.class, cascade=CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "meal_categories",  joinColumns = {
            @JoinColumn(name = "meal_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "category_id",
                    nullable = false, updatable = false) })
    private Set<Category> category;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "meal")
    private Set<Menu> menu;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "meals")
    private Set<Subscription> subscription;

    public Meal() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Meal(String name, String description, Double price, Double weight, String owner) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.owner = owner;
    }

    public Meal(String name, String description, Double price, Double weight, String owner, Set<Category> category, Set<Menu> menus) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.owner = owner;
        this.category = category;
        this.menu = menus;
//        this.subscription = subscription;
    }

    public Set<Category> getCategory() {
        return category;
    }

    public void setCategory(Set<Category> category) {
        this.category = category;
    }

//    public Subscription getSubscription() {
//        return subscription;
//    }
//
//    public void setSubscription(Subscription subscription) {
//        this.subscription = subscription;
//    }

    public Set<Menu> getMenu() {
        return menu;
    }

    public void setMenu(Set<Menu> menus) {
        this.menu = menus;
    }
}
