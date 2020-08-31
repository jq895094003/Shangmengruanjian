package com.smrj.shangmengruanjian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.SalesEntity;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private List<SalesEntity> salesEntities;

    public ListViewAdapter(List<SalesEntity> paramList) {
        this.salesEntities = paramList;
    }

    public int getCount() {
        List<SalesEntity> list = this.salesEntities;
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
            paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.detail_fragment_list_item, paramViewGroup, false);
            TextView orderNo = paramView.findViewById(R.id.sale_orderNo);
            TextView barcode = paramView.findViewById(R.id.sale_barcode);
            TextView name = paramView.findViewById(R.id.sale_name);
            TextView num = paramView.findViewById(R.id.sale_num);
            TextView money = paramView.findViewById(R.id.sale_money);
            TextView worker = paramView.findViewById(R.id.sale_worker);
            TextView date = paramView.findViewById(R.id.sale_date);
            orderNo.setText(salesEntities.get(paramInt).getDNO());
            barcode.setText(salesEntities.get(paramInt).getDBARCODE());
            name.setText(salesEntities.get(paramInt).getDNAME());
            num.setText(String.valueOf(salesEntities.get(paramInt).getDNUM()));
            money.setText(String.valueOf(salesEntities.get(paramInt).getDMONEY_SS()));
            worker.setText(salesEntities.get(paramInt).getDWORKERNAME());
            date.setText(salesEntities.get(paramInt).getDDATE());
        return paramView;
    }
}


/* Location:              C:\Users\SYQ\Desktop\支付查询_classes_dex2jar.jar!\com\smrj\myapplicatio\\util\ListViewAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */