package com.smrj.shangmengruanjian.orderquery;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.adapter.ThingsaleFirstKindAdapter;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.bean.ThingSaleFirstKindBean;
import com.smrj.shangmengruanjian.util.MyUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThingSaleFirrstKind extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private static final String TAG = "ThingSaleFirstKind";
    @BindView(R.id.thingsale_datefx)
    EditText thingsaleDatefx;
    @BindView(R.id.thingsale_monthfx)
    EditText thingsaleMonthfx;
    @BindView(R.id.thingsale_btn)
    Button thingsaleBtn;
    @BindView(R.id.thingsale_list)
    ListView thingsaleList;
    @BindView(R.id.dshopno)
    Spinner dshopno;

    @BindView(R.id.thingsale_type)
    Spinner thingsaleType;

    SharedPreferences sharedPreferences;

    private LinearLayout linearLayout;

    private void findViews() {
        linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
    }


    private ArrayList<ThingSaleFirstKindBean> thingSaleFirstKindBeans = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);//实例化内部存储空间
        findshopNoByUserMethod();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thingsale_firstkind);
        ButterKnife.bind(this);
        thingsaleBtn.setOnClickListener(this);
        thingsaleDatefx.setOnClickListener(this);
        thingsaleMonthfx.setOnClickListener(this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        thingsaleDatefx.setText(sdf.format(date));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        Date date1 = calendar.getTime();
        thingsaleMonthfx.setText(sdf.format(date1));
        findGoodsType();
        thingsaleType.setOnItemSelectedListener(new ProvOnItemSelectedListener());
        findViews();
    }

    static final int Viewid = 63326784;
    EditText editText = null;

    protected View createView() {
        editText = new EditText(this);
        editText.setId(Viewid);
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
        editText.setBackgroundResource(R.drawable.shape_edit);
        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return editText;
    }

    protected View createTextView() {
        TextView textView = new TextView(this);
        textView.setText("供应商编码：");
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return textView;
    }


    private void removeView() {
        int count = linearLayout.getChildCount();
        if (count - 2 > 0) {
            linearLayout.removeViewAt(count - 1);
            linearLayout.removeViewAt(count - 2);
        }
    }

    //OnItemSelected监听器
    private class ProvOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
            //获取选择的项的值
            String sInfo = adapter.getItemAtPosition(position).toString();
            switch (sInfo) {
                case "按天门店分析":
                    linearLayout.addView(createTextView(), 1);
                    linearLayout.addView(createView(), 2);
                    editText.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    editText.setOnClickListener(ThingSaleFirrstKind.this);
                    break;
                default:
                    removeView();
                    break;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            String sInfo = "请选择分析类型";
            Toast.makeText(getApplicationContext(), sInfo, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 获取当前账号的门店信息权限
     */
    private void findshopNoByUserMethod() {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("DWORKERNO", sharedPreferences.getString("workerno", ""));
        MyAsyncClient.doPost(new MyUrl().getFindAllQX(), new RequestParams(), new MyResponseHandler(ThingSaleFirrstKind.this) {
            @Override
            public void success(JSONObject jsonObject) {
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                String[] items = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    items[i] = jsonArray.optString(i);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ThingSaleFirrstKind.this, android.R.layout.simple_dropdown_item_1line, items);
                dshopno.setAdapter(arrayAdapter);
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    private void findGoodsType() {
//      "按天门店分析", "按周门店分析", "按月门店分析"
        String[] items = new String[]{"按周门店分析", "按月门店分析","按周供应商分析","按月供应商分析"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ThingSaleFirrstKind.this, android.R.layout.simple_dropdown_item_1line, items);
        thingsaleType.setAdapter(arrayAdapter);
    }


//    private void findAllData(String tableName) {
//        RequestParams requestParams = new RequestParams();
//        requestParams.put("tableName", tableName);
//        requestParams.put("primonth", thingsaleMonthfx.getText().toString());
//        requestParams.put("curdate", thingsaleDatefx.getText().toString());
//        MyAsyncClient.doPost(new MyUrl().getFindAllData(), requestParams, new MyResponseHandler(ThingSaleFirrstKind.this) {
//            @Override
//            public void success(JSONObject jsonObject) {
//                JSONArray jsonArray = jsonObject.optJSONArray("data");
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    System.out.println(jsonArray.optJSONObject(i));
//                }
//                placeMap.clear();
//                String[] items = new String[jsonArray.length()];
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    items[i] = jsonArray.optJSONObject(i).optString("DSHOPNAME");
//                    placeMap.put(jsonArray.optJSONObject(i).optString("DSHOPNO"), jsonArray.optJSONObject(i).optString("DSHOPNAME"));
//                }
//                ArrayAdapter<String> placeAdapter = new ArrayAdapter<String>(ThingSaleFirrstKind.this, android.R.layout.simple_dropdown_item_1line, items);
//                thingsaleShop.setAdapter(placeAdapter);
//                searchFirstkindMonth();
//            }
//
//            @Override
//            public void faile(JSONObject jsonObject) {
//
//            }
//        });
//    }

    private void searchFirstkindMonth() {
        thingSaleFirstKindBeans.clear();
        RequestParams requestParams = new RequestParams();
        requestParams.put("shopno", dshopno.getSelectedItem().toString());
        requestParams.put("type", thingsaleType.getSelectedItem().toString());
        requestParams.put("curdate", thingsaleDatefx.getText().toString());
        if (editText != null){
            requestParams.put("priweek", editText.getText().toString());
        }
        requestParams.put("primonth", thingsaleMonthfx.getText().toString());

        MyAsyncClient.doPost(new MyUrl().getSearchFirstkindMonth(), requestParams, new MyResponseHandler(ThingSaleFirrstKind.this) {
            @Override
            public void success(JSONObject jsonObject) {


                String str = String.valueOf(thingsaleType.getSelectedItem().toString().charAt(1));

                System.out.println("str = " + str);
                System.out.println("月".equals(str));

                JSONArray jsonArray1 = jsonObject.optJSONArray("data");
                if ("月".equals(str)){
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        ThingSaleFirstKindBean saleFirstKindBean = new ThingSaleFirstKindBean();
                        saleFirstKindBean.setDCODE(jsonArray1.optJSONObject(i).optString("DCODE"));
                        saleFirstKindBean.setDCURSALE(jsonArray1.optJSONObject(i).optString("DCURSALE"));
                        saleFirstKindBean.setDCURMLV(jsonArray1.optJSONObject(i).optString("DCURMLV"));
                        saleFirstKindBean.setDCURML(jsonArray1.optJSONObject(i).optString("DCURML"));

                        saleFirstKindBean.setDMINCREASEMLBL(jsonArray1.optJSONObject(i).optString("DMINCREASEMLBL"));
                        saleFirstKindBean.setDMINCREASEML(jsonArray1.optJSONObject(i).optString("DMINCREASEML"));
                        saleFirstKindBean.setDMONTHML(jsonArray1.optJSONObject(i).optString("DMONTHML"));
                        saleFirstKindBean.setDMONTHSALE(jsonArray1.optJSONObject(i).optString("DMONTHSALE"));
                        saleFirstKindBean.setDMONTHMLV(jsonArray1.optJSONObject(i).optString("DMONTHMLV"));
                        saleFirstKindBean.setDMINCREASESALE(jsonArray1.optJSONObject(i).optString("DMINCREASESALE"));
                        saleFirstKindBean.setDMINCREASESALEBL(jsonArray1.optJSONObject(i).optString("DMINCREASESALEBL"));
                        thingSaleFirstKindBeans.add(saleFirstKindBean);
                    }
                } else if ("周".equals(str)){
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        ThingSaleFirstKindBean saleFirstKindBean = new ThingSaleFirstKindBean();
                        saleFirstKindBean.setDCODE(jsonArray1.optJSONObject(i).optString("DCODE"));
                        saleFirstKindBean.setDCURSALE(jsonArray1.optJSONObject(i).optString("DCURSALE"));
                        saleFirstKindBean.setDCURMLV(jsonArray1.optJSONObject(i).optString("DCURMLV"));
                        saleFirstKindBean.setDCURML(jsonArray1.optJSONObject(i).optString("DCURML"));

                        saleFirstKindBean.setDMINCREASEMLBL(jsonArray1.optJSONObject(i).optString("DWINCREASEMLBL"));
                        saleFirstKindBean.setDMINCREASEML(jsonArray1.optJSONObject(i).optString("DWINCREASEML"));
                        saleFirstKindBean.setDMONTHML(jsonArray1.optJSONObject(i).optString("DWEEKML"));
                        saleFirstKindBean.setDMONTHSALE(jsonArray1.optJSONObject(i).optString("DWEEKSALE"));
                        saleFirstKindBean.setDMONTHMLV(jsonArray1.optJSONObject(i).optString("DWEEKMLV"));
                        saleFirstKindBean.setDMINCREASESALE(jsonArray1.optJSONObject(i).optString("DWINCREASESALE"));
                        saleFirstKindBean.setDMINCREASESALEBL(jsonArray1.optJSONObject(i).optString("DWINCREASESALEBL"));
                        thingSaleFirstKindBeans.add(saleFirstKindBean);
                    }
                }
                ThingsaleFirstKindAdapter thingsaleFirstKindAdapter = new ThingsaleFirstKindAdapter(thingSaleFirstKindBeans);
                thingsaleList.setAdapter(thingsaleFirstKindAdapter);
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView textView = (TextView) view;
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void dateDialogMehod(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(ThingSaleFirrstKind.this, new DatePickerDialog.OnDateSetListener() {
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
            case R.id.thingsale_btn:
                searchFirstkindMonth();
                break;
            case R.id.thingsale_datefx:
                dateDialogMehod(thingsaleDatefx);
                break;
            case R.id.thingsale_monthfx:
                dateDialogMehod(thingsaleMonthfx);
                break;
            case Viewid:
                dateDialogMehod(editText);
                break;
            default:
                break;
        }
    }
}
