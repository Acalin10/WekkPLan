package com.example.weeklyplanner;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class ListItemAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<String> list_items;
    private ArrayList<String> list_items_days;
    private File saveshop;
    private File savedays;
    ArrayList<String> list_ingredients;
    File save_ingredients;

    public void setList_items(ArrayList<String> list_items) {
        this.list_items = list_items;
    }

    public LayoutInflater getmInflater() {
        return mInflater;
    }

    public void setmInflater(LayoutInflater mInflater) {
        this.mInflater = mInflater;
    }

    public ArrayList<String> getList_items() {
        return list_items;
    }

    public ArrayList<String> getList_items_days() {
        return list_items_days;
    }

    public void setList_items_days(ArrayList<String> list_items_days) {
        this.list_items_days = list_items_days;
    }




    public File getSaveshop() {
        return saveshop;
    }

    public void setSaveshop(File saveshop) {
        this.saveshop = saveshop;
    }

    public File getSavedays() {
        return savedays;
    }

    public void setSavedays(File savedays) {
        this.savedays = savedays;
    }

    public ListItemAdapter(Context context, ArrayList<String> i, ArrayList<String> j, File shopping_list, File shopping_list_days){
        setList_items(i);
        setList_items_days(j);
        setmInflater((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        setSavedays(shopping_list_days);
        setSaveshop(shopping_list);
    }
    @Override

    public int getCount() {
        return getList_items().size();

    }

    @Override
    public Object getItem(int i) {
        return getList_items().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        View v = getmInflater().inflate(R.layout.shop_list_view,null);
        final TextView nameTextView = v.findViewById(R.id.item_name);
        final TextView dayTextView = v.findViewById(R.id.item_day);
        ImageButton deleteButton = v.findViewById(R.id.item_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getList_items().remove(position);
                getList_items_days().remove(position);

                notifyDataSetChanged();
                ShoppingList.saveArray(getList_items(),getSaveshop());
                ShoppingList.saveArray(getList_items_days(),getSavedays());
            }
        });
        String name = getList_items().get(position);
        String day = getList_items_days().get(position);
        nameTextView.setText(name);
        dayTextView.setText(day);
        return v;
    }
}