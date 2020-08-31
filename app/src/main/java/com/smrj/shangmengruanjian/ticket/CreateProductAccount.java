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

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class CreateProductAccount extends AppCompatActivity implements OnItemClickListener {
  /**
   * 声明所需要的对象
   */
  public static CreateProductAccount createProductAccount;
  CreateProductAccountAdapter createProductAccountAdapter;
  ArrayList<CreateProductAccountBean> createProductAccountBeans = new ArrayList();

  @BindView(R.id.create_product_account_list)
  ListView createProductAccountList;

  /**
   * 查询新建商品单据方法
   * 如果返回值为200则使用适配器显示在页面，如果不为200则弹出后台返回的信息提示
   */
  private void createProductAccount() {
    new AsyncHttpClient(true, 80, 443).post(new MyUrl().getFindCreateNewProduct(), new RequestParams(), new JsonHttpResponseHandler() {
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
          JSONArray localJSONArray = paramJSONObject.optJSONArray("data");
          Gson localGson = new Gson();
          while (i < localJSONArray.length()) {
            System.out.println(localJSONArray.optJSONObject(i));
            CreateProductAccountBean localCreateProductAccountBean = (CreateProductAccountBean)localGson.fromJson(String.valueOf(localJSONArray.optJSONObject(i)), CreateProductAccountBean.class);
            CreateProductAccount.this.createProductAccountBeans.add(localCreateProductAccountBean);
            i++;
          }
          CreateProductAccount localCreateProductAccount = CreateProductAccount.this;
          localCreateProductAccount.createProductAccountAdapter = new CreateProductAccountAdapter(createProductAccountBeans);
          CreateProductAccount.this.createProductAccountList.setAdapter(CreateProductAccount.this.createProductAccountAdapter);
          return;
        }
        Toast.makeText(CreateProductAccount.this, paramJSONObject.optString("msg"), Toast.LENGTH_SHORT).show();
      }
    });
  }

  //Activity生命周期，页面创建方法
  //设置监听，调用查询方法
  @Override
  protected void onCreate(@Nullable Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.create_product_account);
    ButterKnife.bind(this);
    this.createProductAccountList.setOnItemClickListener(this);
    createProductAccount();
    createProductAccount = this;
  }

  /**
   * 列表控件子项单击监听实现方法
   * 进行页面跳转并向下一个页面发送所需要的参数
   * @param paramAdapterView
   * @param paramView
   * @param paramInt
   * @param paramLong
   */
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
    Intent localIntent = new Intent(this, CreateProductAccountInfo.class);
    localIntent.putExtra("createProductAccountBean", (Serializable)this.createProductAccountBeans.get(paramInt));
    startActivity(localIntent);
  }

  /**
   * 移除列表子项的方法
   * 根据传入的单据号来删除
   * @param paramString
   */
  public void removeCreateProductAccountBeans(String paramString) {
    for (int i = 0; i < this.createProductAccountBeans.size(); i++) {
      if (!((CreateProductAccountBean)this.createProductAccountBeans.get(i)).getDticketno().equals(paramString))
        continue;
      this.createProductAccountBeans.remove(i);
    }
    this.createProductAccountAdapter.notifyDataSetChanged();
  }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.CreateProductAccount
 * JD-Core Version:    0.6.0
 */