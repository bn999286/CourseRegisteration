package com.example.group12.courseregisteration;


/**
 * The type User information.
 * Basic functionality written by Peter and Xao
 * Cleaned up by Bin He and Chasteen
 */
public class UserInformation {

    /**
     * The Email.
     */
    public String email;
    /**
     * The Name.
     */
    public String name;
    /**
     * The Phone.
     */
    public String phone;
    /**
     * The Password.
     */
    public String password;
    /**
     * The Course 1.
     */
    public String course1;
    /**
     * The Course 2.
     */
    public String course2;
    /**
     * The Course 3.
     */
    public String course3;

    /**
     * Instantiates a new User information.
     */
    public UserInformation()
    {

    }

    /**
     * Instantiates a new User information.
     *
     * @param em  the em
     * @param na  the na
     * @param ph  the ph
     * @param psw the psw
     */
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
