package com.app.paramjobsindia.Model;

public class SelectQSModel {

    String qid,qname;

    public SelectQSModel(String qid, String qname) {
        this.qid = qid;
        this.qname = qname;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getQname() {
        return qname;
    }

    public void setQname(String qname) {
        this.qname = qname;
    }

    @Override
    public String toString() {
        return qname;
    }///for showing name in spinner as items

}
