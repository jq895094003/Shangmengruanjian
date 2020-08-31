package com.smrj.shangmengruanjian.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.dateReport.ReportMain;
import com.smrj.shangmengruanjian.orderoperation.CardticketSale;
import com.smrj.shangmengruanjian.orderoperation.promotionorder.PromotionMain;
import com.smrj.shangmengruanjian.orderquery.EightAbnormalProduct;
import com.smrj.shangmengruanjian.orderquery.SaleMonitor;
import com.smrj.shangmengruanjian.orderquery.SearchSaleOrders;
import com.smrj.shangmengruanjian.orderquery.ShopSale;
import com.smrj.shangmengruanjian.orderquery.StockInfo;
import com.smrj.shangmengruanjian.orderoperation.AdjustmentOfPurchasePrice;
import com.smrj.shangmengruanjian.orderoperation.CheckBottom;
import com.smrj.shangmengruanjian.orderoperation.OrderGoods;
import com.smrj.shangmengruanjian.orderoperation.ReturnFactory;
import com.smrj.shangmengruanjian.orderquery.ThingSaleFirrstKind;
import com.smrj.shangmengruanjian.orderquery.VThing;
import com.smrj.shangmengruanjian.orderquery.WorkerSale;
import com.smrj.shangmengruanjian.pandian.PandianMain;
import com.smrj.shangmengruanjian.orderquery.DetailFragment;
import com.smrj.shangmengruanjian.productoperation.CreateProduct;
import com.smrj.shangmengruanjian.productoperation.UpdateProduct;
import com.smrj.shangmengruanjian.ticket.AccountItemSelect;
import com.smrj.shangmengruanjian.workeropration.WorkerAdd;
import com.smrj.shangmengruanjian.workeropration.WorkerJurisdiction;

import java.util.ArrayList;

public class MyExpandableAdapter extends BaseExpandableListAdapter {

    private ArrayList<String> groupList;
    private ArrayList<ArrayList<String>> itemList;
    private ArrayList<Drawable> groupPicList;
    private ArrayList<ArrayList<Drawable>> itemPicList;
    private Context context;
    private GridView gridView;

    public MyExpandableAdapter(ArrayList<String> groupList, ArrayList<ArrayList<String>> itemList, ArrayList<Drawable> groupPicList, ArrayList<ArrayList<Drawable>> itemPicList, Context context) {
        this.groupList = groupList;
        this.itemList = itemList;
        this.groupPicList = groupPicList;
        this.itemPicList = itemPicList;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return itemList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = View.inflate(context, R.layout.expandablelistview_group,null);
        }
        ImageView ivGroup = convertView.findViewById(R.id.iv_group);
        ImageView ivGroupRight = convertView.findViewById(R.id.iv_group_right);
        TextView tvGroup = convertView.findViewById(R.id.tv_group);
        if (isExpanded){
            ivGroup.setImageResource(R.mipmap.iv_open);
        }else {
            ivGroup.setImageResource(R.mipmap.iv_close);
        }
        tvGroup.setText(groupList.get(groupPosition));
        ivGroupRight.setImageDrawable(groupPicList.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = View.inflate(context, R.layout.expandablelist_item, null);
        }
        // 因为 convertView 的布局就是一个 GridView，
        // 所以可以向下转型为 GridView
        gridView = (GridView) convertView;
        // 创建 GridView 适配器
        MyGridViewAdapter myGridViewAdapter = new MyGridViewAdapter(context,itemList.get(groupPosition),itemPicList.get(groupPosition));
        gridView.setAdapter(myGridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = view.findViewById(R.id.tv_gridview);
                if (textView.getText().toString().equals("新建商品")){
                    Intent intent = new Intent(context, CreateProduct.class);
                    context.startActivity(intent);
                }
                if (textView.getText().toString().equals("修改商品")){
                    Intent intent = new Intent(context, UpdateProduct.class);
                    context.startActivity(intent);
                }
                if (textView.getText().toString().equals("进售价调整")){
                    Intent intent = new Intent(context, AdjustmentOfPurchasePrice.class);
                    context.startActivity(intent);
                }
                if (textView.getText().toString().equals("当前门店信息")){
                    Intent intent = new Intent(context, VThing.class);
                    context.startActivity(intent);
                }
                if (textView.getText().toString().equals("审核登账")){
                    Intent intent = new Intent(context, AccountItemSelect.class);
//                    Intent intent = new Intent(context, AdjustmentOfPurchasePrice.class);
                    context.startActivity(intent);
                }
                if (textView.getText().toString().equals("验收单制作")){
                    Intent intent = new Intent(context, CheckBottom.class);
                    context.startActivity(intent);
                }
                if (textView.getText().toString().equals("盘点单制作")){
                    Intent intent = new Intent(context, PandianMain.class);
                    context.startActivity(intent);
                }
                if (textView.getText().toString().equals("促销单制作")){
                    Intent intent = new Intent(context, PromotionMain.class);
                    context.startActivity(intent);
                }
                if (textView.getText().toString().equals("返厂单制作")){
                    Intent intent = new Intent(context, ReturnFactory.class);
                    context.startActivity(intent);
                }
                if (textView.getText().toString().equals("订货单制作")){
                    Intent intent = new Intent(context, OrderGoods.class);
                    context.startActivity(intent);
                }
                if (textView.getText().toString().equals("卡券营销")){
                    Intent intent = new Intent(context, CardticketSale.class);
                    context.startActivity(intent);
                }
                if (textView.getText().toString().equals("库存查询")){
                    Intent intent = new Intent(context, StockInfo.class);
                    context.startActivity(intent);
                }
                if (textView.getText().toString().equals("支付查询")){
                    Intent intent = new Intent(context, DetailFragment.class);
                    context.startActivity(intent);
                }


                if (textView.getText().toString().equals("整体收款")){
                    Intent intent = new Intent(context, ShopSale.class);
                    context.startActivity(intent);
                }
                if (textView.getText().toString().equals("员工收款")){
                    Intent intent = new Intent(context, WorkerSale.class);
                    context.startActivity(intent);
                }
                if (textView.getText().toString().equals("实时监控")){
                    Intent intent = new Intent(context, SaleMonitor.class);
                    context.startActivity(intent);
                }


                if (textView.getText().toString().equals("验收查询")){
                    Toast.makeText(context,textView.getText().toString(),Toast.LENGTH_SHORT).show();
                }
                if (textView.getText().toString().equals("新建商品查询")){
                    Toast.makeText(context,textView.getText().toString(),Toast.LENGTH_SHORT).show();
                }
                if (textView.getText().toString().equals("商品修改查询")){
                    Toast.makeText(context,textView.getText().toString(),Toast.LENGTH_SHORT).show();
                }
                if (textView.getText().toString().equals("营业统计查询")){
                    Intent intent = new Intent(context, ReportMain.class);
                    context.startActivity(intent);
                }
                if (textView.getText().toString().equals("毛利分析")){
                    Toast.makeText(context,textView.getText().toString(),Toast.LENGTH_SHORT).show();
                }
                if (textView.getText().toString().equals("销售同比")){
                    Intent intent = new Intent(context, ThingSaleFirrstKind.class);
                    context.startActivity(intent);
                }
                if (textView.getText().toString().equals("销售排行")){
                    Intent intent = new Intent(context, SearchSaleOrders.class);
                    context.startActivity(intent);
                }
                if (textView.getText().toString().equals("异常商品")){
                    Intent intent = new Intent(context, EightAbnormalProduct.class);
                    context.startActivity(intent);
                }
                if (textView.getText().toString().equals("添加员工")){
                    Intent intent = new Intent(context, WorkerAdd.class);
                    context.startActivity(intent);
                }
                if (textView.getText().toString().equals("员工权限")){
                    Intent intent = new Intent(context, WorkerJurisdiction.class);
                    context.startActivity(intent);
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
