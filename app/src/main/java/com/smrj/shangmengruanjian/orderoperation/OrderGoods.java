package com.smrj.shangmengruanjian.orderoperation;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.adapter.OrderGoodsOprationAdapter;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.bean.Order;
import com.smrj.shangmengruanjian.bean.OrderGoodsBean;
import com.smrj.shangmengruanjian.productoperation.UpdateProduct;
import com.smrj.shangmengruanjian.util.MyUrl;
import com.smrj.shangmengruanjian.zxing.android.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderGoods extends AppCompatActivity implements View.OnClickListener, OrderGoodsOprationAdapter.OrderGoodsChanged {

    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.order_goods_place)
    Spinner orderGoodsPlace;
    @BindView(R.id.order_goods_ware)
    Spinner orderGoodsWare;
    @BindView(R.id.order_goods_provider)
    Spinner orderGoodsProvider;
    @BindView(R.id.order_goods_date)
    EditText orderGoodsDate;
    @BindView(R.id.order_goods_barcode)
    EditText orderGoodsBarcode;
    @BindView(R.id.order_goods_barbtn)
    Button orderGoodsBarbtn;
    @BindView(R.id.order_goods_list)
    ListView orderGoodsList;
    @BindView(R.id.first_money)
    EditText firstMoney;
    @BindView(R.id.order_goods_commit)
    Button orderGoodsCommit;
    String TAG = "myTag";
    private SharedPreferences sharedPreferences;
    private ArrayList<OrderGoodsBean> checkOrderArrayList = new ArrayList<>();
    private OrderGoodsOprationAdapter orderGoodsOprationAdapter;
    private String TICKETNO = "";
    private Map<String, String> depotMap = new HashMap<>();
    private Map<String, String> placeMap = new HashMap<>();
    private Map<String, String> providerMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_goods);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        orderGoodsDate.setOnClickListener(this);
        orderGoodsBarbtn.setOnClickListener(this);
        orderGoodsCommit.setOnClickListener(this);
        findProviderMethod();
        findAllData("F_SHOP");
        orderGoodsOprationAdapter = new OrderGoodsOprationAdapter(checkOrderArrayList, this);
        orderGoodsList.setAdapter(orderGoodsOprationAdapter);
        orderGoodsPlace.setOnItemSelectedListener(spinSelectedListener);
    }

    private void findProviderMethod() {
        MyAsyncClient.doPost(new MyUrl().getFindAllProvider(), new RequestParams(), new MyResponseHandler(OrderGoods.this) {
            @Override
            public void success(JSONObject jsonObject) {
                System.out.println(jsonObject);
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                String[] items = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    items[i] = jsonArray.optJSONObject(i).optString("dprovidercode") + "-" + jsonArray.optJSONObject(i).optString("dprovidername");
                    providerMap.put(jsonArray.optJSONObject(i).optString("dprovidercode"), jsonArray.optJSONObject(i).optString("dprovidername"));
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(OrderGoods.this, android.R.layout.simple_dropdown_item_1line, items);
                orderGoodsProvider.setAdapter(arrayAdapter);
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    private void findAllData(String tableName) {
        RequestParams requestParams = new RequestParams();
        if (tableName.equals("F_SHOP")) {
            requestParams.put("tableName", tableName);
        }
        if (tableName.equals("F_DEPOT")) {
            requestParams.put("tableName", tableName);
            for (Map.Entry entry : placeMap.entrySet()) {
                if (entry.getValue().equals(orderGoodsPlace.getSelectedItem())) {
                    requestParams.put("vaules", "AND DSHOPNO = '" + entry.getKey() + "'");
                }
            }
        }
        MyAsyncClient.doPost(new MyUrl().getFindAllData(), requestParams, new MyResponseHandler(OrderGoods.this) {
            @Override
            public void success(JSONObject jsonObject) {
                if (jsonObject.optInt("code") == 200) {
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    if (tableName.equals("F_SHOP")) {
                        placeMap.clear();
                        String[] items = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            items[i] = jsonArray.optJSONObject(i).optString("DSHOPNAME");
                            placeMap.put(jsonArray.optJSONObject(i).optString("DSHOPNO"), jsonArray.optJSONObject(i).optString("DSHOPNAME"));
                        }
                        ArrayAdapter<String> placeAdapter = new ArrayAdapter<String>(OrderGoods.this, android.R.layout.simple_dropdown_item_1line, items);
                        orderGoodsPlace.setAdapter(placeAdapter);
                        findAllData("F_DEPOT");
                    } else if (tableName.equals("F_DEPOT")) {
                        depotMap.clear();
                        Log.d("F_DEPOT", "success: " + jsonObject);
                        String[] items = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            items[i] = jsonArray.optJSONObject(i).optString("DDEPOTNAME");
                            depotMap.put(jsonArray.optJSONObject(i).optString("DDEPOTNO"), jsonArray.optJSONObject(i).optString("DDEPOTNAME"));
                        }
                        ArrayAdapter<String> placeAdapter = new ArrayAdapter<String>(OrderGoods.this, android.R.layout.simple_dropdown_item_1line, items);
                        orderGoodsWare.setAdapter(placeAdapter);
                    }

                }
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 商品条码查询
     * 如果返回值code为200则设置参数
     * 如果不为200则弹出后台的提示
     */
    private void barCodeQuery() {
        String barcode = orderGoodsBarcode.getText().toString();
        if (barcode == null || barcode == ""){
            Toast.makeText(OrderGoods.this, "以免数据量过载，造成体验不佳，请输入条码后再进行查询！", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("tableName", "V_THING_BARCODE");
        localRequestParams.put("column", "dbarcode");
        localRequestParams.put("DPROVIDERCODE",  orderGoodsProvider.getSelectedItem().toString().split("-")[0]);
        localRequestParams.put("vaules", orderGoodsBarcode.getText().toString());
        MyAsyncClient.doPost(new MyUrl().getFindGoodInfo(), localRequestParams, new MyResponseHandler(OrderGoods.this) {
            @Override
            public void success(JSONObject jsonObject) {
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                Log.i("BarcodeQuery", String.valueOf(jsonArray));
                ArrayList<OrderGoodsBean> checkOrderBeans = new ArrayList<>();
                Gson localGson = new Gson();
                if (jsonArray.length() <= 0) {
                    Toast.makeText(OrderGoods.this, "没有此商品", Toast.LENGTH_SHORT).show();
                    return;
                }
                String[] items = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    items[i] = jsonArray.optJSONObject(i).optString("DNAME") + "        " + jsonArray.optJSONObject(i).optString("DBARCODE");
                    OrderGoodsBean checkOrder = (localGson.fromJson(String.valueOf(jsonArray.optJSONObject(i)), OrderGoodsBean.class));
                    checkOrderBeans.add(checkOrder);
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderGoods.this);
                builder.setTitle("选择商品");
                builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println(items[which]);
                        for (int i = 0; i < checkOrderBeans.size(); i++) {
                            if ((checkOrderBeans.get(i).getDNAME() + "        " + checkOrderBeans.get(i).getDBARCODE()).equals(items[which])) {
                                OrderGoodsBean checkOrder = (localGson.fromJson(String.valueOf(jsonArray.optJSONObject(i)), OrderGoodsBean.class));
                                checkOrder.setDSELECTNUM(0);
                                checkOrderArrayList.add(checkOrder);
                            }
                        }
                        orderGoodsOprationAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.show();

            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }
    private void newTicketNoMethod(){
        RequestParams requestParams = new RequestParams();
        requestParams.put("DTICKETKIND","0305");
        MyAsyncClient.doPost(new MyUrl().getNewTicketNo(), requestParams, new MyResponseHandler(OrderGoods.this) {
            @Override
            public void success(JSONObject jsonObject) {
                TICKETNO = jsonObject.optString("data");
                insertOrderGoodsMethod();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    private void insertOrderGoodsMethod() {
        RequestParams requestParams = new RequestParams();
        JSONArray itemsJSON = new JSONArray();
        requestParams.put("itemsTable","ITEMS_CG");
        requestParams.put("ticketTable","TICKET_CG");
        for (int i = 0; i < checkOrderArrayList.size(); i++) {
            Map<String,String> resultMap = new HashMap<>();
            OrderGoodsBean orderGoodsBean = checkOrderArrayList.get(i);
            resultMap.put("DTICKETNO","'" + TICKETNO + "'");
            resultMap.put("DTHINGCODE", "'" + orderGoodsBean.getDTHINGCODE() + "'");
            resultMap.put("DBARCODE","'" + orderGoodsBean.getDBARCODE() + "'");
            resultMap.put("DCODE","'" + orderGoodsBean.getDCODE() + "'");
            resultMap.put("DUNITNAME","'" + orderGoodsBean.getDUNITNAME() + "'");
            resultMap.put("DNAME","'" + orderGoodsBean.getDNAME() + "'");
            resultMap.put("DSPEC","'" + orderGoodsBean.getDSPEC() + "'");
            resultMap.put("DPACK","'" + orderGoodsBean.getDPACK() + "'");
            resultMap.put("DNUM", "'" + orderGoodsBean.getDSELECTNUM() + "'");
            resultMap.put("DPRICEIN","'" + orderGoodsBean.getDPRICEIN() + "'");
            JSONObject jsonObject = new JSONObject(resultMap);
            itemsJSON.put(jsonObject);
        }
        Map<String,String> ticketMap = new HashMap<>();
        ticketMap.put("DTICKETNO","'" + TICKETNO + "'");
        for (Map.Entry entry : placeMap.entrySet()){
            if (entry.getValue().equals(orderGoodsPlace.getSelectedItem())){
                ticketMap.put("DPLACE","'" + entry.getKey() + "'");
            }
        }
        for (Map.Entry entry : depotMap.entrySet()){
            if (entry.getValue().equals(orderGoodsWare.getSelectedItem())){
                ticketMap.put("DDEPOTNO","'" + entry.getKey() + "'");
            }
        }
        for (Map.Entry entry : providerMap.entrySet()){
            if (entry.getValue().equals(orderGoodsProvider.getSelectedItem())){
                ticketMap.put("DPROVIDERCODE","'" + entry.getKey() + "'");
            }
        }
        ticketMap.put("DPLACEINPUT","'" + sharedPreferences.getString("shopno","") + "'");
        ticketMap.put("DSETMAN","'" + sharedPreferences.getString("workercode","") + "'");
        ticketMap.put("DSETDATE","GETDATE()");
        ticketMap.put("DOPERATOR","'" + sharedPreferences.getString("workercode","") + "'");
        ticketMap.put("DDATEINPUT","GETDATE()");
        ticketMap.put("DDATEDZ","GETDATE()");
        ticketMap.put("DSTATERUN","'0'");
        ticketMap.put("DVERIFY","'0'");
        ticketMap.put("DDATELIMIT","'" + orderGoodsDate.getText().toString() + "'");
        ticketMap.put("DPAYCG","'" + firstMoney.getText().toString() + "'");
        JSONObject ticketJSON = new JSONObject(ticketMap);
        requestParams.put("ticket",ticketJSON);
        requestParams.put("items",itemsJSON);
        System.out.println(ticketJSON);
        System.out.println(itemsJSON);
        MyAsyncClient.doPost(new MyUrl().getInsertTable(), requestParams, new MyResponseHandler(OrderGoods.this) {
            @Override
            public void success(JSONObject jsonObject) {
                if (jsonObject.optString("code").equals("200")) {
                    Toast.makeText(OrderGoods.this, "数据保存成功！", Toast.LENGTH_SHORT).show();
                    OrderGoods.this.finish();
                    return;
                }
                Log.d(TAG, "success: " + jsonObject);
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_goods_date:
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(OrderGoods.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String monthReulst = "";
                        String dateResult = "";
                        if (month < 10) {
                            monthReulst = "0" + (month + 1);
                        } else {
                            monthReulst = String.valueOf(month + 1);
                        }
                        if (dayOfMonth < 10) {
                            dateResult = "0" + dayOfMonth;
                        } else {
                            dateResult = String.valueOf(dayOfMonth);
                        }
                        orderGoodsDate.setText(year + "-" + monthReulst + "-" + dateResult);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                orderGoodsDate.clearFocus();
                datePickerDialog.show();
                break;
            case R.id.order_goods_barbtn:
                if (ContextCompat.checkSelfPermission(OrderGoods.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(OrderGoods.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    goScan();
                }
                break;
            case R.id.order_goods_commit:
                newTicketNoMethod();
                break;
        }
    }

    Spinner.OnItemSelectedListener spinSelectedListener = new Spinner.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.title_text:
                    TextView textView = (TextView) view;
                    textView.setTextSize(24);
                    textView.setGravity(Gravity.CENTER);
                    break;
                case R.id.order_goods_place:
                    findAllData("F_DEPOT");
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    @Override
    public void orderGoodsNumChanged(OrderGoodsBean orderGoodsBean) {
        Double money = 0.0;
        for (int i = 0; i < checkOrderArrayList.size(); i++) {
            if (checkOrderArrayList.get(i).getDBARCODE().equals(orderGoodsBean.getDBARCODE())) {
                checkOrderArrayList.get(i).setDSELECTNUM(orderGoodsBean.getDSELECTNUM());
            }
        }
        for (int i = 0; i < checkOrderArrayList.size(); i++) {
            money = money + (checkOrderArrayList.get(i).getDSELECTNUM() * checkOrderArrayList.get(i).getDPRICEIN());
        }
        firstMoney.setText(String.valueOf(money));
    }
    /**
     * 跳转到扫码界面扫码
     */
    private void goScan() {
        Intent intent = new Intent(OrderGoods.this, CaptureActivity.class);
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
                orderGoodsBarcode.setText(content);
                barCodeQuery();
            }
        }
    }

    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;
    public static final int RESULT_OK = -1;
}
