package com.app.paramjobsindia.Model;

public class JobModelList {
    String title,salary;

    public JobModelList(String title, String salary) {
        this.title = title;
        this.salary = salary;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }


}
