package com.smrj.shangmengruanjian.ticket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smrj.shangmengruanjian.R;

import java.util.ArrayList;

public class CheckOrderAccountAdapter extends BaseAdapter {
  ArrayList<CheckOrderAccountBean> checkOrderAccountBeans;

  public CheckOrderAccountAdapter(ArrayList<CheckOrderAccountBean> paramArrayList) {
    this.checkOrderAccountBeans = paramArrayList;
  }

  public int getCount()
  {
    return this.checkOrderAccountBeans.size();
  }

  public Object getItem(int paramInt)
  {
    return this.checkOrderAccountBeans.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    if (paramView == null){
      paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.check_order_account_adapter, paramViewGroup, false);
    }
    ((TextView)paramView.findViewById(R.id.check_order_account_dticketno)).setText(((CheckOrderAccountBean)this.checkOrderAccountBeans.get(paramInt)).getDticketno());
    ((TextView)paramView.findViewById(R.id.check_order_account_dplaceinput)).setText(((CheckOrderAccountBean)this.checkOrderAccountBeans.get(paramInt)).getDplaceinput());
    ((TextView)paramView.findViewById(R.id.check_order_account_dplace)).setText(((CheckOrderAccountBean)this.checkOrderAccountBeans.get(paramInt)).getDplace());
    ((TextView)paramView.findViewById(R.id.check_order_account_ddepotno)).setText(((CheckOrderAccountBean)this.checkOrderAccountBeans.get(paramInt)).getDdepotno());
    ((TextView)paramView.findViewById(R.id.check_order_account_dprovidercode)).setText(((CheckOrderAccountBean)this.checkOrderAccountBeans.get(paramInt)).getDprovidercode());
    return paramView;
  }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.util.CheckOrderAccountAdapter
 * JD-Core Version:    0.6.0
 */