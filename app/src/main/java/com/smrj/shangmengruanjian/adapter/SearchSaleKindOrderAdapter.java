package com.smrj.shangmengruanjian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.SearchSaleKindOrdersBean;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchSaleKindOrderAdapter extends BaseAdapter {
    ArrayList<SearchSaleKindOrdersBean> searchSaleKindOrdersBeans;

    public SearchSaleKindOrderAdapter(ArrayList<SearchSaleKindOrdersBean> searchSaleKindOrdersBeans) {
        this.searchSaleKindOrdersBeans = searchSaleKindOrdersBeans;
    }

    @Override
    public int getCount() {
        return searchSaleKindOrdersBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return searchSaleKindOrdersBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_sale_order_kind_adapter, parent, false);
        }
        TextView searchSaleOrderKindKindname = convertView.findViewById(R.id.search_sale_order_kind_kindname);
        TextView searchSaleOrderKindDid = convertView.findViewById(R.id.search_sale_order_kind_did);
        TextView searchSaleOrderKindKindno = convertView.findViewById(R.id.search_sale_order_kind_kindno);
        TextView searchSaleOrderKindMlzbl = convertView.findViewById(R.id.search_sale_order_kind_mlzbl);
        TextView searchSaleOrderKindPlanml = convertView.findViewById(R.id.search_sale_order_kind_planml);
        TextView searchSaleOrderKindPlansale = convertView.findViewById(R.id.search_sale_order_kind_plansale);
        TextView searchSaleOrderKindKindnum = convertView.findViewById(R.id.search_sale_order_kind_kindnum);
        TextView searchSaleOrderKindKindml = convertView.findViewById(R.id.search_sale_order_kind_kindml);
        TextView searchSaleOrderKindKindmoney = convertView.findViewById(R.id.search_sale_order_kind_kindmoney);
        TextView searchSaleOrderKindSalezbl = convertView.findViewById(R.id.search_sale_order_kind_salezbl);
        searchSaleOrderKindKindname.setText(searchSaleKindOrdersBeans.get(position).getDKINDNAME());
        searchSaleOrderKindDid.setText(String.valueOf(searchSaleKindOrdersBeans.get(position).getDID()));
        searchSaleOrderKindKindno.setText(searchSaleKindOrdersBeans.get(position).getDKINDNO());
        DecimalFormat df = new DecimalFormat("##.##");
        searchSaleOrderKindMlzbl.setText(df.format(Double.valueOf(searchSaleKindOrdersBeans.get(position).getDMLZBL())));
        searchSaleOrderKindPlanml.setText(String.valueOf(searchSaleKindOrdersBeans.get(position).getDPLANML()));
        searchSaleOrderKindPlansale.setText(searchSaleKindOrdersBeans.get(position).getDPLANSALE());
        searchSaleOrderKindKindnum.setText(String.valueOf(searchSaleKindOrdersBeans.get(position).getDKINDNUM()));
        searchSaleOrderKindKindml.setText(String.valueOf(searchSaleKindOrdersBeans.get(position).getDKINDML()));
        searchSaleOrderKindKindmoney.setText(String.valueOf(searchSaleKindOrdersBeans.get(position).getDKINDMONEY()));
        searchSaleOrderKindSalezbl.setText(df.format(Double.valueOf(searchSaleKindOrdersBeans.get(position).getDSALEZBL())));
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.search_sale_order_kind_kindname)
        TextView searchSaleOrderKindKindname;
        @BindView(R.id.search_sale_order_kind_did)
        TextView searchSaleOrderKindDid;
        @BindView(R.id.search_sale_order_kind_kindno)
        TextView searchSaleOrderKindKindno;
        @BindView(R.id.search_sale_order_kind_mlzbl)
        TextView searchSaleOrderKindMlzbl;
        @BindView(R.id.search_sale_order_kind_planml)
        TextView searchSaleOrderKindPlanml;
        @BindView(R.id.search_sale_order_kind_plansale)
        TextView searchSaleOrderKindPlansale;
        @BindView(R.id.search_sale_order_kind_kindnum)
        TextView searchSaleOrderKindKindnum;
        @BindView(R.id.search_sale_order_kind_kindml)
        TextView searchSaleOrderKindKindml;
        @BindView(R.id.search_sale_order_kind_kindmoney)
        TextView searchSaleOrderKindKindmoney;
        @BindView(R.id.search_sale_order_kind_salezbl)
        TextView searchSaleOrderKindSalezbl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}