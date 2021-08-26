package com.example.placementapp.acitivties;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.placementapp.R;
import com.example.placementapp.utilites.DatePickerFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddCompanyActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Toolbar toolbar;
    EditText companyName, recruitmentDate,location,allowedDepartment;
    Button add;
    ProgressBar progressBar;
    DatabaseReference root;
    FirebaseDatabase database;
    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);
        database=FirebaseDatabase.getInstance();
        root=database.getReference().child("AddedCompany");
        toolbar=findViewById(R.id.add_toolbar);
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
        progressBar=findViewById(R.id.addCompanyProgressBar);
        add=findViewById(R.id.add);
        recruitmentDate.setShowSoftInputOnFocus(false);
        recruitmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (companyName.getText().toString().isEmpty()||recruitmentDate.getText().toString().isEmpty()||
                        location.getText().toString().isEmpty()||allowedDepartment.getText().toString().isEmpty()){
                    Toast.makeText(AddCompanyActivity.this, "Please enter a valid details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    uploadingCompanyToFireBase();
                }
            }
        });
    }

    public void uploadingCompanyToFireBase(){
        HashMap<String,String> userMap= new HashMap<>();
        userMap.put("companyName",(companyName.getText().toString().trim()));
        userMap.put("recruitmentDate",(recruitmentDate.getText().toString().trim()));
        userMap.put("location",(location.getText().toString().trim()));
        userMap.put("allowedDepartment",(allowedDepartment.getText().toString().trim()));
        userMap.put("docUrl",("null"));
        root.push().setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AddCompanyActivity.this, "Company added", Toast.LENGTH_SHORT).show();
                companyName.setText("");
                recruitmentDate.setText("");
                location.setText("");
                allowedDepartment.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddCompanyActivity.this, "Unable to update data", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        recruitmentDate.setText(currentDateString);
    }
}