package com.smrj.shangmengruanjian.util;


import com.smrj.shangmengruanjian.bean.CheckOrderAccountInfoBean;
import com.smrj.shangmengruanjian.bean.LevelType;
import com.smrj.shangmengruanjian.bean.PandianAccountInfoBean;
import com.smrj.shangmengruanjian.bean.ProductCornerBean;
import com.smrj.shangmengruanjian.bean.Providers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonToString {
  public String cornerJsonToString(ArrayList<ProductCornerBean> paramArrayList) {
    JSONArray localJSONArray = new JSONArray();
    for (int i = 0; i < paramArrayList.size(); i++) {
      JSONObject localJSONObject = new JSONObject();
      try {
        localJSONObject.put("dpcname", ((ProductCornerBean)paramArrayList.get(i)).getDareaname());
        localJSONObject.put("dpckindno", ((ProductCornerBean)paramArrayList.get(i)).getDareano());
        localJSONArray.put(localJSONObject);
      } catch (JSONException localJSONException) {
        localJSONException.printStackTrace();
      }
    }
    System.out.println(localJSONArray);
    return String.valueOf(localJSONArray);
  }

  public String levelJsonToString(ArrayList<LevelType> paramArrayList) {
    JSONArray localJSONArray = new JSONArray();
    for (int i = 0; i < paramArrayList.size(); i++) {
      JSONObject localJSONObject = new JSONObject();
      try {
        localJSONObject.put("dpcname", ((LevelType)paramArrayList.get(i)).getDkindname());
        localJSONObject.put("dpckindno", ((LevelType)paramArrayList.get(i)).getDkindno());
        localJSONArray.put(localJSONObject);
      } catch (JSONException localJSONException) {
        localJSONException.printStackTrace();
      }
    }
    System.out.println(localJSONArray);
    return String.valueOf(localJSONArray);
  }

  public String pandianInfoJsonToString(ArrayList<PandianAccountInfoBean> paramArrayList) {
    JSONArray localJSONArray = new JSONArray();
    for (int i = 0; i < paramArrayList.size(); i++) {
      JSONObject localJSONObject = new JSONObject();
      try {
        localJSONObject.put("dticketno", ((PandianAccountInfoBean)paramArrayList.get(i)).getDticketno());
        localJSONObject.put("dbarcode", ((PandianAccountInfoBean)paramArrayList.get(i)).getDbarcode());
        localJSONObject.put("dnum", ((PandianAccountInfoBean)paramArrayList.get(i)).getDnum());
        localJSONArray.put(localJSONObject);
      } catch (JSONException localJSONException) {
        localJSONException.printStackTrace();
      }
    }
    System.out.println(localJSONArray);
    return String.valueOf(localJSONArray);
  }

  public String productJsonToString(ArrayList<CheckOrderAccountInfoBean> paramArrayList) {
    JSONArray localJSONArray = new JSONArray();
    for (int i = 0; i < paramArrayList.size(); i++) {
      JSONObject localJSONObject = new JSONObject();
      try {
        localJSONObject.put("dpcname", ((CheckOrderAccountInfoBean)paramArrayList.get(i)).getDname());
        localJSONObject.put("dpckindno", ((CheckOrderAccountInfoBean)paramArrayList.get(i)).getDbarcode());
        localJSONArray.put(localJSONObject);
      } catch (JSONException localJSONException) {
        localJSONException.printStackTrace();
      }
    }
    System.out.println(localJSONArray);
    return String.valueOf(localJSONArray);
  }

  public String providerJsonToString(ArrayList<Providers> paramArrayList) {
    JSONArray localJSONArray = new JSONArray();
    for (int i = 0; i < paramArrayList.size(); i++) {
      JSONObject localJSONObject = new JSONObject();
      try {
        localJSONObject.put("dpcname", ((Providers)paramArrayList.get(i)).getDprovidername());
        localJSONObject.put("dpckindno", ((Providers)paramArrayList.get(i)).getDproviderno());
        localJSONArray.put(localJSONObject);
      } catch (JSONException localJSONException) {
        localJSONException.printStackTrace();
      }
    }
    System.out.println(localJSONArray);
    return String.valueOf(localJSONArray);
  }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.util.JsonToString
 * JD-Core Version:    0.6.0
 */