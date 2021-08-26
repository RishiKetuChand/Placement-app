package com.example.placementapp.acitivties;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadStudentDetailsActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView companyName, recruitmentDate,location,allowedDepartment;
    Button selectFile,upload;
    ProgressBar progressBar;
    FirebaseDatabase database;
    DatabaseReference root;
    Window window;
    FirebaseStorage storage;
    String companyNameString, recruitmentDateString,locationString,allowedDepartmentString,docUrlString,uidString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_student_details);
        companyNameString=getIntent().getStringExtra("name");
        recruitmentDateString=getIntent().getStringExtra("date");
        locationString=getIntent().getStringExtra("loc");
        allowedDepartmentString=getIntent().getStringExtra("dept");
        docUrlString=getIntent().getStringExtra("docUrl");
        uidString=getIntent().getStringExtra("uid");
        database=FirebaseDatabase.getInstance();
        root=database.getReference().child("AddedCompany");
        storage=FirebaseStorage.getInstance();
        initializeView();
        companyName.setText(companyNameString);
        recruitmentDate.setText(recruitmentDateString);
        location.setText(locationString);
        allowedDepartment.setText(allowedDepartmentString);
        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select a file"),99);
            }
        });

    }
    public void initializeView(){
        toolbar=findViewById(R.id.upload_toolbar);
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
        progressBar=findViewById(R.id.uploadProgressBar);
        progressBar.bringToFront();
        selectFile=findViewById(R.id.selectBtn);
        upload=findViewById(R.id.uploadBtn);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==99 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            upload.setVisibility(View.VISIBLE);
            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    uploadPdfFile(data.getData());
                }
            });
        }
    }

    private void uploadPdfFile(Uri data) {
        String time=System.currentTimeMillis()+"";
        StorageReference storageReference =storage.getReference();
        storageReference.child("PlacedStudents").child(time).putFile(data).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(UploadStudentDetailsActivity.this, "Failed to upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uri =uriTask.getResult();
                root.child(uidString).child("docUrl").setValue(uri.toString());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(UploadStudentDetailsActivity.this, "List Sucessfully Added", Toast.LENGTH_SHORT).show();
            }
        });
    }
}