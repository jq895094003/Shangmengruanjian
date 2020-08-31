package com.smrj.shangmengruanjian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.AbnormalProductBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoreminusAdapter extends BaseAdapter {
    ArrayList<AbnormalProductBean> abnormalProductBeans;

    public StoreminusAdapter(ArrayList<AbnormalProductBean> abnormalProductBeans) {
        this.abnormalProductBeans = abnormalProductBeans;
    }

    @Override
    public int getCount() {
        return abnormalProductBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return abnormalProductBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_minus_adapter, parent, false);
        }

        TextView abnormalName = convertView.findViewById(R.id.abnormal_name);
        TextView abnormalBarcode = convertView.findViewById(R.id.abnormal_barcode);
        TextView abnormalNum = convertView.findViewById(R.id.abnormal_num);
        TextView abnormalTop = convertView.findViewById(R.id.abnormal_top);
        TextView abnormalBottom = convertView.findViewById(R.id.abnormal_bottom);
        TextView abnormalTypeno = convertView.findViewById(R.id.abnormal_typeno);
        TextView abnormalTypename = convertView.findViewById(R.id.abnormal_typename);
        abnormalBarcode.setText(abnormalProductBeans.get(position).getDMASTERBARCODE());
        abnormalName.setText(abnormalProductBeans.get(position).getDNAME());
        abnormalNum.setText(String.valueOf(abnormalProductBeans.get(position).getDNUM()));
        abnormalTypename.setText(abnormalProductBeans.get(position).getDkindname());
        abnormalTypeno.setText(abnormalProductBeans.get(position).getDkindno());
        abnormalTop.setText(String.valueOf(abnormalProductBeans.get(position).getDMAX()));
        abnormalBottom.setText(String.valueOf(abnormalProductBeans.get(position).getDMIN()));
        return convertView;
    }
}
