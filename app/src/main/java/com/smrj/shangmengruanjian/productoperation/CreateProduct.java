package com.smrj.shangmengruanjian.productoperation;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.orderoperation.OrderGoods;
import com.smrj.shangmengruanjian.service.FragmentToFragment;
import com.smrj.shangmengruanjian.util.MyUrl;
import com.smrj.shangmengruanjian.zxing.android.CaptureActivity;

import net.sourceforge.pinyin4j.PinyinHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class CreateProduct extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    @BindView(R.id.mastercode)
    EditText mastercode;
    @BindView(R.id.unitname)
    Spinner unitname;
    @BindView(R.id.kindno)
    Spinner kindno;
    @BindView(R.id.areacode)
    Spinner areacode;
    @BindView(R.id.providercode)
    Spinner providercode;
    @BindView(R.id.dthingtype)
    Spinner dthingtype;
    @BindView(R.id.name)
    EditText name;
    //    @BindView(R.id.code)
//    EditText code;
    @BindView(R.id.pricein)
    EditText pricein;
    @BindView(R.id.price)
    EditText price;
    @BindView(R.id.pricepf)
    EditText pricepf;
    //    @BindView(R.id.pricehy)
//    EditText pricehy;
    @BindView(R.id.commit_new_product)
    Button commitNewProduct;

    @BindView(R.id.btn_scan)
    Button btn_scan;


    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;
    public static final int RESULT_OK = -1;

    ArrayList<String> result = new ArrayList();
    SharedPreferences sharedPreferences;
    private Map<String, String> providerMap = new HashMap<>();
    private Map<String, String> goodsKinsMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_product);
        ButterKnife.bind(this);
        findProviderMethod();
        findGoodsKindMethod();
        findUnit();
        findGoodsType();
        findShopArea();
        findGoodsBarcodeType();
        this.commitNewProduct.setOnClickListener(this);
        this.commitNewProduct.setOnTouchListener(this);
        this.btn_scan.setOnClickListener(this);
        this.btn_scan.setOnTouchListener(this);
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
    }

    /**
     * 获取供应商下拉选
     */
    private void findProviderMethod() {
        MyAsyncClient.doPost(new MyUrl().getFindAllProvider(), new RequestParams(), new MyResponseHandler(CreateProduct.this) {
            @Override
            public void success(JSONObject jsonObject) {
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                String[] items = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    items[i] = jsonArray.optJSONObject(i).optString("dprovidercode") + "-" + jsonArray.optJSONObject(i).optString("dprovidername");
                    providerMap.put(jsonArray.optJSONObject(i).optString("dprovidercode"), jsonArray.optJSONObject(i).optString("dprovidername"));
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateProduct.this, android.R.layout.simple_dropdown_item_1line, items);
                providercode.setAdapter(arrayAdapter);
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 获取分类下拉选
     */
    private void findGoodsKindMethod() {
        MyAsyncClient.doPost(new MyUrl().getFindLastKind(), new RequestParams(), new MyResponseHandler(CreateProduct.this) {
            @Override
            public void success(JSONObject jsonObject) {
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                String[] items = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    items[i] = jsonArray.optJSONObject(i).optString("dkindcode") + "-" + jsonArray.optJSONObject(i).optString("dkindname");
                    goodsKinsMap.put(jsonArray.optJSONObject(i).optString("dkindcode"), jsonArray.optJSONObject(i).optString("dkindname"));
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateProduct.this, android.R.layout.simple_dropdown_item_1line, items);
                kindno.setAdapter(arrayAdapter);
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 获取货区下拉选
     */
    private void findShopArea() {
        MyAsyncClient.doPost(new MyUrl().getFindShopArea(), new RequestParams(), new MyResponseHandler(CreateProduct.this) {
            @Override
            public void success(JSONObject jsonObject) {
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                String[] items = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    items[i] = jsonArray.optJSONObject(i).optString("DAREACODE") + "-" + jsonArray.optJSONObject(i).optString("DAREANAME");
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CreateProduct.this, android.R.layout.simple_dropdown_item_1line, items);
                areacode.setAdapter(arrayAdapter);
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 获取单位下拉选
     */
    private void findUnit() {
        MyAsyncClient.doPost(new MyUrl().getFindUnit(), new RequestParams(), new MyResponseHandler(CreateProduct.this) {
            @Override
            public void success(JSONObject jsonObject) {
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                String[] items = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    items[i] = jsonArray.optJSONObject(i).optString("DUNITNAME");
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CreateProduct.this, android.R.layout.simple_dropdown_item_1line, items);
                unitname.setAdapter(arrayAdapter);
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 获取商品类型下拉选
     */
    private String barcode;

    private String findGoodsBarcodeType() {
        MyAsyncClient.doPost(new MyUrl().getFindGoodsType(), new RequestParams(), new MyResponseHandler(CreateProduct.this) {
            @Override
            public void success(JSONObject jsonObject) {
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                String[] items = new String[jsonArray.length()];
                String[] dcoderule = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    items[i] = jsonArray.optJSONObject(i).optString("DKIND");
                    if ("散货".equals(items[i].trim())) {
                        barcode = jsonArray.optJSONObject(i).optString("DCODERULE") + "-" + jsonArray.optJSONObject(i).optString("DMAXLEVEL");
                    }
                }
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
        return barcode;
    }

    private void findGoodsType() {
        String[] items = new String[]{"普通商品", "生鲜商品", "残次商品", "组合商品", "专柜商品", "度量商品"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CreateProduct.this, android.R.layout.simple_dropdown_item_1line, items);
        dthingtype.setAdapter(arrayAdapter);
    }

    /**
     * 权限检测接口
     * 如果有权限则弹出更改商铺弹出框
     * 如果没有权限则弹出没有权限提示
     * 弹出框点击是调用提交订单接口
     * 如果点击否则什么都不做
     */
    private void checkPermission() {
        this.result.clear();
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("DWORKERNO", sharedPreferences.getString("workerno", ""));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>" + sharedPreferences.getString("workerno", ""));
        MyAsyncClient.doPost(new MyUrl().getFindAllQX(), localRequestParams, new MyResponseHandler(CreateProduct.this) {
            @Override
            public void success(JSONObject jsonObject) {
                JSONArray localJSONArray = jsonObject.optJSONArray("data");
                Log.i("checkPermission", String.valueOf(localJSONArray));
                if (!jsonObject.optString("code").equals("200")) {
                    Toast.makeText(CreateProduct.this, "您没有此权限", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    final String[] arrayOfString = new String[localJSONArray.length()];
                    boolean[] arrayOfBoolean = new boolean[localJSONArray.length()];
                    for (int i = 0; i < localJSONArray.length(); i++) {
                        arrayOfString[i] = localJSONArray.optString(i);
                        arrayOfBoolean[i] = false;
                    }
                    AlertDialog.Builder localBuilder = new AlertDialog.Builder(CreateProduct.this);
                    localBuilder.setTitle("请选择要更改的商铺");
                    localBuilder.setMultiChoiceItems(arrayOfString, arrayOfBoolean, new DialogInterface.OnMultiChoiceClickListener() {
                        public void onClick(DialogInterface paramDialogInterface, int paramInt, boolean paramBoolean) {
                            if (paramBoolean == true) {
                                result.add(arrayOfString[paramInt]);
                                return;
                            } else {
                                for (int i = 0; i < CreateProduct.this.result.size(); i++) {
                                    if (!((String) CreateProduct.this.result.get(i)).equals(arrayOfString[paramInt])) {
                                    } else {
                                        result.remove(i);
                                    }
                                }
                            }
                        }
                    });
                    localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            String str1 = String.valueOf(CreateProduct.this.result);
                            String str2 = str1.substring(1, str1.length() - 1);
                            createProductMethod(str2);
                        }
                    });
                    localBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        }
                    });
                    localBuilder.show();
                }
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }

        });
    }

    private void createProductMethod(String shopno) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("dplace", shopno);
        requestParams.put("json", jsonToString());
        MyAsyncClient.doPost(new MyUrl().getInsOrderXPJD(), requestParams, new MyResponseHandler(CreateProduct.this) {
            @Override
            public void success(JSONObject jsonObject) {
                if (jsonObject.optString("code").equals("200")) {
                    Toast.makeText(CreateProduct.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                    mastercode.setText("");
                    unitname.getSelectedItem();
                    kindno.getSelectedItem();
                    providercode.getSelectedItem();
                    name.setText("");
//                    code.setText("");
                    pricein.setText("");
                    price.setText("");
                    pricepf.setText("");
//                    pricehy.setText("");
                    return;
                } else {
                    Toast.makeText(CreateProduct.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * pinyin4j方法
     *
     * @param paramChar
     * @param paramBoolean
     * @return
     */
    public static char[] getHeadByChar(char paramChar, boolean paramBoolean) {
        if (paramChar <= '')
            return new char[]{paramChar};
        String[] arrayOfString = PinyinHelper.toHanyuPinyinStringArray(paramChar);
        char[] arrayOfChar = new char[arrayOfString.length];
        int i = arrayOfString.length;
        int j = 0;
        int k = 0;
        while (j < i) {
            char c = arrayOfString[j].charAt(0);
            if (paramBoolean)
                arrayOfChar[k] = Character.toUpperCase(c);
            else
                arrayOfChar[k] = c;
            k++;
            j++;
        }
        return arrayOfChar;
    }

    /**
     * pinyin4j方法
     */
    public static String[] getHeadByString(String paramString) {
        return getHeadByString(paramString, true);
    }

    /**
     * pinyin4j方法
     */
    public static String[] getHeadByString(String paramString, boolean paramBoolean) {
        return getHeadByString(paramString, paramBoolean, null);
    }

    /**
     * pinyin4j方法
     */
    public static String[] getHeadByString(String paramString1, boolean paramBoolean, String paramString2) {
        char[] arrayOfChar1 = paramString1.toCharArray();
        String[] arrayOfString = new String[arrayOfChar1.length];
        int i = arrayOfChar1.length;
        int j = 0;
        int k = 0;
        while (j < i) {
            char[] arrayOfChar2 = getHeadByChar(arrayOfChar1[j], paramBoolean);
            StringBuffer localStringBuffer = new StringBuffer();
            if (paramString2 != null) {
                int m = arrayOfChar2.length;
                int n = 0;
                int i1 = 1;
                while (n < m) {
                    localStringBuffer.append(arrayOfChar2[n]);
                    if (i1 != arrayOfChar2.length)
                        localStringBuffer.append(paramString2);
                    i1++;
                    n++;
                }
            }
            localStringBuffer.append(arrayOfChar2[0]);
            arrayOfString[k] = localStringBuffer.toString();
            k++;
            j++;
        }
        return arrayOfString;
    }

    //将chekOrders数组转换成JSONArray
    private String jsonToString() {
        JSONArray localJSONArray = new JSONArray();
        JSONObject localJSONObject = new JSONObject();
        try {
            localJSONObject.put("dmasterbarcode", this.mastercode.getText().toString());
            localJSONObject.put("dunitname", this.unitname.getSelectedItem());
            localJSONObject.put("dkindcode", this.kindno.getSelectedItem().toString().split("-")[0]);
            localJSONObject.put("dareacode", this.areacode.getSelectedItem().toString().split("-")[0]);
            localJSONObject.put("dprovidercode", this.providercode.getSelectedItem().toString().split("-")[0]);
            localJSONObject.put("dname", this.name.getText().toString());
            localJSONObject.put("dthingtype", this.dthingtype.getSelectedItem().toString().trim());
            localJSONObject.put("dpricein", this.pricein.getText().toString());
            localJSONObject.put("dprice", this.price.getText().toString());
            localJSONObject.put("dpricepf", this.pricepf.getText().toString());
//            localJSONObject.put("dpricehy", this.pricehy.getText().toString());
            String[] arrayOfString = getHeadByString(this.name.getText().toString());
            String str = "";
            for (int i = 0; i < arrayOfString.length; i++) {
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append(str);
                localStringBuilder.append(arrayOfString[i]);
                str = localStringBuilder.toString();
            }
            localJSONObject.put("dzjm", str);
            localJSONArray.put(localJSONObject);
        } catch (JSONException localJSONException) {
            localJSONException.printStackTrace();
        }
        System.out.println(localJSONArray);
        return String.valueOf(localJSONArray);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit_new_product:
                if (this.mastercode.getText().toString().trim().equals("") || this.mastercode.getText() == null) {
                    Toast.makeText(this, "商品条码不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (this.name.getText().toString().trim().equals("") || this.name.getText() == null) {
                    Toast.makeText(this, "品名不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (this.dthingtype.getSelectedItem().toString().trim().equals("生鲜商品")) {
//                    String barcodeType =  findGoodsBarcodeType();
                    if (barcode != null) {
                        //判断是否符合生鲜条码
                        String barcodes = this.mastercode.getText().toString().trim();
                        boolean bl = barcodes.startsWith(barcode.split("-")[0].trim());
                        if (!bl || barcodes.length() != Integer.parseInt(barcode.split("-")[1].trim())) {
                            Toast.makeText(this, "该条码不是生鲜条码!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                if (this.pricein.getText().toString().trim().equals("") || this.pricein.getText() == null) {
                    Toast.makeText(this, "进价不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (this.price.getText().toString().trim().equals("") || this.price.getText() == null) {
                    Toast.makeText(this, "售价不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (this.pricepf.getText().toString().trim().equals("") || this.pricepf.getText() == null) {
                    Toast.makeText(this, "批发价不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                checkPermission();
//                if (v.getId() == R.id.commit_new_product) {
//                    if ((!this.mastercode.getText().toString().trim().equals(null)) && (!this.mastercode.getText().toString().trim().equals(""))) {
//                        if ((!this.unitname.getSelectedItem().equals(null)) && (!this.unitname.getSelectedItem().equals(""))) {
//                            if ((!this.kindno.getSelectedItem().equals(null)) && (!this.kindno.getSelectedItem().equals(""))) {
//                                if ((!this.providercode.getSelectedItem().equals(null)) && (!this.providercode.getSelectedItem().equals(""))) {
//                                    if ((!this.name.getText().toString().trim().equals(null)) && (!this.name.getText().toString().trim().equals(""))) {
//                                        if ((!this.code.getText().toString().trim().equals(null)) && (!this.code.getText().toString().trim().equals(""))) {
//                                            if ((!this.pricein.getText().toString().trim().equals(null)) && (!this.pricein.getText().toString().trim().equals(""))) {
//                                                if ((!this.price.getText().toString().trim().equals(null)) && (!this.price.getText().toString().trim().equals(""))) {
//                                                    if ((!this.pricepf.getText().toString().trim().equals(null)) && (!this.pricepf.getText().toString().trim().equals(""))) {
//                                                        if ((!this.pricehy.getText().toString().trim().equals(null)) && (!this.pricehy.getText().toString().trim().equals(null))) {
//                                                            checkPermission();
//                                                            return;
//                                                        }
//                                                        Toast.makeText(this, "会员价不能为空!", Toast.LENGTH_SHORT).show();
//                                                        return;
//                                                    }
//                                                    Toast.makeText(this, "批发价不能为空!", Toast.LENGTH_SHORT).show();
//                                                    return;
//                                                }
//                                                Toast.makeText(this, "正常售价不能为空!", Toast.LENGTH_SHORT).show();
//                                                return;
//                                            }
//                                            Toast.makeText(this, "合同进价不能为空!", Toast.LENGTH_SHORT).show();
//                                            return;
//                                        }
//                                        Toast.makeText(this, "店内码不能为空!", Toast.LENGTH_SHORT).show();
//                                        return;
//                                    }
//                                    Toast.makeText(this, "商品名称不能为空!", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//                                Toast.makeText(this, "供应商编码不能为空!", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                            Toast.makeText(this, "货类编码不能为空!", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        Toast.makeText(this, "商品单位不能为空!", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    Toast.makeText(this, "主商品条码不能为空!", Toast.LENGTH_SHORT).show();
//                }
                break;
            default:
        }
        if (v.getId() == R.id.btn_scan) {
            //动态权限申请
            if (ContextCompat.checkSelfPermission(CreateProduct.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CreateProduct.this, new String[]{Manifest.permission.CAMERA}, 1);
            } else {
                goScan();
            }
        }
    }


    /**
     * 跳转到扫码界面扫码
     */
    private void goScan() {
        Intent intent = new Intent(CreateProduct.this, CaptureActivity.class);
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
                mastercode.setText(content);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            v.setAlpha((float) 0.30);
        if (event.getAction() == MotionEvent.ACTION_UP)
            v.setAlpha(1);
        return false;
    }
}
