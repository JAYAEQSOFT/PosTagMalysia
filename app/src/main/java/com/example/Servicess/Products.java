package com.example.Servicess;

import com.example.API.ApiClient;
import com.example.API.ApiInterface;
import com.example.General.ICustomCallBack;
import com.example.Model.BatchLst;
import com.example.Model.Employee;
import com.example.Model.PriceLevel;
import com.example.Model.Product;
import com.example.Model.StandardProduct;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Products {

    public  static ApiInterface apiinterface1;

    public void ListProductBatchesByUnitNew( String Url,String CompanyDbName,String Name,String Code,boolean DefaultBatchesOnly,String UnitFlag,long PrdType , String Ason,long BranchId,boolean IncludeBlocked,boolean ShowValidStockItms,long GodownID,long SearchLmt ,boolean ExcludeExpired,long PrdId,boolean DisplayStock,boolean MRPSearchEnabled, ICustomCallBack customCallback) {
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<Product>> call = apiinterface1.ListProductBatchesByUnitNew(Url,CompanyDbName,Name,Code,DefaultBatchesOnly,UnitFlag,PrdType,Ason,BranchId,IncludeBlocked,ShowValidStockItms,GodownID,SearchLmt,ExcludeExpired,PrdId,DisplayStock,MRPSearchEnabled);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.code() == 200) {
/*List<Customer> CustList=response.body();
                    if(CustList.size() != 0){*/
                    List<Product> prdLst=new  ArrayList();
                    prdLst= (List<Product>) response.body();
                    customCallback.onSucess(prdLst);


                }
                else
                {
                    customCallback.onFailure(response.message());
                }


            }


            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

                customCallback.onFailure(t.getMessage());


            }


        });

    }

    public void GetProductByBatchIdasProduct( String Url,String CompanyDbName,long BatchId, ICustomCallBack customCallback) {
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Product> call = apiinterface1.GetProductByBatchIdasProduct(Url,CompanyDbName,BatchId);

        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.code() == 200) {
/*List<Customer> CustList=response.body();
                    if(CustList.size() != 0){*/
                    Product prdObj=new Product();
                    prdObj= (Product) response.body();
                    customCallback.onSucess(prdObj);


                }
                else
                {
                    customCallback.onFailure(response.message());
                }


            }


            @Override
            public void onFailure(Call<Product> call, Throwable t) {

                customCallback.onFailure(t.getMessage());


            }


        });

    }

    public void GetBatchListByProductId( String Url,String CompanyDbName,long Id, ICustomCallBack customCallback) {
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<BatchLst>> call = apiinterface1.GetBatchListByProductId(Url,CompanyDbName,Id);

        call.enqueue(new Callback<List<BatchLst>>() {
            @Override
            public void onResponse(Call<List<BatchLst>> call, Response<List<BatchLst>> response) {
                if (response.code() == 200) {
/*List<Customer> CustList=response.body();
                    if(CustList.size() != 0){*/
                    List<BatchLst> Obj=new ArrayList<>();
                    Obj= (List<BatchLst>) response.body();
                    customCallback.onSucess(Obj);


                }
                else
                {
                    customCallback.onFailure(response.message());
                }


            }


            @Override
            public void onFailure(Call<List<BatchLst>> call, Throwable t) {

                customCallback.onFailure(t.getMessage());


            }


        });

    }
    public void PriceLevelListsBySeq(String CompanyDbName, String Url, ICustomCallBack customCallback) {
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<PriceLevel>> call = apiinterface1.PriceLevelListsBySeq(Url,CompanyDbName);

        call.enqueue(new Callback<List<PriceLevel>>() {
            @Override
            public void onResponse(Call<List<PriceLevel>> call, Response<List<PriceLevel>> response) {
                if (response.code() == 200) {
                     List<PriceLevel> priceLevelLst=response.body();


                    priceLevelLst=response.body();
                    customCallback.onSucess(priceLevelLst);


                }
                else
                {
                    customCallback.onFailure(response.message());
                }


            }


            @Override
            public void onFailure(Call<List<PriceLevel>> call, Throwable t) {

                customCallback.onFailure(t.getMessage());


            }


        });

    }
    public void BranchwisePriceLevelListsBySeq(String CompanyDbName, String Url,long BranchId, ICustomCallBack customCallback) {
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<PriceLevel>> call = apiinterface1.BranchwisePriceLevelListsBySeq(Url,CompanyDbName,BranchId);

        call.enqueue(new Callback<List<PriceLevel>>() {
            @Override
            public void onResponse(Call<List<PriceLevel>> call, Response<List<PriceLevel>> response) {
                if (response.code() == 200) {
                    List<PriceLevel> priceLevelLst=response.body();


                    priceLevelLst=response.body();
                    customCallback.onSucess(priceLevelLst);


                }
                else
                {
                    customCallback.onFailure(response.message());
                }


            }


            @Override
            public void onFailure(Call<List<PriceLevel>> call, Throwable t) {

                customCallback.onFailure(t.getMessage());


            }


        });

    }

    public void GetProductBatchByCode( String Url,String CompanyDbName,String Code, ICustomCallBack customCallback) {
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);

        Call<BatchLst> call = apiinterface1.GetProductBatchByCode(Url,CompanyDbName,Code);

        call.enqueue(new Callback<BatchLst>() {
            @Override
            public void onResponse(Call<BatchLst> call, Response<BatchLst> response) {
                if (response.code() == 200) {
/*List<Customer> CustList=response.body();
                    if(CustList.size() != 0){*/
                    BatchLst Obj=new BatchLst();
                    Obj= (BatchLst) response.body();
                    customCallback.onSucess(Obj);


                }
                else
                {
                    customCallback.onFailure(response.message());
                }


            }


            @Override
            public void onFailure(Call<BatchLst> call, Throwable t) {

                customCallback.onFailure(t.getMessage());


            }


        });

    }

    public void GetProductBatchListByBatchCode( String Url,String CompanyDbName,String Code, ICustomCallBack customCallback) {
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<BatchLst>> call = apiinterface1.GetProductBatchListByBatchCode(Url,CompanyDbName,Code);

        call.enqueue(new Callback<List<BatchLst>>() {
            @Override
            public void onResponse(Call<List<BatchLst>> call, Response<List<BatchLst>> response) {
                if (response.code() == 200) {
/*List<Customer> CustList=response.body();
                    if(CustList.size() != 0){*/
                    List<BatchLst> LstObj=new ArrayList<>();
                    LstObj= (List<BatchLst>) response.body();
                    customCallback.onSucess(LstObj);


                }
                else
                {
                    customCallback.onFailure(response.message());
                }


            }


            @Override
            public void onFailure(Call<List<BatchLst>> call, Throwable t) {

                customCallback.onFailure(t.getMessage());


            }


        });

    }

    public void GetProductByWeighingScaleCode( String Url,String CompanyDbName,String Code, ICustomCallBack customCallback) {
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Product> call = apiinterface1.GetProductByWeighingScaleCode(Url,CompanyDbName,Code);

        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.code() == 200) {
/*List<Customer> CustList=response.body();
                    if(CustList.size() != 0){*/
                    Product prdObj=new Product();
                    prdObj= (Product) response.body();
                    customCallback.onSucess(prdObj);


                }
                else
                {
                    customCallback.onFailure(response.message());
                }


            }


            @Override
            public void onFailure(Call<Product> call, Throwable t) {

                customCallback.onFailure(t.getMessage());


            }


        });

    }

    public void ProductBatchesByProductID( String Url,String CompanyDbName,String Code, ICustomCallBack customCallback) {
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<BatchLst>> call = apiinterface1.GetProductBatchListByBatchCode(Url,CompanyDbName,Code);

        call.enqueue(new Callback<List<BatchLst>>() {
            @Override
            public void onResponse(Call<List<BatchLst>> call, Response<List<BatchLst>> response) {
                if (response.code() == 200) {
/*List<Customer> CustList=response.body();
                    if(CustList.size() != 0){*/
                    Product prdObj=new Product();
                    prdObj= (Product) response.body();
                    customCallback.onSucess(prdObj);


                }
                else
                {
                    customCallback.onFailure(response.message());
                }


            }


            @Override
            public void onFailure(Call<List<BatchLst>> call, Throwable t) {

                customCallback.onFailure(t.getMessage());


            }


        });

    }

    public void ProductBatchesByProductID( String Url,String CompanyDbName,long PrdID,boolean IncludeAll, ICustomCallBack customCallback) {
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<BatchLst>> call = apiinterface1.ProductBatchesByProductID(Url,CompanyDbName,PrdID,IncludeAll);

        call.enqueue(new Callback<List<BatchLst>>() {
            @Override
            public void onResponse(Call<List<BatchLst>> call, Response<List<BatchLst>> response) {
                if (response.code() == 200) {
/*List<Customer> CustList=response.body();
                    if(CustList.size() != 0){*/
                    List<BatchLst> LstObj=new ArrayList<>();
                    LstObj= (List<BatchLst>) response.body();
                    customCallback.onSucess(LstObj);


                }
                else
                {
                    customCallback.onFailure(response.message());
                }


            }


            @Override
            public void onFailure(Call<List<BatchLst>> call, Throwable t) {

                customCallback.onFailure(t.getMessage());


            }


        });

    }

    public void GetProductByStandardMapBatchCode( String Url,String CompanyDbName,String Code, ICustomCallBack customCallback) {
        apiinterface1 = ApiClient.getApiClient().create(ApiInterface.class);

        Call<StandardProduct> call = apiinterface1.GetProductByStandardMapBatchCode(Url,CompanyDbName,Code);

        call.enqueue(new Callback<StandardProduct>() {
            @Override
            public void onResponse(Call<StandardProduct> call, Response<StandardProduct> response) {
                if (response.code() == 200) {
/*List<Customer> CustList=response.body();
                    if(CustList.size() != 0){*/
                    StandardProduct prdObj=new StandardProduct();
                    prdObj= (StandardProduct) response.body();
                    customCallback.onSucess(prdObj);


                }
                else
                {
                    customCallback.onFailure(response.message());
                }


            }


            @Override
            public void onFailure(Call<StandardProduct> call, Throwable t) {

                customCallback.onFailure(t.getMessage());


            }


        });

    }

}
