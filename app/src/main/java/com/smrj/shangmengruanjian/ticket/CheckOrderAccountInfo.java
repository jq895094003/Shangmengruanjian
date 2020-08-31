package com.smrj.shangmengruanjian.ticket;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.adapter.CheckOrderAccountInfoAdapter;
import com.smrj.shangmengruanjian.bean.CheckOrderAccountInfoBean;
import com.smrj.shangmengruanjian.util.MyUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class CheckOrderAccountInfo extends AppCompatActivity implements OnClickListener, View.OnTouchListener {
  /**
   * 32-43行，声明并实例化页面控件、所需工具
   * 使用ButterKnife绑定
   */
  CheckOrderAccountBean checkOrderAccountBean;
  ArrayList<CheckOrderAccountInfoBean> checkOrderAccountInfoBeans = new ArrayList();

  @BindView(R.id.check_order_account_info_commit)
  Button checkOrderAccountInfoCommit;

  @BindView(R.id.check_order_account_info_delete)
  Button checkOrderAccountInfoDelete;

  @BindView(R.id.check_order_account_info_list)
  ListView checkOrderAccountInfoList;
  String ticktno = "";

  /**
   * Activity生命周期
   * 实例化页面控件、所需工具、设置监听
   * 调用查询订单方法
   * @param paramBundle
   */
  @Override
  protected void onCreate(@Nullable Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.check_order_account_info);
    ButterKnife.bind(this);
    this.checkOrderAccountInfoCommit.setOnClickListener(this);
    this.checkOrderAccountInfoCommit.setOnTouchListener(this);
    this.checkOrderAccountInfoDelete.setOnClickListener(this);
    this.checkOrderAccountInfoDelete.setOnTouchListener(this);
    this.checkOrderAccountBean = ((CheckOrderAccountBean)getIntent().getSerializableExtra("checkOrderAccountBean"));
    this.ticktno = this.checkOrderAccountBean.getDticketno();
    checkOrderAccountInfo();
  }

  /**
   * 查询订饭方法
   * 如果返回值code为200则将数据加载到列表控件
   * 如果不为200则弹出后台提示信息
   */
  private void checkOrderAccountInfo() {
    AsyncHttpClient localAsyncHttpClient = new AsyncHttpClient(true, 80, 443);
    String str = new MyUrl().getOrderInfoYs();
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("dticketno", this.checkOrderAccountBean.getDticketno());
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
          Log.i("response", String.valueOf(paramJSONObject));
          JSONArray localJSONArray = paramJSONObject.optJSONArray("data");
          Gson localGson = new Gson();
          while (i < localJSONArray.length()) {
            CheckOrderAccountInfoBean localCheckOrderAccountInfoBean = (CheckOrderAccountInfoBean)localGson.fromJson(String.valueOf(localJSONArray.optJSONObject(i)), CheckOrderAccountInfoBean.class);
            CheckOrderAccountInfo.this.checkOrderAccountInfoBeans.add(localCheckOrderAccountInfoBean);
            i++;
          }
          CheckOrderAccountInfoAdapter localCheckOrderAccountInfoAdapter = new CheckOrderAccountInfoAdapter(CheckOrderAccountInfo.this.checkOrderAccountInfoBeans);
          CheckOrderAccountInfo.this.checkOrderAccountInfoList.setAdapter(localCheckOrderAccountInfoAdapter);
          return;
        }
        Toast.makeText(CheckOrderAccountInfo.this, paramJSONObject.optString("msg"), Toast.LENGTH_SHORT).show();
      }
    });
  }

  /**
   * 提交订单方法
   * 如果返回值code为200则弹出审核成功信息
   * 如果不为200则弹出后台返回的提示信息
   */
  private void commitCheckOrderAccountInfo() {
    AsyncHttpClient localAsyncHttpClient = new AsyncHttpClient(true, 80, 443);
    String str = new MyUrl().getCommitOrderInfoYs();
    RequestParams localRequestParams = new RequestParams();
    Log.i("dticketno", this.ticktno);
    localRequestParams.put("dticketno", this.ticktno);
    localAsyncHttpClient.post(str, localRequestParams, new JsonHttpResponseHandler() {
      @Override
      public void onFailure(int paramInt, Header[] paramArrayOfHeader, Throwable paramThrowable, JSONObject paramJSONObject) {
        super.onFailure(paramInt, paramArrayOfHeader, paramThrowable, paramJSONObject);
      }
      @Override
      public void onSuccess(int paramInt, Header[] paramArrayOfHeader, JSONObject paramJSONObject) {
        super.onSuccess(paramInt, paramArrayOfHeader, paramJSONObject);
        if (paramJSONObject.optString("code").equals("200")) {
          Toast.makeText(CheckOrderAccountInfo.this, "审核成功!", Toast.LENGTH_SHORT).show();
          CheckOrderAccount.checkOrderAccount.removeItem(CheckOrderAccountInfo.this.ticktno);
          CheckOrderAccountInfo.this.finish();
          return;
        }
        Toast.makeText(CheckOrderAccountInfo.this, paramJSONObject.optString("msg"), Toast.LENGTH_SHORT).show();
      }
    });
  }

  /**
   * 删除订单方法
   * 如果返回值code为200则弹出删除成功信息
   * 如果不为200则弹出后台提示信息
   */
  private void deleteCheckOrderAccountInfo() {
    AsyncHttpClient localAsyncHttpClient = new AsyncHttpClient(true, 80, 443);
    String str = new MyUrl().getDeleteOrderInfoYs();
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("dticketno", this.ticktno);
    localAsyncHttpClient.post(str, localRequestParams, new JsonHttpResponseHandler() {
      @Override
      public void onFailure(int paramInt, Header[] paramArrayOfHeader, Throwable paramThrowable, JSONObject paramJSONObject) {
        super.onFailure(paramInt, paramArrayOfHeader, paramThrowable, paramJSONObject);
      }

      @Override
      public void onSuccess(int paramInt, Header[] paramArrayOfHeader, JSONObject paramJSONObject) {
        super.onSuccess(paramInt, paramArrayOfHeader, paramJSONObject);
        if (paramJSONObject.optString("code").equals("200")) {
          Toast.makeText(CheckOrderAccountInfo.this, "删除成功!", Toast.LENGTH_SHORT).show();
          CheckOrderAccount.checkOrderAccount.removeItem(CheckOrderAccountInfo.this.ticktno);
          CheckOrderAccountInfo.this.finish();
          return;
        }
        Toast.makeText(CheckOrderAccountInfo.this, "删除失败!", Toast.LENGTH_SHORT).show();
      }
    });
  }

  /**
   * 页面控件点击事件监听，调用对应的方法
   * @param paramView
   */
  @Override
  public void onClick(View paramView) {
    switch (paramView.getId()) {
    case R.id.check_order_account_info_delete:
      deleteCheckOrderAccountInfo();
      return;
    case R.id.check_order_account_info_commit:
      commitCheckOrderAccountInfo();
      return;
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
 * Qualified Name:     com.example.shangmengsoft.CheckOrderAccountInfo
 * JD-Core Version:    0.6.0
 */