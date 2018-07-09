package com.example.group12.courseregisteration;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})

public class TuitionFee_UnitTest {


    LinkedList<String> list = new LinkedList<>();

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
    public void TuitutionFee_Test(){


        when(mockedDatabaseReference.child("Students/WtE8Fvl4JRcpBbEGzrIzKZBJ9pi2/Courses")).thenReturn(mockedDatabaseReference);


        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation){

                ValueEventListener valueEventListener = (ValueEventListener) invocation.getArguments()[0];

                DataSnapshot mockedDataSnapshot = Mockito.mock(DataSnapshot.class);

                valueEventListener.onDataChange(mockedDataSnapshot);

                for (DataSnapshot child : mockedDataSnapshot.getChildren()) {

                    String name = child.child("Fee").getValue(String.class);
                    list.add(name);

                }

                double totalFee = 0;
                //Calculate total fee
                for (int i = 0; i < list.size(); i++) {
                    totalFee = totalFee + Double.parseDouble(list.get(i));
                }


                assertEquals(2500.0, totalFee);

                return null;

            }

        }).when(mockedDatabaseReference).addValueEventListener(any(ValueEventListener.class));




    }

}