package com.smrj.shangmengruanjian.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smrj.shangmengruanjian.R;

import java.util.ArrayList;

public class MyGridViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> itemGridList;
    private ArrayList<Drawable> itemGridPic;

    public MyGridViewAdapter(Context context, ArrayList<String> itemGridList,ArrayList<Drawable> itemGridPic) {
        this.context = context;
        this.itemGridList = itemGridList;
        this.itemGridPic = itemGridPic;
    }

    @Override
    public int getCount() {
        return itemGridList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemGridList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = View.inflate(context, R.layout.gridview_item, null);
        }
        TextView tvGridView = (TextView) convertView.findViewById(R.id.tv_gridview);
        tvGridView.setText(itemGridList.get(position));
        ImageView ivGridView = convertView.findViewById(R.id.iv_gridview);
        ivGridView.setImageDrawable(itemGridPic.get(position));
        return convertView;
    }
}
