package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.API.ApiClient;
import com.example.API.ApiInterface;
import com.example.Adapter.CompanyLstAdapter;
import com.example.General.EncryptDecrypt;
import com.example.General.ICustomCallBack;
import com.example.Model.Company;
import com.example.Model.Counter;
import com.example.Model.CounterLst;
import com.example.Model.Employee;
import com.example.Servicess.Employees;
import com.example.possale.R;
import com.google.android.material.textfield.TextInputLayout;


import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    Employee employee;
    Employees embObj;
    EditText EtUsername, Etpassword;
    Button login;
    TextView register;
    boolean isEmailValid, isPasswordValid;
    TextInputLayout emailError, passError;
    String userName,password;
    public  static ApiInterface apiinterface1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);
        EtUsername = (EditText) findViewById(R.id.email);
        Etpassword = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);

        emailError = (TextInputLayout) findViewById(R.id.emailError);
        passError = (TextInputLayout) findViewById(R.id.passError);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password=Etpassword.getText().toString();
                userName=EtUsername.getText().toString();

              Login();

               // SetValidation();
            }
        });


    }

    public void Login() {

        SharedPreferences preferences = getSharedPreferences("CompanyDBName", Context.MODE_PRIVATE);
        SharedPreferences preferences1= getSharedPreferences("Url", Context.MODE_PRIVATE);
        SharedPreferences preferences2 = getSharedPreferences("BranchId", Context.MODE_PRIVATE);
        SharedPreferences preferencesUsername = getSharedPreferences("Username", Context.MODE_PRIVATE);
        SharedPreferences preferencesuserid = getSharedPreferences("UserId", Context.MODE_PRIVATE);

        String dbname=preferences.getString("CompanyDBName","");
        String url = preferences1.getString("Url", "");
        Long BranchId= preferences2.getLong("BranchId", 0);

        final RequestBody rusername = RequestBody.create(MediaType.parse("text/plain"), "sample phone number");

        embObj=new Employees();
        embObj.Login(dbname,url + "Login",userName,password, new ICustomCallBack() {

            @Override
            public <T> void onSucess(T value) {
                employee=new Employee();
                employee= (Employee) value;
                if(employee!=null)
                {


                  GetEmployeeById(employee);
                }
                else
                {

                    Toast.makeText(getApplicationContext(), "Couldn't find User !!!", Toast.LENGTH_SHORT).show();



                }
            }

            @Override
            public void onFailure(String e) {
                Toast.makeText(Login.this,e,Toast.LENGTH_LONG);
                Toast.makeText(getApplicationContext(), "Couldn't find User !!!", Toast.LENGTH_SHORT).show();



            }


        });




    }

    public void GetEmployeeByName() {

        SharedPreferences preferences = getSharedPreferences("CompanyDBName", Context.MODE_PRIVATE);
        SharedPreferences preferences1= getSharedPreferences("Url", Context.MODE_PRIVATE);

        String dbname=preferences.getString("CompanyDBName","");

        String url = preferences1.getString("Url", "");


        embObj=new Employees();
        embObj.GetEmployeeByName(dbname,url + "GetEmployeeByUserName",userName, new ICustomCallBack() {

            @Override
            public <T> void onSucess(T value) {
                employee=new Employee();
                employee= (Employee) value;
              //  GetEmployeeById();

            }

            @Override
            public void onFailure(String e) {
                Toast.makeText(Login.this,e,Toast.LENGTH_LONG);
            }


        });




    }

    public void GetEmployeeById(Employee emp) {
        SharedPreferences preferences = getSharedPreferences("CompanyDBName", Context.MODE_PRIVATE);
        SharedPreferences preferences1= getSharedPreferences("Url", Context.MODE_PRIVATE);
        SharedPreferences preferences2 = getSharedPreferences("BranchId", Context.MODE_PRIVATE);
        SharedPreferences preferencesUsername = getSharedPreferences("Username", Context.MODE_PRIVATE);
        SharedPreferences preferencesuserid = getSharedPreferences("UserId", Context.MODE_PRIVATE);

        String dbname=preferences.getString("CompanyDBName","");
        String url = preferences1.getString("Url", "");
        Long BranchId= preferences2.getLong("BranchId", 0);
        embObj=new Employees();
        embObj.GetEmployeeById(dbname,url + "GetEmployeeById",emp.getId(), new ICustomCallBack() {

            @Override
            public <T> void onSucess(T value) {
                employee=new Employee();
                employee= (Employee) value;
                try {

                    SharedPreferences.Editor editor1 = preferencesUsername.edit();

                    editor1.putString("Username",employee.getName());

                    editor1.apply();

                    SharedPreferences.Editor editor2 = preferencesuserid.edit();

                    editor2.putLong("UserId",employee.getId());
                    editor2.apply();


Boolean flag =false;
                List<CounterLst>  Lst=employee.getCounterLst();
                        for (CounterLst L:Lst
                             ) {
                            if(Long.valueOf(L.getCounterID())==BranchId)
                            {
                                flag=true;
                            }

                        }
                        if(flag) {


                            editor2.apply();
                            Intent intent = new Intent(Login.this, ScanItems.class);
                            Login.this.startActivity(intent);
                            Login.this.finish();
                        }
                        else
                        {

                            Toast.makeText(getApplicationContext(), "Selected Branch is not allowed", Toast.LENGTH_SHORT).show();

                        }

                } catch (Exception e) {

                    String s=e.getMessage();
                }

            }

            @Override
            public void onFailure(String e) {
                Toast.makeText(Login.this,e,Toast.LENGTH_LONG);
            }


        });



    }



    public void SetValidation() {
        // Check for a valid email address.
        if (Etpassword.getText().toString().isEmpty()) {
            emailError.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else {
            isEmailValid = true;
            emailError.setErrorEnabled(false);
        }

        // Check for a valid password.
        if (Etpassword.getText().toString().isEmpty()) {
            passError.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
            passError.setErrorEnabled(false);
        }

        if (isEmailValid && isPasswordValid) {
            Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Login.this, ScanItems.class);
            Login.this.startActivity(intent);
            Login.this.finish();

        }

    }
}