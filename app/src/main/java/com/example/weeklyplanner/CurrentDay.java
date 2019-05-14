package com.example.weeklyplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class CurrentDay extends AppCompatActivity {
ArrayList<String> receivedInfo;
ArrayList<String> receivedRecipies;
ArrayList<Recipe> recipesToBeShown;
String dayToBeChecked;
TextView Day;
ListView currentDayList;
Button back;
File saveWhatShouldBeRemoved;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_day);
        Log.i(String.valueOf(getIntent().getStringArrayListExtra("RecipiesToBeChecked")),"smth");
        receivedInfo = getIntent().getStringArrayListExtra("RecipiesToBeChecked");
        receivedInfo = MainScreen.initialise(receivedInfo);
        receivedRecipies = MainScreen.initialise(receivedRecipies);
        back=findViewById(R.id.backToMealAdderFromDay);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(CurrentDay.this,MainScreen.class);
                startActivity(i2);
            }
        });
        if(receivedInfo.size()>1){
            dayToBeChecked=receivedInfo.get(receivedInfo.size()-1);
            receivedInfo.remove(receivedInfo.size()-1);
        }
        Log.i(dayToBeChecked,"dayToBeChecked");
        Day =findViewById(R.id.Day);
        currentDayList = (findViewById(R.id.dayListView));
        Day.setText(dayToBeChecked);
            receivedRecipies.addAll(receivedInfo);

        Log.i(String.valueOf(receivedInfo),"receivedInfo");
        receivedInfo = MainScreen.initialise(receivedInfo);
        receivedRecipies = MainScreen.initialise(receivedRecipies);
        recipesToBeShown = MainScreen.initialise(recipesToBeShown);
        if(receivedInfo.size()>1) {
            for (int i = 0; i < receivedRecipies.size(); i++) {
                recipesToBeShown.add(Recipe.toRecipe(receivedRecipies.get(i)));
            }
        }
        currentDayList.setAdapter(new CurrentDayAdapter(this,recipesToBeShown,dayToBeChecked,saveWhatShouldBeRemoved));
    }
}
