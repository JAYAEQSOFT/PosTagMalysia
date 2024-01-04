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
import android.widget.EditText;
import android.widget.Toast;

import com.example.possale.R;

public class setupApiUrl extends AppCompatActivity {
    EditText etxturl;
    Button btnsubmit;
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_api_url);


        SharedPreferences preferences = getSharedPreferences("Url", Context.MODE_PRIVATE);
        SharedPreferences preferences1 = getSharedPreferences("CompanyDBName", Context.MODE_PRIVATE);
        SharedPreferences preferences2 = getSharedPreferences("BranchId", Context.MODE_PRIVATE);
        SharedPreferences preferences3 = getSharedPreferences("GodownId", Context.MODE_PRIVATE);
        SharedPreferences preferences4 = getSharedPreferences("GodownName", Context.MODE_PRIVATE);
        SharedPreferences preferences5 = getSharedPreferences("BranchName", Context.MODE_PRIVATE);
        SharedPreferences preferences6 = getSharedPreferences("Config", Context.MODE_PRIVATE);
        SharedPreferences preferences7 = getSharedPreferences("CompanyName", Context.MODE_PRIVATE);
        SharedPreferences preferences8 = getSharedPreferences("CompanyId", Context.MODE_PRIVATE);
        etxturl = (EditText) findViewById(R.id.etxturl);
        btnsubmit = (Button) findViewById(R.id.btnsaveurl);

        url = preferences.getString("Url", "http://192.168.1.202:100/Equal/Pos/");
        etxturl.setText(url);


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  if (isConnected()) {
                    url = etxturl.getText().toString();
                    if (url.trim() != null) {

                        SharedPreferences.Editor editor = preferences.edit();

                        editor.putString("Url", url);

                        editor.apply();

                        Intent intent = new Intent(setupApiUrl.this, CompaniesList.class);
                        // intent.putExtra("crefid", clid);


                        setupApiUrl.this.startActivity(intent);
                        setupApiUrl.this.finish();


                   }
              //  } else {
             //       Toast.makeText(setupApiUrl.this, "Internet connection required to sync data...plz check connectivity", Toast.LENGTH_LONG).show();

            //    }

            }
        });

        SharedPreferences.Editor editor = preferences6.edit();

        editor.putString("Config", "No");

        editor.apply();


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(setupApiUrl.this, ScanItems.class);
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

