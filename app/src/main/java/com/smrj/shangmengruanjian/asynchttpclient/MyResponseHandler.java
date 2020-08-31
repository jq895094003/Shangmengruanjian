package com.smrj.shangmengruanjian.asynchttpclient;

import android.app.Activity;
import android.util.Log;

import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public abstract class MyResponseHandler extends TextHttpResponseHandler {

    private String TAG = "responsehandler";

    private Activity context;

    public abstract void success(JSONObject jsonObject);

    public abstract void faile(JSONObject jsonObject);

    public MyResponseHandler(Activity context){
        this.context = context;
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        Log.i(TAG,"请求失败");
        Log.d(TAG, "onFailure: " + responseString);
        JSONObject jsonObject = new JSONObject();
        faile(jsonObject);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        Log.i(TAG,"请求成功");
        try {
            JSONObject jsonObject = new JSONObject(responseString);
            success(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
