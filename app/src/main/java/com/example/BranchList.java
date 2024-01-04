package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.API.ApiClient;
import com.example.API.ApiInterface;
import com.example.Adapter.BranchLstAdapter;
import com.example.Adapter.CountersAdapter;
import com.example.General.ICustomCallBack;
import com.example.Model.Branch;
import com.example.Model.Counter;
import com.example.Servicess.Branches;
import com.example.Servicess.Counters;
import com.example.possale.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BranchList extends AppCompatActivity {
    public  static ApiInterface apiinterface1;

    public static ProgressDialog progress;
    private ArrayAdapter<Branch> adapter;
    ListView brList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_list);
        this.progress = new ProgressDialog(this);
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);
        this.brList = (ListView) findViewById(R.id.BrList);

        BranchList.progress.setMessage("\tLoading...");
        BranchList.progress.setCancelable(false);
        BranchList.progress.show();
        Sync_Data();
        this.brList.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {



                String name = ((TextView) view.findViewById(R.id.Name)).getText().toString();
                String gdid = ((TextView) view.findViewById(R.id.id)).getText().toString();
                SharedPreferences preferences = getSharedPreferences("BranchName", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("BranchName",name);

                editor.apply();


                SharedPreferences preferences1 = getSharedPreferences("BranchId", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = preferences1.edit();

                editor1.putLong("BranchId",Long.valueOf(gdid));

                editor1.apply();
                //  String clid = ((TextView) view.findViewById(R.id.custrefid)).getText().toString();
                //   String cgstn = ((TextView) view.findViewById(R.id.customerGstn)).getText().toString();
                Intent intent = new Intent(BranchList.this, GodownList.class);


                BranchList.this.startActivity(intent);
                BranchList.this.finish();


            }
        });


    }

    public void Sync_Data() {

        SharedPreferences preferences = getSharedPreferences("CompanyDBName", Context.MODE_PRIVATE);
        String dbname=preferences.getString("CompanyDBName","");
        SharedPreferences preferences1= getSharedPreferences("Url", Context.MODE_PRIVATE);
        //   Call<List<Company>> call = apiinterface1.Companies(preferences.getString("Url",""));
        String url = preferences1.getString("Url", "");

        Branches branchesObj=new Branches();
        branchesObj.GetBranches(dbname, url + "Branches", new ICustomCallBack() {



            @Override
            public <T> void onSucess(T value) {

                if(BranchList.progress.isShowing())
                {
                    BranchList.progress.dismiss();
                }
                List<Branch> Lst= (List<Branch>) value;
                if (value != null) {
                    adapter = new BranchLstAdapter(getApplicationContext(), R.layout.branchlist_item,Lst);
                    brList.setAdapter(adapter);


                }
                else
                {

                    if(BranchList.progress.isShowing())
                    {
                        BranchList.progress.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "No Branches Found ...", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(BranchList.this, setupApiUrl.class);


                    BranchList.this.startActivity(intent);
                    BranchList.this.finish();
                }
            }

            @Override
            public void onFailure(String e) {

                if(BranchList.progress.isShowing())
                {
                    BranchList.progress.dismiss();
                }
                Toast.makeText(getApplicationContext(), "Something Went Wrong ...Either Url is Wrong nor Network is not Connected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BranchList.this, setupApiUrl.class);


                BranchList.this.startActivity(intent);
                BranchList.this.finish();
            }


        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(BranchList.this, CompaniesList.class);
        startActivity(i);
        finish();
    }

}


