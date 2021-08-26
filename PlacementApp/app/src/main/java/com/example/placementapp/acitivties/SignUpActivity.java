package com.example.placementapp.acitivties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.placementapp.MainActivity;
import com.example.placementapp.R;
import com.example.placementapp.utilites.ConnectivityCheck;
import com.example.placementapp.utilites.PreferenceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import org.jetbrains.annotations.Nullable;

public class SignUpActivity extends AppCompatActivity {
    PowerSpinnerView powerSpinnerView;
    Button signUp;
    ImageView imageView;
    EditText userName,userUsn,userPassword,userEmail,userPhone;
    TextView alreadyUser;
    String role,stringUserName,stringUserUsn,stringUserPassword,stringUserEmail,stringUserPhone;
    FirebaseAuth mAuth;
    PreferenceManager preferenceManager = new PreferenceManager(this);
    Window window;
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        broadcastReceiver = new ConnectivityCheck();
        registerRequest();
        window=this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.white));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        initializeView();
        powerSpinnerView.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
            @Override
            public void onItemSelected(int i, @Nullable String s, int i1, String t1) {
                role =t1;
                if (role.equals("Student")){
                    imageView.setImageResource(R.drawable.graduate);
                    studentDataRegister();
                } else {
                    imageView.setImageResource(R.drawable.profile);
                    placementDataRegister();
                }
            }
        });
        alreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
        public void initializeView() {
        mAuth=FirebaseAuth.getInstance();
            if (mAuth.getCurrentUser()!=null){
                if(preferenceManager.getUserRole().equals("Student")){
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SignUpActivity.this,ProfHomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            userName = findViewById(R.id.userName);
            userEmail = findViewById(R.id.userEmail);
            userPassword = findViewById(R.id.userPassword);
            userPhone = findViewById(R.id.userPhoneNumber);
            userUsn = findViewById(R.id.userUSN);
            alreadyUser = findViewById(R.id.alreadyUser);
            signUp = findViewById(R.id.btnSignUp);
            powerSpinnerView = findViewById(R.id.roleSpinnerView);
            imageView=findViewById(R.id.userImage);

            userName.setVisibility(View.GONE);
            userEmail.setVisibility(View.GONE);
            userPhone.setVisibility(View.GONE);
            userPassword.setVisibility(View.GONE);
            userUsn.setVisibility(View.GONE);
            signUp.setVisibility(View.GONE);
        }
        public void studentDataRegister(){
            userName.setVisibility(View.VISIBLE);
            userEmail.setVisibility(View.VISIBLE);
            userPhone.setVisibility(View.VISIBLE);
            userPassword.setVisibility(View.VISIBLE);
            userUsn.setVisibility(View.VISIBLE);
            signUp.setVisibility(View.VISIBLE);

            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(userName.getText().toString().isEmpty() || userEmail.getText().toString().isEmpty() || userUsn.getText().toString().isEmpty() ||
                            userPhone.getText().toString().isEmpty() || userPassword.getText().toString().isEmpty() || userPhone.getText().toString().length()!=10 || userUsn.getText().toString().length()!=10){
                        Toast.makeText(SignUpActivity.this, "Please enter a valid credentials", Toast.LENGTH_SHORT).show();
                    } else if(userPassword.getText().toString().length()!=8){
                        Toast.makeText(SignUpActivity.this, "Please enter 8 digit password", Toast.LENGTH_SHORT).show();
                    } else {
                        stringUserName=userName.getText().toString();
                        stringUserEmail=userEmail.getText().toString();
                        stringUserUsn=userUsn.getText().toString();
                        stringUserPassword=userPassword.getText().toString();
                        stringUserPhone=userPhone.getText().toString();

                        Intent intent = new Intent(SignUpActivity.this,OtpAuthActivity.class);
                        intent.putExtra("userName",stringUserName);
                        intent.putExtra("userEmail",stringUserEmail);
                        intent.putExtra("userPassword",stringUserPassword);
                        intent.putExtra("userUsn",stringUserUsn);
                        intent.putExtra("userPhone",stringUserPhone);
                        intent.putExtra("userRole",role);
                        startActivity(intent);
                        finish();
                    }
                }
            });

        }
        public void placementDataRegister(){
        userUsn.setVisibility(View.GONE);
            userName.setVisibility(View.VISIBLE);
            userEmail.setVisibility(View.VISIBLE);
            userPhone.setVisibility(View.VISIBLE);
            userPassword.setVisibility(View.VISIBLE);
            signUp.setVisibility(View.VISIBLE);

            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(userName.getText().toString().isEmpty() || userEmail.getText().toString().isEmpty() ||
                            userPhone.getText().toString().isEmpty() || userPassword.getText().toString().isEmpty() || userPhone.getText().toString().length()!=10){
                        Toast.makeText(SignUpActivity.this, "Please enter a valid credentials", Toast.LENGTH_SHORT).show();
                    } else if(userPassword.getText().toString().length()!=8){
                        Toast.makeText(SignUpActivity.this, "Please enter 8 digit password", Toast.LENGTH_SHORT).show();
                    } else {
                        stringUserName=userName.getText().toString();
                        stringUserEmail=userEmail.getText().toString();
                        stringUserPassword=userPassword.getText().toString();
                        stringUserPhone=userPhone.getText().toString();
                        Intent intent = new Intent(SignUpActivity.this,OtpAuthActivity.class);
                        intent.putExtra("userName",stringUserName);
                        intent.putExtra("userEmail",stringUserEmail);
                        intent.putExtra("userPassword",stringUserPassword);
                        intent.putExtra("userPhone",stringUserPhone);
                        intent.putExtra("userRole",role);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }

    public void registerRequest(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }
    protected void unregisterRequest(){
        try {
            unregisterReceiver(broadcastReceiver);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterRequest();
    }

}