package com.spring.spring.models.condot;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class tbl_crpt_dataupload {
    @Id
    Long id;
    String reportid;
    String jobid;

    public tbl_crpt_dataupload() {
    }

    public tbl_crpt_dataupload(Long id, String reportid, String jobid) {
        this.id = id;
        this.reportid = reportid;
        this.jobid = jobid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportid() {
        return reportid;
    }

    public void setReportid(String reportid) {
        this.reportid = reportid;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }
}
