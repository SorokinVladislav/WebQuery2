package com.spring.spring.models.condot;


import org.hibernate.annotations.Table;
import javax.persistence.Entity;
import org.hibernate.annotations.Table;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Table(appliesTo = "tbl_crpt_orderbookingmaster")
public class tbl_crpt_orderbookingmaster {

    public tbl_crpt_orderbookingmaster() {
    }

    public tbl_crpt_orderbookingmaster(Long id, String jid, String jobname, String batchno, String gtin, String orderid, String quantity, String obreqdt, String product_name) {
        this.id = id;
        this.jid = jid;
        this.jobname = jobname;
        this.batchno = batchno;
        this.gtin = gtin;
        this.orderid = orderid;
        this.quantity = quantity;
        this.obreqdt = obreqdt;
        this.product_name = product_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getObreqdt() {
        return obreqdt;
    }

    public void setObreqdt(String obreqdt) {
        this.obreqdt = obreqdt;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    @Id
    Long id;


    String jid;
    String jobname;
    String batchno;
    String gtin;
    String orderid;
    String quantity;
    String obreqdt;
    String product_name;



}
