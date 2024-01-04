package com.example.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;



import android.widget.TextView;

import com.example.Model.Branch;
import com.example.possale.R;


import java.util.ArrayList;
import java.util.List;


public class BranchLstAdapter extends ArrayAdapter<Branch> {

    private final Context context;
    private final int resourceID;
    private List<Branch> BrList;
    private ArrayList<Branch> objects;
    private LayoutInflater inflater;

    private class ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
    }

    public BranchLstAdapter(Context context, int resource, List<Branch> BrList) {
        super(context, resource, BrList);

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.resourceID = resource;
        this.BrList = BrList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.branchlist_item, null);

            holder.textView1 = (TextView) convertView.findViewById(R.id.Name);
            holder.textView2 = (TextView) convertView.findViewById(R.id.id);
            holder.textView3 = (TextView) convertView.findViewById(R.id.comGstn);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }



        holder.textView1.setText(BrList.get(position).getName());
        holder.textView2.setText(String.valueOf(BrList.get(position).getId()));

//        holder.textView3.setVisibility(View.VISIBLE);
        //holder.textView3.setHeight(10);
        //   holder.textView3.setText(String.valueOf(BrList.get(position).getId()));



//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View rowView = inflater.inflate(resourceID, parent, false);

        //(TextView convertView.findViewById(R.id.)).setText(project[position]);

        return convertView;
    }

}
