package com.smrj.shangmengruanjian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.SearchSaleOrdersBean;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchSaleOrdersAdapter extends BaseAdapter {
    ArrayList<SearchSaleOrdersBean> searchSaleOrdersBeans;

    public SearchSaleOrdersAdapter(ArrayList<SearchSaleOrdersBean> searchSaleOrdersBeans) {
        this.searchSaleOrdersBeans = searchSaleOrdersBeans;
    }

    @Override
    public int getCount() {
        return searchSaleOrdersBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return searchSaleOrdersBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_sale_order_adapter, parent, false);
        }
        TextView searchSaleOrderName = convertView.findViewById(R.id.search_sale_order_name);
        TextView searchSaleOrderDid = convertView.findViewById(R.id.search_sale_order_did);
        TextView searchSaleOrderMasterbarcode = convertView.findViewById(R.id.search_sale_order_masterbarcode);
        TextView searchSaleOrderUnit = convertView.findViewById(R.id.search_sale_order_unit);
        TextView searchSaleOrderSpec = convertView.findViewById(R.id.search_sale_order_spec);
        TextView searchSaleOrderPack = convertView.findViewById(R.id.search_sale_order_pack);
        TextView searchSaleOrderThingnum = convertView.findViewById(R.id.search_sale_order_thingnum);
        TextView searchSaleOrderThingml = convertView.findViewById(R.id.search_sale_order_thingml);
        TextView searchSaleOrderThingmoney = convertView.findViewById(R.id.search_sale_order_thingmoney);
        TextView searchSaleOrderPlanml = convertView.findViewById(R.id.search_sale_order_planml);
        TextView searchSaleOrderPlansale = convertView.findViewById(R.id.search_sale_order_plansale);
        TextView searchSaleOrderSalezbl = convertView.findViewById(R.id.search_sale_order_salezbl);
        TextView searchSaleOrderMlzbl = convertView.findViewById(R.id.search_sale_order_mlzbl);
        TextView searchSaleOrderKindno = convertView.findViewById(R.id.search_sale_order_kindno);
        TextView searchSaleOrderKindname = convertView.findViewById(R.id.search_sale_order_kindname);
        searchSaleOrderName.setText(searchSaleOrdersBeans.get(position).getDNAME());
        searchSaleOrderDid.setText(searchSaleOrdersBeans.get(position).getDID());
        searchSaleOrderMasterbarcode.setText(searchSaleOrdersBeans.get(position).getDMASTERBARCODE());
        searchSaleOrderUnit.setText(searchSaleOrdersBeans.get(position).getDUNITNAME());
        searchSaleOrderSpec.setText(searchSaleOrdersBeans.get(position).getDSPEC());
        searchSaleOrderPack.setText(searchSaleOrdersBeans.get(position).getDPACK());
        searchSaleOrderThingnum.setText(searchSaleOrdersBeans.get(position).getDTHINGNUM());
        searchSaleOrderThingml.setText(searchSaleOrdersBeans.get(position).getDTHINGML());
        searchSaleOrderThingmoney.setText(searchSaleOrdersBeans.get(position).getDTHINGMONEY());
        searchSaleOrderPlanml.setText(searchSaleOrdersBeans.get(position).getDPLANML());
        searchSaleOrderPlansale.setText(searchSaleOrdersBeans.get(position).getDPLANSALE());
        DecimalFormat df = new DecimalFormat("##.##");
        String dsalzbl = searchSaleOrdersBeans.get(position).getDSALEZBL().replace("%","");
        searchSaleOrderSalezbl.setText(df.format(Double.valueOf(dsalzbl)) + "%");
        String dmlzbl = searchSaleOrdersBeans.get(position).getDMLZBL().replace("%","");
        searchSaleOrderMlzbl.setText(df.format(Double.valueOf(dmlzbl)) + "%");
        searchSaleOrderKindno.setText(searchSaleOrdersBeans.get(position).getDKINDNO());
        searchSaleOrderKindname.setText(searchSaleOrdersBeans.get(position).getDKINDNAME());

        System.out.println("searchSaleOrdersBeans = " + searchSaleOrdersBeans);

        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.search_sale_order_name)
        TextView searchSaleOrderName;
        @BindView(R.id.search_sale_order_did)
        TextView searchSaleOrderDid;
        @BindView(R.id.search_sale_order_masterbarcode)
        TextView searchSaleOrderMasterbarcode;
        @BindView(R.id.search_sale_order_unit)
        TextView searchSaleOrderUnit;
        @BindView(R.id.search_sale_order_spec)
        TextView searchSaleOrderSpec;
        @BindView(R.id.search_sale_order_pack)
        TextView searchSaleOrderPack;
        @BindView(R.id.search_sale_order_thingnum)
        TextView searchSaleOrderThingnum;
        @BindView(R.id.search_sale_order_thingml)
        TextView searchSaleOrderThingml;
        @BindView(R.id.search_sale_order_thingmoney)
        TextView searchSaleOrderThingmoney;
        @BindView(R.id.search_sale_order_planml)
        TextView searchSaleOrderPlanml;
        @BindView(R.id.search_sale_order_plansale)
        TextView searchSaleOrderPlansale;
        @BindView(R.id.search_sale_order_salezbl)
        TextView searchSaleOrderSalezbl;
        @BindView(R.id.search_sale_order_mlzbl)
        TextView searchSaleOrderMlzbl;
        @BindView(R.id.search_sale_order_kindno)
        TextView searchSaleOrderKindno;
        @BindView(R.id.search_sale_order_kindname)
        TextView searchSaleOrderKindname;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}