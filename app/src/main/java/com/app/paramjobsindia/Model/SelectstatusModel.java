package com.app.paramjobsindia.Model;

public class SelectstatusModel {
    String jbstatus_id,jbstatus_name;

    public SelectstatusModel(String jbstatus_id, String jbstatus_name) {
        this.jbstatus_id = jbstatus_id;
        this.jbstatus_name = jbstatus_name;
    }

    public String getJbstatus_id() {
        return jbstatus_id;
    }

    public void setJbstatus_id(String jbstatus_id) {
        this.jbstatus_id = jbstatus_id;
    }

    public String getJbstatus_name() {
        return jbstatus_name;
    }

    public void setJbstatus_name(String jbstatus_name) {
        this.jbstatus_name = jbstatus_name;
    }

    @Override
    public String toString() {
        return jbstatus_name;
    }///for showing name in spinner as items


}
