package com.example.group12.courseregisteration;


/**
 * The type Date time conflict.
 * Basic functionality written by Bin He and Chasteen
 */
public class DateTimeConflict {

    private Boolean result;

    /**
     * Instantiates a new Date time conflict.
     */
//constructor
    public DateTimeConflict(){

    }

    /**
     * Time conflict boolean.
     *
     * @param enrolled the enrolled
     * @param newTime  the new time
     * @return the boolean
     */
//return true if has date time conflict
    public Boolean TimeConflict(DateTime enrolled, DateTime newTime){

        //if there are no date time have already enrolled, there is no conflict
        // return false
        if(enrolled.getDate()==null || enrolled.getStartTime()==null || enrolled.getEndTime()==null){
            return false;
        }
        else {

            //convert time to integer
            String[] HourMin_AS = enrolled.getStartTime().split(":");
            String[] HourMin_AE = enrolled.getEndTime().split(":");
            int AS = Integer.parseInt(HourMin_AS[0] + HourMin_AS[1]);
            int AE = Integer.parseInt(HourMin_AE[0] + HourMin_AE[1]);

            String[] HourMin_BS = newTime.getStartTime().split(":");
            String[] HourMin_BE = newTime.getEndTime().split(":");
            int BS = Integer.parseInt(HourMin_BS[0] + HourMin_BS[1]);
            int BE = Integer.parseInt(HourMin_BE[0] + HourMin_BE[1]);

            //if the date is same, possibly have time conflict
            if (enrolled.getDate().equals(newTime.getDate())) {

                if (AS > BE || BS > AE) {
                    result = false;
                    return false;

                } else {
                    result = true;
                    return true;
                }
            } else {
                result = false;
                return false;
            }

        }

    }

    /**
     * Test result int.
     *
     * @return the int
     */
//return 1 if datetime has conflict
    public int TestResult(){

        if(result == true){
            return 1;
        }
        else{
            return 0;
        }

    }



}
