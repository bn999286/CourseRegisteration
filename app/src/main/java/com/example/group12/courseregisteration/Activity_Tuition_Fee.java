package com.example.group12.courseregisteration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class Activity_Tuition_Fee extends AppCompatActivity {

    private Button Back;

    private TextView ViewFee;

    private LinkedList<String> Fee_list = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition_fee);


        //direct to student_id, the child of root Students in Firebase
        final String student_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference sRef = FirebaseDatabase.getInstance().getReference().child("Students").child(student_id).child("Courses");

        Fee_list.clear();
        sRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    String fee = child.child("Fee").getValue(String.class);
                    Fee_list.add(fee);

                }


                if(Fee_list!=null) {

                    double totalFee = 0;

                    //Calculate total fee
                    for (int i = 0; i < Fee_list.size(); i++) {
                        totalFee = totalFee + Double.parseDouble(Fee_list.get(i));
                    }

                    //Display total fee
                    ViewFee = (TextView) findViewById(R.id.textViewFee);
                    ViewFee.setText("$ " + String.valueOf(totalFee));

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });




        //Back button
        Back = (Button) findViewById(R.id.buttonBack);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Activity_UserProfile.class));
            }
        });


    }
}
