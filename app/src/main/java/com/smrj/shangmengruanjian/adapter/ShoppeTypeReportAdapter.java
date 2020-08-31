package com.smrj.shangmengruanjian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.ShoppeTypeReportBean;

import java.util.ArrayList;

public class ShoppeTypeReportAdapter extends BaseAdapter {
  ArrayList<ShoppeTypeReportBean> shoppeTypeReportBeans;

  public ShoppeTypeReportAdapter(ArrayList<ShoppeTypeReportBean> paramArrayList) {
    this.shoppeTypeReportBeans = paramArrayList;
  }

  public int getCount()
  {
    return this.shoppeTypeReportBeans.size();
  }

  public Object getItem(int paramInt)
  {
    return this.shoppeTypeReportBeans.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null)
      paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.shoppe_reporter_adapter, paramViewGroup, false);
    TextView localTextView1 = (TextView)paramView.findViewById(R.id.shoppe_report_ticketno);
    TextView localTextView2 = (TextView)paramView.findViewById(R.id.shoppe_report_no);
    TextView localTextView3 = (TextView)paramView.findViewById(R.id.shoppe_report_name);
    TextView localTextView4 = (TextView)paramView.findViewById(R.id.shoppe_report_shopno);
    TextView localTextView5 = (TextView)paramView.findViewById(R.id.shoppe_report_dates);
    TextView localTextView6 = (TextView)paramView.findViewById(R.id.shoppe_report_moneyys);
    TextView localTextView7 = (TextView)paramView.findViewById(R.id.shoppe_report_moneyss);
    localTextView1.setText(((ShoppeTypeReportBean)this.shoppeTypeReportBeans.get(paramInt)).getDTICKETNO());
    localTextView2.setText(((ShoppeTypeReportBean)this.shoppeTypeReportBeans.get(paramInt)).getDAREANO());
    localTextView3.setText(((ShoppeTypeReportBean)this.shoppeTypeReportBeans.get(paramInt)).getDAREANAME());
    localTextView4.setText(((ShoppeTypeReportBean)this.shoppeTypeReportBeans.get(paramInt)).getDSHOPNO());
    localTextView5.setText(((ShoppeTypeReportBean)this.shoppeTypeReportBeans.get(paramInt)).getDDATE_S());
    localTextView6.setText(((ShoppeTypeReportBean)this.shoppeTypeReportBeans.get(paramInt)).getDMONEY_YS());
    localTextView7.setText(((ShoppeTypeReportBean)this.shoppeTypeReportBeans.get(paramInt)).getDMONEY_SS());
    return paramView;
  }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.util.ShoppeTypeReportAdapter
 * JD-Core Version:    0.6.0
 */