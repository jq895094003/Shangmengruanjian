package com.smrj.shangmengruanjian.dateReport;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.adapter.GuestReportAdapter;
import com.smrj.shangmengruanjian.adapter.MoneierReportAdapter;
import com.smrj.shangmengruanjian.adapter.ProductTypeReportAdapter;
import com.smrj.shangmengruanjian.adapter.RealTypeReportAdapter;
import com.smrj.shangmengruanjian.adapter.ShoppeTypeReportAdapter;
import com.smrj.shangmengruanjian.adapter.SingleReportAdapter;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.bean.GuestTypeBean;
import com.smrj.shangmengruanjian.bean.MoneierTypeReportBean;
import com.smrj.shangmengruanjian.bean.ProductTypeReportBean;
import com.smrj.shangmengruanjian.bean.RealTypeReportBean;
import com.smrj.shangmengruanjian.bean.ShoppeTypeReportBean;
import com.smrj.shangmengruanjian.bean.SingleTypeReportBean;
import com.smrj.shangmengruanjian.util.MyUrl;
import com.smrj.shangmengruanjian.zxing.android.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportInfo extends AppCompatActivity implements OnClickListener {
    /**
     * 50-73行，声明并实例化页面控件、所需工具，使用ButterKnife监听
     */
    GuestReportAdapter guestReportAdapter;
    ArrayList<GuestTypeBean> guestTypeBeans = new ArrayList();
    int indes = 1;

    MoneierReportAdapter moneierReportAdapter;
    ArrayList<MoneierTypeReportBean> moneierTypeReportBeans = new ArrayList();

    ProductTypeReportAdapter productTypeReportAdapter;
    ArrayList<ProductTypeReportBean> productTypeReportBeans = new ArrayList();

    RealTypeReportAdapter realTypeReportAdapter;
    ArrayList<RealTypeReportBean> realTypeReportBeans = new ArrayList();


    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;
    public static final int RESULT_OK = -1;
    @BindView(R.id.report_btn)
    Button reportBtn;

    @BindView(R.id.btn_scan)
    Button btn_scan;

    @BindView(R.id.report_edt)
    EditText reportEdit;
    @BindView(R.id.report_date_begin)
    EditText reportDateBegin;
    @BindView(R.id.report_date_stop)
    EditText reportDateStop;
    @BindView(R.id.date_linear)
    LinearLayout dateLinear;

    @BindView(R.id.report_info)
    PullToRefreshListView reportInfoList;
    ShoppeTypeReportAdapter shoppeTypeReportAdapter;
    ArrayList<ShoppeTypeReportBean> shoppeTypeReportBeans = new ArrayList();
    SingleReportAdapter singleReportAdapter;
    ArrayList<SingleTypeReportBean> singleTypeReportBeans = new ArrayList();
    String type = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SharedPreferences sharedPreferences;

    private LinearLayout linearLayout;

    private void findViews() {
        linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
    }


    protected View createView(String text) {
        removeView();
        TextView textView = new EditText(this);
        textView.setText(text);
        textView.setFocusable(false);
        textView.setFocusableInTouchMode(false);
        return textView;
    }

    private void removeView() {
        int count = linearLayout.getChildCount();
        if (count - 1 > 0) {
            linearLayout.removeViewAt(count - 1);
        }
    }

    /**
     * Activity声明周期，页面创建方法，根据传入的参数进行不通的操作
     *
     * @param paramBundle
     */
    @Override
    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.report_info);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        this.type = getIntent().getStringExtra("type");
        this.reportBtn.setOnClickListener(this);
        this.btn_scan.setOnClickListener(this);


        reportDateBegin.setText(sdf.format(new Date()));
        reportDateStop.setOnClickListener(this);
        reportDateStop.setText(sdf.format(new Date()));
        reportDateBegin.setOnClickListener(this);

        findViews();

        if (this.type.equals("单品")) {
            this.reportEdit.setHint("请输入商品条码");
            this.singleReportAdapter = new SingleReportAdapter(this.singleTypeReportBeans);
            this.reportInfoList.setAdapter(singleReportAdapter);
            this.reportInfoList.setMode(PullToRefreshBase.Mode.BOTH);
            this.reportInfoList.setOnRefreshListener(this.mListViewOnRefreshListener1);
            singleTypeReport(String.valueOf(1));
            return;
        }
        if (this.type.equals("类型")) {
            this.reportEdit.setHint("请输入最小类别名称");
            this.productTypeReportAdapter = new ProductTypeReportAdapter(productTypeReportBeans);
            this.reportInfoList.setAdapter(this.productTypeReportAdapter);
            this.reportInfoList.setMode(PullToRefreshBase.Mode.BOTH);
            this.reportInfoList.setOnRefreshListener(this.mListViewOnRefreshListener1);
            productTypeReport(String.valueOf(1));
            return;
        }
        if (this.type.equals("客流")) {
            this.reportEdit.setHint("请输入门店编号");
            guestTypeReport(String.valueOf(1));
            this.guestReportAdapter = new GuestReportAdapter(this.guestTypeBeans);
            this.reportInfoList.setAdapter(this.guestReportAdapter);
            this.reportInfoList.setMode(PullToRefreshBase.Mode.BOTH);
            this.reportInfoList.setOnRefreshListener(this.mListViewOnRefreshListener1);
            return;
        }
        if (this.type.equals("收款员")) {
            this.reportEdit.setHint("请输入收款员编号");
            moneierTypeReport(String.valueOf(1));
            this.moneierReportAdapter = new MoneierReportAdapter(this.moneierTypeReportBeans);
            this.reportInfoList.setAdapter(this.moneierReportAdapter);
            this.reportInfoList.setMode(PullToRefreshBase.Mode.BOTH);
            this.reportInfoList.setOnRefreshListener(this.mListViewOnRefreshListener1);
            return;
        }
        if (this.type.equals("专柜")) {
            reportEdit.setVisibility(View.GONE);
            this.reportEdit.setHint("请输入专柜名称");
            this.shoppeTypeReportAdapter = new ShoppeTypeReportAdapter(shoppeTypeReportBeans);
            this.reportInfoList.setAdapter(this.shoppeTypeReportAdapter);
            this.reportInfoList.setMode(PullToRefreshBase.Mode.BOTH);
            this.reportInfoList.setOnRefreshListener(this.mListViewOnRefreshListener1);
            shoppeTypeReport(String.valueOf(1));
            return;
        }
        if (this.type.equals("实时")) {
            dateLinear.setVisibility(View.GONE);
            this.reportEdit.setHint("请输入商品编码");
            this.realTypeReportAdapter = new RealTypeReportAdapter(this.realTypeReportBeans);
            this.reportInfoList.setAdapter(this.realTypeReportAdapter);
            this.reportInfoList.setMode(PullToRefreshBase.Mode.BOTH);
            this.reportInfoList.setOnRefreshListener(this.mListViewOnRefreshListener1);
            realTypeReport(String.valueOf(1));
        }
    }

    /**
     * 列表控件上拉下拉操作
     * 根据传入的参数来更新不同的适配器
     */
    private PullToRefreshBase.OnRefreshListener2<ListView> mListViewOnRefreshListener1 = new PullToRefreshBase.OnRefreshListener2<ListView>() {
        /**
         * 下拉方法
         * @param pullToRefreshBase
         */
        @Override
        public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
            reportInfoList.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (ReportInfo.this.type.equals("单品")) {
                        ReportInfo.this.singleTypeReportBeans.clear();
                        ReportInfo.this.singleReportAdapter.notifyDataSetChanged();
                        ReportInfo.this.indes = 1;
                        ReportInfo.this.singleTypeReport(String.valueOf(ReportInfo.this.indes));
                    } else if (ReportInfo.this.type.equals("类型")) {
                        ReportInfo.this.productTypeReportBeans.clear();
                        ReportInfo.this.productTypeReportAdapter.notifyDataSetChanged();
                        ReportInfo.this.indes = 1;
                        ReportInfo.this.productTypeReport(String.valueOf(ReportInfo.this.indes));
                    } else if (ReportInfo.this.type.equals("客流")) {
                        ReportInfo.this.guestTypeBeans.clear();
                        ReportInfo.this.guestReportAdapter.notifyDataSetChanged();
                        ReportInfo.this.indes = 1;
                        ReportInfo.this.guestTypeReport(String.valueOf(ReportInfo.this.indes));
                    } else if (ReportInfo.this.type.equals("收款员")) {
                        ReportInfo.this.moneierTypeReportBeans.clear();
                        ReportInfo.this.moneierReportAdapter.notifyDataSetChanged();
                        ReportInfo.this.indes = 1;
                        ReportInfo.this.moneierTypeReport(String.valueOf(ReportInfo.this.indes));
                    } else if (ReportInfo.this.type.equals("专柜")) {
                        ReportInfo.this.shoppeTypeReportBeans.clear();
                        ReportInfo.this.shoppeTypeReportAdapter.notifyDataSetChanged();
                        ReportInfo.this.indes = 1;
                        ReportInfo.this.shoppeTypeReport(String.valueOf(ReportInfo.this.indes));
                    } else if (ReportInfo.this.type.equals("实时")) {
                        ReportInfo.this.realTypeReportBeans.clear();
                        ReportInfo.this.realTypeReportAdapter.notifyDataSetChanged();
                        ReportInfo.this.indes = 1;
                        ReportInfo.this.realTypeReport(String.valueOf(ReportInfo.this.indes));
                    }
                    ReportInfo.this.reportInfoList.onRefreshComplete();
                }
            }, 500);
        }

        /**
         * 上拉方法
         * @param pullToRefreshBase
         */
        @Override
        public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
            reportInfoList.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (ReportInfo.this.type.equals("单品")) {
                        ReportInfo.this.indes = (1 + ReportInfo.this.indes);
                        ReportInfo.this.singleTypeReport(String.valueOf(ReportInfo.this.indes));
                    } else if (ReportInfo.this.type.equals("类型")) {
                        ReportInfo.this.indes = (1 + ReportInfo.this.indes);
                        ReportInfo.this.productTypeReport(String.valueOf(ReportInfo.this.indes));
                    } else if (ReportInfo.this.type.equals("客流")) {
                        ReportInfo.this.indes = (1 + ReportInfo.this.indes);
                        ReportInfo.this.guestTypeReport(String.valueOf(ReportInfo.this.indes));
                    } else if (ReportInfo.this.type.equals("收款员")) {
                        ReportInfo.this.indes = (1 + ReportInfo.this.indes);
                        ReportInfo.this.moneierTypeReport(String.valueOf(ReportInfo.this.indes));
                    } else if (ReportInfo.this.type.equals("专柜")) {
                        ReportInfo.this.indes = (1 + ReportInfo.this.indes);
                        ReportInfo.this.shoppeTypeReport(String.valueOf(ReportInfo.this.indes));
                    } else if (ReportInfo.this.type.equals("实时")) {
                        ReportInfo.this.indes = (1 + ReportInfo.this.indes);
                        ReportInfo.this.realTypeReport(String.valueOf(ReportInfo.this.indes));
                    }
                    reportInfoList.onRefreshComplete();
                }
            }, 500);
        }
    };

    /**
     * 客流数据查询方法
     * 如果返回值code为200则加载数据，加载到列表控件中
     * 如果不为200则弹出后台提示
     *
     * @param paramString
     */
    private void guestTypeReport(String paramString) {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("page", paramString);
        if (!this.reportEdit.getText().toString().equals("")) {
            localRequestParams.put("condition", "DSHOPNO = '" + this.reportEdit.getText().toString().trim() + "' AND DDATE_S >= '"
                    + reportDateBegin.getText().toString() + "' AND DDATE_S <= '" + reportDateStop.getText().toString() + "'");
        } else {
            localRequestParams.put("condition", this.reportEdit.getText().toString().trim() + " DDATE_S >= '"
                    + reportDateBegin.getText().toString() + "' AND DDATE_S <= '" + reportDateStop.getText().toString() + "'");
        }
        MyAsyncClient.doPost(new MyUrl().getFindSaleCustomerwater(), localRequestParams, new MyResponseHandler(ReportInfo.this) {
            @Override
            public void success(JSONObject jsonObject) {
                int i = 0;
                JSONObject dataJsonObject = jsonObject.optJSONObject("data");
                boolean bool = jsonObject.optString("code").equals("200");
                if (bool) {
                    if (dataJsonObject.optInt("total") > 0) {
                        JSONArray localJSONArray = dataJsonObject.optJSONArray("list");
                        while (i < localJSONArray.length() - 1) {
                            GuestTypeBean localGuestTypeBean = new GuestTypeBean();
                            localGuestTypeBean.setDDATE_S(localJSONArray.optJSONObject(i).optString("DDATE_S"));
                            localGuestTypeBean.setDPRICECUSTOMER(String.valueOf(localJSONArray.optJSONObject(i).optDouble("DPRICECUSTOMER")));
                            localGuestTypeBean.setDSHOPNO(localJSONArray.optJSONObject(i).optString("DSHOPNO"));
                            localGuestTypeBean.setDTICKETNO(localJSONArray.optJSONObject(i).optString("DTICKETNO"));
                            localGuestTypeBean.setDWATER(String.valueOf(localJSONArray.optJSONObject(i).optInt("DWATER")));
                            ReportInfo.this.guestTypeBeans.add(localGuestTypeBean);
                            i++;
                        }

                        JSONObject sum = localJSONArray.optJSONObject(localJSONArray.length() - 1);
                        try {
                            Double ttlDwater = sum.getDouble("DWATER");
                            linearLayout.addView(createView("整体客流量 ：" + String.format("%.0f", ttlDwater) + " 人"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ReportInfo.this.guestReportAdapter.notifyDataSetChanged();
                        return;
                    }
                } else {
                    Toast.makeText(ReportInfo.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }

        });
    }

    /**
     * 收银员数据查询方法
     * 如果返回值code为200则加载数据，加载到列表控件中
     * 如果不为200则弹出后台提示
     *
     * @param paramString
     */
    private void moneierTypeReport(String paramString) {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("page", paramString);
        if (!this.reportEdit.getText().toString().equals("")) {
            localRequestParams.put("condition", "DWORKERNO = '" + this.reportEdit.getText().toString().trim() + "' AND DDATE_S >= '"
                    + reportDateBegin.getText().toString() + "' AND DDATE_S <= '" + reportDateStop.getText().toString() + "'");
        } else {
            localRequestParams.put("condition", this.reportEdit.getText().toString().trim() + " DDATE_S >= '"
                    + reportDateBegin.getText().toString() + "' AND DDATE_S <= '" + reportDateStop.getText().toString() + "'");
        }
        MyAsyncClient.doPost(new MyUrl().getFindSaleSaler(), localRequestParams, new MyResponseHandler(ReportInfo.this) {
            @Override
            public void success(JSONObject jsonObject) {

                int i = 0;
                JSONObject dataJsonObject = jsonObject.optJSONObject("data");
                boolean bool = jsonObject.optString("code").equals("200");
                if (bool) {
                    if (dataJsonObject.optInt("total") > 0) {
                        JSONArray localJSONArray = dataJsonObject.optJSONArray("list");
                        while (i < localJSONArray.length() - 1) {
                            MoneierTypeReportBean localMoneierTypeReportBean = new MoneierTypeReportBean();
                            localMoneierTypeReportBean.setDDATE_S(localJSONArray.optJSONObject(i).optString("DDATE_S"));
                            localMoneierTypeReportBean.setDMONEY_SS(String.valueOf(localJSONArray.optJSONObject(i).optDouble("DMONEY_SS")));
                            localMoneierTypeReportBean.setDMONEY_YS(String.valueOf(localJSONArray.optJSONObject(i).optDouble("DMONEY_YS")));
                            localMoneierTypeReportBean.setDTICKETNO(localJSONArray.optJSONObject(i).optString("DTICKETNO"));
                            localMoneierTypeReportBean.setDWORKERNAME(localJSONArray.optJSONObject(i).optString("DWORKERNAME"));
                            ReportInfo.this.moneierTypeReportBeans.add(localMoneierTypeReportBean);
                            i++;
                        }
                        JSONObject sum = localJSONArray.optJSONObject(localJSONArray.length() - 1);
                        try {
                            Double DMONEYSS = sum.getDouble("DMONEYSS");//实收
                            Double DMONEYZK = sum.getDouble("DMONEYZK");//折扣
                            Double DMONEYTH = sum.getDouble("DMONEYTH");//退货
                            Double DMONEYSSXJ = sum.getDouble("DMONEYSSXJ");//现金
                            Double DMONEYSSCZK = sum.getDouble("DMONEYSSCZK");//储值卡
                            Double DMONEYSSWX = sum.getDouble("DMONEYSSWX");//微信
                            Double DMONEYSSZFB = sum.getDouble("DMONEYSSZFB");//支付宝
                            linearLayout.addView(createView("总实收额 ：" + String.format("%.2f", DMONEYSS) + " 元 \n"
                                    + "总折扣额 ：" + String.format("%.2f", DMONEYZK) + " 元 \n"
                                    + "总退款额 ：" + String.format("%.2f", DMONEYTH) + " 元 \n"
                                    + "总现金额 ：" + String.format("%.2f", DMONEYSSXJ) + " 元 \n"
                                    + "总微信额 ：" + String.format("%.2f", DMONEYSSWX) + " 元 \n"
                                    + "总支付宝额 ：" + String.format("%.2f", DMONEYSSZFB) + " 元\n"
                                    + "总储值卡额 ：" + String.format("%.2f", DMONEYSSCZK) + " 元 "));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ReportInfo.this.moneierReportAdapter.notifyDataSetChanged();
                        return;
                    }
                } else {
                    Toast.makeText(ReportInfo.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 货类数据查询方法
     * 如果返回值code为200则加载数据，加载到列表控件中
     * 如果不为200则弹出后台提示
     *
     * @param paramString
     */
    private void productTypeReport(String paramString) {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("page", paramString);
        if (!this.reportEdit.getText().toString().equals("")) {
            localRequestParams.put("condition", "DKINDNAME LIKE '%" + this.reportEdit.getText().toString().trim() + "%' AND DDATE_S >= '"
                    + reportDateBegin.getText().toString() + "' AND DDATE_S <= '" + reportDateStop.getText().toString() + "'");
        } else {
            localRequestParams.put("condition", this.reportEdit.getText().toString().trim() + " DDATE_S >= '"
                    + reportDateBegin.getText().toString() + "' AND DDATE_S <= '" + reportDateStop.getText().toString() + "'");
        }
        MyAsyncClient.doPost(new MyUrl().getFindSaleKind(), localRequestParams, new MyResponseHandler(ReportInfo.this) {
            @Override
            public void success(JSONObject jsonObject) {
                int i = 0;
                JSONObject dataJsonObject = jsonObject.optJSONObject("data");
                boolean bool = jsonObject.optString("code").equals("200");
                if (bool) {
                    if (dataJsonObject.optInt("total") > 0) {
                        JSONArray localJSONArray = dataJsonObject.optJSONArray("list");
                        while (i < localJSONArray.length() - 1) {
                            ProductTypeReportBean localProductTypeReportBean = new ProductTypeReportBean();
                            localProductTypeReportBean.setDDATE_S(localJSONArray.optJSONObject(i).optString("DDATE_S"));
                            localProductTypeReportBean.setDDEPOTNO(localJSONArray.optJSONObject(i).optString("DDEPOTNO"));
                            localProductTypeReportBean.setDKINDNAME(localJSONArray.optJSONObject(i).optString("DKINDNAME"));
                            localProductTypeReportBean.setDKINDNO(localJSONArray.optJSONObject(i).optString("DKINDNO"));
                            localProductTypeReportBean.setDMONEY_SS(String.valueOf(localJSONArray.optJSONObject(i).optDouble("DMONEY_YS")));
                            localProductTypeReportBean.setDMONEY_YS(String.valueOf(localJSONArray.optJSONObject(i).optDouble("DMONEY_SS")));
                            localProductTypeReportBean.setDSHOPNO(localJSONArray.optJSONObject(i).optString("DSHOPNO"));
                            localProductTypeReportBean.setDTICKETNO(localJSONArray.optJSONObject(i).optString("DTICKETNO"));
                            ReportInfo.this.productTypeReportBeans.add(localProductTypeReportBean);
                            i++;
                        }
                        JSONObject sum = localJSONArray.optJSONObject(localJSONArray.length() - 1);
                        try {
                            Double ttlMoney = sum.getDouble("TTLMONEY");
                            Double ttlDml = sum.getDouble("TTLDML");
                            Double ttlDwater = sum.getDouble("TTLDWATER");
                            linearLayout.addView(createView("总计实收额 ：" + String.format("%.2f", ttlMoney) + " 元 \n"
                                    + "总计毛利额 ：" + String.format("%.2f", ttlDml) + " 元 \n"
                                    + "总计客流量 ：" + String.format("%.2f", ttlDwater) + " 人"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ReportInfo.this.productTypeReportAdapter.notifyDataSetChanged();
                        return;
                    } else {
                        Toast.makeText(ReportInfo.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 实时数据查询方法
     * 如果返回值code为200则加载数据，加载到列表控件中
     * 如果不为200则弹出后台提示
     *
     * @param paramString
     */
    private void realTypeReport(String paramString) {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("page", paramString);
        if (!this.reportEdit.getText().toString().equals("")) {
            localRequestParams.put("condition", "DBARCODE LIKE '%" + this.reportEdit.getText().toString().trim() + "%'");
        } else {
            localRequestParams.put("condition", this.reportEdit.getText().toString().trim());
        }
        MyAsyncClient.doPost(new MyUrl().getFindFXStorerealtime(), localRequestParams, new MyResponseHandler(ReportInfo.this) {
            @Override
            public void success(JSONObject jsonObject) {
                JSONArray localJSONArray = jsonObject.optJSONArray("data");
                boolean bool = jsonObject.optString("code").equals("200");
                int i = 0;
                if (bool) {
                    while (i < localJSONArray.length()) {
                        Log.i("realType", String.valueOf(localJSONArray.optJSONObject(i)));
                        RealTypeReportBean localRealTypeReportBean = new RealTypeReportBean();
                        localRealTypeReportBean.setDBARCODE(localJSONArray.optJSONObject(i).optString("DBARCODE"));
                        localRealTypeReportBean.setDDEPOTNO(localJSONArray.optJSONObject(i).optString("DDEPOTNO"));
                        localRealTypeReportBean.setDKINDNAME(localJSONArray.optJSONObject(i).optString("DKINDNAME"));
                        localRealTypeReportBean.setDNAME(localJSONArray.optJSONObject(i).optString("DNAME"));
                        localRealTypeReportBean.setDNUM(String.valueOf(localJSONArray.optJSONObject(i).optDouble("DNUM")));
                        localRealTypeReportBean.setDPROVIDERNAME(localJSONArray.optJSONObject(i).optString("DPROVIDERNAME"));
                        localRealTypeReportBean.setDSHOPNO(localJSONArray.optJSONObject(i).optString("DSHOPNO"));
                        ReportInfo.this.realTypeReportBeans.add(localRealTypeReportBean);
                        i++;
                    }
                    ReportInfo.this.realTypeReportAdapter.notifyDataSetChanged();
                    return;
                }
                Toast.makeText(ReportInfo.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 专柜数据查询方法
     * 如果返回值code为200则加载数据，加载到列表控件中
     * 如果不为200则弹出后台提示
     *
     * @param paramString
     */
    private void shoppeTypeReport(String paramString) {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("page", paramString);
        if (!this.reportEdit.getText().toString().equals("")) {
            localRequestParams.put("condition", "DSHOPNO = '" + sharedPreferences.getString("shopno", "") + "' AND DDATE_S >= '"
                    + reportDateBegin.getText().toString() + "' AND DDATE_S <= '" + reportDateStop.getText().toString() + "'");
        } else {
            localRequestParams.put("condition", this.reportEdit.getText().toString().trim() + " DDATE_S >= '"
                    + reportDateBegin.getText().toString() + "' AND DDATE_S <= '" + reportDateStop.getText().toString() + "'");
        }
        MyAsyncClient.doPost(new MyUrl().getFindSaleArea(), localRequestParams, new MyResponseHandler(ReportInfo.this) {
            @Override
            public void success(JSONObject jsonObject) {
                int i = 0;
                JSONObject dataJsonObject = jsonObject.optJSONObject("data");
                boolean bool = jsonObject.optString("code").equals("200");
                if (bool) {
                    if (dataJsonObject.optInt("total") > 0) {
                        JSONArray localJSONArray = dataJsonObject.optJSONArray("list");
                        while (i < localJSONArray.length() - 1) {
                            ShoppeTypeReportBean localShoppeTypeReportBean = new ShoppeTypeReportBean();
                            localShoppeTypeReportBean.setDAREANAME(localJSONArray.optJSONObject(i).optString("DAREANAME"));
                            localShoppeTypeReportBean.setDAREANO(localJSONArray.optJSONObject(i).optString("DAREACODE"));
                            localShoppeTypeReportBean.setDDATE_S(localJSONArray.optJSONObject(i).optString("DDATE_S"));
                            localShoppeTypeReportBean.setDMONEY_SS(String.valueOf(localJSONArray.optJSONObject(i).optDouble("DMONEY_SS")));
                            localShoppeTypeReportBean.setDMONEY_YS(String.valueOf(localJSONArray.optJSONObject(i).optDouble("DMONEY_YS")));
                            localShoppeTypeReportBean.setDSHOPNO(localJSONArray.optJSONObject(i).optString("DSHOPNO"));
                            localShoppeTypeReportBean.setDTICKETNO(localJSONArray.optJSONObject(i).optString("DTICKETNO"));
                            ReportInfo.this.shoppeTypeReportBeans.add(localShoppeTypeReportBean);
                            i++;
                        }
                        JSONObject sum = localJSONArray.optJSONObject(localJSONArray.length() - 1);
                        try {
                            Double DMONEYSS = sum.getDouble("DMONEYSS");
                            Double DMONEYZK = sum.getDouble("DMONEYZK");
                            Double DML = sum.getDouble("DML");
                            linearLayout.addView(createView("总计实收额 ：" + String.format("%.2f", DMONEYSS) + " 元 \n"
                                    + "总计折扣额 ：" + String.format("%.2f", DMONEYZK) + " 元 \n"
                                    + "总计毛利额 ：" + String.format("%.2f", DML) + " 元 "));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        ReportInfo.this.shoppeTypeReportAdapter.notifyDataSetChanged();
                        return;
                    }
                } else {
                    Toast.makeText(ReportInfo.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }

    /**
     * 单品数据查询方法
     * 如果返回值code为200则加载数据，加载到列表控件中
     * 如果不为200则弹出后台提示
     *
     * @param paramString
     */
    private void singleTypeReport(String paramString) {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("page", paramString);
        if (!this.reportEdit.getText().toString().equals("")) {
            localRequestParams.put("condition", "DBARCODE LIKE '%" + this.reportEdit.getText().toString().trim() + "%' AND DDATE_S >= '"
                    + reportDateBegin.getText().toString() + "' AND DDATE_S <= '" + reportDateStop.getText().toString() + "'");
        } else {
            localRequestParams.put("condition", this.reportEdit.getText().toString().trim() + " DDATE_S >= '"
                    + reportDateBegin.getText().toString() + "' AND DDATE_S <= '" + reportDateStop.getText().toString() + "'");
        }
        System.out.println(reportEdit.getText().toString());
        MyAsyncClient.doPost(new MyUrl().getFindSaleThing(), localRequestParams, new MyResponseHandler(ReportInfo.this) {
            @Override
            public void success(JSONObject jsonObject) {
                int i = 0;
                JSONObject dataJsonObject = jsonObject.optJSONObject("data");
                boolean bool = jsonObject.optString("code").equals("200");
                if (bool) {
                    if (dataJsonObject.optInt("total") > 0) {
                        JSONArray localJSONArray = dataJsonObject.optJSONArray("list");
                        while (i < localJSONArray.length() - 1) {
                            SingleTypeReportBean localSingleTypeReportBean = new SingleTypeReportBean();
                            localSingleTypeReportBean.setDBARCODE(localJSONArray.optJSONObject(i).optString("DBARCODE"));
                            localSingleTypeReportBean.setDSHOPNO(localJSONArray.optJSONObject(i).optString("DSHOPNO"));
                            localSingleTypeReportBean.setDMONEY_SS(String.valueOf(localJSONArray.optJSONObject(i).optDouble("DMONEY_SS")));
                            localSingleTypeReportBean.setDMONEY_YS(String.valueOf(localJSONArray.optJSONObject(i).optDouble("DMONEY_YS")));
                            localSingleTypeReportBean.setDNAME(localJSONArray.optJSONObject(i).optString("DNAME"));
                            localSingleTypeReportBean.setDNUM(String.valueOf(localJSONArray.optJSONObject(i).optDouble("DNUM")));
                            localSingleTypeReportBean.setDUNITNAME(localJSONArray.optJSONObject(i).optString("DUNITNAME"));
                            localSingleTypeReportBean.setDDATE_S(localJSONArray.optJSONObject(i).optString("DDATE_S"));
                            ReportInfo.this.singleTypeReportBeans.add(localSingleTypeReportBean);
                            i++;
                        }
                        JSONObject sum = localJSONArray.optJSONObject(localJSONArray.length() - 1);
                        try {
                            Double ttlMoney = sum.getDouble("TTLMONEY");
                            Double ttlNum = sum.getDouble("TTLNUM");
                            linearLayout.addView(createView("总计实收额 ：" + String.format("%.2f", ttlMoney) + " 元 \n"
                                    + "总计销售量 ：" + String.format("%.2f", ttlNum)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ReportInfo.this.singleReportAdapter.notifyDataSetChanged();
                        return;
                    }
                } else {
                    Toast.makeText(ReportInfo.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }

        });
    }

    /**
     * 页面控件点击事件
     * 根据点击的控件ID来调用不同的方法
     * 更新列表控件显示内容
     *
     * @param paramView
     */
    @Override
    public void onClick(View paramView) {
        DatePickerDialog datePickerDialog2;
        Calendar calendar2;
        if (paramView.getId() == R.id.btn_scan) {
            if (dateRoundCheck()) {
                if (ContextCompat.checkSelfPermission(ReportInfo.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ReportInfo.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    goScan();
                }
            } else {
                Toast.makeText(this, "日期范围不能超过30天", Toast.LENGTH_SHORT).show();
            }
        }
        if (paramView.getId() == R.id.report_btn) {
            if (this.type.equals("单品")) {
                this.singleTypeReportBeans.clear();
                this.singleReportAdapter.notifyDataSetChanged();
                if (reportDateBegin.getText().toString().equals("") || reportDateStop.getText().toString().equals("")) {
                    Toast.makeText(this, "开始日期和结束日期不能为空", Toast.LENGTH_SHORT).show();
                } else if (reportDateBegin.getText().toString().equals(null) || reportDateStop.getText().toString().equals(null)) {
                    Toast.makeText(this, "开始日期和结束日期不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    singleTypeReport(String.valueOf(1));
                }
                return;
            }
            if (this.type.equals("类型")) {
                this.productTypeReportBeans.clear();
                this.productTypeReportAdapter.notifyDataSetChanged();
                if (reportDateBegin.getText().toString().equals("") || reportDateStop.getText().toString().equals("")) {
                    Toast.makeText(this, "开始日期和结束日期不能为空", Toast.LENGTH_SHORT).show();
                } else if (reportDateBegin.getText().toString().equals(null) || reportDateStop.getText().toString().equals(null)) {
                    Toast.makeText(this, "开始日期和结束日期不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (dateRoundCheck()) {
                        productTypeReport(String.valueOf(1));
                    } else {
                        Toast.makeText(this, "日期范围不能超过30天", Toast.LENGTH_SHORT).show();
                    }
                }
                return;
            }
            if (this.type.equals("客流")) {
                this.guestTypeBeans.clear();
                this.guestReportAdapter.notifyDataSetChanged();
                if (reportDateBegin.getText().toString().equals("") || reportDateStop.getText().toString().equals("")) {
                    Toast.makeText(this, "开始日期和结束日期不能为空", Toast.LENGTH_SHORT).show();
                } else if (reportDateBegin.getText().toString().equals(null) || reportDateStop.getText().toString().equals(null)) {
                    Toast.makeText(this, "开始日期和结束日期不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (dateRoundCheck()) {
                        guestTypeReport(String.valueOf(1));
                    } else {
                        Toast.makeText(this, "日期范围不能超过30天", Toast.LENGTH_SHORT).show();
                    }
                }
                return;
            }
            if (this.type.equals("收款员")) {
                this.moneierTypeReportBeans.clear();
                this.moneierReportAdapter.notifyDataSetChanged();
                if (reportDateBegin.getText().toString().equals("") || reportDateStop.getText().toString().equals("")) {
                    Toast.makeText(this, "开始日期和结束日期不能为空", Toast.LENGTH_SHORT).show();
                } else if (reportDateBegin.getText().toString().equals(null) || reportDateStop.getText().toString().equals(null)) {
                    Toast.makeText(this, "开始日期和结束日期不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (dateRoundCheck()) {
                        moneierTypeReport(String.valueOf(1));
                    } else {
                        Toast.makeText(this, "日期范围不能超过30天", Toast.LENGTH_SHORT).show();
                    }
                }
                return;
            }
            if (this.type.equals("专柜")) {
                this.shoppeTypeReportBeans.clear();
                this.shoppeTypeReportAdapter.notifyDataSetChanged();
                if (reportDateBegin.getText().toString().equals("") || reportDateStop.getText().toString().equals("")) {
                    Toast.makeText(this, "开始日期和结束日期不能为空", Toast.LENGTH_SHORT).show();
                } else if (reportDateBegin.getText().toString().equals(null) || reportDateStop.getText().toString().equals(null)) {
                    Toast.makeText(this, "开始日期和结束日期不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (dateRoundCheck()) {
                        shoppeTypeReport(String.valueOf(1));
                    } else {
                        Toast.makeText(this, "日期范围不能超过30天", Toast.LENGTH_SHORT).show();
                    }
                }
                return;
            }
            if (this.type.equals("实时")) {
                this.realTypeReportBeans.clear();
                this.realTypeReportAdapter.notifyDataSetChanged();
                if (reportDateBegin.getText().toString().equals("") || reportDateStop.getText().toString().equals("")) {
                    Toast.makeText(this, "开始日期和结束日期不能为空", Toast.LENGTH_SHORT).show();
                } else if (reportDateBegin.getText().toString().equals(null) || reportDateStop.getText().toString().equals(null)) {
                    Toast.makeText(this, "开始日期和结束日期不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (dateRoundCheck()) {
                        realTypeReport(String.valueOf(1));
//                        if (dateRoundCheck()){
//                            shoppeTypeReport(String.valueOf(1));
//                        }else {
//                            Toast.makeText(this,"日期范围不能超过30天",Toast.LENGTH_SHORT).show();
//                        }
//                        if (ContextCompat.checkSelfPermission(ReportInfo.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                            ActivityCompat.requestPermissions(ReportInfo.this, new String[]{Manifest.permission.CAMERA}, 1);
//                        } else {
//                            goScan();
//                        }
                    } else {
                        Toast.makeText(this, "日期范围不能超过30天", Toast.LENGTH_SHORT).show();
                    }
                }
                return;
            }
        }
        if (paramView.getId() == R.id.report_date_begin) {
            calendar2 = Calendar.getInstance();
            datePickerDialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker param1DatePicker, int param1Int1, int param1Int2, int param1Int3) {
                    String str2;
                    String str1;
                    int i = param1Int2 + 1;
                    if (i < 10) {
                        StringBuilder stringBuilder1 = new StringBuilder();
                        stringBuilder1.append("0");
                        stringBuilder1.append(i);
                        str1 = stringBuilder1.toString();
                    } else {
                        str1 = String.valueOf(i);
                    }
                    if (param1Int3 < 10) {
                        StringBuilder stringBuilder1 = new StringBuilder();
                        stringBuilder1.append("0");
                        stringBuilder1.append(param1Int3);
                        str2 = stringBuilder1.toString();
                    } else {
                        str2 = String.valueOf(param1Int3);
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(param1Int1);
                    stringBuilder.append("-");
                    stringBuilder.append(str1);
                    stringBuilder.append("-");
                    stringBuilder.append(str2);
                    reportDateBegin.setText(stringBuilder.toString());
                }
            }, calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DATE));
            datePickerDialog2.show();
            reportDateBegin.clearFocus();
        }
        if (paramView.getId() == R.id.report_date_stop) {
            calendar2 = Calendar.getInstance();
            datePickerDialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker param1DatePicker, int param1Int1, int param1Int2, int param1Int3) {
                    String str2;
                    String str1;
                    int i = param1Int2 + 1;
                    if (i < 10) {
                        StringBuilder stringBuilder1 = new StringBuilder();
                        stringBuilder1.append("0");
                        stringBuilder1.append(i);
                        str1 = stringBuilder1.toString();
                    } else {
                        str1 = String.valueOf(i);
                    }
                    if (param1Int3 < 10) {
                        StringBuilder stringBuilder1 = new StringBuilder();
                        stringBuilder1.append("0");
                        stringBuilder1.append(param1Int3);
                        str2 = stringBuilder1.toString();
                    } else {
                        str2 = String.valueOf(param1Int3);
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(param1Int1);
                    stringBuilder.append("-");
                    stringBuilder.append(str1);
                    stringBuilder.append("-");
                    stringBuilder.append(str2);
                    reportDateStop.setText(stringBuilder.toString());
                }

            }, calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DATE));
            datePickerDialog2.show();
            reportDateStop.clearFocus();
        }

    }


    private boolean dateRoundCheck() {
        String begin = reportDateBegin.getText().toString();
        String stop = reportDateStop.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int result = 0;
        try {
            Date start = sdf.parse(begin);
            Date end = sdf.parse(stop);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(end);
            calendar.add(Calendar.DATE, -30);
            Date date = calendar.getTime();
            result = date.compareTo(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (result == -1) {
            return true;
        } else if (result == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 跳转到扫码界面扫码
     */
    private void goScan() {
        Intent intent = new Intent(ReportInfo.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码，条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                //返回的文本内容
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                //返回的BitMap图像
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
                reportEdit.setText(content);
//                if (this.type.equals("单品")){
//                    singleTypeReport(String.valueOf(1));
//                } else if (this.type.equals("实时")){
//                    realTypeReport(String.valueOf(1));
//                }
            }
        }
    }

}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.dateReport.ReportInfo
 * JD-Core Version:    0.6.0
 */