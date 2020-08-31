package com.smrj.shangmengruanjian.bean;

public class OrderGoodsBean {

    /**
     * DRATE : 0
     * DKINDCODE : 679
     * DBREEDCODE :
     * DISXX : false
     * DSEASON : false
     * DMASTERBARCODE : 6901028169639
     * DPRODUCERCODE :
     * DTHINGTYPE : 普通商品
     * DISUSED : 1
     * DISNY : false
     * DSPEC : *10
     * DPROVIDERCODE : 100039
     * DMEMO :
     * DPRICEIN : 9.33
     * DINCLUDENUM : 1
     * DSIZE :
     * DABC :
     * DCOLOR :
     * DTHINGCODE : 11823
     * DPRODUCTMEMO :
     * DISBX : false
     * DBARCODE : 6901028169639
     * DGRADE :
     * DFULLNAME : 黄金叶（帝豪）
     * DCODE : 30504000459
     * DSALERATE : 0
     * DNAME : 黄金叶（帝豪）
     * DPICTURE :
     * DSHL :
     * DFUNCTIONMEMO :
     * DPASSTIME : 0
     * DBARCODETYPE : 主条码
     * DZJM : HJYDH
     * DOTHERCODE :
     * DPACK :
     * DUNITNAME : 条
     * DBIGPACK : 盒
     */

    private int DRATE;
    private int DKINDCODE;
    private String DBREEDCODE;
    private boolean DISXX;
    private boolean DSEASON;
    private String DMASTERBARCODE;
    private String DPRODUCERCODE;
    private String DTHINGTYPE;
    private int DISUSED;
    private boolean DISNY;
    private String DSPEC;
    private int DPROVIDERCODE;
    private String DMEMO;
    private double DPRICEIN;
    private double DPRICEFC;
    private int DINCLUDENUM;
    private String DSIZE;
    private String DABC;
    private String DCOLOR;
    private int DTHINGCODE;
    private String DPRODUCTMEMO;
    private boolean DISBX;
    private String DBARCODE;
    private String DGRADE;
    private String DFULLNAME;
    private String DCODE;
    private int DSALERATE;
    private String DNAME;
    private String DPICTURE;
    private String DSHL;
    private String DFUNCTIONMEMO;
    private int DPASSTIME;
    private String DBARCODETYPE;
    private String DZJM;
    private String DOTHERCODE;
    private String DPACK;
    private String DUNITNAME;
    private String DBIGPACK;
    private int DSELECTNUM;
    private double ticketMoney;
    private double DNUM;

    @Override
    public String toString() {
        return "OrderGoodsBean{" +
                "DRATE=" + DRATE +
                ", DKINDCODE=" + DKINDCODE +
                ", DBREEDCODE='" + DBREEDCODE + '\'' +
                ", DISXX=" + DISXX +
                ", DSEASON=" + DSEASON +
                ", DMASTERBARCODE='" + DMASTERBARCODE + '\'' +
                ", DPRODUCERCODE='" + DPRODUCERCODE + '\'' +
                ", DTHINGTYPE='" + DTHINGTYPE + '\'' +
                ", DISUSED=" + DISUSED +
                ", DISNY=" + DISNY +
                ", DSPEC='" + DSPEC + '\'' +
                ", DPROVIDERCODE=" + DPROVIDERCODE +
                ", DMEMO='" + DMEMO + '\'' +
                ", DPRICEIN=" + DPRICEIN +
                ", DPRICEFC=" + DPRICEFC +
                ", DINCLUDENUM=" + DINCLUDENUM +
                ", DSIZE='" + DSIZE + '\'' +
                ", DABC='" + DABC + '\'' +
                ", DCOLOR='" + DCOLOR + '\'' +
                ", DTHINGCODE=" + DTHINGCODE +
                ", DPRODUCTMEMO='" + DPRODUCTMEMO + '\'' +
                ", DISBX=" + DISBX +
                ", DBARCODE='" + DBARCODE + '\'' +
                ", DGRADE='" + DGRADE + '\'' +
                ", DFULLNAME='" + DFULLNAME + '\'' +
                ", DCODE='" + DCODE + '\'' +
                ", DSALERATE=" + DSALERATE +
                ", DNAME='" + DNAME + '\'' +
                ", DPICTURE='" + DPICTURE + '\'' +
                ", DSHL='" + DSHL + '\'' +
                ", DFUNCTIONMEMO='" + DFUNCTIONMEMO + '\'' +
                ", DPASSTIME=" + DPASSTIME +
                ", DBARCODETYPE='" + DBARCODETYPE + '\'' +
                ", DZJM='" + DZJM + '\'' +
                ", DOTHERCODE='" + DOTHERCODE + '\'' +
                ", DPACK='" + DPACK + '\'' +
                ", DUNITNAME='" + DUNITNAME + '\'' +
                ", DBIGPACK='" + DBIGPACK + '\'' +
                ", DSELECTNUM=" + DSELECTNUM +
                ", ticketMoney=" + ticketMoney +
                ", DNUM=" + DNUM +
                '}';
    }

    public double getDPRICEFC() {
        return DPRICEFC;
    }

    public void setDPRICEFC(double DPRICEFC) {
        this.DPRICEFC = DPRICEFC;
    }

    public int getDRATE() {
        return DRATE;
    }

    public void setDRATE(int DRATE) {
        this.DRATE = DRATE;
    }

    public int getDKINDCODE() {
        return DKINDCODE;
    }

    public void setDKINDCODE(int DKINDCODE) {
        this.DKINDCODE = DKINDCODE;
    }

    public String getDBREEDCODE() {
        return DBREEDCODE;
    }

    public void setDBREEDCODE(String DBREEDCODE) {
        this.DBREEDCODE = DBREEDCODE;
    }

    public boolean isDISXX() {
        return DISXX;
    }

    public void setDISXX(boolean DISXX) {
        this.DISXX = DISXX;
    }

    public boolean isDSEASON() {
        return DSEASON;
    }

    public void setDSEASON(boolean DSEASON) {
        this.DSEASON = DSEASON;
    }

    public String getDMASTERBARCODE() {
        return DMASTERBARCODE;
    }

    public void setDMASTERBARCODE(String DMASTERBARCODE) {
        this.DMASTERBARCODE = DMASTERBARCODE;
    }

    public String getDPRODUCERCODE() {
        return DPRODUCERCODE;
    }

    public void setDPRODUCERCODE(String DPRODUCERCODE) {
        this.DPRODUCERCODE = DPRODUCERCODE;
    }

    public String getDTHINGTYPE() {
        return DTHINGTYPE;
    }

    public void setDTHINGTYPE(String DTHINGTYPE) {
        this.DTHINGTYPE = DTHINGTYPE;
    }

    public int getDISUSED() {
        return DISUSED;
    }

    public void setDISUSED(int DISUSED) {
        this.DISUSED = DISUSED;
    }

    public boolean isDISNY() {
        return DISNY;
    }

    public void setDISNY(boolean DISNY) {
        this.DISNY = DISNY;
    }

    public String getDSPEC() {
        return DSPEC;
    }

    public void setDSPEC(String DSPEC) {
        this.DSPEC = DSPEC;
    }

    public int getDPROVIDERCODE() {
        return DPROVIDERCODE;
    }

    public void setDPROVIDERCODE(int DPROVIDERCODE) {
        this.DPROVIDERCODE = DPROVIDERCODE;
    }

    public String getDMEMO() {
        return DMEMO;
    }

    public void setDMEMO(String DMEMO) {
        this.DMEMO = DMEMO;
    }

    public double getDPRICEIN() {
        return DPRICEIN;
    }

    public void setDPRICEIN(double DPRICEIN) {
        this.DPRICEIN = DPRICEIN;
    }

    public int getDINCLUDENUM() {
        return DINCLUDENUM;
    }

    public void setDINCLUDENUM(int DINCLUDENUM) {
        this.DINCLUDENUM = DINCLUDENUM;
    }

    public String getDSIZE() {
        return DSIZE;
    }

    public void setDSIZE(String DSIZE) {
        this.DSIZE = DSIZE;
    }

    public String getDABC() {
        return DABC;
    }

    public void setDABC(String DABC) {
        this.DABC = DABC;
    }

    public String getDCOLOR() {
        return DCOLOR;
    }

    public void setDCOLOR(String DCOLOR) {
        this.DCOLOR = DCOLOR;
    }

    public int getDTHINGCODE() {
        return DTHINGCODE;
    }

    public void setDTHINGCODE(int DTHINGCODE) {
        this.DTHINGCODE = DTHINGCODE;
    }

    public String getDPRODUCTMEMO() {
        return DPRODUCTMEMO;
    }

    public void setDPRODUCTMEMO(String DPRODUCTMEMO) {
        this.DPRODUCTMEMO = DPRODUCTMEMO;
    }

    public boolean isDISBX() {
        return DISBX;
    }

    public void setDISBX(boolean DISBX) {
        this.DISBX = DISBX;
    }

    public String getDBARCODE() {
        return DBARCODE;
    }

    public void setDBARCODE(String DBARCODE) {
        this.DBARCODE = DBARCODE;
    }

    public String getDGRADE() {
        return DGRADE;
    }

    public void setDGRADE(String DGRADE) {
        this.DGRADE = DGRADE;
    }

    public String getDFULLNAME() {
        return DFULLNAME;
    }

    public void setDFULLNAME(String DFULLNAME) {
        this.DFULLNAME = DFULLNAME;
    }

    public String getDCODE() {
        return DCODE;
    }

    public void setDCODE(String DCODE) {
        this.DCODE = DCODE;
    }

    public int getDSALERATE() {
        return DSALERATE;
    }

    public void setDSALERATE(int DSALERATE) {
        this.DSALERATE = DSALERATE;
    }

    public String getDNAME() {
        return DNAME;
    }

    public void setDNAME(String DNAME) {
        this.DNAME = DNAME;
    }

    public String getDPICTURE() {
        return DPICTURE;
    }

    public void setDPICTURE(String DPICTURE) {
        this.DPICTURE = DPICTURE;
    }

    public String getDSHL() {
        return DSHL;
    }

    public void setDSHL(String DSHL) {
        this.DSHL = DSHL;
    }

    public String getDFUNCTIONMEMO() {
        return DFUNCTIONMEMO;
    }

    public void setDFUNCTIONMEMO(String DFUNCTIONMEMO) {
        this.DFUNCTIONMEMO = DFUNCTIONMEMO;
    }

    public int getDPASSTIME() {
        return DPASSTIME;
    }

    public void setDPASSTIME(int DPASSTIME) {
        this.DPASSTIME = DPASSTIME;
    }

    public String getDBARCODETYPE() {
        return DBARCODETYPE;
    }

    public void setDBARCODETYPE(String DBARCODETYPE) {
        this.DBARCODETYPE = DBARCODETYPE;
    }

    public String getDZJM() {
        return DZJM;
    }

    public void setDZJM(String DZJM) {
        this.DZJM = DZJM;
    }

    public String getDOTHERCODE() {
        return DOTHERCODE;
    }

    public void setDOTHERCODE(String DOTHERCODE) {
        this.DOTHERCODE = DOTHERCODE;
    }

    public String getDPACK() {
        return DPACK;
    }

    public void setDPACK(String DPACK) {
        this.DPACK = DPACK;
    }

    public String getDUNITNAME() {
        return DUNITNAME;
    }

    public void setDUNITNAME(String DUNITNAME) {
        this.DUNITNAME = DUNITNAME;
    }

    public String getDBIGPACK() {
        return DBIGPACK;
    }

    public void setDBIGPACK(String DBIGPACK) {
        this.DBIGPACK = DBIGPACK;
    }

    public int getDSELECTNUM() {
        return DSELECTNUM;
    }

    public void setDSELECTNUM(int DSELECTNUM) {
        this.DSELECTNUM = DSELECTNUM;
    }

    public double getTicketMoney() {
        return ticketMoney;
    }

    public void setTicketMoney(double ticketMoney) {
        this.ticketMoney = ticketMoney;
    }

    public double getDNUM() {
        return DNUM;
    }

    public void setDNUM(double DNUM) {
        this.DNUM = DNUM;
    }

}
