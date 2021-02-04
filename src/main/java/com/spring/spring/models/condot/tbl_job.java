package com.spring.spring.models.condot;

import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Table(appliesTo = "tbl_job")
public class tbl_job {
    public Long getjid() {
        return jid;
    }

    public void setjid(Long jid) {
        this.jid = jid;
    }

    public String getjobname() {
        return jobname;
    }

    public void setjobname(String jobname) {
        jobname = jobname;
    }


    public tbl_job(Long jid, String jobname, String product_name, String jobstatus) {
        this.jid = jid;
        this.jobname = jobname;
        this.product_name = product_name;
        this.jobstatus = jobstatus;
    }

    @Id
    public Long jid;

    public String jobname;

    public String quantity;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getLastupdateddate() {
        if (lastupdateddate == null)
            return "";
        else
            return lastupdateddate.substring(0, 19);
    }

    public void setLastupdateddate(String lastupdateddate) {
        this.lastupdateddate = lastupdateddate.substring(0, 10);
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public tbl_job(Long jid, String jobname, String lastupdateddate, String product_name, String gtin, String batchno,
                   String line_name, String jobstatus, String datatransferstage, String quantity) {
        this.datatransferstage = datatransferstage;
        this.jobstatus = jobstatus;
        this.jid = jid;
        this.jobname = jobname;
        this.line_name = line_name;
        this.lastupdateddate = lastupdateddate.substring(0, 10);
        this.product_name = product_name;
        this.gtin = gtin;
        this.batchno = batchno;
        this.quantity = quantity;
    }


    public String lastupdateddate, product_name, gtin;

    public String getbatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getJobstatus() {
        switch (jobstatus) {
            case "4":
                return "4 - Линия выделена";
            case "7":
                return "7 - Transfer to Plant";
            case "8":
                return "8 - Направлена на линию";
            case "9":
                return "9 - JobEXEAllotment";
            case "12":
                return "12 - Работа приостановлена";
            case "16":
                return "16 - Работа отклонена";
            case "13":
                return "13 - Работа закрыта";
            default:
                return jobstatus;
        }
    }

    public void setJobstatus(String jobstatus) {
        this.jobstatus = jobstatus;
    }

    public String getDatatransferstage() {
        switch (datatransferstage) {
            case "14":
                return "14 - Тайм-аут по времени в 6 часов";
            case "200":
                return "200 - Данные ушли в МДЛП, но не закрыли заказ в СУЗ";
            case "201":
                return "201 - Все данные переданы в том числе в Trace Way";
            case "17":
                return "17 - Передача данных в МДЛП выполнена, но не выполнена отправка дерева в TraceWay";
            case "102":
                return "102";
            default:
                return datatransferstage;
        }
    }

    public void setDatatransferstage(String datatransferstage) {
        this.datatransferstage = datatransferstage;
    }

    String jobstatus, datatransferstage;
    String line_name;
    public String batchno;

    public tbl_job() {
    }
}
