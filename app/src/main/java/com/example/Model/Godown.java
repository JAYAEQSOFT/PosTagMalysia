
package com.example.Model;
public class Godown {

    private Integer id;
    private String name;
    private String adress1;
    private String adress2;
    private String adress3;
    private Integer sequence;
    private Boolean blocked;
    private Integer parentGodownId;
    private String parentGodownName;

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

    public String getAdress1() {
        return adress1;
    }

    public void setAdress1(String adress1) {
        this.adress1 = adress1;
    }

    public String getAdress2() {
        return adress2;
    }

    public void setAdress2(String adress2) {
        this.adress2 = adress2;
    }

    public String getAdress3() {
        return adress3;
    }

    public void setAdress3(String adress3) {
        this.adress3 = adress3;
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

    public Integer getParentGodownId() {
        return parentGodownId;
    }

    public void setParentGodownId(Integer parentGodownId) {
        this.parentGodownId = parentGodownId;
    }

    public String getParentGodownName() {
        return parentGodownName;
    }

    public void setParentGodownName(String parentGodownName) {
        this.parentGodownName = parentGodownName;
    }
}