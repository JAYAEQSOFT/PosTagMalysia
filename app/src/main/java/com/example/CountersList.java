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
import com.example.Adapter.CountersAdapter;
import com.example.General.ICustomCallBack;
import com.example.Model.Counter;


import com.example.Model.CounterLst;
import com.example.Servicess.Counters;
import com.example.possale.R;

import java.util.ArrayList;
import java.util.List;


public class CountersList extends AppCompatActivity {
    public  static ApiInterface apiinterface1;
    List<Counter> Listforinsert = new ArrayList<>();
    public static ProgressDialog progress;
    private ArrayAdapter<Counter> adapter;
    ListView CdrList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counters_list);
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);
        this.CdrList = (ListView) findViewById(R.id.CdrList);

        this.progress = new ProgressDialog(this);
        CountersList.progress.setMessage("\tLoading...");
        CountersList.progress.setCancelable(false);
        CountersList.progress.show();
        Sync_Data();
        this.CdrList.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {



                String name = ((TextView) view.findViewById(R.id.Name)).getText().toString();
                String gdid = ((TextView) view.findViewById(R.id.id)).getText().toString();
                SharedPreferences preferences = getSharedPreferences("CounterName", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("CounterName",name);

                editor.apply();


                SharedPreferences preferences1 = getSharedPreferences("CounterId", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = preferences1.edit();

                editor1.putLong("CounterId",Long.valueOf(gdid));

                editor1.apply();
                //  String clid = ((TextView) view.findViewById(R.id.custrefid)).getText().toString();
                //   String cgstn = ((TextView) view.findViewById(R.id.customerGstn)).getText().toString();
                Intent intent = new Intent(CountersList.this, CodeFormatSelector.class);


                CountersList.this.startActivity(intent);
                CountersList.this.finish();


            }
        });


    }

    public void Sync_Data() {

        SharedPreferences preferences = getSharedPreferences("CompanyDBName", Context.MODE_PRIVATE);
        String dbname = preferences.getString("CompanyDBName", "");
        SharedPreferences preferences1 = getSharedPreferences("Url", Context.MODE_PRIVATE);
        //   Call<List<Company>> call = apiinterface1.Companies(preferences.getString("Url",""));
        String url = preferences1.getString("Url", "");


      Counters countersObj=new Counters();
      countersObj.GetCounters(dbname, url + "Counters", new ICustomCallBack() {



          @Override
          public <T> void onSucess(T value) {

              if(CountersList.progress.isShowing())
              {
                  CountersList.progress.dismiss();
              }
              List<Counter> Lst= (List<Counter>) value;
              if (value != null) {
                  adapter = new CountersAdapter(getApplicationContext(), R.layout.counterlist_item,Lst);
                  CdrList.setAdapter(adapter);


              }
              else
              {

                  if(CountersList.progress.isShowing())
                  {
                      CountersList.progress.dismiss();
                  }
                  Toast.makeText(getApplicationContext(), "No Counter's Found ...", Toast.LENGTH_SHORT).show();
                  Intent intent = new Intent(CountersList.this, setupApiUrl.class);


                  CountersList.this.startActivity(intent);
                  CountersList.this.finish();
              }
          }

          @Override
          public void onFailure(String e) {
              Toast.makeText(getApplicationContext(), "Something Went Wrong ...Either Url is Wrong nor Network is not Connected", Toast.LENGTH_SHORT).show();
              Intent intent = new Intent(CountersList.this, setupApiUrl.class);


              CountersList.this.startActivity(intent);
              CountersList.this.finish();
          }


      });



      /*  Call<List<Counter>> call = apiinterface1.getCounters(url+"Counters",dbname);


        apiinterface1.getCounters(url+"Counters",dbname)

        call.enqueue(new Callback<List<Counter>>() {
            @Override
            public void onResponse(Call<List<Counter>> call, Response<List<Counter>> response) {
                if (response.code() == 200) {
*//*List<Customer> CustList=response.body();
                    if(CustList.size() != 0){*//*
                    Listforinsert=new ArrayList<>();
                    Listforinsert=response.body();



                }
                else
                {
                    String s=response.message();
                }

                if (Listforinsert != null) {
                    adapter = new CountersAdapter(getApplicationContext(), R.layout.companylist_item,Listforinsert);
                    CdrList.setAdapter(adapter);
                }

            }


            @Override
            public void onFailure(Call<List<Counter>> call, Throwable t) {

                String s = t.getMessage();


            }


        });

*/

    }


    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(CountersList.this, CompaniesList.class);
        startActivity(i);
        finish();
    }

}


