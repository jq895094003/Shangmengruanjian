package com.smrj.shangmengruanjian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.AbnormalProductBean;

import java.util.ArrayList;

public class ThingStoreEhightAdapter extends BaseAdapter {
    ArrayList<AbnormalProductBean> abnormalProductBeans;

    public ThingStoreEhightAdapter(ArrayList<AbnormalProductBean> abnormalProductBeans) {
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
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.thing_store_ehight_adapter,parent,false);
        }
        TextView abnormalName = convertView.findViewById(R.id.abnormal_name);
        TextView abnormalBarcode = convertView.findViewById(R.id.abnormal_barcode);
        TextView abnormalUnit = convertView.findViewById(R.id.abnormal_unit);
        TextView abnormalSpec = convertView.findViewById(R.id.abnormal_spec);
        TextView abnormalNumday = convertView.findViewById(R.id.abnormal_numday);
        TextView abnormalTypeno = convertView.findViewById(R.id.abnormal_typeno);
        TextView abnormalTypename = convertView.findViewById(R.id.abnormal_typename);
        TextView abnormalStore = convertView.findViewById(R.id.abnormal_store);
        TextView abnormalThingcode = convertView.findViewById(R.id.abnormal_thingcode);
        abnormalName.setText(abnormalProductBeans.get(position).getDNAME());
        abnormalBarcode.setText(abnormalProductBeans.get(position).getDBARCODE());
        abnormalUnit.setText(abnormalProductBeans.get(position).getDUNITNAME());
        abnormalSpec.setText(abnormalProductBeans.get(position).getDSPEC());
        abnormalNumday.setText(abnormalProductBeans.get(position).getDNUM_DAY());
        abnormalTypeno.setText(abnormalProductBeans.get(position).getDKINDNO());
        abnormalTypename.setText(abnormalProductBeans.get(position).getDKINDNAME());
        abnormalStore.setText(abnormalProductBeans.get(position).getDNUM_STORE());
        abnormalThingcode.setText(String.valueOf(abnormalProductBeans.get(position).getDTHINGCODE()));
        return convertView;
    }
}
