package com.smrj.shangmengruanjian.ticket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

public class CheckOrderAccount extends AppCompatActivity implements OnItemClickListener {
  /**
   * 30-37行，声明并实例化页面控件，所需要的工具
   */
  public static CheckOrderAccount checkOrderAccount;
  CheckOrderAccountAdapter checkOrderAccountAdapter;
  ArrayList<CheckOrderAccountBean> checkOrderAccountBeans = new ArrayList();

  @BindView(R.id.check_order_account_list)
  ListView checkOrderAccountList;

  /**
   * Activity声明周期
   * 实例化工具，调用查询订单接口
   * @param paramBundle
   */
  @Override
  protected void onCreate(@Nullable Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.check_order_account);
    ButterKnife.bind(this);
    checkOrderAccount = this;
    this.checkOrderAccountList.setOnItemClickListener(this);
    this.checkOrderAccountAdapter = new CheckOrderAccountAdapter(this.checkOrderAccountBeans);
    this.checkOrderAccountList.setAdapter(this.checkOrderAccountAdapter);
    checkOrderAccount();
  }

  /**
   * 查询订单接口
   * 如果返回值code为200则将数据加载到列表
   * 如果不为200则弹出后台提示信息
   */
  private void checkOrderAccount() {
    new AsyncHttpClient().post(new MyUrl().getAllOrderOfYS(), new RequestParams(), new JsonHttpResponseHandler() {
      public void onFailure(int paramInt, Header[] paramArrayOfHeader, Throwable paramThrowable, JSONObject paramJSONObject) {
        super.onFailure(paramInt, paramArrayOfHeader, paramThrowable, paramJSONObject);
      }

      public void onSuccess(int paramInt, Header[] paramArrayOfHeader, JSONObject paramJSONObject) {
        super.onSuccess(paramInt, paramArrayOfHeader, paramJSONObject);
        boolean bool = paramJSONObject.optString("code").equals("200");
        int i = 0;
        if (bool) {
          JSONArray localJSONArray = paramJSONObject.optJSONArray("data");
          Gson localGson = new Gson();
          while (i < localJSONArray.length()) {
            CheckOrderAccountBean localCheckOrderAccountBean = (CheckOrderAccountBean)localGson.fromJson(String.valueOf(localJSONArray.optJSONObject(i)), CheckOrderAccountBean.class);
            CheckOrderAccount.this.checkOrderAccountBeans.add(localCheckOrderAccountBean);
            CheckOrderAccount.this.checkOrderAccountAdapter.notifyDataSetChanged();
            i++;
          }
        }
        Toast.makeText(CheckOrderAccount.this, paramJSONObject.optString("msg"), Toast.LENGTH_SHORT).show();
      }
    });
  }


  /**
   * 列表子项点击事件监听
   * 跳转页面，并发送所需要的参数
   * @param paramAdapterView
   * @param paramView
   * @param paramInt
   * @param paramLong
   */
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
    CheckOrderAccountBean localCheckOrderAccountBean = (CheckOrderAccountBean)this.checkOrderAccountBeans.get(paramInt);
    Intent localIntent = new Intent(this, CheckOrderAccountInfo.class);
    localIntent.putExtra("checkOrderAccountBean", localCheckOrderAccountBean);
    startActivity(localIntent);
  }

  /**
   * 删除子项方法
   * 从列表控件中删除所对应的子项
   * @param paramString
   */
  public void removeItem(String paramString) {
    for (int i = 0; i < this.checkOrderAccountBeans.size(); i++) {
      if (!((CheckOrderAccountBean)this.checkOrderAccountBeans.get(i)).getDticketno().equals(paramString))
        continue;
      this.checkOrderAccountBeans.remove(i);
    }
    this.checkOrderAccountAdapter.notifyDataSetChanged();
  }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.CheckOrderAccount
 * JD-Core Version:    0.6.0
 */