package com.example.servingwebcontent.models;

import java.math.BigDecimal;

public class SelectedProduct {

    private String PLU;
    private String deskripsi;
    private Double FRAC;
    private BigDecimal harga;
    private Integer qty;
    private String satuan;
    private BigDecimal total;

    public SelectedProduct() {
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

    public BigDecimal getHarga() {
        return harga;
    }

    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
