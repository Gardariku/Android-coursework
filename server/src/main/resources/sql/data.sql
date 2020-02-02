-- Пользователи
INSERT INTO user (id,name,sex,password)
VALUES (1, 'Roman', true, 'password1');
INSERT INTO user (id,name,sex,password)
VALUES (2, 'Yakunin George', true, 'password2');
INSERT INTO user (id,name,sex,password)
VALUES (3, 'Korshunova Anna', true, 'password3');
INSERT INTO user (id,name,sex,password)
VALUES (4, 'Baturin Alexander', false, 'password4');

-- Ингредиенты
INSERT INTO ingredient (id,name,proteins,fats,carbohydrates,calories,cost)
VALUES (1, 'Potato', 5, 1, 25, 87, 2.9);
INSERT INTO ingredient (id,name,proteins,fats,carbohydrates,calories,cost)
VALUES (2, 'Bread', 10, 4, 50, 259, 5);
INSERT INTO ingredient (id,name,proteins,fats,carbohydrates,calories,cost)
VALUES (3, 'Eggs', 13, 11, 2, 155, 12);
INSERT INTO ingredient (id,name,proteins,fats,carbohydrates,calories,cost)
VALUES (4, 'Fruits', 1, 1, 15, 52, 9);
INSERT INTO ingredient (id,name,proteins,fats,carbohydrates,calories,cost)
VALUES (5, 'Vegetables', 3, 0.5, 13, 60, 14);
INSERT INTO ingredient (id,name,proteins,fats,carbohydrates,calories,cost)
VALUES (6, 'Meat', 23, 22, 0, 295, 53);
INSERT INTO ingredient (id,name,proteins,fats,carbohydrates,calories,cost)
VALUES (7, 'Fish', 22, 12, 0, 206, 61);
INSERT INTO ingredient (id,name,proteins,fats,carbohydrates,calories,cost)
VALUES (8, 'Milk', 7, 2, 16, 107, 7.2);
INSERT INTO ingredient (id,name,proteins,fats,carbohydrates,calories,cost)
VALUES (9, 'dorime', 7, 2, 16, 107, 7.2);
INSERT INTO ingredient (id,name,proteins,fats,carbohydrates,calories,cost)
VALUES (10, 'interimi', 7, 2, 16, 107, 7.2);
INSERT INTO ingredient (id,name,proteins,fats,carbohydrates,calories,cost)
VALUES (11, 'adaparo', 7, 2, 16, 107, 7.2);
INSERT INTO ingredient (id,name,proteins,fats,carbohydrates,calories,cost)
VALUES (12, 'dorimee', 7, 2, 16, 107, 7.2);
INSERT INTO ingredient (id,name,proteins,fats,carbohydrates,calories,cost)
VALUES (13, 'ameno', 7, 2, 16, 107, 7.2);
INSERT INTO ingredient (id,name,proteins,fats,carbohydrates,calories,cost)
VALUES (14, 'amenoo', 7, 2, 16, 107, 7.2);
INSERT INTO ingredient (id,name,proteins,fats,carbohydrates,calories,cost)
VALUES (15, 'placime', 7, 2, 16, 107, 7.2);
INSERT INTO ingredient (id,name,proteins,fats,carbohydrates,calories,cost)
VALUES (16, 'placime do', 7, 2, 16, 107, 7.2);
INSERT INTO ingredient (id,name,proteins,fats,carbohydrates,calories,cost)
VALUES (17, 'Butter', 1, 82, 0, 717, 98);


-- Диеты
INSERT INTO diet (id,name,description)
VALUES (1, 'Mediterranean', '1111111111111111111111111111111111111111111111111111111111111111111111111111111111111');
INSERT INTO diet (id,name,description)
VALUES (2, 'Protein', '2222222222222222');
INSERT INTO diet (id,name,description)
VALUES (3, '5P', '333333333333333333');
INSERT INTO diet (id,name,description)
VALUES (4, 'Non-carbohydrate', '444444444444444444444444444444444444444444444444444444444444444444444444444444444444444');

-- Свойства
INSERT INTO property (id,name,description)
VALUES (1, 'Flour', '1');
INSERT INTO property (id,name,description)
VALUES (2, 'Fat', '2');
INSERT INTO property (id,name,description)
VALUES (3, 'Spicy', '3');
INSERT INTO property (id,name,description)
VALUES (4, 'Hot', '4');
INSERT INTO property (id,name,description)
VALUES (5, 'Meat', '5');
INSERT INTO property (id,name,description)
VALUES (6, 'Cold', '6');
INSERT INTO property (id,name,description)
VALUES (7, 'Fried', 'That is not good at all');

-- limitations
INSERT INTO limitations (dietid,propertyid)
VALUES (1, 1);
INSERT INTO limitations (dietid,propertyid)
VALUES (1, 3);
INSERT INTO limitations (dietid,propertyid)
VALUES (2, 3);
INSERT INTO limitations (dietid,propertyid)
VALUES (2, 1);
INSERT INTO limitations (dietid,propertyid)
VALUES (3, 1);
INSERT INTO limitations (dietid,propertyid)
VALUES (3, 2);
INSERT INTO limitations (dietid,propertyid)
VALUES (3, 3);
INSERT INTO limitations (dietid,propertyid)
VALUES (4, 1);

-- dishes
INSERT INTO dish (id,name,recipe, type)
VALUES (0, 'Fried Potatoes', 'Well, like you need to chop the potatoes, and then fry them (preferably in oil and in a pan)','Second');
INSERT INTO dish (id,name,recipe, type)
VALUES (1, 'Kek', 'For test','Drink');
INSERT INTO dish (id,name,recipe,type)
VALUES (2, 'Vegetable salad', 'Vegetables mode, we interfere, add some sunflower oil','First');
INSERT INTO dish (id,name,recipe,type,image)
VALUES (3, 'berry smoothie', 'Grind fruits, add lemon, add sugar according to your taste', 'Drink', FILE_READ('C:\Users\user\Pictures\dishes\drink.jpg'));
INSERT INTO dish (id,name,recipe,type)
VALUES (4, 'Charlotte', 'This is too long', 'Dessert');
INSERT INTO dish (id,name,recipe,type)
VALUES (5, 'Steak', 'This is too long', 'Second');

-- compositions
INSERT INTO compositions (dish_id,ingredient_id,quantity)
VALUES (0, 1, 500);
INSERT INTO compositions (dish_id,ingredient_id,quantity)
VALUES (0, 17, 40);
INSERT INTO compositions (dish_id,ingredient_id,quantity)
VALUES (1, 1, 500);
INSERT INTO compositions (dish_id,ingredient_id,quantity)
VALUES (1, 17, 40);
INSERT INTO compositions (dish_id,ingredient_id,quantity)
VALUES (2, 5, 300);
INSERT INTO compositions (dish_id,ingredient_id,quantity)
VALUES (2, 17, 30);
INSERT INTO compositions (dish_id,ingredient_id,quantity)
VALUES (3, 4, 200);
INSERT INTO compositions (dish_id,ingredient_id,quantity)
VALUES (4, 4, 100);
INSERT INTO compositions (dish_id,ingredient_id,quantity)
VALUES (4, 2, 400);
INSERT INTO compositions (dish_id,ingredient_id,quantity)
VALUES (5, 6, 500);

-- qualities
INSERT INTO qualities (dishid,propertyid)
VALUES (1, 7);
INSERT INTO qualities (dishid,propertyid)
VALUES (1, 2);
INSERT INTO qualities (dishid,propertyid)
VALUES (4, 1);
INSERT INTO qualities (dishid,propertyid)
VALUES (5, 7);
INSERT INTO qualities (dishid,propertyid)
VALUES (5, 4);
INSERT INTO qualities (dishid,propertyid)
VALUES (5, 5);
