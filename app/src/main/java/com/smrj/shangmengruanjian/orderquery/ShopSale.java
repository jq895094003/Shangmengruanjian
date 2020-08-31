package com.smrj.shangmengruanjian.orderquery;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.adapter.ShopSaleAdapter;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.bean.ShopSaleEntity;
import com.smrj.shangmengruanjian.util.MyUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopSale extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.detail_fragment_list)
    PullToRefreshListView pullToRefreshListView;
    @BindView(R.id.begindate_filtrate)
    EditText startEdit;
    @BindView(R.id.stopdate_filtrate)
    EditText endEdit;
//    @BindView(R.id.fil_param)
//    EditText filParam;


    @BindView(R.id.DMONEYSS)
    EditText DMONEYSS;

    @BindView(R.id.DMONEYSSCZK)
    EditText DMONEYSSCZK;

    @BindView(R.id.DMONEYSSZFB)
    EditText DMONEYSSZFB;

    @BindView(R.id.DMONEYSSWX)
    EditText DMONEYSSWX;

    @BindView(R.id.DMONEYZK)
    EditText DMONEYZK;

    @BindView(R.id.DMONEYTH)
    EditText DMONEYTH;

    @BindView(R.id.DMONEYSSXJ)
    EditText DMONEYSSXJ;

    @BindView(R.id.DML)
    EditText DML;

    @BindView(R.id.DWATER)
    EditText DWATER;

    @BindView(R.id.fil_button)
    Button query;
    ArrayList<ShopSaleEntity> salesEntities = new ArrayList<>();
    ShopSaleAdapter listViewAdapter;
    Integer index = 1;

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
        setContentView(R.layout.shopsale);
        ButterKnife.bind(this);
        listViewAdapter = new ShopSaleAdapter(salesEntities);
        pullToRefreshListView.setAdapter(listViewAdapter);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        startEdit.setText(simpleDateFormat.format(new Date()));
        endEdit.setText(simpleDateFormat.format(new Date()));
        startEdit.setOnClickListener(this);
        endEdit.setOnClickListener(this);
        query.setOnClickListener(this);
        detailQueryMethod();
        this.pullToRefreshListView.setOnRefreshListener(this.mListViewOnRefreshListener2);
        this.pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void detailQueryMethod() {
        RequestParams requestParams = new RequestParams();
//        if (!filParam.getText().toString().trim().equals("")) {
//            requestParams.put("DBARCODE", filParam.getText().toString());
//        }
        requestParams.put("begin", startEdit.getText().toString());
        requestParams.put("end", endEdit.getText().toString());
        requestParams.put("page", index);
        MyAsyncClient.doPost(new MyUrl().getQueryAllShopSale(), requestParams, new MyResponseHandler(ShopSale.this) {
            @Override
            public void success(JSONObject jsonObject) {
                JSONObject dataJsonObject = jsonObject.optJSONObject("data");
                Gson gson = new Gson();
                System.out.println(dataJsonObject);
                if (dataJsonObject.optInt("total") > 0) {
                    JSONArray jsonArray = dataJsonObject.optJSONArray("list");
                    for (int i = 0; i < jsonArray.length() - 1; i++) {
                        ShopSaleEntity ShopSaleEntity = gson.fromJson(String.valueOf(jsonArray.optJSONObject(i)), ShopSaleEntity.class);
                        System.out.println("ShopSaleEntity ================= " + ShopSaleEntity);
                        salesEntities.add(ShopSaleEntity);
                    }
                    JSONObject sum = jsonArray.optJSONObject(jsonArray.length() - 1);
                    try {
                        DMONEYSS.setText(sum.getString("DMONEYSS"));
                        DMONEYSSCZK.setText(sum.getString("DMONEYSSCZK"));
                        DMONEYSSZFB.setText(sum.getString("DMONEYSSZFB"));
                        DMONEYSSWX.setText(sum.getString("DMONEYSSWX"));
                        DMONEYZK.setText(sum.getString("DMONEYZK"));
                        DMONEYTH.setText(sum.getString("DMONEYTH"));
                        DMONEYSSXJ.setText(sum.getString("DMONEYSSXJ"));
                        DML.setText(sum.getString("DML"));
                        DWATER.setText(sum.getString("DWATER"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    DMONEYSS.setText("");
                    DMONEYSSCZK.setText("");
                    DMONEYSSZFB.setText("");
                    DMONEYSSWX.setText("");
                    DMONEYZK.setText("");
                    DMONEYTH.setText("");
                    DMONEYSSXJ.setText("");
                    DML.setText("");
                    DWATER.setText("");
                    Toast.makeText(ShopSale.this, "查询无数据", Toast.LENGTH_SHORT).show();
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(ShopSale.this, new DatePickerDialog.OnDateSetListener() {
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
            case R.id.stopdate_filtrate:
                Calendar calendar1 = Calendar.getInstance();
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(ShopSale.this, new DatePickerDialog.OnDateSetListener() {
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
            case R.id.fil_button:
                salesEntities.clear();
                detailQueryMethod();
                break;
        }
    }
}
