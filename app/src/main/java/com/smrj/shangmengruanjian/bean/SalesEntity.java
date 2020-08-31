package com.smrj.shangmengruanjian.bean;

/**
 * @ClassName SalesEntity
 * @Date 2020-04-07 15:08
 * @Version V1.0
 * @Author Mr. Lee
 * @Description: TODO
 **/
public class SalesEntity {

    /**
     *  Mr. Lee
     *  订单号
     **/
    private String DNO;
    /**
     *  Mr. Lee
     *  条码
     **/
    private String DBARCODE;
    /**
     *  Mr. Lee
     *  品名
     **/
    private String DNAME;
    /**
     *  Mr. Lee
     *  日期
     **/
    private String DDATE;
    /**
     *  Mr. Lee
     *  时间
     **/
    private String DTIME;
    /**
     *  Mr. Lee
     *  数量
     **/
    private Integer DNUM;
    /**
     *  Mr. Lee
     *  进价
     **/
    private Double DPRICE_IN;
    /**
     *  Mr. Lee
     *  售价
     **/
    private Double DPRICE;
    /**
     *  Mr. Lee
     *  应收
     **/
    private Double DMONEY_SALE;
    /**
     *  Mr. Lee
     *  实收
     **/
    private Double DMONEY_SS;
    /**
     *  Mr. Lee
     *  销售员
     **/
    private String DWORKERNAME;
    /**
     *  Mr. Lee
     *  积分
     **/
    private Double DJF;
    /**
     *  Mr. Lee
     *  供应商名称
     **/
    private String DPROVIDERNAME;
    /**
     *  Mr. Lee
     *  开始日期
     **/
    private String BEGIN;

    /**
     *  Mr. Lee
     *  结束日期
     **/
    private String END;

    @Override
    public String toString() {
        return "SalesEntity{" +
                "DNO='" + DNO + '\'' +
                ", DBARCODE='" + DBARCODE + '\'' +
                ", DNAME='" + DNAME + '\'' +
                ", DDATE='" + DDATE + '\'' +
                ", DTIME='" + DTIME + '\'' +
                ", DNUM=" + DNUM +
                ", DPRICE_IN=" + DPRICE_IN +
                ", DPRICE=" + DPRICE +
                ", DMONEY_SALE=" + DMONEY_SALE +
                ", DMONEY_SS=" + DMONEY_SS +
                ", DWORKERNAME='" + DWORKERNAME + '\'' +
                ", DJF=" + DJF +
                ", DPROVIDERNAME='" + DPROVIDERNAME + '\'' +
                ", BEGIN='" + BEGIN + '\'' +
                ", END='" + END + '\'' +
                '}';
    }

    public String getBEGIN() {
        return BEGIN;
    }

    public void setBEGIN(String BEGIN) {
        this.BEGIN = BEGIN;
    }

    public String getEND() {
        return END;
    }

    public void setEND(String END) {
        this.END = END;
    }

    public String getDNO() {
        return DNO;
    }

    public void setDNO(String DNO) {
        this.DNO = DNO;
    }

    public String getDBARCODE() {
        return DBARCODE;
    }

    public void setDBARCODE(String DBARCODE) {
        this.DBARCODE = DBARCODE;
    }

    public String getDNAME() {
        return DNAME;
    }

    public void setDNAME(String DNAME) {
        this.DNAME = DNAME;
    }

    public String getDDATE() {
        return DDATE;
    }

    public void setDDATE(String DDATE) {
        this.DDATE = DDATE;
    }

    public String getDTIME() {
        return DTIME;
    }

    public void setDTIME(String DTIME) {
        this.DTIME = DTIME;
    }

    public Integer getDNUM() {
        return DNUM;
    }

    public void setDNUM(Integer DNUM) {
        this.DNUM = DNUM;
    }

    public Double getDPRICE_IN() {
        return DPRICE_IN;
    }

    public void setDPRICE_IN(Double DPRICE_IN) {
        this.DPRICE_IN = DPRICE_IN;
    }

    public Double getDPRICE() {
        return DPRICE;
    }

    public void setDPRICE(Double DPRICE) {
        this.DPRICE = DPRICE;
    }

    public Double getDMONEY_SALE() {
        return DMONEY_SALE;
    }

    public void setDMONEY_SALE(Double DMONEY_SALE) {
        this.DMONEY_SALE = DMONEY_SALE;
    }

    public Double getDMONEY_SS() {
        return DMONEY_SS;
    }

    public void setDMONEY_SS(Double DMONEY_SS) {
        this.DMONEY_SS = DMONEY_SS;
    }

    public String getDWORKERNAME() {
        return DWORKERNAME;
    }

    public void setDWORKERNAME(String DWORKERNAME) {
        this.DWORKERNAME = DWORKERNAME;
    }

    public Double getDJF() {
        return DJF;
    }

    public void setDJF(Double DJF) {
        this.DJF = DJF;
    }

    public String getDPROVIDERNAME() {
        return DPROVIDERNAME;
    }

    public void setDPROVIDERNAME(String DPROVIDERNAME) {
        this.DPROVIDERNAME = DPROVIDERNAME;
    }
}
