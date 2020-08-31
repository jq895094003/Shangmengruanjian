package com.smrj.shangmengruanjian.asynchttpclient;

import android.content.Context;
import android.content.pm.ServiceInfo;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

public class MyAsyncClient {

    private static AsyncHttpClient syncHttpClient = new SyncHttpClient();
    private static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private static ProgressBar progressBar;
    private Context context;

    public MyAsyncClient(Context context) {
        this.context = context;
    }


    public static void doPost(String relativeUrl,RequestParams requestParams,MyResponseHandler myResponseHandler){
        getClient().setTimeout(20000);
        getClient().post(relativeUrl,requestParams,myResponseHandler);
    }

    public static void doPost(String relativiUrl,MyResponseHandler myResponseHandler){
        getClient().setTimeout(20000);
        getClient().post(relativiUrl,myResponseHandler);
    }

    public static void doGet(String relativiUrl,MyResponseHandler myResponseHandler){
        getClient().setTimeout(20000);
        getClient().get(relativiUrl,myResponseHandler);
    }

    public static void getAll(String relativeUrl,String what,MyResponseHandler myResponseHandler){
        RequestParams requestParams = new RequestParams();
        requestParams.put("key",what);
        doPost(relativeUrl,myResponseHandler);
    }

    public static void downLoadFile(Context context, String url, BinaryHttpResponseHandler binaryHttpResponseHandler){
        getClient().get(context,url,binaryHttpResponseHandler);
    }

    public static void downLoadFile(Context context, String url, FileAsyncHttpResponseHandler fileAsyncHttpResponseHandler){
        getClient().get(context,url,fileAsyncHttpResponseHandler);
    }

    private static AsyncHttpClient getClient(){
        if (Looper.myLooper() == null){
            return syncHttpClient;
        }else {
            return asyncHttpClient;
        }
    }

    public static void cancelRequest(Context context){
        getClient().cancelRequests(context,true);
    }

}
