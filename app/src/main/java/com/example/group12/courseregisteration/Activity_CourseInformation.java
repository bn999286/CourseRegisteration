package com.example.group12.courseregisteration;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.LinkedList;

/**
 * Displays the detailed information for a single course
 * Basic functionality written by Peter and Xao
 * Timing conflict detector was written by Bin He and Chasteen
 * Register course and drop course added by Jon and Mao
 * Course slots functionality added by Peter and Mao
 *
 */
public class Activity_CourseInformation extends AppCompatActivity {

    //text view
    private TextView CourseID;
    private TextView Professor;
    private TextView CourseName;
    private TextView TimeStart;
    private TextView TimeEnd;
    private TextView Date;
    private TextView Location;
    private TextView SlotHolder;

    //button
    private Button buttonBack;
    private Button buttonRegister;
    private Button buttonDrop;

    //string
    private String course_id;
    private String name;
    private String prof;
    private String location;
    private String date;
    private String start;
    private String end;
    private String slots;


    /**
     * The Student id.
     */
//direct to student_id, the child of root Students in Firebase
    final String student_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    /**
     * The S ref.
     */
    DatabaseReference sRef = FirebaseDatabase.getInstance().getReference().child("Students").child(student_id);

    //list to store the record of time conflict test
    private LinkedList<String> testDateTimeRecord = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_information);

        //direct to course_id key
        course_id = getIntent().getStringExtra("Course ID");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Courses/" + course_id);

        //retreive course information from firebase and display them on UI
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                name = dataSnapshot.child("CourseName").getValue(String.class);
                prof = dataSnapshot.child("Professor").getValue(String.class);
                location = dataSnapshot.child("Location").getValue(String.class);
                date = dataSnapshot.child("Date").getValue(String.class);
                start = dataSnapshot.child("TimeStart").getValue(String.class);
                end = dataSnapshot.child("TimeEnd").getValue(String.class);
                slots = dataSnapshot.child("Slots").getValue(String.class);

                displayCourse(prof, name, location, date, start, end);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });


        //test whether this course has conflict with course already registered
        //record the test record for each comparson
        testDateTimeRecord.clear();
        sRef.child("Courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    String d = child.child("Date").getValue(String.class);
                    String s = child.child("TimeStart").getValue(String.class);
                    String e = child.child("TimeEnd").getValue(String.class);

                    DateTime enrolled_time = new DateTime(d, s, e);
                    DateTime new_time = new DateTime(date, start, end);

                    DateTimeConflict test = new DateTimeConflict();
                    if (test.TimeConflict(enrolled_time, new_time))
                        testDateTimeRecord.add("conflict");
                    else
                        testDateTimeRecord.add("pass");

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        //assign buttons
        buttonBack = (Button)findViewById(R.id.buttonBack);
        buttonDrop = (Button)findViewById(R.id.buttonDrop);
        buttonRegister = (Button)findViewById(R.id.buttonRegister);


        //back button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), Activity_OfferedCourses.class));
            }
        });


        //Drop button, only works if user is actually *in* the course
        //otherwise sends a toast message and waits
        buttonDrop.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                sRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("Courses").hasChild(course_id))
                        {
                            sRef.child("Courses").child(course_id).setValue(null);
                            int slotNum = Integer.parseInt(slots);
                            slotNum--;
                            myRef.child("Slots").setValue(Integer.toString(slotNum));
                            Toast.makeText(getApplicationContext(), "Drop Success!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), Activity_OfferedCourses.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "You cannot drop a course you are not in", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        //Register button
        //the timing conflict detector serves double duty as also preventing multiple
        //registerations in the same class by the same user
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if the test record of conflict test contains a "conflict" record
                if(testDateTimeRecord.contains("conflict")) {

                    //report error of time conflict
                    Toast.makeText(getApplicationContext(), "Can't register! Time conflict!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Activity_OfferedCourses.class));

                }
                //if not
                else if (Integer.parseInt(slots) > 70) {
                    Toast.makeText(getApplicationContext(), "No slots available", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Activity_OfferedCourses.class));
                }
                else{

                    //register the course with attributes
                    sRef.child("Courses").child(course_id).child("Professor").setValue(prof);
                    sRef.child("Courses").child(course_id).child("CourseName").setValue(name);
                    sRef.child("Courses").child(course_id).child("Date").setValue(date);
                    sRef.child("Courses").child(course_id).child("Location").setValue(location);
                    sRef.child("Courses").child(course_id).child("TimeStart").setValue(start);
                    sRef.child("Courses").child(course_id).child("TimeEnd").setValue(end);

                    int slotNum = Integer.parseInt(slots);
                    slotNum++;
                    myRef.child("Slots").setValue(Integer.toString(slotNum));

                    //report register success
                    Toast.makeText(getApplicationContext(), "Register Success!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Activity_OfferedCourses.class));

                }

            }

        });

    }

    /**
     * Display course. Lots of textviews getting filled out.
     *
     * @param prof     the prof
     * @param name     the name
     * @param location the location
     * @param date     the date
     * @param start    the start
     * @param end      the end
     */
//Display the course information
    public void displayCourse(String prof, String name, String location, String date, String start, String end) {

        CourseID = (TextView) findViewById(R.id.CourseID);
        CourseID.setText(course_id);
        CourseID.setTextColor(Color.BLACK);

        Professor = (TextView) findViewById(R.id.professor);
        Professor.setText("Professor:     " + prof);

        CourseName = (TextView) findViewById(R.id.course_name);
        CourseName.setText("Course Name:     " + name);

        Location = (TextView) findViewById(R.id.Location);
        Location.setText("Location:     " + location);

        Date = (TextView) findViewById(R.id.Date);
        Date.setText("Date:     " + date);

        TimeStart = (TextView) findViewById(R.id.time_start);
        TimeStart.setText("Time Start:     " + start);

        TimeEnd = (TextView) findViewById(R.id.time_end);
        TimeEnd.setText("Time End:     " + end);

        SlotHolder = (TextView)findViewById(R.id.slots);
        SlotHolder.setText("Slots Remaining:    " + slots + " of 70");


    }



}
