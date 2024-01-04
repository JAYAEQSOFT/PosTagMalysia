package com.example.General;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Id")
    @Expose
    private long id;
    @SerializedName("Errors")
    @Expose
    private List<String> errors = null;
    private final static long serialVersionUID = 8626423422205515739L;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
