package com.smrj.shangmengruanjian.baseactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.util.MyUrl;
import com.smrj.shangmengruanjian.util.Uuid;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Register extends AppCompatActivity implements View.OnClickListener {

    /**
     * 30-41行，声明并实例化页面控件，使用ButterKnife绑定
     */
    @BindView(R.id.register_account)
    EditText account;
    private String deviceId;

    @BindView(R.id.register_name)
    EditText name;

    @BindView(R.id.register_btn)
    Button register;

    @BindView(R.id.register_telephone)
    EditText telephone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ButterKnife.bind(this);
        register.setOnClickListener(this);
    }

    private void registerMethod() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userphone", telephone.getText().toString());
        requestParams.put("account", "");
        requestParams.put("password", "");
        requestParams.put("username", name.getText().toString());
        requestParams.put("phonenumber", new Uuid(Register.this).createUUID());
        requestParams.put("appname", MyUrl.appName);
        requestParams.put("spname", account.getText().toString());
        MyAsyncClient.doPost(new MyUrl().getRegisterMethod(), requestParams, new MyResponseHandler(Register.this) {
            @Override
            public void success(JSONObject jsonObject) {
                if (jsonObject.optString("code").equals("200")) {
                    Toast.makeText(Register.this, "注册成功，请联系售后客户审核", Toast.LENGTH_SHORT).show();
                    Intent localIntent = new Intent(Register.this, Login.class);
                    startActivity(localIntent);
                    Register.this.finish();
                    return;
                }
                Toast.makeText(Register.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void faile(JSONObject jsonObject) {
                Toast.makeText(Register.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uuidCheckMethod() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("phonenumber", new Uuid(Register.this).createUUID());
        requestParams.put("appname", MyUrl.appName);
        MyAsyncClient.doPost(new MyUrl().getUuidQueryMethod(), requestParams, new MyResponseHandler(Register.this) {
            @Override
            public void success(JSONObject jsonObject) {
                String str1 = jsonObject.optString("code");
                Log.i("RegisterResult", String.valueOf(jsonObject));
                if (str1.equals("200")) {
                    Toast.makeText(Register.this, "此手机已被注册", Toast.LENGTH_SHORT).show();
                }
                if (str1.equals("400")) {
                    registerMethod();
                }
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() != R.id.register_btn) {
            return;
        } else {
            uuidCheckMethod();
        }
    }
}
