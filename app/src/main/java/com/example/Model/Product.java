package com.example.Model;


import java.util.List;





public class Product {

    private Integer id;
    private String name;
    private String printName;
    private Integer hsnid;
    private String hsnName;
    private Integer categoryId;
    private String categoryName;
    private String brand;
    private String mFR;
    private Integer taxId;
    private String taxName;
    private Double taxRate;
    private Integer taxType;
    private Double cGSTTaxRate;
    private Double sGSTTaxRate;
    private Integer unitId;

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    private String batchCode;
    private String unitName;
    private long salesUnit;
    private Integer purchaseUnit;
    private Double reorderLevel;
    private Double maxStock;
    private Double discountAmount;
    private Double rent;
    private Double coolie;
    private Double discountRate;
    private Double pointPerQty;
    private Boolean hasSerials;
    private Boolean hasExpiry;
    private Boolean hasBatchNoControl;
    private Boolean hasBatch;
    private Boolean manuelCode;
    private Boolean blocked;
    private Integer defaultBatch;
    private Integer type;
    private String code;
    private String shortName;
    private Double _package;
    private Double openingStock;
    private Double agentcommsn;
    private Double engineerCmmsn;
    private Double salesmanCommsn;
    private Double promoterCommsn;
    private String rack;
    private Integer batchId;
    private List<BatchLst> batchLst = null;
    private  List<PriceLst> priceLsts=null;
    private Object productUnitLst;
    private Integer pictureID;
    private List<Unit> unitList = null;
    private Object sizeList;
    private String hSNCode;
    private Integer excludeFromPoint;
    private Integer bulkUnitID;
    private Double openingBulk;
    private Double cessPer;
    private Double addlCessAmt;
    private Integer cessUnit;
    private Integer stockUnitId;
    private Double stockQty;
    private Integer stockBulk;
    private Integer stockVoucherId;
    private Integer stockRate;
    private Integer wskey;
    private Integer weighmchnid;
    private Double weight;
    private String wscode;
    private Integer serviceLinkedAccId;
    private Boolean editPrice;
    private Boolean editQty;
    private Boolean dailyPrizChange;
    private String accountCode;
    private String accountName;
    private Boolean allowNegativeStk;
    private String weighScaleUnit;
    private Double keralaFloodCess;
    private Integer flag;
    private String remarks;

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    private String uQC;

    public String getWeighScaleUnit() {
        return weighScaleUnit;
    }

    public void setWeighScaleUnit(String weighScaleUnit) {
        this.weighScaleUnit = weighScaleUnit;
    }

    private Integer supplierId;
    private String suppName;
    private Double mRP;
    private Double purchaseRate;
    private Double purchaseCost;
    private Double rate1;
    private Double rate2;
    private Double rate3;
    private Double rate4;
    private Double rate5;
    private long craetedBy;
    private String createdAt;
    private long modifiedBy;
    private String modifiedAt;
    private Boolean listInApp;

    public List<PriceLst> getPriceLsts() {
        return priceLsts;
    }

    public void setPriceLsts(List<PriceLst> priceLsts) {
        this.priceLsts = priceLsts;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setBatchId(int id) {
        this.batchId = id;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public List<BatchLst> getBatchLst() {
        return batchLst;
    }

    public Double getRate1() {
        return rate1;
    }

    public void setRate1(Double rate1) {
        this.rate1 = rate1;
    }

    public Double getRate2() {
        return rate2;
    }

    public void setRate2(Double rate2) {
        this.rate2 = rate2;
    }

    public Double getRate3() {
        return rate3;
    }

    public void setRate3(Double rate3) {
        this.rate3 = rate3;
    }

    public Double getRate4() {
        return rate4;
    }

    public void setRate4(Double rate4) {
        this.rate4 = rate4;
    }

    public Double getRate5() {
        return rate5;
    }

    public void setRate5(Double rate5) {
        this.rate5 = rate5;
    }

    public void setBatchLst(List<BatchLst> batchLst) {
        this.batchLst = batchLst;
    }

    public long getSalesUnit() {
        return salesUnit;
    }

    public void setSalesUnit(long salesUnit) {
        this.salesUnit = salesUnit;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getmRP() {
        return mRP;
    }

    public void setmRP(Double mRP) {
        this.mRP = mRP;
    }
}

