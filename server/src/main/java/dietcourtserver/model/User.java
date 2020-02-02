package dietcourtserver.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.Nullable;
import org.hibernate.annotations.Formula;
import scpsolver.constraints.LinearBiggerThanEqualsConstraint;
import scpsolver.constraints.LinearSmallerThanEqualsConstraint;
import scpsolver.lpsolver.LinearProgramSolver;
import scpsolver.lpsolver.SolverFactory;
import scpsolver.problems.LPWizard;
import scpsolver.problems.LinearProgram;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="password")
    private String password;

    @Column(name="name")
    @NotEmpty(message = "Укажите имя пользователя")
    private String name;

    @Column(name="sex")
    private Boolean sex;

    @Column(name="weight")
    private Integer weight;

    @Column(name="height")
    private Integer height;

    @Column(name="age")
    private Integer age;

    @Column(name="activity")
    @Nullable
    private Double activity;

    @ManyToOne
    @JoinColumn(name = "DietID", referencedColumnName = "ID")
    @JsonIgnoreProperties("bannedProperties")
    private Diet diet;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Menu> ingestions;

    @Column(nullable = true)
    private Double totalCost;

    public Double calculateCost() {
        Double cost = 0.0;
        for (Menu menu: ingestions) {
            cost += menu.getQuantity()/100 * menu.getDish().getCost();
        }
        this.totalCost = cost;
        return cost;
    }

    @Column(nullable = true)
    private Double totalProteins;

    public Double calculateProteins() {
        Double proteins = 0.0;
        for (Menu menu: ingestions) {
            proteins += menu.getQuantity()/100 * menu.getDish().getProteins();
        }
        this.totalProteins = proteins;
        return proteins;
    }

    @Column(nullable = true)
    private Double totalFats;

    public Double calculateFats() {
        Double fats = 0.0;
        for (Menu menu: ingestions) {
            fats += menu.getQuantity()/100 * menu.getDish().getFats();
        }
        this.totalFats = fats;
        return fats;
    }

    @Column(nullable = true)
    private Double totalCarbohydrates;

    public Double calculateCarbohydrates() {
        Double carbohydrates = 0.0;
        for (Menu menu: ingestions) {
            carbohydrates += menu.getQuantity()/100 * menu.getDish().getCarbohydrates();
        }
        this.totalFats = carbohydrates;
        return carbohydrates;
    }

    @Column(nullable = true)
    private Double totalCalories;

    public Double calculateCalories() {
        Double calories = 0.0;
        for (Menu menu: ingestions) {
            calories += menu.getQuantity() / 100 * menu.getDish().getCalories();
        }
        this.totalCalories = calories;
        return calories;
    }

    @Column(nullable = true)
    private Double recommendedProteins;

    public Double calculateRecommendedProteins() {
        this.recommendedCalories = recommendedCalories / (6 * 4);
        return this.recommendedCalories;
    }

    public boolean proteinsAreSatisfied() {
        return totalProteins >= recommendedProteins;
    }

    @Column(nullable = true)
    private Double recommendedFats;

    public Double calculateRecommendedFats() {
        this.recommendedFats = recommendedCalories / (6 * 9);
        return this.recommendedFats;
    }

    public boolean fatsAreSatisfied() {
        return totalFats >= recommendedFats;
    }

    @Column(nullable = true)
    private Double recommendedCarbohydrates;

    public Double calculateRecommendedCarbohydrates() {
        this.recommendedCarbohydrates = recommendedCalories / 6 ;
        return this.recommendedCarbohydrates;
    }

    boolean carbohydratesAreSatisfied() {
        return totalCarbohydrates >= recommendedCarbohydrates;
    }

    @Column(nullable = true)
    private Double recommendedCalories;

    public Double calculateRecommendedCalories() {
        //Расчет по формуле Миффлина-Сан Жеора
        //для мужчин: (10 x вес (кг) + 6.25 x рост (см) – 5 x возраст (г) + 5) x A;
        //для женщин: (10 x вес (кг) + 6.25 x рост (см) – 5 x возраст (г) – 161) x A.
        Double calories;
        if(sex) {
            calories = (10 * weight + 6.25 * height - 5 * age + 5) * activity;
        } else {
            calories = (10 * weight + 6.25 * height - 5 * age - 161) * activity;
        }
        this.recommendedCalories = calories;
        return calories;
    }

    @Column(nullable = true)
    public boolean caloriesAreSatisfied() {
        return totalCalories >= recommendedCalories;
    }

    public Set<Dish> getUnallowedDishes() {
        if (diet == null)
            return null;
        Set<Dish> unallowedDishes = new HashSet<>();
        for(Menu menu: ingestions) {
            if (!menu.getDish().isAllowedTo(this))
                unallowedDishes.add(menu.getDish());
        }
        return unallowedDishes;
    }

    //Поиск состава меню, минимально изменяющего рацион (измененение веса блюда с учетом стоимости)
    //и удовлетворяющего при этом требованиям по питательным веществам.
    //Используется библиотека для решения задач линейного программирования симплекс-методом
    public Collection<Menu> optimizeMenu() {

        List<Double> cost = new ArrayList<>();
        List<Double> proteins = new ArrayList<>();
        List<Double> fats = new ArrayList<>();
        List<Double> carbohydrates = new ArrayList<>();
        List<Double> calories = new ArrayList<>();

        for(Menu menu : ingestions) {
            cost.add(1/menu.getDish().getCost());
            proteins.add(menu.getDish().getProteins());
            fats.add(menu.getDish().getFats());
            carbohydrates.add(menu.getDish().getCarbohydrates());
            calories.add(menu.getDish().getCalories());
        }

        double[] variables = new double[cost.size()*2];
        double[] proteinsConstraint = new double[proteins.size()*2];
        double[] fatsConstraint = new double[fats.size()*2];
        double[] carbohydratesConstraint = new double[carbohydrates.size()*2];
        double[] caloriesConstraint = new double[calories.size()*2];
        double[] lowerBounds = new double[calories.size()*2];
        for (int i = 0; i < cost.size(); i++) {
            variables[i] = 1 - 1/cost.get(i);
            variables[i+cost.size()] = 1 + 1/cost.get(i);
            proteinsConstraint[i] = proteins.get(i)/100;
            proteinsConstraint[i+cost.size()] = -proteins.get(i)/100;
            fatsConstraint[i] = fats.get(i)/100;
            fatsConstraint[i+cost.size()] = -fats.get(i)/100;
            carbohydratesConstraint[i] = carbohydrates.get(i)/100;
            carbohydratesConstraint[i+cost.size()] = -carbohydrates.get(i)/100;
            caloriesConstraint[i] = calories.get(i)/100;
            caloriesConstraint[i+cost.size()] = -calories.get(i)/100;
            lowerBounds[i] = 0.0;
            lowerBounds[i+cost.size()] = 0.0;
        }

        LinearProgram lp = new LinearProgram(variables);
        lp.addConstraint(new LinearBiggerThanEqualsConstraint(proteinsConstraint, recommendedProteins-totalProteins, "c1"));
        lp.addConstraint(new LinearBiggerThanEqualsConstraint(fatsConstraint, recommendedFats-totalFats, "c2"));
        lp.addConstraint(new LinearBiggerThanEqualsConstraint(carbohydratesConstraint, recommendedCarbohydrates-totalCarbohydrates, "c3"));
        lp.addConstraint(new LinearBiggerThanEqualsConstraint(caloriesConstraint, recommendedCalories-totalCalories, "c4"));
        lp.setMinProblem(true);
        lp.setLowerbound(lowerBounds);

        LinearProgramSolver solver  = SolverFactory.newDefault();
        double[] sol = solver.solve(lp);

        Collection<Menu> recommendedMenu = ingestions;
        int i = 0;
        for(Menu menu : recommendedMenu) {
            menu.setQuantity(menu.getQuantity()+sol[i]-sol[i+ingestions.size()]);
            i++;
        }

        return recommendedMenu;
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

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getActivity() {
        return activity;
    }

    public void setActivity(Double activity) {
        this.activity = activity;
    }

    public Diet getDiet() {
        return diet;
    }

    public void setDiet(Diet diet) {
        this.diet = diet;
    }

    public List<Menu> getIngestions() {
        return ingestions;
    }

    public void setIngestions(List<Menu> ingestions) {
        this.ingestions = ingestions;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Double getTotalProteins() {
        return totalProteins;
    }

    public void setTotalProteins(Double totalProteins) {
        this.totalProteins = totalProteins;
    }

    public Double getTotalFats() {
        return totalFats;
    }

    public void setTotalFats(Double totalFats) {
        this.totalFats = totalFats;
    }

    public Double getTotalCarbohydrates() {
        return totalCarbohydrates;
    }

    public void setTotalCarbohydrates(Double totalCarbohydrates) {
        this.totalCarbohydrates = totalCarbohydrates;
    }

    public Double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(Double totalCalories) {
        this.totalCalories = totalCalories;
    }

    public Double getRecommendedProteins() {
        return recommendedProteins;
    }

    public void setRecommendedProteins(Double recommendedProteins) {
        this.recommendedProteins = recommendedProteins;
    }

    public Double getRecommendedFats() {
        return recommendedFats;
    }

    public void setRecommendedFats(Double recommendedFats) {
        this.recommendedFats = recommendedFats;
    }

    public Double getRecommendedCarbohydrates() {
        return recommendedCarbohydrates;
    }

    public void setRecommendedCarbohydrates(Double recommendedCarbohydrates) {
        this.recommendedCarbohydrates = recommendedCarbohydrates;
    }

    public Double getRecommendedCalories() {
        return recommendedCalories;
    }

    public void setRecommendedCalories(Double recommendedCalories) {
        this.recommendedCalories = recommendedCalories;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSex() {
        return sex;
    }
}
