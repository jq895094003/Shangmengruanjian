package com.smrj.shangmengruanjian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.RealTypeReportBean;

import java.util.ArrayList;

public class RealTypeReportAdapter extends BaseAdapter {
    ArrayList<RealTypeReportBean> realTypeReportBeans;

    public RealTypeReportAdapter(ArrayList<RealTypeReportBean> paramArrayList) {
        this.realTypeReportBeans = paramArrayList;
    }

    public int getCount() {
        return this.realTypeReportBeans.size();
    }

    public Object getItem(int paramInt) {
        return this.realTypeReportBeans.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        if (paramView == null)
            paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.real_report_adapter, paramViewGroup, false);
        TextView localTextView1 = (TextView) paramView.findViewById(R.id.real_report_name);
        TextView localTextView2 = (TextView) paramView.findViewById(R.id.real_report_barcode);
        TextView localTextView3 = (TextView) paramView.findViewById(R.id.real_report_warehouse);
        TextView localTextView4 = (TextView) paramView.findViewById(R.id.real_report_num);
        TextView localTextView5 = (TextView) paramView.findViewById(R.id.real_report_kindname);
        TextView localTextView6 = (TextView) paramView.findViewById(R.id.real_report_providername);
        TextView localTextView7 = (TextView) paramView.findViewById(R.id.real_report_shopno);
        localTextView1.setText(((RealTypeReportBean) this.realTypeReportBeans.get(paramInt)).getDNAME());
        localTextView2.setText(((RealTypeReportBean) this.realTypeReportBeans.get(paramInt)).getDBARCODE());
        localTextView3.setText(((RealTypeReportBean) this.realTypeReportBeans.get(paramInt)).getDDEPOTNO());
        localTextView4.setText(((RealTypeReportBean) this.realTypeReportBeans.get(paramInt)).getDNUM());
        localTextView5.setText(((RealTypeReportBean) this.realTypeReportBeans.get(paramInt)).getDKINDNAME());
        localTextView6.setText(((RealTypeReportBean) this.realTypeReportBeans.get(paramInt)).getDPROVIDERNAME());
        localTextView7.setText(((RealTypeReportBean) this.realTypeReportBeans.get(paramInt)).getDSHOPNO());
        return paramView;
    }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.util.RealTypeReportAdapter
 * JD-Core Version:    0.6.0
 */