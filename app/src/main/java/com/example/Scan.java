package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.API.ApiClient;
import com.example.API.ApiInterface;
import com.example.Model.Product;
import com.example.Model.Sale;
import com.example.Model.Sale_Static;
import com.example.possale.R;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class Scan extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {

    Sale SaleObj;
    private ArrayAdapter<Product> adapter;
    BarcodeReader barcodeReader;
    public  static ApiInterface apiinterface1;
String code;
    ListView PrdLst;

    List<Product> Listforinsert = new ArrayList<>();
    List<Product> otherListforinsert = new ArrayList<>();

    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);
        SaleObj= Sale_Static.getInstance();
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get the barcode reader instance
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
    }

    @Override
    public void onScanned(Barcode barcode) {
        // single barcode scanned

       // barcodeReader.playBeep();
        barcodeReader.playBeep();
       code=barcode.displayValue;
        Intent intent = new Intent(Scan.this, ScanItems.class);

        intent.putExtra("Code",code);
        Scan.this.startActivity(intent);
      //  Scan.this.finish();

    }

    @Override
    public void onScannedMultiple(List<Barcode> list) {
        // multiple barcodes scanned
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {
        // barcode scanned from bitmap image
    }

    @Override
    public void onScanError(String s) {
        // scan error
    }

    @Override
    public void onCameraPermissionDenied() {
        // camera permission denied
    }

    public void showDialog(Activity activity){

        final Dialog dialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_listview);

        ImageView btndialog = (ImageView) dialog.findViewById(R.id.closeicon);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();
            }
        });

       ListView listView = (ListView) dialog.findViewById(R.id.listview);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.list_item, R.id.tv, Listforinsert);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

           //   Sync_Data();
            }
        });

        dialog.show();

    }

    public void Sync_Data() {

        SharedPreferences preferences2 = getSharedPreferences("BranchId", Context.MODE_PRIVATE);
        SharedPreferences preferences3 = getSharedPreferences("GodownId", Context.MODE_PRIVATE);
        SharedPreferences preferences = getSharedPreferences("CompanyDBName", Context.MODE_PRIVATE);
        String dbname=preferences.getString("CompanyDBName","");     SharedPreferences preferencesrl = getSharedPreferences("Url", Context.MODE_PRIVATE);
        long branchid=preferences2.getLong("BranchId",0);
        long godownid=preferences3.getLong("GodownId",0);
        SharedPreferences preferences1= getSharedPreferences("Url", Context.MODE_PRIVATE);
        //   Call<List<Company>> call = apiinterface1.Companies(preferences.getString("Url",""));
        String url = preferences1.getString("Url", "");
//code="8710100101218";
      /*  Call<List<Product>> call = apiinterface1.GetProducts(url+"GetProducts",code,dbname,branchid,godownid);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.code() == 200) {
*//*List<Customer> CustList=response.body();
                    if(CustList.size() != 0){*//*


                    if(response.body().size()>0)
                    {
                        for (Product prd : response.body()
                        ) {

                            SaleDtl DtlObj =new SaleDtl();
                            DtlObj.setProductName(prd.getName());

                       //     DtlObj.setRate(prd.getMRP());
                            DtlObj.setQuantity(1.00);
                            //DtlObj.setTotalAmount(prd.getPrice());

                            List<SaleDtl> items = SaleObj.getItems();




                            items.add(DtlObj);
                            SaleObj.setItems(items);


                        }
                        Intent intent = new Intent(Scan.this, ScanItems.class);


                        Scan.this.startActivity(intent);
                       // Scan.this.finish();





                    } else {
                        // movie not found

                        *//*  *//*
                        showNoTicket();

                    }
                }





            }


            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

                Toast.makeText(Scan.this, "Something went wrong...please try again"+code, Toast.LENGTH_LONG).show();

                *//*Intent i = new Intent(Scan.this, .class);
                startActivity(i);
                finish();*//*

            }


        });*/

    }
    private void showNoTicket() {
        Toast.makeText(Scan.this, "No item found with "+code, Toast.LENGTH_LONG).show();


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    //    Sale_Static.setInstance();

        Intent i = new Intent(Scan.this, ScanItems.class);
        i.putExtra("Code","");
        startActivity(i);
      //  finish();
    }

}