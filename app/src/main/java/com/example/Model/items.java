package com.example.Model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class items {
    @PrimaryKey(autoGenerate = true)

    private long id;
    private long seq;

    private long productId;
    private String productName;
    private long batchId;
    private String batchCode;
    private double quantity;

    private double price;
    private double amount;
    private long unitId;
    private String unitName;
    private  double qtyy;

    public double getQtyy() {
        return qtyy;
    }

    public void setQtyy(double qtyy) {
        this.qtyy = qtyy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getBatchId() {
        return batchId;
    }

    public void setBatchId(long batchId) {
        this.batchId = batchId;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void addToQuantity(Double qty){
        this.quantity += qty;
    }

    public void removeFromQuantity(Double qty){
        if(this.quantity > 0){
            this.quantity -= qty;
        }
    }

}
