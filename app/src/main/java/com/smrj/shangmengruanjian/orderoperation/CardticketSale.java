package com.smrj.shangmengruanjian.orderoperation;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.adapter.InsertHYCardAdapter;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.bean.CardTicketInsertBean;
import com.smrj.shangmengruanjian.util.MyUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardticketSale extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {

    @BindView(R.id.order_card_ticket_operation)
    TextView orderCardTicketOperation;
    @BindView(R.id.order_card_ticket_generate)
    Button orderCardTicketGenerate;
    @BindView(R.id.order_card_ticket_list)
    ListView orderCardTicketList;
    @BindView(R.id.order_card_ticket_commit)
    Button orderCardTicketCommit;
    @BindView(R.id.card_ticket_name)
    EditText cardTicketName;
    @BindView(R.id.card_ticket_phone)
    EditText cardTicketPhone;
    private String TAG = "CARDTICKETSALE";
    private ArrayList<CardTicketInsertBean> cardTicketInsertBeans = new ArrayList<>();
    private InsertHYCardAdapter insertHYCardAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_ticket_sale);
        ButterKnife.bind(this);
        orderCardTicketGenerate.setOnClickListener(this);
        orderCardTicketCommit.setOnClickListener(this);
        insertHYCardAdapter = new InsertHYCardAdapter(cardTicketInsertBeans);
        orderCardTicketList.setAdapter(insertHYCardAdapter);
        orderCardTicketList.setOnItemLongClickListener(this);
    }

    private void findAllData(){
        RequestParams requestParams = new RequestParams();
        requestParams.put("tableName","KCARD_HYINFO");
        requestParams.put("vaules"," and dmobile = '" + cardTicketPhone.getText().toString()+"'");
        MyAsyncClient.doPost(new MyUrl().getFindAllData(), requestParams, new MyResponseHandler(CardticketSale.this) {
            @Override
            public void success(JSONObject jsonObject) {
                Log.d(TAG, "success: " + jsonObject);
                if (jsonObject.optInt("code") == 201){
                    CardTicketInsertBean cardTicketInsertBean = new CardTicketInsertBean();
                    cardTicketInsertBean.setHYNAME(cardTicketName.getText().toString());
                    cardTicketInsertBean.setHYPHONE(cardTicketPhone.getText().toString());
                    cardTicketInsertBeans.add(cardTicketInsertBean);
                    insertHYCardAdapter.notifyDataSetChanged();
                    cardTicketName.setText("");
                    cardTicketPhone.setText("");
                }else {
                    Toast.makeText(CardticketSale.this,"此会员已被注册",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    private void insertHYCard(){
        RequestParams requestParams = new RequestParams();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < cardTicketInsertBeans.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("phone",cardTicketInsertBeans.get(i).getHYPHONE());
                jsonObject.put("dnamehy",cardTicketInsertBeans.get(i).getHYNAME());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        requestParams.put("yhs",jsonArray);
        MyAsyncClient.doPost(new MyUrl().getInsertHYCard(), requestParams, new MyResponseHandler(CardticketSale.this) {
            @Override
            public void success(JSONObject jsonObject) {
                Log.d(TAG, "success: " + jsonObject);
                if (jsonObject.optInt("code") == 200){
                    cardTicketName.setText("");
                    cardTicketPhone.setText("");
                    cardTicketInsertBeans.clear();
                    insertHYCardAdapter.notifyDataSetChanged();
                    Toast.makeText(CardticketSale.this, "已成功创建会员！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.order_card_ticket_generate:
                if (!cardTicketPhone.getText().toString().matches("^1[3456789]\\d{9}$")){
                    Toast.makeText(CardticketSale.this,"手机号不符合规则",Toast.LENGTH_SHORT).show();
                }else {
                    if (!cardTicketName.getText().toString().trim().equals("")){
                        if (!cardTicketPhone.getText().toString().trim().equals("")){
                            findAllData();
                        }else {
                            Toast.makeText(CardticketSale.this,"会员手机号不能为空",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(CardticketSale.this,"会员姓名不能为空",Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.order_card_ticket_commit:
                if (cardTicketInsertBeans.size() > 0){
                    insertHYCard();
                }else {
                    Toast.makeText(CardticketSale.this,"请先添加会员",Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        TextView editText = view.findViewById(R.id.insert_hycard_item_phone);
        for (int i = 0; i < cardTicketInsertBeans.size(); i++) {
            if (cardTicketInsertBeans.get(i).getHYPHONE().equals(editText.getText().toString())){
                cardTicketInsertBeans.remove(i);
            }
        }
        insertHYCardAdapter.notifyDataSetChanged();
        return false;
    }
}
