package com.example.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.Model.Company;
import com.example.Model.CompanyInfo;
import com.example.possale.R;


import java.util.ArrayList;
import java.util.List;


public class CompanyLstAdapter extends ArrayAdapter<Company> {

    private final Context context;
    private final int resourceID;
    private List<Company> CustList;
    private ArrayList<Company> objects;
    private LayoutInflater inflater;

    private class ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
    }

    public CompanyLstAdapter(Context context, int resource, List<Company> custList) {
        super(context, resource, custList);

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.resourceID = resource;
        this.CustList = custList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.companylist_item, null);

            holder.textView1 = (TextView) convertView.findViewById(R.id.comName);
            holder.textView2 = (TextView) convertView.findViewById(R.id.id);
            holder.textView3 = (TextView) convertView.findViewById(R.id.comdbname);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }



        holder.textView1.setText(CustList.get(position).getName());
        holder.textView2.setText(String.valueOf(CustList.get(position).getId()));


        holder.textView3.setText(String.valueOf(CustList.get(position).getDbName()));



//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View rowView = inflater.inflate(resourceID, parent, false);

        //(TextView convertView.findViewById(R.id.)).setText(project[position]);

        return convertView;
    }

}
