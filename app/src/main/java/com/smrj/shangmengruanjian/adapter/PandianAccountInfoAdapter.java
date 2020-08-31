package com.smrj.shangmengruanjian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.PandianAccountInfoBean;

import java.util.ArrayList;

public class PandianAccountInfoAdapter extends BaseAdapter {
    ArrayList<PandianAccountInfoBean> pandianAccountInfoBeans;

    public PandianAccountInfoAdapter(ArrayList<PandianAccountInfoBean> paramArrayList) {
        this.pandianAccountInfoBeans = paramArrayList;
    }

    public int getCount() {
        return this.pandianAccountInfoBeans.size();
    }

    public Object getItem(int paramInt) {
        return this.pandianAccountInfoBeans.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return paramInt;
    }

    public ArrayList<PandianAccountInfoBean> getPandianAccountInfoBeans() {
        return this.pandianAccountInfoBeans;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        if (paramView == null)
            paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.pandian_account_info_adapter, paramViewGroup, false);
        ((TextView) paramView.findViewById(R.id.pandian_account_info_adapter_dname)).setText(((PandianAccountInfoBean) this.pandianAccountInfoBeans.get(paramInt)).getDname());
        ((TextView) paramView.findViewById(R.id.pandian_account_info_adapter_barcode)).setText(((PandianAccountInfoBean) this.pandianAccountInfoBeans.get(paramInt)).getDbarcode());
        ((TextView) paramView.findViewById(R.id.pandian_account_info_adapter_dthingcode)).setText(((PandianAccountInfoBean) this.pandianAccountInfoBeans.get(paramInt)).getDthingcode());
        ((TextView) paramView.findViewById(R.id.pandian_account_info_adapter_dunitname)).setText(((PandianAccountInfoBean) this.pandianAccountInfoBeans.get(paramInt)).getDunitname());


        ((EditText) paramView.findViewById(R.id.pandian_account_info_adapter_dnum)).setText(this.pandianAccountInfoBeans.get(paramInt).getDnum());

//        paramView.findViewById(R.id.pandian_account_info_adapter_dnum);
        return paramView;
    }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.util.PandianAccountInfoAdapter
 * JD-Core Version:    0.6.0
 */