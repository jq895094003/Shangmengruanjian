package com.smrj.shangmengruanjian.orderquery;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
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
import com.smrj.shangmengruanjian.adapter.ListViewAdapter;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.bean.SalesEntity;
import com.smrj.shangmengruanjian.orderoperation.ReturnFactory;
import com.smrj.shangmengruanjian.util.MyUrl;
import com.smrj.shangmengruanjian.zxing.android.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.detail_fragment_list)
    PullToRefreshListView pullToRefreshListView;
    @BindView(R.id.begindate_filtrate)
    EditText startEdit;
    @BindView(R.id.stopdate_filtrate)
    EditText endEdit;
    @BindView(R.id.fil_param)
    EditText filParam;
    @BindView(R.id.fil_button)
    Button query;
    @BindView(R.id.stock_barbtn)
    Button stockBarbtn;
    ArrayList<SalesEntity> salesEntities = new ArrayList<>();
    ListViewAdapter listViewAdapter;
    Integer index = 1;

    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;
    public static final int RESULT_OK = -1;


    private PullToRefreshBase.OnRefreshListener2<ListView> mListViewOnRefreshListener2 = new PullToRefreshBase.OnRefreshListener2<ListView>() {
        public void onPullDownToRefresh(PullToRefreshBase<ListView> param1PullToRefreshBase) {
            pullToRefreshListView.postDelayed(new Runnable() {
                public void run() {
                    salesEntities.clear();
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
        setContentView(R.layout.detailfragment);
        ButterKnife.bind(this);
        listViewAdapter = new ListViewAdapter(salesEntities);
        pullToRefreshListView.setAdapter(listViewAdapter);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        startEdit.setText(simpleDateFormat.format(new Date()));
        endEdit.setText(simpleDateFormat.format(new Date()));
        startEdit.setOnClickListener(this);
        endEdit.setOnClickListener(this);
        query.setOnClickListener(this);
        stockBarbtn.setOnClickListener(this);
        detailQueryMethod();
    }

    private void detailQueryMethod(){
        RequestParams requestParams = new RequestParams();
        if (!filParam.getText().toString().trim().equals("")){
            requestParams.put("DBARCODE",filParam.getText().toString());
        }
        requestParams.put("BEGIN",startEdit.getText().toString());
        requestParams.put("END",endEdit.getText().toString());
        requestParams.put("page",index);
        MyAsyncClient.doPost(new MyUrl().getSelectSales(), requestParams, new MyResponseHandler(DetailFragment.this) {
            @Override
            public void success(JSONObject jsonObject) {
                JSONObject dataJsonObject = jsonObject.optJSONObject("data");
                Gson gson = new Gson();
                System.out.println(dataJsonObject);
                if (dataJsonObject.optInt("total") > 0){
                    JSONArray jsonArray = dataJsonObject.optJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        SalesEntity salesEntity = gson.fromJson(String.valueOf(jsonArray.optJSONObject(i)),SalesEntity.class);
                        salesEntities.add(salesEntity);
                    }
                }else {
                    Toast.makeText(DetailFragment.this,"当前没有销售记录",Toast.LENGTH_SHORT).show();
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
        switch (v.getId()){
            case R.id.begindate_filtrate:
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(DetailFragment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String formatDate;
                        String formatMonth;
                        if (month < 10){
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append("0");
                            stringBuffer.append(month + 1);
                            formatMonth = stringBuffer.toString();
                        }else {
                            formatMonth = String.valueOf(month);
                        }
                        if (dayOfMonth < 10){
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append("0" + dayOfMonth);
                            formatDate = String.valueOf(stringBuffer);
                        }else {
                            formatDate = String.valueOf(dayOfMonth);
                        }
                        startEdit.setText(String.valueOf(year) + "-" + formatMonth + "-" + formatDate);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
                datePickerDialog.show();
                startEdit.clearFocus();
                break;
            case R.id.stopdate_filtrate:
                Calendar calendar1 = Calendar.getInstance();
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(DetailFragment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String formatDate;
                        String formatMonth;
                        if (month < 10){
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append("0");
                            stringBuffer.append(month + 1);
                            formatMonth = stringBuffer.toString();
                        }else {
                            formatMonth = String.valueOf(month);
                        }
                        if (dayOfMonth < 10){
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append("0" + dayOfMonth);
                            formatDate = String.valueOf(stringBuffer);
                        }else {
                            formatDate = String.valueOf(dayOfMonth);
                        }
                        endEdit.setText(String.valueOf(year) + "-" + formatMonth + "-" + formatDate);
                    }
                },calendar1.get(Calendar.YEAR),calendar1.get(Calendar.MONTH),calendar1.get(Calendar.DATE));
                datePickerDialog1.show();
                endEdit.clearFocus();
                break;
            case R.id.fil_button:
                salesEntities.clear();
                detailQueryMethod();
                break;
            case R.id.stock_barbtn:
                if (ContextCompat.checkSelfPermission(DetailFragment.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DetailFragment.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    goScan();
                }
            default:
        }
    }

    /**
     * 跳转到扫码界面扫码
     */
    private void goScan() {
        Intent intent = new Intent(DetailFragment.this, CaptureActivity.class);
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
                filParam.setText(content);
            }
        }
    }
}
