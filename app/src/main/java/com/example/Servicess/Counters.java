package com.example.Servicess;

import com.example.API.ApiClient;
import com.example.API.ApiInterface;

import com.example.General.ICustomCallBack;
import com.example.Model.Counter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Counters {
    public  static ApiInterface apiinterface1;
    List<com.example.Model.Counter> countersLst;
    public void GetCounters(String CompanyDbName, String Url , ICustomCallBack customCallback) {
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<Counter>> call = apiinterface1.GetCounters(Url,CompanyDbName);

        call.enqueue(new Callback<List<Counter>>() {
            @Override
            public void onResponse(Call<List<Counter>> call, Response<List<Counter>> response) {
                if (response.code() == 200) {
/*List<Customer> CustList=response.body();
                    if(CustList.size() != 0){*/
                    countersLst=new ArrayList<>();
                    countersLst=response.body();

                    customCallback.onSucess(countersLst);

                }
                else
                {
                    customCallback.onFailure(response.message());
                }


            }


            @Override
            public void onFailure(Call<List<Counter>> call, Throwable t) {

                String s = t.getMessage();

                customCallback.onFailure(t.getMessage());
            }


        });

    }

}
