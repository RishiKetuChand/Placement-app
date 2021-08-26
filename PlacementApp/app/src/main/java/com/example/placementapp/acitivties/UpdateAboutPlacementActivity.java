package com.example.placementapp.acitivties;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.placementapp.MainActivity;
import com.example.placementapp.R;
import com.example.placementapp.utilites.DatePickerFragment;
import com.example.placementapp.utilites.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import org.jetbrains.annotations.Nullable;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class UpdateAboutPlacementActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    EditText studentName,studentEmail,studentPhoneNumber,studentUsn,companyName, recruitmentDate,location;
    PowerSpinnerView powerSpinnerView;
    Toolbar toolbar;
    Button update;
    ProgressBar progressBar;
    FirebaseDatabase database;
    DatabaseReference root;
    Window window;
    String department;
    PreferenceManager preferenceManager = new PreferenceManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_about_placement);
        database=FirebaseDatabase.getInstance();
        root=database.getReference().child("PlacementDrive");
        initializeView();
        studentName.setText(preferenceManager.getUserName());
        studentEmail.setText(preferenceManager.getUserEmail());
        studentPhoneNumber.setText(preferenceManager.getUserPhoneNumber());
        studentUsn.setText(preferenceManager.getUserUSN());
        recruitmentDate.setShowSoftInputOnFocus(false);
        recruitmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        powerSpinnerView.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
            @Override
            public void onItemSelected(int i, @Nullable String s, int i1, String t1) {
                department =t1;
                update.setVisibility(View.VISIBLE);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (studentName.getText().toString().isEmpty()||studentEmail.getText().toString().isEmpty()||
                        studentPhoneNumber.getText().toString().isEmpty()||studentUsn.getText().toString().isEmpty() ||
                        companyName.getText().toString().isEmpty()||department.isEmpty()|| department.equals("All Branch")||
                        location.getText().toString().isEmpty()||recruitmentDate.getText().toString().isEmpty()){
                    Toast.makeText(UpdateAboutPlacementActivity.this, "Please enter a valid details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    uploadPlacementStudents();
                }
            }
        });
    }

    private void uploadPlacementStudents() {
        HashMap<String,String> userMap= new HashMap<>();
        userMap.put("companyName",(companyName.getText().toString()));
        userMap.put("recruitmentDate",(recruitmentDate.getText().toString()));
        userMap.put("location",(location.getText().toString()));
        userMap.put("Department",(department));
        userMap.put("studentName",(studentName.getText().toString()));
        userMap.put("studentEmail",(studentEmail.getText().toString()));
        userMap.put("studentPhoneNumber",(studentPhoneNumber.getText().toString()));
        userMap.put("studentUSN",(studentUsn.getText().toString()));
        root.push().setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(UpdateAboutPlacementActivity.this, "Update Sucessfull", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(UpdateAboutPlacementActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateAboutPlacementActivity.this, "Unable to register", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initializeView(){
        toolbar=findViewById(R.id.placement_toolbar);
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
        studentEmail=findViewById(R.id.emailEt);
        studentName=findViewById(R.id.studNameEt);
        studentPhoneNumber=findViewById(R.id.phoneNumberET);
        studentUsn=findViewById(R.id.usnEt);
        progressBar=findViewById(R.id.addCompanyProgressBar);
        progressBar.bringToFront();
        update=findViewById(R.id.updateBtn);
        powerSpinnerView=findViewById(R.id.brnachSpinnerView);
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