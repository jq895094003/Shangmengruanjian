package com.smrj.shangmengruanjian.bean;

public class Providers {
    private String dfullname;           //供应商全称
    private String dprovidercode;       //供应商编码
    private String dprovidername;       //供应商名称
    private String dproviderno;         //供应商编号

    @Override
    public String toString() {
        return "Providers{" +
                "dfullname='" + dfullname + '\'' +
                ", dprovidercode='" + dprovidercode + '\'' +
                ", dprovidername='" + dprovidername + '\'' +
                ", dproviderno='" + dproviderno + '\'' +
                '}';
    }

    public String getDfullname() {
        return this.dfullname;
    }

    public String getDprovidercode() {
        return this.dprovidercode;
    }

    public String getDprovidername() {
        return this.dprovidername;
    }

    public String getDproviderno() {
        return this.dproviderno;
    }

    public void setDfullname(String paramString) {
        this.dfullname = paramString;
    }

    public void setDprovidercode(String dprovidercode) {
        this.dprovidercode = dprovidercode;
    }

    public void setDprovidername(String paramString) {
        this.dprovidername = paramString;
    }

    public void setDproviderno(String paramString) {
        this.dproviderno = paramString;
    }
}

/* Location:           C:\Users\SYQ\Desktop\移动领航者_classes_dex2jar.jar
 * Qualified Name:     com.example.shangmengsoft.bean.Providers
 * JD-Core Version:    0.6.0
 */