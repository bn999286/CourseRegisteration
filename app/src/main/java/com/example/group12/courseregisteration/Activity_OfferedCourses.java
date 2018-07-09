package com.example.group12.courseregisteration;

//to-do
//add javadoc and expresso

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//lists offered courses from database
public class Activity_OfferedCourses extends AppCompatActivity {


    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offered_courses);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Courses");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int count = (int) dataSnapshot.getChildrenCount();
                int position = 0;
                String[] courseNames = new String[count];
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    courseNames[position] = child.getKey();
                    position++;
                }

                displayCourses(courseNames);

            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });


        //back button
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), Activity_UserProfile.class));
            }
        });


    }


    //display course method
    public void displayCourses(String[] courseNames) {

        ListAdapter adpt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courseNames);
        ListView listview = (ListView) findViewById(R.id.viewer);
        listview.setAdapter(adpt);

        listview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String food = String.valueOf(parent.getItemAtPosition(position));
                        Intent intent = new Intent(getBaseContext(), Activity_CourseInformation.class);
                        intent.putExtra("Course ID", food);
                        finish();
                        startActivity(intent);
                    }
                }
        );

    }


}