package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.General.ICustomCallBack;
import com.example.Model.Company;
import com.example.Model.Sale;
import com.example.Model.Sale_Static;
import com.example.Servicess.Companies;
import com.example.possale.R;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import okhttp3.internal.Util;

public class SaveSale extends AppCompatActivity {

    String value = "";
    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket socket;
    BluetoothDevice bluetoothDevice;
    OutputStream outputStream;
    InputStream inputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;
    ImageView img_QR;
    ImageView imgClose;
    Button btnprint;
    String QrCode;
    TextView code;
    TextView txtcomname,Textviewvalue;
    Sale SaleObj;
    String codetype;
    Bitmap img;
    Bitmap qrbigsize;
    String qty,count;
    String comname,comadd,comphone,comemail,username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_sale);
        img_QR=findViewById(R.id.img);
        imgClose=findViewById(R.id.Close);
        code=findViewById(R.id.tvcode);
        txtcomname= findViewById(R.id.comname);
        Textviewvalue= findViewById(R.id.value);
        SharedPreferences preferencetype = getSharedPreferences("CodeFormat", Context.MODE_PRIVATE);
        SharedPreferences preferences2 = getSharedPreferences("CompanyName", Context.MODE_PRIVATE);
         comname = preferences2.getString("CompanyName", "");
        SharedPreferences preferencesUsername = getSharedPreferences("Username", Context.MODE_PRIVATE);
          username=preferencesUsername.getString("Username","");

        SharedPreferences preferences3 = getSharedPreferences("CompanyAdress", Context.MODE_PRIVATE);
        comadd = preferences3.getString("CompanyAdress", "");
        SharedPreferences preferences4 = getSharedPreferences("CompanyPhone", Context.MODE_PRIVATE);
        comphone = preferences4.getString("CompanyPhone", "");
        SharedPreferences preferences5 = getSharedPreferences("CompanyEmail", Context.MODE_PRIVATE);
        comemail = preferences5.getString("CompanyEmail", "");
        txtcomname.setText(comname);

        codetype = preferencetype.getString("CodeFormat", "");
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sale_Static.setInstance();

                Intent i = new Intent(SaveSale.this, ScanItems.class);
                startActivity(i);
                finish();
            }
        });
        imgClose=findViewById(R.id.Close);
        btnprint=findViewById(R.id.print);
        btnprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codetype.equals("QR")) {
                    if (img != null)

                        Print(img);

                }

                else {

                if (qrbigsize != null)

                    Print(qrbigsize);

            }
                }




        });
       QrCode=getIntent().getStringExtra("Id");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < QrCode.length(); i++) {
            if (i > 0) {
                result.append("    ");
            }

            result.append(QrCode.charAt(i));
        }
       code.setText(result);

        qty=getIntent().getStringExtra("totqty");
        count=getIntent().getStringExtra("count");
Textviewvalue.setText("Total Count : "+ count+"  Total Qty : "+qty);
        Save();
    }

    public void Save()
    {
        String qrCodeText = QrCode;
        String filePath = "JD.png";
        int size = 150;
        String fileType = "png";
        File qrFile = new File(filePath);

         GenerateQRCode codeObj=new GenerateQRCode();

        try {


          if(codetype.equals("QR")) {
               img = codeObj.createQRImage(qrFile, qrCodeText, size, fileType, "QR");

          }
          else
          {
              img = codeObj.createQRImage(qrFile, qrCodeText, size, fileType, "Barcode");
              qrbigsize=codeObj.createBarcodePrintImage(qrFile, qrCodeText, 350, fileType, "Barcode");
          }
          img_QR.setImageBitmap(img);
       //   Print(img);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sale_Static.setInstance();
    }

    public  void Print(Bitmap bitmap)
    {
        String newline="\n";
        try {
            BluetoothSocket socket = PrinterConnect.getSocket();

            if(socket!=null) {
                socket.close();
            }


            SharedPreferences preferences1 = getSharedPreferences("PrinterAdd", Context.MODE_PRIVATE);
            String PrinterAdd = preferences1.getString("PrinterAdd", "");
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(PrinterAdd);



            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
            //   Method m = btDevices.getClass().getMethod("createRfcommSocket", new Class[]{int.class});

            socket = device
                    .createRfcommSocketToServiceRecord(uuid);
            mBluetoothAdapter.cancelDiscovery();
            socket.connect();


            outputStream = socket.getOutputStream();
            if (outputStream != null) {
              //  bitmap.setWidth(150);
               // bitmap.setHeight(250);
                byte[] command;
               /* if(codetype.equals("QR")) {
                   command = Utils.decodeBitmap(img); }
                else
                {
*/
              command = Utils.decodeBitmap(bitmap);

              //  }
                    String tab="                          ";
                StringBuilder space1 = new StringBuilder();
                long pagesize = String.valueOf("______________________________________________").getBytes().length;


            String    Data = new String(new byte[]{0x1B, 0x21, 0x10});
                outputStream.write(Data.getBytes());
                final byte[] ESC_ALIGN_CENTER = new byte[] { 0x1b, 'a', 0x01 };
           byte[] SELECT_FONT_A = {20, 33, 0};

  String defaultsize = new String(new byte[] {0x1b,0x21,0x00});//default
                String doubleHeight = new String(new byte[] {0x1b,0x21,0x10});//default
                String doubleWidth = new String(new byte[] {0x1b,0x21,0x20});//default
                String doubleHeightAndWidth = new String(new byte[] {0x1b,0x21,0x20});//default


                String   small = new String(new byte[] {0x1b,0x21,0x01});

             String   bold = new String(new byte[]{0x1b,0x21,0x08});
                byte[] SELECT_FONT_BOLD = {20, 33, 1};
                Data = "POS TAG" + "\n";



                outputStream.write(ESC_ALIGN_CENTER);
               // Data = new String(new byte[]{0x1B, 0x21, 0x20});
              outputStream.write(small.getBytes());

                Data = "POS TAG" + "\n";
                outputStream.write(Data.getBytes());

             //   pagesize = String.valueOf("_________________________").getBytes().length;

                Data = comname + "\n";

                outputStream.write(ESC_ALIGN_CENTER);
                outputStream.write(defaultsize.getBytes());
                outputStream.write(Data.getBytes());


                Data = comadd + "\n";

                outputStream.write(ESC_ALIGN_CENTER);
                outputStream.write(small.getBytes());
                outputStream.write(Data.getBytes());
                if(!comphone.equals(null)&&! comphone.equals("")) {
                    Data = "Phone :" + comphone + "\n";

                    outputStream.write(ESC_ALIGN_CENTER);
                    outputStream.write(small.getBytes());
                    outputStream.write(Data.getBytes());
                }
                if(!comemail.equals(null)&&! comemail.equals("")) {
                    Data = "Email :" + comemail + "\n";

                    outputStream.write(ESC_ALIGN_CENTER);
                    outputStream.write(small.getBytes());
                    outputStream.write(Data.getBytes());

                }
outputStream.write(newline.getBytes());



                 outputStream.write(ESC_ALIGN_CENTER);
                outputStream.write(doubleHeightAndWidth.getBytes());
                outputStream.write(command);
                outputStream.write(newline.getBytes());
                outputStream.write(ESC_ALIGN_CENTER);
                outputStream.write(defaultsize.getBytes());
                outputStream.write(QrCode.getBytes());
                outputStream.write(newline.getBytes());
                Data="Total Count : "+ count+"  Total Qty : "+qty;
                outputStream.write(ESC_ALIGN_CENTER);
                outputStream.write(small.getBytes());
                outputStream.write(Data.getBytes());

                outputStream.write(newline.getBytes());

                String SS = String.format("%30s", " ");
                if(!username.equals(null)&&! username.equals("")) {

                    String uname = (username + SS).substring(0, 30);
                    Data = "User :" + username+" / ";

                    outputStream.write(ESC_ALIGN_CENTER);
                    outputStream.write(small.getBytes());
                    outputStream.write(Data.getBytes());

                }

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat2 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                String createdTime = mdformat2.format(calendar.getTime());
                Data = "Date :" + createdTime + "\n";


                outputStream.write(small.getBytes());
                outputStream.write(Data.getBytes());



                outputStream.write(newline.getBytes());


                Data="Thank you";
                outputStream.write(ESC_ALIGN_CENTER);
                outputStream.write(small.getBytes());
                outputStream.write(Data.getBytes());
                outputStream.write(newline.getBytes());
                outputStream.write(newline.getBytes());


                outputStream.flush();
                //PrintAgain();
            }
        }
        catch(Exception e)
        {

            //  Sale_Invoice.this.startActivity(new Intent(Sale_Invoice.this, MainActivity.class));
            //    Sale_Invoice.this.finish();
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Sale_Static.setInstance();

        Intent i = new Intent(SaveSale.this, ScanItems.class);
        startActivity(i);
        finish();
    }



}
