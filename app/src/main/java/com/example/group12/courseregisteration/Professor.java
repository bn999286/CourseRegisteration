package com.example.group12.courseregisteration;

public class Professor {

    private String prof_name;
    private String email;
    private String office;
    private String office_hrs;

    public Professor(String prof_name, String email,String office, String office_hrs){

        this.prof_name= prof_name;
        this.email= email;
        this.office= office;
        this.office_hrs= office_hrs;

    }

    public void setProf_name(String prof_name) {
        this.prof_name = prof_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public void setOffice_hrs(String office_hrs) {
        this.office_hrs = office_hrs;
    }

    public String getEmail() {
        return email;
    }

    public String getOffice() {
        return office;
    }

    public String getProf_name() {
        return prof_name;
    }

    public String getOffice_hrs() {
        return office_hrs;
    }

}
