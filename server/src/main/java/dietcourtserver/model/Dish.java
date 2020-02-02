package dietcourtserver.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Blob;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="dish")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    @NotEmpty(message = "Укажите название блюда")
    private String name;

    @Column(name="recipe")
    private String recipe;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Diet> getAppropriateDiets() {
        return appropriateDiets;
    }

    public void setAppropriateDiets(Set<Diet> appropriateDiets) {
        this.appropriateDiets = appropriateDiets;
    }

    @Column(name="type")
    private String type;

    @Column(name = "image")
    @JsonIgnore
    private Blob avatar = null;


    @Formula("select sum(i.cost * c.quantity / 100) from ingredient i " +
            "join compositions c where c.dish_id = id and c.ingredient_id = i.id")
    private double cost;

    @Formula("select sum(i.proteins * c.quantity / 100) from ingredient i " +
            "join compositions c where c.dish_id = id and c.ingredient_id = i.id")
    private double proteins;

    @Formula("select sum(i.fats * c.quantity / 100) from ingredient i " +
            "join compositions c where c.dish_id = id and c.ingredient_id = i.id")
    private double fats;

    @Formula("select sum(i.carbohydrates * c.quantity / 100) from ingredient i " +
            "join compositions c where c.dish_id = id and c.ingredient_id = i.id")
    private double carbohydrates;

    @Formula("select sum(i.calories * c.quantity / 100) from ingredient i " +
            "join compositions c where c.dish_id = id and c.ingredient_id = i.id")
    private double calories;

    /*
    @Formula("(select sum(i.calories) from compositions c " +
            "join ingredient i where c.dish_id = id) * (select c.quantity from compositions c join ingredient i where c.dish_id = id) / 100") */

    @ManyToMany
    @JoinTable(name = "qualities",
            joinColumns = @JoinColumn(name = "dishid"), inverseJoinColumns = @JoinColumn(name = "propertyid"))
    private Set<Property> properties;

    @ManyToMany
    @JoinTable(name = "suitables",
            joinColumns = @JoinColumn(name = "dishid"), inverseJoinColumns = @JoinColumn(name = "dietid"))
    private Set<Diet> appropriateDiets;

    @OneToMany(
            mappedBy = "dish",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "dish"})
    private List<Compositions> composition;

    @OneToMany(
            mappedBy = "dish",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private List<Menu> ingestions;

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

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public Blob getAvatar() {
        return avatar;
    }

    public void setAvatar(Blob avatar) {
        this.avatar = avatar;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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

    public Collection<Compositions> getComposition() {
        return composition;
    }

    public void setComposition(List<Compositions> composition) {
        this.composition = composition;
    }

    public Collection<Menu> getIngestions() {
        return ingestions;
    }

    public void setIngestions(List<Menu> ingestions) {
        this.ingestions = ingestions;
    }

    public boolean isAllowedTo(Diet diet) {
        Set<Property> intersection = new HashSet<>(this.properties);
        intersection.retainAll(diet.getBannedProperties());
        return (intersection.isEmpty());
    }

    public boolean isAllowedTo(User user) {
        if(user.getDiet() == null)
            return false;
        return (isAllowedTo(user.getDiet()));
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
    }
}
