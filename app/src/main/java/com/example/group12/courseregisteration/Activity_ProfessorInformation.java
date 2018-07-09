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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity_ProfessorInformation extends AppCompatActivity {

    //button
    private Button buttonBack;

    //text view
    private TextView Professor_name;
    private TextView Email;
    private TextView Office;
    private TextView Office_hours;

    //string from course information page
    private String professor;
    private String course_id;

    //string
    private String prof_name;
    private String email;
    private String office;
    private String office_hrs;

    //Professor list to store professors information from firebase
    ProfessorList prof_list = new ProfessorList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_information);

        //get professor and course id name from course information page
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        professor = extras.getString("Professor");
        course_id = extras.getString("Course_ID");

        //read professor information from firebase and add to professor list
        DatabaseReference pRef = FirebaseDatabase.getInstance().getReference().child("Professors");

        prof_list.clear();
        pRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //retrieve professor information from firebase and add to the professor list
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    prof_name = child.child("Name").getValue(String.class);
                    email = child.child("Email Address").getValue(String.class);
                    office = child.child("Office").getValue(String.class);
                    office_hrs = child.child("Office Hours").getValue(String.class);

                    Professor prof = new Professor(prof_name, email, office, office_hrs);
                    prof_list.add(prof);

                }

                //if list contain professor
                //display the professor information
                if(prof_list.Prof_list_exist(professor)!=false){

                    int i = prof_list.index(professor);
                    //display professor information
                    displayProfessor(prof_list.get(i).getEmail(),
                            prof_list.get(i).getOffice(),
                            prof_list.get(i).getOffice_hrs());

                }
                else {
                    //display not available
                    displayProfessor("NA", "NA","NA");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //back button to course information page
        buttonBack = (Button)findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Activity_CourseInformation.class);
                intent.putExtra("Course ID", course_id);
                finish();
                startActivity(intent);
            }
        });



    }

    //display the professor information method
    public void displayProfessor(String email, String office, String office_hrs){

        Professor_name = (TextView) findViewById(R.id.textViewProfessorName);
        Email = (TextView) findViewById(R.id.textViewEmail);
        Office = (TextView) findViewById(R.id.textViewOffice);
        Office_hours = (TextView) findViewById(R.id.textViewOfficeHours);

        //set professor name black color
        Professor_name.setTextColor(Color.BLACK);

        //set the title words black color
        Spannable wordEmail = new SpannableString("Email:");
        wordEmail.setSpan(new ForegroundColorSpan(Color.BLACK), 0, wordEmail.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Spannable wordOffice = new SpannableString("Office:");
        wordOffice.setSpan(new ForegroundColorSpan(Color.BLACK), 0, wordOffice.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Spannable wordOfficeHours = new SpannableString("Office hours:");
        wordOfficeHours.setSpan(new ForegroundColorSpan(Color.BLACK), 0, wordOfficeHours.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //set the text of professor information
        Professor_name.setText(professor);

        Email.setText(wordEmail);
        Email.append("  " + email);

        Office.setText(wordOffice);
        Office.append("  " + office);

        Office_hours.setText(wordOfficeHours);
        Office_hours.append("  " + office_hrs);


    }
}
