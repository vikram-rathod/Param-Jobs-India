package com.app.paramjobsindia.Model;

public class SelectJtypeModel {

    String jtype_id,jtype_name;

    public SelectJtypeModel(String jtype_id, String jtype_name) {
        this.jtype_id = jtype_id;
        this.jtype_name = jtype_name;
    }

    public String getJtype_id() {
        return jtype_id;
    }

    public void setJtype_id(String jtype_id) {
        this.jtype_id = jtype_id;
    }

    public String getJtype_name() {
        return jtype_name;
    }

    public void setJtype_name(String jtype_name) {
        this.jtype_name = jtype_name;
    }

    @Override
    public String toString() {
        return jtype_name;
    }///for showing name in spinner as items


}
