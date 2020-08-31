package com.smrj.shangmengruanjian.orderoperation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import com.google.android.material.tabs.TabLayout;
import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.service.FragmentToFragment;
import com.smrj.shangmengruanjian.util.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CheckBottom extends AppCompatActivity implements FragmentToFragment {
  /**
   * 29-30行，声明页面上的控件
   */
  private TextView title;
  private ViewPager viewPager;

  /**
   * Activity事件监听
   * 初始化页面控件
   * @param paramBundle
   */
  protected void onCreate(@Nullable Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.check_bottom);
    initView();
  }
  //初始化页面控件方法，设置监听，加载Fragment
  private void initView() {
    String[] arrayOfString = getResources().getStringArray(R.array.check_bottom);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new CheckOrder());
    localArrayList.add(new CommitCheckOrder());
    this.viewPager = ((ViewPager)findViewById(R.id.view_pager));
    this.viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), localArrayList, arrayOfString));
    this.viewPager.setOffscreenPageLimit(4);
    this.viewPager.addOnPageChangeListener(new OnPageChangeListener() {
      public void onPageScrollStateChanged(int paramInt) {
      }

      public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {
      }

      public void onPageSelected(int paramInt) {
      }
    });
    TabLayout localTabLayout = (TabLayout)findViewById(R.id.tab1);
    localTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      public void onTabReselected(TabLayout.Tab paramTab) {
      }

      public void onTabSelected(TabLayout.Tab paramTab) {
      }

      public void onTabUnselected(TabLayout.Tab paramTab) {
      }
    });
    localTabLayout.setupWithViewPager(this.viewPager);
  }
  //退出App方法
  private void showExitConfirm()
  {
    Process.killProcess(Process.myPid());
  }

  //广播事件方法，放入需要的参数进行广播
  @Override
  public void checkOrderToCommitCheckOrder(HashMap<String, String> paramHashMap) {
    new CommitCheckOrder();
    Intent localIntent = new Intent("android.intent.action.CART_BROADCAST");
    Iterator localIterator = paramHashMap.entrySet().iterator();
    while (localIterator.hasNext()) {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localIntent.putExtra((String)localEntry.getKey(), (String)localEntry.getValue());
    }
    LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
  }


  /**
   * 按键监听，按返回键退出App
   * @param paramInt
   * @param paramKeyEvent
   * @return
   */
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    if (paramInt == 4)
      showExitConfirm();
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  /**
   * 页面重新打开方法，进行Fragment页面切换，并发送需要的数据
   */
  @Override
  protected void onResume() {
    int i = getIntent().getIntExtra("id", 0);
    String str = getIntent().getStringExtra("checkInfoDelete");
    if (i == 2) {
      CommitCheckOrder localCommitCheckOrder = new CommitCheckOrder();
      FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
      localFragmentTransaction.replace(R.id.view_pager, localCommitCheckOrder);
      localFragmentTransaction.commit();
      this.viewPager.setCurrentItem(2);
      Intent localIntent = new Intent();
      localIntent.setClass(this, CommitCheckOrder.class);
      localIntent.putExtra("id", 2);
      localIntent.putExtra("checkInfoDelete", str);
    }
    super.onResume();
  }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.CheckBottom
 * JD-Core Version:    0.6.0
 */