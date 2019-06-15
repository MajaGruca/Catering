package Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Category")
public class Category implements Serializable {

    @Column(name = "category_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;
    
    @ManyToMany(cascade=CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Meal> meal = new HashSet<Meal>();

    public Category() {
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

    public Category(String name) {
        this.name = name;
    }

    public Set<Meal> getMeal() {
        return meal;
    }

    public void setMeal(Set<Meal> meal) {
        this.meal = meal;
    }

    public Category(String name, Set<Meal> meal) {
        this.name = name;
        this.meal = meal;
    }
}
