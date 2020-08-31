package com.smrj.shangmengruanjian.baseactivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.adapter.MyExpandableAdapter;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.util.MyUrl;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @BindView(R.id.main_expand)
    ExpandableListView expandableListView;//声明ExpandableListView控件，并使用ButterKnife工具绑定布局
    private ArrayList<String> groupList = new ArrayList<>();
    private ArrayList<String> shopSale = new ArrayList<>();
    private ArrayList<String> itemGridList = new ArrayList<>();
    private ArrayList<ArrayList<String>> itemList = new ArrayList<>();
    private ArrayList<Drawable> picGroupList = new ArrayList<>();
    private ArrayList<Drawable> picItemList = new ArrayList<>();
    private ArrayList<ArrayList<Drawable>> picList = new ArrayList<>();
    private long firstTime = 0L;
    private SharedPreferences sharedPreferences;

    //Activity生命周期
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//设置此页面使用得哪个布局文件
        ButterKnife.bind(this);//导入ButterKnife控件
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        groupList.add("商品操作");
        groupList.add("单据操作");
        groupList.add("单据查询");
//        groupList.add("员工管理");
//        groupList.add("营业统计");

        itemGridList = new ArrayList<>();
        itemGridList.add("新建商品");
        itemGridList.add("修改商品");
        itemGridList.add("进售价调整");
        itemGridList.add("当前门店信息");
        itemList.add(itemGridList);

        itemGridList = new ArrayList<>();
        itemGridList.add("验收单制作");
        itemGridList.add("盘点单制作");
//        itemGridList.add("促销单制作");    //功能不完整
        itemGridList.add("返厂单制作");
        itemGridList.add("订货单制作");
//        itemGridList.add("补货单制作");
        itemGridList.add("卡券营销");
        itemGridList.add("审核登账");
        itemList.add(itemGridList);

        itemGridList = new ArrayList<>();
        itemGridList.add("库存查询");
        itemGridList.add("支付查询");
//        itemGridList.add("验收查询");
//        itemGridList.add("新建商品查询");
//        itemGridList.add("商品修改查询");
        itemGridList.add("营业统计查询");
//        itemGridList.add("毛利分析");
        itemGridList.add("销售同比");
        itemGridList.add("销售排行");
        itemGridList.add("异常商品");
        itemGridList.add("整体收款");
        itemGridList.add("员工收款");
        itemGridList.add("实时监控");
        itemList.add(itemGridList);

        itemGridList = new ArrayList<>();
        itemGridList.add("添加员工");
        itemGridList.add("员工权限");
        itemList.add(itemGridList);

//        itemGridList = new ArrayList<>();
//        itemGridList.add("整体收款");
//        itemGridList.add("员工收款");
//        itemList.add(itemGridList);

        picGroupList.add(getResources().getDrawable(R.mipmap.group_product));
        picGroupList.add(getResources().getDrawable(R.mipmap.group_order));
        picGroupList.add(getResources().getDrawable(R.mipmap.group_report));
        picGroupList.add(getResources().getDrawable(R.mipmap.item_worker));

        picItemList = new ArrayList<>();
        picItemList.add(getResources().getDrawable(R.mipmap.item_produce_add));
        picItemList.add(getResources().getDrawable(R.mipmap.item_product_update));
        picItemList.add(getResources().getDrawable(R.mipmap.item_product_price));
        picItemList.add(getResources().getDrawable(R.mipmap.shangpinxinxi));
        picList.add(picItemList);

        picItemList = new ArrayList<>();
        picItemList.add(getResources().getDrawable(R.mipmap.item_order_check));
        picItemList.add(getResources().getDrawable(R.mipmap.item_order_pandian));
        picItemList.add(getResources().getDrawable(R.mipmap.item_order_promotion));
        picItemList.add(getResources().getDrawable(R.mipmap.item_order_returnfactory));
        picItemList.add(getResources().getDrawable(R.mipmap.item_order_goods));
        picItemList.add(getResources().getDrawable(R.mipmap.item_order_replenishment));
        picItemList.add(getResources().getDrawable(R.mipmap.ticket));
        picItemList.add(getResources().getDrawable(R.mipmap.order_card_ticket));
        picList.add(picItemList);

        picItemList = new ArrayList<>();
        picItemList.add(getResources().getDrawable(R.mipmap.item_report_stock));
        picItemList.add(getResources().getDrawable(R.mipmap.item_report_pay));
        picItemList.add(getResources().getDrawable(R.mipmap.item_report_check));
        picItemList.add(getResources().getDrawable(R.mipmap.item_report_product_add));
        picItemList.add(getResources().getDrawable(R.mipmap.item_report_product_update));
        picItemList.add(getResources().getDrawable(R.mipmap.item_business_report));
        picItemList.add(getResources().getDrawable(R.mipmap.item_order_gross_margin_analysis));
        picItemList.add(getResources().getDrawable(R.mipmap.item_report_circle));
        picItemList.add(getResources().getDrawable(R.mipmap.item_report_sales));
        picItemList.add(getResources().getDrawable(R.mipmap.item_report_eight_abnormal));
        picList.add(picItemList);

        picItemList = new ArrayList<>();
        picItemList.add(getResources().getDrawable(R.mipmap.item_worker_add));
        picItemList.add(getResources().getDrawable(R.mipmap.item_worker_jurisdiction));
        picList.add(picItemList);
        MyExpandableAdapter myExpandableAdapter = new MyExpandableAdapter(groupList, itemList, picGroupList, picList, MainActivity.this);
        expandableListView.setAdapter(myExpandableAdapter);
        expandableListView.setGroupIndicator(null);
        expandableListView.expandGroup(0);
    }


    private void exit() {
        RequestParams requestParams = new RequestParams();
        System.out.println("sharedPreferences.getString(\"SESSION\", \"\") = " + sharedPreferences.getString("SESSION", ""));
        requestParams.put("sessionid", sharedPreferences.getString("SESSION", ""));
        MyAsyncClient.doPost(new MyUrl().getExitMethod(), requestParams, new MyResponseHandler(MainActivity.this) {
            @Override
            public void success(JSONObject jsonObject) {
                MainActivity.this.finish();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 返回键间隔监听
     * 两秒内点击返回键退出App
     *
     * @param paramInt
     * @param paramKeyEvent
     * @return
     */
    @Override
    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
        long l = System.currentTimeMillis();
        if (paramInt == 4) {
            if (l - this.firstTime < 2000L) {
                exit();
            } else {
                Toast.makeText(getApplicationContext(), "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                this.firstTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(paramInt, paramKeyEvent);
    }

}
