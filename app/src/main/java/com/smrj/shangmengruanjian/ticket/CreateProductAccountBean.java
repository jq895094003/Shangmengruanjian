package com.smrj.shangmengruanjian.ticket;

import java.io.Serializable;

public class CreateProductAccountBean implements Serializable {
    private String ddatedz;             //登账日期
    private String ddateinput;          //录入日期
    private String dmemo;               //备注
    private String doperator;           //操作员
    private String dplace;              //业务地点
    private String dplaceinput;         //录入地点
    private String dsetdate;            //制单日期
    private String dsetman;             //制单人
    private String dstaterun;           //单据状态
    private String dtickethandle;       //手工单号
    private String dticketno;           //单据号
    private String dverify;             //财务校对

    public String getDdatedz() {
        return this.ddatedz;
    }

    public String getDdateinput() {
        return this.ddateinput;
    }

    public String getDmemo() {
        return this.dmemo;
    }

    public String getDoperator() {
        return this.doperator;
    }

    public String getDplace() {
        return this.dplace;
    }

    public String getDplaceinput() {
        return this.dplaceinput;
    }

    public String getDsetdate() {
        return this.dsetdate;
    }

    public String getDsetman() {
        return this.dsetman;
    }

    public String getDstaterun() {
        return this.dstaterun;
    }

    public String getDtickethandle() {
        return this.dtickethandle;
    }

    public String getDticketno() {
        return this.dticketno;
    }

    public String getDverify() {
        return this.dverify;
    }

    public void setDdatedz(String paramString) {
        this.ddatedz = paramString;
    }

    public void setDdateinput(String paramString) {
        this.ddateinput = paramString;
    }

    public void setDmemo(String paramString) {
        this.dmemo = paramString;
    }

    public void setDoperator(String paramString) {
        this.doperator = paramString;
    }

    public void setDplace(String paramString) {
        this.dplace = paramString;
    }

    public void setDplaceinput(String paramString) {
        this.dplaceinput = paramString;
    }

    public void setDsetdate(String paramString) {
        this.dsetdate = paramString;
    }

    public void setDsetman(String paramString) {
        this.dsetman = paramString;
    }

    public void setDstaterun(String paramString) {
        this.dstaterun = paramString;
    }

    public void setDtickethandle(String paramString) {
        this.dtickethandle = paramString;
    }

    public void setDticketno(String paramString) {
        this.dticketno = paramString;
    }

    public void setDverify(String paramString) {
        this.dverify = paramString;
    }

    public String toString() {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("CreateProductAccountBean{dticketno='");
        localStringBuilder.append(this.dticketno);
        localStringBuilder.append('\'');
        localStringBuilder.append(", dplace='");
        localStringBuilder.append(this.dplace);
        localStringBuilder.append('\'');
        localStringBuilder.append(", dplaceinput='");
        localStringBuilder.append(this.dplaceinput);
        localStringBuilder.append('\'');
        localStringBuilder.append(", dsetman='");
        localStringBuilder.append(this.dsetman);
        localStringBuilder.append('\'');
        localStringBuilder.append(", dsetdate='");
        localStringBuilder.append(this.dsetdate);
        localStringBuilder.append('\'');
        localStringBuilder.append(", dtickethandle='");
        localStringBuilder.append(this.dtickethandle);
        localStringBuilder.append('\'');
        localStringBuilder.append(", dmemo='");
        localStringBuilder.append(this.dmemo);
        localStringBuilder.append('\'');
        localStringBuilder.append(", doperator='");
        localStringBuilder.append(this.doperator);
        localStringBuilder.append('\'');
        localStringBuilder.append(", ddateinput='");
        localStringBuilder.append(this.ddateinput);
        localStringBuilder.append('\'');
        localStringBuilder.append(", ddatedz='");
        localStringBuilder.append(this.ddatedz);
        localStringBuilder.append('\'');
        localStringBuilder.append(", dstaterun='");
        localStringBuilder.append(this.dstaterun);
        localStringBuilder.append('\'');
        localStringBuilder.append(", dverify='");
        localStringBuilder.append(this.dverify);
        localStringBuilder.append('\'');
        localStringBuilder.append('}');
        return localStringBuilder.toString();
    }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.bean.CreateProductAccountBean
 * JD-Core Version:    0.6.0
 */