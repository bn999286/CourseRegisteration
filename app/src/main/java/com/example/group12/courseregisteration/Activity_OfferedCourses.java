package com.example.group12.courseregisteration;

//to-do
//add javadoc and expresso

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Gets the list of courses from the database, and displays them here on a spinnner.
 * Basic functionality written by Peter and Xao
 */
public class Activity_OfferedCourses extends AppCompatActivity {

    private Button buttonBack;
    private ListView listview;
    MaterialSearchView searchView;
    String[] courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offered_courses);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Courses");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //get the name of every course in the database and save them in a string array
                //for later access by the spinner
                int count = (int) dataSnapshot.getChildrenCount();
                int position = 0;
                String[] courseNames = new String[count];

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    courseNames[position] = child.getKey();
                    position++;
                }

                courseName = courseNames;

                displayCourses(courseNames);


            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });




        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Course Search");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        searchView = (MaterialSearchView)findViewById(R.id.search_view);

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                displayCourses(courseName);
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText != null && !newText.isEmpty())
                {
                    List<String> IsFound = new ArrayList<String>();
                    for (String s : courseName)
                    {
                        if(s.toLowerCase().contains(newText))
                        {
                            IsFound.add(s);
                        }
                    }

                    String[] stockArr = new String[IsFound.size()];
                    stockArr = IsFound.toArray(stockArr);
                    displayCourses(stockArr);
                }
                else
                {
                    displayCourses(courseName);
                }

                return true;
            }
        });


        //back button to user profile
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), Activity_UserProfile.class));
            }
        });


    }


    /**
     * Display courses.
     *
     * @param courseNames the course names
     */
    public void displayCourses(String[] courseNames) {

        ListAdapter adpt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courseNames);
        listview = (ListView) findViewById(R.id.viewer);
        listview.setAdapter(adpt);

        //set the color of the item if course has been registered
        String student_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference sRef = FirebaseDatabase.getInstance().getReference("Students/" + student_id +"/Courses");

        sRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    String course_id = child.getKey();

                    if (course_id != null) {

                        for (int i = 0; i < listview.getAdapter().getCount(); i++) {

                            if (course_id.equals(listview.getAdapter().getItem(i).toString())) {

                                if(listview.getChildAt(i)!=null) {
                                    listview.getChildAt(i).setBackgroundColor(Color.YELLOW);
                                }

                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //View course information by clicking item
        listview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String course_id = String.valueOf(parent.getItemAtPosition(position));
                        Intent intent = new Intent(getBaseContext(), Activity_CourseInformation.class);
                        intent.putExtra("Course ID", course_id);
                        finish();
                        startActivity(intent);
                    }
                }
        );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }


}