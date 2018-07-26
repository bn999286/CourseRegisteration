package com.example.group12.courseregisteration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * The type Activity user profile.
 * Basic functionality written by Peter and Xao
 * Cleaned up by Bin He and Chasteen
 */
public class Activity_UserProfile extends AppCompatActivity {

    private Button buttonSignOut;
    private Button buttonOfferedCourses;
    private Button buttonSchedule;
    private Button changePassword;
    private Button buttonTuitionFee;
    private TextView showEmail;
    private FirebaseAuth mAuth;
    private Button toProfileSelection;
    String fileName = "";
    private StorageReference mStorageRef;
    File localFile;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mImageView = findViewById(R.id.imageViewProfileImage);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this,Activity_SignIn.class));
        }

        mStorageRef = FirebaseStorage.getInstance().getReference();

        final String student_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference sRef = FirebaseDatabase.getInstance().getReference("Students/"+ student_id+ "/profPic");
        sRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null)
                {
                    fileName = (String) dataSnapshot.getValue();

                    mStorageRef.child(fileName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.with(getApplicationContext()).load(uri).into(mImageView);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getApplicationContext(), "err, failure to load profile pic", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        buttonSignOut =(Button)findViewById(R.id.buttonSignOut);
        buttonOfferedCourses = (Button)findViewById(R.id.buttonOfferedCourse);
        buttonSchedule = (Button)findViewById(R.id.buttonSchedule);
        buttonTuitionFee = (Button)findViewById(R.id.buttonTuitionFee);
        toProfileSelection = (Button)findViewById(R.id.buttonSetImage);


        changePassword = (Button)findViewById(R.id.buttonPassword);

        //toProfileImageSelection
        toProfileSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), Activity_ProfileImage.class));
            }
        });

        //password button
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), Activity_ResetPassword.class));
            }
        });


        //sign out button
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), Activity_SignIn.class));

            }
        });


        //offeredCourses button
        buttonOfferedCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Activity_OfferedCourses.class));
            }
        });


        //Schedule button
        buttonSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Activity_Schedule_Mon.class));
            }
        });

        //Tuition Fee button
        buttonTuitionFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Activity_Tuition_Fee.class));
            }
        });



    }
}
