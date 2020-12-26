package com.smrj.shangmengruanjian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.WorkerSaleEntity;

import java.text.DecimalFormat;
import java.util.List;

public class WorkerSaleAdapter extends BaseAdapter {
    private List<WorkerSaleEntity> salesEntities;

    public WorkerSaleAdapter(List<WorkerSaleEntity> paramList) {
        this.salesEntities = paramList;
    }


    public int getCount() {
        List<WorkerSaleEntity> list = this.salesEntities;
        return (list == null) ? 0 : list.size();
    }

    public Object getItem(int paramInt) {
        return this.salesEntities.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        if (paramView == null)
            paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.worker_sale_list_item, paramViewGroup, false);

        //日期
        TextView date = paramView.findViewById(R.id.sale_date);
        date.setText(salesEntities.get(paramInt).getDDATE_S());
        //姓名
        TextView DWORKERNAME = paramView.findViewById(R.id.DWORKERNAME);
        DWORKERNAME.setText(salesEntities.get(paramInt).getDWORKERNAME());
        //应收
        TextView DMONEY_YS = paramView.findViewById(R.id.DMONEY_YS);
        DMONEY_YS.setText(salesEntities.get(paramInt).getDMONEY_YS());
        //实收
        TextView DMONEY_SS = paramView.findViewById(R.id.DMONEY_SS);
        DMONEY_SS.setText(salesEntities.get(paramInt).getDMONEY_SS());
        //退货金额
        TextView DMONEY_TH = paramView.findViewById(R.id.DMONEY_TH);
        DMONEY_TH.setText(salesEntities.get(paramInt).getDMONEY_TH());
        //折扣
        TextView DMONEY_ZK = paramView.findViewById(R.id.DMONEY_ZK);
        DMONEY_ZK.setText(salesEntities.get(paramInt).getDMONEY_ZK());
        //现金
        TextView DMONEY_SS_XJ = paramView.findViewById(R.id.DMONEY_SS_XJ);
        DMONEY_SS_XJ.setText(salesEntities.get(paramInt).getDMONEY_SS_XJ());
        ///储值卡
        TextView DMONEY_SS_CZK = paramView.findViewById(R.id.DMONEY_SS_CZK);
        DMONEY_SS_CZK.setText(salesEntities.get(paramInt).getDMONEY_SS_CZK());
        //微信
        TextView dmoney_ss_WX = paramView.findViewById(R.id.dmoney_ss_WX);
        dmoney_ss_WX.setText(salesEntities.get(paramInt).getDMONEY_SS_WX());
        //支付宝
        TextView dmoney_ss_zfb = paramView.findViewById(R.id.dmoney_ss_zfb);
        dmoney_ss_zfb.setText(salesEntities.get(paramInt).getDmoney_ss_zfb());
        //整体客流
        TextView DWATER = paramView.findViewById(R.id.DWATER);
        DWATER.setText(salesEntities.get(paramInt).getDNO_NO());
        //整体客单
        TextView DPRICECUSTOMER = paramView.findViewById(R.id.DPRICECUSTOMER);
        DecimalFormat df = new DecimalFormat("##.##");
        DPRICECUSTOMER.setText(df.format(Double.valueOf(salesEntities.get(paramInt).getDPRICECUSTOMER())));





        return paramView;
    }
}


/* Location:              C:\Users\SYQ\Desktop\支付查询_classes_dex2jar.jar!\com\smrj\myapplicatio\\util\ListViewAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */