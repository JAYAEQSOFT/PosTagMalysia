package com.example.Servicess;

import com.example.API.ApiClient;
import com.example.API.ApiInterface;
import com.example.General.ICustomCallBack;
import com.example.Model.Company;
import com.example.Model.CompanyInfo;
import com.example.Model.Counter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Companies {

    public  static ApiInterface apiinterface1;
   CompanyInfo companyInfo;
   List<Company> Lst;
    public void GetCompanies( String Url , ICustomCallBack customCallback) {
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<Company>> call = apiinterface1.GetCompanies(Url);

        call.enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                if (response.code() == 200) {
/*List<Customer> CustList=response.body();
                    if(CustList.size() != 0){*/
                    Lst=new ArrayList<>();
                    Lst=response.body();

                    customCallback.onSucess(Lst);

                }
                else
                {
                    String s=response.message();
                    customCallback.onFailure(s);
                }


            }


            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {

                String s = t.getMessage();

                customCallback.onFailure(t.getMessage());
            }


        });

    }
    public void GetCompanyInfo( String Url,String dbname, ICustomCallBack customCallback) {
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);

        Call<CompanyInfo> call = apiinterface1.GetCompanyInfo(Url,dbname);

        call.enqueue(new Callback<CompanyInfo>() {
            @Override
            public void onResponse(Call<CompanyInfo> call, Response<CompanyInfo> response) {
                if (response.code() == 200) {
/*List<Customer> CustList=response.body();
                    if(CustList.size() != 0){*/

                    companyInfo=response.body();

                    customCallback.onSucess(companyInfo);

                }
                else
                {
                    String s=response.message();
                    customCallback.onFailure(s);
                }


            }


            @Override
            public void onFailure(Call<CompanyInfo> call, Throwable t) {

                String s = t.getMessage();

                customCallback.onFailure(t.getMessage());
            }


        });

    }

}
