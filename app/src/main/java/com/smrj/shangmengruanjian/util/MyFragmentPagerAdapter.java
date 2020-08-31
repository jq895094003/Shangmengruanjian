package com.smrj.shangmengruanjian.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter
{
  private List<Fragment> fragmentList;
  private String[] titles;

  public MyFragmentPagerAdapter(FragmentManager paramFragmentManager, List<Fragment> paramList, String[] paramArrayOfString)
  {
    super(paramFragmentManager);
    this.fragmentList = paramList;
    this.titles = paramArrayOfString;
  }

  public int getCount()
  {
    return this.fragmentList.size();
  }

  public Fragment getItem(int paramInt)
  {
    return (Fragment)this.fragmentList.get(paramInt);
  }

  public CharSequence getPageTitle(int paramInt)
  {
    return this.titles[paramInt];
  }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.util.MyFragmentPagerAdapter
 * JD-Core Version:    0.6.0
 */