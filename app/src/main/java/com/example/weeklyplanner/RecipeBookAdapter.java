package com.example.weeklyplanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.HashMap;

public class RecipeBookAdapter  extends BaseAdapter {
    ArrayList<String> myRecipiesString;
    ArrayList<Recipe> myRecipies;
    LayoutInflater mInflater;
Context context;
File saveRec;
    public ArrayList<Recipe> getMyRecipies() {
        return myRecipies;
    }

    RecipeBookAdapter(Context context, ArrayList<String> recipeBook, File file){
        this.myRecipiesString=recipeBook;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myRecipies = new ArrayList<>();
        this.context=context;
        saveRec=file;
        for(int i=0;i<myRecipiesString.size();i++){
            myRecipies.add(Recipe.toRecipe(myRecipiesString.get(i)));
        }

    }
    @Override
    public int getCount() {
        return myRecipiesString.size();
    }

    @Override
    public Object getItem(int position) {
        return myRecipiesString.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.recipe_book_view, null);

        TextView recipeTextView = v.findViewById(R.id.recipeText);
        ImageButton delete_recipe = v.findViewById(R.id.recipe_delete);
        delete_recipe.setFocusable(false);
        delete_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete this meal?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myRecipiesString.remove(position);
                                for(int i=0;i<myRecipiesString.size();i++){
                                    myRecipies.add(Recipe.toRecipe(myRecipiesString.get(i)));
                                }
                                notifyDataSetInvalidated();
                                Log.i(String.valueOf(myRecipiesString),"items saved after removals in adaprter");
                                ShoppingList.saveArray(myRecipiesString,saveRec);
                                Intent i1 = new Intent(context,MealAdder.class);
                                context.startActivity(i1);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        String name = myRecipies.get(position).getName();
        recipeTextView.setText(name);
        return v;
    }
    public static class OnItemAutoCompleteAdder implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                String autocomplete;
                final String selected = (String) a.getItemAtPosition(position);
                Log.i(selected,"selecteditem");
                Recipe temp = Recipe.toRecipe(selected);

                autocomplete = temp.getName()+";";
            for(int i=0;i<temp.getIngredients().size();i++){
                if(i==temp.getIngredients().size()-1){
                    autocomplete = autocomplete;
                }
                autocomplete = autocomplete + temp.getIngredients().get(i)+ "<";
            }
                Context context = v.getContext();
                Intent i = new Intent(context,MealAdder.class);
                i.putExtra("autocompletion",autocomplete);
            Log.i(autocomplete,"before sent to main, recipe autocmplet");
                context.startActivity(i);

        }
    }
}


