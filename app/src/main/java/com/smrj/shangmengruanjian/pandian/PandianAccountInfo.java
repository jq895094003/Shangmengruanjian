package com.smrj.shangmengruanjian.pandian;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.adapter.PandianAccountInfoAdapter;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.bean.PandianAccountInfoBean;
import com.smrj.shangmengruanjian.util.JsonToString;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class PandianAccountInfo extends AppCompatActivity implements OnClickListener {
    /**
     * 44-55行，声明并实例化页面工具，控件、使用ButterKnife进行绑定
     */
    PandianAccountInfoAdapter pandianAccountInfoAdapter;
    ArrayList<PandianAccountInfoBean> pandianAccountInfoBeans = new ArrayList();

    @BindView(R.id.pandian_account_info_commit)
    Button pandianAccountInfoCommit;

    @BindView(R.id.pandian_account_info_delete)
    Button pandianAccountInfoDelete;

    @BindView(R.id.pandian_account_info_list)
    ListView pandianAccountInfoList;
    String ticketNo = "";

    /**
     * 提交盘点信息单据
     * 如果返回值code为200则结束当前页面，调用上级页面列表子项删除方法
     * 如果返回值code不为200则弹出后台提示信息
     * @param paramString
     */
    private void commitPandianAccountInfo(String paramString) {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("dticketno", paramString);
        MyAsyncClient.doPost("OrderPCDZ.action", localRequestParams, new MyResponseHandler(PandianAccountInfo.this) {
            @Override
            public void success(JSONObject jsonObject) {
                if (jsonObject.optString("code").equals("200")) {
                    Toast.makeText(PandianAccountInfo.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                    PandianAccount.pandianAccount.removePandianAccountItem(PandianAccountInfo.this.ticketNo);
                    PandianAccountInfo.this.finish();
                    return;
                }
                Toast.makeText(PandianAccountInfo.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 删除盘点信息单据
     * 如果返回值code为200则调用上级页面列表子项移除接口
     * 如果返回值code不为200则弹出后台提示信息
     */
    private void deletePandianAccountInfo() {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("dticketno", this.ticketNo);
        MyAsyncClient.doPost("delOrderPC.action", localRequestParams, new MyResponseHandler(PandianAccountInfo.this) {
            @Override
            public void success(JSONObject jsonObject) {
                if (jsonObject.optString("code").equals("200")) {
                    Toast.makeText(PandianAccountInfo.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                    PandianAccount.pandianAccount.removePandianAccountItem(PandianAccountInfo.this.ticketNo);
                    PandianAccountInfo.this.finish();
                    return;
                }
                Toast.makeText(PandianAccountInfo.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 查询所有盘点信息接口
     * 如果返回值code为200则将数据加载到列表控件中
     * 如果不为200则弹出后台提示信息
     * @param paramString
     */
    private void findPandianAccountInfo(String paramString) {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("dticketno", paramString);
        MyAsyncClient.doPost("findOrderPCMX.action", localRequestParams, new MyResponseHandler(PandianAccountInfo.this) {
            @Override
            public void success(JSONObject jsonObject) {
                boolean bool = jsonObject.optString("code").equals("200");
                int i = 0;
                if (bool) {
                    JSONArray localJSONArray = jsonObject.optJSONArray("data");
                    Gson localGson = new Gson();
                    while (i < localJSONArray.length()) {
                        PandianAccountInfoBean localPandianAccountInfoBean = (PandianAccountInfoBean) localGson.fromJson(String.valueOf(localJSONArray.optJSONObject(i)), PandianAccountInfoBean.class);
                        PandianAccountInfo.this.pandianAccountInfoBeans.add(localPandianAccountInfoBean);
                        i++;
                    }
                    PandianAccountInfo.this.pandianAccountInfoAdapter.notifyDataSetChanged();
                    return;
                }
                Toast.makeText(PandianAccountInfo.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 保存盘点信息接口
     * 如果返回值code为200则调用调教盘点信息接口
     * 如果返回值code不为200则弹出后台提示信息
     * @param paramArrayList
     */
    private void savePandianAccountInfo(ArrayList<PandianAccountInfoBean> paramArrayList) {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("json", new JsonToString().pandianInfoJsonToString(paramArrayList));
        MyAsyncClient.doPost("upOrderPCNUM.action", localRequestParams, new MyResponseHandler(PandianAccountInfo.this) {
            @Override
            public void success(JSONObject jsonObject) {
                if (jsonObject.optString("code").equals("200")) {
                    PandianAccountInfo localPandianAccountInfo = PandianAccountInfo.this;
                    localPandianAccountInfo.commitPandianAccountInfo(localPandianAccountInfo.ticketNo);
                    return;
                }
                Toast.makeText(PandianAccountInfo.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 页面控件点击接口
     * 获取列表子项控件，判断是否为空
     * 调用保存接口
     * @param paramView
     */
    @Override
    public void onClick(View paramView) {
        switch (paramView.getId()) {
            case R.id.pandian_account_info_delete:
                deletePandianAccountInfo();
                return;
            case R.id.pandian_account_info_commit:
                ArrayList localArrayList = new ArrayList();
                for (int i = 0; i < this.pandianAccountInfoList.getCount(); i++) {
                    int j = i - this.pandianAccountInfoList.getFirstVisiblePosition();
                    if ((j < 0) || (j >= this.pandianAccountInfoList.getChildCount())){

                    }else {
                        LinearLayout localLinearLayout = (LinearLayout) pandianAccountInfoList.getChildAt(j);
                        EditText localEditText = (EditText) localLinearLayout.findViewById(R.id.pandian_account_info_adapter_dnum);
                        TextView localTextView = (TextView) localLinearLayout.findViewById(R.id.pandian_account_info_adapter_barcode);
                        if ((localEditText.getText().toString().trim().equals("")) || (localEditText.getText().toString().trim().equals(null))) {

                        } else {
                            PandianAccountInfoBean localPandianAccountInfoBean = new PandianAccountInfoBean();
                            localPandianAccountInfoBean.setDticketno(this.ticketNo);
                            localPandianAccountInfoBean.setDbarcode(localTextView.getText().toString());
                            localPandianAccountInfoBean.setDnum(localEditText.getText().toString());
                            localArrayList.add(localPandianAccountInfoBean);
                        }
                    }
                }
                savePandianAccountInfo(localArrayList);
                return;
        }

    }

    /**
     * Activity声明周期，页面创建方法
     * 实例化所有工具
     * 设置监听
     * @param paramBundle
     */
    @Override
    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.pandian_account_info);
        ButterKnife.bind(this);
        this.pandianAccountInfoAdapter = new PandianAccountInfoAdapter(this.pandianAccountInfoBeans);
        this.pandianAccountInfoList.setAdapter(this.pandianAccountInfoAdapter);
        this.pandianAccountInfoCommit.setOnClickListener(this);
        this.pandianAccountInfoDelete.setOnClickListener(this);
        this.ticketNo = getIntent().getStringExtra("ticketno");
        findPandianAccountInfo(this.ticketNo);
    }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.pandian.PandianAccountInfo
 * JD-Core Version:    0.6.0
 */