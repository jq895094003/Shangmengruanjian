package com.smrj.shangmengruanjian.util;


public class MyUrl {
    //裴营鑫乐福         http://39.96.34.144:8080/PYXLFAPP
    //林州欣起源         http://101.36.150.148:8080/LZXQYZDT
    //邓州168便利店      http://39.96.34.144:8080/DZ168APP
    //商蒙测试           http://39.96.60.156:8080/SHANGMENGZDT
    //南阳东菇生鲜       http://39.96.34.144:8080/NYDGSXZDT
    //盘州舒麦          http://47.104.205.74:8080/PZSMZDT
    //郏县富轻          http://101.36.150.148:8080/JXFQZDT
    //漯河喜盈门          http://47.93.17.175:8080/LHXYMZDT
    //唐河黑龙镇永鲜e家    http://47.93.17.175:8080/THHLZZDT
    //贵州遵义家家乐     http://47.93.17.175:8080/ZYJJLZDT

    //注册的app名字
    public static final String appName = "掌店通";
    //检测更新的名字
    public static final String updateAppName = "贵州遵义家家乐";
    //后台接口访问地址
    public static final String url = "http://47.93.17.175:8080/ZYJJLZDT";
    //本地测试接口
//    public static final String url = "http://192.168.1.78:8081/shangmengapp";
    //检测更新请求地址
    public static final String updateUrl = "http://39.96.60.156:8080/APPMANAGE";
    //    public static final String updateUrl = "http://192.168.1.80:8082/APPMANAGE";
    //下载App地址
    public static final String updateuIp = "http://39.96.60.156:8080";



    public String getCommitPandianOrderInfo() {
        return url + "/order/OrderPCDZ.action";
    }

    public String getDeletePandianOrderInfo() {
        return url + "/order/delOrderPC.action";
    }

    public String getSavePandianOrderInfo() {
        return url + "/order/upOrderPCNUM.action";
    }

    public String getFindPandianOrderInfo() {
        return url + "/order/findOrderPCMX.action";
    }

    public String getRunUpAnAccountCommitUrl() {
        return url + "/order/ticketsjtjjzs.action";
    }

    public String getAllItemDeleteUrl() {
        return url + "/order/delTJ.action";
    }

    public String getRunUpAnAccountAllItemUrl() {
        return url + "/order/findAllItems.action";
    }

    public String getRunUpAnAccountUrl() {
        return url + "/order/findAllsjtj.action";
    }

    public String getFindAllPandianOrder() {
        return url + "/order/findOrderPC.action";
    }

    public String getUuidQueryMethod() {
        return updateUrl + "/app/selphonenum.action";
    }

    public String getLoginMethod() {
        return url + "/user/login.action";
    }

    public String getUpDateQueryMethod() {
        return updateUrl + "/app/uploadapp.action";
    }

    public String getDownLoadMethod() {
        return updateuIp + "/apk/";
    }

    public String getLoginstatusMethod() {
        return updateUrl + "/app/updateuserstate.action";
    }

    public String getRegisterMethod() {
        return updateUrl + "/app/logon.action";
    }

    public String getExitMethod() {
        return updateUrl + "/app/session.action";
    }

    public String getFindSaleCustomerwater() {
        return url + "/daily/findSaleCustomerwater.action";
    }

    public String getFindSaleSaler() {
        return url + "/daily/findSaleSaler.action";
    }

    public String getFindSaleKind() {
        return url + "/daily/findSaleKind.action";
    }

    public String getFindFXStorerealtime() {
        return url + "/daily/findFXStorerealtime.action";
    }

    public String getFindSaleArea() {
        return url + "/daily/findSaleArea.action";
    }

    public String getFindSaleThing() {
        return url + "/daily/findSaleThing.action";
    }

    public String getFindKH() {
        return url + "/user/findKH.action";
    }

    public String getFindThingModel() {
        return url + "/order/findThingModel.action";
    }

    public String getFindAllQX() {
        return url + "/order/findAllQX.action";
    }
    public String getVthingByShopNo() {
        return url + "/vThing/queryAll.action";
    }

    public String getFindAllProvider() {
        return url + "/order/findAllProvider.action";
    }

    public String getFindAllDepot() {
        return url + "/order/findAllDepot.action";
    }

    public String getInsOrderYS() {
        return url + "/order/insOrderYS.action";
    }

    public String getOrderjj() {
        return url + "/order/Orderjj.action";
    }

    public String getOrderls() {
        return url + "/order/Orderls.action";
    }

    public String getSelectSales() {
        return url + "/sales/selectSales.action";
    }

    public String getOrderPCNEW() {
        return url + "/order/OrderPCNEW.action";
    }

    public String getFindArea() {
        return url + "/order/findArea.action";
    }

    public String getFindKind() {
        return url + "/order/findKind.action";
    }

    public String getFindLastKind() {
        return url + "/order/findLastKind.action";
    }

    public String getFindUnit() {
        return url + "/order/findUnit.action";
    }

    public String getFindGoodsType() {
        return url + "/order/findGoodsType.action";
    }

    public String getFindShopArea() {
        return url + "/order/findShopArea.action";
    }

    public String getInsOrderXPJD() {
        return url + "/order/insOrderXPJD.action";
    }

    public String getProductUpdateBarcode() {
        return url + "/vThingBaseName/selectOne.action";
    }

    public String getNewTicketNo() {
        return url + "/ticket/newTicketNo.action";
    }

    public String getFindGoodInfo() {
        return url + "/ticket/findGoodInfo.action";
    }

    public String getInsertTable() {
        return url + "/ticket/insertTable.action";
    }

    public String getFindAllData() {
        return url + "/ticket/findAllData.action";
    }

    public String getReturnFactoryBarcode() {
        return url + "/ticket/findGoodInfoAddDNum.action";
    }

    public String getInsertHYCard() {
        return url + "/ticket/addNewMembershipCard.action";
    }

    public String getSearchFirstkindMonth() {
        return url + "/ticket/searchFirstkindMonth.action";
    }

    public String getSearchSaleorderTj() {
        return url + "/ticket/searchSaleorderTj.action";
    }

    public String getSearchKingSaleorderTj() {
        return url + "/ticket/searchKingSaleorderTj.action";
    }

    public String getFindAllotOrder() {
        return url + "/order/findOrderYC.action";
    }

    public String getAllotOrderAccountCommit() {
        return url + "/order/OrderTBDZ.action";
    }

    public String getAllotOrderInfo() {
        return url + "/order/findOrderYCMX.action";
    }

    public String getAllotOrderAccountDelete() {
        return url + "/order/delOrderYC.action";
    }

    public String getAllOrderOfYS() {
        return url + "/order/lsitYS.action";
    }

    public String getOrderInfoYs() {
        return url + "/order/listYSMX.action";
    }

    public String getCommitOrderInfoYs() {
        return url + "/order/OrderYSDZ.action";
    }

    public String getDeleteOrderInfoYs() {
        return url + "/order/delOrderYS.action";
    }

    public String getFindCreateNewProduct() {
        return url + "/order/findOrderXPJD.action";
    }

    public String getCreateProductAccountInfoQuery() {
        return url + "/order/findOrderXPJDMX.action";
    }

    public String getCommitCreateNewProduct() {
        return url + "/order/OrderXPJD.action";
    }

    public String getDeleteCreateNewProduct() {
        return url + "/order/delOrderXPJD.action";
    }

    public String getQueryAllShopSale() {
        return url + "/vSaleShop/queryAllShopSale.action";
    }

    public String getQueryAllWorkerSale() {
        return url + "/vSaleShop/queryAllWorkerSale.action";
    }
    public String getQueryAllSale() {
        return url + "/vSaleShop/queryAllSale.action";
    }
}
