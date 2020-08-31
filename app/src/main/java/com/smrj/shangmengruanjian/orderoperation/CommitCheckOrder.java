package com.smrj.shangmengruanjian.orderoperation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.adapter.CheckOrderAdapter;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.bean.CheckOrderBean;
import com.smrj.shangmengruanjian.util.MyUrl;

import net.sourceforge.pinyin4j.PinyinHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CommitCheckOrder extends Fragment implements OnClickListener, OnItemClickListener, View.OnTouchListener {
    /**
     * 45-58行，声明各种页面控件，所需工具
     */
    CheckOrderAdapter checkOrderAdapter;
    ArrayList<CheckOrderBean> checkOrders = new ArrayList();

    @BindView(R.id.commit_check_order_btn)
    Button commitCheckOrderBtn;

    @BindView(R.id.commit_check_order_list)
    ListView commitCheckOrderListView;
    String dplace = "";
    LocalBroadcastManager localBroadcastManager;
    String providercode = "";
    Unbinder unbinder;
    String warehouse = "";
    SharedPreferences sharedPreferences;

    /**
     * 广播接收器
     * 接收广播发送者广播的参数
     * 更新本页面列表
     */
    //这里你没走  所以没有数据
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context paramContext, Intent paramIntent) {
            String str1 = paramIntent.getStringExtra("checkOrderBean");
            String str2 = str1.substring(14, str1.length());
            CommitCheckOrder.this.dplace = paramIntent.getStringExtra("dplace");
            CommitCheckOrder.this.warehouse = paramIntent.getStringExtra("dplace");
            try {
                JSONObject jsonObject = new JSONObject(str2);
                String dprovidercode = jsonObject.getString("dprovidercode");
                providercode = dprovidercode;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Gson localGson = new Gson();
            try {
                CheckOrderBean localCheckOrder =
                        (CheckOrderBean) localGson.fromJson(String.valueOf(new JSONObject(str2)), CheckOrderBean.class);
                //checkOrders 这个值为老数据 不是你新填写的数据 你看看之前的怎么写的 把我搞蒙了都 用广播接受数据
                checkOrders.add(localCheckOrder);
                checkOrderAdapter.notifyDataSetChanged();
            } catch (JSONException localJSONException) {
                localJSONException.printStackTrace();
            }
        }
    };

    /**
     * 提交订单
     * 如果返回值code为200则弹出添加成功提示，清空列表
     * 如果code不为200则弹出后台返回的提示信息
     */
    private void commitCheckOrder() {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("dplace", dplace);
        localRequestParams.put("dplaceinput", dplace);
        localRequestParams.put("dprovidercode", providercode);
        localRequestParams.put("json", jsonToString());
        localRequestParams.put("DSHOPNO", sharedPreferences.getString("shopno", ""));
        MyAsyncClient.doPost(new MyUrl().getInsOrderYS(), localRequestParams, new MyResponseHandler(getActivity()) {
            @Override
            public void success(JSONObject jsonObject) {
                if (jsonObject.optString("code").equals("200")) {
                    Toast.makeText(CommitCheckOrder.this.getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
                    checkOrders.clear();
                    checkOrderAdapter.notifyDataSetChanged();
                    return;
                }
                Toast.makeText(CommitCheckOrder.this.getActivity(), jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }


    /**
     * pinyin4j工具类
     *
     * @param paramChar
     * @param paramBoolean
     * @return
     */
    public static char[] getHeadByChar(char paramChar, boolean paramBoolean) {
        if (paramChar <= '')
            return new char[]{paramChar};
        String[] arrayOfString = PinyinHelper.toHanyuPinyinStringArray(paramChar);
        char[] arrayOfChar = new char[arrayOfString.length];
        int i = arrayOfString.length;
        int j = 0;
        int k = 0;
        while (j < i) {
            char c = arrayOfString[j].charAt(0);
            if (paramBoolean)
                arrayOfChar[k] = Character.toUpperCase(c);
            else
                arrayOfChar[k] = c;
            k++;
            j++;
        }
        return arrayOfChar;
    }

    /**
     * pinyin4j工具类
     */
    public static String[] getHeadByString(String paramString) {
        return getHeadByString(paramString, true);
    }

    /**
     * pinyin4j工具类
     */
    public static String[] getHeadByString(String paramString, boolean paramBoolean) {
        return getHeadByString(paramString, paramBoolean, null);
    }

    /**
     * pinyin4j工具类
     */
    public static String[] getHeadByString(String paramString1, boolean paramBoolean, String paramString2) {
        char[] arrayOfChar1 = paramString1.toCharArray();
        String[] arrayOfString = new String[arrayOfChar1.length];
        int i = arrayOfChar1.length;
        int j = 0;
        int k = 0;
        while (j < i) {
            char[] arrayOfChar2 = getHeadByChar(arrayOfChar1[j], paramBoolean);
            StringBuffer localStringBuffer = new StringBuffer();
            if (paramString2 != null) {
                int m = arrayOfChar2.length;
                int n = 0;
                int i1 = 1;
                while (n < m) {
                    localStringBuffer.append(arrayOfChar2[n]);
                    if (i1 != arrayOfChar2.length)
                        localStringBuffer.append(paramString2);
                    i1++;
                    n++;
                }
            }
            localStringBuffer.append(arrayOfChar2[0]);
            arrayOfString[k] = localStringBuffer.toString();
            k++;
            j++;
        }
        return arrayOfString;
    }

    //将chekOrders数组转换成JSONArray
    private String jsonToString() {
        JSONArray localJSONArray = new JSONArray();
        for (int i = 0; i < this.checkOrders.size(); i++) {
            JSONObject localJSONObject = new JSONObject();
            try {
                localJSONObject.put("barcode", ((CheckOrderBean) this.checkOrders.get(i)).getDbarcode());
                localJSONObject.put("num", this.checkOrders.get(i).getDnum());
                localJSONObject.put("dareacode", this.warehouse);
                localJSONObject.put("dspec", ((CheckOrderBean) this.checkOrders.get(i)).getDspec());
                localJSONObject.put("priceIn", ((CheckOrderBean) this.checkOrders.get(i)).getDpricein());
                //提交时候需要提交输入框的值，不是原值，需要提交改变后的值


                localJSONArray.put(localJSONObject);
            } catch (JSONException localJSONException) {
                localJSONException.printStackTrace();
            }
        }
        System.out.println(localJSONArray);
        return String.valueOf(localJSONArray);
    }


    /**
     * Fragment声明周期
     * 页面创建方法
     * 实例化所需工具，监听
     *
     * @param paramBundle
     */
    @Override
    public void onActivityCreated(@Nullable Bundle paramBundle) {
        super.onActivityCreated(paramBundle);
        sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        this.commitCheckOrderBtn.setOnClickListener(this);
        this.commitCheckOrderBtn.setOnTouchListener(this);
        this.checkOrderAdapter = new CheckOrderAdapter(this.checkOrders);
        this.commitCheckOrderListView.setAdapter(this.checkOrderAdapter);
        this.commitCheckOrderListView.setOnItemClickListener(this);
        this.localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("android.intent.action.CART_BROADCAST");
        this.localBroadcastManager.registerReceiver(this.broadcastReceiver, localIntentFilter);
        String[] arrayOfString = getHeadByString("今晚又要通宵了");
        String str = "";
        for (int i = 0; i < arrayOfString.length; i++) {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append(str);
            localStringBuilder.append(arrayOfString[i]);
            str = localStringBuilder.toString();
        }
        Log.i("headArray", str);
    }

    /**
     * 页面控件单击事件监听
     *
     * @param paramView
     */
    @Override
    public void onClick(View paramView) {

        if (this.commitCheckOrderListView.getChildCount() > 0) {
            for (int i = 0; i < this.commitCheckOrderListView.getChildCount(); i++) {
                //获取输入框进价
                EditText items = this.commitCheckOrderListView.getChildAt(i).findViewById(R.id.check_order_dpricein);
                String pricein = items.getText().toString();
                //获取输入框验收数量
                EditText check_order_dnum = this.commitCheckOrderListView.getChildAt(i).findViewById(R.id.check_order_dnum);
                String dnum = check_order_dnum.getText().toString();
                checkOrders.get(i).setDpricein(pricein);
                checkOrders.get(i).setDnum(dnum);
            }
        }

        if (paramView.getId() == R.id.commit_check_order_btn) {
            if (this.checkOrderAdapter.getCount() == 0) {
                Toast.makeText(getActivity(), "请先添加商品", Toast.LENGTH_SHORT).show();
            } else {
                commitCheckOrder();
            }
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

    /**
     * Fragment生命周期，页面加载方法
     *
     * @param paramLayoutInflater
     * @param paramViewGroup
     * @param paramBundle
     * @return
     */
    @Nullable
    public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        View localView = paramLayoutInflater.inflate(R.layout.commit_check_order, paramViewGroup, false);
        this.unbinder = ButterKnife.bind(this, localView);
        getArguments();
        return localView;
    }

    /**
     * 列表控件子项单击事件监听
     * 进行页面跳转，并向下一页面传递所需参数
     *
     * @param paramAdapterView
     * @param paramView
     * @param paramInt
     * @param paramLong
     */
    @Override
    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
        CheckOrderBean localCheckOrder = (CheckOrderBean) this.checkOrders.get(paramInt);
        System.out.println(localCheckOrder.toString());
        Intent localIntent = new Intent(getActivity(), CheckOrderInfo.class);
        localIntent.putExtra("checkOrderInfo", localCheckOrder);
        startActivity(localIntent);
    }

    /**
     * 页面再次被调起方法
     * 刷新列表控件
     */
    @Override
    public void onResume() {
        Intent localIntent = getActivity().getIntent();
        int i = 0;
        if (localIntent.getIntExtra("id", 0) == 2) {
            String str = getActivity().getIntent().getStringExtra("checkInfoDelete");
            while (i < this.checkOrders.size()) {
                if (((CheckOrderBean) this.checkOrders.get(i)).getDbarcode().equals(str))
                    this.checkOrders.remove(i);
                i++;
            }
            this.checkOrderAdapter.notifyDataSetChanged();
        }
        super.onResume();
    }

//    @OnClick({R.id.checkbox, R.id.commit_check_order_btn})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.checkbox:
//                for (int i = 1; i <= this.checkOrderAdapter.getCount(); i++) {
//                    Object a = this.checkOrderAdapter.getItem(i);
//                }
//                mAdapter.notifyDataSetChanged();
//                break;
//            case R.id.btnselect:
//                StringBuffer sb = new StringBuffer();
//                //获取选中的数据
//                for (int i = 0; i < mList.size(); i++) {
//                    if (mList.get(i).isCheck()) {
//                        sb.append(mList.get(i).toString());
//                    }
//                }
//                Toast.makeText(CommitCheckOrder.this, "选中的值是" + sb.toString(), Toast.LENGTH_LONG).show();
//                break;
//        }
//    }
}

