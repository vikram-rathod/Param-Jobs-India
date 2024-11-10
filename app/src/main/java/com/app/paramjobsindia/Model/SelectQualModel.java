package com.app.paramjobsindia.Model;

public class SelectQualModel {

    String qualification_id,qualification_name;

    public SelectQualModel(String qualification_id, String qualification_name) {
        this.qualification_id = qualification_id;
        this.qualification_name = qualification_name;
    }

    public String getQualification_id() {
        return qualification_id;
    }

    public void setQualification_id(String qualification_id) {
        this.qualification_id = qualification_id;
    }

    public String getQualification_name() {
        return qualification_name;
    }

    public void setQualification_name(String qualification_name) {
        this.qualification_name = qualification_name;
    }

    @Override
    public String toString() {
        return qualification_name;
    }///for showing name in spinner as items


}
