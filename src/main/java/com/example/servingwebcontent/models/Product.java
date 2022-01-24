package com.example.servingwebcontent.models;

import java.math.BigDecimal;

public class Product {

    private String DIV;
    private String DEP;
    private String KATB;
    private String PLU;
    private String deskripsi;
    private Double FRAC;
    private BigDecimal NET_CTN;
    private BigDecimal NET_PCS;
    private BigDecimal NET_MBT;
    private BigDecimal NET_BOX;
    private BigDecimal TOT_CB;
    private BigDecimal SLS;

    public Product() {
    }

    public String getDIV() {
        return DIV;
    }

    public void setDIV(String DIV) {
        this.DIV = DIV;
    }

    public String getDEP() {
        return DEP;
    }

    public void setDEP(String DEP) {
        this.DEP = DEP;
    }

    public String getKATB() {
        return KATB;
    }

    public void setKATB(String KATB) {
        this.KATB = KATB;
    }

    public String getPLU() {
        return PLU;
    }

    public void setPLU(String PLU) {
        this.PLU = PLU;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Double getFRAC() {
        return FRAC;
    }

    public void setFRAC(Double FRAC) {
        this.FRAC = FRAC;
    }

    public BigDecimal getNET_CTN() {
        return NET_CTN;
    }

    public void setNET_CTN(BigDecimal NET_CTN) {
        this.NET_CTN = NET_CTN;
    }

    public BigDecimal getNET_PCS() {
        return NET_PCS;
    }

    public void setNET_PCS(BigDecimal NET_PCS) {
        this.NET_PCS = NET_PCS;
    }

    public BigDecimal getNET_MBT() {
        return NET_MBT;
    }

    public void setNET_MBT(BigDecimal NET_MBT) {
        this.NET_MBT = NET_MBT;
    }

    public BigDecimal getNET_BOX() {
        return NET_BOX;
    }

    public void setNET_BOX(BigDecimal NET_BOX) {
        this.NET_BOX = NET_BOX;
    }

    public BigDecimal getTOT_CB() {
        return TOT_CB;
    }

    public void setTOT_CB(BigDecimal TOT_CB) {
        this.TOT_CB = TOT_CB;
    }

    public BigDecimal getSLS() {
        return SLS;
    }

    public void setSLS(BigDecimal SLS) {
        this.SLS = SLS;
    }
}
