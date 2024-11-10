package com.app.paramjobsindia.Model;

public class SelectflmodeModel {

    String fl_mod_id,fl_mod_name;

    public SelectflmodeModel(String fl_mod_id, String fl_mod_name) {
        this.fl_mod_id = fl_mod_id;
        this.fl_mod_name = fl_mod_name;
    }

    public String getFl_mod_id() {
        return fl_mod_id;
    }

    public void setFl_mod_id(String fl_mod_id) {
        this.fl_mod_id = fl_mod_id;
    }

    public String getFl_mod_name() {
        return fl_mod_name;
    }

    public void setFl_mod_name(String fl_mod_name) {
        this.fl_mod_name = fl_mod_name;
    }

    @Override
    public String toString() {
        return fl_mod_name;
    }///for showing name in spinner as items


}
