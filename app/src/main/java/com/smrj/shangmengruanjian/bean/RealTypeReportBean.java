package com.smrj.shangmengruanjian.bean;

public class RealTypeReportBean {
  private String DBARCODE;            //条码
  private String DDEPOTNO;            //分仓编号
  private String DKINDNAME;           //货类名称
  private String DNAME;               //商品名称
  private String DNUM;                //数量
  private String DPROVIDERNAME;       //供应商名称
  private String DSHOPNO;             //商铺编号

  public String getDBARCODE()
  {
    return this.DBARCODE;
  }

  public String getDDEPOTNO()
  {
    return this.DDEPOTNO;
  }

  public String getDKINDNAME()
  {
    return this.DKINDNAME;
  }

  public String getDNAME()
  {
    return this.DNAME;
  }

  public String getDNUM()
  {
    return this.DNUM;
  }

  public String getDPROVIDERNAME()
  {
    return this.DPROVIDERNAME;
  }

  public String getDSHOPNO()
  {
    return this.DSHOPNO;
  }

  public void setDBARCODE(String paramString)
  {
    this.DBARCODE = paramString;
  }

  public void setDDEPOTNO(String paramString)
  {
    this.DDEPOTNO = paramString;
  }

  public void setDKINDNAME(String paramString)
  {
    this.DKINDNAME = paramString;
  }

  public void setDNAME(String paramString)
  {
    this.DNAME = paramString;
  }

  public void setDNUM(String paramString)
  {
    this.DNUM = paramString;
  }

  public void setDPROVIDERNAME(String paramString)
  {
    this.DPROVIDERNAME = paramString;
  }

  public void setDSHOPNO(String paramString)
  {
    this.DSHOPNO = paramString;
  }
}