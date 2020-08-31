package com.smrj.shangmengruanjian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.GuestTypeBean;

import java.util.ArrayList;

public class GuestReportAdapter extends BaseAdapter {
  ArrayList<GuestTypeBean> guestTypeBeans;

  public GuestReportAdapter(ArrayList<GuestTypeBean> paramArrayList) {
    this.guestTypeBeans = paramArrayList;
  }

  public int getCount()
  {
    return this.guestTypeBeans.size();
  }

  public Object getItem(int paramInt)
  {
    return this.guestTypeBeans.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    if (paramView == null)
      paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.guest_report_adapter, paramViewGroup, false);
    TextView localTextView1 = (TextView)paramView.findViewById(R.id.guest_report_shopno);
    TextView localTextView2 = (TextView)paramView.findViewById(R.id.guest_report_ticketno);
    TextView localTextView3 = (TextView)paramView.findViewById(R.id.guest_report_dates);
    TextView localTextView4 = (TextView)paramView.findViewById(R.id.guest_report_pricecustomer);
    TextView localTextView5 = (TextView)paramView.findViewById(R.id.guest_report_water);
    localTextView1.setText(((GuestTypeBean)this.guestTypeBeans.get(paramInt)).getDSHOPNO());
    localTextView2.setText(((GuestTypeBean)this.guestTypeBeans.get(paramInt)).getDTICKETNO());
    localTextView3.setText(((GuestTypeBean)this.guestTypeBeans.get(paramInt)).getDDATE_S());
    localTextView4.setText(((GuestTypeBean)this.guestTypeBeans.get(paramInt)).getDPRICECUSTOMER());
    localTextView5.setText(((GuestTypeBean)this.guestTypeBeans.get(paramInt)).getDWATER());
    return paramView;
  }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.util.GuestReportAdapter
 * JD-Core Version:    0.6.0
 */