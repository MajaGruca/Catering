package Entities;

import Security.PasswordConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
public class Users implements Serializable {

    public Users() {
    }

    public Users(String name, String passwd, String role) {
        this.name = name;
        this.password = passwd;
        this.role = role;
    }

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Convert(converter = PasswordConverter.class)
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Subscription> subscriptions = new HashSet<Subscription>();

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<Transaction>();

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

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<Subscription> subscriptions) {
        if (this.subscriptions != null) this.subscriptions.clear();
        if (subscriptions != null) {
            this.subscriptions = subscriptions;
        }
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        if (this.transactions != null) this.transactions.clear();
        if (transactions != null) {
            this.transactions = transactions;
        }
    }
}
