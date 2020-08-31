package com.smrj.shangmengruanjian.orderquery;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.adapter.StoreminusAdapter;
import com.smrj.shangmengruanjian.adapter.ThingGRKNoSale;
import com.smrj.shangmengruanjian.adapter.ThingMinusAdapter;
import com.smrj.shangmengruanjian.adapter.ThingPassTime;
import com.smrj.shangmengruanjian.adapter.ThingStoreCQAdapter;
import com.smrj.shangmengruanjian.adapter.ThingStoreEhightAdapter;
import com.smrj.shangmengruanjian.adapter.ThingStoreZeroAdapter;
import com.smrj.shangmengruanjian.adapter.ThingZXAdapter;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.bean.AbnormalProductBean;
import com.smrj.shangmengruanjian.progressdialog.ProgressDialogUtil;
import com.smrj.shangmengruanjian.util.MyUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EightAbnormalProduct extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemLongClickListener {

    @BindView(R.id.report_abnormal_type)
    Spinner reportAbnormalType;
    @BindView(R.id.report_abnormal_list)
    ListView reportAbnormalList;
    private static final String STOREMINUS = "V_QUERY_STOREMINUS";
    private static final String THINGEMINUS = "V_QUERY_THINGEMINUS";
    private static final String THINGZX = "V_QUERY_THINGZX";
    private static final String THINGRKNOSALE = "V_QUERY_THINGRKNOSALE";
    private static final String THINGPASSTIME = "V_SPFX_THINGPASSTIME";
    private static final String THINGSTORECQ = "V_QUERY_THINGSTORECQ";
    private static final String THINGSTOREhight = "V_QUERY_THINGSTOREhight";
    private static final String THINGSTOREZERO = "V_QUERY_THINGSTOREZERO";
    @BindView(R.id.report_abnormal_shop)
    Spinner reportAbnormalShop;
    private Map<String, String> placeMap = new HashMap<>();
    private String TAG = "EIGHTABNORMALPRODUCT";
    private ArrayList<AbnormalProductBean> abnormalProductBeans = new ArrayList<>();
    private ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_abnormal_product);
        ButterKnife.bind(this);
        reportAbnormalType.setOnItemSelectedListener(this);
        reportAbnormalList.setOnItemLongClickListener(this);
        reportAbnormalShop.setOnItemSelectedListener(this);
        findAllData("F_SHOP", "");
    }

    private void findAllData(String tableName, String shopNo) {
        progressDialogUtil.showProgressDialog(this);
        RequestParams requestParams = new RequestParams();
        if (tableName.equals("F_SHOP")) {
            requestParams.put("tableName", tableName);
        } else {
            requestParams.put("tableName", tableName);
            requestParams.put("vaules", "AND DSHOPNO = '" + shopNo + "'");
        }
        MyAsyncClient.doPost(new MyUrl().getFindAllData(), requestParams, new MyResponseHandler(EightAbnormalProduct.this) {
            @Override
            public void success(JSONObject jsonObject) {
                progressDialogUtil.dismiss(EightAbnormalProduct.this);
                if (jsonObject.optInt("code") == 200) {
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    if (tableName.equals("F_SHOP")) {
                        placeMap.clear();
                        String[] items = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            items[i] = jsonArray.optJSONObject(i).optString("DSHOPNAME");
                            placeMap.put(jsonArray.optJSONObject(i).optString("DSHOPNO"), jsonArray.optJSONObject(i).optString("DSHOPNAME"));
                        }
                        ArrayAdapter<String> placeAdapter = new ArrayAdapter<String>(EightAbnormalProduct.this, android.R.layout.simple_dropdown_item_1line, items);
                        reportAbnormalShop.setAdapter(placeAdapter);
                    } else {
                        JSONArray jsonArray1 = jsonObject.optJSONArray("data");
                        if (tableName.equals(STOREMINUS)){
                            abnormalProductBeans.clear();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                AbnormalProductBean abnormalProductBean = new Gson().fromJson(String.valueOf(jsonArray1.optJSONObject(i)),AbnormalProductBean.class);
                                abnormalProductBeans.add(abnormalProductBean);
                            }
                            StoreminusAdapter storeminusAdapter = new StoreminusAdapter(abnormalProductBeans);
                            reportAbnormalList.setAdapter(storeminusAdapter);
                        }else if (tableName.equals(THINGEMINUS)){
                            abnormalProductBeans.clear();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                AbnormalProductBean abnormalProductBean = new Gson().fromJson(String.valueOf(jsonArray1.optJSONObject(i)),AbnormalProductBean.class);
                                abnormalProductBeans.add(abnormalProductBean);
                            }
                            ThingMinusAdapter storeminusAdapter = new ThingMinusAdapter(abnormalProductBeans);
                            reportAbnormalList.setAdapter(storeminusAdapter);
                        }else if (tableName.equals(THINGZX)){
                            abnormalProductBeans.clear();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                AbnormalProductBean abnormalProductBean = new Gson().fromJson(String.valueOf(jsonArray1.optJSONObject(i)),AbnormalProductBean.class);
                                abnormalProductBeans.add(abnormalProductBean);
                            }
                            ThingZXAdapter storeminusAdapter = new ThingZXAdapter(abnormalProductBeans);
                            reportAbnormalList.setAdapter(storeminusAdapter);
                        }else if (tableName.equals(THINGRKNOSALE)){
                            abnormalProductBeans.clear();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                AbnormalProductBean abnormalProductBean = new Gson().fromJson(String.valueOf(jsonArray1.optJSONObject(i)),AbnormalProductBean.class);
                                abnormalProductBeans.add(abnormalProductBean);
                            }
                            ThingGRKNoSale storeminusAdapter = new ThingGRKNoSale(abnormalProductBeans);
                            reportAbnormalList.setAdapter(storeminusAdapter);
                        }else if (tableName.equals(THINGPASSTIME)){
                            abnormalProductBeans.clear();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                AbnormalProductBean abnormalProductBean = new Gson().fromJson(String.valueOf(jsonArray1.optJSONObject(i)),AbnormalProductBean.class);
                                abnormalProductBeans.add(abnormalProductBean);
                            }
                            ThingPassTime storeminusAdapter = new ThingPassTime(abnormalProductBeans);
                            reportAbnormalList.setAdapter(storeminusAdapter);
                        }else if (tableName.equals(THINGSTORECQ)){
                            abnormalProductBeans.clear();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                AbnormalProductBean abnormalProductBean = new Gson().fromJson(String.valueOf(jsonArray1.optJSONObject(i)),AbnormalProductBean.class);
                                abnormalProductBeans.add(abnormalProductBean);
                            }
                            ThingStoreCQAdapter storeminusAdapter = new ThingStoreCQAdapter(abnormalProductBeans);
                            reportAbnormalList.setAdapter(storeminusAdapter);
                        }else if (tableName.equals(THINGSTOREhight)){
                            abnormalProductBeans.clear();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                AbnormalProductBean abnormalProductBean = new Gson().fromJson(String.valueOf(jsonArray1.optJSONObject(i)),AbnormalProductBean.class);
                                abnormalProductBeans.add(abnormalProductBean);
                            }
                            ThingStoreEhightAdapter storeminusAdapter = new ThingStoreEhightAdapter(abnormalProductBeans);
                            reportAbnormalList.setAdapter(storeminusAdapter);
                        }else if (tableName.equals(THINGSTOREZERO)){
                            abnormalProductBeans.clear();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                AbnormalProductBean abnormalProductBean = new Gson().fromJson(String.valueOf(jsonArray1.optJSONObject(i)),AbnormalProductBean.class);
                                abnormalProductBeans.add(abnormalProductBean);
                            }
                            ThingStoreZeroAdapter storeminusAdapter = new ThingStoreZeroAdapter(abnormalProductBeans);
                            reportAbnormalList.setAdapter(storeminusAdapter);
                        }

                    }
                }else if (jsonObject.optInt("code") == 201){
                    abnormalProductBeans.clear();
                    StoreminusAdapter storeminusAdapter = new StoreminusAdapter(abnormalProductBeans);
                    reportAbnormalList.setAdapter(storeminusAdapter);

                }
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView textView = (TextView) view;
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(24);
        String tableName = "";
        String shopNo = "";
        if (reportAbnormalType.getSelectedItem().toString().equals("负库存商品")) {
            tableName = STOREMINUS;
        }
        if (reportAbnormalType.getSelectedItem().toString().equals("负毛利商品")) {
            tableName = THINGEMINUS;
        }
        if (reportAbnormalType.getSelectedItem().toString().equals("滞销商品")) {
            tableName = THINGZX;
        }
        if (reportAbnormalType.getSelectedItem().toString().equals("进货未销商品")) {
            tableName = THINGRKNOSALE;
        }
        if (reportAbnormalType.getSelectedItem().toString().equals("临过期商品")) {
            tableName = THINGPASSTIME;
        }
        if (reportAbnormalType.getSelectedItem().toString().equals("畅缺商品")) {
            tableName = THINGSTORECQ;
        }
        if (reportAbnormalType.getSelectedItem().toString().equals("高库存商品")) {
            tableName = THINGSTOREhight;
        }
        if (reportAbnormalType.getSelectedItem().toString().equals("正常品库存为零")) {
            tableName = THINGSTOREZERO;
        }
        for (Map.Entry entry : placeMap.entrySet()) {
            if (entry.getValue().equals(reportAbnormalShop.getSelectedItem().toString())) {
                shopNo = entry.getKey().toString();
            }
        }
        findAllData(tableName, shopNo);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}
