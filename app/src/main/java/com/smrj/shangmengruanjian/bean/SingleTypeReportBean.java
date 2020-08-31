package com.smrj.shangmengruanjian.bean;

public class SingleTypeReportBean {
  private String DBARCODE;          //条码
  private String DMONEY_SS;         //实收
  private String DMONEY_YS;         //应收
  private String DNAME;             //商品名称
  private String DNUM;              //数量
  private String DSHOPNO;           //商铺编号
  private String DUNITNAME;         //单位
  private String DDATE_S;           //日期

  @Override
  public String toString() {
    return "SingleTypeReportBean{" +
            "DBARCODE='" + DBARCODE + '\'' +
            ", DMONEY_SS='" + DMONEY_SS + '\'' +
            ", DMONEY_YS='" + DMONEY_YS + '\'' +
            ", DNAME='" + DNAME + '\'' +
            ", DNUM='" + DNUM + '\'' +
            ", DSHOPNO='" + DSHOPNO + '\'' +
            ", DUNITNAME='" + DUNITNAME + '\'' +
            ", DDATE_S='" + DDATE_S + '\'' +
            '}';
  }

  public String getDDATE_S() {
    return DDATE_S;
  }

  public void setDDATE_S(String DDATE_S) {
    this.DDATE_S = DDATE_S;
  }

  public String getDBARCODE()
  {
    return this.DBARCODE;
  }

  public String getDMONEY_SS()
  {
    return this.DMONEY_SS;
  }

  public String getDMONEY_YS()
  {
    return this.DMONEY_YS;
  }

  public String getDNAME()
  {
    return this.DNAME;
  }

  public String getDNUM()
  {
    return this.DNUM;
  }

  public String getDSHOPNO()
  {
    return this.DSHOPNO;
  }

  public String getDUNITNAME()
  {
    return this.DUNITNAME;
  }

  public void setDBARCODE(String paramString)
  {
    this.DBARCODE = paramString;
  }

  public void setDMONEY_SS(String paramString)
  {
    this.DMONEY_SS = paramString;
  }

  public void setDMONEY_YS(String paramString)
  {
    this.DMONEY_YS = paramString;
  }

  public void setDNAME(String paramString)
  {
    this.DNAME = paramString;
  }

  public void setDNUM(String paramString)
  {
    this.DNUM = paramString;
  }

  public void setDSHOPNO(String paramString)
  {
    this.DSHOPNO = paramString;
  }

  public void setDUNITNAME(String paramString)
  {
    this.DUNITNAME = paramString;
  }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.bean.SingleTypeReportBean
 * JD-Core Version:    0.6.0
 */