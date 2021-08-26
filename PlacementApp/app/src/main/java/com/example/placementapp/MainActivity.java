package com.example.placementapp;

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

import com.example.placementapp.acitivties.AddCompanyActivity;
import com.example.placementapp.acitivties.PlacedStudentsActivity;
import com.example.placementapp.acitivties.ProfHomeActivity;
import com.example.placementapp.acitivties.RegisteredStudentsActivity;
import com.example.placementapp.acitivties.UpdateAboutPlacementActivity;
import com.example.placementapp.acitivties.ViewCompanyActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    CardView  allCompany,placementDrive;
    Window window;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        allCompany =findViewById(R.id.availableCompanyCard);
        placementDrive =findViewById(R.id.placementCard);
        toolbar=findViewById(R.id.home_toolbar);
        toolbar.setTitle("Hello, Buddy");
        setSupportActionBar(toolbar);
        window=this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.white));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        allCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewCompanyActivity.class);
                intent.putExtra("key","stud");
                startActivity(intent);
            }
        });
        placementDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,UpdateAboutPlacementActivity.class);
                startActivity(intent);
                finish();
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
            default:return super.onOptionsItemSelected(item);
        }
    }
}