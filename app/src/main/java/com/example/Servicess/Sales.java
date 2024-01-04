package com.example.Servicess;

import com.example.API.ApiClient;
import com.example.API.ApiInterface;
import com.example.General.ICustomCallBack;
import com.example.Model.Counter;
import com.example.Model.Sale;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sales {


    public  static ApiInterface apiinterface1;

    public void AddPosDraft( String Url ,String CompanyDbName, Sale Obj, ICustomCallBack customCallback) {
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);


        Call<Sale> call=apiinterface1.AddPosDraft(Url,CompanyDbName,Obj);
        call.enqueue(new Callback<Sale>() {

            @Override
            public void onResponse(Call<Sale> call, Response<Sale> response) {


                if(response.code()==200) {
                    Sale sale=response.body();

                    customCallback.onSucess(sale);

                    /*if(response.message().equals("Success"))
                    {

                        customCallback.onSucess("Success");
                    }
                    else
                    {
                        customCallback.onFailure("Failed"+response.message());
                    }*/
                }

                else
                {
                    String s=response.message().toString();
                    String s2=response.errorBody().toString();

                    customCallback.onFailure("Failed"+response.message());
                }

            }







            @Override
            public void onFailure(Call<Sale> call, Throwable t) {

                String s = t.getMessage();

                customCallback.onFailure(t.getMessage());
            }


        });

    }
}
