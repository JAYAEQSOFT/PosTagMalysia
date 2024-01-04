package com.example;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.possale.R;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PrinterSettings extends AppCompatActivity {
 //   EditText Etextprinter;
    String printer="",PrinterAdd="";
    Spinner printersel;
    Button btn,cancel;
    ImageView back;
TextView printername;
    List<String> list = new ArrayList<>();

    static public final int REQUEST_CONNECT_BT = 0x2300;

    static private final int REQUEST_ENABLE_BT = 0x1000;

    static private BluetoothAdapter mBluetoothAdapter = null;

    static private ArrayAdapter<String> mArrayAdapter = null;

    static private ArrayAdapter<BluetoothDevice> btDevices = null;

    private static final UUID SPP_UUID = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
// UUID.fromString(“00001101-0000-1000-8000-00805F9B34FB”);

    static private BluetoothSocket mbtSocket = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_printer_settings);
        printername=(TextView)findViewById(R.id.printername);
        SharedPreferences preferences = getSharedPreferences("Printer", Context.MODE_PRIVATE);
        printer=preferences.getString("Printer","");

        SharedPreferences preferences1 = getSharedPreferences("PrinterAdd", Context.MODE_PRIVATE);
        PrinterAdd=preferences1.getString("PrinterAdd","");
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        } else if (!mBluetoothAdapter.isEnabled()) {
            // Bluetooth is not enabled :)
        } else {
            // Bluetooth is enabled
            if(!printer.equals("")&&!printer.equals(null))
            {
                try {
                    OutputStream outputStream = PrinterConnect.getSocket().getOutputStream();
                    if(outputStream!=null) {



                        printername.setText(printer);
                    }
                }
                catch (Exception e)
                {
                    String gd=e.getMessage();
                }

            }
        }


        btn=(Button) findViewById(R.id.save);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

connect();

               /* SharedPreferences printerpreferences = getSharedPreferences("Printer", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = printerpreferences.edit();

                printer=printersel.getSelectedItem().toString();
                editor.putString("Printer", printer);


                editor.apply();
                    showMsg();
                   // InitPrinter();
                    finish();*/

            }
        });

        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //  Receipt.this.ticketprint(Receipt.this.oid);
                PrinterSettings.this.startActivity(new Intent(PrinterSettings.this, ScanItems.class));
                PrinterSettings.this.finish();
            }
        });

        back = (ImageView) findViewById(R.id.back_icon);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //  Receipt.this.ticketprint(Receipt.this.oid);
                PrinterSettings.this.startActivity(new Intent(PrinterSettings.this, ScanItems.class));
                PrinterSettings.this.finish();
            }
        });




    }
    protected void connect() {
        try {




         //   if (PrinterConnect.getSocket() == null) {
                Intent BTIntent = new Intent(getApplicationContext(), PrinterConnect.class);
            BTIntent.putExtra("from","connect");
                this.startActivityForResult(BTIntent, PrinterConnect.REQUEST_CONNECT_BT);

          /*  } else {

                OutputStream opstream = null;

            }*/
        }
        catch (Exception e)
        {

        }
    }

    public  void showMsg()
    {
        Toast.makeText(this, "Details Saved Successfully", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, ScanItems.class));
        finish();
    }


    public void onBackPressed() {
        Intent intent = new Intent(PrinterSettings.this, ScanItems.class);
        PrinterSettings.this.startActivity(intent);
        PrinterSettings.this.finish();
    }



}