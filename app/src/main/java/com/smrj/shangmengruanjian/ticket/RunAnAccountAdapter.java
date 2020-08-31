package com.smrj.shangmengruanjian.ticket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smrj.shangmengruanjian.R;
import com.smrj.shangmengruanjian.service.AdapterItemClick;

import java.util.ArrayList;

public class RunAnAccountAdapter extends BaseAdapter {
  public AdapterItemClick adapterItemClick;
  private ArrayList<RunAnAccountBean> runAnAccounts;

  public RunAnAccountAdapter(ArrayList<RunAnAccountBean> paramArrayList) {
    this.runAnAccounts = paramArrayList;
  }

  public int getCount()
  {
    return this.runAnAccounts.size();
  }

  public Object getItem(int paramInt)
  {
    return this.runAnAccounts.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    if (paramView == null){
      paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.run_an_account_adapter, paramViewGroup, false);
    }
    ((TextView)paramView.findViewById(R.id.account_adapter_dticketno)).setText(((RunAnAccountBean)this.runAnAccounts.get(paramInt)).getDticketno());
    ((TextView)paramView.findViewById(R.id.account_adapter_dplace)).setText(((RunAnAccountBean)this.runAnAccounts.get(paramInt)).getDplace());
    ((TextView)paramView.findViewById(R.id.account_adapter_dsetdate)).setText(((RunAnAccountBean)this.runAnAccounts.get(paramInt)).getDsetdate());
    return paramView;
  }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.util.RunAnAccountAdapter
 * JD-Core Version:    0.6.0
 */