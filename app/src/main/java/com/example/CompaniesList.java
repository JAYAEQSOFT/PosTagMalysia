package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.API.ApiClient;
import com.example.API.ApiInterface;

import com.example.Adapter.CompanyLstAdapter;
import com.example.Adapter.CountersAdapter;
import com.example.General.ICustomCallBack;
import com.example.Model.Company;
import com.example.Model.CompanyInfo;
import com.example.Model.Counter;
import com.example.Servicess.Companies;
import com.example.Servicess.Counters;
import com.example.possale.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class
CompaniesList extends AppCompatActivity {
    public  static ApiInterface apiinterface1;
    private ArrayAdapter<Company> adapter;
    ListView comList;
    public static ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies_list);
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);

        this.comList = (ListView) findViewById(R.id.ComList);
        this.progress = new ProgressDialog(this);
        CompaniesList.progress.setMessage("\tLoading...");
        CompaniesList.progress.setCancelable(false);
        CompaniesList.progress.show();
        Sync_Companies();
        this.comList.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {





                String name = ((TextView) view.findViewById(R.id.comName)).getText().toString();
                String Adress = ((TextView) view.findViewById(R.id.comaddress)).getText().toString();
                String phone = ((TextView) view.findViewById(R.id.comphone)).getText().toString();
                String email = ((TextView) view.findViewById(R.id.comemail)).getText().toString();
                String dbname = ((TextView) view.findViewById(R.id.comdbname)).getText().toString();
                String comid = ((TextView) view.findViewById(R.id.id)).getText().toString();

                SharedPreferences preferences = getSharedPreferences("CompanyDBName", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("CompanyDBName",dbname);

                editor.apply();



                SharedPreferences preferences1 = getSharedPreferences("CompanyName", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = preferences1.edit();

                editor1.putString("CompanyName",name);

                editor1.apply();



                SharedPreferences preferences4 = getSharedPreferences("CompanyAdress", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor4 = preferences4.edit();

                editor4.putString("CompanyAdress",name);

                editor4.apply();


                SharedPreferences preferences5 = getSharedPreferences("CompanyPhone", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor5 = preferences5.edit();

                editor5.putString("CompanyPhone",name);

                editor5.apply();

                SharedPreferences preferences6= getSharedPreferences("CompanyEmail", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor6 = preferences6.edit();

                editor6.putString("CompanyEmail",name);

                editor6.apply();


                SharedPreferences preferences2 = getSharedPreferences("CompanyId", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2= preferences2.edit();

                editor2.putLong("CompanyId",Long.valueOf(comid));

                editor2.apply();

                //  String clid = ((TextView) view.findViewById(R.id.custrefid)).getText().toString();
                //   String cgstn = ((TextView) view.findViewById(R.id.customerGstn)).getText().toString();
                Intent intent = new Intent(CompaniesList.this, BranchList.class);


                CompaniesList.this.startActivity(intent);
                CompaniesList.this.finish();


            }
        });




    }

    public void Sync_Companies() {

        SharedPreferences preferences = getSharedPreferences("Url", Context.MODE_PRIVATE);
        //   Call<List<Company>> call = apiinterface1.Companies(preferences.getString("Url",""));
        String url = preferences.getString("Url", "");



        Companies companiesObj=new Companies();
        companiesObj.GetCompanies(url + "Companies", new ICustomCallBack() {

            @Override
            public <T> void onSucess(T value) {

                if(CompaniesList.progress.isShowing())
                {
                    CompaniesList.progress.dismiss();
                }
                List<Company> Lst= (List<Company>) value;
                if (value != null) {
                    adapter = new CompanyLstAdapter(getApplicationContext(), R.layout.companylist_item,Lst);
                    comList.setAdapter(adapter);

                }

                else
                {


                    if(CompaniesList.progress.isShowing())
                    {
                        CompaniesList.progress.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "No Companies Found ...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CompaniesList.this, setupApiUrl.class);


                    CompaniesList.this.startActivity(intent);
                    CompaniesList.this.finish();


                }
            }

            @Override
            public void onFailure(String e) {


                if(CompaniesList.progress.isShowing())
                {
                    CompaniesList.progress.dismiss();
                }
                Toast.makeText(getApplicationContext(), "Something Went Wrong ...Either Url is Wrong nor Network is not Connected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CompaniesList.this, setupApiUrl.class);


                CompaniesList.this.startActivity(intent);
                CompaniesList.this.finish();
            }


        });



    }


}