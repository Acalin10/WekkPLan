package com.example.weeklyplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class IngredientAdapter extends BaseAdapter {
    ArrayList<String> ingredientsForDay;
    File ingredientsForDay_list;
    LayoutInflater mInflater;
    IngredientAdapter(Context context, ArrayList<String> ingredientsForDay){
        this.ingredientsForDay=ingredientsForDay;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return ingredientsForDay.size();
    }

    @Override
    public Object getItem(int position) {
        return ingredientsForDay.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.ingredients_view, null);
        TextView ingredientTextView = v.findViewById(R.id.ingredient_name);
        ImageButton delete_ingredient = v.findViewById(R.id.ingredient_delete);
        delete_ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientsForDay.remove(position);
                notifyDataSetChanged();
                ShoppingList.saveArray(ingredientsForDay,ingredientsForDay_list);
            }
        });
        String name = ingredientsForDay.get(position);
        ingredientTextView.setText(name);
        return v;
    }
}
