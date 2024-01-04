package com.example.Servicess;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.API.ApiClient;
import com.example.API.ApiInterface;
import com.example.Adapter.CompanyLstAdapter;
import com.example.CompaniesList;
import com.example.General.ICustomCallBack;
import com.example.Model.Company;
import com.example.Model.Employee;
import com.example.Model.PosSettings;
import com.example.Model.Setting;
import com.example.possale.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Settings {


    public  static ApiInterface apiinterface1;

    public void GetKeyValueSettingByKey(String CompanyDbName, String Url,String KeyNAme, ICustomCallBack customCallback) {
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Setting> call = apiinterface1.GetKeyValueSettingByKey(Url,CompanyDbName,KeyNAme);

        call.enqueue(new Callback<Setting>() {
            @Override
            public void onResponse(Call<Setting> call, Response<Setting> response) {
                if (response.code() == 200) {
/*List<Customer> CustList=response.body();
                    if(CustList.size() != 0){*/
                    Setting sett=new Setting();
                    sett=response.body();
                    customCallback.onSucess(sett);


                }
                else
                {
                    customCallback.onFailure(response.message());
                }


            }


            @Override
            public void onFailure(Call<Setting> call, Throwable t) {

                customCallback.onFailure(t.getMessage());


            }


        });

    }

    public  PosSettings getSettings(String CompanyDbName, String Url)
    {
        PosSettings settings =new PosSettings();

        GetKeyValueSettingByKey(CompanyDbName,Url + "GetKeyValueSettingByKey","BRANCH WISE PRICE LEVEL", new ICustomCallBack() {

            @Override
            public <T> void onSucess(T value) {
                Setting Obj= (Setting) value;
                settings.setBRANCH_WISE_PRICE_LEVEL(Integer.valueOf(Obj.getValue()));
            }

            @Override
            public void onFailure(String e) {

            }


        });

        GetKeyValueSettingByKey(CompanyDbName,Url + "GetKeyValueSettingByKey","PRODUCT SEARCH LIMIT(TRANSACTIONS)", new ICustomCallBack() {

            @Override
            public <T> void onSucess(T value) {
                Setting Obj= (Setting) value;
                settings.setPRODUCT_SEARCH_LIMIT(Integer.valueOf(Obj.getValue()));
            }

            @Override
            public void onFailure(String e) {

            }


        });

        GetKeyValueSettingByKey(CompanyDbName,Url + "GetKeyValueSettingByKey","SALES_BLOCK_EXPIRED_ITEMS", new ICustomCallBack() {

            @Override
            public <T> void onSucess(T value) {
                Setting Obj= (Setting) value;

                if(Obj.getValue().equals("0"))
                {
                    settings.setSALES_BLOCK_EXPIRED_ITEMS(false);
                }
                else
                {
                    settings.setSALES_BLOCK_EXPIRED_ITEMS(true);
                }


            }

            @Override
            public void onFailure(String e) {

            }


        });
        GetKeyValueSettingByKey(CompanyDbName,Url + "GetKeyValueSettingByKey","PRODUCT MRP SEARCH WITH NAME", new ICustomCallBack() {

            @Override
            public <T> void onSucess(T value) {
                Setting Obj= (Setting) value;
                if(Obj.getValue().equals("0"))
                {
                settings.setMRP_SEARCH_WITH_NAME(false);
                }
                else
                {
                    settings.setMRP_SEARCH_WITH_NAME(true);
                }
            }

            @Override
            public void onFailure(String e) {

            }


        });
        GetKeyValueSettingByKey(CompanyDbName,Url + "GetKeyValueSettingByKey","SHOW VALID STOCK ITEMS", new ICustomCallBack() {

            @Override
            public <T> void onSucess(T value) {
                Setting Obj= (Setting) value;

                if(Obj.getValue().equals("0")) {
                    settings.setSHOW_VALID_STOCK_ITEMS(false);
                }
                else
                {
                    settings.setSHOW_VALID_STOCK_ITEMS(true);
                }
            }

            @Override
            public void onFailure(String e) {

            }


        });

        GetKeyValueSettingByKey(CompanyDbName,Url + "GetKeyValueSettingByKey","DISPLAY STOCK IN SALES RELATED SEARCHES", new ICustomCallBack() {

            @Override
            public <T> void onSucess(T value) {
                Setting Obj= (Setting) value;

                if(Obj.getValue().equals("0")) {
                    settings.setDISPLAY_STOCK_IN_SALES_RELATED_SEARCHES(false);
                }
                else
                {
                    settings.setDISPLAY_STOCK_IN_SALES_RELATED_SEARCHES(true);
                }
            }

            @Override
            public void onFailure(String e) {

            }


        });


        GetKeyValueSettingByKey(CompanyDbName,Url + "GetKeyValueSettingByKey","PRODUCTS WITH MULTI UNIT", new ICustomCallBack() {

            @Override
            public <T> void onSucess(T value) {
                Setting Obj= (Setting) value;

                if(Obj.getValue().equals("0")) {
                    settings.setPRODUCT_WITHMULTI_UNIT(false);
                }
                else
                {
                    settings.setPRODUCT_WITHMULTI_UNIT(true);
                }
            }

            @Override
            public void onFailure(String e) {

            }


        });

        GetKeyValueSettingByKey(CompanyDbName,Url + "GetKeyValueSettingByKey","SALES PROMPT MULTI UNIT SELECTION", new ICustomCallBack() {

            @Override
            public <T> void onSucess(T value) {
                Setting Obj= (Setting) value;
                if(Obj.getValue().equals("0")) {
                    settings.setSALES_PROMPT_MULTI_UNIT_SELECTION(false);
                }
                else
                {
                    settings.setSALES_PROMPT_MULTI_UNIT_SELECTION(true);
                }
            }

            @Override
            public void onFailure(String e) {

            }


        });


        GetKeyValueSettingByKey(CompanyDbName,Url + "GetKeyValueSettingByKey","WEIGHING SCALE CODE CHARACTER START FROM", new ICustomCallBack() {

            @Override
            public <T> void onSucess(T value) {
                Setting Obj= (Setting) value;


                    settings.setWEIGHING_SCALE_CODE_CHARACTER_START_FROM(Integer.valueOf(Obj.getValue()));

            }

            @Override
            public void onFailure(String e) {

            }


        });

        GetKeyValueSettingByKey(CompanyDbName,Url + "GetKeyValueSettingByKey","WEIGHING SCALE CODE INDENTIFICATION CHARACTER", new ICustomCallBack() {

            @Override
            public <T> void onSucess(T value) {
                Setting Obj= (Setting) value;


                settings.setWEIGHING_SCALE_CODE_INDENTIFICATION_CHARACTER(Obj.getValue());

            }

            @Override
            public void onFailure(String e) {

            }


        });

        GetKeyValueSettingByKey(CompanyDbName,Url + "GetKeyValueSettingByKey","WEIGHING SCALE ENABLE", new ICustomCallBack() {

            @Override
            public <T> void onSucess(T value) {
                Setting Obj= (Setting) value;

                if(Obj.getValue().equals("0")) {
                    settings.setWEIGHING_SCALE_ENABLE(false);
                }
                else
                {
                    settings.setWEIGHING_SCALE_ENABLE(true);
                }

            }

            @Override
            public void onFailure(String e) {

            }


        });

        GetKeyValueSettingByKey(CompanyDbName,Url + "GetKeyValueSettingByKey","WEIGHING SCALE PRICE QTY CHARACTER DECIMALS", new ICustomCallBack() {

            @Override
            public <T> void onSucess(T value) {
                Setting Obj= (Setting) value;


                    settings.setWEIGHING_SCALE_PRICE_QTY_CHARACTER_DECIMALS(Integer.valueOf(Obj.getValue()));

            }

            @Override
            public void onFailure(String e) {

            }


        });



        GetKeyValueSettingByKey(CompanyDbName,Url + "GetKeyValueSettingByKey","WEIGHING SCALE PRICE QTY CHARACTER LENGTH", new ICustomCallBack() {

            @Override
            public <T> void onSucess(T value) {
                Setting Obj= (Setting) value;


                settings.setWEIGHING_SCALE_PRICE_QTY_CHARACTER_LENGTH(Integer.valueOf(Obj.getValue()));

            }

            @Override
            public void onFailure(String e) {

            }


        });



        GetKeyValueSettingByKey(CompanyDbName,Url + "GetKeyValueSettingByKey","WEIGHING SCALE PRICE QTY CHARACTER START FROM", new ICustomCallBack() {

            @Override
            public <T> void onSucess(T value) {
                Setting Obj= (Setting) value;


                settings.setWEIGHING_SCALE_PRICE_QTY_CHARACTER_START_FROM(Integer.valueOf(Obj.getValue()));

            }

            @Override
            public void onFailure(String e) {

            }


        });



        GetKeyValueSettingByKey(CompanyDbName,Url + "GetKeyValueSettingByKey","WEIGHING SCALE REMAINING CHARACTER FOR", new ICustomCallBack() {

            @Override
            public <T> void onSucess(T value) {
                Setting Obj= (Setting) value;


                settings.setWEIGHING_SCALE_REMAINING_CHARACTER_FOR(Integer.valueOf(Obj.getValue()));

            }

            @Override
            public void onFailure(String e) {

            }


        });



        GetKeyValueSettingByKey(CompanyDbName,Url + "GetKeyValueSettingByKey","WEIGHING SCALE TOTAL CHARACTER", new ICustomCallBack() {

            @Override
            public <T> void onSucess(T value) {
                Setting Obj= (Setting) value;


                settings.setWEIGHING_SCALE_TOTAL_CHARACTER(Integer.valueOf(Obj.getValue()));

            }

            @Override
            public void onFailure(String e) {

            }


        });

        GetKeyValueSettingByKey(CompanyDbName,Url + "GetKeyValueSettingByKey","WEIGHING SCALE SINGLE LINE CODE", new ICustomCallBack() {

            @Override
            public <T> void onSucess(T value) {
                Setting Obj= (Setting) value;

                if(Obj.getValue().equals("0")) {
                    settings.setWEIGHING_SCALE_SINGLE_LINE_CODE(false);
                }
                else
                {
                    settings.setWEIGHING_SCALE_SINGLE_LINE_CODE(true);
                }

            }

            @Override
            public void onFailure(String e) {

            }


        });


        return settings;

    }
}
