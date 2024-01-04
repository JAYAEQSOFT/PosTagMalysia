package com.example.Servicess;

import com.example.API.ApiClient;
import com.example.API.ApiInterface;
import com.example.General.ICustomCallBack;
import com.example.Model.Branch;
import com.example.Model.Godown;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Godowns {
    public  static ApiInterface apiinterface1;
    List<Godown> Lst;
    public void GetGodowns(String CompanyDbName, String Url , ICustomCallBack customCallback) {
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<Godown>> call = apiinterface1.GetGodwown(Url,CompanyDbName);

        call.enqueue(new Callback<List<Godown>>() {
            @Override
            public void onResponse(Call<List<Godown>> call, Response<List<Godown>> response) {
                if (response.code() == 200) {
/*List<Customer> CustList=response.body();
                    if(CustList.size() != 0){*/
                    Lst=new ArrayList<>();
                    Lst=response.body();

                    customCallback.onSucess(Lst);

                }
                else
                {
                    customCallback.onFailure(response.message());
                }


            }


            @Override
            public void onFailure(Call<List<Godown>> call, Throwable t) {

                String s = t.getMessage();

                customCallback.onFailure(t.getMessage());
            }


        });

    }
}
