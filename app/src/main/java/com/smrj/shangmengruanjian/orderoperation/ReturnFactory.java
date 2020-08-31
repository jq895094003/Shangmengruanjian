package com.smrj.shangmengruanjian.orderoperation;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.adapter.OrderGoodsOprationAdapter;
import com.smrj.shangmengruanjian.adapter.ReturnFactoryAdapter;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.bean.OrderGoodsBean;
import com.smrj.shangmengruanjian.pandian.OrderPandian;
import com.smrj.shangmengruanjian.productoperation.UpdateProduct;
import com.smrj.shangmengruanjian.util.MyUrl;
import com.smrj.shangmengruanjian.zxing.android.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReturnFactory extends AppCompatActivity implements View.OnClickListener, OrderGoodsOprationAdapter.OrderGoodsChanged, AdapterView.OnItemSelectedListener {

    @BindView(R.id.return_factory_ware)
    Spinner returnFactoryWare;
    @BindView(R.id.return_factory_provider)
    Spinner returnFactoryProvider;
    @BindView(R.id.return_factory_type)
    AutoCompleteTextView returnFactoryType;
    @BindView(R.id.return_factory_place)
    Spinner returnFactoryPlace;
    @BindView(R.id.return_factory_barcode)
    EditText returnFactoryBarcode;
    @BindView(R.id.return_factory_barbtn)
    Button returnFactoryBarbtn;
    @BindView(R.id.return_factory_list)
    ListView returnFactoryList;
    @BindView(R.id.return_factory_commit)
    Button returnFactoryCommit;
    String TAG = "myTag";

    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;
    public static final int RESULT_OK = -1;



    private String[] returnType = new String[]{"过期返货", "促销返货", "供应商领用", "终止合作", "其他原因"};
    private SharedPreferences sharedPreferences;
    private ArrayList<OrderGoodsBean> checkOrderBeanArrayList = new ArrayList<>();
    private ReturnFactoryAdapter orderGoodsOprationAdapter;
    private Map<String, String> placeMap = new HashMap<>();
    private Map<String, String> depotMap = new HashMap<>();
    private Map<String, String> providerMap = new HashMap<>();
    private String TICKETNO = "";
    private Double ticketMoney = 0.0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.return_factory);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        returnFactoryBarbtn.setOnClickListener(this);
        findProviderMethod();
        findAllData("F_SHOP");
        returnFactoryPlace.setOnItemSelectedListener(this);
        orderGoodsOprationAdapter = new ReturnFactoryAdapter(checkOrderBeanArrayList, this);
        returnFactoryList.setAdapter(orderGoodsOprationAdapter);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(ReturnFactory.this, android.R.layout.simple_dropdown_item_1line, returnType);
        returnFactoryType.setAdapter(typeAdapter);
        returnFactoryCommit.setOnClickListener(this);
    }

    private void findAllData(String tableName) {
        RequestParams requestParams = new RequestParams();
        if (tableName.equals("F_SHOP")) {
            requestParams.put("tableName", tableName);
        }
        if (tableName.equals("F_DEPOT")) {
            requestParams.put("tableName", tableName);
            for (Map.Entry entry : placeMap.entrySet()) {
                if (entry.getValue().equals(returnFactoryPlace.getSelectedItem())) {
                    requestParams.put("vaules", "AND DSHOPNO = '" + entry.getKey() + "'");
                }
            }
        }
        MyAsyncClient.doPost(new MyUrl().getFindAllData(), requestParams, new MyResponseHandler(ReturnFactory.this) {
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
                        ArrayAdapter<String> placeAdapter = new ArrayAdapter<String>(ReturnFactory.this, android.R.layout.simple_dropdown_item_1line, items);
                        returnFactoryPlace.setAdapter(placeAdapter);
                        findAllData("F_DEPOT");
                    } else if (tableName.equals("F_DEPOT")) {
                        depotMap.clear();
                        Log.d("F_DEPOT", "success: " + jsonObject);
                        String[] items = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            items[i] = jsonArray.optJSONObject(i).optString("DDEPOTNAME");
                            depotMap.put(jsonArray.optJSONObject(i).optString("DDEPOTNO"), jsonArray.optJSONObject(i).optString("DDEPOTNAME"));
                        }
                        ArrayAdapter<String> placeAdapter = new ArrayAdapter<String>(ReturnFactory.this, android.R.layout.simple_dropdown_item_1line, items);
                        returnFactoryWare.setAdapter(placeAdapter);
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
        String barcode = returnFactoryBarcode.getText().toString();
        if (barcode == null || "".equals(barcode)){
            Toast.makeText(ReturnFactory.this, "请输入条码后再查询，防止数据量过大造成体验不佳！", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("tableName", "V_THING_BARCODE");
        localRequestParams.put("column", "DBARCODE");
        localRequestParams.put("vaules", returnFactoryBarcode.getText().toString());
        for (Map.Entry entry : placeMap.entrySet()) {
            if (entry.getValue().equals(returnFactoryPlace.getSelectedItem())) {
                localRequestParams.put("DSHOPNO", entry.getKey());
            }
        }
        MyAsyncClient.doPost(new MyUrl().getReturnFactoryBarcode(), localRequestParams, new MyResponseHandler(ReturnFactory.this) {
            @Override
            public void success(JSONObject jsonObject) {
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                ArrayList<OrderGoodsBean> checkOrderBeans = new ArrayList<>();
                Gson localGson = new Gson();
                if (jsonArray.length() <= 0) {
                    Toast.makeText(ReturnFactory.this, "没有此商品", Toast.LENGTH_SHORT).show();
                    return;
                }
                String[] items = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    System.out.println(jsonArray.optJSONObject(i));
                    items[i] = jsonArray.optJSONObject(i).optString("DNAME") + "        " + jsonArray.optJSONObject(i).optString("DBARCODE");
                    OrderGoodsBean checkOrder = (localGson.fromJson(String.valueOf(jsonArray.optJSONObject(i)), OrderGoodsBean.class));
                    checkOrderBeans.add(checkOrder);
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(ReturnFactory.this);
                builder.setTitle("选择商品");
                builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println(items[which]);
                        for (int i = 0; i < checkOrderBeans.size(); i++) {
                            if ((checkOrderBeans.get(i).getDNAME() + "        " + checkOrderBeans.get(i).getDBARCODE()).equals(items[which])) {
                                OrderGoodsBean checkOrder = (localGson.fromJson(String.valueOf(jsonArray.optJSONObject(i)), OrderGoodsBean.class));
                                System.out.println(checkOrder.toString());
                                checkOrder.setDSELECTNUM(0);
                                checkOrderBeanArrayList.add(checkOrder);
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

    private void findProviderMethod() {
        MyAsyncClient.doPost(new MyUrl().getFindAllProvider(), new RequestParams(), new MyResponseHandler(ReturnFactory.this) {
            @Override
            public void success(JSONObject jsonObject) {
                System.out.println(jsonObject);
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                String[] items = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    System.out.println(jsonArray.optJSONObject(i));
                    items[i] = jsonArray.optJSONObject(i).optString("dprovidername");
                    providerMap.put(jsonArray.optJSONObject(i).optString("dprovidercode"), jsonArray.optJSONObject(i).optString("dprovidername"));

                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ReturnFactory.this, android.R.layout.simple_dropdown_item_1line, items);
                returnFactoryProvider.setAdapter(arrayAdapter);
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    private void newTicketNoMethod() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("DTICKETKIND", "0341");
        MyAsyncClient.doPost(new MyUrl().getNewTicketNo(), requestParams, new MyResponseHandler(ReturnFactory.this) {
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
        requestParams.put("itemsTable", "ITEMS_FC");
        requestParams.put("ticketTable", "TICKET_FC");
        for (int i = 0; i < checkOrderBeanArrayList.size(); i++) {
            Map<String, String> resultMap = new HashMap<>();
            OrderGoodsBean orderGoodsBean = checkOrderBeanArrayList.get(i);
            resultMap.put("DTICKETNO", "'" + TICKETNO + "'");
            resultMap.put("DTHINGCODE", "'" + orderGoodsBean.getDTHINGCODE() + "'");
            resultMap.put("DBARCODE", "'" + orderGoodsBean.getDBARCODE() + "'");
            resultMap.put("DCODE", "'" + orderGoodsBean.getDCODE() + "'");
            resultMap.put("DUNITNAME", "'" + orderGoodsBean.getDUNITNAME() + "'");
            resultMap.put("DNAME", "'" + orderGoodsBean.getDNAME() + "'");
            resultMap.put("DSPEC", "'" + orderGoodsBean.getDSPEC() + "'");
            resultMap.put("DPACK", "'" + orderGoodsBean.getDPACK() + "'");
//            resultMap.put("DMONEYTICKET","'" + orderGoodsBean.getTicketMoney() + "'");
            resultMap.put("DMONEYTICKET","'" + orderGoodsBean.getDSELECTNUM() * orderGoodsBean.getDPRICEFC() + "'");
            resultMap.put("DORDERID","'1'");
            resultMap.put("DNUMSTORE", String.valueOf(orderGoodsBean.getDNUM()));
            resultMap.put("DDATE", "GETDATE()");
            resultMap.put("DPRICEIN", String.valueOf(orderGoodsBean.getDPRICEIN()));
            //返厂价格没有取输入价格

            System.out.println("orderGoodsBean.getDPRICEFC() ==================== " + orderGoodsBean.getDPRICEFC());

            resultMap.put("DPRICEFC", String.valueOf(orderGoodsBean.getDPRICEFC()));
            resultMap.put("DNUM", "'" + orderGoodsBean.getDSELECTNUM() + "'");
            JSONObject jsonObject = new JSONObject(resultMap);
            itemsJSON.put(jsonObject);
        }
        Map<String, String> ticketMap = new HashMap<>();
        ticketMap.put("DTICKETNO", "'" + TICKETNO + "'");
        for (Map.Entry entry : placeMap.entrySet()) {
            if (entry.getValue().equals(returnFactoryPlace.getSelectedItem())) {
                ticketMap.put("DPLACE", "'" + entry.getKey() + "'");
                ticketMap.put("DPLACEINPUT", "'" + entry.getKey() + "'");
            }
        }
        for (Map.Entry entry : depotMap.entrySet()) {
            if (entry.getValue().equals(returnFactoryWare.getSelectedItem())) {
                ticketMap.put("DDEPOTNO", "'" + entry.getKey() + "'");
            }
        }
        for (Map.Entry entry : providerMap.entrySet()) {
            if (entry.getValue().equals(returnFactoryProvider.getSelectedItem())) {
                ticketMap.put("DPROVIDERCODE", "'" + entry.getKey() + "'");
            }
        }
        ticketMap.put("DSETMAN", "'" + sharedPreferences.getString("workercode", "") + "'");
        ticketMap.put("DSETDATE", "GETDATE()");
        ticketMap.put("DOPERATOR", "'" + sharedPreferences.getString("workercode", "") + "'");
        ticketMap.put("DDATEINPUT", "GETDATE()");
        ticketMap.put("DDATEDZ", "GETDATE()");
        ticketMap.put("DSTATERUN", "'0'");
        ticketMap.put("DVERIFY", "'0'");
        ticketMap.put("DFCKIND", "'" + returnFactoryType.getText().toString() + "'");
        JSONObject ticketJSON = new JSONObject(ticketMap);
        requestParams.put("ticket", ticketJSON);
        requestParams.put("items", itemsJSON);
        System.out.println(ticketJSON);
        System.out.println(itemsJSON);
        MyAsyncClient.doPost(new MyUrl().getInsertTable(), requestParams, new MyResponseHandler(ReturnFactory.this) {
            @Override
            public void success(JSONObject jsonObject) {
                if (jsonObject.optString("code").equals("200")) {
                    Toast.makeText(ReturnFactory.this, "数据保存成功！", Toast.LENGTH_SHORT).show();
                    ReturnFactory.this.finish();
                    return;
                }
                Log.d(TAG, "success: " + jsonObject);
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_factory_barbtn:
                if (ContextCompat.checkSelfPermission(ReturnFactory.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ReturnFactory.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    goScan();
                }
                break;
            case R.id.return_factory_commit:
                if (this.returnFactoryList.getChildCount() > 0) {
                    for (int i = 0; i < this.returnFactoryList.getChildCount(); i++) {
                        EditText items = this.returnFactoryList.getChildAt(i).findViewById(R.id.goods_opration_return_return_price);
                        Double pricein = Double.valueOf(items.getText().toString());
                        checkOrderBeanArrayList.get(i).setDPRICEFC(pricein);
                    }
                }
                newTicketNoMethod();
        }
    }


    @Override
    public void orderGoodsNumChanged(OrderGoodsBean orderGoodsBean) {
        for (int i = 0; i < checkOrderBeanArrayList.size(); i++) {
            if (checkOrderBeanArrayList.get(i).getDBARCODE().equals(orderGoodsBean.getDBARCODE())) {
                checkOrderBeanArrayList.get(i).setDPRICEIN(orderGoodsBean.getDPRICEIN());
                checkOrderBeanArrayList.get(i).setDSELECTNUM(orderGoodsBean.getDSELECTNUM());
//                checkOrderBeanArrayList.get(i).setDPRICEFC(orderGoodsBean.getDPRICEFC());
//                checkOrderBeanArrayList.get(i).setTicketMoney(ticketMoney + checkOrderBeanArrayList.get(i).getDPRICEFC()*checkOrderBeanArrayList.get(i).getDSELECTNUM());
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.return_factory_place:
                findAllData("F_DEPOT");
        }
    }

    /**
     * 跳转到扫码界面扫码
     */
    private void goScan() {
        Intent intent = new Intent(ReturnFactory.this, CaptureActivity.class);
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
                returnFactoryBarcode.setText(content);
                barCodeQuery();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
