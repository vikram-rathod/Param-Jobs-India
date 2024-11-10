package com.app.paramjobsindia.Model;

public class SelectIvShedulestatusModel {
    String ct_jas_id,ct_jas_name;

    public SelectIvShedulestatusModel(String ct_jas_id, String ct_jas_name) {
        this.ct_jas_id = ct_jas_id;
        this.ct_jas_name = ct_jas_name;
    }

    public String getCt_jas_id() {
        return ct_jas_id;
    }

    public void setCt_jas_id(String ct_jas_id) {
        this.ct_jas_id = ct_jas_id;
    }

    public String getCt_jas_name() {
        return ct_jas_name;
    }

    public void setCt_jas_name(String ct_jas_name) {
        this.ct_jas_name = ct_jas_name;
    }


    @Override
    public String toString() {
        return ct_jas_name;
    }///for showing name in spinner as items


}
