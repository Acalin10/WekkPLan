
        package com.example.weeklyplanner;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

        public class RecipeBook extends AppCompatActivity {
            ArrayList<String> allRecipiesString;
            ArrayList<String> newNames;
            ArrayList<String> existingNames;
            ArrayList<String> allRecipiesNewString;
            File savedRecipies;
            ListView recipe_list;
            Button back;
            File newlyReceivedRecipies;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_recipe_book);
                recipe_list= findViewById(R.id.recipeBookListView);

                allRecipiesNewString = getIntent().getStringArrayListExtra("allRecipiesForBook");
                Log.i(String.valueOf(allRecipiesNewString),"recipies received into RecipeBook");
                savedRecipies = new File(getApplicationContext().getFilesDir(),"savedBook.txt");
                allRecipiesString = ShoppingList.loadArray(savedRecipies);
                Log.i(String.valueOf(allRecipiesString),"receipies loaded");
                newNames = MainScreen.initialise(newNames);
                existingNames = MainScreen.initialise(existingNames);

                for(int i = 0; i<allRecipiesNewString.size();i++){
                    String[] splitting = allRecipiesNewString.get(i).split(";");
                    newNames.add(splitting[0]);
                }

                Log.i(String.valueOf(allRecipiesNewString), "allRecipiesNewString");
                for(int i = 0; i<newNames.size();i++){
                    ArrayList<String> copy = newNames;
                    for(int j=i+1; j<copy.size();j++){
                        if(newNames.get(i).equals(copy.get(j))){
                            Log.i(newNames.get(i),"newNames");
                            Log.i(copy.get(j),"getj");
                            newNames.remove(j);
                            allRecipiesNewString.remove(j);
                        }
                    }
                }



                for(int i = 0; i<allRecipiesString.size();i++){
                    String[] splitting = allRecipiesString.get(i).split(";");
                    existingNames.add(splitting[0]);
                }
                allRecipiesString.addAll(allRecipiesNewString);
                for(int i =0; i<newNames.size();i++){
                    for(int j=0; j<existingNames.size();j++) {

                        if (newNames.get(i).equals(existingNames.get(j))) {
                            allRecipiesString.remove(j);
                        }
                    }
                }


                //DisplayMetrics dm = new DisplayMetrics();
                //getWindowManager().getDefaultDisplay().getMetrics(dm);
                //int width = dm.widthPixels;
                //int height = dm.heightPixels;
                //getWindow().setLayout((int)(width*.8),(int)(height*.7));
                //WindowManager.LayoutParams params = getWindow().getAttributes();
                //params.gravity= Gravity.CENTER;
                //params.x=0;
                //params.y= -20;
                //getWindow().setAttributes(params);
                recipe_list.setOnItemClickListener(new RecipeBookAdapter.OnItemAutoCompleteAdder());
                recipe_list.setAdapter(new RecipeBookAdapter(this, allRecipiesString, savedRecipies));
                back=findViewById(R.id.backToMealAdder);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i1=new Intent(com.example.weeklyplanner.RecipeBook.this,MealAdder.class);
                        ShoppingList.saveArray(allRecipiesString,savedRecipies);
                        startActivity(i1);
                    }
                });
            }
        }