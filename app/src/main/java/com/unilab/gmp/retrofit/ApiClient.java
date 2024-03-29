package com.unilab.gmp.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by c_jhcanuto on 8/11/2017.
 */
public class ApiClient {
    public static final String BASE_URL_AUDIT_REPORT = "http://sams.webqa.unilab.com.ph/json/audit_report/";
    public static final String BASE_URL_TEMPLATE = "http://sams.webqa.unilab.com.ph/json/template/";
    public static final String BASE_URL = "http://sams.webqa.unilab.com.ph/json/data_maintenance/"; // new api
    public static final String BASE_URL_POST = "http://sams.webqa.unilab.com.ph/";
    public static final String CONFIG_URL = "http://sams.webqa.unilab.com.ph/json/";

//    public static final String BASE_URL_AUDIT_REPORT = "http://13.250.208.116/json/audit_report/";
//    public static final String BASE_URL_TEMPLATE = "http://13.250.208.116/json/template/";
//    public static final String BASE_URL = "http://13.250.208.116/json/data_maintenance/"; // new api
//    public static final String BASE_URL_POST = "http://13.250.208.116/";
//    public static final String CONFIG_URL = "http://13.250.208.116/json/";

    /*public static final String BASE_URL_TEMPLATE = "http://172.16.9.196/gmp/json/template/";
    public static final String BASE_URL = "http://172.16.9.196/gmp/json/data_maintenance/";
    public static final String BASE_URL_POST = "http://172.16.9.196/gmp/";*/
    public static Retrofit retrofit = null;

    final static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.HOURS)
            .writeTimeout(1, TimeUnit.HOURS)
            .readTimeout(1, TimeUnit.HOURS)
            .build();

    public static Retrofit getApiClientPostAuditReport() {
        //if (retrofit==null)
        //{
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_POST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        //}
        return retrofit;
    }

    public static Retrofit getApiBaseURLTemplate() {
        //if (retrofit==null)
        //{
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_TEMPLATE)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        //}
        return retrofit;
    }


    public static Retrofit getBaseURLDataMaintenance() {
        //if (retrofit==null)
        //{
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        //}
        return retrofit;
    }

    public static Retrofit getApiBaseURLAuditReport() {
        //if (retrofit==null)
        //{
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_AUDIT_REPORT)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        //}
        return retrofit;
    }

    public static Retrofit getBaseURLConfig() {
        //if (retrofit==null)
        //{
        retrofit = new Retrofit.Builder()
                .baseUrl(CONFIG_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        //}
        return retrofit;
    }

}
