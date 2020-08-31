package com.smrj.shangmengruanjian.pandian;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.CheckOrderAccountInfoBean;
import com.smrj.shangmengruanjian.service.FragmentToFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PandianOrderInfo extends AppCompatActivity implements OnClickListener {

    /**
     * 30-68行，声明页面控件并实例化，使用ButterKnife进行绑定
     */
    @BindView(R.id.check_order_dthingcode)
    TextView barCode;

    @BindView(R.id.check_order_dbarcode)
    TextView code;
    FragmentToFragment fragmentToFragment;

    @BindView(R.id.check_order_dname)
    TextView name;

    @BindView(R.id.check_order_dnum)
    TextView num;

    @BindView(R.id.check_order_dpack)
    TextView pack;

    @BindView(R.id.check_order_dprice)
    TextView price;

    @BindView(R.id.check_order_pricein)
    TextView priceIn;

    @BindView(R.id.check_order_dprovidercode)
    TextView providerCode;
    CheckOrderAccountInfoBean purchase;

    @BindView(R.id.check_order_delete)
    Button purchaseChangeCommit;
    ArrayList<String> result = new ArrayList();
    SharedPreferences sharedPreferences;

    @BindView(R.id.check_order_dspec)
    TextView spec;

    @BindView(R.id.check_order_dcode)
    TextView thingCode;

    @BindView(R.id.check_order_dunitname)
    TextView unitName;

    /**
     * 页面控件点击方法
     * 删除单据方法
     * @param paramView
     */
    @Override
    public void onClick(View paramView) {
        if (paramView.getId() != R.id.check_order_delete){
            OrderPandian.orderPandian.removeProduct(this.barCode.getText().toString());
            finish();
        }
    }

    /**
     * Activity声明周期，页面创建方法
     * 实例化所需工具，设置控件监听
     * @param paramBundle
     */
    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.pandian_order_info);
        ButterKnife.bind(this);
        this.purchase = ((CheckOrderAccountInfoBean) getIntent().getSerializableExtra("orderPandian"));
        this.sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        this.priceIn.setText(this.purchase.getDpricein());
        this.thingCode.setText(this.purchase.getDthingcode());
        this.barCode.setText(this.purchase.getDbarcode());
        this.spec.setText(this.purchase.getDspec());
        this.pack.setText(this.purchase.getDpack());
        this.unitName.setText(this.purchase.getDunitname());
        this.providerCode.setText(this.purchase.getDprovidercode());
        this.code.setText(this.purchase.getDcode());
        this.name.setText(this.purchase.getDname());
        this.price.setText(this.purchase.getDprice());
        this.num.setText(this.purchase.getDnum());
        this.purchaseChangeCommit.setOnClickListener(this);
    }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.pandian.PandianOrderInfo
 * JD-Core Version:    0.6.0
 */