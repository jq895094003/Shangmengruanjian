package com.smrj.shangmengruanjian.ticket;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.smrj.shangmengruanjian.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AccountItemSelect extends AppCompatActivity implements OnClickListener, View.OnTouchListener {
    /**
     * 21-34行为声明并实例化各种控件，使用ButterKnife绑定
     */
    @BindView(R.id.to_allot_order)
    ImageButton toAllotOrder;

    @BindView(R.id.to_check_order)
    ImageButton toCheckOrder;

    @BindView(R.id.to_create_order)
    ImageButton toCreateOrder;

    @BindView(R.id.to_pandian_order)
    ImageButton toPandianOrder;

    @BindView(R.id.to_purchase_change)
    ImageButton toPurchase;

    //Activity生命周期，初始化各种工具，设置监听
    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.run_account__select);
        ButterKnife.bind(this);
        this.toPurchase.setOnClickListener(this);
        this.toPurchase.setOnTouchListener(this);
        this.toCheckOrder.setOnClickListener(this);
        this.toCheckOrder.setOnTouchListener(this);
        this.toAllotOrder.setOnClickListener(this);
        this.toAllotOrder.setOnTouchListener(this);
        this.toCreateOrder.setOnClickListener(this);
        this.toCreateOrder.setOnTouchListener(this);
        this.toPandianOrder.setOnClickListener(this);
        this.toPandianOrder.setOnTouchListener(this);
    }

    //按钮单击事件监听
    public void onClick(View paramView) {
        switch (paramView.getId()) {
            case R.id.to_purchase_change:
                startActivity(new Intent(this, RunAnAccount.class));
                break;
            case R.id.to_pandian_order:
                startActivity(new Intent(this, PandianAccount.class));
                break;
            case R.id.to_create_order:
                startActivity(new Intent(this, CreateProductAccount.class));
                break;
            case R.id.to_check_order:
                startActivity(new Intent(this, CheckOrderAccount.class));
                break;
            case R.id.to_allot_order:
                startActivity(new Intent(this, AllotOrderAccount.class));
                break;
        }
    }
    @Override
    public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
        if (paramMotionEvent.getAction() == 0)
            paramView.setBackgroundColor(-65536);
        if (paramMotionEvent.getAction() == 1)
            paramView.setBackgroundColor(-1);
        return false;
    }

}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.AccountItemSelect
 * JD-Core Version:    0.6.0
 */