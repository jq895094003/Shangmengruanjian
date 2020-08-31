package com.smrj.shangmengruanjian.orderoperation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.adapter.CheckOrderInfoAdapter;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.bean.CheckOrderBean;
import com.smrj.shangmengruanjian.bean.Providers;
import com.smrj.shangmengruanjian.service.FragmentToFragment;
import com.smrj.shangmengruanjian.util.MyUrl;
import com.smrj.shangmengruanjian.zxing.android.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CheckOrder extends Fragment implements OnClickListener, View.OnTouchListener {
    /**
     * 45-85行，声明并实例化页面控件，使用ButterKnife绑定
     */
    CheckOrderBean checkOrderBean;// = new CheckOrderBean();

    public CheckOrderBean getCheckOrderBean() {
        return checkOrderBean;
    }

    public void setCheckOrderBean(CheckOrderBean checkOrderBean) {
        this.checkOrderBean = checkOrderBean;
    }

    @BindView(R.id.check_order_barcode)
    EditText checkOrderBarcode;

    @BindView(R.id.check_order_btn)
    Button checkOrderBtn;

    @BindView(R.id.btn_scan)
    Button btn_scan;

//    @BindView(R.id.check_order_dbarcode)
//    TextView checkOrderDbarcode;

//    @BindView(R.id.check_order_dname)
//    TextView checkOrderDname;

    @BindView(R.id.check_order_dnum)
    EditText checkOrderDnum;

    public EditText getCheckOrderDnum() {
        return checkOrderDnum;
    }
    //    @BindView(R.id.check_order_dprovidername)
//    TextView checkOrderDproviderName;

//    @BindView(R.id.check_order_dprovidercode)
//    TextView checkOrderDprovidercode;

//    @BindView(R.id.check_order_dunitname)
//    TextView checkOrderDunitName;

//    @BindView(R.id.check_order_dpricein)
//    TextView checkOrderDpricein;

//    @BindView(R.id.check_order_dprice)
//    TextView checkOrderDprice;

//    @BindView(R.id.check_order_imgbtn)
//    ImageButton checkOrderImgBtn;

    @BindView(R.id.check_order_place)
    Spinner checkOrderPlace;

    @BindView(R.id.check_order_provider)
    AutoCompleteTextView checkOrderProvider;

    public AutoCompleteTextView getCheckOrderProvider() {
        return checkOrderProvider;
    }

    @BindView(R.id.check_order_warehouse)
    Spinner checkOrderWareHouse;
    FragmentToFragment fragmentToFragment;
    ArrayList<Providers> providersArrayList;
    SharedPreferences sharedPreferences;
    private Unbinder unbinder;

    /**
     * 商品条码查询
     * 如果返回值code为200则设置参数
     * 如果不为200则弹出后台的提示
     */
    ArrayList<CheckOrderBean> checkOrderBeans = new ArrayList<>();
    CheckOrderInfoAdapter listViewAdapter;
    @BindView(R.id.detail_fragment_list)
    PullToRefreshListView pullToRefreshListView;


    private void barCodeQuery() {
        checkOrderBeans.clear();
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("dticketno", checkOrderBarcode.getText().toString());
        localRequestParams.put("DSHOPNO", sharedPreferences.getString("shopno", ""));

//        if ("".equals(checkOrderProvider.getText().toString().trim()) || checkOrderProvider.getText().toString() == null) {
//            Toast.makeText(CheckOrder.this.getActivity(), "供应商信息不能为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        localRequestParams.put("dprovidercode", checkOrderProvider.getText().toString().trim().split("-")[1].trim());
        if (!"".equals(checkOrderProvider.getText().toString().trim()) && checkOrderProvider.getText().toString() != null) {
            localRequestParams.put("dprovidercode", checkOrderProvider.getText().toString().trim().split("-")[1].trim());
        }

        MyAsyncClient.doPost(new MyUrl().getFindThingModel(), localRequestParams, new MyResponseHandler(getActivity()) {
//            @Override
//            public void success(JSONObject jsonObject) {
//                JSONArray jsonArray = jsonObject.optJSONArray("data");
//                Log.i("BarcodeQuery", String.valueOf(jsonArray));
//                ArrayList<CheckOrderBean> checkOrderBeans = new ArrayList<>();
//                Gson localGson = new Gson();
//                if (jsonArray.length() <= 0) {
//                    Toast.makeText(CheckOrder.this.getActivity(), "没有此商品", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                String[] items = new String[jsonArray.length()];
//                boolean[] itemscheck = new boolean[items.length];
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    items[i] = jsonArray.optJSONObject(i).optString("dname") + "        " + jsonArray.optJSONObject(i).optString("dbarcode");
//                    CheckOrder.this.checkOrderBean = ((CheckOrderBean) localGson.fromJson(String.valueOf(jsonArray.optJSONObject(i)), CheckOrderBean.class));
//                    checkOrderBeans.add(checkOrderBean);
//                }
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("选择商品");
//                builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        for (int i = 0; i < checkOrderBeans.size(); i++) {
//                            if ((checkOrderBeans.get(i).getDname() + "        " + checkOrderBeans.get(i).getDbarcode()).equals(items[which])) {
//                                CheckOrder.this.checkOrderBean = ((CheckOrderBean) localGson.fromJson(String.valueOf(jsonArray.optJSONObject(i)), CheckOrderBean.class));
//                                CheckOrder.this.checkOrderDname.setText(jsonArray.optJSONObject(i).optString("dname"));
//                                CheckOrder.this.checkOrderDbarcode.setText(jsonArray.optJSONObject(i).optString("dbarcode"));
//                                CheckOrder.this.checkOrderDprovidercode.setText(jsonArray.optJSONObject(i).optString("dprovidercode"));
//                                CheckOrder.this.checkOrderDproviderName.setText(jsonArray.optJSONObject(i).optString("dprovidername"));
//                                CheckOrder.this.checkOrderDunitName.setText(jsonArray.optJSONObject(i).optString("dunitname"));
//                                CheckOrder.this.checkOrderDpricein.setText(jsonArray.optJSONObject(i).optString("dpricein"));
//                                CheckOrder.this.checkOrderDprice.setText(jsonArray.optJSONObject(i).optString("dprice"));
//                            }
//                        }
//                        dialog.dismiss();
//                    }
//                });
//                builder.show();
//            }
            @Override
            public void success(JSONObject jsonObject) {
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                Gson gson = new Gson();
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        CheckOrderBean checkOrderBean = gson.fromJson(String.valueOf(jsonArray.optJSONObject(i)), CheckOrderBean.class);
                        checkOrderBeans.add(checkOrderBean);
                    }
                } else {
                    Toast.makeText(getActivity(), "查询无数据", Toast.LENGTH_SHORT).show();
                }
                listViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 权限检测接口
     * 如果有权限则弹出更改商铺弹出框
     * 如果没有权限则弹出没有权限提示
     * 弹出框点击是调用查询分仓接口
     * 如果点击否则什么都不做
     */
    private void checkPermission() {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("DWORKERNO", sharedPreferences.getString("workerno", ""));
        MyAsyncClient.doPost(new MyUrl().getFindAllQX(), localRequestParams, new MyResponseHandler(getActivity()) {
            @Override
            public void success(JSONObject jsonObject) {
                Log.i("检查权限", String.valueOf(jsonObject));
                if (jsonObject.optString("code").equals("200")) {
                    JSONArray localJSONArray = jsonObject.optJSONArray("data");
                    String[] arrayOfString = new String[localJSONArray.length()];
                    int k;
                    if (localJSONArray.length() == 0) {
                        Toast.makeText(CheckOrder.this.getActivity(), "您没有此权限", Toast.LENGTH_SHORT).show();
                        k = 0;
                    } else {
                        boolean[] arrayOfBoolean = new boolean[localJSONArray.length()];
                        for (int i = 0; ; i++) {
                            int j = localJSONArray.length();
                            k = 0;
                            if (i >= j)
                                break;
                            arrayOfString[i] = localJSONArray.optString(i);
                            arrayOfBoolean[i] = false;
                        }
                    }
                    while (k < arrayOfString.length) {
                        Log.i("item", arrayOfString[k]);
                        k++;
                    }
                    ArrayAdapter localArrayAdapter = new ArrayAdapter(CheckOrder.this.getActivity(), android.R.layout.simple_dropdown_item_1line, arrayOfString);
                    CheckOrder.this.checkOrderPlace.setAdapter(localArrayAdapter);
                    wareHouseQuery();
                    return;
                }
                Toast.makeText(CheckOrder.this.getActivity(), jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void faile(JSONObject jsonObject) {
                Log.i("权限检查", "请求失败");
            }

        });
    }

    /**
     * 供应商查询接口
     * 如果返回值code为200则设置到下拉文本框
     * 如果不为200则弹出后台返回的信息提示
     */
    private void providerQuery() {
        MyAsyncClient.doPost(new MyUrl().getFindAllProvider(), new RequestParams(), new MyResponseHandler(getActivity()) {
            @Override
            public void success(JSONObject jsonObject) {
                boolean bool = jsonObject.optString("code").equals("200");
                int i = 0;
                if (bool) {
                    JSONArray localJSONArray = jsonObject.optJSONArray("data");
                    Gson localGson = new Gson();
                    providersArrayList = new ArrayList();
                    ArrayList localArrayList = new ArrayList();
                    while (i < localJSONArray.length()) {
                        Providers localProviders = localGson.fromJson(String.valueOf(localJSONArray.optJSONObject(i)), Providers.class);
                        providersArrayList.add(localProviders);
                        localArrayList.add(localProviders.getDprovidername() + "-" + localProviders.getDprovidercode());
                        i++;
                    }
                    ArrayAdapter localArrayAdapter = new ArrayAdapter(CheckOrder.this.getActivity(), android.R.layout.simple_dropdown_item_1line, localArrayList);
                    CheckOrder.this.checkOrderProvider.setAdapter(localArrayAdapter);
                    return;
                }
                Toast.makeText(CheckOrder.this.getActivity(), jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void faile(JSONObject jsonObject) {
                Log.i("供应商查询", "查询失败");
            }
        });
    }

    /**
     * 分仓查询
     * 如果返回值code为200则设置到下拉选项框
     * 如果不为200则弹出后台提示信息
     */
    private void wareHouseQuery() {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("dshopno", checkOrderPlace.getSelectedItem().toString());
        MyAsyncClient.doPost(new MyUrl().getFindAllDepot(), localRequestParams, new MyResponseHandler(getActivity()) {
            @Override
            public void success(JSONObject jsonObject) {
                boolean bool = jsonObject.optString("code").equals("200");
                int i = 0;
                if (bool) {
                    JSONArray localJSONArray = jsonObject.optJSONArray("data");
                    ArrayList localArrayList = new ArrayList();
                    while (i < localJSONArray.length()) {
                        localArrayList.add(localJSONArray.optJSONObject(i).optString("ddepotname"));
                        i++;
                    }
                    ArrayAdapter localArrayAdapter = new ArrayAdapter(CheckOrder.this.getActivity(), android.R.layout.simple_dropdown_item_1line, localArrayList);
                    checkOrderWareHouse.setAdapter(localArrayAdapter);
                    return;
                }
                Toast.makeText(CheckOrder.this.getActivity(), jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void faile(JSONObject jsonObject) {
                Log.i("分仓查询", "请求失败");
            }
        });
    }

    /**
     * Fragment声明周期，实例化工具、设置监听
     * 调用查询供应商和查询权限接口
     *
     * @param paramBundle
     */
    public void onActivityCreated(@Nullable Bundle paramBundle) {
        super.onActivityCreated(paramBundle);
        ButterKnife.bind(getActivity());
        this.sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        this.fragmentToFragment = ((FragmentToFragment) getActivity());
        this.checkOrderBtn.setOnClickListener(this);
        checkOrderBtn.setOnTouchListener(this);
        this.btn_scan.setOnClickListener(this);
        btn_scan.setOnTouchListener(this);


        listViewAdapter = new CheckOrderInfoAdapter(checkOrderBeans,CheckOrder.this);
        pullToRefreshListView.setAdapter(listViewAdapter);


//        this.checkOrderImgBtn.setOnClickListener(this);
//        this.checkOrderImgBtn.setOnTouchListener(this);
//        providerQuery();

        checkPermission();
    }

    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;
    public static final int RESULT_OK = -1;

    /**
     * 页面控件点击监听
     *
     * @param paramView
     */
    public void onClick(View paramView) {
        int i = paramView.getId();
        String str = "";
        if (i == R.id.check_order_btn) {
            if ((!this.checkOrderBarcode.getText().toString().trim().equals(str)) && (!this.checkOrderBarcode.getText().toString().trim().equals(null))) {
                barCodeQuery();
            } else {
                Toast.makeText(getActivity(), "商品条码不能为空!", Toast.LENGTH_SHORT).show();
            }
        }
//        if (i == R.id.check_order_imgbtn) {
        if (i == CheckOrderInfoAdapter.id) {
            HashMap localHashMap = new HashMap();
            this.checkOrderBean.setDnum(checkOrderDnum.getText().toString());
            localHashMap.put("checkOrderBean", checkOrderBean.toString());
            localHashMap.put("dplace", checkOrderPlace.getSelectedItem().toString());
            localHashMap.put("dplaceinput", checkOrderPlace.getSelectedItem().toString());
            localHashMap.put("warehouse", checkOrderWareHouse.getSelectedItem().toString());
//            for (int j = 0; j < providersArrayList.size(); j++) {
//                if (providersArrayList.get(j).getDprovidername().equals(checkOrderProvider.getText().toString())) {
//                    str = ((Providers) providersArrayList.get(j)).getDproviderno();
//                }
//            }
            str = checkOrderProvider.getText().toString().trim().split("-")[1].trim();
            localHashMap.put("dprovidercode", str);
            this.fragmentToFragment.checkOrderToCommitCheckOrder(localHashMap);
            Toast.makeText(getActivity(), "添加成功!", Toast.LENGTH_SHORT).show();
            // 4:清空数量,条码，删除本条记录,光标重新定位到条码,清楚非本供应商商品
            checkOrderDnum.setText("");
            checkOrderBarcode.setText("");
            checkOrderBarcode.requestFocus();
            listViewAdapter.notifyDataSetInvalidated();
        }

        if (i == R.id.btn_scan) {
            //动态权限申请
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
            } else {
                goScan();
            }
        }
    }

    /**
     * 跳转到扫码界面扫码
     */
    private void goScan() {
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
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
                checkOrderBarcode.setText(content);
                barCodeQuery();
            }
        }
    }

    /**
     * Fragment声明周期，加载页面布局文件
     *
     * @param paramLayoutInflater
     * @param paramViewGroup
     * @param paramBundle
     * @return
     */
    @Nullable
    public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        View localView = paramLayoutInflater.inflate(R.layout.check_order, paramViewGroup, false);
        this.unbinder = ButterKnife.bind(this, localView);
        return localView;
    }

    @Override
    public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
        if (paramMotionEvent.getAction() == MotionEvent.ACTION_DOWN)
            paramView.setAlpha((float) 0.3);
        if (paramMotionEvent.getAction() == MotionEvent.ACTION_UP)
            paramView.setAlpha(1);
        return false;
    }

    /**
     * 页面销毁方法
     */
    public void onDestroyView() {
        Unbinder localUnbinder = this.unbinder;
        if (localUnbinder != null)
            localUnbinder.unbind();
        super.onDestroyView();
    }

    public class BroadcastActivity extends Activity {
        //设定为com.china.ui.NEW_LIFEFORM，显示内容前，多一条信息"收到广播信息"；
        //public static final String MY_NEW_LIFEFORM="com.china.ui.NEW_LIFEFORM";
        public static final String MY_NEW_LIFEFORM = "NEW_LIFEFORM";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.check_order);
            //传递数据
            final Intent intent = new Intent(MY_NEW_LIFEFORM);
            intent.putExtra("check_order_provider", checkOrderProvider.getText().toString().trim().split("-")[1].trim());
            //初始化按钮
            @SuppressLint("WrongViewCast") Button button = (Button) findViewById(R.id.check_order_imgbtn);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    sendBroadcast(intent);
                }
            });
        }
    }

}