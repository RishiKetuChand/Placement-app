package com.example.placementapp.acitivties;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.placementapp.MainActivity;
import com.example.placementapp.R;
import com.example.placementapp.utilites.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class OtpAuthActivity extends AppCompatActivity {
    String role,stringUserName,stringUserUsn,stringUserPassword,stringUserEmail,stringUserPhone,verificationId,userUID;
    EditText enterOtp;
    Button verify;
    TextView editNum;
    FirebaseDatabase database;
    String phone;
    FirebaseAuth mAuth;
    DatabaseReference root;
    PreferenceManager preferenceManager = new PreferenceManager(this);
    FirebaseUser currentUserOfAuth;
    ProgressBar progressBar;
    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_auth);
        Toast.makeText(this, "Please Wait Checking For Captcha", Toast.LENGTH_SHORT).show();
        role = getIntent().getStringExtra("userRole");
        stringUserName = getIntent().getStringExtra("userName");
        stringUserEmail = getIntent().getStringExtra("userEmail");
        stringUserPassword = getIntent().getStringExtra("userPassword");
        stringUserPhone = getIntent().getStringExtra("userPhone");
        stringUserUsn = getIntent().getStringExtra("userUsn");
        window=this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.white));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        initializeView();
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validating if the OTP text field is empty or not.
                if (enterOtp.getText().toString().isEmpty()){
                    //if the OTP text field is empty display a message to user to enter OTP
                    Toast.makeText(OtpAuthActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                }else{
                    //if OTP field is not empty calling method to verify the OTP.
                    verifyCode(enterOtp.getText().toString());
                }

            }
        });
        editNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtpAuthActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void initializeView(){
        mAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        root=database.getReference();
        enterOtp=findViewById(R.id.enterOtp);
        verify=findViewById(R.id.btnVerify);
        editNum=findViewById(R.id.editNum);
        progressBar=findViewById(R.id.progressBar);
        phone ="+91"+stringUserPhone;
        sendVerificationCode(phone);
    }

    //initializing on click listner for verify otp button

    private void signInWithCredential(PhoneAuthCredential credential) {
        //inside this method we are checking if the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //if the code is correct and the task is succesful we are sending our user to new activity.
                                userUID = mAuth.getCurrentUser().getUid();
                                if (role.equals("Student")) {
                                    root=database.getReference().child("usersStudents");
                                    preferenceManager.saveUserRole(role);
                                    preferenceManager.saveUserData(stringUserName, stringUserEmail, stringUserPhone, stringUserUsn.toLowerCase(), stringUserPassword);
                                    uploadingUserDataToFireBase();
                                    progressBar.setVisibility(View.GONE);
                                    Intent intent = new Intent(OtpAuthActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    root=database.getReference().child("usersPlacementOfficer");
                                    preferenceManager.saveUserRole(role);
                                    preferenceManager.saveProfUserData(stringUserName, stringUserEmail, stringUserPhone, stringUserPassword);
                                    uploadingProfessorUserDataToFireBase();
                                    progressBar.setVisibility(View.GONE);
                                    Intent intent = new Intent(OtpAuthActivity.this,ProfHomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                        } else {
                            //if the code is not correct then we are displaying an error message to the user.
                            Toast.makeText(OtpAuthActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String number) {
        //this method is used for getting OTP on user phone number.

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(90L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    //callback method is called on Phone auth provider.
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks
            //initializing our callbacks for on verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        //below method is used when OTP is sent from Firebase
        @Override
        public void onCodeSent(@NotNull String s, @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            final String code = phoneAuthCredential.getSmsCode();
            enterOtp.setText(code);
            verifyCode(code);
           /* if (code != null) {
                enterOtp.setText(code);
                verifyCode(code);
            }

            */

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OtpAuthActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    //below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        //below line is used for getting getting credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        //after getting credential we are calling sign in method.
        signInWithCredential(credential);
        progressBar.setVisibility(View.VISIBLE);
    }
    public void uploadingUserDataToFireBase(){
        HashMap<String,String> userMap= new HashMap<>();
        userMap.put("Name",(stringUserName));
        userMap.put("Email",(stringUserEmail));
        userMap.put("PhoneNumber",(stringUserPhone));
        userMap.put("USN",(stringUserUsn));
        userMap.put("Password",(stringUserPassword));
        root.child(userUID).setValue(userMap).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                AuthCredential credential = EmailAuthProvider.getCredential(preferenceManager.getUserEmail(),preferenceManager.getUserUSN());
                currentUserOfAuth.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                currentUserOfAuth.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(OtpAuthActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        });
            }
        });
    }

    public void uploadingProfessorUserDataToFireBase(){
        HashMap<String,String> userMap= new HashMap<>();
        userMap.put("Name",(stringUserName));
        userMap.put("Email",(stringUserEmail));
        userMap.put("PhoneNumber",(stringUserPhone));
        userMap.put("Password",(stringUserPassword));
        root.child(userUID).setValue(userMap).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                AuthCredential credential = EmailAuthProvider.getCredential(preferenceManager.getUserEmail(),preferenceManager.getUserUSN());
                currentUserOfAuth.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                currentUserOfAuth.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(OtpAuthActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        });
            }
        });
    }
}

