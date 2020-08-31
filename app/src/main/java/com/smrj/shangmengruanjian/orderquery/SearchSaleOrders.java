package com.smrj.shangmengruanjian.orderquery;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.adapter.SearchSaleKindOrderAdapter;
import com.smrj.shangmengruanjian.adapter.SearchSaleOrdersAdapter;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.bean.Providers;
import com.smrj.shangmengruanjian.bean.SearchSaleKindOrdersBean;
import com.smrj.shangmengruanjian.bean.SearchSaleOrdersBean;
import com.smrj.shangmengruanjian.orderoperation.CheckOrder;
import com.smrj.shangmengruanjian.progressdialog.ProgressDialogUtil;
import com.smrj.shangmengruanjian.util.MyUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchSaleOrders extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.search_sale_type)
    Spinner searchSaleType;

    @BindView(R.id.search_sale_shopno)
    Spinner searchSaleShopno;

    @BindView(R.id.search_sale_begindate)
    EditText searchSaleBegindate;

    @BindView(R.id.search_sale_enddate)
    EditText searchSaleEnddate;

    @BindView(R.id.search_sale_sortnum)
    EditText searchSaleSortnum;

    @BindView(R.id.search_sale_sortrole)
    Spinner searchSaleSortrole;

    @BindView(R.id.search_sale_protype)
    Spinner searchSaleProtype;

    @BindView(R.id.search_sale_sorttype)
    Spinner searchSaleSorttype;

    @BindView(R.id.search_sale_orderbtn)
    Button searchSaleOrderbtn;

    @BindView(R.id.search_order_list)
    ListView searchOrderList;

    @BindView(R.id.provide)
    Spinner provide;

//    @BindView(R.id.inpricee)
//    Spinner inpricee;
//
//    @BindView(R.id.saleprice)
//    Spinner saleprice;

    private Map<String, String> proTypeMap = new HashMap<>();
    private Map<String, String> providerMap = new HashMap<>();
    private Map<String, String> placeMap = new HashMap<>();
    private ArrayList<SearchSaleOrdersBean> searchSaleOrdersBeans = new ArrayList<>();
    private ArrayList<SearchSaleKindOrdersBean> searchSaleKindOrdersBeans = new ArrayList<>();
    private ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_sale_orders);
        ButterKnife.bind(this);
        searchSaleSortnum.setText("50");
        findAllData("F_THING_KIND");
        findAllData("F_SHOP");
        findAllData("F_PROVIDER");
//        condition(inpricee);
//        condition(saleprice);


        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        searchSaleEnddate.setText(simpleDateFormat.format(date));
        searchSaleBegindate.setText(simpleDateFormat.format(date));
        searchSaleShopno.setOnItemSelectedListener(this);
        searchSaleType.setOnItemSelectedListener(this);
        searchSaleBegindate.setOnClickListener(this);
        searchSaleEnddate.setOnClickListener(this);
        searchSaleOrderbtn.setOnClickListener(this);
    }


    private void condition(Spinner sprinner) {
        String[] items = new String[]{"大于", "小于","等于"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SearchSaleOrders.this, android.R.layout.simple_dropdown_item_1line, items);
        sprinner.setAdapter(arrayAdapter);
    }

    private void findAllData(String tableName) {
        progressDialogUtil.showProgressDialog(SearchSaleOrders.this);
        RequestParams requestParams = new RequestParams();
        requestParams.put("tableName", tableName);

        MyAsyncClient.doPost(new MyUrl().getFindAllData(), requestParams, new MyResponseHandler(SearchSaleOrders.this) {
            @Override
            public void success(JSONObject jsonObject) {
                progressDialogUtil.dismiss(SearchSaleOrders.this);
                if (tableName.equals("F_THING_KIND")) {
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
//                    String[] items = new String[jsonArray.length()];
                    List<String> items = new ArrayList<>();
                    items.add("所有货类");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        proTypeMap.put(jsonArray.optJSONObject(i).optString("DKINDNO"), jsonArray.optJSONObject(i).optString("DKINDNAME"));
//                        items[i] = jsonArray.optJSONObject(i).optString("DKINDNAME");
                        items.add(jsonArray.optJSONObject(i).optString("DKINDNAME"));
                    }
                    ArrayAdapter<String> proTypeAdapter = new ArrayAdapter<String>(SearchSaleOrders.this, android.R.layout.simple_spinner_dropdown_item, items);
                    searchSaleProtype.setAdapter(proTypeAdapter);
                } else if (tableName.equals("F_SHOP")) {
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    String[] items = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        items[i] = jsonArray.optJSONObject(i).optString("DSHOPNAME");
                        placeMap.put(jsonArray.optJSONObject(i).optString("DSHOPNO"), jsonArray.optJSONObject(i).optString("DSHOPNAME"));
                    }
                    ArrayAdapter<String> shopAdapter = new ArrayAdapter<String>(SearchSaleOrders.this, android.R.layout.simple_dropdown_item_1line, items);
                    searchSaleShopno.setAdapter(shopAdapter);
                } else if (tableName.equals("F_PROVIDER")) {
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    List<String> items = new ArrayList<>();
                    items.add("所有供应商");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        providerMap.put(jsonArray.optJSONObject(i).optString("DPROVIDERNO"), jsonArray.optJSONObject(i).optString("DPROVIDERNAME"));
                        items.add(jsonArray.optJSONObject(i).optString("DPROVIDERNAME"));
                    }
                    ArrayAdapter<String> proTypeAdapter = new ArrayAdapter<String>(SearchSaleOrders.this, android.R.layout.simple_spinner_dropdown_item, items);
                    provide.setAdapter(proTypeAdapter);
                }
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }


    private void searchSaleOrderMethod() {
        progressDialogUtil.showProgressDialog(SearchSaleOrders.this);
        RequestParams requestParams = new RequestParams();
        for (Map.Entry entry : placeMap.entrySet()) {
            if (entry.getValue().equals(searchSaleShopno.getSelectedItem().toString())) {
                requestParams.put("shopno", entry.getKey());
            }
        }
        if (!"所有货类".equals(searchSaleProtype.getSelectedItem().toString())) {
            for (Map.Entry entry : proTypeMap.entrySet()) {
                if (entry.getValue().equals(searchSaleProtype.getSelectedItem().toString())) {
                    requestParams.put("kind", entry.getKey());
                }
            }
        } else {
            requestParams.put("kind", "");
        }
        if (!"所有供应商".equals(provide.getSelectedItem().toString())) {
            for (Map.Entry entry : providerMap.entrySet()) {
                if (entry.getValue().equals(provide.getSelectedItem().toString())) {
                    requestParams.put("provider", entry.getKey());
                }
            }
        } else {
            requestParams.put("provider", "");
        }
        requestParams.put("sortname", searchSaleSortrole.getSelectedItem().toString());
        requestParams.put("datebgn", searchSaleBegindate.getText().toString());
        requestParams.put("dateend", searchSaleEnddate.getText().toString());
        requestParams.put("sortnum", searchSaleSortnum.getText().toString());
        requestParams.put("orderfc", searchSaleSorttype.getSelectedItem().toString());
        MyAsyncClient.doPost(new MyUrl().getSearchSaleorderTj(), requestParams, new MyResponseHandler(SearchSaleOrders.this) {
            @Override
            public void success(JSONObject jsonObject) {
                searchSaleOrdersBeans.clear();
                progressDialogUtil.dismiss(SearchSaleOrders.this);
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                Gson gson = new Gson();
                for (int i = 0; i < jsonArray.length(); i++) {
                    System.out.println(jsonArray.optJSONObject(i));
                    SearchSaleOrdersBean searchSaleKindOrdersBean = gson.fromJson(String.valueOf(jsonArray.optJSONObject(i)), SearchSaleOrdersBean.class);
                    searchSaleOrdersBeans.add(searchSaleKindOrdersBean);
                }
                SearchSaleOrdersAdapter searchSaleOrdersAdapter = new SearchSaleOrdersAdapter(searchSaleOrdersBeans);
                searchOrderList.setAdapter(searchSaleOrdersAdapter);
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    private void searchSaleKindOrderMethod() {
        progressDialogUtil.showProgressDialog(SearchSaleOrders.this);
        RequestParams requestParams = new RequestParams();
        for (Map.Entry entry : placeMap.entrySet()) {
            if (entry.getValue().equals(searchSaleShopno.getSelectedItem().toString())) {
                requestParams.put("shopno", entry.getKey());
            }
        }
        requestParams.put("sortname", searchSaleSortrole.getSelectedItem().toString());
        requestParams.put("datebgn", searchSaleBegindate.getText().toString());
        requestParams.put("dateend", searchSaleEnddate.getText().toString());
        requestParams.put("sortnum", searchSaleSortnum.getText().toString());
        MyAsyncClient.doPost(new MyUrl().getSearchKingSaleorderTj(), requestParams, new MyResponseHandler(SearchSaleOrders.this) {
            @Override
            public void success(JSONObject jsonObject) {
                progressDialogUtil.dismiss(SearchSaleOrders.this);
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                Gson gson = new Gson();
                for (int i = 0; i < jsonArray.length(); i++) {
                    SearchSaleKindOrdersBean searchSaleKindOrdersBean = gson.fromJson(String.valueOf(jsonArray.optJSONObject(i)), SearchSaleKindOrdersBean.class);
                    searchSaleKindOrdersBeans.add(searchSaleKindOrdersBean);
                }
                SearchSaleKindOrderAdapter searchSaleKindOrderAdapter = new SearchSaleKindOrderAdapter(searchSaleKindOrdersBeans);
                searchOrderList.setAdapter(searchSaleKindOrderAdapter);
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    private void dateDialogMehod(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(SearchSaleOrders.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String resultMonth = "";
                String resultDay = "";
                if (month < 10) {
                    resultMonth = "0" + (month + 1);
                } else {
                    resultMonth = String.valueOf(month + 1);
                }
                if (dayOfMonth < 10) {
                    resultDay = "0" + dayOfMonth;
                } else {
                    resultDay = String.valueOf(dayOfMonth);
                }
                editText.setText(year + "-" + resultMonth + "-" + resultDay);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        datePickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_sale_begindate:
                dateDialogMehod(searchSaleBegindate);
                break;
            case R.id.search_sale_enddate:
                dateDialogMehod(searchSaleEnddate);
                break;
            case R.id.search_sale_orderbtn:
                if (searchSaleType.getSelectedItem().toString().equals("单品销量排行")) {
                    searchSaleOrderMethod();
                } else if (searchSaleType.getSelectedItem().toString().equals("货类销量排行")) {
                    searchSaleKindOrderMethod();
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.search_sale_shopno) {
            System.out.println("检测到到了");
            TextView textView = (TextView) view;
            textView.setTextSize(20);
            textView.setGravity(Gravity.CENTER);
        } else if (parent.getId() == R.id.search_sale_type) {
            TextView textView = (TextView) view;
            textView.setTextSize(20);
            textView.setGravity(Gravity.CENTER);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
