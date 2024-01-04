package com.example.Model;


public class Counter {

    private Integer id;
    private String name;
    private String description;
    private Integer cashId;
    private Integer sequence;
    private Boolean blocked;
    private Integer docNo;
    private String docPrefix;
    private String docSuffix;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCashId() {
        return cashId;
    }

    public void setCashId(Integer cashId) {
        this.cashId = cashId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Integer getDocNo() {
        return docNo;
    }

    public void setDocNo(Integer docNo) {
        this.docNo = docNo;
    }

    public String getDocPrefix() {
        return docPrefix;
    }

    public void setDocPrefix(String docPrefix) {
        this.docPrefix = docPrefix;
    }

    public String getDocSuffix() {
        return docSuffix;
    }

    public void setDocSuffix(String docSuffix) {
        this.docSuffix = docSuffix;
    }
}