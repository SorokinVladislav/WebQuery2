package com.spring.spring.models.condot;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class tbl_product_pkg_level_details {

    @Id
    Long id;

    String prod_pkg_id, pkg_level, pcmapcount;

    public tbl_product_pkg_level_details(Long id, String prod_pkg_id, String pkg_level, String pcmapcount) {
        this.id = id;
        this.prod_pkg_id = prod_pkg_id;
        this.pkg_level = pkg_level;
        this.pcmapcount = pcmapcount;
    }

    public tbl_product_pkg_level_details() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProd_pkg_id() {
        return prod_pkg_id;
    }

    public void setProd_pkg_id(String prod_pkg_id) {
        this.prod_pkg_id = prod_pkg_id;
    }

    public String getPkg_level() {
        return pkg_level;
    }

    public void setPkg_level(String pkg_level) {
        this.pkg_level = pkg_level;
    }

    public String getPcmapcount() {
        return pcmapcount;
    }

    public void setPcmapcount(String pcmapcount) {
        this.pcmapcount = pcmapcount;
    }
}
