package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.possale.R;
import com.example.*;

public class Settings extends AppCompatActivity {
    TextView tvurl,tvcom,tvbranch,tvgodown;
    Button btnprintersettgs,btnchangesettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SharedPreferences preferences = getSharedPreferences("Url", Context.MODE_PRIVATE);
        SharedPreferences preferences7 = getSharedPreferences("CompanyName", Context.MODE_PRIVATE);
        SharedPreferences preferences4 = getSharedPreferences("GodownName", Context.MODE_PRIVATE);
        SharedPreferences preferences5 = getSharedPreferences("BranchName", Context.MODE_PRIVATE);
        tvurl=(TextView)findViewById(R.id.tvurl);
        tvcom=(TextView)findViewById(R.id.tvcom);
        tvbranch=(TextView)findViewById(R.id.tvbranch);
        tvgodown=(TextView)findViewById(R.id.tvgodown);

        tvurl.setText(preferences.getString("Url",""));
        tvcom.setText(preferences7.getString("CompanyName",""));
        tvbranch.setText(preferences5.getString("BranchName",""));
        tvgodown.setText(preferences4.getString("GodownName",""));

        btnchangesettings=(Button)findViewById(R.id.btn_change);
        btnprintersettgs=(Button)findViewById(R.id.btn_printerconfig);

        btnprintersettgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, PrinterSettings.class);
                // intent.putExtra("crefid", clid);


                Settings.this.startActivity(intent);
                Settings.this.finish();

            }
        });
        btnchangesettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  if(isConnected())
              //  {
                    SharedPreferences preferences6 = getSharedPreferences("Config", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences6.edit();

                    editor.putString("Config", "No");

                    editor.apply();
                    Intent intent = new Intent(Settings.this, setupApiUrl.class);
                    // intent.putExtra("crefid", clid);


                    Settings.this.startActivity(intent);
                    Settings.this.finish();
               // }
              //  else
              //  {
               //     Toast.makeText(Settings.this,"Network connection required to sync data...plz check connectivity", Toast.LENGTH_LONG).show();

              //  }

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(Settings.this, ScanItems.class);
        startActivity(i);
        finish();
    }


    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }


}