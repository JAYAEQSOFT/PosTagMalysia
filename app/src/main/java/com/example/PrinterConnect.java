package com.example;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class PrinterConnect extends ListActivity {
 //   EditText Etextprinter;
    String printer="";
    Spinner printersel;
    Button btn,cancel;

    List<String> list = new ArrayList<>();

    static public final int REQUEST_CONNECT_BT = 0x2300;

    static private final int REQUEST_ENABLE_BT = 0x1000;

    static private BluetoothAdapter mBluetoothAdapter = null;

    static private ArrayAdapter<String> mArrayAdapter = null;

    static private ArrayAdapter<BluetoothDevice> btDevices = null;

    private static final UUID SPP_UUID = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
// UUID.fromString(“00001101-0000-1000-8000-00805F9B34FB”);

    static private BluetoothSocket mbtSocket = null;

String from="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // setContentView(R.layout.activity_printer_settings);
        from=getIntent().getStringExtra("from");


        setTitle("Bluetooth Devices");

        try {
            if (initDevicesList() != 0) {
                this.finish();
                return;
            }

        } catch (Exception ex) {
            this.finish();
            return;
        }


        IntentFilter btIntentFilter = new IntentFilter(
                BluetoothDevice.ACTION_FOUND);
        registerReceiver(mBTReceiver, btIntentFilter);
       /* setContentView(R.layout.activity_printer_settings);


        try {
            if (initDevicesList() != 0) {
                this.finish();
                return;
            }

        } catch (Exception ex) {
            this.finish();
            return;
        }

        IntentFilter btIntentFilter = new IntentFilter(
                BluetoothDevice.ACTION_FOUND);
        registerReceiver(mBTReceiver, btIntentFilter);
      //  Etextprinter=(EditText) findViewById(R.id.printer);
        printersel=(Spinner)findViewById(R.id.printer);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        SharedPreferences preferences = getSharedPreferences("Printer", Context.MODE_PRIVATE);
        printer=preferences.getString("Printer","");



            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {

                    String name = device.getName().toString();
                    list.add(name);


                }
            }
            ArrayAdapter<String> spinnerCountShoesArrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    list);
            printersel.setAdapter(spinnerCountShoesArrayAdapter);




        }
        catch (Exception e)
        {

        }
            SharedPreferences printerpreferences = getSharedPreferences("Printer", Context.MODE_PRIVATE);

        if(printerpreferences!=null) {
            printer = printerpreferences.getString("Printer", "");
        }
        else
        {
            printer = printerpreferences.getString("Printer", "");
        }
        if(printer=="")
        {


printersel.setSelection(0);

        }
        else
        {
            for(int i=0;i< list.size();i++)
            {
                if(list.get(i).equals(printer))
                {
                    printersel.setSelection(i);
                }
            }
          //  Etextprinter.setText(printer.toString());

        }
*/





    }


    public  void showMsg()
    {
        Toast.makeText(this, "Details Saved Successfully", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }




    public static BluetoothSocket getSocket() {
        return mbtSocket;
    }

    public void flushData() {
        try {
            if (mbtSocket != null) {
                mbtSocket.close();
                mbtSocket = null;
            }

            if (mBluetoothAdapter != null) {
                mBluetoothAdapter.cancelDiscovery();
            }

            if (btDevices != null) {
                btDevices.clear();
                btDevices = null;
            }

            if (mArrayAdapter != null) {
                mArrayAdapter.clear();
                mArrayAdapter.notifyDataSetChanged();
                mArrayAdapter.notifyDataSetInvalidated();
                mArrayAdapter = null;
            }

            finalize();

        } catch (Exception ex) {
        } catch (Throwable e) {
        }

    }
    private int initDevicesList() {

        flushData();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),
                    "Bluetooth not supported!!", Toast.LENGTH_LONG).show();
            return -1;
        }

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        mArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1);

        setListAdapter(mArrayAdapter);

        Intent enableBtIntent = new Intent(
                BluetoothAdapter.ACTION_REQUEST_ENABLE);
        try {
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } catch (Exception ex) {
            return -2;
        }

        Toast.makeText(getApplicationContext(),
                "Getting all available Bluetooth Devices", Toast.LENGTH_SHORT)
                .show();

        return 0;

    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent intent) {
        super.onActivityResult(reqCode, resultCode, intent);

        switch (reqCode) {
            case REQUEST_ENABLE_BT:

                if (resultCode == RESULT_OK) {
                    Set<BluetoothDevice> btDeviceList = mBluetoothAdapter
                            .getBondedDevices();
                    try {
                        if (btDeviceList.size() > 0) {

                            for (BluetoothDevice device : btDeviceList) {
                               // if (btDeviceList.contains(device) == false) {
                                if (btDevices == null) {
                                    btDevices = new ArrayAdapter<BluetoothDevice>(
                                            getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item);
                                }
                                    btDevices.add(device);

                                    mArrayAdapter.add(device.getName() + "\n"+ device.getAddress());
                                    mArrayAdapter.notifyDataSetInvalidated();
                               // }
                            }
                        }
                    } catch (Exception ex) {

                        String s=ex.getMessage();
                    }
                }

                break;
        }

        mBluetoothAdapter.startDiscovery();

    }

    private final BroadcastReceiver mBTReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                try {
                    if (btDevices == null) {
                        btDevices = new ArrayAdapter<BluetoothDevice>(
                                getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item);
                    }

                    if (btDevices.getPosition(device) < 0) {
                        btDevices.add(device);
                        mArrayAdapter.add(device.getName() + "\n"
                                + device.getAddress() + "\n" );
                        mArrayAdapter.notifyDataSetInvalidated();
                    }
                } catch (Exception ex) {
// ex.fillInStackTrace();
                }
            }
        }
    };

    @Override
    protected void onListItemClick(ListView l, View v, final int position,
                                   long id) {
        super.onListItemClick(l, v, position, id);

        if (mBluetoothAdapter == null) {
            return;
        }

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }




        Toast.makeText(
                getApplicationContext(),
                "Connecting to "+ btDevices.getItem(position).getName() + ","
                        + btDevices.getItem(position).getAddress(),
                Toast.LENGTH_SHORT).show();
        SharedPreferences printerpreferences = getSharedPreferences("Printer", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = printerpreferences.edit();

        printer=btDevices.getItem(position).getName().toString();
        String printerAdd=btDevices.getItem(position).getAddress().toString();
        editor.putString("Printer", printer);


        editor.apply();


        SharedPreferences printerpreferences1 = getSharedPreferences("PrinterAdd", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = printerpreferences1.edit();
        editor1.putString("PrinterAdd", printerAdd);


        editor1.apply();
        Thread connectThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                  /*  boolean gotuuid = btDevices.getItem(position)
                            .fetchUuidsWithSdp();
                    UUID uuid = btDevices.getItem(position).getUuids()[0]
                            .getUuid();
                    mbtSocket = btDevices.getItem(position)
                            .createRfcommSocketToServiceRecord(uuid);

                    mbtSocket.connect();*/


                    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
                 //   Method m = btDevices.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                    mbtSocket = btDevices.getItem(position)
                            .createInsecureRfcommSocketToServiceRecord(uuid);
                    mBluetoothAdapter.cancelDiscovery();
                    mbtSocket.connect();
                } catch (IOException  ex) {
                    runOnUiThread(socketErrorRunnable);
                    try {
                        mbtSocket.close();
                    } catch (Exception e) {
// e.printStackTrace();
                    }
                    mbtSocket = null;
                    return;
                } finally {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                     /*       Toast.makeText(
                                    getApplicationContext(),
                                    "Successfully  Connected to "+ btDevices.getItem(position).getName() + ","
                                            + btDevices.getItem(position).getAddress(),
                                    Toast.LENGTH_SHORT).show();

                            SharedPreferences printerpreferences = getSharedPreferences("Printer", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = printerpreferences.edit();

                            printer=btDevices.getItem(position).getName().toString();
                            editor.putString("Printer", printer);


                            editor.apply();*/
                            finish();

                            if(from.equals("connect")) {
                                Intent intent = new Intent(PrinterConnect.this, PrinterSettings.class);
                                PrinterConnect.this.startActivity(intent);
                                PrinterConnect.this.finish();
                            }
                        }
                    });
                }
            }
        });

        connectThread.start();
    }

    private Runnable socketErrorRunnable = new Runnable() {

        @Override
        public void run() {
            Toast.makeText(getApplicationContext(),
                    "Cannot establish connection", Toast.LENGTH_SHORT).show();
            mBluetoothAdapter.startDiscovery();

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, Menu.FIRST, Menu.NONE, "Refresh Scanning");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case Menu.FIRST:
                initDevicesList();
                break;
        }

        return true;
    }
}