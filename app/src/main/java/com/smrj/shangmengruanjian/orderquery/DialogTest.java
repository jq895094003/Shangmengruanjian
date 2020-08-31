//package com.smrj.shangmengruanjian.orderquery;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.Button;
//import android.widget.LinearLayout;
//
//import com.smrj.shangmengruanjian.R;
//
//public class DialogTest extends Activity implements OnClickListener {
//    private Button add_btn, remove_btn;
//    private LinearLayout linearLayout;
//    private int index = 0;
//
//    /**
//     * Called when the activity is first created.
//     */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        findViews();
//        register();
//    }
//
//    private void register() {
//        add_btn.setOnClickListener(this);
//        remove_btn.setOnClickListener(this);
//    }
//
//    private void findViews() {
//        add_btn = (Button) findViewById(R.id.add);
//        remove_btn = (Button) findViewById(R.id.remove);
//        linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
//    }
//
//    protected View createView() {
//        Button btn = new Button(this);
//        btn.setId(index++);
//        btn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//        btn.setText("aaaaaa" + index);
//        return btn;
//    }
//
//    private void removeView() {
////获取linearlayout子view的个数
//        int count = linearLayout.getChildCount();
////研究整个LAYOUT布局，第0位的是含add和remove两个button的layout
////第count-1个是那个文字被置中的textview
////因此，在remove的时候，只能操作的是0<location<count-1这个范围的
////在执行每次remove时，我们从count-2的位置即textview上面的那个控件开始删除~
//        if (count - 2 > 0) {
////count-2>0用来判断当前linearlayout子view数多于2个，即还有我们点add增加的button
//            linearLayout.removeViewAt(count - 2);
//        }
//    }
//
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.add:
//                linearLayout.addView(createView(), 1);
//                break;
//            case R.id.remove:
//                removeView();
//                break;
//            default:
//                break;
//        }
//    }
//}