package com.smrj.shangmengruanjian.ticket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.smrj.shangmengruanjian.R;

import java.util.ArrayList;

public class AllotOrderAccountAdapter extends BaseAdapter {
  ArrayList<AllotOrderAccountBean> allotOrderAccounts;

  public AllotOrderAccountAdapter(ArrayList<AllotOrderAccountBean> paramArrayList) {
    this.allotOrderAccounts = paramArrayList;
  }

  public int getCount()
  {
    return this.allotOrderAccounts.size();
  }

  public Object getItem(int paramInt)
  {
    return this.allotOrderAccounts.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    if (paramView == null){
      paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.allot_order_account_adapter, paramViewGroup, false);
    }
    ((TextView)paramView.findViewById(R.id.allot_order_account_dticketno)).setText(((AllotOrderAccountBean)this.allotOrderAccounts.get(paramInt)).getDticketno());
    ((TextView)paramView.findViewById(R.id.allot_order_account_dplaceinput)).setText(((AllotOrderAccountBean)this.allotOrderAccounts.get(paramInt)).getDplaceinput());
    ((TextView)paramView.findViewById(R.id.allot_order_account_dplace)).setText(((AllotOrderAccountBean)this.allotOrderAccounts.get(paramInt)).getDplace());
    ((TextView)paramView.findViewById(R.id.allot_order_account_old)).setText(((AllotOrderAccountBean)this.allotOrderAccounts.get(paramInt)).getDdepotnoold());
    ((TextView)paramView.findViewById(R.id.allot_order_account_new)).setText(((AllotOrderAccountBean)this.allotOrderAccounts.get(paramInt)).getDdepotno());
    return paramView;
  }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.util.AllotOrderAccountAdapter
 * JD-Core Version:    0.6.0
 */