package dietcourtserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name="diet")
public class Diet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name="name")
    @NotEmpty(message = "Укажите название диеты")
    private String name;

    @Column(name="description")
    private String description;

    @ManyToMany
    @JoinTable(name = "limitations",
            joinColumns = @JoinColumn(name = "dietid"), inverseJoinColumns = @JoinColumn(name = "propertyid"))
    @JsonIgnoreProperties({"dishes", "unfriendlyDiets"})
    private Set<Property> bannedProperties;

    @ManyToMany
    @JoinTable(name = "suitables",
            joinColumns = @JoinColumn(name = "dietid"), inverseJoinColumns = @JoinColumn(name = "dishid"))
    @JsonIgnore
    private Set<Dish> appropriateDishes;

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

    public Set<Property> getBannedProperties() {
        return bannedProperties;
    }

    public void setBannedProperties(Set<Property> bannedProperties) {
        this.bannedProperties = bannedProperties;
    }
}
