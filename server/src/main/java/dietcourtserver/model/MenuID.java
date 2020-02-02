package dietcourtserver.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MenuID
        implements Serializable {

    @Column(name = "UserID")
    private int userID;

    @Column(name = "DishID")
    private int dishID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        MenuID that = (MenuID) o;
        return Objects.equals(userID, that.userID) &&
                Objects.equals(dishID, that.dishID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, dishID);
    }
}