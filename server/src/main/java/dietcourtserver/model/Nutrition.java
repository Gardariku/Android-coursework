package dietcourtserver.model;

public class Nutrition {

    private double proteins;

    private double fats;

    private double carbohydrates;

    private double calories;

    Nutrition(double protein, double fat, double carbohydrate, double calorie) {
        this.proteins = protein;
        this.fats = fat;
        this.carbohydrates = carbohydrate;
        this.calories = calorie;
    }
}
