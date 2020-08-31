package com.smrj.shangmengruanjian.pandian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.smrj.shangmengruanjian.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PandianMain extends AppCompatActivity implements OnClickListener {

    /**
     * 22-32行，声明页面控件，使用ButterKnife进行绑定
     */
    @BindView(R.id.corner_pandian)
    ImageButton cornerPandian;

    @BindView(R.id.product_pandian)
    ImageButton productPandian;

    @BindView(R.id.provider_pandian)
    ImageButton providerPandian;

    @BindView(R.id.type_pandian)
    ImageButton typePandian;

    /**
     * 页面控件点击方法，根据点击控件的ID放入不同的参数
     * @param paramView
     */
    public void onClick(View paramView) {
        Intent localIntent = new Intent(this, OrderPandian.class);
        switch (paramView.getId()) {
            default:
                return;
            case R.id.type_pandian:
                localIntent.putExtra("tag", "货类盘点");
                startActivity(localIntent);
                return;
            case R.id.provider_pandian:
                localIntent.putExtra("tag", "供应商盘点");
                startActivity(localIntent);
                return;
            case R.id.product_pandian:
                localIntent.putExtra("tag", "单品盘点");
                startActivity(localIntent);
                return;
            case R.id.corner_pandian:
                localIntent.putExtra("tag", "货区盘点");
                startActivity(localIntent);
                return;
        }

    }

    /**
     * Activity页面创建方法
     * 设置监听
     * @param paramBundle
     */
    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.pandian);
        ButterKnife.bind(this);
        this.typePandian.setOnClickListener(this);
        this.productPandian.setOnClickListener(this);
        this.providerPandian.setOnClickListener(this);
        this.cornerPandian.setOnClickListener(this);
    }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.pandian.PandianMain
 * JD-Core Version:    0.6.0
 */