package com.example;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.example.General.ICustomCallBack;
import com.example.Model.Company;
import com.example.Model.Sale_Static;
import com.example.Servicess.Companies;
import com.example.possale.R;

import java.util.List;

public class Strart extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strart);
        getWindow().clearFlags(67108864);
        getWindow().addFlags(Integer.MIN_VALUE);
     //   getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        Sale_Static.setInstance();

Sync_Companies();

    }


    public void Sync_Companies() {

        SharedPreferences preferences = getSharedPreferences("Url", Context.MODE_PRIVATE);
        //   Call<List<Company>> call = apiinterface1.Companies(preferences.getString("Url",""));
        String url = preferences.getString("Url", "");

        SharedPreferences preferences6 = getSharedPreferences("Config", Context.MODE_PRIVATE);
        String value = preferences6.getString("Config", "");
        if (value.equals("Config")) {

            Companies companiesObj = new Companies();
            companiesObj.GetCompanies(url + "Companies", new ICustomCallBack() {

                @Override
                public <T> void onSucess(T value) {


                    List<Company> Lst = (List<Company>) value;

                    if (Lst.size() <= 0) {


                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Strart.this);
                        builder1.setMessage("Server Is Not Connected !! Do You Want to Continue ???");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        new Handler().postDelayed(new Runnable() {

                                            @Override
                                            public void run() {
                                                Strart.this.startActivity(new Intent(Strart.this, setupApiUrl.class));
                                                Strart.this.finish();

                                            }
                                        }, 2000);

                                    }
                                });

                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                        new Handler().postDelayed(new Runnable() {

                                            @Override
                                            public void run() {
                                                finishAffinity();
                                                finish();
                                            }
                                        }, 2000);
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();


                    }
                    else
                    {

                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                Strart.this.startActivity(new Intent(Strart.this, ScanItems.class));
                                Strart.this.finish();

                            }
                        }, 2000);


                    }
                }

                @Override
                public void onFailure(String e) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Strart.this);
                    builder1.setMessage("Server Is Not Connected or Something Went wrong  !! Do You Want to Continue ???");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {



                                    new Handler().postDelayed(new Runnable() {

                                        @Override
                                        public void run() {
                                            Strart.this.startActivity(new Intent(Strart.this, setupApiUrl.class));
                                            Strart.this.finish();

                                        }
                                    }, 2000);




                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                    new Handler().postDelayed(new Runnable() {

                                        @Override
                                        public void run() {
                                            finishAffinity();
                                            finish();
                                        }
                                    }, 2000);
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }


            });


        } else {


            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Strart.this.startActivity(new Intent(Strart.this, setupApiUrl.class));
                    Strart.this.finish();

                }
            }, 2000);


        }
    }

}