package com.smrj.shangmengruanjian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.PurchaseBean;

import java.util.ArrayList;

public class PurchaseAdapter extends BaseAdapter {
  private ArrayList<PurchaseBean> purchases;

  public PurchaseAdapter(ArrayList<PurchaseBean> paramArrayList) {
    this.purchases = paramArrayList;
  }

  public int getCount()
  {
    return this.purchases.size();
  }

  public Object getItem(int paramInt)
  {
    return this.purchases.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    if (paramView == null){
      paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.adjustment_recyclerview_list, paramViewGroup, false);
    }
    ((TextView)paramView.findViewById(R.id.purchase_dmasterbarcode)).setText(((PurchaseBean)this.purchases.get(paramInt)).getDmasterbarcode());
    ((TextView)paramView.findViewById(R.id.purchase_dname)).setText(((PurchaseBean)this.purchases.get(paramInt)).getDname());
    ((TextView)paramView.findViewById(R.id.purchase_dthingtype)).setText(((PurchaseBean)this.purchases.get(paramInt)).getDthingtype());
    ((TextView)paramView.findViewById(R.id.purchase_dshopname)).setText(((PurchaseBean)this.purchases.get(paramInt)).getDshopname());
    ((TextView)paramView.findViewById(R.id.purchase_dpricein)).setText(((PurchaseBean)this.purchases.get(paramInt)).getDpricein());
    return paramView;
  }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.util.PurchaseAdapter
 * JD-Core Version:    0.6.0
 */