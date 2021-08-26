package com.example.placementapp.acitivties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.placementapp.R;

public class StudentDetailsActivity extends AppCompatActivity {
    String companyNameString,locationString,recruitmentDateString,allowedDepartmentString,studentNameString,studentEmailString,studentPhoneNumberString,studentUSNString;
    TextView companyName, recruitmentDate,location,allowedDepartment,studentName,studentEmail,studentPhoneNumber,studentUsn;
    Toolbar toolbar;
    Button done;
    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        companyNameString=getIntent().getStringExtra("companyName");
        locationString=getIntent().getStringExtra("location");
        recruitmentDateString=getIntent().getStringExtra("recruitmentDate");
        allowedDepartmentString=getIntent().getStringExtra("allowedDepartment");
        studentNameString=getIntent().getStringExtra("studentName");
        studentEmailString=getIntent().getStringExtra("studentEmail");
        studentPhoneNumberString=getIntent().getStringExtra("studentPhoneNumber");
        studentUSNString=getIntent().getStringExtra("studentUSN");
        initializeView();
        companyName.setText(companyNameString);
        recruitmentDate.setText(recruitmentDateString);
        location.setText(locationString);
        allowedDepartment.setText(allowedDepartmentString);
        studentName.setText(studentNameString);
        studentEmail.setText(studentEmailString);
        studentPhoneNumber.setText(studentPhoneNumberString);
        studentUsn.setText(studentUSNString);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void initializeView(){
        toolbar=findViewById(R.id.stud_details_toolbar);
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
        done=findViewById(R.id.doneBtn);
    }
}