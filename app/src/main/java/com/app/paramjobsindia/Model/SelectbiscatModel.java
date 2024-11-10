package com.app.paramjobsindia.Model;

public class SelectbiscatModel {

    String bu_cat_id,bu_cat_name;

    public SelectbiscatModel(String bu_cat_id, String bu_cat_name) {
        this.bu_cat_id = bu_cat_id;
        this.bu_cat_name = bu_cat_name;
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

    @Override
    public String toString() {
        return bu_cat_name;
    }///for showing name in spinner as items

}
