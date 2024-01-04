package com.example.Model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Sale {

    @PrimaryKey(autoGenerate = false)

    private long id;

    private String docDate;

    private long godownID;

    private String godownName;

    private long branchID;
    private String branchName;

    private long billingCounterID;
    private String billingCounterName;

    private long userID;
    private String userName;

    private String posDeviceName;

    public String createdAt;

    private long itemCount;
    private  double amount;
    private  boolean processed;
    private  long salesId;

    @Ignore
    public  List<items> items;



public Sale()
{

}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public long getGodownID() {
        return godownID;
    }

    public void setGodownID(long godownID) {
        this.godownID = godownID;
    }

    public String getGodownName() {
        return godownName;
    }

    public void setGodownName(String godownName) {
        this.godownName = godownName;
    }

    public long getBranchID() {
        return branchID;
    }

    public void setBranchID(long branchID) {
        this.branchID = branchID;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public long getBillingCounterID() {
        return billingCounterID;
    }

    public void setBillingCounterID(long billingCounterID) {
        this.billingCounterID = billingCounterID;
    }

    public String getBillingCounterName() {
        return billingCounterName;
    }

    public void setBillingCounterName(String billingCounterName) {
        this.billingCounterName = billingCounterName;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPosDeviceName() {
        return posDeviceName;
    }

    public void setPosDeviceName(String posDeviceName) {
        this.posDeviceName = posDeviceName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public long getItemCount() {
        return itemCount;
    }

    public void setItemCount(long itemCount) {
        this.itemCount = itemCount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public long getSalesId() {
        return salesId;
    }

    public void setSalesId(long salesId) {
        this.salesId = salesId;
    }

    public List<items> getItems() {
        return items;
    }

    public void setItems(List<items> items) {
        items = items;
    }



    public Sale(long id, String docDate, long godownID, String godownName, long branchID, String branchName, long billingCounterID, String billingCounterName, long userID, String userName, String posDeviceName, String createdAt, long itemCount, double amount, boolean processed, long salesId, List<items> items) {
        this.id = id;
        this.docDate = docDate;
        this.godownID = godownID;
        this.godownName = godownName;
        this.branchID = branchID;
        this.branchName = branchName;
        this.billingCounterID = billingCounterID;
        this.billingCounterName = billingCounterName;
        this.userID = userID;
        this.userName = userName;
        this.posDeviceName = posDeviceName;
        this.createdAt = createdAt;
        this.itemCount = itemCount;
        this.amount = amount;
        this.processed = processed;
        this.salesId = salesId;
        items = items;

    }
}