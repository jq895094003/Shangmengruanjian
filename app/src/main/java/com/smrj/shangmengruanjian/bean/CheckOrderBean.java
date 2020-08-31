package com.smrj.shangmengruanjian.bean;

import java.io.Serializable;

public class CheckOrderBean implements Serializable {
  private String dbarcode;              //条码
  private String dcode;                 //货类编码
  private String ddateinput;            //录入日期
  private String dkindname;             //货类名称
  private String dmasterbarcode;        //主条码
  private String dname;                 //商品名称
  private String dnum;                  //商品数量
  private String dpack;                 //包装
  private String dprice;                //售价
  private String dpricein;              //成本
  private String dprovidercode;         //供应商编号
  private String dprovidername;         //供应商名称
  private String dproviderno;           //供应商编码
  private String dshopname;             //商铺名称
  private String dshopno;               //商铺编编号
  private String dspec;                 //规格
  private String dthingcode;            //商品编码
  private String dthingstate;           //商品编码
  private String dthingtype;            //商品类型
  private String dunitname;             //单位名称

  public String getDbarcode()
  {
    return this.dbarcode;
  }

  public String getDcode()
  {
    return this.dcode;
  }

  public String getDdateinput()
  {
    return this.ddateinput;
  }

  public String getDkindname()
  {
    return this.dkindname;
  }

  public String getDmasterbarcode()
  {
    return this.dmasterbarcode;
  }

  public String getDname()
  {
    return this.dname;
  }

  public String getDnum()
  {
    return this.dnum;
  }

  public String getDpack()
  {
    return this.dpack;
  }

  public String getDprice()
  {
    return this.dprice;
  }

  public String getDpricein()
  {
    return this.dpricein;
  }

  public String getDprovidercode()
  {
    return this.dprovidercode;
  }

  public String getDprovidername()
  {
    return this.dprovidername;
  }

  public String getDproviderno()
  {
    return this.dproviderno;
  }

  public String getDshopname()
  {
    return this.dshopname;
  }

  public String getDshopno()
  {
    return this.dshopno;
  }

  public String getDspec()
  {
    return this.dspec;
  }

  public String getDthingcode()
  {
    return this.dthingcode;
  }

  public String getDthingstate()
  {
    return this.dthingstate;
  }

  public String getDthingtype()
  {
    return this.dthingtype;
  }

  public String getDunitname()
  {
    return this.dunitname;
  }

  public void setDbarcode(String paramString)
  {
    this.dbarcode = paramString;
  }

  public void setDcode(String paramString)
  {
    this.dcode = paramString;
  }

  public void setDdateinput(String paramString)
  {
    this.ddateinput = paramString;
  }

  public void setDkindname(String paramString)
  {
    this.dkindname = paramString;
  }

  public void setDmasterbarcode(String paramString)
  {
    this.dmasterbarcode = paramString;
  }

  public void setDname(String paramString)
  {
    this.dname = paramString;
  }

  public void setDnum(String paramString)
  {
    this.dnum = paramString;
  }

  public void setDpack(String paramString)
  {
    this.dpack = paramString;
  }

  public void setDprice(String paramString)
  {
    this.dprice = paramString;
  }

  public void setDpricein(String paramString)
  {
    this.dpricein = paramString;
  }

  public void setDprovidercode(String paramString)
  {
    this.dprovidercode = paramString;
  }

  public void setDprovidername(String paramString)
  {
    this.dprovidername = paramString;
  }

  public void setDproviderno(String paramString)
  {
    this.dproviderno = paramString;
  }

  public void setDshopname(String paramString)
  {
    this.dshopname = paramString;
  }

  public void setDshopno(String paramString)
  {
    this.dshopno = paramString;
  }

  public void setDspec(String paramString)
  {
    this.dspec = paramString;
  }

  public void setDthingcode(String paramString)
  {
    this.dthingcode = paramString;
  }

  public void setDthingstate(String paramString)
  {
    this.dthingstate = paramString;
  }

  public void setDthingtype(String paramString)
  {
    this.dthingtype = paramString;
  }

  public void setDunitname(String paramString)
  {
    this.dunitname = paramString;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("CheckOrderBean{ddateinput='");
    localStringBuilder.append(this.ddateinput);
    localStringBuilder.append('\'');
    localStringBuilder.append(", dthingtype='");
    localStringBuilder.append(this.dthingtype);
    localStringBuilder.append('\'');
    localStringBuilder.append(", dshopno='");
    localStringBuilder.append(this.dshopno);
    localStringBuilder.append('\'');
    localStringBuilder.append(", dshopname='");
    localStringBuilder.append(this.dshopname);
    localStringBuilder.append('\'');
    localStringBuilder.append(", dnum='");
    localStringBuilder.append(this.dnum);
    localStringBuilder.append('\'');
    localStringBuilder.append(", dprovidername='");
    localStringBuilder.append(this.dprovidername);
    localStringBuilder.append('\'');
    localStringBuilder.append(", dthingstate='");
    localStringBuilder.append(this.dthingstate);
    localStringBuilder.append('\'');
    localStringBuilder.append(", dkindname='");
    localStringBuilder.append(this.dkindname);
    localStringBuilder.append('\'');
    localStringBuilder.append(", dmasterbarcode='");
    localStringBuilder.append(this.dmasterbarcode);
    localStringBuilder.append('\'');
    localStringBuilder.append(", dbarcode='");
    localStringBuilder.append(this.dbarcode);
    localStringBuilder.append('\'');
    localStringBuilder.append(", dunitname='");
    localStringBuilder.append(this.dunitname);
    localStringBuilder.append('\'');
    localStringBuilder.append(", dthingcode='");
    localStringBuilder.append(this.dthingcode);
    localStringBuilder.append('\'');
    localStringBuilder.append(", dprovidercode='");
    localStringBuilder.append(this.dprovidercode);
    localStringBuilder.append('\'');
    localStringBuilder.append(", dproviderno='");
    localStringBuilder.append(this.dproviderno);
    localStringBuilder.append('\'');
    localStringBuilder.append(", dcode='");
    localStringBuilder.append(this.dcode);
    localStringBuilder.append('\'');
    localStringBuilder.append(", dspec='");
    localStringBuilder.append(this.dspec);
    localStringBuilder.append('\'');
    localStringBuilder.append(", dprice='");
    localStringBuilder.append(this.dprice);
    localStringBuilder.append('\'');
    localStringBuilder.append(", dname='");
    localStringBuilder.append(this.dname);
    localStringBuilder.append('\'');
    localStringBuilder.append(", dpack='");
    localStringBuilder.append(this.dpack);
    localStringBuilder.append('\'');
    localStringBuilder.append(", dpricein='");
    localStringBuilder.append(this.dpricein);
    localStringBuilder.append('\'');
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.bean.CheckOrderBean
 * JD-Core Version:    0.6.0
 */