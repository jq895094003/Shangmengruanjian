package com.smrj.shangmengruanjian.workeropration;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.productoperation.UpdateProduct;
import com.smrj.shangmengruanjian.util.MyUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkerAdd extends AppCompatActivity implements TextWatcher {

    @BindView(R.id.worker_no)
    EditText workerNo;
    @BindView(R.id.worker_name)
    EditText workerName;
    @BindView(R.id.worker_sex)
    EditText workerSex;
    @BindView(R.id.worker_shop)
    EditText workerShop;
    @BindView(R.id.worker_dept)
    EditText workerDept;
    @BindView(R.id.worker_pro)
    EditText workerPro;
    @BindView(R.id.worker_school)
    EditText workerSchool;
    @BindView(R.id.worker_born)
    EditText workerBorn;
    @BindView(R.id.worker_time)
    EditText workerTime;
    @BindView(R.id.worker_password)
    EditText workerPassword;
    @BindView(R.id.worker_level)
    EditText workerLevel;
    @BindView(R.id.worker_emaill)
    EditText workerEmaill;
    @BindView(R.id.worker_phone)
    EditText workerPhone;
    @BindView(R.id.worker_remark)
    EditText workerRemark;
    @BindView(R.id.login_reception)
    CheckBox loginReception;
    @BindView(R.id.login_backstage)
    CheckBox loginBackstage;
    @BindView(R.id.worker_commit)
    Button workerCommit;
    String TAG = "INSERTWORKER";
    @BindView(R.id.worker_money)
    EditText workerMoney;
    private String result = "";
    private String maxValue = "";
    private String workerNameZJM = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_add);
        ButterKnife.bind(this);
        findMaxValue();
        workerName.addTextChangedListener(this);
    }
    /**
     * 权限检测接口
     * 如果有权限则弹出更改商铺弹出框
     * 如果没有权限则弹出没有权限提示
     * 弹出框点击是调用查询分仓接口
     * 如果点击否则什么都不做
     */
    private void checkPermission() {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("DWORKERNO", "0000");
        MyAsyncClient.doPost(new MyUrl().getFindAllQX(), localRequestParams, new MyResponseHandler(WorkerAdd.this) {
            @Override
            public void success(JSONObject jsonObject) {
                JSONArray localJSONArray = jsonObject.optJSONArray("data");
                Log.i("checkPermission", String.valueOf(localJSONArray));
                if (!jsonObject.optString("code").equals("200")) {
                    Toast.makeText(WorkerAdd.this, "您没有此权限", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String[] arrayOfString = new String[localJSONArray.length()];
                for (int i = 0; i < localJSONArray.length(); i++) {
                    arrayOfString[i] = localJSONArray.optString(i);
                }
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(WorkerAdd.this);
                localBuilder.setTitle("请选择要更改的商铺");
                localBuilder.setSingleChoiceItems(arrayOfString, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result = arrayOfString[which];
                        insertWorkerMethod();

                        dialog.dismiss();
                    }
                });
                localBuilder.show();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }

        });
    }
    private void generatorZJM(){
        RequestParams requestParams = new RequestParams();
        requestParams.put("words",workerName.getText().toString());
        MyAsyncClient.doPost("http://192.168.1.88:8081/BESPHONE/ticket/createZJM.action", requestParams, new MyResponseHandler(WorkerAdd.this) {
            @Override
            public void success(JSONObject jsonObject) {
                if (jsonObject.optInt("code") == 200)
                workerNameZJM = jsonObject.optString("data");
                insertWorkerMethod();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }
    private void findMaxValue(){
        RequestParams requestParams = new RequestParams();
        requestParams.put("tableName","F_WORKER");
        requestParams.put("column","DWORKERCODE");
        MyAsyncClient.doPost("http://192.168.1.88:8081/BESPHONE/ticket/findMaxValue.action", requestParams, new MyResponseHandler(WorkerAdd.this) {
            @Override
            public void success(JSONObject jsonObject) {
                Log.d(TAG, "success: " + jsonObject);
                maxValue = jsonObject.optString("data");
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    private void workerPermission(){
        RequestParams requestParams = new RequestParams();
        Map<String,String> itemsMap = new HashMap<>();
        requestParams.put("itemsTable","R_SHOP_RIGHT");
        itemsMap.put("DCODE",String.valueOf(Integer.valueOf(maxValue) + 1));
        itemsMap.put("DSHOPNO",workerShop.getText().toString());
        JSONObject jsonObject = new JSONObject(itemsMap);
        requestParams.put("items",jsonObject);
        MyAsyncClient.doPost("http://192.168.1.88:8081/BESPHONE/ticket/insertTable.action", requestParams, new MyResponseHandler(WorkerAdd.this) {
            @Override
            public void success(JSONObject jsonObject) {
                Log.d(TAG, "success: " + jsonObject);
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });

    }

    private void insertWorkerMethod() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("itemsTable", "F_WORKER");
        Map<String, String> itemsMap = new HashMap<>();
        itemsMap.put("DWORKERCODE", String.valueOf(Integer.valueOf(maxValue) + 1));
        if (workerNo.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DWORKERNO", "'" + workerNo.getText().toString() + "'");
        }
        if (workerName.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DWORKERNAME", "'" + workerName.getText().toString() + "'");
        }
        if (workerSex.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DSEX", "'" + workerSex.getText().toString() + "'");
        }
        if (workerShop.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DSHOPNO", "'" + workerShop.getText().toString() + "'");
        }
        if (workerDept.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DDEPARTMENTCODE", "'" + workerDept.getText().toString() + "'");
        }
        if (workerPro.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DSTATION", "'" + workerPro.getText().toString() + "'");
        }
        if (workerSchool.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DDEGREE", workerSchool.getText().toString());
        }
        if (workerBorn.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DBORNDATE", "'" + workerBorn.getText().toString() + "'");
        }
        if (workerTime.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DWORKDATE", "'" + workerTime.getText().toString() + "'");
        }
        if (workerPassword.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DPASSWORD", "'" + workerPassword.getText().toString() + "'");
        }
        if (workerLevel.getText().toString().trim().equals("")) {
            itemsMap.put("DLEVEL", "'9'");
        } else {
            itemsMap.put("DLEVEL", "'" + workerLevel.getText().toString() + "'");
        }
        if (workerEmaill.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DEMAIL", "'" + workerEmaill.getText().toString() + "'");
        }
        if (workerPhone.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DMOBILE", "'" + workerPhone.getText().toString() + "'");
        }
        if (workerRemark.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DMEMO", "'" + workerRemark.getText().toString() + "'");
        }
        if (workerMoney.getText().toString().trim().equals("")) {
            itemsMap.put("DSALARY", "'0'");
        } else {
            itemsMap.put("DSALARY", "'" + workerMoney.getText().toString() + "'");
        }
        if (loginReception.isChecked()) {
            itemsMap.put("DCANTOPOS", "'1'");
        } else {
            itemsMap.put("DCANTOPOS", "'0'");
        }
        if (loginBackstage.isChecked()) {
            itemsMap.put("DCANTOMANAGE", "'1'");
        } else {
            itemsMap.put("DCANTOMANAGE", "'0'");
        }
        itemsMap.put("DISUSED","1");
        itemsMap.put("DBORNDATE","GETDATE()");
        itemsMap.put("DWORKDATE","GETDATE()");
        itemsMap.put("DZJM","'" + workerNameZJM + "'");
        JSONObject itemsJson = new JSONObject(itemsMap);
        requestParams.put("items", itemsJson);
        MyAsyncClient.doPost("http://192.168.1.88:8081/BESPHONE/ticket/insertTable.action", requestParams, new MyResponseHandler(WorkerAdd.this) {
            @Override
            public void success(JSONObject jsonObject) {
                Log.d(TAG, "success: " + jsonObject);
                workerPermission();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    @OnClick(R.id.worker_commit)
    public void onViewClicked() {
        generatorZJM();

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
