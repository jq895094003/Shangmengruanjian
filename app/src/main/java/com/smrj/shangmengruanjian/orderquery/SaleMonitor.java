package com.smrj.shangmengruanjian.orderquery;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
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
import com.smrj.shangmengruanjian.adapter.SaleMonitorAdapter;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.bean.SaleMonitorEntity;
import com.smrj.shangmengruanjian.productoperation.CreateProduct;
import com.smrj.shangmengruanjian.util.MyUrl;
import com.smrj.shangmengruanjian.zxing.android.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaleMonitor extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    @BindView(R.id.detail_fragment_list)
    PullToRefreshListView pullToRefreshListView;

    @BindView(R.id.dinsetcode)
    EditText barcode;
    @BindView(R.id.dname)
    EditText dname;
    @BindView(R.id.dno)
    EditText dno;
    @BindView(R.id.dworker)
    EditText dworker;

//    @BindView(R.id.dkindname)
//    EditText dkindname;

    @BindView(R.id.dkindname)
    AutoCompleteTextView dkindname;

    @BindView(R.id.dprovidername)
    EditText dprovidername;

    @BindView(R.id.begindate)
    EditText startEdit;
    @BindView(R.id.enddate)
    EditText endEdit;


    @BindView(R.id.XJ)
    EditText XJ;
    @BindView(R.id.WX)
    EditText WX;
    @BindView(R.id.ZFB)
    EditText ZFB;
    @BindView(R.id.CZKMONEY)
    EditText CZKMONEY;
    @BindView(R.id.ZKMONEY)
    EditText ZKMONEY;
    @BindView(R.id.TTLMONEY)
    EditText TTLMONEY;
    @BindView(R.id.THMONEY)
    EditText THMONEY;
    @BindView(R.id.MLMONEY)
    EditText MLMONEY;
    @BindView(R.id.KLNUM)
    EditText KLNUM;
    @BindView(R.id.KDMONEY)
    EditText KDMONEY;
    @BindView(R.id.SALENUM)
    EditText SALENUM;

    @BindView(R.id.payment_type)
    Spinner paymentType;
    @BindView(R.id.negative_gross_profit)
    CheckBox negativeGrossProfit;
    @BindView(R.id.negative_inventory)
    CheckBox negativeInventory;


    private Map<String, String> goodsKinsMap = new HashMap<>();

    @BindView(R.id.btn_scan)
    Button btn_scan;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;
    public static final int RESULT_OK = -1;

    @BindView(R.id.fil_button)
    Button query;
    ArrayList<SaleMonitorEntity> saleMonitorEntities = new ArrayList<>();
    SaleMonitorAdapter listViewAdapter;
    Integer index = 1;

    private PullToRefreshBase.OnRefreshListener2<ListView> mListViewOnRefreshListener2 = new PullToRefreshBase.OnRefreshListener2<ListView>() {
        public void onPullDownToRefresh(PullToRefreshBase<ListView> param1PullToRefreshBase) {
            pullToRefreshListView.postDelayed(new Runnable() {
                public void run() {
                    saleMonitorEntities.clear();
                    index = 1;
                    detailQueryMethod();
                    pullToRefreshListView.onRefreshComplete();
                }
            }, 500);
        }

        public void onPullUpToRefresh(PullToRefreshBase<ListView> param1PullToRefreshBase) {
            pullToRefreshListView.postDelayed(new Runnable() {
                public void run() {
                    index = index + 1;
                    detailQueryMethod();
                    pullToRefreshListView.onRefreshComplete();
                }
            }, 500);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salemonitor);
        ButterKnife.bind(this);
        listViewAdapter = new SaleMonitorAdapter(saleMonitorEntities);
        pullToRefreshListView.setAdapter(listViewAdapter);
        query.setOnClickListener(this);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        startEdit.setText(simpleDateFormat.format(new Date()));
        endEdit.setText(simpleDateFormat.format(new Date()));
        startEdit.setOnClickListener(this);
        endEdit.setOnClickListener(this);

//        detailQueryMethod();
        String[] arrayOfString = new String[]{"全部", "微信", "支付宝", "现金", "储值卡"};
        ArrayAdapter localArrayAdapter = new ArrayAdapter(SaleMonitor.this, android.R.layout.simple_dropdown_item_1line, arrayOfString);
        paymentType.setAdapter(localArrayAdapter);


        findGoodsKindMethod();

        this.pullToRefreshListView.setOnRefreshListener(this.mListViewOnRefreshListener2);
        this.pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        this.btn_scan.setOnClickListener(this);
        this.btn_scan.setOnTouchListener(this);

    }

    /**
     * 获取分类下拉选
     */
    private void findGoodsKindMethod() {
        MyAsyncClient.doPost(new MyUrl().getFindKind(), new RequestParams(), new MyResponseHandler(SaleMonitor.this) {
            @Override
            public void success(JSONObject jsonObject) {
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                String[] items = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    items[i] = jsonArray.optJSONObject(i).optString("dkindname");

                    System.out.println("jsonArray.optJSONObject(i).optString(\"dkindname\") = " + jsonArray.optJSONObject(i).optString("dkindname"));

                    goodsKinsMap.put(jsonArray.optJSONObject(i).optString("dkindno"), jsonArray.optJSONObject(i).optString("dkindname"));
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SaleMonitor.this, android.R.layout.simple_dropdown_item_1line, items);
                dkindname.setAdapter(arrayAdapter);
                detailQueryMethod();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }


    private void detailQueryMethod() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("page", index);
        requestParams.put("dno", dno.getText().toString());
        requestParams.put("dname", dname.getText().toString());
        requestParams.put("dworker", dworker.getText().toString());
        if ("".equals(dkindname.getText().toString())){
            requestParams.put("dkindname", dkindname.getText().toString());
        } else {
            for (Map.Entry entry : goodsKinsMap.entrySet()) {
                if (entry.getValue().equals(dkindname.getText().toString())) {
                    requestParams.put("dkindname", entry.getKey());
//                requestParams.put("shopno", entry.getKey());
                }
            }
        }

//        requestParams.put("dkindname", dkindname.getText().toString());
        requestParams.put("dprovidername", dprovidername.getText().toString());
        requestParams.put("dinsetcode", barcode.getText().toString());
        requestParams.put("begin", startEdit.getText().toString());
        requestParams.put("payType", paymentType.getSelectedItem().toString() + "");
        requestParams.put("isFml", (negativeGrossProfit.isChecked() ? "1" : "0"));
        requestParams.put("isFkc", (negativeInventory.isChecked() ? "1" : "0"));
        requestParams.put("end", endEdit.getText().toString());
        MyAsyncClient.doPost(new MyUrl().getQueryAllSale(), requestParams, new MyResponseHandler(SaleMonitor.this) {
            @Override
            public void success(JSONObject jsonObject) {
                JSONObject dataJsonObject = jsonObject.optJSONObject("data");
                Gson gson = new Gson();
                if (dataJsonObject.optInt("total") > 0) {
                    JSONArray jsonArray = dataJsonObject.optJSONArray("list");
                    for (int i = 0; i < jsonArray.length() - 1; i++) {
                        SaleMonitorEntity saleMonitorEntity = gson.fromJson(String.valueOf(jsonArray.optJSONObject(i)), SaleMonitorEntity.class);
                        saleMonitorEntities.add(saleMonitorEntity);
                    }
                    JSONObject sum = jsonArray.optJSONObject(jsonArray.length() - 1);
                    try {
                        XJ.setText(sum.getString("XJ"));
                        WX.setText(sum.getString("WX"));
                        ZFB.setText(sum.getString("ZFB"));
                        CZKMONEY.setText(sum.getString("CZK"));
                        ZKMONEY.setText(sum.getString("ZK"));
                        TTLMONEY.setText(sum.getString("TTL"));
                        THMONEY.setText(sum.getString("TH"));
                        MLMONEY.setText(sum.getString("ML"));
                        KLNUM.setText(sum.getString("KL"));
                        if (!"0".equals(sum.getString("KL"))){
                            KDMONEY.setText (String.format("%.2f",Double.parseDouble(sum.getString("TTL"))/Double.parseDouble(sum.getString("KL"))));
                        } else {
                            KDMONEY.setText(sum.getString("ML"));
                        }
                        SALENUM.setText(sum.getString("SALENUM"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    XJ.setText("");
                    WX.setText("");
                    ZFB.setText("");
                    CZKMONEY.setText("");
                    ZKMONEY.setText("");
                    TTLMONEY.setText("");
                    THMONEY.setText("");
                    MLMONEY.setText("");
                    KLNUM.setText("");
                    KDMONEY.setText("");
                    SALENUM.setText("");
                    Toast.makeText(SaleMonitor.this, "查询无数据", Toast.LENGTH_SHORT).show();
                }
                listViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fil_button:
                saleMonitorEntities.clear();
                detailQueryMethod();
                break;
            case R.id.begindate:
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(SaleMonitor.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String formatDate;
                        String formatMonth;
                        if (month < 10) {
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append("0");
                            stringBuffer.append(month + 1);
                            formatMonth = stringBuffer.toString();
                        } else {
                            formatMonth = String.valueOf(month);
                        }
                        if (dayOfMonth < 10) {
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append("0" + dayOfMonth);
                            formatDate = String.valueOf(stringBuffer);
                        } else {
                            formatDate = String.valueOf(dayOfMonth);
                        }
                        startEdit.setText(String.valueOf(year) + "-" + formatMonth + "-" + formatDate);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                datePickerDialog.show();
                startEdit.clearFocus();
                break;
            case R.id.enddate:
                Calendar calendar1 = Calendar.getInstance();
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(SaleMonitor.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String formatDate;
                        String formatMonth;
                        if (month < 10) {
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append("0");
                            stringBuffer.append(month + 1);
                            formatMonth = stringBuffer.toString();
                        } else {
                            formatMonth = String.valueOf(month);
                        }
                        if (dayOfMonth < 10) {
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append("0" + dayOfMonth);
                            formatDate = String.valueOf(stringBuffer);
                        } else {
                            formatDate = String.valueOf(dayOfMonth);
                        }
                        endEdit.setText(String.valueOf(year) + "-" + formatMonth + "-" + formatDate);
                    }
                }, calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DATE));
                datePickerDialog1.show();
                endEdit.clearFocus();
                break;
            default:
                break;
        }
        if (v.getId() == R.id.btn_scan) {
            //动态权限申请
            if (ContextCompat.checkSelfPermission(SaleMonitor.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SaleMonitor.this, new String[]{Manifest.permission.CAMERA}, 1);
            } else {
                goScan();
            }
        }
    }

    /**
     * 跳转到扫码界面扫码
     */
    private void goScan() {
        Intent intent = new Intent(SaleMonitor.this, CaptureActivity.class);
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
                barcode.setText(content);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
