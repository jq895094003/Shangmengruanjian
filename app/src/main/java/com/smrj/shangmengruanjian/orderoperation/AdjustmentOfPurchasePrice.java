package com.smrj.shangmengruanjian.orderoperation;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.adapter.PurchaseAdapter;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.bean.PurchaseBean;
import com.smrj.shangmengruanjian.bean.WorkerSaleEntity;
import com.smrj.shangmengruanjian.orderquery.WorkerSale;
import com.smrj.shangmengruanjian.util.MyUrl;
import com.smrj.shangmengruanjian.zxing.android.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdjustmentOfPurchasePrice extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    @BindView(R.id.changelist)
    Spinner purchaseValue;
    @BindView(R.id.change_param)
    EditText paramEt;
    @BindView(R.id.purchase_btn)
    Button purchaseBtn;
    @BindView(R.id.change_recycler)
    PullToRefreshListView recyclerView;

    @BindView(R.id.btn_scan)
    Button btn_scan;

    int page = 1;
    private ArrayList<PurchaseBean> purchaseBeans = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private PurchaseAdapter purchaseAdapter;

    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;
    public static final int RESULT_OK = -1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adjustmentofpurchaseprice);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        purchaseAdapter = new PurchaseAdapter(purchaseBeans);
        recyclerView.setAdapter(purchaseAdapter);
        recyclerView.setOnItemClickListener(this);
        purchaseBtn.setOnClickListener(this);
        btn_scan.setOnClickListener(this);
        this.recyclerView.setOnRefreshListener(this.mListViewOnRefreshListener2);
        this.recyclerView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private PullToRefreshBase.OnRefreshListener2<ListView> mListViewOnRefreshListener2 = new PullToRefreshBase.OnRefreshListener2<ListView>() {
        public void onPullDownToRefresh(PullToRefreshBase<ListView> param1PullToRefreshBase) {
            recyclerView.postDelayed(new Runnable() {
                public void run() {
                    purchaseBeans.clear();
                    page = 1;
                    detailQueryMethod(page);
                    recyclerView.onRefreshComplete();
                }
            }, 500);
        }

        public void onPullUpToRefresh(PullToRefreshBase<ListView> param1PullToRefreshBase) {
            recyclerView.postDelayed(new Runnable() {
                public void run() {
                    page = page + 1;
                    detailQueryMethod(page);
                    recyclerView.onRefreshComplete();
                }
            }, 500);
        }
    };

    private void detailQueryMethod(Integer page) {
        purchaseBeans.clear();
        RequestParams requestParams = new RequestParams();
        requestParams.put("page",page);
        requestParams.put("DSHOPNO","0000");
        MyAsyncClient.doPost(new MyUrl().getFindKH(), requestParams, new MyResponseHandler(AdjustmentOfPurchasePrice.this) {
            @Override
            public void success(JSONObject jsonObject) {
                if (jsonObject.optString("code").equals("200")){
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    Gson gson = new Gson();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        PurchaseBean purchaseBean = gson.fromJson(String.valueOf(jsonArray.optJSONObject(i)),PurchaseBean.class);
                        purchaseBeans.add(purchaseBean);
                    }
                    purchaseAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(AdjustmentOfPurchasePrice.this,jsonObject.optString("msg"),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void faile(JSONObject jsonObject) {
                Toast.makeText(AdjustmentOfPurchasePrice.this,"网络异常,请稍后重试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void chagePriceMethod(String page){
        purchaseBeans.clear();
        RequestParams requestParams = new RequestParams();
        requestParams.put("page",page);
        requestParams.put("DSHOPNO","0000");
        requestParams.put(getField(purchaseValue.getSelectedItem().toString()),paramEt.getText().toString());
        MyAsyncClient.doPost(new MyUrl().getFindKH(), requestParams, new MyResponseHandler(AdjustmentOfPurchasePrice.this) {
            @Override
            public void success(JSONObject jsonObject) {
                if (jsonObject.optString("code").equals("200")){
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    Gson gson = new Gson();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        PurchaseBean purchaseBean = gson.fromJson(String.valueOf(jsonArray.optJSONObject(i)),PurchaseBean.class);
                        purchaseBeans.add(purchaseBean);
                    }
                    purchaseAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(AdjustmentOfPurchasePrice.this,jsonObject.optString("msg"),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void faile(JSONObject jsonObject) {
                Toast.makeText(AdjustmentOfPurchasePrice.this,"网络异常,请稍后重试",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String getField(String paramString) {
        if (paramString.equals("条码"))
            return "DMASTERBARCODE";
        if (paramString.equals("内码"))
            return "DCODE";
        if (paramString.equals("商品名"))
            return "DNAME";
        if (paramString.equals("供应商编号"))
            return "DPROVIDERNO";
        if (paramString.equals("供应商名"))
            return "DPROVIDERNAME";
        if (paramString.equals("商品类型"))
            return "DTHINGTYPE";
        return "";
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent localIntent = new Intent(this, PurchaseChange.class);
        localIntent.putExtra("purchase", (Serializable) purchaseBeans.get(position - 1));
        startActivity(localIntent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.purchase_btn) {
            chagePriceMethod("1");
        } else if (v.getId() == R.id.btn_scan) {
            if (ContextCompat.checkSelfPermission(AdjustmentOfPurchasePrice.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AdjustmentOfPurchasePrice.this, new String[]{Manifest.permission.CAMERA}, 1);
            } else {
                goScan();
            }
        }

//        String type = purchaseValue.getSelectedItem().toString();
//        if ("条码".equals(type)){
//            //动态权限申请
//            if (ContextCompat.checkSelfPermission(AdjustmentOfPurchasePrice.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(AdjustmentOfPurchasePrice.this, new String[]{Manifest.permission.CAMERA}, 1);
//            } else {
//                goScan();
//            }
//        } else {
//            chagePriceMethod("1");
//        }
    }

    /**
     * 跳转到扫码界面扫码
     */
    private void goScan() {
        Intent intent = new Intent(AdjustmentOfPurchasePrice.this, CaptureActivity.class);
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
                paramEt.setText(content);
                chagePriceMethod("1");
            }
        }
    }
}
