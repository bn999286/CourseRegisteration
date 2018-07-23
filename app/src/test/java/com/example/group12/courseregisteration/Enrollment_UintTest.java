package com.example.group12.courseregisteration;

import org.junit.Test;
import org.junit.runners.JUnit4;
import static org.junit.Assert.assertEquals;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;


import java.util.LinkedList;


@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})

public class Enrollment_UintTest {

    LinkedList<Integer> list = new LinkedList<>();

    int count;

    DatabaseReference mockedDatabaseReference;

    @Before
    public void before(){

        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);

        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);


        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);

    }

    @Test
    public void Course_limit_Test(){


        when(mockedDatabaseReference.child("Students/WtE8Fvl4JRcpBbEGzrIzKZBJ9pi2/Courses")).thenReturn(mockedDatabaseReference);


        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {

                ValueEventListener valueEventListener = (ValueEventListener) invocation.getArguments()[0];

                DataSnapshot mockedDataSnapshot = Mockito.mock(DataSnapshot.class);

                valueEventListener.onDataChange(mockedDataSnapshot);

                for (DataSnapshot child : mockedDataSnapshot.getChildren()) {

                    String name = child.child("Slots").getValue(String.class);
                    list.add(Integer.parseInt(name));
                    count++;
                }

                int sum=0;
                for(int i =0 ; i<list.size(); i++){
                    sum = sum + list.get(i);
                }

                assertEquals(500, sum);
                assertEquals(5, count);

                return null;

            }

        }).when(mockedDatabaseReference).addListenerForSingleValueEvent(any(ValueEventListener.class));


    }

}
