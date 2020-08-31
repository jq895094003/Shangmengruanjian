package com.smrj.shangmengruanjian.pandian;

import android.Manifest;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.adapter.CheckOrderAccountInfoAdapter;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.bean.CheckOrderAccountInfoBean;
import com.smrj.shangmengruanjian.bean.LevelType;
import com.smrj.shangmengruanjian.bean.ProductCornerBean;
import com.smrj.shangmengruanjian.bean.Providers;
import com.smrj.shangmengruanjian.bean.WareHouseBean;
import com.smrj.shangmengruanjian.orderoperation.AdjustmentOfPurchasePrice;
import com.smrj.shangmengruanjian.util.JsonToString;
import com.smrj.shangmengruanjian.util.MyUrl;
import com.smrj.shangmengruanjian.zxing.android.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class OrderPandian extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {
    /**
     * 57-128行，声明并实例化所有页面控件，使用ButterKnife绑定
     */
    public static OrderPandian orderPandian;

    @BindView(R.id.barcode_edit)
    EditText barcodeEdit;

    @BindView(R.id.barcode_query)
    LinearLayout barcodeQuery;
    CheckOrderAccountInfoAdapter checkOrderAccountInfoAdapter;
    ArrayList<CheckOrderAccountInfoBean> checkOrderAccountInfoBeans = new ArrayList();

    @BindView(R.id.commit_pandian_order)
    Button commitPandianOrder;
    ArrayList<LevelType> levelCode = new ArrayList();

    @BindView(R.id.level_spinner)
    Spinner levelProduct;

    @BindView(R.id.level_selected)
    LinearLayout levelSelected;
    ArrayList<LevelType> levelTypesOne = new ArrayList();

    @BindView(R.id.product_corner)
    Button productCorner;
    ArrayList<ProductCornerBean> productCornerBeans = new ArrayList();
    ArrayList<ProductCornerBean> productCornerBeansSelected = new ArrayList();

    @BindView(R.id.product_corner_selected)
    LinearLayout productCornerSelected;

    @BindView(R.id.product_corners)
    TextView productCorners;

    @BindView(R.id.product_list)
    ListView productList;

    @BindView(R.id.product_type)
    Button productType;

    @BindView(R.id.product_types)
    TextView productTypes;

    @BindView(R.id.product_types_selected)
    LinearLayout productTypesSelected;

    @BindView(R.id.provict_btns)
    LinearLayout provictBtns;

    @BindView(R.id.provider_btn)
    Button providerBtn;
    ArrayList<Providers> providerCode = new ArrayList();

    @BindView(R.id.providers)
    TextView providers;
    ArrayList<Providers> providersArrayList = new ArrayList();

    @BindView(R.id.providers_selected)
    LinearLayout providersSelected;

    @BindView(R.id.query_add)
    Button queryAdd;

    @BindView(R.id.query_btn)
    Button queryBtn;

    @BindView(R.id.buttons)
    LinearLayout queryButtons;
    SharedPreferences sharedPreferences;
    String tag;

    @BindView(R.id.warehouse_spinner)
    Spinner wareHouse;
    ArrayList<WareHouseBean> wareHouseBeans = new ArrayList();


    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;
    public static final int RESULT_OK = -1;

    /**
     * 查询所有盘点商品
     * 如果返回值code为200则将数据加载到列表控件
     * 如果不为200则弹出后台提示
     */
    private void barcodeQueryProduct() {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("dticketno", this.barcodeEdit.getText().toString());
        localRequestParams.put("DSHOPNO",sharedPreferences.getString("shopno", ""));
        MyAsyncClient.doPost(new MyUrl().getFindThingModel(), localRequestParams, new MyResponseHandler(OrderPandian.this) {
            @Override
            public void success(JSONObject jsonObject) {
                if (jsonObject.optString("code").equals("200")) {
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        CheckOrderAccountInfoBean localCheckOrderAccountInfoBean = (CheckOrderAccountInfoBean) new Gson().fromJson(String.valueOf(jsonArray.optJSONObject(i)), CheckOrderAccountInfoBean.class);
                        OrderPandian.this.checkOrderAccountInfoBeans.add(localCheckOrderAccountInfoBean);
                    }
                    OrderPandian.this.checkOrderAccountInfoAdapter.notifyDataSetChanged();
                    return;
                }
                Toast.makeText(OrderPandian.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 提交盘点信息接口
     * 如果返回值code为200则更新列表控件内容
     * 如果返回值code不为200则弹出后台提示信息
     * @param paramString1
     * @param paramString2
     */
    private void commitPandianOrder(String paramString1, String paramString2) {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("ddepotnopc", paramString1);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>" + paramString1 + paramString2);
        localRequestParams.put("dpcmode", paramString2);
        if (paramString2.equals("按货类"))
            localRequestParams.put("json", new JsonToString().levelJsonToString(this.levelCode));
        else if (paramString2.equals("按单品"))
            localRequestParams.put("json", new JsonToString().productJsonToString(this.checkOrderAccountInfoBeans));
        else if (paramString2.equals("按供应商"))
            localRequestParams.put("json", new JsonToString().providerJsonToString(this.providerCode));
        else if (paramString2.equals("按货区"))
            localRequestParams.put("json", new JsonToString().cornerJsonToString(this.productCornerBeansSelected));
        MyAsyncClient.doPost(new MyUrl().getOrderPCNEW(), localRequestParams, new MyResponseHandler(OrderPandian.this) {
            @Override
            public void success(JSONObject jsonObject) {
                if (jsonObject.optString("code").equals("200")) {
                    Toast.makeText(OrderPandian.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                    OrderPandian.this.checkOrderAccountInfoBeans.clear();
                    OrderPandian.this.checkOrderAccountInfoAdapter.notifyDataSetChanged();
                    return;
                }
                Toast.makeText(OrderPandian.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 货区盘点信息
     * 如果返回值code为200则添加到productCornerBeans数组中
     * 如果不为200则弹出提示
     */
    private void productCornerAsync() {
        MyAsyncClient.doPost(new MyUrl().getFindArea(), new RequestParams(), new MyResponseHandler(OrderPandian.this) {
            @Override
            public void success(JSONObject jsonObject) {
                boolean bool = jsonObject.optString("code").equals("200");
                int i = 0;
                if (bool) {
                    JSONArray localJSONArray = jsonObject.optJSONArray("data");
                    Gson localGson = new Gson();
                    while (i < localJSONArray.length()) {
                        Log.i("productCorner", String.valueOf(localJSONArray.optJSONObject(i)));
                        ProductCornerBean localProductCornerBean = (ProductCornerBean) localGson.fromJson(String.valueOf(localJSONArray.optJSONObject(i)), ProductCornerBean.class);
                        OrderPandian.this.productCornerBeans.add(localProductCornerBean);
                        i++;
                    }
                }
                Toast.makeText(OrderPandian.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 货类盘点信息查询
     * 如果返回值code为200则将数据加载到列表控件
     * 如果不为200则弹出后台提示
     */
    private void productTypeAsync() {
        MyAsyncClient.doPost(new MyUrl().getFindKind(), new RequestParams(), new MyResponseHandler(OrderPandian.this) {
            @Override
            public void success(JSONObject jsonObject) {
                boolean bool = jsonObject.optString("code").equals("200");
                int i = 0;
                if (bool) {
                    JSONArray localJSONArray = jsonObject.optJSONArray("data");
                    Log.i("productTypeLength", String.valueOf(localJSONArray.length()));
                    Gson localGson = new Gson();
                    for (int j = 0; j < localJSONArray.length(); j++) {
                        LevelType localLevelType = (LevelType) localGson.fromJson(String.valueOf(localJSONArray.optJSONObject(j)), LevelType.class);
                        OrderPandian.this.levelTypesOne.add(localLevelType);
                    }
                    ArrayList localArrayList1 = new ArrayList();
                    while (i < OrderPandian.this.levelTypesOne.size()) {
                        localArrayList1.add(String.valueOf(((LevelType) OrderPandian.this.levelTypesOne.get(i)).getDlevel()));
                        i++;
                    }
                    ArrayList localArrayList2 = new ArrayList(new HashSet(localArrayList1));
                    ArrayAdapter localArrayAdapter = new ArrayAdapter(OrderPandian.this, android.R.layout.simple_dropdown_item_1line, localArrayList2);
                    OrderPandian.this.levelProduct.setAdapter(localArrayAdapter);
                    return;
                }
                Toast.makeText(OrderPandian.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 供应商商品查询
     * 如果返回值code为200则将数据添加到providersArrayList数组中
     * 如果不为200则弹出后台返回的提示
     */
    private void providersAsync() {
        MyAsyncClient.doPost(new MyUrl().getFindAllProvider(), new RequestParams(), new MyResponseHandler(OrderPandian.this) {
            @Override
            public void success(JSONObject jsonObject) {
                boolean bool = jsonObject.optString("code").equals("200");
                int i = 0;
                if (bool) {
                    JSONArray localJSONArray = jsonObject.optJSONArray("data");
                    while (i < localJSONArray.length()) {
                        Providers localProviders = new Providers();
                        localProviders.setDfullname(localJSONArray.optJSONObject(i).optString("dfullname"));
                        localProviders.setDprovidercode(localJSONArray.optJSONObject(i).optString("dprovidercode"));
                        localProviders.setDproviderno(localJSONArray.optJSONObject(i).optString("dproviderno"));
                        localProviders.setDprovidername(localJSONArray.optJSONObject(i).optString("dprovidername"));
                        OrderPandian.this.providersArrayList.add(localProviders);
                        i++;
                    }
                }
                Toast.makeText(OrderPandian.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 仓库查询
     * 如果返回值为200则将数据加载到列表控件
     * 如果不为200则弹出后台提示
     */
    private void wareHouseAsync() {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("dshopno", this.sharedPreferences.getString("shopno", ""));
        MyAsyncClient.doPost(new MyUrl().getFindAllDepot(), localRequestParams, new MyResponseHandler(OrderPandian.this) {
            @Override
            public void success(JSONObject jsonObject) {
                boolean bool = jsonObject.optString("code").equals("200");
                int i = 0;
                if (bool) {
                    Log.i("wareHouse", String.valueOf(jsonObject));
                    JSONArray localJSONArray = jsonObject.optJSONArray("data");
                    new Gson();
                    for (int j = 0; j < localJSONArray.length(); j++) {
                        WareHouseBean localWareHouseBean = new WareHouseBean();
                        localWareHouseBean.setWareHouseCode(localJSONArray.optJSONObject(j).optString("ddepotno"));
                        localWareHouseBean.setWareHouseName(localJSONArray.optJSONObject(j).optString("ddepotname"));
                        OrderPandian.this.wareHouseBeans.add(localWareHouseBean);
                    }
                    String[] arrayOfString = new String[localJSONArray.length()];
                    while (i < OrderPandian.this.wareHouseBeans.size()) {
                        arrayOfString[i] = ((WareHouseBean) OrderPandian.this.wareHouseBeans.get(i)).getWareHouseName();
                        i++;
                    }
                    ArrayAdapter localArrayAdapter = new ArrayAdapter(OrderPandian.this, android.R.layout.simple_dropdown_item_1line, arrayOfString);
                    OrderPandian.this.wareHouse.setAdapter(localArrayAdapter);
                    return;
                }
                Toast.makeText(OrderPandian.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 页面控件单击事件
     * 调起对应查询方法，弹出选择框、选择供应商、类别等信息
     * @param paramView
     */
    public void onClick(View paramView) {
        switch (paramView.getId()) {
            default:
                return;
            case R.id.query_btn:
                if (ContextCompat.checkSelfPermission(OrderPandian.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(OrderPandian.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    goScan();
                }
                return;
            case R.id.provider_btn:
                String[] arrayOfString3 = new String[providersArrayList.size()];
                boolean[] arrayOfBoolean3 = new boolean[providersArrayList.size()];
                this.providerCode = new ArrayList();
                for (int n = 0; n < this.providersArrayList.size(); n++) {
                    arrayOfString3[n] = (providersArrayList.get(n)).getDfullname();
                    arrayOfBoolean3[n] = false;
                }
                new Builder(this).setTitle("请选择供应商").setMultiChoiceItems(arrayOfString3, arrayOfBoolean3, new OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt, boolean paramBoolean) {
                        System.out.println("booleanKey:" + paramInt + ",booleanValue:" + paramBoolean);
                        arrayOfBoolean3[paramInt] = paramBoolean;
                    }
                }).setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        for (int i = 0; i < arrayOfBoolean3.length ; i++) {
                            if (arrayOfBoolean3[i] != false){
                                StringBuilder localStringBuilder2 = new StringBuilder();
                                localStringBuilder2.append(arrayOfString3[i]);
                                localStringBuilder2.append(",");
                                OrderPandian.this.providerCode.add(OrderPandian.this.providersArrayList.get(i));
                                OrderPandian.this.providers.setText(localStringBuilder2);
                            }
                        }
                    }
                }).setNegativeButton("取消", new OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        OrderPandian.this.providers.setText("");
                        paramDialogInterface.dismiss();
                    }
                }).show();
                return;
            case R.id.product_type:
                StringBuilder localStringBuilder1 = new StringBuilder();
                String str2 = "";
                if (levelProduct.getChildCount() <= 0){
                    Toast.makeText(this,"请先选择货级",Toast.LENGTH_SHORT).show();
                }else {
                    str2 = this.levelProduct.getSelectedItem().toString();
                    ArrayList localArrayList = new ArrayList();
                    this.levelCode = new ArrayList();
                    for (int k = 0; k < this.levelTypesOne.size(); k++) {
                        if (!String.valueOf(((LevelType) this.levelTypesOne.get(k)).getDlevel()).equals(str2))
                            continue;
                        localArrayList.add(((LevelType) this.levelTypesOne.get(k)).getDkindname());
                    }
                    boolean[] arrayOfBoolean2 = new boolean[localArrayList.size()];
                    String[] arrayOfString2 = new String[localArrayList.size()];
                    for (int m = 0; m < localArrayList.size(); m++) {
                        arrayOfString2[m] = ((String) localArrayList.get(m));
                        arrayOfBoolean2[m] = false;
                    }
                    new Builder(this).setTitle("请选择要盘点的品类").setMultiChoiceItems(arrayOfString2, arrayOfBoolean2, new OnMultiChoiceClickListener() {
                        public void onClick(DialogInterface paramDialogInterface, int paramInt, boolean paramBoolean) {
                            arrayOfBoolean2[paramInt] = paramBoolean;
                        }
                    }).setPositiveButton("确定", new OnClickListener() {
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            for (int i = 0; i < arrayOfBoolean2.length; i++) {
                                if (arrayOfBoolean2[i] != false){
                                    StringBuilder localStringBuilder2 = new StringBuilder();
                                    localStringBuilder2.append(arrayOfString2[i]);
                                    System.out.println(arrayOfString2[i]);
                                    localStringBuilder2.append(",");
                                    localStringBuilder1.append(localStringBuilder2.toString());
                                    OrderPandian.this.levelCode.add(OrderPandian.this.levelTypesOne.get(i));
                                    OrderPandian.this.productTypes.setText(localStringBuilder1);
                                }
                            }
                        }
                    }).setNegativeButton("取消", new OnClickListener() {
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            OrderPandian.this.productTypes.setText("");
                            paramDialogInterface.dismiss();
                        }
                    }).show();
                }

                return;
            case R.id.product_corner:
                StringBuilder StringBuilder1 = new StringBuilder();
                String[] arrayOfString1 = new String[this.productCornerBeans.size()];
                boolean[] arrayOfBoolean1 = new boolean[this.productCornerBeans.size()];
                this.productCornerBeansSelected = new ArrayList();
                for (int j = 0; j < this.productCornerBeans.size(); j++) {
                    arrayOfString1[j] = ((ProductCornerBean) this.productCornerBeans.get(j)).getDareaname();
                    arrayOfBoolean1[j] = false;
                }
                new Builder(this).setTitle("请选择货区").setMultiChoiceItems(arrayOfString1,arrayOfBoolean1, new OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt, boolean paramBoolean) {
                        arrayOfBoolean1[paramInt] = paramBoolean;
                    }
                }).setPositiveButton("确认", new OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        for (int i = 0; i < arrayOfBoolean1.length; i++) {
                            if (arrayOfBoolean1[i] != false){
                                StringBuilder localStringBuilder2 = new StringBuilder();
                                localStringBuilder2.append(arrayOfString1[i]);
                                localStringBuilder2.append(",");
                                StringBuilder1.append(localStringBuilder2.toString());
                                productCornerBeansSelected.add(productCornerBeans.get(i));
                                OrderPandian.this.productCorners.setText(StringBuilder1);
                            }
                        }
                    }
                }).setNegativeButton("取消", new OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        OrderPandian.this.productCorners.setText("");
                        paramDialogInterface.dismiss();
                    }
                }).show();
                return;
            case R.id.commit_pandian_order:
                String str1 = "";
                for (int i = 0; i < this.wareHouseBeans.size(); i++) {
                    if (!((WareHouseBean) this.wareHouseBeans.get(i)).getWareHouseName().equals(this.wareHouse.getSelectedItem().toString()))
                        continue;
                    str1 = ((WareHouseBean) this.wareHouseBeans.get(i)).getWareHouseCode();
                }
                if (this.tag.equals("货类盘点")) {
                    if (this.levelCode.size() == 0) {
                        Toast.makeText(this, "请先选择商品类别", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    commitPandianOrder(str1, "按货类");
                    return;
                }
                if (this.tag.equals("单品盘点")) {
                    if (this.checkOrderAccountInfoAdapter.getCount() == 0) {
                        Toast.makeText(this, "请先添加商品", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    commitPandianOrder(str1, "按单品");
                    return;
                }
                if (this.tag.equals("供应商盘点")) {
                    if (this.providerCode.size() == 0) {
                        Toast.makeText(this, "请先添加供应商", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    commitPandianOrder(str1, "按供应商");
                    return;
                }
                if (this.tag.equals("货区盘点")) {
                    if (this.productCornerBeansSelected.size() == 0) {
                        Toast.makeText(this, "请先添加货区", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    commitPandianOrder(str1, "按货区");
                }
                break;
        }

    }

    /**
     * Activity声明周期
     * 根据传入的参数来确定显示哪些控件，并实现该控件对应的监听事件
     * @param paramBundle
     */
    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.pandian_order);
        ButterKnife.bind(this);
        this.sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        this.tag = getIntent().getStringExtra("tag");
        if (this.tag.equals("货类盘点")) {
            this.levelProduct.setVisibility(View.VISIBLE);
            this.provictBtns.setVisibility(View.VISIBLE);
            this.productType.setVisibility(View.VISIBLE);
            this.levelSelected.setVisibility(View.VISIBLE);
            this.productTypesSelected.setVisibility(View.VISIBLE);
            this.checkOrderAccountInfoAdapter = new CheckOrderAccountInfoAdapter(this.checkOrderAccountInfoBeans);
            this.productType.setOnClickListener(this);
            this.providerBtn.setOnClickListener(this);
            this.commitPandianOrder.setOnClickListener(this);
            wareHouseAsync();
            productTypeAsync();
            return;
        }
        if (this.tag.equals("单品盘点")) {
            this.barcodeQuery.setVisibility(View.VISIBLE);
            this.queryButtons.setVisibility(View.VISIBLE);
            this.queryBtn.setVisibility(View.VISIBLE);
            this.productList.setVisibility(View.VISIBLE);
            this.productList.setOnItemClickListener(this);
            this.queryBtn.setOnClickListener(this);
            this.commitPandianOrder.setOnClickListener(this);
            orderPandian = this;
            this.checkOrderAccountInfoAdapter = new CheckOrderAccountInfoAdapter(this.checkOrderAccountInfoBeans);
            this.productList.setAdapter(this.checkOrderAccountInfoAdapter);
            wareHouseAsync();
            return;
        }
        if (this.tag.equals("货区盘点")) {
            this.provictBtns.setVisibility(View.VISIBLE);
            this.productCorner.setVisibility(View.VISIBLE);
            this.productCornerSelected.setVisibility(View.VISIBLE);
            this.productCorner.setOnClickListener(this);
            this.commitPandianOrder.setOnClickListener(this);
            this.checkOrderAccountInfoAdapter = new CheckOrderAccountInfoAdapter(this.checkOrderAccountInfoBeans);
            wareHouseAsync();
            productCornerAsync();
            return;
        }
        if (this.tag.equals("供应商盘点")) {
            this.provictBtns.setVisibility(View.VISIBLE);
            this.providerBtn.setVisibility(View.VISIBLE);
            this.providersSelected.setVisibility(View.VISIBLE);
            this.providers.setVisibility(View.VISIBLE);
            this.providerBtn.setOnClickListener(this);
            this.commitPandianOrder.setOnClickListener(this);
            this.checkOrderAccountInfoAdapter = new CheckOrderAccountInfoAdapter(this.checkOrderAccountInfoBeans);
            wareHouseAsync();
            providersAsync();
        }
    }

    /**
     * 列表控件子项点击方法
     * 跳转页面
     * 向下一页面传递所需要的参数
     * @param paramAdapterView
     * @param paramView
     * @param paramInt
     * @param paramLong
     */
    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
        Intent localIntent = new Intent(this, PandianOrderInfo.class);
        localIntent.putExtra("orderPandian", (Serializable) this.checkOrderAccountInfoBeans.get(paramInt));
        startActivity(localIntent);
    }

    /**
     * 列表子项移除方法
     * 根据传入参数删除checkOrderAccountInfoBeans中对应项
     * 更新列表显示内容
     * @param paramString
     */
    public void removeProduct(String paramString) {
        for (int i = 0; i < this.checkOrderAccountInfoBeans.size(); i++) {
            if (!((CheckOrderAccountInfoBean) this.checkOrderAccountInfoBeans.get(i)).getDbarcode().equals(paramString))
                continue;
            this.checkOrderAccountInfoBeans.remove(i);
        }
        this.checkOrderAccountInfoAdapter.notifyDataSetChanged();
    }

    /**
     * 跳转到扫码界面扫码
     */
    private void goScan() {
        Intent intent = new Intent(OrderPandian.this, CaptureActivity.class);
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
                barcodeEdit.setText(content);
                barcodeQueryProduct();
            }
        }
    }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.pandian.OrderPandian
 * JD-Core Version:    0.6.0
 */