package com.example.placementapp.utilites;

import com.example.placementapp.dto.Company;
import com.example.placementapp.dto.Student;

import java.util.List;

public interface ServerCallback {
    public void onCompanyDataReceived(List<Company> data);
    public void onRegisterStudentDataReceived(List<Student> data);
    public void onPlacementDataReceived(List<Student> data);
}
