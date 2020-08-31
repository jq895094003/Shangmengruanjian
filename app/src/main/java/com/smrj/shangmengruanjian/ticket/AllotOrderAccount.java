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

public class AllotOrderAccount extends AppCompatActivity implements OnItemClickListener {
    /**
     * 36-41行声明和实例化各种控件、工具，使用ButterKnife绑定
     */
    public static AllotOrderAccount allotOrderAccount;
    AllotOrderAccountAdapter allotOrderAccountAdapter;
    ArrayList<AllotOrderAccountBean> allotOrderAccountBeans = new ArrayList();

    @BindView(R.id.check_order_account_list)
    ListView allotOrderAccountList;

    /**
     * Activity生命周期
     * 实例化各种工具，调用初始方法
     * @param paramBundle
     */
    @Override
    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.check_order_account);
        ButterKnife.bind(this);
        allotOrderAccount = this;
        this.allotOrderAccountList.setOnItemClickListener(this);
        allotOrderAccount();
    }

    /**
     * 查询所有订单接口
     * 如果返回值code为200则将数据加载进列表控件
     * 如果不为200则弹出后台返回的提示信息
     */
    private void allotOrderAccount() {
        new AsyncHttpClient(true, 80, 443).post(new MyUrl().getFindAllotOrder(), new RequestParams(), new JsonHttpResponseHandler() {
            public void onFailure(int paramInt, Header[] paramArrayOfHeader, Throwable paramThrowable, JSONObject paramJSONObject) {
                super.onFailure(paramInt, paramArrayOfHeader, paramThrowable, paramJSONObject);
            }

            public void onSuccess(int paramInt, Header[] paramArrayOfHeader, JSONObject paramJSONObject) {
                super.onSuccess(paramInt, paramArrayOfHeader, paramJSONObject);
                boolean bool = paramJSONObject.optInt("code") == 200;
                System.out.println(paramJSONObject);
                int i = 0;
                if (bool) {
                    JSONArray localJSONArray = paramJSONObject.optJSONArray("data");
                    Gson localGson = new Gson();
                    while (i < localJSONArray.length()) {
                        AllotOrderAccountBean localAllotOrderAccountBean = (AllotOrderAccountBean) localGson.fromJson(String.valueOf(localJSONArray.optJSONObject(i)), AllotOrderAccountBean.class);
                        AllotOrderAccount.this.allotOrderAccountBeans.add(localAllotOrderAccountBean);
                        i++;
                    }
                    AllotOrderAccount localAllotOrderAccount = AllotOrderAccount.this;
                    localAllotOrderAccount.allotOrderAccountAdapter = new AllotOrderAccountAdapter(allotOrderAccountBeans);
                    AllotOrderAccount.this.allotOrderAccountList.setAdapter(AllotOrderAccount.this.allotOrderAccountAdapter);
                    return;
                }
                Toast.makeText(AllotOrderAccount.this, paramJSONObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 提交单据回调
     * 从列表控件中删除提交的订单
     * @param paramString
     */
    public void commitAllotOrderAccount(String paramString) {
        for (int i = 0; i < this.allotOrderAccountBeans.size(); i++) {
            if (!((AllotOrderAccountBean) this.allotOrderAccountBeans.get(i)).getDticketno().equals(paramString))
                continue;
            this.allotOrderAccountBeans.remove(i);
        }
        this.allotOrderAccountAdapter.notifyDataSetChanged();
    }

    /**
     * 删除订单回调
     * 从列表控件中删除提交的订单
     * @param paramString
     */
    public void deleteAllotOrderAccount(String paramString) {
        for (int i = 0; i < this.allotOrderAccountBeans.size(); i++) {
            if (!((AllotOrderAccountBean) this.allotOrderAccountBeans.get(i)).getDticketno().equals(paramString))
                continue;
            this.allotOrderAccountBeans.remove(i);
        }
        this.allotOrderAccountAdapter.notifyDataSetChanged();
    }

    //列表控件子项单击监听
    //跳转页面，并向下一页面发送需要的数据
    @Override
    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
        Intent localIntent = new Intent(this, AllotOrderAccountInfo.class);
        localIntent.putExtra("ticketno", ((AllotOrderAccountBean) this.allotOrderAccountBeans.get(paramInt)).getDticketno());
        startActivity(localIntent);
    }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.AllotOrderAccount
 * JD-Core Version:    0.6.0
 */