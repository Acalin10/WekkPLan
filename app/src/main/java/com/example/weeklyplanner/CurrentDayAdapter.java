package com.example.weeklyplanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class CurrentDayAdapter extends BaseAdapter {
    ArrayList<Recipe> RecipiesForDay;
    LayoutInflater mInflater;
    String day;
    Context context;
    ArrayList<String> RemovedItems;
    File toSaveRemoved;
    CurrentDayAdapter(Context context, ArrayList<Recipe> RecForDay, String currentDay, File toSave){
        RecipiesForDay=RecForDay;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        day=currentDay;
        RemovedItems= new ArrayList<>();
        Log.i(String.valueOf(RecipiesForDay),"RecipiesForDay in contructor");
        toSaveRemoved = toSave;
        this.context=context;
    }
    @Override
    public int getCount() {
        return RecipiesForDay.size();
    }

    @Override
    public Object getItem(int position) {
        return RecipiesForDay.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.current_day_view, null);
        TextView currentDayText = v.findViewById(R.id.current_day_nameText);
        ImageButton deleteRecipeForCurrentDay = v.findViewById(R.id.current_day_delete);
        String name = RecipiesForDay.get(position).getName();
        int mealsInDay = 0;
        String daysThatIsChecked = RecipiesForDay.get(position).getDay();
        Log.i(RecipiesForDay.get(position).getName(),"get Name for position");
        Log.i(day,"day");
        Log.i(RecipiesForDay.get(position).getDay(),"get day");
        if(name!=null&&name!="") {
            if (daysThatIsChecked.equals(day)) {
                currentDayText.setText(name);
            }
            else{
                currentDayText.setVisibility(View.GONE);
                deleteRecipeForCurrentDay.setVisibility(View.GONE);
            }

        }
        deleteRecipeForCurrentDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete this meal?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                RemovedItems.add(RecipiesForDay.get(position).toString());
                                RecipiesForDay.remove(position);
                                notifyDataSetChanged();

                                Intent i2=new Intent(context,MainScreen.class);
                                i2.putExtra("toRemove",RemovedItems);
                                context.startActivity(i2);
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
        return v;
    }
}
