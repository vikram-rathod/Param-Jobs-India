package com.app.paramjobsindia.Model;

public class SelectFSModel {

    String follow_up_mod_id,follow_up_mod_name;

    public SelectFSModel(String follow_up_mod_id, String follow_up_mod_name) {
        this.follow_up_mod_id = follow_up_mod_id;
        this.follow_up_mod_name = follow_up_mod_name;
    }

    public String getFollow_up_mod_id() {
        return follow_up_mod_id;
    }

    public void setFollow_up_mod_id(String follow_up_mod_id) {
        this.follow_up_mod_id = follow_up_mod_id;
    }

    public String getFollow_up_mod_name() {
        return follow_up_mod_name;
    }

    public void setFollow_up_mod_name(String follow_up_mod_name) {
        this.follow_up_mod_name = follow_up_mod_name;
    }

    @Override
    public String toString() {
        return follow_up_mod_name;
    }///for showing name in spinner as items

}
