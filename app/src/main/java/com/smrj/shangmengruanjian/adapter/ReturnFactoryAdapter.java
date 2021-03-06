package com.smrj.shangmengruanjian.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.OrderGoodsBean;

import java.util.ArrayList;

public class ReturnFactoryAdapter extends BaseAdapter {
    private ArrayList<OrderGoodsBean> arrayList;
    private OrderGoodsOprationAdapter.OrderGoodsChanged orderGoodsChanged;

    public ReturnFactoryAdapter(ArrayList<OrderGoodsBean> arrayList, OrderGoodsOprationAdapter.OrderGoodsChanged orderGoodsChanged) {
        this.arrayList = arrayList;
        this.orderGoodsChanged = orderGoodsChanged;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_return_factory_item,null);
        }
        TextView barcode = convertView.findViewById(R.id.goods_opration_return_barcode);
        TextView name = convertView.findViewById(R.id.goods_opration_return_name);
        TextView provider = convertView.findViewById(R.id.goods_opration_return_provider);
        EditText num = convertView.findViewById(R.id.goods_opration_return_num);
        EditText returnPrice = convertView.findViewById(R.id.goods_opration_return_return_price);
        returnPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).getDBARCODE().equals(barcode.getText().toString())){
                        OrderGoodsBean orderGoodsBean = arrayList.get(i);
                        if (!num.getText().toString().trim().equals("")){
                            orderGoodsBean.setDSELECTNUM(Integer.valueOf(num.getText().toString()));
                        }else {
                            orderGoodsBean.setDSELECTNUM(0);
                        }
                        orderGoodsChanged.orderGoodsNumChanged(orderGoodsBean);
                    }
                }
            }
        });
        num.setText(String.valueOf(arrayList.get(position).getDSELECTNUM()));
        num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).getDBARCODE().equals(barcode.getText().toString())){
                        OrderGoodsBean orderGoodsBean = arrayList.get(i);
                        if (!num.getText().toString().trim().equals("")){
                            orderGoodsBean.setDSELECTNUM(Integer.valueOf(num.getText().toString()));
                        }else {
                            orderGoodsBean.setDSELECTNUM(0);
                        }
                        orderGoodsChanged.orderGoodsNumChanged(orderGoodsBean);
                    }
                }
            }
        });
        barcode.setText(arrayList.get(position).getDBARCODE());
        name.setText(arrayList.get(position).getDNAME());
        provider.setText(String.valueOf(arrayList.get(position).getDPROVIDERCODE()));
        returnPrice.setText(String.valueOf(arrayList.get(position).getDPRICEIN()));
        return convertView;
    }

    public static interface OrderGoodsChanged {
        void orderGoodsNumChanged(OrderGoodsBean orderGoodsBean);
    }
}
