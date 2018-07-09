package com.example.group12.courseregisteration;

/**
 * The type Date time.
 * Basic functionality written by Bin He and Chasteen
 */
public class DateTime {

    private String date;
    private String start;
    private String end;

    /**
     * Instantiates a new Date time.
     *
     * @param date  the date
     * @param start the start
     * @param end   the end
     */
    public DateTime(String date, String start, String end){
        this.date=date;
        this.start=start;
        this.end=end;
    }

    /**
     * Get date string.
     *
     * @return the string
     */
//get method
    public String getDate(){
        return date;
    }

    /**
     * Get start time string.
     *
     * @return the string
     */
    public String getStartTime(){
        return start;
    }

    /**
     * Get end time string.
     *
     * @return the string
     */
    public String getEndTime(){
        return end;
    }

    /**
     * Set date.
     *
     * @param date the date
     */
//set method
    public void setDate(String date){
        this.date=date;
    }

    /**
     * Set start time.
     *
     * @param start the start
     */
    public void setStartTime(String start){
        this.start=start;
    }

    /**
     * Set end time.
     *
     * @param end the end
     */
    public void setEndTime(String end){
        this.end=end;
    }

}
