package dietcourtserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Blob;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name="name")
    @NotEmpty(message = "Укажите название ингредиента")
    private String name;

    @Column(name="proteins")
    @NotEmpty(message = "Укажите содержание белка")
    private double proteins;

    @Column(name="fats")
    @NotEmpty(message = "Укажите содержание жиров")
    private double fats;

    @Column(name="carbohydrates")
    @NotEmpty(message = "Укажите содержание углеводов")
    private double carbohydrates;

    @Column(name="calories")
    @NotEmpty(message = "Укажите содержание калорий")
    private double calories;

    @Column(name="cost")
    @NotEmpty(message = "Укажите примерную стоимость ингредиента")
    private double cost;

    @Column(name = "image")
    private Blob avatar = null;

    @JsonIgnore
    @OneToMany(
            mappedBy = "ingredient",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Compositions> composition;

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

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Blob getAvatar() {
        return avatar;
    }

    public void setAvatar(Blob avatar) {
        this.avatar = avatar;
    }

    public List<Compositions> getComposition() {
        return composition;
    }

    public void setComposition(List<Compositions> composition) {
        this.composition = composition;
    }
}
