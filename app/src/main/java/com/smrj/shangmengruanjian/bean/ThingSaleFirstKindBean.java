package com.smrj.shangmengruanjian.bean;

public class ThingSaleFirstKindBean {

    /**
     * DMINCREASESALEBL :
     * DMINCREASEML :
     * DWEEKMLV :
     * DCURSALE :
     * DCOMPUTERNAME : DESKTOP-2K4PH16
     * DKINDNAME : 冬有散称
     * DMONTHML :
     * DCURMLV :
     * DMONTHSALE :
     * DMINCREASEMLBL :
     * DMONTHMLV :
     * DKINDNO : 19
     * DPLANML : 0
     * DPLANSALE :
     * DWEEKSALE :
     * DCURML :
     * DMINCREASESALE :
     * DWEEKML :
     */

    private String DCODE;               //供应商编号
    private String DCURSALE;            //当日|销售额
    private String DCURML;              //当日|毛利
    private String DCURMLV;             //当日|毛利率
    private String DMONTHSALE;          //上月|同期销售额
    private String DMONTHML;            //上月|同期毛利
    private String DMONTHMLV;           //上月|同期毛利率
    private String DMINCREASESALE;      //上月|同期销售增长
    private String DMINCREASESALEBL;    //上月|同期销售增长率
    private String DMINCREASEML;        //上月|同期毛利增长
    private String DMINCREASEMLBL;      //上月|同期毛利增长率

    @Override
    public String toString() {
        return "ThingSaleFirstKindBean{" +
                "DCODE='" + DCODE + '\'' +
                ", DCURSALE='" + DCURSALE + '\'' +
                ", DCURML='" + DCURML + '\'' +
                ", DCURMLV='" + DCURMLV + '\'' +
                ", DMONTHSALE='" + DMONTHSALE + '\'' +
                ", DMONTHML='" + DMONTHML + '\'' +
                ", DMONTHMLV='" + DMONTHMLV + '\'' +
                ", DMINCREASESALE='" + DMINCREASESALE + '\'' +
                ", DMINCREASESALEBL='" + DMINCREASESALEBL + '\'' +
                ", DMINCREASEML='" + DMINCREASEML + '\'' +
                ", DMINCREASEMLBL='" + DMINCREASEMLBL + '\'' +
                '}';
    }

    public String getDCODE() {
        return DCODE;
    }

    public void setDCODE(String DCODE) {
        this.DCODE = DCODE;
    }

    public String getDCURSALE() {
        return DCURSALE;
    }

    public void setDCURSALE(String DCURSALE) {
        this.DCURSALE = DCURSALE;
    }

    public String getDCURML() {
        return DCURML;
    }

    public void setDCURML(String DCURML) {
        this.DCURML = DCURML;
    }

    public String getDCURMLV() {
        return DCURMLV;
    }

    public void setDCURMLV(String DCURMLV) {
        this.DCURMLV = DCURMLV;
    }

    public String getDMONTHSALE() {
        return DMONTHSALE;
    }

    public void setDMONTHSALE(String DMONTHSALE) {
        this.DMONTHSALE = DMONTHSALE;
    }

    public String getDMONTHML() {
        return DMONTHML;
    }

    public void setDMONTHML(String DMONTHML) {
        this.DMONTHML = DMONTHML;
    }

    public String getDMONTHMLV() {
        return DMONTHMLV;
    }

    public void setDMONTHMLV(String DMONTHMLV) {
        this.DMONTHMLV = DMONTHMLV;
    }

    public String getDMINCREASESALE() {
        return DMINCREASESALE;
    }

    public void setDMINCREASESALE(String DMINCREASESALE) {
        this.DMINCREASESALE = DMINCREASESALE;
    }

    public String getDMINCREASESALEBL() {
        return DMINCREASESALEBL;
    }

    public void setDMINCREASESALEBL(String DMINCREASESALEBL) {
        this.DMINCREASESALEBL = DMINCREASESALEBL;
    }

    public String getDMINCREASEML() {
        return DMINCREASEML;
    }

    public void setDMINCREASEML(String DMINCREASEML) {
        this.DMINCREASEML = DMINCREASEML;
    }

    public String getDMINCREASEMLBL() {
        return DMINCREASEMLBL;
    }

    public void setDMINCREASEMLBL(String DMINCREASEMLBL) {
        this.DMINCREASEMLBL = DMINCREASEMLBL;
    }

}
