package com.smrj.shangmengruanjian.ticket;

import android.os.Bundle;
import android.util.Log;
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

public class AccountAllItem extends AppCompatActivity implements OnClickListener, View.OnTouchListener {
    /**
     * 30-91行为声明并实例化页面控件，使用ButterKnife绑定
     */
    @BindView(R.id.all_item_commit)
    Button allItemCommit;

    @BindView(R.id.all_item_delete)
    Button allItemDelete;

    @BindView(R.id.all_item_dbarcode)
    TextView barCode;

    @BindView(R.id.all_item_dcode)
    TextView code;

    @BindView(R.id.all_item_ddate)
    TextView date;

    @BindView(R.id.all_item_dname)
    TextView name;

    @BindView(R.id.all_item_dnum)
    TextView num;

    @BindView(R.id.all_item_dpack)
    TextView pack;

    @BindView(R.id.all_item_dprice)
    TextView price;

    @BindView(R.id.all_item_dpricehy)
    TextView priceHy;

    @BindView(R.id.all_item_dpricehynew)
    TextView priceHyNew;

    @BindView(R.id.all_item_dpriceinnew)
    TextView priceInNew;

    @BindView(R.id.all_item_dpricenew)
    TextView priceNew;

    @BindView(R.id.all_item_dpricepf)
    TextView pricePf;

    @BindView(R.id.all_item_dpricepfnew)
    TextView pricePfNew;

    @BindView(R.id.all_item_dpricein)
    TextView pricein;

    @BindView(R.id.all_item_dprovidercode)
    TextView providerCode;

    @BindView(R.id.all_item_dspec)
    TextView spec;

    @BindView(R.id.all_item_dthingcode)
    TextView thingCode;

    @BindView(R.id.all_item_dticketno)
    TextView ticketNo;

    @BindView(R.id.all_item_dunitname)
    TextView unitName;

    //Activity生命周期，实例化各种工具并设置监听
    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.run_an_account_all_item);
        ButterKnife.bind(this);
        allItemAsync(getIntent().getStringExtra("ticketno"));
        this.allItemCommit.setOnClickListener(this);
        this.allItemDelete.setOnClickListener(this);
        this.allItemCommit.setOnTouchListener(this);
        this.allItemDelete.setOnTouchListener(this);
    }

    //单据信息查询，如果返回值code为200则设置各种参数，如果不为200则弹出后台返回的提示
    private void allItemAsync(String paramString) {
        AsyncHttpClient localAsyncHttpClient = new AsyncHttpClient(true, 80, 443);
        String str = new MyUrl().getRunUpAnAccountAllItemUrl();
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("dticketno", paramString);
        localAsyncHttpClient.post(str, localRequestParams, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int paramInt, Header[] paramArrayOfHeader, Throwable paramThrowable, JSONObject paramJSONObject) {
                super.onFailure(paramInt, paramArrayOfHeader, paramThrowable, paramJSONObject);
            }

            @Override
            public void onSuccess(int paramInt, Header[] paramArrayOfHeader, JSONObject paramJSONObject) {
                super.onSuccess(paramInt, paramArrayOfHeader, paramJSONObject);
                boolean bool = paramJSONObject.optString("code").equals("200");
                int i = 0;
                if (bool) {
                    Log.i("allItem", String.valueOf(paramJSONObject));
                    JSONArray localJSONArray = paramJSONObject.optJSONArray("data");
                    System.out.println(localJSONArray);
                    while (i < localJSONArray.length()) {
                        AccountAllItem.this.ticketNo.setText(localJSONArray.optJSONObject(i).optString("dticketno"));
                        AccountAllItem.this.thingCode.setText(localJSONArray.optJSONObject(i).optString("dthingcode"));
                        AccountAllItem.this.barCode.setText(localJSONArray.optJSONObject(i).optString("dbarcode"));
                        AccountAllItem.this.code.setText(localJSONArray.optJSONObject(i).optString("dcode"));
                        AccountAllItem.this.unitName.setText(localJSONArray.optJSONObject(i).optString("dunitname"));
                        AccountAllItem.this.name.setText(localJSONArray.optJSONObject(i).optString("dname"));
                        AccountAllItem.this.spec.setText(localJSONArray.optJSONObject(i).optString("dspec"));
                        AccountAllItem.this.pack.setText(localJSONArray.optJSONObject(i).optString("dpack"));
                        AccountAllItem.this.date.setText(localJSONArray.optJSONObject(i).optString("ddate"));
                        AccountAllItem.this.price.setText(localJSONArray.optJSONObject(i).optString("dprice"));
                        AccountAllItem.this.priceNew.setText(localJSONArray.optJSONObject(i).optString("dpricenew"));
                        AccountAllItem.this.priceHy.setText(localJSONArray.optJSONObject(i).optString("dpricehy"));
                        AccountAllItem.this.priceHyNew.setText(localJSONArray.optJSONObject(i).optString("dpricehynew"));
                        AccountAllItem.this.pricePf.setText(localJSONArray.optJSONObject(i).optString("dpricepf"));
                        AccountAllItem.this.pricePfNew.setText(localJSONArray.optJSONObject(i).optString("dpricepfnew"));
                        AccountAllItem.this.pricein.setText(localJSONArray.optJSONObject(i).optString("dpricein"));
                        AccountAllItem.this.priceInNew.setText(localJSONArray.optJSONObject(i).optString("dpriceinnew"));
                        AccountAllItem.this.num.setText(localJSONArray.optJSONObject(i).optString("dnum"));
                        AccountAllItem.this.providerCode.setText(localJSONArray.optJSONObject(i).optString("dprovidercode"));
                        i++;
                    }
                }
                Toast.makeText(AccountAllItem.this, paramJSONObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //单据删除方法
    //返回值code如果为200则弹出删除成功提示，反之弹出后台返回的提示信息
    private void allItemDelete() {
        AsyncHttpClient localAsyncHttpClient = new AsyncHttpClient(true, 80, 443);
        String str = new MyUrl().getAllItemDeleteUrl();
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("DTICKETNO", this.ticketNo.getText().toString());
        localAsyncHttpClient.post(str, localRequestParams, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int paramInt, Header[] paramArrayOfHeader, Throwable paramThrowable, JSONObject paramJSONObject) {
                super.onFailure(paramInt, paramArrayOfHeader, paramThrowable, paramJSONObject);
            }
            @Override
            public void onSuccess(int paramInt, Header[] paramArrayOfHeader, JSONObject paramJSONObject) {
                super.onSuccess(paramInt, paramArrayOfHeader, paramJSONObject);
                if (paramJSONObject.optString("code").equals("200")) {
                    Toast.makeText(AccountAllItem.this, "删除成功!", Toast.LENGTH_SHORT).show();
                    AccountAllItem.this.finish();
                    return;
                }
                Toast.makeText(AccountAllItem.this, paramJSONObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 单据提交接口
     * 如果返回值code为200则弹出审核成功提示
     * 如果不为200则弹出后台提示信息
     */
    private void runUpAnAccountCommit() {
        AsyncHttpClient localAsyncHttpClient = new AsyncHttpClient(true, 80, 443);
        String str = new MyUrl().getRunUpAnAccountCommitUrl();
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
                    Toast.makeText(AccountAllItem.this, "审核成功!", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                Toast.makeText(AccountAllItem.this, paramJSONObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //按钮单击事件监听
    @Override
    public void onClick(View paramView) {
        int i = paramView.getId();
        if (i == R.id.all_item_commit) {
            runUpAnAccountCommit();
        }
        if (i == R.id.all_item_delete){
            allItemDelete();
        }
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
 * Qualified Name:     com.example.shangmengsoft.AccountAllItem
 * JD-Core Version:    0.6.0
 */