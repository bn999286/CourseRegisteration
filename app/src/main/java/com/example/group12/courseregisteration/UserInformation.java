package com.example.group12.courseregisteration;


public class UserInformation {

    public String email;
    public String name;
    public String phone;
    public String password;
    public String course1;
    public String course2;
    public String course3;

    public UserInformation()
    {

    }

    public UserInformation(String em, String na, String ph, String psw)
    {
        email = em;
        name = na;
        phone = ph;
        password = psw;
        course1 = null;
        course2 = null;
        course3 = null;
    }
}
