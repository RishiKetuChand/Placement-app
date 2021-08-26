package com.example.placementapp.acitivties;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.airbnb.lottie.LottieAnimationView;
import com.example.placementapp.R;
import com.example.placementapp.adapters.PlacedStudentAdapter;
import com.example.placementapp.adapters.RegisterStudentAdapter;
import com.example.placementapp.dto.Company;
import com.example.placementapp.dto.Student;
import com.example.placementapp.utilites.ApiHelper;
import com.example.placementapp.utilites.ServerCallback;

import java.util.ArrayList;
import java.util.List;

public class PlacedStudentsActivity extends AppCompatActivity implements ServerCallback {
    Toolbar toolbar;
    RecyclerView recyclerView;
    LottieAnimationView lottieAnimationView;
    PlacedStudentAdapter adapter;
    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_students);
        lottieAnimationView=findViewById(R.id.loading);
        toolbar=findViewById(R.id.reg_stud_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        window=this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.light_blue_login));
        }
        fetchPlacement();
        recyclerView=findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        adapter= new PlacedStudentAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCompanyDataReceived(List<Company> data) {

    }

    @Override
    public void onRegisterStudentDataReceived(List<Student> data) {

    }

    @Override
    public void onPlacementDataReceived(List<Student> data) {
        lottieAnimationView.setVisibility(View.GONE);
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_company_menu,menu);
        return true;
    }
    private void fetchPlacement() {
        ApiHelper helper = new ApiHelper(this);
        helper.fetchPlacement(this);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapter.getFilter().filter(newText.toLowerCase());
                        return false;
                    }
                });
                return true;
            default:return super.onOptionsItemSelected(item);
        }

    }
}