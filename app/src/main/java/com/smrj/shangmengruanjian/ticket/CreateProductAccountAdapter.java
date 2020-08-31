package com.smrj.shangmengruanjian.ticket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.smrj.shangmengruanjian.R;

import java.util.ArrayList;


public class CreateProductAccountAdapter extends BaseAdapter {
  ArrayList<CreateProductAccountBean> createProductAccountBeans;

  public CreateProductAccountAdapter(ArrayList<CreateProductAccountBean> paramArrayList) {
    this.createProductAccountBeans = paramArrayList;
  }

  public int getCount()
  {
    return this.createProductAccountBeans.size();
  }

  public Object getItem(int paramInt)
  {
    return this.createProductAccountBeans.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    if (paramView == null){
      paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.create_product_account_adapter, paramViewGroup, false);
    }
    ((TextView)paramView.findViewById(R.id.allot_order_account_dticketno)).setText(((CreateProductAccountBean)this.createProductAccountBeans.get(paramInt)).getDticketno());
    ((TextView)paramView.findViewById(R.id.allot_order_account_dplace)).setText(((CreateProductAccountBean)this.createProductAccountBeans.get(paramInt)).getDplaceinput());
    ((TextView)paramView.findViewById(R.id.allot_order_account_dplaceinput)).setText(((CreateProductAccountBean)this.createProductAccountBeans.get(paramInt)).getDplace());
    return paramView;
  }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.util.CreateProductAccountAdapter
 * JD-Core Version:    0.6.0
 */