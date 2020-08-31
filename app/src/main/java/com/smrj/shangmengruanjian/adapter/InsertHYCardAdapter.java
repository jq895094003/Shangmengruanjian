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
import com.smrj.shangmengruanjian.bean.CardTicketInsertBean;
import com.smrj.shangmengruanjian.bean.OrderGoodsBean;

import java.util.ArrayList;

public class InsertHYCardAdapter extends BaseAdapter {

    private ArrayList<CardTicketInsertBean> cardTicketInsertBeans;

    public InsertHYCardAdapter(ArrayList<CardTicketInsertBean> cardTicketInsertBeans) {
        this.cardTicketInsertBeans = cardTicketInsertBeans;
    }

    @Override
    public int getCount() {
        return cardTicketInsertBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return cardTicketInsertBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.insert_hycard_item,parent,false);
        }
        TextView name = convertView.findViewById(R.id.insert_hycard_item_name);
        TextView phone = convertView.findViewById(R.id.insert_hycard_item_phone);
        name.setText(cardTicketInsertBeans.get(position).getHYNAME());
        phone.setText(cardTicketInsertBeans.get(position).getHYPHONE());
        return convertView;
    }


}
