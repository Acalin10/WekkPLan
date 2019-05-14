package com.example.weeklyplanner;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private ArrayList<String> ingredients;
    private String Day;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public Recipe(String Name, ArrayList<String> Ingredients, String Day){
        setName(Name);
        setDay(Day);
        setIngredients(Ingredients);
    }

    @Override
    public String toString() {
        String recipeString= new String();
        recipeString = getName() + ";";
        for(int k=0; k<getIngredients().size();k++){
            if(k>=0&&k<getIngredients().size()-1) {
                recipeString = recipeString + getIngredients().get(k) + "<";
            }else{recipeString = recipeString + getIngredients().get(k);}
        }
        recipeString = recipeString + ";" +getDay();
        return recipeString;
    }
    public static Recipe toRecipe(String string){

        String[] parts = string.split(";");
        ArrayList<String> ingredients = new ArrayList<>();
        String[] ingr = parts[1].split("<");
        for(int i=0;i<ingr.length;i++){
            ingredients.add(ingr[i]);
        }
        if(ingr.length == 0){
            ingredients.add(parts[1]);
        }
        String name = parts[0];
        String day = parts[2];
        return new Recipe(name,ingredients,day);
    }
}
