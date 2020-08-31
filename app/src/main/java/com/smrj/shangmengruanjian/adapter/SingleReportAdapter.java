package com.smrj.shangmengruanjian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.SingleTypeReportBean;

import java.util.ArrayList;

public class SingleReportAdapter extends BaseAdapter {
  ArrayList<SingleTypeReportBean> singleTypeReportBeans;

  public SingleReportAdapter(ArrayList<SingleTypeReportBean> paramArrayList) {
    this.singleTypeReportBeans = paramArrayList;
  }

  public int getCount()
  {
    return this.singleTypeReportBeans.size();
  }

  public Object getItem(int paramInt)
  {
    return this.singleTypeReportBeans.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    if (paramView == null)
      paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.single_type_report_adapter, paramViewGroup, false);
    TextView localTextView1 = (TextView)paramView.findViewById(R.id.single_type_report_name);
    TextView localTextView2 = (TextView)paramView.findViewById(R.id.single_type_report_barcode);
    TextView localTextView3 = (TextView)paramView.findViewById(R.id.single_type_report_num);
    TextView localTextView4 = (TextView)paramView.findViewById(R.id.single_type_report_unitname);
    TextView localTextView5 = (TextView)paramView.findViewById(R.id.single_type_report_kindname);
    TextView localTextView6 = (TextView)paramView.findViewById(R.id.single_type_report_moneyys);
    TextView localTextView7 = (TextView)paramView.findViewById(R.id.single_type_report_moneyss);
    TextView localTextView8 = (TextView)paramView.findViewById(R.id.DDATE_S);
    localTextView1.setText(((SingleTypeReportBean)this.singleTypeReportBeans.get(paramInt)).getDNAME());
    localTextView2.setText(((SingleTypeReportBean)this.singleTypeReportBeans.get(paramInt)).getDBARCODE());
    localTextView3.setText(((SingleTypeReportBean)this.singleTypeReportBeans.get(paramInt)).getDNUM());
    localTextView4.setText(((SingleTypeReportBean)this.singleTypeReportBeans.get(paramInt)).getDUNITNAME());
    localTextView5.setText(((SingleTypeReportBean)this.singleTypeReportBeans.get(paramInt)).getDSHOPNO());
    localTextView6.setText(((SingleTypeReportBean)this.singleTypeReportBeans.get(paramInt)).getDMONEY_YS());
    localTextView7.setText(((SingleTypeReportBean)this.singleTypeReportBeans.get(paramInt)).getDMONEY_SS());
    localTextView8.setText(((SingleTypeReportBean)this.singleTypeReportBeans.get(paramInt)).getDDATE_S());
    return paramView;
  }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.util.SingleReportAdapter
 * JD-Core Version:    0.6.0
 */