package com.example.placementapp.acitivties;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.example.placementapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfHomeActivity extends AppCompatActivity {
    Toolbar toolbar;
    CardView addCompany, viewCompany,registeredStudents, placedStudents;
    Window window;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_home);
        mAuth= FirebaseAuth.getInstance();
        addCompany =findViewById(R.id.addCompanyCard);
        viewCompany =findViewById(R.id.viewCompanyCard);
        registeredStudents =findViewById(R.id.registeredStudentsCard);
        placedStudents =findViewById(R.id.placedStudentCard);
        toolbar=findViewById(R.id.home_toolbar);
        toolbar.setTitle("Hello, Sir");
        setSupportActionBar(toolbar);
        window=this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.white));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        addCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfHomeActivity.this,AddCompanyActivity.class);
                startActivity(intent);
            }
        });
        viewCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfHomeActivity.this,ViewCompanyActivity.class);
                intent.putExtra("key","prof");
                startActivity(intent);
            }
        });
        registeredStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfHomeActivity.this,RegisteredStudentsActivity.class);
                startActivity(intent);
            }
        });
        placedStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfHomeActivity.this,PlacedStudentsActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logOut:
                mAuth.signOut();
                this.finish();
                return true;
            default:return super.onOptionsItemSelected(item);
        }

    }
}