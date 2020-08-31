package com.smrj.shangmengruanjian.bean;

import java.util.Date;
import java.io.Serializable;


/**
 * @ClassName 
 * @Date 2020-07-06 11:51:31
 * @Version V1.0
 * @Author Mr. Lee
 * @Description: 实体类
**/
public class VThingBean implements Serializable {
    private static final long serialVersionUID = 488159822923353453L;
    
    private String DSHOPNO;
    
    private String DTHINGCODE;
    
    private String DPRICEIN;
    
    private String DPRICEINAVG;
    
    private String DPRICEINNEW;
    
    private String DMASTERBARCODE;
    
    private String DNAME;
    
    private String DSPEC;
    
    private String DPACK;
    
    private String DPROVIDERCODE;
    
    private String DPROVIDERNO;
    
    private String DPROVIDERNAME;
    
    private String DDATELASTSALE;
    
    private String DPRICEINWHOLE;
    
    private String DPRICE;
    
    private boolean DCANCHANGEPRICE;
    
    private boolean DCANPROMOTION;
    
    private String DSHL;
    
    private String DMINCG;
    
    private String DPLANSALE;
    
    private String DMINBH;
    
    private boolean DBHUSEWHOLEBAR;
    
    private boolean DMANAGESTORE;
    
    private String DTHINGSTATE;
    
    private String DPRICEPF;
    
    private String DMLV;
    
    private String DPRICEHY;
    
    private String DJFMODE;
    
    private String DJFBLV;

    private String DHLJF;

    private String DHLVALUE;

    private String DPRICEHL;

    private String DWLMODE;

    private String DBARCODE;

    private String DABC;

    private String DGRADE;

    private boolean DISXX;

    private boolean DISNY;

    private boolean DISBX;

    private String VTHINGBASENAMEDSHL;

    private String DPRODUCTMEMO;

    private String DFUNCTIONMEMO;

    private String DCOLOR;

    private String DSIZE;

    private String DSALERATE;

    private String DRATE;

    private boolean DSEASON;

    private String DPASSTIME;

    private String DINCLUDENUM;

    private String DFULLNAME;

    private String VTHINGBASENAMEDPLANML;

    private String DTHINGTYPE;

    private String DKINDCODE;

    private String DBREEDCODE;

    private String DUNITNAME;

    private String DPRODUCERCODE;

    private String DPRODUCERNO;

    private String DPRODUCERNAME;

    private String DMOBILE;

    private String DLINKMAN;

    private String DBARCODEPRE;

    private String DKINDNO;

    private String DKINDNAME;

    private String DLEVEL;

    private String DPLANNUM;

    private String DNEWGUIDELINE;

    private String DPRICERATE;

    private String DCODE;

    private String DISUSED;

    private String DBARCODETYPE;

    private boolean DCANCHANGEPRICEIN;

    private String DZJM;

    private boolean DISAUTOCG;

    private String DPLANML;

    private String DAREACODE;

    private String DSALEMODE;

    private String DSALETEST;

    private String DSALEMONEY;

    private String DSALEAVG;

    private String DJHAVG;

    private String DPRODUCERAREA;

    private String DAREANOLOCAL;

    private String DAREANAMELOCAL;

    private String DDATEINPUT;

    private Double DNUMSTORE;

    private String DNUM;

    private String DBLV;

    @Override
    public String toString() {
        return "VThingBean{" +
                "DSHOPNO='" + DSHOPNO + '\'' +
                ", DTHINGCODE='" + DTHINGCODE + '\'' +
                ", DPRICEIN='" + DPRICEIN + '\'' +
                ", DPRICEINAVG='" + DPRICEINAVG + '\'' +
                ", DPRICEINNEW='" + DPRICEINNEW + '\'' +
                ", DMASTERBARCODE='" + DMASTERBARCODE + '\'' +
                ", DNAME='" + DNAME + '\'' +
                ", DSPEC='" + DSPEC + '\'' +
                ", DPACK='" + DPACK + '\'' +
                ", DPROVIDERCODE='" + DPROVIDERCODE + '\'' +
                ", DPROVIDERNO='" + DPROVIDERNO + '\'' +
                ", DPROVIDERNAME='" + DPROVIDERNAME + '\'' +
                ", DDATELASTSALE=" + DDATELASTSALE +
                ", DPRICEINWHOLE='" + DPRICEINWHOLE + '\'' +
                ", DPRICE='" + DPRICE + '\'' +
                ", DCANCHANGEPRICE=" + DCANCHANGEPRICE +
                ", DCANPROMOTION=" + DCANPROMOTION +
                ", DSHL='" + DSHL + '\'' +
                ", DMINCG='" + DMINCG + '\'' +
                ", DPLANSALE='" + DPLANSALE + '\'' +
                ", DMINBH='" + DMINBH + '\'' +
                ", DBHUSEWHOLEBAR=" + DBHUSEWHOLEBAR +
                ", DMANAGESTORE=" + DMANAGESTORE +
                ", DTHINGSTATE='" + DTHINGSTATE + '\'' +
                ", DPRICEPF='" + DPRICEPF + '\'' +
                ", DMLV='" + DMLV + '\'' +
                ", DPRICEHY='" + DPRICEHY + '\'' +
                ", DJFMODE='" + DJFMODE + '\'' +
                ", DJFBLV=" + DJFBLV +
                ", DHLJF='" + DHLJF + '\'' +
                ", DHLVALUE='" + DHLVALUE + '\'' +
                ", DPRICEHL='" + DPRICEHL + '\'' +
                ", DWLMODE='" + DWLMODE + '\'' +
                ", DBARCODE='" + DBARCODE + '\'' +
                ", DABC='" + DABC + '\'' +
                ", DGRADE='" + DGRADE + '\'' +
                ", DISXX=" + DISXX +
                ", DISNY=" + DISNY +
                ", DISBX=" + DISBX +
                ", VTHINGBASENAMEDSHL='" + VTHINGBASENAMEDSHL + '\'' +
                ", DPRODUCTMEMO='" + DPRODUCTMEMO + '\'' +
                ", DFUNCTIONMEMO='" + DFUNCTIONMEMO + '\'' +
                ", DCOLOR='" + DCOLOR + '\'' +
                ", DSIZE='" + DSIZE + '\'' +
                ", DSALERATE='" + DSALERATE + '\'' +
                ", DRATE='" + DRATE + '\'' +
                ", DSEASON=" + DSEASON +
                ", DPASSTIME='" + DPASSTIME + '\'' +
                ", DINCLUDENUM='" + DINCLUDENUM + '\'' +
                ", DFULLNAME='" + DFULLNAME + '\'' +
                ", VTHINGBASENAMEDPLANML='" + VTHINGBASENAMEDPLANML + '\'' +
                ", DTHINGTYPE='" + DTHINGTYPE + '\'' +
                ", DKINDCODE='" + DKINDCODE + '\'' +
                ", DBREEDCODE='" + DBREEDCODE + '\'' +
                ", DUNITNAME='" + DUNITNAME + '\'' +
                ", DPRODUCERCODE=" + DPRODUCERCODE +
                ", DPRODUCERNO='" + DPRODUCERNO + '\'' +
                ", DPRODUCERNAME='" + DPRODUCERNAME + '\'' +
                ", DMOBILE='" + DMOBILE + '\'' +
                ", DLINKMAN='" + DLINKMAN + '\'' +
                ", DBARCODEPRE='" + DBARCODEPRE + '\'' +
                ", DKINDNO='" + DKINDNO + '\'' +
                ", DKINDNAME='" + DKINDNAME + '\'' +
                ", DLEVEL='" + DLEVEL + '\'' +
                ", DPLANNUM=" + DPLANNUM +
                ", DNEWGUIDELINE='" + DNEWGUIDELINE + '\'' +
                ", DPRICERATE='" + DPRICERATE + '\'' +
                ", DCODE='" + DCODE + '\'' +
                ", DISUSED='" + DISUSED + '\'' +
                ", DBARCODETYPE='" + DBARCODETYPE + '\'' +
                ", DCANCHANGEPRICEIN=" + DCANCHANGEPRICEIN +
                ", DZJM='" + DZJM + '\'' +
                ", DISAUTOCG=" + DISAUTOCG +
                ", DPLANML='" + DPLANML + '\'' +
                ", DAREACODE=" + DAREACODE +
                ", DSALEMODE='" + DSALEMODE + '\'' +
                ", DSALETEST='" + DSALETEST + '\'' +
                ", DSALEMONEY='" + DSALEMONEY + '\'' +
                ", DSALEAVG='" + DSALEAVG + '\'' +
                ", DJHAVG='" + DJHAVG + '\'' +
                ", DPRODUCERAREA='" + DPRODUCERAREA + '\'' +
                ", DAREANOLOCAL='" + DAREANOLOCAL + '\'' +
                ", DAREANAMELOCAL='" + DAREANAMELOCAL + '\'' +
                ", DDATEINPUT=" + DDATEINPUT +
                ", DNUMSTORE=" + DNUMSTORE +
                ", DNUM=" + DNUM +
                ", DBLV=" + DBLV +
                '}';
    }

    public String getDSHOPNO() {
        return DSHOPNO;
    }

    public void setDSHOPNO(String DSHOPNO) {
        this.DSHOPNO = DSHOPNO;
    }

    public String getDTHINGCODE() {
        return DTHINGCODE;
    }

    public void setDTHINGCODE(String DTHINGCODE) {
        this.DTHINGCODE = DTHINGCODE;
    }

    public String getDPRICEIN() {
        return DPRICEIN;
    }

    public void setDPRICEIN(String DPRICEIN) {
        this.DPRICEIN = DPRICEIN;
    }

    public String getDPRICEINAVG() {
        return DPRICEINAVG;
    }

    public void setDPRICEINAVG(String DPRICEINAVG) {
        this.DPRICEINAVG = DPRICEINAVG;
    }

    public String getDPRICEINNEW() {
        return DPRICEINNEW;
    }

    public void setDPRICEINNEW(String DPRICEINNEW) {
        this.DPRICEINNEW = DPRICEINNEW;
    }

    public String getDMASTERBARCODE() {
        return DMASTERBARCODE;
    }

    public void setDMASTERBARCODE(String DMASTERBARCODE) {
        this.DMASTERBARCODE = DMASTERBARCODE;
    }

    public String getDNAME() {
        return DNAME;
    }

    public void setDNAME(String DNAME) {
        this.DNAME = DNAME;
    }

    public String getDSPEC() {
        return DSPEC;
    }

    public void setDSPEC(String DSPEC) {
        this.DSPEC = DSPEC;
    }

    public String getDPACK() {
        return DPACK;
    }

    public void setDPACK(String DPACK) {
        this.DPACK = DPACK;
    }

    public String getDPROVIDERCODE() {
        return DPROVIDERCODE;
    }

    public void setDPROVIDERCODE(String DPROVIDERCODE) {
        this.DPROVIDERCODE = DPROVIDERCODE;
    }

    public String getDPROVIDERNO() {
        return DPROVIDERNO;
    }

    public void setDPROVIDERNO(String DPROVIDERNO) {
        this.DPROVIDERNO = DPROVIDERNO;
    }

    public String getDPROVIDERNAME() {
        return DPROVIDERNAME;
    }

    public void setDPROVIDERNAME(String DPROVIDERNAME) {
        this.DPROVIDERNAME = DPROVIDERNAME;
    }

    public String getDDATELASTSALE() {
        return DDATELASTSALE;
    }

    public void setDDATELASTSALE(String DDATELASTSALE) {
        this.DDATELASTSALE = DDATELASTSALE;
    }

    public String getDPRICEINWHOLE() {
        return DPRICEINWHOLE;
    }

    public void setDPRICEINWHOLE(String DPRICEINWHOLE) {
        this.DPRICEINWHOLE = DPRICEINWHOLE;
    }

    public String getDPRICE() {
        return DPRICE;
    }

    public void setDPRICE(String DPRICE) {
        this.DPRICE = DPRICE;
    }

    public boolean isDCANCHANGEPRICE() {
        return DCANCHANGEPRICE;
    }

    public void setDCANCHANGEPRICE(boolean DCANCHANGEPRICE) {
        this.DCANCHANGEPRICE = DCANCHANGEPRICE;
    }

    public boolean isDCANPROMOTION() {
        return DCANPROMOTION;
    }

    public void setDCANPROMOTION(boolean DCANPROMOTION) {
        this.DCANPROMOTION = DCANPROMOTION;
    }

    public String getDSHL() {
        return DSHL;
    }

    public void setDSHL(String DSHL) {
        this.DSHL = DSHL;
    }

    public String getDMINCG() {
        return DMINCG;
    }

    public void setDMINCG(String DMINCG) {
        this.DMINCG = DMINCG;
    }

    public String getDPLANSALE() {
        return DPLANSALE;
    }

    public void setDPLANSALE(String DPLANSALE) {
        this.DPLANSALE = DPLANSALE;
    }

    public String getDMINBH() {
        return DMINBH;
    }

    public void setDMINBH(String DMINBH) {
        this.DMINBH = DMINBH;
    }

    public boolean isDBHUSEWHOLEBAR() {
        return DBHUSEWHOLEBAR;
    }

    public void setDBHUSEWHOLEBAR(boolean DBHUSEWHOLEBAR) {
        this.DBHUSEWHOLEBAR = DBHUSEWHOLEBAR;
    }

    public boolean isDMANAGESTORE() {
        return DMANAGESTORE;
    }

    public void setDMANAGESTORE(boolean DMANAGESTORE) {
        this.DMANAGESTORE = DMANAGESTORE;
    }

    public String getDTHINGSTATE() {
        return DTHINGSTATE;
    }

    public void setDTHINGSTATE(String DTHINGSTATE) {
        this.DTHINGSTATE = DTHINGSTATE;
    }

    public String getDPRICEPF() {
        return DPRICEPF;
    }

    public void setDPRICEPF(String DPRICEPF) {
        this.DPRICEPF = DPRICEPF;
    }

    public String getDMLV() {
        return DMLV;
    }

    public void setDMLV(String DMLV) {
        this.DMLV = DMLV;
    }

    public String getDPRICEHY() {
        return DPRICEHY;
    }

    public void setDPRICEHY(String DPRICEHY) {
        this.DPRICEHY = DPRICEHY;
    }

    public String getDJFMODE() {
        return DJFMODE;
    }

    public void setDJFMODE(String DJFMODE) {
        this.DJFMODE = DJFMODE;
    }

    public String getDJFBLV() {
        return DJFBLV;
    }

    public void setDJFBLV(String DJFBLV) {
        this.DJFBLV = DJFBLV;
    }

    public String getDHLJF() {
        return DHLJF;
    }

    public void setDHLJF(String DHLJF) {
        this.DHLJF = DHLJF;
    }

    public String getDHLVALUE() {
        return DHLVALUE;
    }

    public void setDHLVALUE(String DHLVALUE) {
        this.DHLVALUE = DHLVALUE;
    }

    public String getDPRICEHL() {
        return DPRICEHL;
    }

    public void setDPRICEHL(String DPRICEHL) {
        this.DPRICEHL = DPRICEHL;
    }

    public String getDWLMODE() {
        return DWLMODE;
    }

    public void setDWLMODE(String DWLMODE) {
        this.DWLMODE = DWLMODE;
    }

    public String getDBARCODE() {
        return DBARCODE;
    }

    public void setDBARCODE(String DBARCODE) {
        this.DBARCODE = DBARCODE;
    }

    public String getDABC() {
        return DABC;
    }

    public void setDABC(String DABC) {
        this.DABC = DABC;
    }

    public String getDGRADE() {
        return DGRADE;
    }

    public void setDGRADE(String DGRADE) {
        this.DGRADE = DGRADE;
    }

    public boolean isDISXX() {
        return DISXX;
    }

    public void setDISXX(boolean DISXX) {
        this.DISXX = DISXX;
    }

    public boolean isDISNY() {
        return DISNY;
    }

    public void setDISNY(boolean DISNY) {
        this.DISNY = DISNY;
    }

    public boolean isDISBX() {
        return DISBX;
    }

    public void setDISBX(boolean DISBX) {
        this.DISBX = DISBX;
    }

    public String getVTHINGBASENAMEDSHL() {
        return VTHINGBASENAMEDSHL;
    }

    public void setVTHINGBASENAMEDSHL(String VTHINGBASENAMEDSHL) {
        this.VTHINGBASENAMEDSHL = VTHINGBASENAMEDSHL;
    }

    public String getDPRODUCTMEMO() {
        return DPRODUCTMEMO;
    }

    public void setDPRODUCTMEMO(String DPRODUCTMEMO) {
        this.DPRODUCTMEMO = DPRODUCTMEMO;
    }

    public String getDFUNCTIONMEMO() {
        return DFUNCTIONMEMO;
    }

    public void setDFUNCTIONMEMO(String DFUNCTIONMEMO) {
        this.DFUNCTIONMEMO = DFUNCTIONMEMO;
    }

    public String getDCOLOR() {
        return DCOLOR;
    }

    public void setDCOLOR(String DCOLOR) {
        this.DCOLOR = DCOLOR;
    }

    public String getDSIZE() {
        return DSIZE;
    }

    public void setDSIZE(String DSIZE) {
        this.DSIZE = DSIZE;
    }

    public String getDSALERATE() {
        return DSALERATE;
    }

    public void setDSALERATE(String DSALERATE) {
        this.DSALERATE = DSALERATE;
    }

    public String getDRATE() {
        return DRATE;
    }

    public void setDRATE(String DRATE) {
        this.DRATE = DRATE;
    }

    public boolean isDSEASON() {
        return DSEASON;
    }

    public void setDSEASON(boolean DSEASON) {
        this.DSEASON = DSEASON;
    }

    public String getDPASSTIME() {
        return DPASSTIME;
    }

    public void setDPASSTIME(String DPASSTIME) {
        this.DPASSTIME = DPASSTIME;
    }

    public String getDINCLUDENUM() {
        return DINCLUDENUM;
    }

    public void setDINCLUDENUM(String DINCLUDENUM) {
        this.DINCLUDENUM = DINCLUDENUM;
    }

    public String getDFULLNAME() {
        return DFULLNAME;
    }

    public void setDFULLNAME(String DFULLNAME) {
        this.DFULLNAME = DFULLNAME;
    }

    public String getVTHINGBASENAMEDPLANML() {
        return VTHINGBASENAMEDPLANML;
    }

    public void setVTHINGBASENAMEDPLANML(String VTHINGBASENAMEDPLANML) {
        this.VTHINGBASENAMEDPLANML = VTHINGBASENAMEDPLANML;
    }

    public String getDTHINGTYPE() {
        return DTHINGTYPE;
    }

    public void setDTHINGTYPE(String DTHINGTYPE) {
        this.DTHINGTYPE = DTHINGTYPE;
    }

    public String getDKINDCODE() {
        return DKINDCODE;
    }

    public void setDKINDCODE(String DKINDCODE) {
        this.DKINDCODE = DKINDCODE;
    }

    public String getDBREEDCODE() {
        return DBREEDCODE;
    }

    public void setDBREEDCODE(String DBREEDCODE) {
        this.DBREEDCODE = DBREEDCODE;
    }

    public String getDUNITNAME() {
        return DUNITNAME;
    }

    public void setDUNITNAME(String DUNITNAME) {
        this.DUNITNAME = DUNITNAME;
    }

    public String getDPRODUCERCODE() {
        return DPRODUCERCODE;
    }

    public void setDPRODUCERCODE(String DPRODUCERCODE) {
        this.DPRODUCERCODE = DPRODUCERCODE;
    }

    public String getDPRODUCERNO() {
        return DPRODUCERNO;
    }

    public void setDPRODUCERNO(String DPRODUCERNO) {
        this.DPRODUCERNO = DPRODUCERNO;
    }

    public String getDPRODUCERNAME() {
        return DPRODUCERNAME;
    }

    public void setDPRODUCERNAME(String DPRODUCERNAME) {
        this.DPRODUCERNAME = DPRODUCERNAME;
    }

    public String getDMOBILE() {
        return DMOBILE;
    }

    public void setDMOBILE(String DMOBILE) {
        this.DMOBILE = DMOBILE;
    }

    public String getDLINKMAN() {
        return DLINKMAN;
    }

    public void setDLINKMAN(String DLINKMAN) {
        this.DLINKMAN = DLINKMAN;
    }

    public String getDBARCODEPRE() {
        return DBARCODEPRE;
    }

    public void setDBARCODEPRE(String DBARCODEPRE) {
        this.DBARCODEPRE = DBARCODEPRE;
    }

    public String getDKINDNO() {
        return DKINDNO;
    }

    public void setDKINDNO(String DKINDNO) {
        this.DKINDNO = DKINDNO;
    }

    public String getDKINDNAME() {
        return DKINDNAME;
    }

    public void setDKINDNAME(String DKINDNAME) {
        this.DKINDNAME = DKINDNAME;
    }

    public String getDLEVEL() {
        return DLEVEL;
    }

    public void setDLEVEL(String DLEVEL) {
        this.DLEVEL = DLEVEL;
    }

    public String getDPLANNUM() {
        return DPLANNUM;
    }

    public void setDPLANNUM(String DPLANNUM) {
        this.DPLANNUM = DPLANNUM;
    }

    public String getDNEWGUIDELINE() {
        return DNEWGUIDELINE;
    }

    public void setDNEWGUIDELINE(String DNEWGUIDELINE) {
        this.DNEWGUIDELINE = DNEWGUIDELINE;
    }

    public String getDPRICERATE() {
        return DPRICERATE;
    }

    public void setDPRICERATE(String DPRICERATE) {
        this.DPRICERATE = DPRICERATE;
    }

    public String getDCODE() {
        return DCODE;
    }

    public void setDCODE(String DCODE) {
        this.DCODE = DCODE;
    }

    public String getDISUSED() {
        return DISUSED;
    }

    public void setDISUSED(String DISUSED) {
        this.DISUSED = DISUSED;
    }

    public String getDBARCODETYPE() {
        return DBARCODETYPE;
    }

    public void setDBARCODETYPE(String DBARCODETYPE) {
        this.DBARCODETYPE = DBARCODETYPE;
    }

    public boolean isDCANCHANGEPRICEIN() {
        return DCANCHANGEPRICEIN;
    }

    public void setDCANCHANGEPRICEIN(boolean DCANCHANGEPRICEIN) {
        this.DCANCHANGEPRICEIN = DCANCHANGEPRICEIN;
    }

    public String getDZJM() {
        return DZJM;
    }

    public void setDZJM(String DZJM) {
        this.DZJM = DZJM;
    }

    public boolean isDISAUTOCG() {
        return DISAUTOCG;
    }

    public void setDISAUTOCG(boolean DISAUTOCG) {
        this.DISAUTOCG = DISAUTOCG;
    }

    public String getDPLANML() {
        return DPLANML;
    }

    public void setDPLANML(String DPLANML) {
        this.DPLANML = DPLANML;
    }

    public String getDAREACODE() {
        return DAREACODE;
    }

    public void setDAREACODE(String DAREACODE) {
        this.DAREACODE = DAREACODE;
    }

    public String getDSALEMODE() {
        return DSALEMODE;
    }

    public void setDSALEMODE(String DSALEMODE) {
        this.DSALEMODE = DSALEMODE;
    }

    public String getDSALETEST() {
        return DSALETEST;
    }

    public void setDSALETEST(String DSALETEST) {
        this.DSALETEST = DSALETEST;
    }

    public String getDSALEMONEY() {
        return DSALEMONEY;
    }

    public void setDSALEMONEY(String DSALEMONEY) {
        this.DSALEMONEY = DSALEMONEY;
    }

    public String getDSALEAVG() {
        return DSALEAVG;
    }

    public void setDSALEAVG(String DSALEAVG) {
        this.DSALEAVG = DSALEAVG;
    }

    public String getDJHAVG() {
        return DJHAVG;
    }

    public void setDJHAVG(String DJHAVG) {
        this.DJHAVG = DJHAVG;
    }

    public String getDPRODUCERAREA() {
        return DPRODUCERAREA;
    }

    public void setDPRODUCERAREA(String DPRODUCERAREA) {
        this.DPRODUCERAREA = DPRODUCERAREA;
    }

    public String getDAREANOLOCAL() {
        return DAREANOLOCAL;
    }

    public void setDAREANOLOCAL(String DAREANOLOCAL) {
        this.DAREANOLOCAL = DAREANOLOCAL;
    }

    public String getDAREANAMELOCAL() {
        return DAREANAMELOCAL;
    }

    public void setDAREANAMELOCAL(String DAREANAMELOCAL) {
        this.DAREANAMELOCAL = DAREANAMELOCAL;
    }

    public String getDDATEINPUT() {
        return DDATEINPUT;
    }

    public void setDDATEINPUT(String DDATEINPUT) {
        this.DDATEINPUT = DDATEINPUT;
    }

    public Double getDNUMSTORE() {
        return DNUMSTORE;
    }

    public void setDNUMSTORE(Double DNUMSTORE) {
        this.DNUMSTORE = DNUMSTORE;
    }

    public String getDNUM() {
        return DNUM;
    }

    public void setDNUM(String DNUM) {
        this.DNUM = DNUM;
    }

    public String getDBLV() {
        return DBLV;
    }

    public void setDBLV(String DBLV) {
        this.DBLV = DBLV;
    }
}