package dietcourtserver.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CompositionID
        implements Serializable {

    @Column(name = "Dish_id")
    private int dishID;

    @Column(name = "Ingredient_id")
    private int ingredientID;

    public CompositionID() {
    }

    public CompositionID(int dishid, int ingrid) {
        this.dishID = dishid;
        this.ingredientID = ingrid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        CompositionID that = (CompositionID) o;
        return Objects.equals(dishID, that.dishID) &&
                Objects.equals(ingredientID, that.ingredientID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dishID, ingredientID);
    }
}