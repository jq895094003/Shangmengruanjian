package com.smrj.shangmengruanjian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.SaleMonitorEntity;

import java.util.List;

public class SaleMonitorAdapter extends BaseAdapter {
    private List<SaleMonitorEntity> salesEntities;

    public SaleMonitorAdapter(List<SaleMonitorEntity> paramList) {
        this.salesEntities = paramList;
    }


    public int getCount() {
        List<SaleMonitorEntity> list = this.salesEntities;
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
            paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.sale_monitor_list_item, paramViewGroup, false);

        //小票号
        TextView dno = paramView.findViewById(R.id.dno);
        dno.setText(salesEntities.get(paramInt).getDno());
        //条码
        TextView dinsetcode = paramView.findViewById(R.id.dinsetcode);
        dinsetcode.setText(salesEntities.get(paramInt).getDinsetcode());
        //品名
        TextView dname = paramView.findViewById(R.id.dname);
        dname.setText(salesEntities.get(paramInt).getDname());
        //单位
        TextView dunitname = paramView.findViewById(R.id.dunitname);
        dunitname.setText(salesEntities.get(paramInt).getDunitname());
        //日期
        TextView ddate = paramView.findViewById(R.id.ddate);
        ddate.setText(salesEntities.get(paramInt).getDdate());
        //状态
        TextView dtime = paramView.findViewById(R.id.ddiskname);
        dtime.setText(salesEntities.get(paramInt).getDdiskname());
        //进价
        TextView dpricein = paramView.findViewById(R.id.dpricein);
        dpricein.setText(salesEntities.get(paramInt).getDPRICEIN());
        ///售价
        TextView dprice = paramView.findViewById(R.id.dprice);
        dprice.setText(salesEntities.get(paramInt).getDprice());
        //收银员
        TextView dworker = paramView.findViewById(R.id.dworker);
        dworker.setText(salesEntities.get(paramInt).getDworker());
        //实收
        TextView dmoney_ss = paramView.findViewById(R.id.dmoney_ss);
        dmoney_ss.setText(salesEntities.get(paramInt).getDmoney_ss());
        //毛利
        TextView Dml = paramView.findViewById(R.id.Dml);
        try{
            Dml.setText(String.format("%.2f",Double.parseDouble(salesEntities.get(paramInt).getDml())));
        } catch (Exception e){
            Dml.setText("");
        }
        //数量
        TextView dnum = paramView.findViewById(R.id.dnum);
        dnum.setText(salesEntities.get(paramInt).getDnum());
        //供货商
        TextView dprovidername = paramView.findViewById(R.id.dprovidername);
        dprovidername.setText(salesEntities.get(paramInt).getDprovidername());

        return paramView;
    }
}