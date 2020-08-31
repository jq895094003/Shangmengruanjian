package com.smrj.shangmengruanjian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.ProductTypeReportBean;

import java.util.ArrayList;

public class ProductTypeReportAdapter extends BaseAdapter {
  ArrayList<ProductTypeReportBean> productTypeReportBeans;

  public ProductTypeReportAdapter(ArrayList<ProductTypeReportBean> paramArrayList) {
    this.productTypeReportBeans = paramArrayList;
  }

  public int getCount()
  {
    return this.productTypeReportBeans.size();
  }

  public Object getItem(int paramInt)
  {
    return this.productTypeReportBeans.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    if (paramView == null)
      paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.product_report_adaper, paramViewGroup, false);
    TextView localTextView1 = (TextView)paramView.findViewById(R.id.product_report_ticketno);
    TextView localTextView2 = (TextView)paramView.findViewById(R.id.product_report_moneyys);
    TextView localTextView3 = (TextView)paramView.findViewById(R.id.product_report_name);
    TextView localTextView4 = (TextView)paramView.findViewById(R.id.product_report_shopno);
    TextView localTextView5 = (TextView)paramView.findViewById(R.id.product_report_moneyss);
    TextView localTextView6 = (TextView)paramView.findViewById(R.id.product_report_kindno);
    TextView localTextView7 = (TextView)paramView.findViewById(R.id.product_report_dates);
    localTextView1.setText(((ProductTypeReportBean)this.productTypeReportBeans.get(paramInt)).getDTICKETNO());
    localTextView2.setText(((ProductTypeReportBean)this.productTypeReportBeans.get(paramInt)).getDMONEY_YS());
    localTextView3.setText(((ProductTypeReportBean)this.productTypeReportBeans.get(paramInt)).getDKINDNAME());
    localTextView4.setText(((ProductTypeReportBean)this.productTypeReportBeans.get(paramInt)).getDSHOPNO());
    localTextView5.setText(((ProductTypeReportBean)this.productTypeReportBeans.get(paramInt)).getDMONEY_SS());
    localTextView6.setText(((ProductTypeReportBean)this.productTypeReportBeans.get(paramInt)).getDKINDNO());
    localTextView7.setText(((ProductTypeReportBean)this.productTypeReportBeans.get(paramInt)).getDDATE_S());
    return paramView;
  }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.util.ProductTypeReportAdapter
 * JD-Core Version:    0.6.0
 */