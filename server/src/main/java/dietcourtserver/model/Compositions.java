package dietcourtserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="compositions")
public class Compositions {

    @EmbeddedId
    private CompositionID id;

    @JsonIgnoreProperties({"composition", "properties", "ingestions"})
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("Dish_id")
    private Dish dish;

    @JsonIgnoreProperties({"composition"})
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("Ingredient_id")
    private Ingredient ingredient;

    @Column(name="Quantity")
    private int quantity;

    public Compositions() {
    }

    public Compositions(CompositionID compositionID, int quantity) {
        this.id = compositionID;
        this.quantity = quantity;
    }

    public CompositionID getId() {
        return id;
    }

    public void setId(CompositionID id) {
        this.id = id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
