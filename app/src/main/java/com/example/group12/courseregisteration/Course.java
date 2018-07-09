package com.example.group12.courseregisteration;

/**
 * The type Course, containing all course information
 * Basic functionality written by Bin He and Chasteen
 */
public class Course {

    private String course_name, course_date, time_start, time_end, location, professor;

    /**
     * Instantiates a new Course.
     *
     * @param course_name the course name
     * @param course_date the course date
     * @param time_start  the time start
     * @param time_end    the time end
     * @param location    the location
     * @param professor   the professor
     */
    public Course(String course_name, String course_date, String time_start, String time_end, String location, String professor){

        if(course_date!=null &&
                course_name!=null &&
                time_start!=null &&
                time_end!=null &&
                location!=null &&
                professor!=null) {

            this.course_name = course_name;
            this.course_date = course_date;
            this.time_start = time_start;
            this.time_end = time_end;
            this.location = location;
            this.professor = professor;

        }

    }

    /**
     * Get course name string.
     *
     * @return the string
     */
    public String getCourseName(){
        return course_name;
    }

    /**
     * Get course date string.
     *
     * @return the string
     */
    public String getCourseDate(){
        return course_date;
    }

    /**
     * Get time start string.
     *
     * @return the string
     */
    public String getTimeStart(){
        return time_start;
    }

    /**
     * Get time end string.
     *
     * @return the string
     */
    public String getTimeEnd(){
        return time_end;
    }

    /**
     * Get course location string.
     *
     * @return the string
     */
    public String getCourseLocation(){
        return location;
    }

    /**
     * Get course professor string.
     *
     * @return the string
     */
    public String getCourseProfessor(){
        return professor;
    }



}
