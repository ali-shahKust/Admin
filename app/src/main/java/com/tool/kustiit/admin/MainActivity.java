package com.tool.kustiit.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private EditText phone ;
    private Button loginBtn;

    private ProgressDialog mProgress;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        phone = (EditText) findViewById(R.id.phone);
        loginBtn = (Button)findViewById(R.id.loginbtn);
        mProgress = new ProgressDialog(this);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginwithPhone();
            }
        });

    }

    private void loginwithPhone() {

        mProgress.setTitle("Verifying Phone");
        mProgress.setMessage("Please wait a while");
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();
        PhoneAuthProvider auth = PhoneAuthProvider.getInstance();

        auth.verifyPhoneNumber(phone.getText().toString(), 60, TimeUnit.SECONDS, MainActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        mUser = FirebaseAuth.getInstance().getCurrentUser();
                        final String uID = mUser.getUid();
                        System.err.println("User id is " + uID);


                        if(task.isSuccessful()){
                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Admin").child(uID);
                            HashMap<String, Object> user_map = new HashMap<>();

                            user_map.put("user_number", phone.getText().toString());
                            userRef.setValue(user_map);

                            Toast.makeText(MainActivity.this, "logged in", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, AdminCategory.class);
                            startActivity(intent);
                            finish();
                            mProgress.dismiss();

                        }
                        else {
                            task.getException();
                        }


                    }
                });
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Log.i("dxdiag", e.getMessage());

            }
        });
    }
}
