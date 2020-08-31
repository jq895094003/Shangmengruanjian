package com.smrj.shangmengruanjian.dateReport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.dateReport.ReportInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportMain extends AppCompatActivity implements OnClickListener {

    /**
     * 22-38行，声明并实例化页面控件，使用ButterKnife绑定
     */
    @BindView(R.id.guest_report)
    ImageButton guestReport;

    @BindView(R.id.moneier_report)
    ImageButton monierReport;

    @BindView(R.id.real_report)
    ImageButton realReport;

    @BindView(R.id.shoppe_report)
    ImageButton shoppeReport;

    @BindView(R.id.single_report)
    ImageButton singleReport;

    @BindView(R.id.type_report)
    ImageButton typeReport;

    /**
     * 页面控件点击方法
     * 根据点击控件的ID值来使用不同的方法
     * 进行页面跳转，放入下一页面所需要的参数
     * @param paramView
     */
    public void onClick(View paramView) {
        Intent localIntent = new Intent(this, ReportInfo.class);
        switch (paramView.getId()) {
            default:
                return;
            case R.id.type_report:
                localIntent.putExtra("type", "类型");
                startActivity(localIntent);
                return;
            case R.id.single_report:
                localIntent.putExtra("type", "单品");
                startActivity(localIntent);
                return;
            case R.id.shoppe_report:
                localIntent.putExtra("type", "专柜");
                startActivity(localIntent);
                return;
            case R.id.real_report:
                localIntent.putExtra("type", "实时");
                startActivity(localIntent);
                return;
            case R.id.moneier_report:
                localIntent.putExtra("type", "收款员");
                startActivity(localIntent);
                return;
            case R.id.guest_report:
                localIntent.putExtra("type", "客流");
                startActivity(localIntent);
                return;
        }

    }

    /**
     * Activity生命周期，页面加载方法
     * @param paramBundle
     */
    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.report_main);
        ButterKnife.bind(this);
        this.singleReport.setOnClickListener(this);
        this.typeReport.setOnClickListener(this);
        this.guestReport.setOnClickListener(this);
        this.monierReport.setOnClickListener(this);
        this.shoppeReport.setOnClickListener(this);
        this.realReport.setOnClickListener(this);
    }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.dateReport.ReportMain
 * JD-Core Version:    0.6.0
 */