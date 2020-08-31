package com.smrj.shangmengruanjian.orderquery;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.smrj.shangmengruanjian.adapter.VThingAdapter;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.bean.VThingBean;
import com.smrj.shangmengruanjian.productoperation.CreateProduct;
import com.smrj.shangmengruanjian.util.MyUrl;
import com.smrj.shangmengruanjian.zxing.android.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VThing extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    @BindView(R.id.detail_fragment_list)
    PullToRefreshListView pullToRefreshListView;
    @BindView(R.id.goods_name)
    EditText goodsName;
    @BindView(R.id.goods_marstbarcode)
    EditText dmarstBardode;
    @BindView(R.id.dprovidername)
    EditText dproviderName;
    @BindView(R.id.dkindname)
    EditText dkindName;
    @BindView(R.id.DBARCODETYPE)
    EditText DBARCODETYPE;
    @BindView(R.id.DTHINGTYPE)
    EditText DTHINGTYPE;
    @BindView(R.id.dshopno)
    Spinner dshopno;
    @BindView(R.id.fil_button)
    Button query;
    @BindView(R.id.btn_scan)
    Button btn_scan;

    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;
    public static final int RESULT_OK = -1;


    SharedPreferences sharedPreferences;
    ArrayList<VThingBean> vThingBeans = new ArrayList<>();
    VThingAdapter listViewAdapter;
    Integer index = 1;
    private PullToRefreshBase.OnRefreshListener2<ListView> mListViewOnRefreshListener2 = new PullToRefreshBase.OnRefreshListener2<ListView>() {
        public void onPullDownToRefresh(PullToRefreshBase<ListView> param1PullToRefreshBase) {
            pullToRefreshListView.postDelayed(new Runnable() {
                public void run() {
                    vThingBeans.clear();
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

    /**
     * 获取当前账号的门店信息权限
     */
    private void findshopNoByUserMethod() {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("DWORKERNO", sharedPreferences.getString("workerno", ""));
        MyAsyncClient.doPost(new MyUrl().getFindAllQX(), new RequestParams(), new MyResponseHandler(VThing.this) {
            @Override
            public void success(JSONObject jsonObject) {
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                String[] items = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    items[i] = jsonArray.optString(i);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(VThing.this, android.R.layout.simple_dropdown_item_1line, items);
                dshopno.setAdapter(arrayAdapter);
            }
            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);//实例化内部存储空间
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vthing);
        ButterKnife.bind(this);
        listViewAdapter = new VThingAdapter(vThingBeans);
        pullToRefreshListView.setAdapter(listViewAdapter);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        goodsName.setOnClickListener(this);
        dmarstBardode.setOnClickListener(this);
        query.setOnClickListener(this);
        query.setOnTouchListener(this);
        findshopNoByUserMethod();
        this.btn_scan.setOnClickListener(this);
        this.btn_scan.setOnTouchListener(this);
        this.pullToRefreshListView.setOnRefreshListener(this.mListViewOnRefreshListener2);
        this.pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void detailQueryMethod() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("page", index);
        requestParams.put("dshopno", dshopno.getSelectedItem().toString());
        requestParams.put("dbarcode", dmarstBardode.getText());
        requestParams.put("dname", goodsName.getText());
        requestParams.put("dprovidername", dproviderName.getText());
        requestParams.put("dkindname", dkindName.getText());
        requestParams.put("DTHINGTYPE", DTHINGTYPE.getText());
        requestParams.put("DBARCODETYPE", DBARCODETYPE.getText());
        MyAsyncClient.doPost(new MyUrl().getVthingByShopNo(), requestParams, new MyResponseHandler(VThing.this) {
            @Override
            public void success(JSONObject jsonObject) {
                JSONObject dataJsonObject = jsonObject.optJSONObject("data");
                Gson gson = new Gson();
                if (dataJsonObject.optInt("total") > 0) {
                    JSONArray jsonArray = dataJsonObject.optJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        VThingBean VThingBean = gson.fromJson(String.valueOf(jsonArray.optJSONObject(i)), VThingBean.class);
                        vThingBeans.add(VThingBean);
                    }
                } else {
                    Toast.makeText(VThing.this, "查询无数据", Toast.LENGTH_SHORT).show();
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
            case R.id.begindate_filtrate:
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(VThing.this, new DatePickerDialog.OnDateSetListener() {
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
                        goodsName.setText(String.valueOf(year) + "-" + formatMonth + "-" + formatDate);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                datePickerDialog.show();
                goodsName.clearFocus();
                break;
            case R.id.stopdate_filtrate:
                Calendar calendar1 = Calendar.getInstance();
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(VThing.this, new DatePickerDialog.OnDateSetListener() {
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
                        dmarstBardode.setText(String.valueOf(year) + "-" + formatMonth + "-" + formatDate);
                    }
                }, calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DATE));
                datePickerDialog1.show();
                dmarstBardode.clearFocus();
                break;
            case R.id.fil_button:
                vThingBeans.clear();
                detailQueryMethod();
                break;
            case R.id.btn_scan:
                //动态权限申请
                if (ContextCompat.checkSelfPermission(VThing.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(VThing.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    goScan();
                }
                break;
            default:
        }
    }

    /**
     * 跳转到扫码界面扫码
     */
    private void goScan() {
        Intent intent = new Intent(VThing.this, CaptureActivity.class);
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
                dmarstBardode.setText(content);
                detailQueryMethod();
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        index = 1;
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            v.setAlpha((float) 0.30);
        if (event.getAction() == MotionEvent.ACTION_UP)
            v.setAlpha(1);
        return false;
    }
}
