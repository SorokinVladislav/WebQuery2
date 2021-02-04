package com.spring.spring.models.condot;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class tbl_crpt_documentlog {


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getXmltype() {
        switch (xmltype) {
            case "213":
                return xmltype + " - Бронирование SSCC";
            case "2132":
                return xmltype + " - Отмена бронирования SSCC";
            case "431":
                return xmltype + " - Перемещение";
            case "915":
            case "9151":
            case "9152":
                return xmltype + " - Групповое агрегирование";
            case "552":
                return xmltype + " - Вывод из оборота";
            case "911":
            case "9111":
            case "9112":
                return xmltype + " - Агрегирование";
            default:
                return xmltype;
        }
    }

    public tbl_crpt_documentlog() {
    }
    String product_name, jobstatus, jobname;
    public tbl_crpt_documentlog(Long id, String xmltype, String transactionstatus, String document_receipt, String document_id,
                                String product_name,String jobstatus,String jobname,  String jobid, String updateddate) {
        this.id = id;
        this.xmltype = xmltype;
        this.transactionstatus = transactionstatus;
        this.document_receipt=document_receipt;
        this.document_id = document_id;
        this.jobid = jobid;
        this.updateddate=updateddate;
        this.product_name=product_name;
        this.jobstatus=jobstatus;
        this.jobname=jobname;
    }

    public void setXmltype(String xmltype) {
        this.xmltype = xmltype;
    }

    public String getTransactionstatus() {
        switch (transactionstatus) {
            case "0":
                return "0 – Новый отчёт";
            case "2":
                return "2 – Отчёт отклонён (Reject)";
            case "4":
                return "4 – процесс получения квитанции";
            case "7":
                return "7 – Успешно получена квитанция";
            default:
                return transactionstatus;
        }
    }

    public void setTransactionstatus(String transactionstatus) {
        this.transactionstatus = transactionstatus;
    }

    public String getDocument_receipt() {
        if (document_receipt==null)
            return "";

            // есть ли ошибка в отчете
        else if (document_receipt.contains("<error_desc>")) {
            String[] a=null;
            String[] receiptString=null;
            a = document_receipt.split("<error_desc>");
            receiptString = a[1].split("</");
            return receiptString[0]  ;
        }
        else {
            // иначе проверяем статус отчета
            String[] a = null;
            String[] receiptString = null;
            a = document_receipt.split("<operation_comment>");
            receiptString = a[1].split("</");
            return receiptString[0];
        }
    }

    public void setDocument_receipt(String document_receipt) {
            this.document_receipt=document_receipt;
    }

    public String getDocument_id() {
       return document_id;
    }


    public void setDocument_id(String document_id) {
        this.document_id = document_id;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    @Id
    Long id;
    String xmltype;
    String transactionstatus;
    String document_receipt;
    String document_id;
    String jobid;


    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getJobstatus() {
        switch (jobstatus) {
            case "4":
                return "4 - Линия выделена";
            case "7":
                return "7 ";
            case "8":
                return "8 - Направлена на линию";
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

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public String getUpdateddate() throws NullPointerException {
        if (updateddate==null)
            return "";
        else
        return updateddate.substring(0,19);

    }

    public void setUpdateddate(String updateddate) {
        this.updateddate = updateddate;
    }

    String updateddate;

}
