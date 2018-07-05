package com.example.group12.courseregisteration;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.LinkedList;


public class Activity_Schedule_Mon extends AppCompatActivity {

    private Button buttonBack;
    private Button buttonOfferedCourses;
    private Button buttonMon;
    private Button buttonTue;
    private Button buttonWed;
    private Button buttonThu;
    private Button buttonFri;

    private TextView DateView;

    private LinkedList<Course> Monday_courses = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        //direct to student_id, the child of root Students in Firebase
        final String student_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference sRef = FirebaseDatabase.getInstance()
                .getReference().child("Students").child(student_id).child("Courses");

        //add courses in specific date course list
        //display courses
        sRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    String date = child.child("Date").getValue(String.class);
                    String start = child.child("TimeStart").getValue(String.class);
                    String end = child.child("TimeEnd").getValue(String.class);
                    String name = child.child("CourseName").getValue(String.class);
                    String location = child.child("Location").getValue(String.class);
                    String professor = child.child("Professor").getValue(String.class);

                    Course course = new Course(name, date, start, end, location, professor);

                    if (date != null) {
                        for (int i = 0; i < date.length(); i++) {

                            if (date.charAt(i) == 'M') {
                                Monday_courses.add(course);
                            }

                        }
                    }

                }
                //dispay course on the pad
                displayDailyCourses(Monday_courses);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //display date
        DateView = (TextView) findViewById(R.id.textViewDate);
        DateView.setText("Monday");

        //set buttons
        buttonMon = (Button) findViewById(R.id.buttonMon);
        buttonTue = (Button) findViewById(R.id.buttonTue);
        buttonWed = (Button) findViewById(R.id.buttonWed);
        buttonThu = (Button) findViewById(R.id.buttonThu);
        buttonFri = (Button) findViewById(R.id.buttonFri);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonOfferedCourses = (Button) findViewById(R.id.buttonOfferedCourses);

        buttonMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Activity_Schedule_Mon.class));
            }
        });
        buttonTue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Activity_Schedule_Tue.class));
            }
        });
        buttonWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Activity_Schedule_Wed.class));
            }
        });
        buttonThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Activity_Schedule_Thu.class));
            }
        });
        buttonFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Activity_Schedule_Fri.class));
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Activity_UserProfile.class));
            }
        });
        buttonOfferedCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Activity_OfferedCourses.class));
            }
        });




    }

    @NonNull
    //display course method
    private void displayDailyCourses(LinkedList<Course> daily_courses) {

        for (int i = 0; i < daily_courses.size(); i++) {

            String start = daily_courses.get(i).getTimeStart();
            String end = daily_courses.get(i).getTimeEnd();
            String name = daily_courses.get(i).getCourseName();
            String professor = daily_courses.get(i).getCourseProfessor();
            String location = daily_courses.get(i).getCourseLocation();

            @NonNull
            String[] start_hourMinutes = start.split(":");
            int start_hr = Integer.parseInt(start_hourMinutes[0]);
            int start_min = Integer.parseInt(start_hourMinutes[1]);
            int Start_time = (start_hr * 60) + ((start_min * 60) / 100);

            @NonNull
            String[] end_hourMinutes = end.split(":");
            int end_hr = Integer.parseInt(end_hourMinutes[0]);
            int end_min = Integer.parseInt(end_hourMinutes[1]);
            int End_time = (end_hr * 60) + ((end_min * 60) / 100);


            int BlockHeight = End_time - Start_time;


            TextView EventView = new TextView(Activity_Schedule_Mon.this);
            RelativeLayout.LayoutParams lParam = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);

            lParam.topMargin = (Start_time - 400) * 4;
            lParam.leftMargin = 24;

            EventView.setLayoutParams(lParam);

            EventView.setPadding(24, 0, 24, 0);
            EventView.setHeight(BlockHeight * 4);
            EventView.setWidth(491);
            EventView.setGravity(0x11);
            EventView.setTextColor(Color.BLACK);
            EventView.setText(start + " - " + end + "\n" + name + "\n" + "Professor: " + professor + "\n" + "Location: " + location);
            EventView.setBackgroundColor(Color.parseColor("#F5B041"));

            RelativeLayout mLayout = (RelativeLayout) findViewById(R.id.left_event_column);
            mLayout.addView(EventView);

        }


    }


}

