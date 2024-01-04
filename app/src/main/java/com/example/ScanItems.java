package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.API.ApiClient;
import com.example.API.ApiInterface;
import com.example.Adapter.CompanyLstAdapter;
import com.example.Adapter.CountersAdapter;
import com.example.Adapter.OtherBatchesAdapter;
import com.example.Adapter.OtherPrdsAdapter;
import com.example.Adapter.OtherUnitsAdapter;
import com.example.Adapter.PrdsAdapter;
import com.example.General.ICustomCallBack;
import com.example.Model.BatchLst;
import com.example.Model.Company;
import com.example.Model.Counter;
import com.example.Model.PosSettings;
import com.example.Model.PriceLevel;
import com.example.Model.PriceLst;
import com.example.Model.Product;
import com.example.Model.Sale;
import com.example.Model.StandardProduct;
import com.example.Model.items;
import com.example.Model.Sale_Static;
import com.example.Servicess.Companies;
import com.example.Servicess.Counters;
import com.example.Servicess.Products;
import com.example.Servicess.Sales;
import com.example.possale.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.CollationElementIterator;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import info.androidhive.barcode.BarcodeReader;

public class ScanItems extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    long    SelectedunitId=0;

    Timer timer = new Timer();
    final long DELAY = 1000; // in ms
  PosSettings posSettings ;
    ListView Lst;
    Sale SaleObj;
    double SavedQty,Qty;
    BarcodeReader barcodeReader;
    String code;
    List<Product> Listforinsert = new ArrayList<>();
    public static ApiInterface apiinterface1;
    private ImageView scangBtn, searchBtn, logoutBtn, settingsBtn, doneBtn,cleardoc,goicon;
    EditText Etbarcodevalue;
    List<PriceLevel> priceLevels;
    String dbname;
    long SelectedBatchId;
    long sequence;
    long branchid, godownid ,counterid,userid;
    String username;
    String url;
    Handler messageHandler = new Handler();
    Double Rate = 0.00;
    public static ProgressDialog progress;
    String wcode="";
    double qty=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);
        this.progress = new ProgressDialog(this);
        setContentView(R.layout.activity_scan_items);
        SaleObj = Sale_Static.getInstance();
        ScanItems.progress.setMessage("\tLoading...");
        ScanItems.progress.setCancelable(false);
         ScanItems.progress.show();





        SharedPreferences preferences2 = getSharedPreferences("BranchId", Context.MODE_PRIVATE);
        SharedPreferences preferences3 = getSharedPreferences("GodownId", Context.MODE_PRIVATE);
        SharedPreferences preferences = getSharedPreferences("CompanyDBName", Context.MODE_PRIVATE);
        SharedPreferences preferences1 = getSharedPreferences("Url", Context.MODE_PRIVATE);
        SharedPreferences preferences4 = getSharedPreferences("CounterId", Context.MODE_PRIVATE);
        SharedPreferences preferenceUserName = getSharedPreferences("Username", Context.MODE_PRIVATE);
        SharedPreferences preferenceUserId = getSharedPreferences("UserId", Context.MODE_PRIVATE);


          url = preferences1.getString("Url", "");
        dbname = preferences.getString("CompanyDBName", "");
        SharedPreferences preferencesrl = getSharedPreferences("Url", Context.MODE_PRIVATE);
        branchid = preferences2.getLong("BranchId", 0);
        godownid = preferences3.getLong("GodownId", 0);
        counterid=preferences4.getLong("CounterId",0);
        username=preferenceUserName.getString("Username","");
        userid=preferenceUserId.getLong("UserId",0);



        Lst = (ListView) findViewById(R.id.itemlist);
        scangBtn = findViewById(R.id.scanicon);
        settingsBtn = findViewById(R.id.settings);
        searchBtn = findViewById(R.id.searchicon);
        logoutBtn = findViewById(R.id.logout);
        doneBtn = findViewById(R.id.done);
        cleardoc=findViewById(R.id.cleardoc);
        goicon=findViewById(R.id.goicon);
        Etbarcodevalue = findViewById(R.id.barcodevalue);
        Etbarcodevalue.requestFocus();
        this.code = getIntent().getStringExtra("Code");
        try {
            this.sequence = getPriceLevel();
            LoadItems();
        } catch (Exception e) {
            e.getMessage();
        }
        String str = this.code;
        if (str != null && !str.equals("")) {
            progress.setMessage("\tLoading...");
            progress.setCancelable(false);
            Sync_Data();
        }

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showDialog();
                //Sync_Data();
            }
        });
        goicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Etbarcodevalue.getText().toString().equals(null) && !Etbarcodevalue.getText().toString().equals("")) {
                    code = Etbarcodevalue.getText().toString();


                    Sync_Data();
                }
                else {
                    code=null;
                }

            }
        });
        cleardoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sale_Static.setInstance();
                Intent intent = new Intent(ScanItems.this, ScanItems.class);


                ScanItems.this.startActivity(intent);
                ScanItems.this.finish();
            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
doneBtn.setEnabled(false);
Save();

            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScanItems.this, Login.class);


                ScanItems.this.startActivity(intent);
                ScanItems.this.finish();
            }
        });
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences6 = getSharedPreferences("Config", Context.MODE_PRIVATE);
                String value = preferences6.getString("Config", "");
                if (value.equals("Config")) {
                    Intent intent = new Intent(ScanItems.this, Settings.class);


                    ScanItems.this.startActivity(intent);
                    ScanItems.this.finish();

                    // Sync_Data();
                } else {


                    Intent intent = new Intent(ScanItems.this, setupApiUrl.class);


                    ScanItems.this.startActivity(intent);
                    ScanItems.this.finish();

                }
            }
        });

      /*  Etbarcodevalue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }
            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
*//*if(!Etbarcodevalue.getText().toString().equals(null) && !Etbarcodevalue.getText().toString().equals("")) {
    code = Etbarcodevalue.getText().toString();
    Sync_Data();
}
else {
    code=null;
}*//*
                if (s.length() >= 3) {

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            // TODO: do what you need here (refresh list)
                            // you will probably need to use
                            // runOnUiThread(Runnable action) for some specific
                            // actions

                            if(!Etbarcodevalue.getText().toString().equals(null) && !Etbarcodevalue.getText().toString().equals("")) {
                                code = Etbarcodevalue.getText().toString();
                                Sync_Data();
                            }
                            else {
                                code=null;
                            }
                        }

                    }, DELAY);
                }
            }


        });
*/

     /*  Etbarcodevalue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (Etbarcodevalue.getRight() - Etbarcodevalue.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                      //  Etbarcodevalue.setText("");
                        if(!Etbarcodevalue.getText().toString().equals(null) && !Etbarcodevalue.getText().toString().equals("")) {
                            code = Etbarcodevalue.getText().toString();


                            Sync_Data();
                        }
                        else {
                            code=null;
                        }

                        return true;
                    }
                }
                return false;
            }


        });
*/
       Etbarcodevalue.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    switch (keyCode) {

                        case KeyEvent.KEYCODE_ENTER:

                            code=Etbarcodevalue.getText().toString().trim();

                            Sync_Data();

                            //  progressBar.setVisibility(View.VISIBLE);

                            return true;

                        default:
                            break;
                    }
                }

                return false;
            }

        });



        scangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Sync_Data();
                SharedPreferences preferences6 = getSharedPreferences("Config", Context.MODE_PRIVATE);
                String value = preferences6.getString("Config", "");
                if (value.equals("Config")) {

                    Intent intent = new Intent(ScanItems.this, Scan.class);


                    ScanItems.this.startActivity(intent);
                  //  ScanItems.this.finish();

                   //Sync_Data();
                } else {


                    Intent intent = new Intent(ScanItems.this, setupApiUrl.class);


                    ScanItems.this.startActivity(intent);
                    ScanItems.this.finish();

                }
            }

        });


    }
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            finish();
        }
        else{
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }


    }


    public void LoadItems() {

        try {


            List<items> dtlList;
            List<items> newdtlList=new ArrayList<>(

            );
            if (SaleObj.getItems().size() > 0) {
                dtlList = SaleObj.getItems();
               /* int length=dtlList.size();
                int value=length-1;
                while(value<=0)
                {
                    newdtlList.add(dtlList.get(value));
                    value--;
                }*/
                PrdsAdapter adapter = new PrdsAdapter(getApplicationContext(), R.layout.item_my_prds, dtlList);
                Lst = (ListView) findViewById(R.id.itemlist);
                Lst.setAdapter(adapter);
                Lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String prdid = ((TextView) view.findViewById(R.id.id)).getText().toString();
                    String    prdName = ((TextView) view.findViewById(R.id.name)).getText().toString();
                        Long pid=Long.valueOf(prdid);


                        final Dialog dialog = new Dialog(ScanItems.this);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.qtypopup);




                     TextView   prdname = (TextView) dialog.findViewById(R.id.prdName);

//Layout controls

                     EditText   inputQty = (EditText) dialog.findViewById(R.id.userInputQty);


                   ImageView     btnsave = (ImageView) dialog.findViewById(R.id.saveicon);
                  ImageView       btncancel = (ImageView) dialog.findViewById(R.id.closeicon);
                     //   btncancel.setText("Delete");
                       // inputQty.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(5, 3)});

                        //  inputQty.requestFocus();
                        inputQty.setSelectAllOnFocus(true);

                        prdname.setText(prdName);



                        SaleObj=Sale_Static.getInstance();
                        if(SaleObj!=null)
                        {
                            List<items> lst=SaleObj.getItems();
                            if(lst!=null)
                            {
                                for (items dtl :lst)
                                {
                                    if(dtl.getProductId()==pid)
                                    {


                                        String sqty =String.format("%.3f",dtl.getQuantity());
                                        inputQty.setText(sqty);
                                        SavedQty=dtl.getQuantity();

                                        Qty = dtl.getQuantity();
                                    }
                                }
                            }
                        }


                        inputQty.addTextChangedListener(new TextWatcher() {
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                String str = "";
                                if(!inputQty.getText().toString().equals(".")&&!inputQty.getText().toString().equals("")&&!inputQty.getText().toString().trim().equals("")) {
                                    Qty = Double.valueOf(inputQty.getText().toString());
                                }
                            }

                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            public void afterTextChanged(Editable s) {
                            }
                        });

                        ImageView Closeicon = (ImageView) dialog.findViewById(R.id.closeicon);

                        Closeicon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(android.view.View v) {
                                dialog.dismiss();
                            }



                            
                        });
                        ImageView saveicon = (ImageView) dialog.findViewById(R.id.saveicon);

                        saveicon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(android.view.View v) {

                                //if(TripRefId == InwardObj.getTripRefId()){





                                    if(Qty>0) {
                                        if(SaleObj!=null)
                                        {
                                            List<items> lst=SaleObj.getItems();
                                            if(SaleObj.getItems()!=null)
                                            {
                                                for (items dtl :SaleObj.getItems())
                                                {
                                                    if(dtl.getProductId()==pid)
                                                    {
                                                        dtl.setQuantity(Qty);
                                                    }
                                                }
                                            }
                                        }



                                     List<items>   Items=SaleObj.getItems();
                                        PrdsAdapter adapter = new PrdsAdapter(getApplicationContext(), R.layout.item_my_prds, Items);
                                        Lst = (ListView) findViewById(R.id.itemlist);
                                        Lst.setAdapter(adapter);
                                        dialog.dismiss();



                                    }
                                    else
                                    {
                                        Toast.makeText(ScanItems.this, "Couldnt save with zero qty....", Toast.LENGTH_LONG).show();

                                    }



//                            if(Qty>0) {
//                                UpdateItem();
//                            }
//                            else
//                            {
//                                Toast.makeText(StockInward.this, "Couldnt save with zero qty....", Toast.LENGTH_LONG).show();
//
//                            }

//                        }
//                        else{
//
//                            Toast.makeText(StockInward.this, "Sorry you can't edit Item", Toast.LENGTH_LONG).show();
//                        }


                            }
                        });
                        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
                        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.40);

                        dialog.getWindow().setLayout(width, height);

                     dialog.show();


                    }
                });

            }

        } catch (Exception e) {
            String s = e.getMessage();
        }


        if (ScanItems.progress.isShowing()) {
            ScanItems.progress.dismiss();


        }
        Etbarcodevalue.setText("");


    }

    public void showDialog() {






        final Dialog dialog = new Dialog(ScanItems.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_listview);
        EditText txtvalue = (EditText) dialog.findViewById(R.id.value);
        txtvalue.requestFocus();
        ListView listView = (ListView) dialog.findViewById(R.id.listview);


        ImageView btndialog = (ImageView) dialog.findViewById(R.id.closeicon);

        ImageView Search = (ImageView) dialog.findViewById(R.id.searchicon);
        Search.setVisibility(View.GONE);

        txtvalue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() >= 3) {

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            // TODO: do what you need here (refresh list)
                            // you will probably need to use
                            // runOnUiThread(Runnable action) for some specific
                            // actions

                            if(!txtvalue.getText().toString().equals(null) && !txtvalue.getText().toString().equals("")) {
                                Products prdObj = new Products();
                                try {
                                    String value = txtvalue.getText().toString();
                                    Calendar calendar = Calendar.getInstance();
                                    SimpleDateFormat mdformat = new SimpleDateFormat("dd-MMM-yyyy");
                                    value=value.replace(" ","%");
                                    String date = mdformat.format(calendar.getTime());

                                    prdObj.ListProductBatchesByUnitNew(url + "ListProductBatchesByUnitNew", dbname, value + "%", "%", false, "SA", -1, date, branchid, false, posSettings.isSHOW_VALID_STOCK_ITEMS(), godownid, posSettings.getPRODUCT_SEARCH_LIMIT(), posSettings.getSALES_BLOCK_EXPIRED_ITEMS(), 0, posSettings.isDISPLAY_STOCK_IN_SALES_RELATED_SEARCHES(), posSettings.isMRP_SEARCH_WITH_NAME(), new ICustomCallBack() {


                                        @Override
                                        public <T> void onSucess(T value) {
                                            List<Product> Lst = (List<Product>) value;
                                            Listforinsert = Lst;


                                            OtherPrdsAdapter arrayAdapter = new OtherPrdsAdapter(ScanItems.this, R.layout.list_item, Listforinsert,sequence);
                                            listView.setAdapter(arrayAdapter);

                                        }

                                        @Override
                                        public void onFailure(String e) {
                                            Toast.makeText(ScanItems.this, e, Toast.LENGTH_LONG);
                                        }


                                    });
                                } catch (Exception e) {
                                    String s = e.getMessage();
                                }

                            }

                        }

                    }, DELAY);
                }


               else
                {
                                  }



            }
        });
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String batchid = ((TextView) view.findViewById(R.id.id)).getText().toString();
                SelectedBatchId = Long.valueOf(batchid);
                Sync_SelectedPrd();

                dialog.dismiss();
            }
        });

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);

        dialog.getWindow().setLayout(width, height);

        dialog.show();

    }

    public void Sync_Data() {
//code="##31950500";
           boolean weighscaleEnabled =true;


        if (code == null) {
            code = Etbarcodevalue.getText().toString().trim();

        }


//code="$0000325700005";

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MMM-yyyy");
        String date = mdformat.format(calendar.getTime());
        Products prds = new Products();

        if (code != null) {
            code=code.trim();

            if (posSettings.isWEIGHING_SCALE_ENABLE()) {
                String codeidentificationno =posSettings.getWEIGHING_SCALE_CODE_INDENTIFICATION_CHARACTER();
                int totalcharactersinbatchcode = posSettings.getWEIGHING_SCALE_TOTAL_CHARACTER();
                int startposition = posSettings.getWEIGHING_SCALE_CODE_CHARACTER_START_FROM();
                int length = 8;
                int priceorqtystartposition = 9;
                int priceqtylength = posSettings.getWEIGHING_SCALE_PRICE_QTY_CHARACTER_LENGTH();
                int decimalstartposition = 0;
                String SplitCode = "";
                String[] CodeArray;
                if (code.startsWith(codeidentificationno)) {

                if (code.length() > totalcharactersinbatchcode) {
                    CodeArray = code.toString().trim().split("\\$");

                } else {
                    CodeArray = new String[2];
                    CodeArray[1] = code.replace("$","").trim();

                }
                for(int i=1;i<=CodeArray.length-1;i++)
                {

                    int j=i;
                    wcode = CodeArray[i].substring(startposition-1,length);




                        try {


                            prds.GetProductByWeighingScaleCode(url + "GetProductByWeighingScaleCode", dbname, wcode, new ICustomCallBack() {


                                @Override
                                public <T> void onSucess(T value) {
                                    Product prd = (Product) value;

                                    if (prd != null) {


                                        long id = prd.getId();


                                        Products prds = new Products();
                                        try {


                                            prds.ProductBatchesByProductID(url + "ProductBatchesByProductID", dbname, id, false, new ICustomCallBack() {


                                                @Override
                                                public <T> void onSucess(T value) {
                                                    List<BatchLst> Lst = (List<BatchLst>) value;

                                                  /*  if (Lst != null) {


                                                                SaleDtl DtlObj = new SaleDtl();
                                                                DtlObj.setProductName(prd.getName());

                                                               Double Rate=0.00;
                                                                switch ((int) sequence) {
                                                                    case 1:
                                                                        Rate = Lst.get(0).getRate1();

                                                                        break;
                                                                    case 2:
                                                                        Rate = Lst.get(0).getRate2();
                                                                        break;
                                                                    case 3:
                                                                        Rate = Lst.get(0).getRate3();
                                                                        break;
                                                                    case 4:
                                                                        Rate = Lst.get(0).getRate4();
                                                                        break;
                                                                    case 5:
                                                                        Rate = Lst.get(0).getRate5();
                                                                        break;


                                                                }
                                                                DtlObj.setPrice(Rate);

                                                                String QtyType =prd.getWeighScaleUnit();



                                                                Double qty = Double.valueOf(CodeArray[j].substring(priceorqtystartposition));
                                                                if (QtyType.equals("Weight")) {
                                                                    DtlObj.setQuantity(qty / 1000);

                                                                } else if (QtyType.equals("Nos")) {
                                                                    DtlObj.setQuantity(qty);
                                                                }

                                                                List<SaleDtl> items = SaleObj.getItems();
                                                                items.add(DtlObj);
                                                                SaleObj.setItems(items);







                                                    }*/


                                                    if (Lst.size() <= 1) {


                                                        Products prds = new Products();
                                                        prds.ListProductBatchesByUnitNew(url + "ListProductBatchesByUnitNew", dbname, "%", Lst.get(0).getBatchCode(), false, "SA", -1, date, branchid, false, posSettings.isSHOW_VALID_STOCK_ITEMS(), godownid, posSettings.getPRODUCT_SEARCH_LIMIT(), posSettings.getSALES_BLOCK_EXPIRED_ITEMS(), 0, posSettings.isDISPLAY_STOCK_IN_SALES_RELATED_SEARCHES(), posSettings.isMRP_SEARCH_WITH_NAME(), new ICustomCallBack() {


                                                            @Override
                                                            public <T> void onSucess(T value) {
                                                                List<Product> Lst = (List<Product>) value;
                                                                Listforinsert = Lst;
                                                                items DtlObj = new items();
                                                                DtlObj.setProductName(Listforinsert.get(0).getName());
                                                                DtlObj.setProductId(Listforinsert.get(0).getId());
                                                                DtlObj.setBatchCode(code);
                                                                DtlObj.setBatchId(Listforinsert.get(0).getBatchId());
                                                                DtlObj.setUnitId(Listforinsert.get(0).getUnitId());



                                                                switch ((int) sequence) {
                                                                    case 1:
                                                                        Rate = Lst.get(0).getRate1();

                                                                        break;
                                                                    case 2:
                                                                        Rate = Lst.get(0).getRate2();
                                                                        break;
                                                                    case 3:
                                                                        Rate = Lst.get(0).getRate3();
                                                                        break;
                                                                    case 4:
                                                                        Rate = Lst.get(0).getRate4();
                                                                        break;
                                                                    case 5:
                                                                        Rate = Lst.get(0).getRate5();
                                                                        break;


                                                                }
                                                                DtlObj.setPrice(Rate);

                                                                String QtyType =prd.getWeighScaleUnit();



                                                                Double qty = Double.valueOf(CodeArray[j].substring(priceorqtystartposition));
                                                                if (QtyType.equals("Weight")) {
                                                                    DtlObj.setQuantity(qty / 1000);
                                                                    DtlObj.setQtyy(qty / 1000);
                                                                } else if (QtyType.equals("Nos")) {
                                                                    DtlObj.setQuantity(qty);
                                                                    DtlObj.setQtyy(qty);
                                                                }


                                                                List<items> items = SaleObj.getItems();
                                                               int length= items.size();
                                                            //   DtlObj.setSeq(length+1);
                                                                items.add(0,DtlObj);

                                                                SaleObj.setItems(items);
                                                                LoadItems();

                                                            }

                                                            @Override
                                                            public void onFailure(String e) {
                                                                Toast.makeText(ScanItems.this, e, Toast.LENGTH_LONG);
                                                                if (ScanItems.progress.isShowing()) {
                                                                    ScanItems.progress.dismiss();


                                                                }

                                                            }


                                                        });


                                                    } else {


                                                        showBatchItems(code,0);
                                                    }

                                                }

                                                @Override
                                                public void onFailure(String e) {
                                                    Toast.makeText(ScanItems.this, "Something Went Wrong ...Please Try Again", Toast.LENGTH_LONG);
                                                    if (ScanItems.progress.isShowing()) {
                                                        ScanItems.progress.dismiss();


                                                    }

                                                }


                                            });
                                        } catch (Exception e) {
                                            String s = e.getMessage();

                                            Toast.makeText(ScanItems.this, "Something Went Wrong ...Please Try Again", Toast.LENGTH_LONG);
                                            if (ScanItems.progress.isShowing()) {
                                                ScanItems.progress.dismiss();


                                            }

                                        }


                                    } else {
                                        CheckRePackingCode(code);
                                    }

                                }

                                @Override
                                public void onFailure(String e) {
                                    Toast.makeText(ScanItems.this, e, Toast.LENGTH_LONG);
                                    Toast.makeText(getApplicationContext(), "Something went wrong !!! Either Item not found or server error.", Toast.LENGTH_SHORT).show();

                                    if (ScanItems.progress.isShowing()) {
                                        ScanItems.progress.dismiss();


                                    }

                                }


                            });
                        } catch (Exception e) {
                            String s = e.getMessage();
                        }


                }

                    LoadItems();


                } else {
                    CheckRePackingCode(code);
                }

            } else {
                CheckRePackingCode(code);
            }
        }


       // Etbarcodevalue.setText("");

    }



    public  void CheckRePackingCode (String Code)
    {

         String ActualCode=Code;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MMM-yyyy");
        String date = mdformat.format(calendar.getTime());
        Products prds = new Products();
        //Repacking Code

        String start=code.substring(0, 1);
        if (code.substring(0, 1).equals("#") ){
            code = code.substring(1,6);
            code = code.replace("#", "");
            try {


                prds.GetProductBatchByCode(url + "GetProductBatchByCode", dbname, code, new ICustomCallBack() {


                    @Override
                    public <T> void onSucess(T value) {
                        BatchLst batch = (BatchLst) value;

                        if (batch != null) {

                           /* if (prd.getBatchLst().size() <= 1) {*/





                                Products prds = new Products();


                            try {


                                prds.GetProductBatchListByBatchCode(url + "GetProductBatchListByBatchCode", dbname, batch.getBatchCode(), new ICustomCallBack() {


                                    @Override
                                    public <T> void onSucess(T value) {
                                        List<BatchLst> batchLst = (List<BatchLst>) value;

                                        if (batchLst != null) {

                                            if (batchLst.size() <= 1) {


                                                Products prds = new Products();
                                                prds.ListProductBatchesByUnitNew(url + "ListProductBatchesByUnitNew", dbname, "%", batchLst.get(0).getBatchCode(), false, "SA", -1, date, branchid, false, posSettings.isSHOW_VALID_STOCK_ITEMS(), godownid, posSettings.getPRODUCT_SEARCH_LIMIT(), posSettings.getSALES_BLOCK_EXPIRED_ITEMS(), 0, posSettings.isDISPLAY_STOCK_IN_SALES_RELATED_SEARCHES(), posSettings.isMRP_SEARCH_WITH_NAME(), new ICustomCallBack() {

                                                    @Override
                                                    public <T> void onSucess(T value) {
                                                        List<Product> Lst = (List<Product>) value;
                                                        Listforinsert = Lst;
                                                        items DtlObj = new items();

                                                        DtlObj.setProductName(Listforinsert.get(0).getName());
                                                        DtlObj.setProductId(Listforinsert.get(0).getId());
                                                        DtlObj.setBatchCode(code);
                                                        DtlObj.setBatchId(Listforinsert.get(0).getBatchId());
                                                        DtlObj.setUnitId(Listforinsert.get(0).getUnitId());




                                                        switch ((int) sequence) {
                                                            case 1:
                                                                Rate = Lst.get(0).getRate1();

                                                                break;
                                                            case 2:
                                                                Rate = Lst.get(0).getRate2();
                                                                break;
                                                            case 3:
                                                                Rate = Lst.get(0).getRate3();
                                                                break;
                                                            case 4:
                                                                Rate = Lst.get(0).getRate4();
                                                                break;
                                                            case 5:
                                                                Rate = Lst.get(0).getRate5();
                                                                break;


                                                        }
                                                        DtlObj.setPrice(Rate);
                                                        //  DtlObj.setPrice(150);


                                                        Long QtyType = Long.valueOf(ActualCode.substring(6, 7));
                                                        String strqty = ActualCode.substring(7);
                                                        Double qty = Double.valueOf(strqty);
                                                        if (QtyType == 0) {
                                                            DtlObj.setQuantity(qty / 1000);
                                                            DtlObj.setQtyy(qty / 1000);
                                                        } else if (QtyType == 1) {
                                                            DtlObj.setQuantity(qty);
                                                            DtlObj.setQtyy(qty);
                                                        }




                                                        List<items> items = SaleObj.getItems();
                                                        int length= items.size();
                                                        items.add(0,DtlObj);

                                                        SaleObj.setItems(items);

                                                        LoadItems();

                                                    }

                                                    @Override
                                                    public void onFailure(String e) {
                                                        Toast.makeText(ScanItems.this, e, Toast.LENGTH_LONG);

                                                        Toast.makeText(getApplicationContext(), "Something went wrong !!! Either Item not found or server error.", Toast.LENGTH_SHORT).show();
                                                        if (ScanItems.progress.isShowing()) {
                                                            ScanItems.progress.dismiss();


                                                        }

                                                    }


                                                });


                                            } else {


                                                showBatchItems(code,0);
                                            }
                                        }


                                    }

                                    @Override
                                    public void onFailure(String e) {
                                 //       Toast.makeText(ScanItems.this, e, Toast.LENGTH_LONG);
                                      //  Toast.makeText(ScanItems.this, "Something Went Wrong ...Please Try Again", Toast.LENGTH_LONG);
                                        Toast.makeText(getApplicationContext(), "Something went wrong !!! Either Item not found or server error.", Toast.LENGTH_SHORT).show();
                                        Etbarcodevalue.setText("");
                                        if (ScanItems.progress.isShowing()) {
                                            ScanItems.progress.dismiss();


                                        }
                                    }


                                });
                            } catch (Exception e) {
                                String s = e.getMessage();
                                Toast.makeText(getApplicationContext(), "Something went wrong !!! Either Item not found or server error.", Toast.LENGTH_SHORT).show();

                                Etbarcodevalue.setText("");
                                if (ScanItems.progress.isShowing()) {
                                    ScanItems.progress.dismiss();


                                }

                                //       Toast.makeText(ScanItems.this, "Something Went Wrong ...Please Try Again", Toast.LENGTH_LONG);

                            }




                        }

                    }

                    @Override
                    public void onFailure(String e) {
                      //  Toast.makeText(ScanItems.this, e, Toast.LENGTH_LONG);
                        Toast.makeText(getApplicationContext(), "Something went wrong !!! Either Item not found or server error.", Toast.LENGTH_SHORT).show();

                        if (ScanItems.progress.isShowing()) {
                            ScanItems.progress.dismiss();


                        }
                        Etbarcodevalue.setText("");
                    }


                });
            } catch (Exception e) {
                String s = e.getMessage();
            }

        } else {//Normal Code

         CheckNormalCode(code);

        }
    }


    public  void CheckNormalCode(String code)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MMM-yyyy");
        String date = mdformat.format(calendar.getTime());

        Products prds=new Products();
        try {


            prds.GetProductBatchListByBatchCode(url + "GetProductBatchListByBatchCode", dbname, code, new ICustomCallBack() {


                @Override
                public <T> void onSucess(T value) {
                    List<BatchLst> prd = (List<BatchLst>) value;

                    if (prd.size()!=0) {


                        if (prd.size() == 1) {


                            Products prds = new Products();
                            prds.ListProductBatchesByUnitNew(url + "ListProductBatchesByUnitNew", dbname, "%", code, false, "SA", -1, date, branchid, false, posSettings.isSHOW_VALID_STOCK_ITEMS(), godownid, posSettings.getPRODUCT_SEARCH_LIMIT(), posSettings.getSALES_BLOCK_EXPIRED_ITEMS(), 0, posSettings.isDISPLAY_STOCK_IN_SALES_RELATED_SEARCHES(), posSettings.isMRP_SEARCH_WITH_NAME(), new ICustomCallBack() {


                                @Override
                                public <T> void onSucess(T value) {
                                    List<Product> Lst = (List<Product>) value;
                                    if(Lst.size()!=0) {
                                        Listforinsert = Lst;
                                        items DtlObj = new items();

                                        DtlObj.setProductName(Listforinsert.get(0).getName());
                                        DtlObj.setProductId(Listforinsert.get(0).getId());
                                        DtlObj.setBatchCode(code);
                                        DtlObj.setBatchId(Listforinsert.get(0).getBatchId());
                                        DtlObj.setUnitId(Listforinsert.get(0).getUnitId());


                                        switch ((int) sequence) {
                                            case 1:
                                                Rate = Lst.get(0).getRate1();

                                                break;
                                            case 2:
                                                Rate = Lst.get(0).getRate2();
                                                break;
                                            case 3:
                                                Rate = Lst.get(0).getRate3();
                                                break;
                                            case 4:
                                                Rate = Lst.get(0).getRate4();
                                                break;
                                            case 5:
                                                Rate = Lst.get(0).getRate5();
                                                break;


                                        }
                                        DtlObj.setPrice(Rate);
                                        //  DtlObj.setPrice(150);


                                        DtlObj.setQuantity(1);
                                        DtlObj.setQtyy(1);

                                        List<items> items = SaleObj.getItems();
                                        int length= items.size();
                                        items.add(0,DtlObj);

                                        SaleObj.setItems(items);

                                        LoadItems();
                                    }
                                    else
                                    {
                                        CheckStandardBarcode(code);
                                    }

                                }

                                @Override
                                public void onFailure(String e) {
                                    Toast.makeText(ScanItems.this, e, Toast.LENGTH_LONG);

                                    Toast.makeText(getApplicationContext(), "Something went wrong !!! Either Item not found or server error.", Toast.LENGTH_SHORT).show();

                                    if (ScanItems.progress.isShowing()) {
                                        ScanItems.progress.dismiss();


                                    }

                                }


                            });


                        } else {



                            showBatchItems(code,0);
                        }
                    } else {

                        CheckStandardBarcode(code);


                    }


                }

                @Override
                public void onFailure(String e) {
                  //  Toast.makeText(ScanItems.this, e, Toast.LENGTH_LONG);
                  //  Toast.makeText(ScanItems.this, "Something Went Wrong ...Please Try Again", Toast.LENGTH_LONG);
                    Toast.makeText(getApplicationContext(), "Something went wrong !!! Either Item not found or server error.", Toast.LENGTH_SHORT).show();

                    if (ScanItems.progress.isShowing()) {
                        ScanItems.progress.dismiss();


                    }
                    Etbarcodevalue.setText("");
                }


            });
        } catch (Exception e) {
            String s = e.getMessage();
          //  Toast.makeText(ScanItems.this, "Something Went Wrong ...Please Try Again", Toast.LENGTH_LONG);
            Toast.makeText(getApplicationContext(), "Something went wrong !!! Either Item not found or server error.", Toast.LENGTH_SHORT).show();

            if (ScanItems.progress.isShowing()) {
                ScanItems.progress.dismiss();


            }
            Etbarcodevalue.setText("");

        }

    }


    public  void CheckStandardBarcode(String code)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MMM-yyyy");
        String date = mdformat.format(calendar.getTime());
        Products prds=new Products();
        try {


            prds.GetProductByStandardMapBatchCode(url + "GetProductByStandardMapBatchCode", dbname, code, new ICustomCallBack() {


                @Override
                public <T> void onSucess(T value) {
                   StandardProduct prd = (StandardProduct) value;



                        if (prd!=null) {
                            long unit =prd.getUnitid();


                            try {


                                prds.GetBatchListByProductId(url + "GetBatchListByProductId", dbname, prd.getProductId(), new ICustomCallBack() {


                                    @Override
                                    public <T> void onSucess(T value) {
                                        List<BatchLst> Lst = (List<BatchLst>) value;





                                        if (Lst.size() ==1) {


                                            Products prds = new Products();
                                            try {


                                                prds.GetProductByBatchIdasProduct(url + "GetProductByBatchIdasProduct", dbname, Lst.get(0).getBatchId(), new ICustomCallBack() {


                                                    @Override
                                                    public <T> void onSucess(T value) {
                                                        Product prd = (Product) value;

                                                        if (prd != null) {



                                                                items DtlObj = new items();
                                                                DtlObj.setProductName(prd.getName());


                                                                DtlObj.setProductId(prd.getId());
                                                                DtlObj.setBatchCode(Lst.get(0).getBatchCode());
                                                                DtlObj.setBatchId(Lst.get(0).getBatchId());
                                                                DtlObj.setUnitId(prd.getUnitId());
                                                            long SaleunitId=0;
                                                                if(unit!=0) {
                                                                    SaleunitId=unit;
                                                                }
                                                                else
                                                                {
                                                                SaleunitId = prd.getSalesUnit();
                                                                 }

                                                                List<PriceLst> priceLsts=new ArrayList<>();
                                                                priceLsts=prd.getBatchLst().get(0).getPriceLsts();
                                                                for(PriceLst Lst : priceLsts) {

                                                                    if (Lst.getUnitId() == SaleunitId) {
                                                                        switch ((int) sequence) {
                                                                            case 1:
                                                                                Rate = Lst.getRate1();

                                                                                break;
                                                                            case 2:
                                                                                Rate = Lst.getRate2();
                                                                                break;
                                                                            case 3:
                                                                                Rate = Lst.getRate3();
                                                                                break;
                                                                            case 4:
                                                                                Rate = Lst.getRate4();
                                                                                break;
                                                                            case 5:
                                                                                Rate = Lst.getRate5();
                                                                                break;


                                                                        }
                                                                    }
                                                                }
                                                                DtlObj.setPrice(Rate);
                                                                //  DtlObj.setPrice(150);
                                                            DtlObj.setQuantity(1);

                                                            DtlObj.setQtyy(1);
                                                                List<items> items = SaleObj.getItems();
                                                          //  int length= items.size();
                                                            items.add(0,DtlObj);

                                                                SaleObj.setItems(items);

                                                                LoadItems();



                                                        }

                                                    }

                                                    @Override
                                                    public void onFailure(String e) {
                                                        Toast.makeText(ScanItems.this, e, Toast.LENGTH_LONG);
                                                        if (ScanItems.progress.isShowing()) {
                                                            ScanItems.progress.dismiss();


                                                        }

                                                    }


                                                });
                                            } catch (Exception e) {
                                                String s = e.getMessage();
                                                if (ScanItems.progress.isShowing()) {
                                                    ScanItems.progress.dismiss();


                                                }

                                            }

                                        } else {


                                            showBatchItems("",prd.getProductId());
                                        }

                                    }

                                    @Override
                                    public void onFailure(String e) {
                                        Toast.makeText(ScanItems.this, "Something Went Wrong ...Please Try Again", Toast.LENGTH_LONG);
                                        if (ScanItems.progress.isShowing()) {
                                            ScanItems.progress.dismiss();


                                        }

                                    }


                                });
                            } catch (Exception e) {
                                String s = e.getMessage();

                                Toast.makeText(ScanItems.this, "Something Went Wrong ...Please Try Again", Toast.LENGTH_LONG);
                                if (ScanItems.progress.isShowing()) {
                                    ScanItems.progress.dismiss();


                                }

                            }





                    } else {


                        Toast.makeText(getApplicationContext(), "Something went wrong !!! Either Item not found or server error.", Toast.LENGTH_SHORT).show();


                        if (ScanItems.progress.isShowing()) {
                            ScanItems.progress.dismiss();


                        }
                       // Toast.makeText(ScanItems.this, "Not Found !!!", Toast.LENGTH_LONG);
                        Etbarcodevalue.setText("");


                    }
                }

                @Override
                public void onFailure(String e) {
                  //  Toast.makeText(ScanItems.this, e, Toast.LENGTH_LONG);
                 //   Toast.makeText(ScanItems.this, "Something Went Wrong ...Please Try Again", Toast.LENGTH_LONG);
                    Toast.makeText(getApplicationContext(), "Something went wrong !!! Either Item not found or server error.", Toast.LENGTH_SHORT).show();

                    Etbarcodevalue.setText("");
                    if (ScanItems.progress.isShowing()) {
                        ScanItems.progress.dismiss();


                    }

                }


            });
        } catch (Exception e) {
            String s = e.getMessage();
        }

    }

    public void showBatchItems(String code,long prdid) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MMM-yyyy");
        String date = mdformat.format(calendar.getTime());


            Products prds=new Products();


               if(code.equals("")|| code.equals(null))
               {
                   code="%";
               }

                prds.ListProductBatchesByUnitNew(url + "ListProductBatchesByUnitNew", dbname, "%",code, false, "SA", -1, date, branchid, false, posSettings.isSHOW_VALID_STOCK_ITEMS(), godownid, posSettings.getPRODUCT_SEARCH_LIMIT(), posSettings.getSALES_BLOCK_EXPIRED_ITEMS(), prdid, posSettings.isDISPLAY_STOCK_IN_SALES_RELATED_SEARCHES(), posSettings.isMRP_SEARCH_WITH_NAME(), new ICustomCallBack() {


                    @Override
                    public <T> void onSucess(T value) {
                        List<Product> Lst = (List<Product>) value;
                        Listforinsert = Lst;

                        if(Lst.size()!=0) {
                            final Dialog dialog = new Dialog(ScanItems.this);
                            // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.dialog_listview_otherbatches);
                            TextView prdname= (TextView) dialog.findViewById(R.id.prdname) ;
                            prdname.setText(Listforinsert.get(0).getName());
                            ImageView btndialog = (ImageView) dialog.findViewById(R.id.closeicon);
                            btndialog.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    dialog.dismiss();

                                }
                            });


                            ListView listView = (ListView) dialog.findViewById(R.id.listview);
                            OtherBatchesAdapter arrayAdapter = new OtherBatchesAdapter(ScanItems.this, R.layout.list_item, Listforinsert, sequence);
                            listView.setAdapter(arrayAdapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    String batchid = ((TextView) view.findViewById(R.id.id)).getText().toString();
                                    SelectedBatchId = Long.valueOf(batchid);
                                    Sync_SelectedPrd();

                                    dialog.dismiss();
                                }
                            });

                            dialog.show();
                        }
                        else
                        {

                            Toast.makeText(getApplicationContext(), "Something went wrong !!! Either Item not found or server error.", Toast.LENGTH_SHORT).show();

                            if (ScanItems.progress.isShowing()) {
                                ScanItems.progress.dismiss();


                            }


                        }
                    }

                    @Override
                    public void onFailure(String e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong !!! Either Item not found or server error.", Toast.LENGTH_SHORT).show();

                        if (ScanItems.progress.isShowing()) {
                            ScanItems.progress.dismiss();


                        }

                    }


                });









    }
    public void showOtherUnits(String name , List<PriceLst> priceLst, items itm) {



        final Dialog dialog = new Dialog(ScanItems.this);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_listview_otherunits);
        TextView prdname= (TextView) dialog.findViewById(R.id.prdname) ;
        prdname.setText(Listforinsert.get(0).getName());
        ImageView btndialog = (ImageView) dialog.findViewById(R.id.closeicon);
        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();

            }
        });


        ListView listView = (ListView) dialog.findViewById(R.id.listview);
        OtherUnitsAdapter arrayAdapter = new OtherUnitsAdapter(ScanItems.this, R.layout.list_item, priceLst, sequence);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String unitid = ((TextView) view.findViewById(R.id.id)).getText().toString();
                SelectedunitId = Long.valueOf(unitid);

                for (PriceLst Lst : priceLst) {

                    if (Lst.getUnitId() == SelectedunitId) {
                        switch ((int) sequence) {
                            case 1:
                                Rate = Lst.getRate1();

                                break;
                            case 2:
                                Rate = Lst.getRate2();
                                break;
                            case 3:
                                Rate = Lst.getRate3();
                                break;
                            case 4:
                                Rate = Lst.getRate4();
                                break;
                            case 5:
                                Rate = Lst.getRate5();
                                break;


                        }
                    }


                }


                itm.setPrice(Rate);
                itm.setUnitId(Long.valueOf(unitid));
                //  DtlObj.setPrice(150);
                itm.setQuantity(1);

                itm.setQtyy(1);
                List<items> items = SaleObj.getItems();

                items.add(0,itm);

                SaleObj.setItems(items);

                LoadItems();

                dialog.dismiss();
            }
        });

        dialog.show();




    }

    public void Sync_SelectedPrd() {


        code = Etbarcodevalue.getText().toString().trim();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy");
        String date = mdformat.format(calendar.getTime());

        Products prdObj = new Products();


        try {


            prdObj.GetProductByBatchIdasProduct(url + "GetProductByBatchIdasProduct", dbname, SelectedBatchId, new ICustomCallBack() {


                @Override
                public <T> void onSucess(T value) {
                    Product prd = (Product) value;

                    if (prd != null) {

                        if (prd.getBatchLst().size() <= 1) {


                            items DtlObj = new items();
                            DtlObj.setProductName(prd.getName());


                            DtlObj.setProductId(prd.getId());
                            DtlObj.setBatchCode(code);
                            DtlObj.setBatchId(SelectedBatchId);
                            DtlObj.setUnitId(prd.getUnitId());
                           long SaleunitId=prd.getSalesUnit();

                           List<PriceLst> priceLsts=new ArrayList<>();
                           priceLsts=prd.getBatchLst().get(0).getPriceLsts();
                           if(priceLsts.size()<=1) {
                               for (PriceLst Lst : priceLsts) {

                                   if (Lst.getUnitId() == SaleunitId) {
                                       switch ((int) sequence) {
                                           case 1:
                                               Rate = Lst.getRate1();

                                               break;
                                           case 2:
                                               Rate = Lst.getRate2();
                                               break;
                                           case 3:
                                               Rate = Lst.getRate3();
                                               break;
                                           case 4:
                                               Rate = Lst.getRate4();
                                               break;
                                           case 5:
                                               Rate = Lst.getRate5();
                                               break;


                                       }
                                   }
                               }



                               DtlObj.setPrice(Rate);
                               //  DtlObj.setPrice(150);
                               DtlObj.setQuantity(1);

                               DtlObj.setQtyy(1);
                               List<items> items = SaleObj.getItems();

                               items.add(0,DtlObj);

                               SaleObj.setItems(items);

                               LoadItems();


                           }
                           else
                           {
               showOtherUnits("",priceLsts,DtlObj);

                           }

                        } else {


                            showBatchItems(code,0);
                        }
                    }

                }

                @Override
                public void onFailure(String e) {
                    Toast.makeText(ScanItems.this, e, Toast.LENGTH_LONG);
                    if (ScanItems.progress.isShowing()) {
                        ScanItems.progress.dismiss();


                    }

                }


            });
        } catch (Exception e) {
            String s = e.getMessage();
            if (ScanItems.progress.isShowing()) {
                ScanItems.progress.dismiss();


            }

        }



    }


    public long getPriceLevel() {
        Products prdObj = new Products();

        com.example.Servicess.Settings settings=new com.example.Servicess.Settings();
         posSettings=settings.getSettings(dbname,url);


        if (posSettings.getBRANCH_WISE_PRICE_LEVEL()==1) {
            prdObj.BranchwisePriceLevelListsBySeq(dbname, url + "BranchwisePriceLevelListsBySeq", branchid, new ICustomCallBack() {

                @Override
                public <T> void onSucess(T value) {
                    List<PriceLevel> pLevel = (List<PriceLevel>) value;

                    if (pLevel != null) {

                        sequence = pLevel.get(0).getpOSSequence();
                    }
                }

                @Override
                public void onFailure(String e) {
                    Toast.makeText(ScanItems.this, e, Toast.LENGTH_LONG);
                }


            });
        } else {
            prdObj.PriceLevelListsBySeq(dbname, url + "PriceLevelListsBySeq", new ICustomCallBack() {

                @Override
                public <T> void onSucess(T value) {
                    List<PriceLevel> pLevel = (List<PriceLevel>) value;

                    if (pLevel != null) {

                        sequence = pLevel.get(0).getpOSSequence();
                    }
                }

                @Override
                public void onFailure(String e) {
                    Toast.makeText(ScanItems.this, e, Toast.LENGTH_LONG);
                }


            });
        }
        return sequence;
    }


    public  void Save()
    {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String date = mdformat.format(calendar.getTime());

        SimpleDateFormat mdformat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String createdTime = mdformat2.format(calendar.getTime());
        Sale saleObj=Sale_Static.getInstance();

        saleObj.setId(1);
        saleObj.setDocDate(date);

        saleObj.setBillingCounterID(counterid);
        saleObj.setBranchID(branchid);
        saleObj.setGodownID(godownid);
        saleObj.setPosDeviceName("");
        saleObj.setItemCount(saleObj.getItems().size());
        saleObj.setUserName(username);
        saleObj.setUserID(userid);

        saleObj.setCreatedAt(createdTime);

        Sales salesObj=new Sales();
         int seq=0;

        for (items itm :saleObj.getItems()
             ) {

            itm.setSeq(seq);
            seq++;
            qty+=itm.getQuantity();

        }
        salesObj.AddPosDraft(url + "AddPosDraft",dbname, saleObj, new ICustomCallBack() {



            @Override
            public <T> void onSucess(T value) {
               Sale Obj= (Sale) value;
                if (Obj!=null) {

                   // Toast.makeText(ScanItems.this,"Added Successfully",Toast.LENGTH_LONG);
                    ShowMsg1("Added Successfully");
                    Intent intent = new Intent(ScanItems.this, SaveSale.class);

                    intent.putExtra("Id",String.valueOf(Obj.getId()));
                    intent.putExtra("count",String.valueOf(Obj.getItemCount()));
                    intent.putExtra("totqty",String.valueOf(qty));
                    ScanItems.this.startActivity(intent);
                    ScanItems.this.finish();
               }
              else
                {
                    ShowMsg1("Failed");
                  //  Toast.makeText(ScanItems.this,"Failed ",Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(String e) {
                ShowMsg1("Failed");
                Toast.makeText(ScanItems.this,"Failed ",Toast.LENGTH_LONG);
            }


        });



    }



    public void ShowMsg1(String s) {

        Runnable doDisplayError = new Runnable() {
            public void run() {
                Toast.makeText(ScanItems.this,s, Toast.LENGTH_LONG).show();

            }
        };
        messageHandler.post(doDisplayError);

    }



}