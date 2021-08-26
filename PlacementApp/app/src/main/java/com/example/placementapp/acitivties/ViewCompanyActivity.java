package com.example.placementapp.acitivties;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.placementapp.R;
import com.example.placementapp.adapters.ViewCompanyAdapter;
import com.example.placementapp.adapters.ViewCompanyForStudAdapter;
import com.example.placementapp.dto.Company;
import com.example.placementapp.dto.Student;
import com.example.placementapp.utilites.ApiHelper;
import com.example.placementapp.utilites.ServerCallback;

import java.util.ArrayList;
import java.util.List;

public class ViewCompanyActivity extends AppCompatActivity implements ServerCallback{
    Toolbar toolbar;
    RecyclerView recyclerView;
    ViewCompanyAdapter adapter;
    ViewCompanyForStudAdapter adapterForStud;
    LottieAnimationView lottieAnimationView;
    Window window;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_company);
        role=getIntent().getStringExtra("key");
        lottieAnimationView=findViewById(R.id.loading);
        toolbar=findViewById(R.id.view_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        window=this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.light_blue_login));
        }
        fetchCompany();
        recyclerView=findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        adapter = new ViewCompanyAdapter(this, new ArrayList<>());
        adapterForStud = new ViewCompanyForStudAdapter(this, new ArrayList<>());
        if (role.equals("prof")){
            recyclerView.setAdapter(adapter);
        }else{
            recyclerView.setAdapter(adapterForStud);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_company_menu,menu);
        return true;
    }
    private void fetchCompany() {
        ApiHelper helper = new ApiHelper(this);
        helper.fetchCompany(this);
    }
    @Override
    public  void onCompanyDataReceived(List<Company> data) {
        lottieAnimationView.setVisibility(View.GONE);
        adapter.setData(data);
        adapterForStud.setData(data);
        adapter.notifyDataSetChanged();
        adapterForStud.notifyDataSetChanged();
    }

    @Override
    public void onRegisterStudentDataReceived(List<Student> data) {

    }

    @Override
    public void onPlacementDataReceived(List<Student> data) {

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
                        adapter.getFilter().filter(newText);
                        adapterForStud.getFilter().filter(newText);
                        return false;
                    }
                });
                return true;
            default:return super.onOptionsItemSelected(item);
        }

    }
}
//