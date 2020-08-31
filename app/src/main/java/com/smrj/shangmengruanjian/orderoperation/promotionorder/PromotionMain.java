package com.smrj.shangmengruanjian.orderoperation.promotionorder;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.orderoperation.OrderGoods;
import com.smrj.shangmengruanjian.util.MyUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PromotionMain extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.order_promotion_type)
    Spinner orderPromotionType;
    @BindView(R.id.order_promotion_place)
    Spinner orderPromotionPlace;
    @BindView(R.id.order_promotion_ismore)
    EditText orderPromotionIsmore;
    @BindView(R.id.order_promotion_begin)
    EditText orderPromotionBegin;
    @BindView(R.id.order_promotion_end)
    EditText orderPromotionEnd;
    @BindView(R.id.order_promotion_changeprice)
    CheckBox orderPromotionChangeprice;
    @BindView(R.id.order_promotion_before)
    EditText orderPromotionBefore;
    @BindView(R.id.order_promotion_after)
    EditText orderPromotionAfter;
    @BindView(R.id.order_promotion_barcode)
    EditText orderPromotionBarcode;
    @BindView(R.id.order_promotion_barbtn)
    Button orderPromotionBarbtn;
    @BindView(R.id.order_promotion_morecheck)
    CheckBox orderPromotionMorecheck;
    @BindView(R.id.order_promotion_begindate)
    EditText orderPromotionBegindate;
    @BindView(R.id.order_promotion_enddate)
    EditText orderPromotionEnddate;
    @BindView(R.id.order_promotion_commit)
    TextView orderPromotionCommit;
    @BindView(R.id.order_promotion_list)
    ListView orderPromotionList;
    private Map<String, String> placeMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_promotion);
        ButterKnife.bind(this);
        orderPromotionType.setOnItemSelectedListener(this);
        orderPromotionBarbtn.setOnClickListener(this);
        orderPromotionChangeprice.setOnCheckedChangeListener(this);
        orderPromotionMorecheck.setOnCheckedChangeListener(this);
        orderPromotionBegindate.setOnClickListener(this);
        orderPromotionEnddate.setOnClickListener(this);
        orderPromotionBegin.setOnClickListener(this);
        orderPromotionEnd.setOnClickListener(this);
        orderPromotionCommit.setOnClickListener(this);
        orderPromotionIsmore.setOnClickListener(this);
        findAllData();
    }

    private void findAllData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("tableName", "F_SHOP");
        MyAsyncClient.doPost(new MyUrl().getFindAllData(), requestParams, new MyResponseHandler(PromotionMain.this) {
            @Override
            public void success(JSONObject jsonObject) {
                if (jsonObject.optInt("code") == 200) {
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    String[] items = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        items[i] = jsonArray.optJSONObject(i).optString("DSHOPNAME");
                        placeMap.put(jsonArray.optJSONObject(i).optString("DSHOPNO"), jsonArray.optJSONObject(i).optString("DSHOPNAME"));
                    }
                    ArrayAdapter<String> placeAdapter = new ArrayAdapter<String>(PromotionMain.this, android.R.layout.simple_dropdown_item_1line, items);
                    orderPromotionPlace.setAdapter(placeAdapter);
                }
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView textView = (TextView) view;
        textView.setTextSize(24);
        textView.setGravity(Gravity.CENTER);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_promotion_begindate:
                EditText beginDateText = (EditText) v;
                datePickerMethod(beginDateText);
                break;
            case R.id.order_promotion_enddate:
                EditText endDateText = (EditText) v;
                datePickerMethod(endDateText);
                break;
            case R.id.order_promotion_begin:
                EditText beginText = (EditText) v;
                timePickerMethod(beginText);
                break;
            case R.id.order_promotion_end:
                EditText endText = (EditText) v;
                timePickerMethod(endText);
                break;
            case R.id.order_promotion_ismore:

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getText().toString().equals("是否多店")) {
            if (isChecked) {
                orderPromotionIsmore.setEnabled(true);
            } else {
                orderPromotionIsmore.setEnabled(false);
            }
        }
        if (buttonView.getText().toString().equals("是否调进价")) {
            if (isChecked) {
                orderPromotionBefore.setEnabled(true);
                orderPromotionAfter.setEnabled(true);
            } else {
                orderPromotionBefore.setEnabled(false);
                orderPromotionAfter.setEnabled(false);
            }
        }
    }

    private void datePickerMethod(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(PromotionMain.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String monthResult = "";
                String dateResult = "";
                if (month < 10) {
                    monthResult = "0" + (month + 1);
                } else {
                    monthResult = String.valueOf(month + 1);
                }
                if (dayOfMonth < 10) {
                    dateResult = "0" + dayOfMonth;
                } else {
                    dateResult = String.valueOf(dayOfMonth);
                }
                editText.setText(year + "-" + monthResult + "-" + dateResult);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        editText.clearFocus();
        datePickerDialog.show();
    }

    private void timePickerMethod(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(PromotionMain.this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hourResult = "";
                String minuteResult = "";
                if (hourOfDay < 10) {
                    hourResult = "0" + hourOfDay;
                } else {
                    hourResult = String.valueOf(hourOfDay);
                }
                if (minute < 10) {
                    minuteResult = "0" + minute;
                } else {
                    minuteResult = String.valueOf(minute);
                }
                editText.setText(hourResult + ":" + minuteResult + ":" + "000");
            }
        }, 0, 0, true);
        timePickerDialog.show();
    }
}
