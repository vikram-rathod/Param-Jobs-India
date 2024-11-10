package com.app.paramjobsindia.Model;

public class JobCatagoryModelList {
    String bu_cat_id,bu_cat_name,bu_cat_count;

    public JobCatagoryModelList(String bu_cat_id, String bu_cat_name, String bu_cat_count) {
        this.bu_cat_id = bu_cat_id;
        this.bu_cat_name = bu_cat_name;
        this.bu_cat_count = bu_cat_count;
    }

    public String getBu_cat_id() {
        return bu_cat_id;
    }

    public void setBu_cat_id(String bu_cat_id) {
        this.bu_cat_id = bu_cat_id;
    }

    public String getBu_cat_name() {
        return bu_cat_name;
    }

    public void setBu_cat_name(String bu_cat_name) {
        this.bu_cat_name = bu_cat_name;
    }

    public String getBu_cat_count() {
        return bu_cat_count;
    }

    public void setBu_cat_count(String bu_cat_count) {
        this.bu_cat_count = bu_cat_count;
    }
}
