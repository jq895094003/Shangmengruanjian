package com.smrj.shangmengruanjian.ticket;

import android.content.Intent;
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
import com.smrj.shangmengruanjian.util.MyUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class AllotOrderAccountInfo extends AppCompatActivity implements OnClickListener, View.OnTouchListener {

  /**
   * 33-42行，声明并实例化各种需要的控件、工具，使用ButterKnife绑定
   */
  @BindView(R.id.allot_order_account_info_commit)
  Button allotOrderAccountInfoCommit;

  @BindView(R.id.allot_order_account_info_delete)
  Button allotOrderAccountInfoDelete;

  @BindView(R.id.allot_order_account_info_list)
  ListView allotOrderAccountInfoList;
  ArrayList<AllotOrderAccountInfoBean> orderAccountBeans = new ArrayList();
  String ticketno = "";

  //Activity声明周期，实例化各种工具，设置监听，调用初始方法
  @Override
  protected void onCreate(@Nullable Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.allot_order_account_info);
    Intent localIntent = getIntent();
    ButterKnife.bind(this);
    this.ticketno = localIntent.getStringExtra("ticketno");
    allOrderAccountInfo(this.ticketno);
    this.allotOrderAccountInfoCommit.setOnClickListener(this);
    this.allotOrderAccountInfoCommit.setOnTouchListener(this);
    this.allotOrderAccountInfoDelete.setOnClickListener(this);
    this.allotOrderAccountInfoDelete.setOnTouchListener(this);
  }

  /**
   * 查询所有单据接口
   * 如果返回值code为200则将数据加载到列表控件中
   * 如果不为200则弹出后台返回的提示信息
   * @param paramString
   */
  private void allOrderAccountInfo(String paramString) {
    AsyncHttpClient localAsyncHttpClient = new AsyncHttpClient(true, 80, 443);
    String str = new MyUrl().getAllotOrderInfo();
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("dticketno", paramString);
    localAsyncHttpClient.post(str, localRequestParams, new JsonHttpResponseHandler() {
      public void onFailure(int paramInt, Header[] paramArrayOfHeader, Throwable paramThrowable, JSONObject paramJSONObject) {
        super.onFailure(paramInt, paramArrayOfHeader, paramThrowable, paramJSONObject);
      }

      public void onSuccess(int paramInt, Header[] paramArrayOfHeader, JSONObject paramJSONObject) {
        super.onSuccess(paramInt, paramArrayOfHeader, paramJSONObject);
        boolean bool = paramJSONObject.optString("code").equals("200");
        int i = 0;
        if (bool) {
          Log.i("allOrderAccountInfo", String.valueOf(paramJSONObject));
          JSONArray localJSONArray = paramJSONObject.optJSONArray("data");
          Gson localGson = new Gson();
          while (i < localJSONArray.length()) {
            AllotOrderAccountInfoBean localAllotOrderAccountInfoBean = (AllotOrderAccountInfoBean)localGson.fromJson(String.valueOf(localJSONArray.optJSONObject(i)), AllotOrderAccountInfoBean.class);
            AllotOrderAccountInfo.this.orderAccountBeans.add(localAllotOrderAccountInfoBean);
            i++;
          }
          AllotOrderAccountInfoAdapter localAllotOrderAccountInfoAdapter = new AllotOrderAccountInfoAdapter(AllotOrderAccountInfo.this.orderAccountBeans);
          AllotOrderAccountInfo.this.allotOrderAccountInfoList.setAdapter(localAllotOrderAccountInfoAdapter);
          return;
        }
        Toast.makeText(AllotOrderAccountInfo.this, paramJSONObject.optString("msg"), Toast.LENGTH_SHORT).show();
      }
    });
  }

  /**
   * 单据提交方法
   * 如果返回值code为200则弹出后台返回的信息提示
   * 如果不为200也弹出后台返回的信息提示
   */
  private void allOrderAccountInfoCommit() {
    AsyncHttpClient localAsyncHttpClient = new AsyncHttpClient(true, 80, 443);
    String str = new MyUrl().getAllotOrderAccountCommit();
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("dticketno", this.ticketno);
    localAsyncHttpClient.post(str, localRequestParams, new JsonHttpResponseHandler() {
      public void onFailure(int paramInt, Header[] paramArrayOfHeader, Throwable paramThrowable, JSONObject paramJSONObject) {
        super.onFailure(paramInt, paramArrayOfHeader, paramThrowable, paramJSONObject);
      }

      public void onSuccess(int paramInt, Header[] paramArrayOfHeader, JSONObject paramJSONObject) {
        super.onSuccess(paramInt, paramArrayOfHeader, paramJSONObject);
        if (paramJSONObject.optString("code").equals("200")) {
          Toast.makeText(AllotOrderAccountInfo.this, paramJSONObject.optString("msg"), Toast.LENGTH_SHORT).show();
          AllotOrderAccount.allotOrderAccount.commitAllotOrderAccount(AllotOrderAccountInfo.this.ticketno);
          AllotOrderAccountInfo.this.finish();
          return;
        }
        Toast.makeText(AllotOrderAccountInfo.this, paramJSONObject.optString("msg"), Toast.LENGTH_SHORT).show();
      }
    });
  }

  /**
   * 单据删除接口
   * 如果返回值code为200则弹出后台返回的提示，并删除列表项中的子项
   * 如果不为200也弹出后台返回的提示
   */
  private void allOrderAccountInfoDelete() {
    AsyncHttpClient localAsyncHttpClient = new AsyncHttpClient(true, 80, 443);
    String str = new MyUrl().getAllotOrderAccountDelete();
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("dticketno", this.ticketno);
    localAsyncHttpClient.post(str, localRequestParams, new JsonHttpResponseHandler() {
      public void onFailure(int paramInt, Header[] paramArrayOfHeader, Throwable paramThrowable, JSONObject paramJSONObject) {
        super.onFailure(paramInt, paramArrayOfHeader, paramThrowable, paramJSONObject);
      }

      public void onSuccess(int paramInt, Header[] paramArrayOfHeader, JSONObject paramJSONObject) {
        super.onSuccess(paramInt, paramArrayOfHeader, paramJSONObject);
        if (paramJSONObject.optString("code").equals("200")) {
          Toast.makeText(AllotOrderAccountInfo.this, paramJSONObject.optString("msg"), Toast.LENGTH_SHORT).show();
          AllotOrderAccount.allotOrderAccount.deleteAllotOrderAccount(AllotOrderAccountInfo.this.ticketno);
          AllotOrderAccountInfo.this.finish();
          return;
        }
        Toast.makeText(AllotOrderAccountInfo.this, paramJSONObject.optString("msg"), Toast.LENGTH_SHORT).show();
      }
    });
  }

  /**
   * 页面控件单击监听，调用对应的方法
   * @param paramView
   */
  public void onClick(View paramView) {
    int i = paramView.getId();
    if (i == R.id.allot_order_account_info_commit) {
      allOrderAccountInfoCommit();
    }
    if (i == R.id.allot_order_account_info_delete){
      allOrderAccountInfoDelete();
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
 * Qualified Name:     com.example.shangmengsoft.AllotOrderAccountInfo
 * JD-Core Version:    0.6.0
 */