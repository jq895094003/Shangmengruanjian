package com.smrj.shangmengruanjian.orderquery;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.adapter.RealTypeReportAdapter;
import com.smrj.shangmengruanjian.asynchttpclient.MyAsyncClient;
import com.smrj.shangmengruanjian.asynchttpclient.MyResponseHandler;
import com.smrj.shangmengruanjian.bean.RealTypeReportBean;
import com.smrj.shangmengruanjian.orderoperation.ReturnFactory;
import com.smrj.shangmengruanjian.progressdialog.ProgressDialogUtil;
import com.smrj.shangmengruanjian.util.MyUrl;
import com.smrj.shangmengruanjian.zxing.android.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockInfo extends AppCompatActivity implements View.OnClickListener {
    /**
     * 50-73行，声明并实例化页面控件、所需工具，使用ButterKnife监听
     */
    int indes = 1;

    RealTypeReportAdapter realTypeReportAdapter;
    ArrayList<RealTypeReportBean> realTypeReportBeans = new ArrayList();

    @BindView(R.id.report_btn)
    Button reportBtn;

    @BindView(R.id.report_edt)
    EditText reportEdit;

    @BindView(R.id.report_info)
    PullToRefreshListView reportInfoList;
    String type = "";
    SharedPreferences sharedPreferences;
    private ProgressDialogUtil progressDialogUtil;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;
    public static final int RESULT_OK = -1;

    /**
     * Activity声明周期，页面创建方法，根据传入的参数进行不通的操作
     *
     * @param paramBundle
     */
    @Override
    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.stock_info);
        ButterKnife.bind(this);
        progressDialogUtil = new ProgressDialogUtil();
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        this.type = getIntent().getStringExtra("type");
        this.reportBtn.setOnClickListener(this);
        this.reportEdit.setHint("请输入商品编码");
        this.realTypeReportAdapter = new RealTypeReportAdapter(this.realTypeReportBeans);
        this.reportInfoList.setAdapter(this.realTypeReportAdapter);
        this.reportInfoList.setMode(PullToRefreshBase.Mode.BOTH);
        this.reportInfoList.setOnRefreshListener(this.mListViewOnRefreshListener1);
        realTypeReport(String.valueOf(1));
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
                    realTypeReportBeans.clear();
                    realTypeReportAdapter.notifyDataSetChanged();
                    indes = 1;
                    realTypeReport(String.valueOf(StockInfo.this.indes));
                    reportInfoList.onRefreshComplete();
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
                    indes = (1 + indes);
                    realTypeReport(String.valueOf(indes));
                    reportInfoList.onRefreshComplete();
                }
            }, 500);
        }
    };

    /**
     * 实时数据查询方法
     * 如果返回值code为200则加载数据，加载到列表控件中
     * 如果不为200则弹出后台提示
     *
     * @param paramString
     */
    private void realTypeReport(String paramString) {
        progressDialogUtil.showProgressDialog(StockInfo.this,"搜索中");
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("page", paramString);
        if (!this.reportEdit.getText().toString().equals("")) {
            localRequestParams.put("condition", "DBARCODE LIKE '%" + this.reportEdit.getText().toString().trim() + "%'");
        } else {
            localRequestParams.put("condition", this.reportEdit.getText().toString().trim());
        }
        MyAsyncClient.doPost(new MyUrl().getFindFXStorerealtime(), localRequestParams, new MyResponseHandler(StockInfo.this) {
            @Override
            public void success(JSONObject jsonObject) {
                progressDialogUtil.dismiss(StockInfo.this);
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
                        StockInfo.this.realTypeReportBeans.add(localRealTypeReportBean);
                        i++;
                    }
                    StockInfo.this.realTypeReportAdapter.notifyDataSetChanged();
                }
                Toast.makeText(StockInfo.this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void faile(JSONObject jsonObject) {

            }
        });
    }



    /**
     * 跳转到扫码界面扫码
     */
    private void goScan() {
        Intent intent = new Intent(StockInfo.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                //返回的文本内容
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                //返回的BitMap图像
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
                reportEdit.setText(content);
                realTypeReport(String.valueOf(1));
            }
        }
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
        if (paramView.getId() == R.id.report_btn) {
            realTypeReportBeans.clear();
            realTypeReportAdapter.notifyDataSetChanged();
            if (ContextCompat.checkSelfPermission(StockInfo.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(StockInfo.this, new String[]{Manifest.permission.CAMERA}, 1);
            } else {
                goScan();
            }
        }
    }
}
