package com.spring.spring.models.condot;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class tbl_production_packaging_data {

@Id
Long packdtlsid;

String    jobid, pkg_level, nextlevel_uidcode, uidcode, isprinted, isinspected, isrejected, issampled, isaggregated;

    public tbl_production_packaging_data(Long packdtlsid, String jobid, String pkg_level, String nextlevel_uidcode, String uidcode, String isprinted, String isinspected, String isrejected, String issampled, String isaggregated) {
        this.packdtlsid = packdtlsid;
        this.jobid = jobid;
        this.pkg_level = pkg_level;
        this.nextlevel_uidcode = nextlevel_uidcode;
        this.uidcode = uidcode;
        this.isprinted = isprinted;
        this.isinspected = isinspected;
        this.isrejected = isrejected;
        this.issampled = issampled;
        this.isaggregated = isaggregated;
    }

    public tbl_production_packaging_data() {
    }

    public Long getPackdtlsid() {
        return packdtlsid;
    }

    public void setPackdtlsid(Long packdtlsid) {
        this.packdtlsid = packdtlsid;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getPkg_level() {
        return pkg_level;
    }

    public void setPkg_level(String pkg_level) {
        this.pkg_level = pkg_level;
    }

    public String getNextlevel_uidcode() {
        return nextlevel_uidcode;
    }

    public void setNextlevel_uidcode(String nextlevel_uidcode) {
        this.nextlevel_uidcode = nextlevel_uidcode;
    }

    public String getUidcode() {
        return uidcode;
    }

    public void setUidcode(String uidcode) {
        this.uidcode = uidcode;
    }

    public String getIsprinted() {
        return isprinted;
    }

    public void setIsprinted(String isprinted) {
        this.isprinted = isprinted;
    }

    public String getIsinspected() {
        return isinspected;
    }

    public void setIsinspected(String isinspected) {
        this.isinspected = isinspected;
    }

    public String getIsrejected() {
        return isrejected;
    }

    public void setIsrejected(String isrejected) {
        this.isrejected = isrejected;
    }

    public String getIssampled() {
        return issampled;
    }

    public void setIssampled(String issampled) {
        this.issampled = issampled;
    }

    public String getIsaggregated() {
        return isaggregated;
    }

    public void setIsaggregated(String ssaggregated) {
        this.isaggregated = ssaggregated;
    }
}
