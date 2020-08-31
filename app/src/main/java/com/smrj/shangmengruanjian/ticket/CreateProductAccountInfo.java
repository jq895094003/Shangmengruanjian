package com.smrj.shangmengruanjian.ticket;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.util.MyUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class CreateProductAccountInfo extends AppCompatActivity implements OnClickListener, View.OnTouchListener {
    /**
     * 27-39行，声明并实例化页面控件，使用ButterKnife绑定
     */
    @BindView(R.id.create_product_account_info_commit)
    Button commit;

    @BindView(R.id.create_product_account_info_delete)
    Button delete;

    @BindView(R.id.create_product_account_info_place)
    TextView place;

    @BindView(R.id.create_product_account_info_placeinput)
    TextView placeInput;

    @BindView(R.id.create_product_account_info_ticketno)
    TextView ticketNo;

    @BindView(R.id.create_product_account_info_productName)
    TextView productName;
    @BindView(R.id.create_product_account_info_masterbarcode)
    TextView masterbarcode;
    @BindView(R.id.create_product_account_info_unitname)
    TextView unitname;
    @BindView(R.id.create_product_account_info_providercode)
    TextView providerCode;
    @BindView(R.id.create_product_account_info_thingtype)
    TextView thingType;
    @BindView(R.id.create_product_account_info_pricein)
    TextView priceIn;
    @BindView(R.id.create_product_account_info_price)
    TextView price;
    @BindView(R.id.create_product_account_info_pricehy)
    TextView priceHy;
    @BindView(R.id.create_product_account_info_state)
    TextView thingstate;
    @BindView(R.id.create_product_account_info_pricepf)
    TextView pricePf;


    private void createProductAccountInfoQuery(String dtiketNo) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = new MyUrl().getCreateProductAccountInfoQuery();
        RequestParams requestParams = new RequestParams();
        requestParams.put("dticketno", dtiketNo);
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONArray jsonArray = response.optJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    productName.setText(jsonArray.optJSONObject(i).optString("dname"));
                    masterbarcode.setText(jsonArray.optJSONObject(i).optString("dmasterbarcode"));
                    unitname.setText(jsonArray.optJSONObject(i).optString("dunitname"));
                    providerCode.setText(jsonArray.optJSONObject(i).optString("dprovidercode"));
                    thingType.setText(jsonArray.optJSONObject(i).optString("dthingtype"));
                    priceIn.setText(jsonArray.optJSONObject(i).optString("dpricein"));
                    price.setText(jsonArray.optJSONObject(i).optString("dprice"));
                    priceHy.setText(jsonArray.optJSONObject(i).optString("dpricehy"));
                    thingstate.setText(jsonArray.optJSONObject(i).optString("dthingstate"));
                    pricePf.setText(jsonArray.optJSONObject(i).optString("dpricepf"));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    /**
     * 提交创建商品订单
     * 如果返回值code为200则弹出后台提示信息
     * 如果返回值不为200也弹出后台提示信息
     */
    private void commitCreateProductAccountInfo() {
        AsyncHttpClient localAsyncHttpClient = new AsyncHttpClient(true, 80, 443);
        String str = new MyUrl().getCommitCreateNewProduct();
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("dticketno", this.ticketNo.getText().toString());
        localAsyncHttpClient.post(str, localRequestParams, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int paramInt, Header[] paramArrayOfHeader, Throwable paramThrowable, JSONObject paramJSONObject) {
                super.onFailure(paramInt, paramArrayOfHeader, paramThrowable, paramJSONObject);
            }

            @Override
            public void onSuccess(int paramInt, Header[] paramArrayOfHeader, JSONObject paramJSONObject) {
                super.onSuccess(paramInt, paramArrayOfHeader, paramJSONObject);
                if (paramJSONObject.optString("code").equals("200")) {
                    Toast.makeText(CreateProductAccountInfo.this, paramJSONObject.optString("msg"), Toast.LENGTH_SHORT).show();
                    CreateProductAccount.createProductAccount.removeCreateProductAccountBeans(CreateProductAccountInfo.this.ticketNo.getText().toString());
                    CreateProductAccountInfo.this.finish();
                    return;
                }
                Toast.makeText(CreateProductAccountInfo.this, paramJSONObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 删除创建商品订单
     * 如果返回值code为200则弹出后台提示信息
     * 如果返回值不为200也弹出后台提示信息
     */
    private void deleteCreateProductAccountInfo() {
        AsyncHttpClient localAsyncHttpClient = new AsyncHttpClient(true, 80, 443);
        String str = new MyUrl().getDeleteCreateNewProduct();
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("dticketno", this.ticketNo.getText().toString());
        localAsyncHttpClient.post(str, localRequestParams, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int paramInt, Header[] paramArrayOfHeader, Throwable paramThrowable, JSONObject paramJSONObject) {
                super.onFailure(paramInt, paramArrayOfHeader, paramThrowable, paramJSONObject);
            }

            @Override
            public void onSuccess(int paramInt, Header[] paramArrayOfHeader, JSONObject paramJSONObject) {
                super.onSuccess(paramInt, paramArrayOfHeader, paramJSONObject);
                if (paramJSONObject.optString("code").equals("200")) {
                    Toast.makeText(CreateProductAccountInfo.this, paramJSONObject.optString("msg"), Toast.LENGTH_SHORT).show();
                    CreateProductAccount.createProductAccount.removeCreateProductAccountBeans(CreateProductAccountInfo.this.ticketNo.getText().toString());
                    CreateProductAccountInfo.this.finish();
                    return;
                }
                Toast.makeText(CreateProductAccountInfo.this, paramJSONObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 页面控件点击事件
     *
     * @param paramView
     */
    @Override
    public void onClick(View paramView) {
        switch (paramView.getId()) {
            case R.id.create_product_account_info_delete:
                deleteCreateProductAccountInfo();
                break;
            case R.id.create_product_account_info_commit:
                commitCreateProductAccountInfo();
                break;
        }
    }

    /**
     * Activity创建方法
     * 实例化所需要的工具类
     * 设置监听
     *
     * @param paramBundle
     */
    @Override
    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.create_product_account_info);
        ButterKnife.bind(this);
        CreateProductAccountBean localCreateProductAccountBean = (CreateProductAccountBean) getIntent().getSerializableExtra("createProductAccountBean");
        this.ticketNo.setText(localCreateProductAccountBean.getDticketno());
        this.place.setText(localCreateProductAccountBean.getDplace());
        this.placeInput.setText(localCreateProductAccountBean.getDplaceinput());
        this.commit.setOnClickListener(this);
        this.commit.setOnTouchListener(this);
        this.delete.setOnClickListener(this);
        this.delete.setOnTouchListener(this);
        createProductAccountInfoQuery(localCreateProductAccountBean.getDticketno());
    }

    @Override
    public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
        if (paramMotionEvent.getAction() == 0)
            paramView.setBackgroundColor(-65536);
        if (paramMotionEvent.getAction() == 1)
            paramView.setBackgroundColor(-1);
        return false;
    }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.CreateProductAccountInfo
 * JD-Core Version:    0.6.0
 */