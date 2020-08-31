package com.smrj.shangmengruanjian.orderoperation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.CheckOrderBean;
import com.smrj.shangmengruanjian.service.FragmentToFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckOrderInfo extends AppCompatActivity implements OnClickListener {

  /**
   * 26-91行，声明并实例化各种页面控件，使用ButterKnife绑定
   */
  @BindView(R.id.check_order_dbarcode)
  TextView barCode;

  @BindView(R.id.check_order_dcode)
  TextView code;

  @BindView(R.id.check_order_ddateinput)
  TextView dateInput;
  FragmentToFragment fragmentToFragment;

  @BindView(R.id.check_order_dkindname)
  TextView kindName;

  @BindView(R.id.check_order_dmasterbarcode)
  TextView masterBarcode;

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

  @BindView(R.id.check_order_dprovidername)
  TextView providerName;

  @BindView(R.id.check_order_dproviderno)
  TextView providerNo;
  CheckOrderBean purchase;

  @BindView(R.id.check_order_delete)
  Button purchaseChangeCommit;
  ArrayList<String> result = new ArrayList();
  SharedPreferences sharedPreferences;

  @BindView(R.id.check_order_dshopname)
  TextView shopName;

  @BindView(R.id.check_order_dshopno)
  TextView shopNo;

  @BindView(R.id.check_order_dspec)
  TextView spec;

  @BindView(R.id.check_order_dthingcode)
  TextView thingCode;

  @BindView(R.id.check_order_dthingstate)
  TextView thingState;

  @BindView(R.id.check_order_dthingtype)
  TextView thingType;

  @BindView(R.id.check_order_dunitname)
  TextView unitName;


  /**
   * 页面控件点击事件
   * 实现页面跳转，放入下一页面所需要的参数
   * @param paramView
   */
  public void onClick(View paramView) {
    if (paramView.getId() == R.id.check_order_delete){
      if ((!this.priceIn.getText().toString().trim().equals("")) && (!this.priceIn.getText().toString().trim().equals(null))) {
        String str = this.barCode.getText().toString();
        Intent localIntent = new Intent();
        localIntent.setClass(this, CheckBottom.class);
        localIntent.putExtra("id", 1);
        localIntent.putExtra("checkInfoDelete", str);
        startActivity(localIntent);
        return;
      }
      Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
    }

  }

  //Activity声明周期，实例化各种工具、控件、监听事件
  @Override
  protected void onCreate(@Nullable Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.check_order_info);
    ButterKnife.bind(this);
    this.purchase = ((CheckOrderBean)getIntent().getSerializableExtra("checkOrderInfo"));
    this.sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
    this.priceIn.setText(this.purchase.getDpricein());
    this.thingCode.setText(this.purchase.getDthingcode());
    this.barCode.setText(this.purchase.getDbarcode());
    this.spec.setText(this.purchase.getDspec());
    this.pack.setText(this.purchase.getDpack());
    this.unitName.setText(this.purchase.getDunitname());
    this.providerCode.setText(this.purchase.getDprovidercode());
    this.providerNo.setText(this.purchase.getDproviderno());
    this.code.setText(this.purchase.getDcode());
    this.name.setText(this.purchase.getDname());
    this.dateInput.setText(this.purchase.getDdateinput());
    this.price.setText(this.purchase.getDprice());
    this.masterBarcode.setText(this.purchase.getDmasterbarcode());
    this.shopName.setText(this.purchase.getDshopname());
    this.kindName.setText(this.purchase.getDkindname());
    this.thingState.setText(this.purchase.getDthingstate());
    this.providerName.setText(this.purchase.getDprovidername());
    this.num.setText(this.purchase.getDnum());
    this.thingType.setText(this.purchase.getDthingtype());
    this.shopNo.setText(this.purchase.getDshopno());
    this.purchaseChangeCommit.setOnClickListener(this);
  }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.CheckOrderInfo
 * JD-Core Version:    0.6.0
 */