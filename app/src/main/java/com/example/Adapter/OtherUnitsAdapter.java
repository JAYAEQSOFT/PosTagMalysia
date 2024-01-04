package com.example.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.Model.PriceLst;
import com.example.Model.Product;
import com.example.possale.R;

import java.text.DecimalFormat;
import java.util.List;


public class OtherUnitsAdapter extends ArrayAdapter<PriceLst> {

    private final Context context;
    private final int resourceID;
    private List<PriceLst> PrList;
    Double Rate=0.00;
    private LayoutInflater inflater;
    long seq;

    private class ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
    }

    public OtherUnitsAdapter(Context context, int resource, List<PriceLst> PrdList, long seq) {
        super(context, resource, PrdList);

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.resourceID = resource;
        this.PrList = PrdList;
        this.seq=seq;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        OtherUnitsAdapter.ViewHolder holder = null;
        if(convertView == null) {
            holder = new OtherUnitsAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.unitslist_item, null);

            holder.textView1 = (TextView) convertView.findViewById(R.id.Name);
            holder.textView2 = (TextView) convertView.findViewById(R.id.id);
            holder.textView3 = (TextView) convertView.findViewById(R.id.unit);
            holder.textView4 = (TextView) convertView.findViewById(R.id.rate);
            holder.textView5 = (TextView) convertView.findViewById(R.id.mrp);

            convertView.setTag(holder);
        }else {
            holder = (OtherUnitsAdapter.ViewHolder) convertView.getTag();
        }

        if(seq==1) {
            Rate=Double.valueOf(PrList.get(position).getRate1().toString());
        }else if(seq==2)
        {
            Rate=Double.valueOf(PrList.get(position).getRate1().toString());
        }
        else if(seq==3)
        {
            Rate=Double.valueOf(PrList.get(position).getRate1().toString());
        }else  if(seq==4)
        {
            Rate=Double.valueOf(PrList.get(position).getRate1().toString());
        }
        else if(seq==5)
        {
            Rate=Double.valueOf(PrList.get(position).getRate1().toString());
        }
        DecimalFormat df = new DecimalFormat("#");
        DecimalFormat df1 = new DecimalFormat("#.00");
        String srate = df1.format( Rate);
        String smrp=df1.format(PrList.get(position).getmRP());
     //   holder.textView1.setText(PrList.get(position).g());
        holder.textView2.setText(PrList.get(position).getUnitId().toString());
        String s=PrList.get(position).getUnitName();
        if(s!=null) {
            holder.textView3.setText(s);
        }
        else
        {
            holder.textView3.setVisibility(View.GONE);
        }
        holder.textView4.setText(srate);
        holder.textView5.setText(smrp);
//        holder.textView2.setText("MRP :"+String.format("%.2f", PrList.get(position).getBatchLst().get(position).getmRP())+"   Price:"+String.format("%.2f", PrList.get(position).getBatchLst().get(position).getRate1()));

//        holder.textView3.setVisibility(View.VISIBLE);
        //holder.textView3.setHeight(10);
        //   holder.textView3.setText(String.valueOf(BrList.get(position).getId()));



//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View rowView = inflater.inflate(resourceID, parent, false);

        //(TextView convertView.findViewById(R.id.)).setText(project[position]);

        return convertView;
    }

}


