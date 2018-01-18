package com.unilab.gmp.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by c_jhcanuto on 8/11/2017.
 */
public class ApiClient {
//    public static final String BASE_URL_AUDIT_REPORT = "https://api.myjson.com/bins/";
    public static final String BASE_URL_AUDIT_REPORT = "http://sams.webqa.unilab.com.ph/json/audit_report/";
    public static final String BASE_URL_TEMPLATE = "http://sams.webqa.unilab.com.ph/json/template/";
    //public static final String BASE_URL = "http://sams.ecomqa.com/json/data_maintenance/";
    public static final String BASE_URL = "http://sams.webqa.unilab.com.ph/json/data_maintenance/"; // new api
    //public static final String BASE_URL_POST = "http://sams.ecomqa.com/";
    public static final String BASE_URL_POST = "http://sams.webqa.unilab.com.ph/";
    public static final String CONFIG_URL = "http://sams.webqa.unilab.com.ph/json/";

    /*public static final String BASE_URL_TEMPLATE = "http://172.16.9.196/gmp/json/template/";
    public static final String BASE_URL = "http://172.16.9.196/gmp/json/data_maintenance/";
    public static final String BASE_URL_POST = "http://172.16.9.196/gmp/";*/
    public static Retrofit retrofit = null;

    public static Retrofit getApiClientPostAuditReport()
    {
        //if (retrofit==null)
        //{
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_POST)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        //}
        return retrofit;
    }

    public static Retrofit getApiClientTemplate()
    {
        //if (retrofit==null)
        //{
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_TEMPLATE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //}
        return retrofit;
    }


    public static Retrofit getApiClient()
    {
        //if (retrofit==null)
        //{
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //}
        return retrofit;
    }

    public static Retrofit getApiClientAuditReport()
    {
        //if (retrofit==null)
        //{
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_AUDIT_REPORT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //}
        return retrofit;
    }

    public static Retrofit getConfig()
    {
        //if (retrofit==null)
        //{
        retrofit = new Retrofit.Builder()
                .baseUrl(CONFIG_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //}
        return retrofit;
    }

}
