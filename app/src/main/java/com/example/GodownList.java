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
import com.example.Adapter.GodownLstAdapter;
import com.example.General.ICustomCallBack;
import com.example.Model.Branch;
import com.example.Model.Godown;
import com.example.Servicess.Counters;
import com.example.Servicess.Godowns;
import com.example.possale.R;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class GodownList extends AppCompatActivity {
    public  static ApiInterface apiinterface1;
    public static ProgressDialog progress;

    private ArrayAdapter<com.example.Model.Godown> adapter;
    ListView brList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.progress = new ProgressDialog(this);
        GodownList.progress.setMessage("\tLoading...");
        GodownList.progress.setCancelable(false);
        GodownList.progress.show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_godown_list);
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);
        this.brList = (ListView) findViewById(R.id.GodwnList);
        Sync_Data();
        this.brList.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {



                String name = ((TextView) view.findViewById(R.id.Name)).getText().toString();
                String gdid = ((TextView) view.findViewById(R.id.id)).getText().toString();
                SharedPreferences preferences = getSharedPreferences("GodownName", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("GodownName",name);

                editor.apply();


                SharedPreferences preferences1 = getSharedPreferences("GodownId", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = preferences1.edit();

                editor1.putLong("GodownId",Long.valueOf(gdid));

                editor1.apply();


                SharedPreferences preferences2 = getSharedPreferences("Config", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = preferences2.edit();

                editor2.putString("Config","Config");

                editor2.apply();

               // Toast.makeText(GodownList.this, "Settings Saved ...Now you can start Scanning Items....", Toast.LENGTH_LONG).show();

                //  String clid = ((TextView) view.findViewById(R.id.custrefid)).getText().toString();
                //   String cgstn = ((TextView) view.findViewById(R.id.customerGstn)).getText().toString();
                Intent intent = new Intent(GodownList.this, CountersList.class);


                GodownList.this.startActivity(intent);
                GodownList.this.finish();


            }
        });


    }
    public void Sync_Data() {

        SharedPreferences preferences = getSharedPreferences("CompanyDBName", Context.MODE_PRIVATE);
        String dbname=preferences.getString("CompanyDBName","");
        SharedPreferences preferences1= getSharedPreferences("Url", Context.MODE_PRIVATE);
        //   Call<List<Company>> call = apiinterface1.Companies(preferences.getString("Url",""));
        String url = preferences1.getString("Url", "");

        Godowns godownsObj=new Godowns();
        godownsObj.GetGodowns(dbname, url + "Godowns", new ICustomCallBack() {



            @Override
            public <T> void onSucess(T value) {

                if(GodownList.progress.isShowing())
                {
                    GodownList.progress.dismiss();
                }
                List<Godown> Lst= (List<Godown>) value;
                if (value != null) {
                    adapter = new GodownLstAdapter(getApplicationContext(), R.layout.godownlist_item, Lst);
                    brList.setAdapter(adapter);
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "No Godown's Found ...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GodownList.this, setupApiUrl.class);


                    GodownList.this.startActivity(intent);
                    GodownList.this.finish();
                }
            }

            @Override
            public void onFailure(String e) {

                if(GodownList.progress.isShowing())
                {
                    GodownList.progress.dismiss();
                }
                Toast.makeText(getApplicationContext(), "Something Went Wrong ...Either Url is Wrong nor Network is not Connected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GodownList.this, setupApiUrl.class);


                GodownList.this.startActivity(intent);
                GodownList.this.finish();
            }


        });


    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(GodownList.this, BranchList.class);
        startActivity(i);
        finish();
    }

}


