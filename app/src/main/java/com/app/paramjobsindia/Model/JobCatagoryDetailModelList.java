package com.app.paramjobsindia.Model;

public class JobCatagoryDetailModelList {
    String job_id,job_title,bu_cat_id,get_compname,company_id,qualification_id,job_type,job_salary
            ,job_education,job_location,job_note,job_interview_date,job_interview_time,job_opening
            ,pro_img;

    public JobCatagoryDetailModelList(String job_id, String job_title,
                                      String bu_cat_id, String get_compname,
                                      String company_id, String qualification_id,
                                      String job_type, String job_salary, String job_education,
                                      String job_location, String job_note,
                                      String job_interview_date, String job_interview_time,
                                      String job_opening, String pro_img) {
        this.job_id = job_id;
        this.job_title = job_title;
        this.bu_cat_id = bu_cat_id;
        this.get_compname = get_compname;
        this.company_id = company_id;
        this.qualification_id = qualification_id;
        this.job_type = job_type;
        this.job_salary = job_salary;
        this.job_education = job_education;
        this.job_location = job_location;
        this.job_note = job_note;
        this.job_interview_date = job_interview_date;
        this.job_interview_time = job_interview_time;
        this.job_opening = job_opening;
        this.pro_img = pro_img;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getBu_cat_id() {
        return bu_cat_id;
    }

    public void setBu_cat_id(String bu_cat_id) {
        this.bu_cat_id = bu_cat_id;
    }

    public String getGet_compname() {
        return get_compname;
    }

    public void setGet_compname(String get_compname) {
        this.get_compname = get_compname;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getQualification_id() {
        return qualification_id;
    }

    public void setQualification_id(String qualification_id) {
        this.qualification_id = qualification_id;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public String getJob_salary() {
        return job_salary;
    }

    public void setJob_salary(String job_salary) {
        this.job_salary = job_salary;
    }

    public String getJob_education() {
        return job_education;
    }

    public void setJob_education(String job_education) {
        this.job_education = job_education;
    }

    public String getJob_location() {
        return job_location;
    }

    public void setJob_location(String job_location) {
        this.job_location = job_location;
    }

    public String getJob_note() {
        return job_note;
    }

    public void setJob_note(String job_note) {
        this.job_note = job_note;
    }

    public String getJob_interview_date() {
        return job_interview_date;
    }

    public void setJob_interview_date(String job_interview_date) {
        this.job_interview_date = job_interview_date;
    }

    public String getJob_interview_time() {
        return job_interview_time;
    }

    public void setJob_interview_time(String job_interview_time) {
        this.job_interview_time = job_interview_time;
    }

    public String getJob_opening() {
        return job_opening;
    }

    public void setJob_opening(String job_opening) {
        this.job_opening = job_opening;
    }

    public String getPro_img() {
        return pro_img;
    }

    public void setPro_img(String pro_img) {
        this.pro_img = pro_img;
    }
}
