package com.example.group12.courseregisteration;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>

*/
public class Professor_Information_Unit_Test {

       @Test
       public void testProfessor_exists(){

           Professor p1 = new Professor("A", "", "", "");
           Professor p2 = new Professor("B", "", "", "");
           Professor p3 = new Professor("C", "", "", "");
           Professor p4 = new Professor("D", "", "", "");

           ProfessorList list = new ProfessorList();
           list.add(p1);
           list.add(p2);
           list.add(p3);
           list.add(p4);

           //test professor name exists
           assertEquals(1, list.Prof_list_exist_int("B"));
           assertEquals(1, list.Prof_list_exist_int("A"));
           assertEquals(1, list.Prof_list_exist_int("C"));
           assertEquals(1, list.Prof_list_exist_int("D"));
           assertEquals(0, list.Prof_list_exist_int("F"));

           //test index
           assertEquals(0, list.index("A"));
           assertEquals(1, list.index("B"));
           assertEquals(2, list.index("C"));
           assertEquals(3, list.index("D"));

       }


   }
