package com.smrj.shangmengruanjian.ticket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class RunAnAccount extends AppCompatActivity implements OnItemClickListener {
  /**
   * 声明页面的控件并使用ButterKnife绑定
   */
  @BindView(R.id.run_an_account_list)
  ListView runAnAccountList;
  ArrayList<RunAnAccountBean> runAnAccounts = new ArrayList<>();
  RunAnAccountAdapter localRunAnAccountAdapter;

  /**
   * 查询信息接口，如果返回200则在列表控件显示，如果不为200则弹出后台返回的错误信息
   */
  private void runAnAccountAsync() {
    new AsyncHttpClient(true, 80, 443).post(new MyUrl().getRunUpAnAccountUrl(), new RequestParams(), new JsonHttpResponseHandler() {
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
          Gson localGson = new Gson();
          try {
            JSONArray localJSONArray = paramJSONObject.getJSONArray("data");
            while (i < localJSONArray.length()) {
              Log.i("runAnAccount", String.valueOf(localJSONArray.optJSONObject(i)));
              RunAnAccountBean localRunAnAccount = (RunAnAccountBean)localGson.fromJson(String.valueOf(localJSONArray.getJSONObject(i)), RunAnAccountBean.class);
              RunAnAccount.this.runAnAccounts.add(localRunAnAccount);
              i++;
            }
            localRunAnAccountAdapter.notifyDataSetChanged();
            return;
          } catch (JSONException localJSONException) {
            localJSONException.printStackTrace();
            return;
          }
        }
        Toast.makeText(RunAnAccount.this, paramJSONObject.optString("msg"), Toast.LENGTH_SHORT).show();
      }
    });
  }

  //Activity生命周期，页面创建方法
  @Override
  protected void onCreate(@Nullable Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.run_an_account);
    ButterKnife.bind(this);
    this.runAnAccountList.setOnItemClickListener(this);
    localRunAnAccountAdapter = new RunAnAccountAdapter(runAnAccounts);
    runAnAccountList.setAdapter(localRunAnAccountAdapter);
    runAnAccountAsync();
  }

  /**
   * 列表控件子项单击监听实现方法
   * 进行页面跳转和放入下一个页面需要的参数
   * @param paramAdapterView
   * @param paramView
   * @param paramInt
   * @param paramLong
   */
  @Override
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
    Intent localIntent = new Intent(this, AccountAllItem.class);
    localIntent.putExtra("ticketno", ((RunAnAccountBean)this.runAnAccounts.get(paramInt)).getDticketno());
    startActivity(localIntent);
    this.finish();
  }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.RunAnAccountBean
 * JD-Core Version:    0.6.0
 */