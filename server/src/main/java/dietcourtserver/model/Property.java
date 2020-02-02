package dietcourtserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Entity
@Table(name="property")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name="name")
    @NotEmpty(message = "Укажите название типа блюда")
    private String name;

    @Column(name="description")
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "qualities", joinColumns = @JoinColumn(name = "PropertyID"), inverseJoinColumns = @JoinColumn(name = "DishID"))
    @JsonIgnore
    private Collection<Dish> dishes;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "limitations", joinColumns = @JoinColumn(name = "PropertyID"), inverseJoinColumns = @JoinColumn(name = "DietID"))
    @JsonIgnore
    private Collection<Diet> unfriendlyDiets;

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

    public Collection<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Collection<Dish> dishes) {
        this.dishes = dishes;
    }

    public Collection<Diet> getUnfriendlyDiets() {
        return unfriendlyDiets;
    }

    public void setUnfriendlyDiets(Collection<Diet> unfriendlyDiets) {
        this.unfriendlyDiets = unfriendlyDiets;
    }
}
