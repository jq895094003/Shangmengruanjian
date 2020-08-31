package com.smrj.shangmengruanjian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;


import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.CheckOrderBean;

import java.util.ArrayList;

public class CheckOrderAdapter extends BaseAdapter {
  private ArrayList<CheckOrderBean> checkOrders;

  public CheckOrderAdapter(ArrayList<CheckOrderBean> paramArrayList) {
    this.checkOrders = paramArrayList;
  }

  public int getCount()
  {
    return this.checkOrders.size();
  }

  public Object getItem(int paramInt)
  {
    return this.checkOrders.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    if (paramView == null){
      paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.check_order_adapter, paramViewGroup, false);
    }
    ((TextView)paramView.findViewById(R.id.check_order_barcode)).setText(((CheckOrderBean)this.checkOrders.get(paramInt)).getDbarcode());
    ((TextView)paramView.findViewById(R.id.check_order_dname)).setText(((CheckOrderBean)this.checkOrders.get(paramInt)).getDname());
    ((TextView)paramView.findViewById(R.id.check_order_dthingtype)).setText(((CheckOrderBean)this.checkOrders.get(paramInt)).getDthingtype());
    ((TextView)paramView.findViewById(R.id.check_order_dshopname)).setText(((CheckOrderBean)this.checkOrders.get(paramInt)).getDshopname());
//    ((TextView)paramView.findViewById(R.id.check_order_dpricein)).setText(((CheckOrderBean)this.checkOrders.get(paramInt)).getDpricein());

    ((EditText) paramView.findViewById(R.id.check_order_dpricein)).setText(this.checkOrders.get(paramInt).getDpricein());
    ((EditText) paramView.findViewById(R.id.check_order_dnum)).setText(this.checkOrders.get(paramInt).getDnum());

    return paramView;
  }
}