package com.app.paramjobsindia.Model;

public class TodaysCallListModel {

    String user_assign_data_id,excel_cust_id, excel_cust_name,
            excel_cust_mobileno, business_cat_name;

    public TodaysCallListModel(String user_assign_data_id, String excel_cust_id,
                               String excel_cust_name, String excel_cust_mobileno,
                               String business_cat_name) {
        this.user_assign_data_id = user_assign_data_id;
        this.excel_cust_id = excel_cust_id;
        this.excel_cust_name = excel_cust_name;
        this.excel_cust_mobileno = excel_cust_mobileno;
        this.business_cat_name = business_cat_name;
    }

    public String getUser_assign_data_id() {
        return user_assign_data_id;
    }

    public void setUser_assign_data_id(String user_assign_data_id) {
        this.user_assign_data_id = user_assign_data_id;
    }

    public String getExcel_cust_id() {
        return excel_cust_id;
    }

    public void setExcel_cust_id(String excel_cust_id) {
        this.excel_cust_id = excel_cust_id;
    }

    public String getExcel_cust_name() {
        return excel_cust_name;
    }

    public void setExcel_cust_name(String excel_cust_name) {
        this.excel_cust_name = excel_cust_name;
    }

    public String getExcel_cust_mobileno() {
        return excel_cust_mobileno;
    }

    public void setExcel_cust_mobileno(String excel_cust_mobileno) {
        this.excel_cust_mobileno = excel_cust_mobileno;
    }

    public String getBusiness_cat_name() {
        return business_cat_name;
    }

    public void setBusiness_cat_name(String business_cat_name) {
        this.business_cat_name = business_cat_name;
    }
}
