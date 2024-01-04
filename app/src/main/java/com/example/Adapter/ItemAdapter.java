package com.example.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.Model.Product;
import com.example.Model.items;
import com.example.possale.R;

import java.util.List;

/**
 * Created by Ayo on 17/04/2017.
 */

public class
ItemAdapter extends ArrayAdapter<items>{
    private List<items> list;
    private Context context;
    private LayoutInflater inflater;
    TextView currentFoodName,
            currentCost,
            quantityText,
            addMeal,
            subtractMeal,
            removeMeal;

    public ItemAdapter(Context context, List<items> itms) {
        super(context, 0, itms);
        this.list = itms;
        this.context = context;
    }


    public View getView(final int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_my_prds,parent,false
            );
        }

        final items currentprd = getItem(position);

        currentFoodName = (TextView)listItemView.findViewById(R.id.name);
        currentCost = (TextView)listItemView.findViewById(R.id.selected_food_amount);
        subtractMeal = (TextView)listItemView.findViewById(R.id.minus_meal);
        quantityText = (TextView)listItemView.findViewById(R.id.quantity);
        addMeal = (TextView)listItemView.findViewById(R.id.plus_meal);
        removeMeal = (TextView)listItemView.findViewById(R.id.delete_item);

        //Set the text of the meal, amount and quantity
        currentFoodName.setText(currentprd.getProductName());
        currentCost.setText("GH"+"\u20B5"+" "+ (currentprd.getQuantity() * currentprd.getPrice()));
        quantityText.setText("x "+ currentprd.getQuantity());

        //OnClick listeners for all the buttons on the ListView Item
        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   currentprd.addToQuantity();
                quantityText.setText("x "+ currentprd.getQuantity());
                currentCost.setText(""+" "+ (currentprd.getPrice() * currentprd.getQuantity()));
                notifyDataSetChanged();
            }
        });

        subtractMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // currentprd.removeFromQuantity();
                quantityText.setText("x "+currentprd.getQuantity());
                currentCost.setText("GH"+"\u20B5"+" "+ (currentprd.getPrice() * currentprd.getQuantity()));
                notifyDataSetChanged();
            }
        });

        removeMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        return listItemView;
    }

}