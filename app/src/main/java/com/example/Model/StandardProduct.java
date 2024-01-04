package com.example.Model;

public class StandardProduct {

    long productId;
    int sequence;
    String batchCode;
    String productName;
    long unitId;
    String UnitName;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public long getUnitid() {
        return unitId;
    }

    public void setUnitid(long unitid) {
        this.unitId = unitid;
    }
}
