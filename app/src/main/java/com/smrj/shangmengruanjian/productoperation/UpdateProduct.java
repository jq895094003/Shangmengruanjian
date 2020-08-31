package com.smrj.shangmengruanjian.productoperation;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.bean.ProductBean;
import com.smrj.shangmengruanjian.progressdialog.ProgressDialogUtil;
import com.smrj.shangmengruanjian.util.MyUrl;
import com.smrj.shangmengruanjian.zxing.android.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class UpdateProduct extends AppCompatActivity {

    @BindView(R.id.product_barcode)
    EditText productBarcode;
    @BindView(R.id.product_barbtn)
    Button productBarbtn;
    @BindView(R.id.product_opname)
    EditText productOpname;
    @BindView(R.id.product_optype)
    EditText productOptype;
    @BindView(R.id.product_opunit)
    EditText productOpunit;
    @BindView(R.id.product_opfull)
    EditText productOpfull;
    @BindView(R.id.product_oplose)
    EditText productOplose;
    @BindView(R.id.product_opwarranty)
    EditText productOpwarranty;
    @BindView(R.id.product_opABC)
    EditText productOpABC;
    @BindView(R.id.product_optaxratein)
    EditText productOptaxratein;
    @BindView(R.id.product_optaxrateout)
    EditText productOptaxrateout;
    @BindView(R.id.product_oppack)
    EditText productOppack;
    @BindView(R.id.product_opstatus)
    EditText productOpstatus;
    @BindView(R.id.product_opspec)
    EditText productOpspec;
    @BindView(R.id.product_opcolor)
    EditText productOpcolor;
    @BindView(R.id.product_opsize)
    EditText productOpsize;
    @BindView(R.id.product_procommit)
    Button productProcommit;
    String TAG = "myTag";
    private String TICKETNO = "";
    String result = "";
    String productType = "";
    private ProductBean productBean = new ProductBean();
    private SharedPreferences sharedPreferences;
    private ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil();

    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;
    public static final int RESULT_OK = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_operation);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
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
        MyAsyncClient.doPost(new MyUrl().getFindAllQX(), localRequestParams, new MyResponseHandler(UpdateProduct.this) {
            @Override
            public void success(JSONObject jsonObject) {
                JSONArray localJSONArray = jsonObject.optJSONArray("data");
                Log.i("checkPermission", String.valueOf(localJSONArray));
                if (!jsonObject.optString("code").equals("200")) {
                    Toast.makeText(UpdateProduct.this, "您没有此权限", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String[] arrayOfString = new String[localJSONArray.length()];
                for (int i = 0; i < localJSONArray.length(); i++) {
                    arrayOfString[i] = localJSONArray.optString(i);
                }
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(UpdateProduct.this);
                localBuilder.setTitle("请选择要更改的商铺");
                localBuilder.setSingleChoiceItems(arrayOfString, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result = arrayOfString[which];
                        newTicketNoMethod();
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

    private void newTicketNoMethod() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("DTICKETKIND", "0221");
        MyAsyncClient.doPost("http://192.168.1.88:8081/BESPHONE/ticket/newTicketNo.action", requestParams, new MyResponseHandler(UpdateProduct.this) {
            @Override
            public void success(JSONObject jsonObject) {
                Log.d(TAG, "success: " + jsonObject);
                TICKETNO = jsonObject.optString("data");
                updateProductMethod();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    private void updateProductMethod() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("itemsTable", "ITEMS_THINGUPDATE");
        requestParams.put("ticketTable", "TICKET_THINGUPDATE");
        Map<String, String> ticketMap = new HashMap<>();
        ticketMap.put("DTICKETNO", "'" + TICKETNO + "'");
        ticketMap.put("DPLACE", "'" + result + "'");
        ticketMap.put("DPLACEINPUT", "'" + sharedPreferences.getString("shopno", "") + "'");
        ticketMap.put("DSETMAN", "'" + sharedPreferences.getString("workercode", "") + "'");
        ticketMap.put("DSETDATE", "GETDATE()");
        ticketMap.put("DOPERATOR", "'" + sharedPreferences.getString("workercode", "") + "'");
        ticketMap.put("DDATEINPUT", "GETDATE()");
        ticketMap.put("DDATEDZ", "GETDATE()");
        ticketMap.put("DSTATERUN", "'0'");
        ticketMap.put("DVERIFY", "'0'");
        Map<String, String> itemsMap = new HashMap<>();
        itemsMap.put("DTICKETNO", "'" + TICKETNO + "'");
        itemsMap.put("DBARCODE", "'" + productBean.getDmasterbarcode() + "'");
        itemsMap.put("DTHINGCODE", "'" + productBean.getDthingcode() + "'");
        if (productOpunit.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DUNITNAME", "'" + productOpunit.getText().toString() + "'");
        }
        itemsMap.put("DKINDCODE", "'" + productType + "'");
        if (productOpABC.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DABC", "'" + productOpABC.getText().toString() + "'");
        }
        if (productOplose.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DSHL", "'" + productOplose.getText().toString() + "'");
        }
        if (productOpcolor.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DCOLOR", "'" + productOpcolor.getText().toString() + "'");
        }
        if (productOpsize.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DSIZE", "'" + productOpsize.getText().toString() + "'");
        }
        if (productOptaxrateout.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DSALERATE", "'" + productOptaxrateout.getText().toString() + "'");
        }
        if (productOptaxratein.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DRATE", productOptaxratein.getText().toString());
        }
        if (productOpwarranty.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DPASSTIME", "'" + productOpwarranty.getText().toString() + "'");
        }
        if (productOpname.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DNAME", "'" + productOpname.getText().toString() + "'");
        }
        if (productOpfull.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DFULLNAME", "'" + productOpfull.getText().toString() + "'");
        }
        if (productOpspec.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DSPEC", "'" + productOpspec.getText().toString() + "'");
        }
        if (productOppack.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DPACK", "'" + productOppack.getText().toString() + "'");
        }
        if (productOpstatus.getText().toString().trim().equals("")) {
        } else {
            itemsMap.put("DTHINGSTATE", "'" + productOpstatus.getText().toString() + "'");
        }


        JSONObject ticketJson = new JSONObject(ticketMap);
        requestParams.put("ticket", ticketJson);
        JSONArray itemsJSONARRAY = new JSONArray();
        JSONObject itemsJson = new JSONObject(itemsMap);
        itemsJSONARRAY.put(itemsJson);
        requestParams.put("items", itemsJSONARRAY);
        MyAsyncClient.doPost(new MyUrl().getInsertTable(), requestParams, new MyResponseHandler(UpdateProduct.this) {
            @Override
            public void success(JSONObject jsonObject) {
                if (jsonObject.optString("code").equals("200")) {
                    Toast.makeText(UpdateProduct.this, "数据保存成功！", Toast.LENGTH_SHORT).show();
                    UpdateProduct.this.finish();
                    return;
                }
                Log.d(TAG, "success: " + jsonObject);
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    private void barCodeQueryMethod() {
        progressDialogUtil.showProgressDialog(UpdateProduct.this);
        RequestParams requestParams = new RequestParams();
        requestParams.put("dbarcode", productBarcode.getText().toString());
        MyAsyncClient.doPost(new MyUrl().getProductUpdateBarcode(), requestParams, new MyResponseHandler(UpdateProduct.this) {
            @Override
            public void success(JSONObject jsonObject) {
                Log.d(TAG, "success: " + jsonObject);
                if (jsonObject.optInt("code") == 200) {
                    productBean = new Gson().fromJson(String.valueOf(jsonObject.optJSONObject("data")), ProductBean.class);
                    productType = jsonObject.optJSONObject("data").optString("dkindcode");
                    productOpname.setText(jsonObject.optJSONObject("data").optString("dname"));
                    productOpunit.setText(jsonObject.optJSONObject("data").optString("dunitname"));
                    productOpfull.setText(jsonObject.optJSONObject("data").optString("dfullname"));
                    productOplose.setText(jsonObject.optJSONObject("data").optString("dshl"));
                    productOpwarranty.setText(jsonObject.optJSONObject("data").optString("dpasstime"));
                    productOpABC.setText(jsonObject.optJSONObject("data").optString("dabc"));
                    productOptaxratein.setText(jsonObject.optJSONObject("data").optString("drate"));
                    productOptaxrateout.setText(jsonObject.optJSONObject("data").optString("dsalerate"));
                    productOppack.setText(jsonObject.optJSONObject("data").optString("dpack"));
                    productOpstatus.setText(jsonObject.optJSONObject("data").optString("dthingtype"));
                    productOpspec.setText(jsonObject.optJSONObject("data").optString("dspec"));
                    productOpcolor.setText(jsonObject.optJSONObject("data").optString("dcolor"));
                    productOpsize.setText(jsonObject.optJSONObject("data").optString("dsize"));
                    productTypeMethod(jsonObject.optJSONObject("data").optString("dkindcode"));
                }
                progressDialogUtil.dismiss(UpdateProduct.this);
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    private void productTypeMethod(String typeCode) {
        Log.d(TAG, "productTypeMethod: " + typeCode);
        RequestParams requestParams = new RequestParams();
        requestParams.put("tableName", "F_THING_KIND");
        requestParams.put("vaules", "AND DKINDCODE = '" + typeCode + "'");
        MyAsyncClient.doPost(new MyUrl().getFindAllData(), requestParams, new MyResponseHandler(UpdateProduct.this) {
            @Override
            public void success(JSONObject jsonObject) {
                if (jsonObject.optInt("code") == 200) {
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        productOptype.setText(jsonArray.optJSONObject(i).optString("DKINDNAME"));
                    }
                }
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    @OnClick({R.id.product_barbtn, R.id.product_procommit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.product_barbtn:

                //动态权限申请
                if (ContextCompat.checkSelfPermission(UpdateProduct.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UpdateProduct.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    goScan();
                }
                break;
            case R.id.product_procommit:
                checkPermission();
                break;
        }
    }


    /**
     * 跳转到扫码界面扫码
     */
    private void goScan() {
        Intent intent = new Intent(UpdateProduct.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                //返回的文本内容
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                //返回的BitMap图像
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
                productBarcode.setText(content);
                barCodeQueryMethod();
            }
        }
    }
}
