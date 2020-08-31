package com.smrj.shangmengruanjian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.CheckOrderAccountInfoBean;

import java.util.ArrayList;

public class CheckOrderAccountInfoAdapter extends BaseAdapter {
  private ArrayList<CheckOrderAccountInfoBean> checkOrderAccountInfoBeans;

  public CheckOrderAccountInfoAdapter(ArrayList<CheckOrderAccountInfoBean> paramArrayList)
  {
    this.checkOrderAccountInfoBeans = paramArrayList;
  }

  public int getCount()
  {
    return this.checkOrderAccountInfoBeans.size();
  }

  public Object getItem(int paramInt)
  {
    return this.checkOrderAccountInfoBeans.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    if (paramView == null){
      paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.check_order_account_info_adapter, paramViewGroup, false);
    }
    ((TextView)paramView.findViewById(R.id.purchase_dmasterbarcode)).setText(((CheckOrderAccountInfoBean)this.checkOrderAccountInfoBeans.get(paramInt)).getDbarcode());
    ((TextView)paramView.findViewById(R.id.purchase_dname)).setText(((CheckOrderAccountInfoBean)this.checkOrderAccountInfoBeans.get(paramInt)).getDname());
    ((TextView)paramView.findViewById(R.id.purchase_dthingtype)).setText(((CheckOrderAccountInfoBean)this.checkOrderAccountInfoBeans.get(paramInt)).getDprovidercode());
    ((TextView)paramView.findViewById(R.id.purchase_dshopname)).setText(((CheckOrderAccountInfoBean)this.checkOrderAccountInfoBeans.get(paramInt)).getDnum());
    ((TextView)paramView.findViewById(R.id.purchase_dpricein)).setText(((CheckOrderAccountInfoBean)this.checkOrderAccountInfoBeans.get(paramInt)).getDpricein());
    return paramView;
  }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.util.CheckOrderAccountInfoAdapter
 * JD-Core Version:    0.6.0
 */