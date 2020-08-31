package com.smrj.shangmengruanjian.ticket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.smrj.shangmengruanjian.R;

import java.util.ArrayList;

public class AllotOrderAccountInfoAdapter extends BaseAdapter {
  ArrayList<AllotOrderAccountInfoBean> allotOrderAccountInfoBeans;

  public AllotOrderAccountInfoAdapter(ArrayList<AllotOrderAccountInfoBean> paramArrayList) {
    this.allotOrderAccountInfoBeans = paramArrayList;
  }

  public int getCount()
  {
    return this.allotOrderAccountInfoBeans.size();
  }

  public Object getItem(int paramInt)
  {
    return this.allotOrderAccountInfoBeans.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    if (paramView == null)
      paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.allot_order_account_info_adapter, paramViewGroup, false);
    ((TextView)paramView.findViewById(R.id.allot_order_account_info_dname)).setText(((AllotOrderAccountInfoBean)this.allotOrderAccountInfoBeans.get(paramInt)).getDname());
    ((TextView)paramView.findViewById(R.id.allot_order_account_info_dbarcode)).setText(((AllotOrderAccountInfoBean)this.allotOrderAccountInfoBeans.get(paramInt)).getDbarcode());
    ((TextView)paramView.findViewById(R.id.allot_order_account_info_dnum)).setText(((AllotOrderAccountInfoBean)this.allotOrderAccountInfoBeans.get(paramInt)).getDnum());
    ((TextView)paramView.findViewById(R.id.allot_order_account_info_dunitname)).setText(((AllotOrderAccountInfoBean)this.allotOrderAccountInfoBeans.get(paramInt)).getDunitname());
    return paramView;
  }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.util.AllotOrderAccountInfoAdapter
 * JD-Core Version:    0.6.0
 */