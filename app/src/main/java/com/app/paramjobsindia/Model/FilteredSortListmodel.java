package com.app.paramjobsindia.Model;

public class FilteredSortListmodel {
    String excel_cust_id,user_assign_data_id, excel_cust_name,
            excel_cust_mobileno, follow_up_date,follow_up_desc,follow_up_mod_name;

    public FilteredSortListmodel(String excel_cust_id, String user_assign_data_id,
                                 String excel_cust_name, String excel_cust_mobileno,
                                 String follow_up_date, String follow_up_desc,
                                 String follow_up_mod_name) {
        this.excel_cust_id = excel_cust_id;
        this.user_assign_data_id = user_assign_data_id;
        this.excel_cust_name = excel_cust_name;
        this.excel_cust_mobileno = excel_cust_mobileno;
        this.follow_up_date = follow_up_date;
        this.follow_up_desc = follow_up_desc;
        this.follow_up_mod_name = follow_up_mod_name;
    }

    public String getExcel_cust_id() {
        return excel_cust_id;
    }

    public void setExcel_cust_id(String excel_cust_id) {
        this.excel_cust_id = excel_cust_id;
    }

    public String getUser_assign_data_id() {
        return user_assign_data_id;
    }

    public void setUser_assign_data_id(String user_assign_data_id) {
        this.user_assign_data_id = user_assign_data_id;
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

    public String getFollow_up_date() {
        return follow_up_date;
    }

    public void setFollow_up_date(String follow_up_date) {
        this.follow_up_date = follow_up_date;
    }

    public String getFollow_up_desc() {
        return follow_up_desc;
    }

    public void setFollow_up_desc(String follow_up_desc) {
        this.follow_up_desc = follow_up_desc;
    }

    public String getFollow_up_mod_name() {
        return follow_up_mod_name;
    }

    public void setFollow_up_mod_name(String follow_up_mod_name) {
        this.follow_up_mod_name = follow_up_mod_name;
    }
}
