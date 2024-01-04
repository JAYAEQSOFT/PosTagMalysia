package com.example.Servicess;

import com.example.API.ApiClient;
import com.example.API.ApiInterface;
import com.example.General.ICustomCallBack;
import com.example.Model.Branch;
import com.example.Model.Counter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Branches {

    public  static ApiInterface apiinterface1;
    List<Branch> Lst;
    public void GetBranches(String CompanyDbName, String Url , ICustomCallBack customCallback) {
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<Branch>> call = apiinterface1.GetBranches(Url,CompanyDbName);

        call.enqueue(new Callback<List<Branch>>() {
            @Override
            public void onResponse(Call<List<Branch>> call, Response<List<Branch>> response) {
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
            public void onFailure(Call<List<Branch>> call, Throwable t) {

                String s = t.getMessage();

                customCallback.onFailure(t.getMessage());
            }


        });

    }
}
