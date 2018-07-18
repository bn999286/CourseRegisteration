package com.example.group12.courseregisteration;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.Spannable;
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
    private TextView Enrollment;

    //button
    private Button buttonBack;
    private Button buttonRegister;
    private Button buttonDrop;
    private Button buttonProfessor;

    //string
    private String course_id;
    private String name;
    private String prof;
    private String location;
    private String date;
    private String start;
    private String end;
    private String enroll;
    private String fee;


    //count the number of student's courses
    private int Student_course_count;

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
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                name = dataSnapshot.child("CourseName").getValue(String.class);
                prof = dataSnapshot.child("Professor").getValue(String.class);
                location = dataSnapshot.child("Location").getValue(String.class);
                date = dataSnapshot.child("Date").getValue(String.class);
                start = dataSnapshot.child("TimeStart").getValue(String.class);
                end = dataSnapshot.child("TimeEnd").getValue(String.class);
                enroll = dataSnapshot.child("Slots").getValue(String.class);
                fee = dataSnapshot.child("Fee").getValue(String.class);


                displayCourse(prof, name, location, date, start, end, enroll);

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
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    String d = child.child("Date").getValue(String.class);
                    String s = child.child("TimeStart").getValue(String.class);
                    String e = child.child("TimeEnd").getValue(String.class);

                    //Count the student courses
                    Student_course_count ++;

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
        buttonProfessor = (Button)findViewById(R.id.buttonProfessor);


        //back button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), Activity_OfferedCourses.class));
            }
        });


        //Professor information button
        buttonProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getBaseContext(), Activity_ProfessorInformation.class);

                //pass professor name and course id to professor information page
                Bundle extras = new Bundle();
                extras.putString("Professor", prof);
                extras.putString("Course_ID", course_id);

                intent.putExtras(extras);
                finish();
                startActivity(intent);

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
                            //delete keys
                            sRef.child("Courses").child(course_id).setValue(null);

                            //decrease enroll number by 1
                            int enroll_num = Integer.parseInt(enroll);
                            enroll_num--;

                            myRef.child("Slots").setValue(Integer.toString(enroll_num));

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
                //if student course count is already 5
                else if(Student_course_count ==5){

                    //report error of over course limit
                    Toast.makeText(getApplicationContext(), "Exceed course limit 5!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Activity_OfferedCourses.class));

                }
                //if enrollment for this course is larger than maximum enrollment
                else if (Integer.parseInt(enroll) > 70) {
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
                    sRef.child("Courses").child(course_id).child("Fee").setValue(fee);

                    //increase enrollment number by 1
                    int enroll_num = Integer.parseInt(enroll);
                    enroll_num++;
                    myRef.child("Slots").setValue(Integer.toString(enroll_num));

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
     * @param prof      the professor name
     * @param enroll      the enroll
     */
//Display the course information
    public void displayCourse(@NonNull String prof, String name, String location, String date, String start, String end, String enroll) {

        //Assign the textview
        CourseID = (TextView) findViewById(R.id.CourseID);
        Professor = (TextView) findViewById(R.id.professor);
        CourseName = (TextView) findViewById(R.id.course_name);
        Location = (TextView) findViewById(R.id.Location);
        Date = (TextView) findViewById(R.id.Date);
        TimeStart = (TextView) findViewById(R.id.time_start);
        TimeEnd = (TextView) findViewById(R.id.time_end);
        Enrollment = (TextView)findViewById(R.id.Enrollment);


        //set text of course id
        CourseID.setText(course_id);
        CourseID.setTextColor(Color.BLACK);


        //set the title words black color
        Spannable wordProfessor = new SpannableString("Professor:");
        wordProfessor.setSpan(new ForegroundColorSpan(Color.BLACK), 0, wordProfessor.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Spannable wordCourseName = new SpannableString("Course Name:");
        wordCourseName.setSpan(new ForegroundColorSpan(Color.BLACK), 0, wordCourseName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Spannable wordLocation= new SpannableString("Location:");
        wordLocation.setSpan(new ForegroundColorSpan(Color.BLACK), 0, wordLocation.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Spannable wordDate = new SpannableString("Date:");
        wordDate.setSpan(new ForegroundColorSpan(Color.BLACK), 0, wordDate.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Spannable wordTimeStart = new SpannableString("Time start:");
        wordTimeStart.setSpan(new ForegroundColorSpan(Color.BLACK), 0, wordTimeStart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Spannable wordTimeEnd = new SpannableString("Time end:");
        wordTimeEnd.setSpan(new ForegroundColorSpan(Color.BLACK), 0, wordTimeEnd.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Spannable wordEnroll = new SpannableString("Enrollment:");
        wordEnroll.setSpan(new ForegroundColorSpan(Color.BLACK), 0, wordEnroll.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        //set text of course information
        Professor.setText(wordProfessor);
        Professor.append("  " + prof);

        CourseName.setText(wordCourseName);
        CourseName.append("  " + name);

        Location.setText(wordLocation);
        Location.append("  " + location);

        Date.setText(wordDate);
        Date.append("  " + date);

        TimeStart.setText(wordTimeStart);
        TimeStart.append("  " + start);

        TimeEnd.setText(wordTimeEnd);
        TimeEnd.append("  " + end);

        Enrollment.setText(wordEnroll);
        Enrollment.append("  " + enroll + " of " + "70");
        Enrollment.setTextColor(Color.RED);


    }



}
