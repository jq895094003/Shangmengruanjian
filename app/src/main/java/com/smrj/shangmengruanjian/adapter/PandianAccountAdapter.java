package com.smrj.shangmengruanjian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.PandianAccountBean;

import java.util.ArrayList;

public class PandianAccountAdapter extends BaseAdapter {
  ArrayList<PandianAccountBean> pandianAccountBeans;

  public PandianAccountAdapter(ArrayList<PandianAccountBean> paramArrayList)
  {
    this.pandianAccountBeans = paramArrayList;
  }

  public int getCount()
  {
    return this.pandianAccountBeans.size();
  }

  public Object getItem(int paramInt)
  {
    return this.pandianAccountBeans.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null)
      paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.pandian_account_adapter, paramViewGroup, false);
    ((TextView)paramView.findViewById(R.id.pandian_account_adapter_ticketno)).setText(((PandianAccountBean)this.pandianAccountBeans.get(paramInt)).getDticketno());
    ((TextView)paramView.findViewById(R.id.pandian_account_adapter_dplace)).setText(((PandianAccountBean)this.pandianAccountBeans.get(paramInt)).getDplace());
    ((TextView)paramView.findViewById(R.id.pandian_account_adapter_dplaceinput)).setText(((PandianAccountBean)this.pandianAccountBeans.get(paramInt)).getDplaceinput());
    ((TextView)paramView.findViewById(R.id.pandian_account_adapter_dsetdate)).setText(((PandianAccountBean)this.pandianAccountBeans.get(paramInt)).getDsetdate());
    return paramView;
  }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.util.PandianAccountAdapter
 * JD-Core Version:    0.6.0
 */