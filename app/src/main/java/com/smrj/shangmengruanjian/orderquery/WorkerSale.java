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
import com.smrj.shangmengruanjian.adapter.WorkerSaleAdapter;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.bean.WorkerSaleEntity;
import com.smrj.shangmengruanjian.util.MyUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkerSale extends AppCompatActivity implements View.OnClickListener {

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
    ArrayList<WorkerSaleEntity> workerSaleEntities = new ArrayList<>();
    WorkerSaleAdapter listViewAdapter;
    Integer index = 1;

    private PullToRefreshBase.OnRefreshListener2<ListView> mListViewOnRefreshListener2 = new PullToRefreshBase.OnRefreshListener2<ListView>() {
        public void onPullDownToRefresh(PullToRefreshBase<ListView> param1PullToRefreshBase) {
            pullToRefreshListView.postDelayed(new Runnable() {
                public void run() {
                    workerSaleEntities.clear();
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
        setContentView(R.layout.workersale);
        ButterKnife.bind(this);
        listViewAdapter = new WorkerSaleAdapter(workerSaleEntities);
        pullToRefreshListView.setAdapter(listViewAdapter);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        startEdit.setText(simpleDateFormat.format(new Date()));
//        endEdit.setText(simpleDateFormat.format(new Date()));
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
        requestParams.put("dworkername", filParam.getText().toString());
        MyAsyncClient.doPost(new MyUrl().getQueryAllWorkerSale(), requestParams, new MyResponseHandler(WorkerSale.this) {
            @Override
            public void success(JSONObject jsonObject) {
                JSONObject dataJsonObject = jsonObject.optJSONObject("data");
                Gson gson = new Gson();
                if (dataJsonObject.optInt("total") > 0) {
                    JSONArray jsonArray = dataJsonObject.optJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        WorkerSaleEntity WorkerSaleEntity = gson.fromJson(String.valueOf(jsonArray.optJSONObject(i)), WorkerSaleEntity.class);
                        workerSaleEntities.add(WorkerSaleEntity);
                    }
                } else {
                    Toast.makeText(WorkerSale.this, "查询无数据", Toast.LENGTH_SHORT).show();
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(WorkerSale.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(WorkerSale.this, new DatePickerDialog.OnDateSetListener() {
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
                workerSaleEntities.clear();
                detailQueryMethod();
                break;
        }
    }
}
