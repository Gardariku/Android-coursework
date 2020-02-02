package dietcourtserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="menu")
public class Menu {

    @EmbeddedId
    private MenuID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("UserID")
    @JsonIgnoreProperties({"ingestions"})
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("DishID")
    @JsonIgnoreProperties({"ingestions", "composition", "properties"})
    private Dish dish;

    @Column(name="day")
    @NotEmpty(message = "Укажите день недели")
    private int day;

    @Column(name="quantity")
    @NotEmpty(message = "Укажите размер блюда (грамм)")
    private double quantity;

    public MenuID getId() {
        return id;
    }

    public void setId(MenuID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
