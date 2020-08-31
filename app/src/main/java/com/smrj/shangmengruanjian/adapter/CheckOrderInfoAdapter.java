package com.smrj.shangmengruanjian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.bean.CheckOrderBean;
import com.smrj.shangmengruanjian.orderoperation.CheckOrder;

import java.util.List;

public class CheckOrderInfoAdapter extends BaseAdapter {

    private List<CheckOrderBean> salesEntities;
    private CheckOrder checkOrder;
    public static int id;

    public CheckOrderInfoAdapter(List<CheckOrderBean> paramList, CheckOrder checkOrder) {
        this.salesEntities = paramList;
        this.checkOrder = checkOrder;
    }

    public int getCount() {
        List<CheckOrderBean> list = this.salesEntities;
        return (list == null) ? 0 : list.size();
    }

    public Object getItem(int paramInt) {
        return this.salesEntities.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        if (paramView == null) {
            paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.check_order_list_item, paramViewGroup, false);
        }
        View finalParamView = paramView;
        id = finalParamView.getId();
        Button btn = paramView.findViewById(R.id.check_order_imgbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1:判断数量
                if ("".equals(checkOrder.getCheckOrderDnum().getText().toString().trim())) {
                    Toast.makeText(checkOrder.getActivity(), "数量不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                //2：清空非当前供应商商品
                if ("".equals(checkOrder.getCheckOrderProvider().getText().toString().trim())) {
                    for (int i = 0; i < salesEntities.size(); i++) {
                        if (!salesEntities.get(paramInt).getDprovidercode().equals(salesEntities.get(i).getDprovidercode())) {
                            salesEntities.remove(i);
                        }
                    }
                }
                //3:设置供应商
                checkOrder.getCheckOrderProvider().setText(salesEntities.get(paramInt).getDprovidername() + "-" + salesEntities.get(paramInt).getDprovidercode());
                //4:赋值
                checkOrder.setCheckOrderBean(salesEntities.get(paramInt));
                //5:传值
                checkOrder.onClick(finalParamView);
                //6:清空当前选择商品
                salesEntities.remove(paramInt);
            }
        });

        //商品条码
        TextView check_order_dbarcode = paramView.findViewById(R.id.check_order_dbarcode);
        check_order_dbarcode.setText(salesEntities.get(paramInt).getDbarcode());
        //名称
        TextView check_order_dname = paramView.findViewById(R.id.check_order_dname);
        check_order_dname.setText(salesEntities.get(paramInt).getDname());
        //供应商编号-名称
        TextView check_order_dprovidercode = paramView.findViewById(R.id.check_order_dprovidercode);
        check_order_dprovidercode.setText(salesEntities.get(paramInt).getDprovidercode());
        TextView check_order_dprovidername = paramView.findViewById(R.id.check_order_dprovidername);
        check_order_dprovidername.setText(salesEntities.get(paramInt).getDprovidername());
        //单位
        TextView check_order_dunitname = paramView.findViewById(R.id.check_order_dunitname);
        check_order_dunitname.setText(salesEntities.get(paramInt).getDunitname());
        //商品进价
        TextView check_order_dpricein = paramView.findViewById(R.id.check_order_dpricein);
        check_order_dpricein.setText(salesEntities.get(paramInt).getDpricein());
        //售价
        TextView check_order_dprice = paramView.findViewById(R.id.check_order_dprice);
        check_order_dprice.setText(salesEntities.get(paramInt).getDprice());
        return paramView;
    }
//    public class BroadcastActivity extends Activity {
//        //设定为com.china.ui.NEW_LIFEFORM，显示内容前，多一条信息"收到广播信息"；
//        //public static final String MY_NEW_LIFEFORM="com.china.ui.NEW_LIFEFORM";
//        public static final String MY_NEW_LIFEFORM = "NEW_LIFEFORM";
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.check_order);
//            //传递数据
//            final Intent intent = new Intent(MY_NEW_LIFEFORM);
//            intent.putExtra("check_order_provider", checkOrder.getCheckOrderProvider().getText().toString().trim().split("-")[1].trim());
//            //初始化按钮
//            @SuppressLint("WrongViewCast") Button button = (Button) findViewById(R.id.check_order_imgbtn);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View arg0) {
//                    sendBroadcast(intent);
//                }
//            });
//        }
//    }
}



