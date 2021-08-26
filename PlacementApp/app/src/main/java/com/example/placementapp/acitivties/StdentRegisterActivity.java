package com.example.placementapp.acitivties;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.example.placementapp.R;
import com.example.placementapp.adapters.ViewCompanyForStudAdapter;
import com.example.placementapp.utilites.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;

public class StdentRegisterActivity extends AppCompatActivity {
    TextView companyName, recruitmentDate,location,allowedDepartment;
    EditText studentName,studentEmail,studentPhoneNumber,studentUsn;
    Toolbar toolbar;
    Button register;
    ProgressBar progressBar;
    FirebaseDatabase database;
    DatabaseReference root;
    Window window;
    String companyNameString, recruitmentDateString,locationString,allowedDepartmentString,docUrlString,uidString;
    PreferenceManager preferenceManager = new PreferenceManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stdent_register);
        companyNameString=getIntent().getStringExtra("name");
        recruitmentDateString=getIntent().getStringExtra("date");
        locationString=getIntent().getStringExtra("loc");
        allowedDepartmentString=getIntent().getStringExtra("dept");
        docUrlString=getIntent().getStringExtra("docUrl");
        uidString=getIntent().getStringExtra("uid");
        database=FirebaseDatabase.getInstance();
        root=database.getReference().child("RegisteredStudent");
        initializeView();
        companyName.setText(companyNameString);
        recruitmentDate.setText(recruitmentDateString);
        location.setText(locationString);
        allowedDepartment.setText(allowedDepartmentString);
        studentName.setText(preferenceManager.getUserName());
        studentEmail.setText(preferenceManager.getUserEmail());
        studentPhoneNumber.setText(preferenceManager.getUserPhoneNumber());
        studentUsn.setText(preferenceManager.getUserUSN());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (studentName.getText().toString().isEmpty()||studentEmail.getText().toString().isEmpty()||
                        studentPhoneNumber.getText().toString().isEmpty()||studentUsn.getText().toString().isEmpty()){
                    Toast.makeText(StdentRegisterActivity.this, "Please enter a valid details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    uploadRegisterdStudents();
                }
            }
        });
    }

    public void initializeView(){
        toolbar=findViewById(R.id.stud_register_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        window=this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.white));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        companyName=findViewById(R.id.companyName);
        recruitmentDate=findViewById(R.id.enteredDate);
        location=findViewById(R.id.enteredLoaction);
        allowedDepartment=findViewById(R.id.enteredAllowedDepartment);
        studentEmail=findViewById(R.id.emailEt);
        studentName=findViewById(R.id.studNameEt);
        studentPhoneNumber=findViewById(R.id.phoneNumberET);
        studentUsn=findViewById(R.id.usnEt);
        progressBar=findViewById(R.id.addCompanyProgressBar);
        progressBar.bringToFront();
        register=findViewById(R.id.registerBtn);
    }
    private void uploadRegisterdStudents() {
        HashMap<String,String> userMap= new HashMap<>();
        userMap.put("companyName",(companyNameString));
        userMap.put("recruitmentDate",(recruitmentDateString));
        userMap.put("location",(locationString));
        userMap.put("allowedDepartment",(allowedDepartmentString));
        userMap.put("studentName",(studentName.getText().toString()));
        userMap.put("studentEmail",(studentEmail.getText().toString()));
        userMap.put("studentPhoneNumber",(studentPhoneNumber.getText().toString()));
        userMap.put("studentUSN",(studentUsn.getText().toString()));
        root.push().setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(StdentRegisterActivity.this, "Registration Sucessfull", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(StdentRegisterActivity.this, ViewCompanyActivity.class);
                intent.putExtra("key","stud");
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StdentRegisterActivity.this, "Unable to register", Toast.LENGTH_SHORT).show();
            }
        });
    }
}