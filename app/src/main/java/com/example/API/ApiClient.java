package com.example.API;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


public static final String BASE_URL = "http://192.168.1.202:100/Equal/Pos/";

    private static Retrofit retrofit = null;

    // public static final Integer COMPANY_ID = 55;
    public static Retrofit getApiClient() {

        int timeout = 70000;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(timeout, TimeUnit.MILLISECONDS).addInterceptor(interceptor).build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }


     /*   HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

*/

// add your other interceptors …

// add logging as last interceptor
    /*    httpClient.addInterceptor(logging);  // <-- this is the important line!
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()).build();
        }
      ;*/
        return retrofit;

    }
}
