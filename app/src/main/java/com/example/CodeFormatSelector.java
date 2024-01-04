package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.possale.R;

public class CodeFormatSelector extends AppCompatActivity {
    ImageView img_QR;
    ImageView img_Barcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_format_selector);

        img_Barcode=findViewById(R.id.barcode);
        img_QR=findViewById(R.id.qr);
        img_QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences("CodeFormat", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("CodeFormat","QR");

                editor.apply();
                Intent intent = new Intent(CodeFormatSelector.this, Login.class);
                CodeFormatSelector.this.startActivity(intent);
                CodeFormatSelector.this.finish();

            }
        });
        img_Barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("CodeFormat", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("CodeFormat","Barcode");

                editor.apply();

                Intent intent = new Intent(CodeFormatSelector.this, Login.class);
                CodeFormatSelector.this.startActivity(intent);
                CodeFormatSelector.this.finish();
            }
        });
    }
}