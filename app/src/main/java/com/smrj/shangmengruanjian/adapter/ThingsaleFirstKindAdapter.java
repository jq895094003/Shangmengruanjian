package com.smrj.shangmengruanjian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.ThingSaleFirstKindBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThingsaleFirstKindAdapter extends BaseAdapter {

    ArrayList<ThingSaleFirstKindBean> saleFirstKindBeans;

    public ThingsaleFirstKindAdapter(ArrayList<ThingSaleFirstKindBean> saleFirstKindBeans) {
        this.saleFirstKindBeans = saleFirstKindBeans;
    }

    @Override
    public int getCount() {
        return saleFirstKindBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return saleFirstKindBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.thingsale_firstkind_adapter, parent, false);
        }

        TextView firstkindType = convertView.findViewById(R.id.firstkind_type);
        TextView firstkindPlanml = convertView.findViewById(R.id.firstkind_planml);
        TextView firstkindPlansale = convertView.findViewById(R.id.firstkind_plansale);
        //DCURSALE;            //当日|销售额
        TextView firstkindCursale = convertView.findViewById(R.id.firstkind_cursale);
        firstkindCursale.setText(saleFirstKindBeans.get(position).getDCURSALE());
        //DCURML;              //当日|毛利
        TextView firstkindCurml = convertView.findViewById(R.id.firstkind_curml);
        firstkindCurml.setText(saleFirstKindBeans.get(position).getDCURML());
        //DCURMLV;             //当日|毛利率
        TextView firstkindCurmlv = convertView.findViewById(R.id.firstkind_curmlv);
        if (saleFirstKindBeans.get(position).getDCURMLV().equals("")) {
            firstkindCurmlv.setText("");
        } else {
            firstkindCurmlv.setText(String.valueOf(Float.valueOf(saleFirstKindBeans.get(position).getDCURMLV()) * 100) + "%");
        }
        //DMONTHSALE;          //上月|同期销售额
        TextView firstkindMonthsale = convertView.findViewById(R.id.firstkind_monthsale);
        firstkindMonthsale.setText(saleFirstKindBeans.get(position).getDMONTHSALE());
        //DMONTHML;            //上月|同期毛利
        TextView firstkindMonthml = convertView.findViewById(R.id.firstkind_monthml);
        firstkindMonthml.setText(saleFirstKindBeans.get(position).getDMONTHML());
        //DMONTHMLV;           //上月|同期毛利率
        TextView firstkindMonthmlv = convertView.findViewById(R.id.firstkind_monthmlv);
        if (saleFirstKindBeans.get(position).getDMONTHMLV().equals("")) {
            firstkindMonthmlv.setText("");
        } else {
            firstkindMonthmlv.setText(String.valueOf(Float.valueOf(saleFirstKindBeans.get(position).getDMONTHMLV()) * 100) + "%");
        }
        //DMINCREASESALE;      //上月|同期销售增长
        TextView firstkindMincreasesale = convertView.findViewById(R.id.firstkind_mincreasesale);
        firstkindMincreasesale.setText(saleFirstKindBeans.get(position).getDMINCREASESALE());
        //DMINCREASESALEBL;    //上月|同期销售增长率
        TextView firstkindMincreasesalebl = convertView.findViewById(R.id.firstkind_mincreasesalebl);
        if (saleFirstKindBeans.get(position).getDMINCREASESALEBL() != null) {
            firstkindMincreasesalebl.setText(String.valueOf(Float.valueOf(saleFirstKindBeans.get(position).getDMINCREASESALEBL()) * 100) + "%");
        } else {
            firstkindMincreasesalebl.setText("");
        }
        //DMINCREASEML;        //上月|同期毛利增长
        TextView firstkindMincreasml = convertView.findViewById(R.id.firstkind_mincreasml);
        firstkindMincreasml.setText(saleFirstKindBeans.get(position).getDMINCREASEML());
        //DMINCREASEMLBL;      //上月|同期毛利增长率
        TextView firstkindMincreasmlbl = convertView.findViewById(R.id.firstkind_mincreasmlbl);
        if ("".equals(saleFirstKindBeans.get(position).getDMINCREASEMLBL()) || saleFirstKindBeans.get(position).getDMINCREASEMLBL() == null) {
            firstkindMincreasmlbl.setText("");
        } else {
            firstkindMincreasmlbl.setText(String.valueOf(Float.valueOf(saleFirstKindBeans.get(position).getDMINCREASEMLBL()) * 100) + "%");
        }

        //DPROVIDERNO;      //供应商编号
        TextView DPROVIDERNO = convertView.findViewById(R.id.DPROVIDERNO);
        if ("".equals(saleFirstKindBeans.get(position).getDMINCREASEMLBL()) || saleFirstKindBeans.get(position).getDMINCREASEMLBL() == null) {
            DPROVIDERNO.setText("");
        } else {
            DPROVIDERNO.setText(saleFirstKindBeans.get(position).getDCODE());
        }


        return convertView;
    }
}
