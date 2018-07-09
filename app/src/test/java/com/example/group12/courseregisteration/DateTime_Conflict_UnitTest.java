package com.example.group12.courseregisteration;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DateTime_Conflict_UnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    //test time conflict
    @Test
    public void DateTime_Conflict(){

        DateTime dateTime1 = new DateTime("MWF", "1:00", "2:00");
        DateTime dateTime2 = new DateTime("MWF", "2:00", "5:00");
        DateTime dateTime3 = new DateTime("MWF", "8:00", "9:00");
        DateTime dateTime4 = new DateTime("MWF", "11:00", "12:00");
        DateTime dateTime5 = new DateTime("TR", "11:00", "12:00");

        //1- has conflict  0-no conflict
        DateTimeConflict test1 = new DateTimeConflict();
        test1.TimeConflict(dateTime1, dateTime2);
        assertEquals(1, test1.TestResult());

        DateTimeConflict test2 = new DateTimeConflict();
        test2.TimeConflict(dateTime3, dateTime4);
        assertEquals(0, test2.TestResult());

        DateTimeConflict test3 = new DateTimeConflict();
        test3.TimeConflict(dateTime4, dateTime5);
        assertEquals(0, test3.TestResult());


    }
}