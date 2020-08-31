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

public class ThingMinusAdapter extends BaseAdapter {
    ArrayList<AbnormalProductBean> abnormalProductBeans;

    public ThingMinusAdapter(ArrayList<AbnormalProductBean> abnormalProductBeans) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.thing_eminus_adapter, parent, false);
        }
        TextView abnormalName = convertView.findViewById(R.id.abnormal_name);
        TextView abnormalBarcode = convertView.findViewById(R.id.abnormal_barcode);
        TextView abnormalUnit = convertView.findViewById(R.id.abnormal_unit);
        TextView abnormalPriceIn = convertView.findViewById(R.id.abnormal_price_in);
        TextView abnormalPriceOut = convertView.findViewById(R.id.abnormal_price_out);
        TextView abnormalProviderNo = convertView.findViewById(R.id.abnormal_provider_no);
        TextView abnormalProviderName = convertView.findViewById(R.id.abnormal_provider_name);
        TextView abnormalTypeno = convertView.findViewById(R.id.abnormal_typeno);
        TextView abnormalTypename = convertView.findViewById(R.id.abnormal_typename);
        TextView abnormalSpec = convertView.findViewById(R.id.abnormal_spec);
        TextView abnormalStatus = convertView.findViewById(R.id.abnormal_status);
        abnormalBarcode.setText(abnormalProductBeans.get(position).getDBARCODE());
        abnormalName.setText(abnormalProductBeans.get(position).getDNAME());
        abnormalPriceIn.setText(String.valueOf(abnormalProductBeans.get(position).getDPRICEIN()));
        abnormalPriceOut.setText(String.valueOf(abnormalProductBeans.get(position).getDPRICE()));
        abnormalProviderName.setText(abnormalProductBeans.get(position).getDPROVIDERNAME());
        abnormalProviderNo.setText(abnormalProductBeans.get(position).getDPROVIDERNO());
        abnormalSpec.setText(abnormalProductBeans.get(position).getDSPEC());
        abnormalStatus.setText(abnormalProductBeans.get(position).getDTHINGSTATE());
        abnormalTypename.setText(abnormalProductBeans.get(position).getDKINDNAME());
        abnormalTypeno.setText(abnormalProductBeans.get(position).getDKINDNO());
        abnormalUnit.setText(abnormalProductBeans.get(position).getDUNITNAME());
        return convertView;
    }

}
