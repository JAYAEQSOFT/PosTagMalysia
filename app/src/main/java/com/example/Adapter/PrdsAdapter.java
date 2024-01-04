package com.example.Adapter;

import android.content.Context;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.Model.Product;
import com.example.Model.items;
import com.example.possale.R;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

public class PrdsAdapter extends ArrayAdapter<items> {

    private final Context context;
    private final int resourceID;
    private List<items> PrList;
    PrdsAdapter.ViewHolder holder = null;
    private LayoutInflater inflater;

    private class ViewHolder {
        TextView textViewName;
        TextView textViewQty;
        TextView textViewRate;
        TextView textViewSub;
        TextView textViewAdd;
        TextView textViewRemove;
        TextView textViewid;
        TextView textViewqty1;
    }

    public PrdsAdapter(Context context, int resource, List<items> PrdList) {
        super(context, resource, PrdList);

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.resourceID = resource;
        this.PrList = PrdList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if(convertView == null) {
            holder = new PrdsAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.item_my_prds, null);

            holder.textViewName = (TextView) convertView.findViewById(R.id.name);
            holder.textViewid = (TextView) convertView.findViewById(R.id.id);
            holder.textViewQty = (TextView) convertView.findViewById(R.id.quantity);
            holder.textViewRate = (TextView) convertView.findViewById(R.id.selected_food_amount);
            holder.textViewSub = (TextView)convertView.findViewById(R.id.minus_meal);
            holder.textViewAdd = (TextView)convertView.findViewById(R.id.plus_meal);
            holder.textViewRemove = (TextView)convertView.findViewById(R.id.delete_item);
            holder.textViewqty1 = (TextView)convertView.findViewById(R.id.quantity1);
            convertView.setTag(holder);
        }else {
            holder = (PrdsAdapter.ViewHolder) convertView.getTag();
        }

        DecimalFormat df = new DecimalFormat("#.00");
        DecimalFormat df1 = new DecimalFormat("#.000");
        String sqty = df1.format( PrList.get(position).getQuantity());
        String sqty1 = df1.format( PrList.get(position).getQtyy());

        String samt = df.format( PrList.get(position).getQuantity()*PrList.get(position).getPrice());


        holder.textViewName.setText(PrList.get(position).getProductName()+"( Rs. "+PrList.get(position).getPrice()+")");
        holder.textViewid.setText(String.valueOf(PrList.get(position).getProductId()));
        holder.textViewRate.setText(samt);
       holder.textViewQty.setText(sqty);
       holder.textViewqty1.setText(sqty1);
        //holder.textView3.setHeight(10);
        //   holder.textView3.setText(String.valueOf(BrList.get(position).getId()));



//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View rowView = inflater.inflate(resourceID, parent, false);

        //(TextView convertView.findViewById(R.id.)).setText(project[position]);
        holder.textViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double qty=PrList.get(position).getQtyy();
                PrList.get(position).addToQuantity(qty);
                holder.textViewQty.setText(""+  PrList.get(position).getQuantity());
                holder.textViewRate.setText(""+" "+ (PrList.get(position).getPrice() * PrList.get(position).getQuantity()));
                notifyDataSetChanged();
            }
        });

        holder.textViewSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double qty=PrList.get(position).getQtyy();
                PrList.get(position).removeFromQuantity(qty);
                holder.textViewQty.setText("x "+  PrList.get(position).getQuantity());
                holder.textViewRate.setText(""+" "+ (PrList.get(position).getPrice() * PrList.get(position).getQuantity()));
                notifyDataSetChanged();
            }
        });

        holder.textViewRemove .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrList.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

}


