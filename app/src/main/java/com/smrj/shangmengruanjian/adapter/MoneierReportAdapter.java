package com.smrj.shangmengruanjian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.MoneierTypeReportBean;

import java.util.ArrayList;

public class MoneierReportAdapter extends BaseAdapter {
  ArrayList<MoneierTypeReportBean> moneierTypeReportBeans;

  public MoneierReportAdapter(ArrayList<MoneierTypeReportBean> paramArrayList) {
    this.moneierTypeReportBeans = paramArrayList;
  }

  public int getCount()
  {
    return this.moneierTypeReportBeans.size();
  }

  public Object getItem(int paramInt)
  {
    return this.moneierTypeReportBeans.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    if (paramView == null)
      paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.moneier_report_adapter, paramViewGroup, false);
    TextView localTextView1 = (TextView)paramView.findViewById(R.id.monier_report_woker);
    TextView localTextView2 = (TextView)paramView.findViewById(R.id.moneier_report_ticketno);
    TextView localTextView3 = (TextView)paramView.findViewById(R.id.monier_report_dates);
    TextView localTextView4 = (TextView)paramView.findViewById(R.id.monier_report_moneyys);
    TextView localTextView5 = (TextView)paramView.findViewById(R.id.monier_report_moneyss);
    localTextView1.setText(((MoneierTypeReportBean)this.moneierTypeReportBeans.get(paramInt)).getDWORKERNAME());
    localTextView2.setText(((MoneierTypeReportBean)this.moneierTypeReportBeans.get(paramInt)).getDTICKETNO());
    localTextView3.setText(((MoneierTypeReportBean)this.moneierTypeReportBeans.get(paramInt)).getDDATE_S());
    localTextView4.setText(((MoneierTypeReportBean)this.moneierTypeReportBeans.get(paramInt)).getDMONEY_YS());
    localTextView5.setText(((MoneierTypeReportBean)this.moneierTypeReportBeans.get(paramInt)).getDMONEY_SS());
    return paramView;
  }
}