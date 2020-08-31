package com.smrj.shangmengruanjian.orderoperation;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.bean.PurchaseBean;
import com.smrj.shangmengruanjian.util.MyUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class PurchaseChange extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    /**
     * 35-95行，声明并实例化页面控件，使用ButterKnife进行绑定
     */
    @BindView(R.id.purchase_change_dcode)
    TextView code;

    @BindView(R.id.purchase_change_ddateinput)
    TextView dateInput;

    @BindView(R.id.purchase_change_dkindname)
    TextView kindName;

    @BindView(R.id.purchase_change_dmasterbarcode)
    TextView masterBarcode;

    @BindView(R.id.purchase_change_dname)
    TextView name;

    @BindView(R.id.purchase_change_dnum)
    TextView num;

    @BindView(R.id.purchase_change_dpack)
    TextView pack;

    @BindView(R.id.purchase_change_dprice)
    EditText price;

    @BindView(R.id.purchase_change_pricein)
    EditText priceIn;

    @BindView(R.id.purchase_change_dprovidercode)
    TextView providerCode;

    @BindView(R.id.purchase_change_dprovidername)
    TextView providerName;

    @BindView(R.id.purchase_change_dproviderno)
    TextView providerNo;

    @BindView(R.id.purchase_change_commit)
    Button purchaseChangeCommit;
    ArrayList<String> result = new ArrayList();
    SharedPreferences sharedPreferences;

    @BindView(R.id.purchase_change_dshopname)
    TextView shopName;

    @BindView(R.id.purchase_change_dshopno)
    TextView shopNo;

    @BindView(R.id.purchase_change_dspec)
    TextView spec;

    @BindView(R.id.purchase_change_dthingcode)
    TextView thingCode;

    @BindView(R.id.purchase_change_dthingstate)
    TextView thingState;

    @BindView(R.id.purchase_change_dthingtype)
    TextView thingType;

    @BindView(R.id.purchase_change_dunitname)
    TextView unitName;
    @BindView(R.id.change_operation)
    Spinner changeOperation;

    /**
     * Activity声明周期，接收Intent中的参数，并放置在页面控件上
     *
     * @param paramBundle
     */
    @Override
    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.purchase_change);
        ButterKnife.bind(this);
        PurchaseBean localPurchase = (PurchaseBean) getIntent().getSerializableExtra("purchase");
        this.sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        this.priceIn.setHint(localPurchase.getDpricein());
        this.thingCode.setText(localPurchase.getDthingcode());
        this.spec.setText(localPurchase.getDspec());
        this.pack.setText(localPurchase.getDpack());
        this.unitName.setText(localPurchase.getDunitname());
        this.providerCode.setText(localPurchase.getDprovidercode());
        this.providerNo.setText(localPurchase.getDproviderno());
        this.code.setText(localPurchase.getDcode());
        this.name.setText(localPurchase.getDname());
        this.dateInput.setText(localPurchase.getDdateinput());
        this.price.setText(localPurchase.getDprice());
        this.masterBarcode.setText(localPurchase.getDmasterbarcode());
        this.shopName.setText(localPurchase.getDshopname());
        this.kindName.setText(localPurchase.getDkindname());
        this.thingState.setText(localPurchase.getDthingstate());
        this.providerName.setText(localPurchase.getDprovidername());
        this.num.setText(localPurchase.getDnum());
        this.thingType.setText(localPurchase.getDthingtype());
        this.shopNo.setText(localPurchase.getDshopno());
        this.purchaseChangeCommit.setOnClickListener(this);
        changeOperation.setOnItemSelectedListener(this);
    }

    private void checkPermission() {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("DWORKERNO", "0000");
        MyAsyncClient.doPost(new MyUrl().getFindAllQX(), localRequestParams, new MyResponseHandler(PurchaseChange.this) {
            @Override
            public void success(JSONObject jsonObject) {
                JSONArray localJSONArray = jsonObject.optJSONArray("data");
                Log.i("checkPermission", String.valueOf(localJSONArray));
                if (!jsonObject.optString("code").equals("200")) {
                    Toast.makeText(PurchaseChange.this, "您没有此权限", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String[] arrayOfString = new String[localJSONArray.length()];
                boolean[] arrayOfBoolean = new boolean[localJSONArray.length()];
                for (int i = 0; i < localJSONArray.length(); i++) {
                    arrayOfString[i] = localJSONArray.optString(i);
                    arrayOfBoolean[i] = false;
                }
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(PurchaseChange.this);
                localBuilder.setTitle("请选择要更改的商铺");
                localBuilder.setMultiChoiceItems(arrayOfString, arrayOfBoolean, new OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt, boolean paramBoolean) {
                        if (paramBoolean == true) {
                            PurchaseChange.this.result.add(arrayOfString[paramInt]);
                            return;
                        }
                        for (int i = 0; i < PurchaseChange.this.result.size(); i++) {
                            for (int j = 1; j < result.size(); i++){
                                if (result.get(i).equals(result.get(j))){
                                    PurchaseChange.this.result.remove(i);
                                }
                            }
                        }
                    }
                });
                localBuilder.setPositiveButton("确定", new OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        String str = String.valueOf(PurchaseChange.this.result);
                        PurchaseChange.this.purchaseChangeAsync(str);
                    }
                });
                localBuilder.setNegativeButton("取消", new OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
                localBuilder.show();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }

        });
    }

    private void purchaseChangeAsync(String paramString) {
        String url = "";
        if (changeOperation.getSelectedItem().toString().equals("进价调整")){
            url = new MyUrl().getOrderjj();
        }else {
            url = new MyUrl().getOrderls();
        }
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("DBARCODE", this.masterBarcode.getText().toString());
        localRequestParams.put("moneyin", this.priceIn.getText().toString());
        localRequestParams.put("moneyout", this.price.getText().toString());
        localRequestParams.put("DUNITNAME", this.unitName.getText().toString());
        localRequestParams.put("json", paramString.substring(1,paramString.length() - 1));
        localRequestParams.put("DSHOPNO","0000");
        MyAsyncClient.doPost(url, localRequestParams, new MyResponseHandler(PurchaseChange.this) {
            @Override
            public void success(JSONObject jsonObject) {
                if (jsonObject.optInt("code") == 200) {
                    System.out.println(jsonObject);
                    Toast.makeText(PurchaseChange.this, "修改成功!", Toast.LENGTH_SHORT).show();
                    PurchaseChange.this.finish();
                    return;
                }
                Toast.makeText(PurchaseChange.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    @Override
    public void onClick(View paramView) {
        if (paramView.getId() != R.id.purchase_change_commit)
            return;
        if ((!this.priceIn.getText().toString().trim().equals("")) && (!this.priceIn.getText().toString().trim().equals(null))) {
            checkPermission();
            return;
        }
        Toast.makeText(this, "进价不能为空!", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView selectedTv = (TextView) view;
        selectedTv.setTextSize(24);
        selectedTv.setGravity(Gravity.CENTER_HORIZONTAL);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.PurchaseChange
 * JD-Core Version:    0.6.0
 */