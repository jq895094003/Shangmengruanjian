package com.smrj.shangmengruanjian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.VThingBean;

import java.util.List;

public class VThingAdapter extends BaseAdapter {
    private List<VThingBean> vThingBeans;

    public VThingAdapter(List<VThingBean> paramList) {
        this.vThingBeans = paramList;
    }


    public int getCount() {
        List<VThingBean> list = this.vThingBeans;
        return (list == null) ? 0 : list.size();
    }

    public Object getItem(int paramInt) {
        return this.vThingBeans.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        if (paramView == null)
            paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.v_thing_list_item, paramViewGroup, false);

        //主条码
        TextView DMASTERBARCODE = paramView.findViewById(R.id.DMASTERBARCODE);
        DMASTERBARCODE.setText(vThingBeans.get(paramInt).getDMASTERBARCODE());
        //条码
        TextView DBARCODE = paramView.findViewById(R.id.DBARCODE);
        DBARCODE.setText(vThingBeans.get(paramInt).getDBARCODE());
        //品名
        TextView DNAME = paramView.findViewById(R.id.DNAME);
        DNAME.setText(vThingBeans.get(paramInt).getDNAME());
        //供货商
        TextView DPROVIDERNAME = paramView.findViewById(R.id.DPROVIDERNAME);
        DPROVIDERNAME.setText(vThingBeans.get(paramInt).getDPROVIDERNAME());
        //进价
        TextView DPRICEIN = paramView.findViewById(R.id.DPRICEIN);
        DPRICEIN.setText(vThingBeans.get(paramInt).getDPRICEIN());
        //售价
        TextView DPRICE = paramView.findViewById(R.id.DPRICE);
        DPRICE.setText(vThingBeans.get(paramInt).getDPRICE());
        //商品状态
        TextView DTHINGSTATE = paramView.findViewById(R.id.DTHINGSTATE);
        DTHINGSTATE.setText(vThingBeans.get(paramInt).getDTHINGSTATE());
        ///商品类型
        TextView DTHINGTYPE = paramView.findViewById(R.id.DTHINGTYPE);
        DTHINGTYPE.setText(vThingBeans.get(paramInt).getDTHINGTYPE());
        //货类名称
        TextView DKINDNAME = paramView.findViewById(R.id.DKINDNAME);
        DKINDNAME.setText(vThingBeans.get(paramInt).getDKINDNAME());
        //货区
        TextView DAREANAMELOCAL = paramView.findViewById(R.id.DAREANAMELOCAL);
        DAREANAMELOCAL.setText(vThingBeans.get(paramInt).getDAREANAMELOCAL());
        return paramView;
    }
}


/* Location:              C:\Users\SYQ\Desktop\支付查询_classes_dex2jar.jar!\com\smrj\myapplicatio\\util\ListViewAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */