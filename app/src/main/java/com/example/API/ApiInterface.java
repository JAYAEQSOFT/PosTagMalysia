package com.example.API;


import com.example.Model.BatchLst;
import com.example.Model.Branch;
import com.example.Model.Company;
import com.example.Model.CompanyInfo;
import com.example.Model.Counter;
import com.example.Model.Employee;
import com.example.Model.Godown;
import com.example.Model.PriceLevel;
import com.example.Model.Product;
import com.example.Model.Sale;
import com.example.Model.Setting;
import com.example.Model.StandardProduct;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET()
    @Headers({"Accept: application/json"})
    Call<List<Company>> GetCompanies(@Url String url);

    @GET()
    @Headers({"Accept: application/json"})
    Call<CompanyInfo> GetCompanyInfo(@Url String url, @Query("CompanyDBName") String CompanyDBName);

    @GET()
    @Headers({"Accept: application/json"})
    Call<List<Branch>> GetBranches(@Url String url, @Header("CompanyDBName") String CompanyDBName );

    @GET()
    @Headers({"Accept: application/json"})
    Call<Branch> GetBranchById(@Url String url, @Header("CompanyDBName") String CompanyDBName, @Query("Id") long Id);

    @GET()
    @Headers({"Accept: application/json"})
    Call<List<Godown>> GetGodwown(@Url String url, @Header("CompanyDBName") String CompanyDBName);

    @GET()
    @Headers({"Accept: application/json"})
    Call<Godown> GetGodownById(@Url String url,@Header("CompanyDBName") String CompanyDBName,@Query("Id") long Id);

    @GET()
    @Headers({"Accept: application/json"})
    Call<List<Counter>> GetCounters(@Url String url, @Header("CompanyDBName") String CompanyDBName);

    @GET()
    @Headers({"Accept: application/json"})
    Call<Godown> GetCounterById(@Url String url,@Header("CompanyDBName") String CompanyDBName,@Query("Id") long Id);


    @GET()
    @Headers({"Accept: application/json"})
    Call<List<Product>> ListProductBatchesByUnitNew(@Url String url,@Header("CompanyDBName") String CompanyDBName,@Query("Name") String Name,@Query("Code") String Code,@Query("DefaultBatchesOnly") Boolean DefaultBatchesOnly,@Query("UnitFlag") String UnitFlag,@Query("PrdType") long PrdType,@Query("Ason") String Ason,@Query("BranchId") long BranchId,@Query("IncludeBlocked") boolean IncludeBlocked,@Query("ShowValidStockItms") boolean ShowValidStockItms,@Query("GodownID") long GodownID,@Query("SearchLmt")  long SearchLmt,@Query("ExcludeExpired")   boolean ExcludeExpired,@Query("PrdId") long PrdId,@Query("DisplayStock") boolean DisplayStock,@Query("MRPSearchEnabled") boolean MRPSearchEnabled);


    @GET()
    @Headers({"Accept: application/json"})
    Call<Product> GetProductByBatchIdasProduct(@Url String url,@Header("CompanyDBName") String CompanyDBName,@Query("BatchId") long BatchId);


    @GET()
    @Headers({"Accept: application/json"})
    Call<List<BatchLst>> GetBatchListByProductId(@Url String url, @Header("CompanyDBName") String CompanyDBName, @Query("ID") long Id);




    @GET()
    @Headers({"Accept: application/json"})
    Call<BatchLst> GetProductBatchByCode(@Url String url,@Header("CompanyDBName") String CompanyDBName,@Query("Code") String Code);



    @GET()
    @Headers({"Accept: application/json"})
    Call<List<BatchLst>> GetProductBatchListByBatchCode(@Url String url,@Header("CompanyDBName") String CompanyDBName,@Query("Code") String Code);

    @GET()
    @Headers({"Accept: application/json"})
    Call<Product> GetProductByWeighingScaleCode(@Url String url,@Header("CompanyDBName") String CompanyDBName,@Query("Code") String Code);

    @GET()
    @Headers({"Accept: application/json"})
    Call<List<BatchLst>> ProductBatchesByProductID(@Url String url,@Header("CompanyDBName") String CompanyDBName,@Query("PrdID") long PrdID,@Query("IncludeAll") boolean includeAll);


    @GET()
    @Headers({"Accept: application/json"})
    Call<StandardProduct> GetProductByStandardMapBatchCode(@Url String url, @Header("CompanyDBName") String CompanyDBName, @Query("Code") String Code);



    @GET()
    @Headers({"Accept: application/json"})
    Call<List<PriceLevel>> PriceLevelListsBySeq(@Url String url, @Header("CompanyDBName") String CompanyDBName);

    @GET()
    @Headers({"Accept: application/json"})
    Call<List<PriceLevel>> BranchwisePriceLevelListsBySeq(@Url String url, @Header("CompanyDBName") String CompanyDBName,@Query("BranchId") long BranchId);

    @GET()
    @Headers({"Accept: application/json"})
    Call<Employee> GetEmployeeByUserName(@Url String url, @Header("CompanyDBName") String CompanyDBName, @Query("Name") String Name);


    @POST()
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    Call<Employee> Login(@Url String url, @Header("CompanyDBName") String CompanyDBName, @Field("Username") String Username, @Field("Password") String Password);


    @GET()
    @Headers({"Accept: application/json"})
    Call<Employee> GetEmployeeById(@Url String url,@Header("CompanyDBName") String CompanyDBName,@Query("Id") long Id);

    @POST()
    @Headers({"Accept: application/json"})
    Call<Sale> AddPosDraft(@Url String url,@Header("CompanyDBName") String CompanyDBName,@Body Sale obj);


    @GET()
    @Headers({"Accept: application/json"})
    Call<Setting> GetKeyValueSettingByKey(@Url String url, @Header("CompanyDBName") String CompanyDBName, @Query("KeyNAme") String KeyName);



}
