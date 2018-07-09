package com.example.group12.courseregisteration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    private Button btnSubmit;
    private EditText mName,mEmail,mPhoneNum, mpassword;
    private String userID;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        mName = (EditText) findViewById(R.id.etName);
        mEmail = (EditText) findViewById(R.id.etEmail);
        mPhoneNum = (EditText) findViewById(R.id.etPhone);
        mpassword = (EditText)findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        user = mAuth.getCurrentUser();
        if (user != null) {
            toastMessage("Successfully signed in with: " + user.getEmail());
            finish();
            startActivity(new Intent(getApplicationContext(), Activity_UserProfile.class));
        } else {
            // User is signed out
            toastMessage("Please Log In!");
        }


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = mName.getText().toString().trim();
                final String email = mEmail.getText().toString().trim();
                final String phoneNum = mPhoneNum.getText().toString().trim();
                final String password = mpassword.getText().toString().trim();


                //handle the exception if the EditText fields are null
                if(!name.equals("") && !email.equals("") && !phoneNum.equals("") && !password.equals("")){
                    Toast.makeText(Register.this, email, Toast.LENGTH_SHORT).show();

                    mAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        user = mAuth.getCurrentUser();
                                        userID = user.getUid();

                                        UserInformation userInformation = new UserInformation(email,name,phoneNum, password);
                                        myRef.child("users").child(userID).setValue(userInformation);
                                        toastMessage("New Information has been saved.");
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), Activity_UserProfile.class));
                                    }
                                    else{
                                        Toast.makeText(Register.this, "Could not register", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    toastMessage("Fill out all the fields");
                }
            }
        });
    }




    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
