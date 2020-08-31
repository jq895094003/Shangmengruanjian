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
import com.smrj.shangmengruanjian.adapter.PandianAccountAdapter;
import com.smrj.shangmengruanjian.bean.PandianAccountBean;
import com.smrj.shangmengruanjian.util.MyUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class PandianAccount extends AppCompatActivity implements OnItemClickListener {
    /**
     * 38-43行，声明所需要的工具，实例化页面控件，使用ButterKnife进行绑定
     */
    public static PandianAccount pandianAccount;
    PandianAccountAdapter pandianAccountAdapter;
    ArrayList<PandianAccountBean> pandianAccountBeans = new ArrayList();

    @BindView(R.id.pandian_list)
    ListView pandianAccountList;

    /**
     * 查询所有盘点单据接口
     * 如果返回值code为200则更新列表控件内容
     * 如果返回值code不为200则弹出弹出后台提示
     */
    private void findAllPandianOrder() {
        new AsyncHttpClient(true, 80, 443).post(new MyUrl().getFindAllPandianOrder(), new RequestParams(), new JsonHttpResponseHandler() {
            public void onFailure(int paramInt, Header[] paramArrayOfHeader, Throwable paramThrowable, JSONObject paramJSONObject) {
                super.onFailure(paramInt, paramArrayOfHeader, paramThrowable, paramJSONObject);
            }

            public void onSuccess(int paramInt, Header[] paramArrayOfHeader, JSONObject paramJSONObject) {
                super.onSuccess(paramInt, paramArrayOfHeader, paramJSONObject);
                boolean bool = paramJSONObject.optString("code").equals("200");
                System.out.println(paramJSONObject);
                int i = 0;
                if (bool) {
                    JSONArray localJSONArray = paramJSONObject.optJSONArray("data");
                    Gson localGson = new Gson();
                    while (i < localJSONArray.length()) {
                        Log.i("findAllPandianOrder", String.valueOf(localJSONArray.optJSONObject(i)));
                        PandianAccountBean localPandianAccountBean = (PandianAccountBean) localGson.fromJson(String.valueOf(localJSONArray.optJSONObject(i)), PandianAccountBean.class);
                        PandianAccount.this.pandianAccountBeans.add(localPandianAccountBean);
                        i++;
                    }
                    PandianAccount.this.pandianAccountAdapter.notifyDataSetChanged();
                    return;
                }
                Toast.makeText(PandianAccount.this, paramJSONObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Activity声明周期，页面创建方法
     * 实例化所需工具，调用初始方法
     * @param paramBundle
     */
    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.pandian_account);
        ButterKnife.bind(this);
        pandianAccount = this;
        this.pandianAccountAdapter = new PandianAccountAdapter(this.pandianAccountBeans);
        this.pandianAccountList.setAdapter(this.pandianAccountAdapter);
        this.pandianAccountList.setOnItemClickListener(this);
        findAllPandianOrder();
    }

    /**
     * 列表子项点击发方法，进行页面跳转
     * 向下一页面传递所需要的参数
     * @param paramAdapterView
     * @param paramView
     * @param paramInt
     * @param paramLong
     */
    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
        String str = ((PandianAccountBean) this.pandianAccountBeans.get(paramInt)).getDticketno();
        Intent localIntent = new Intent(this, PandianAccountInfo.class);
        localIntent.putExtra("ticketno", str);
        startActivity(localIntent);
    }

    /**
     * 移除子项内容
     * 根据传入的参数从pandianAccountBeans删除对应项
     * 更新列表显示内容
     * @param paramString
     */
    public void removePandianAccountItem(String paramString) {
        for (int i = 0; i < this.pandianAccountBeans.size(); i++) {
            if (!((PandianAccountBean) this.pandianAccountBeans.get(i)).getDticketno().equals(paramString))
                continue;
            this.pandianAccountBeans.remove(i);
        }
        this.pandianAccountAdapter.notifyDataSetChanged();
    }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.pandian.PandianAccount
 * JD-Core Version:    0.6.0
 */