package com.app.paramjobsindia.Model;

public class AppliedJobcmpnyModelList {
    String candidate_id,jobId,candidate_name,jobcardno,candidate_number,candidate_email_id,candidate_address,candidate_education,
            candidate_exprince,
            bu_cat_id,get_compname,company_id,qualification_namw,job_title,job_type,job_salary,
            job_education,job_location,job_note,job_interview_date,job_interview_time,
            job_opening,candidate_post_applied_status,pro_img;

    public AppliedJobcmpnyModelList(String candidate_id,String jobId,String candidate_name, String jobcardno,
                                    String candidate_number, String candidate_email_id,
                                    String candidate_address, String candidate_education,
                                    String candidate_exprince, String bu_cat_id,
                                    String get_compname, String company_id,
                                    String qualification_namw,String job_title, String job_type,
                                    String job_salary, String job_education,
                                    String job_location, String job_note,
                                    String job_interview_date, String job_interview_time,
                                    String job_opening,String candidate_post_applied_status, String pro_img) {
        this.candidate_id=candidate_id;
        this.jobId=jobId;
        this.candidate_name = candidate_name;
        this.jobcardno = jobcardno;
        this.candidate_number = candidate_number;
        this.candidate_email_id = candidate_email_id;
        this.candidate_address = candidate_address;
        this.candidate_education = candidate_education;
        this.candidate_exprince = candidate_exprince;
        this.bu_cat_id = bu_cat_id;
        this.get_compname = get_compname;
        this.company_id = company_id;
        this.qualification_namw = qualification_namw;
        this.job_title=job_title;
        this.job_type = job_type;
        this.job_salary = job_salary;
        this.job_education = job_education;
        this.job_location = job_location;
        this.job_note = job_note;
        this.job_interview_date = job_interview_date;
        this.job_interview_time = job_interview_time;
        this.job_opening = job_opening;
        this.candidate_post_applied_status=candidate_post_applied_status;
        this.pro_img = pro_img;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getCandidate_post_applied_status() {
        return candidate_post_applied_status;
    }

    public void setCandidate_post_applied_status(String candidate_post_applied_status) {
        this.candidate_post_applied_status = candidate_post_applied_status;
    }

    public String getJob_id() {
        return jobId;
    }

    public void setJob_id(String jobId) {
        this.jobId = jobId;
    }

    public String getCandidate_id() {
        return candidate_id;
    }

    public void setCandidate_id(String candidate_id) {
        this.candidate_id = candidate_id;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getCandidate_name() {
        return candidate_name;
    }

    public void setCandidate_name(String candidate_name) {
        this.candidate_name = candidate_name;
    }

    public String getJobcardno() {
        return jobcardno;
    }

    public void setJobcardno(String jobcardno) {
        this.jobcardno = jobcardno;
    }

    public String getCandidate_number() {
        return candidate_number;
    }

    public void setCandidate_number(String candidate_number) {
        this.candidate_number = candidate_number;
    }

    public String getCandidate_email_id() {
        return candidate_email_id;
    }

    public void setCandidate_email_id(String candidate_email_id) {
        this.candidate_email_id = candidate_email_id;
    }

    public String getCandidate_address() {
        return candidate_address;
    }

    public void setCandidate_address(String candidate_address) {
        this.candidate_address = candidate_address;
    }

    public String getCandidate_education() {
        return candidate_education;
    }

    public void setCandidate_education(String candidate_education) {
        this.candidate_education = candidate_education;
    }

    public String getCandidate_exprince() {
        return candidate_exprince;
    }

    public void setCandidate_exprince(String candidate_exprince) {
        this.candidate_exprince = candidate_exprince;
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

    public String getQualification_namw() {
        return qualification_namw;
    }

    public void setQualification_namw(String qualification_namw) {
        this.qualification_namw = qualification_namw;
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
