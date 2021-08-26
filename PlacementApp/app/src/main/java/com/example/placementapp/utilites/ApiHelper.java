package com.example.placementapp.utilites;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.placementapp.acitivties.PlacedStudentsActivity;
import com.example.placementapp.acitivties.RegisteredStudentsActivity;
import com.example.placementapp.acitivties.ViewCompanyActivity;
import com.example.placementapp.dto.Company;
import com.example.placementapp.dto.Student;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ApiHelper {
    Context context;
    public ApiHelper(Context context){
        this.context = context;
    }
    private static String VIEW_COMPANY ="https://placementapp-dev-default-rtdb.firebaseio.com/AddedCompany.json";
    private static String REGISTER_STUDENT ="https://placementapp-dev-default-rtdb.firebaseio.com/RegisteredStudent.json";
    private static String PLACEMENT_DRIVE ="https://placementapp-dev-default-rtdb.firebaseio.com/PlacementDrive.json";
    public void fetchCompany(ViewCompanyActivity viewCompanyActivity){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,VIEW_COMPANY,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                processCompany(response,viewCompanyActivity);
            }
        },null);
        VolleySingleton.getInstance(context).getRequestQueue().add(jsonObjectRequest);

    }

    private void processCompany(JSONObject response,ViewCompanyActivity viewCompanyActivity) {
        List<Company> companies = new ArrayList<>();
        if(null != response){
            Iterator<String> keys = response.keys();

            while(keys.hasNext()) {
                String key = keys.next();
                try {
                    if (response.get(key) instanceof JSONObject) {
                        // do something with jsonObject here
                        JSONObject temp = response.getJSONObject(key);
                        Company company = new Company();
                        company.setName(temp.getString("companyName"));
                        company.setDate(temp.getString("recruitmentDate"));
                        company.setLoc(temp.getString("location"));
                        company.setDepartment(temp.getString("allowedDepartment"));
                        company.setDocUrl(temp.getString("docUrl"));
                        company.setUid(key);
                        companies.add(company);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        viewCompanyActivity.onCompanyDataReceived(companies);
    }
    public void fetchStudent(RegisteredStudentsActivity registeredStudentsActivity){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,REGISTER_STUDENT,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                processStudent(response,registeredStudentsActivity);
            }
        },null);
        VolleySingleton.getInstance(context).getRequestQueue().add(jsonObjectRequest);

    }

    private void processStudent(JSONObject response,RegisteredStudentsActivity registeredStudentsActivity) {
        List<Student> students = new ArrayList<>();
        if(null != response){
            Iterator<String> keys = response.keys();

            while(keys.hasNext()) {
                String key = keys.next();
                try {
                    if (response.get(key) instanceof JSONObject) {
                        // do something with jsonObject here
                        JSONObject temp = response.getJSONObject(key);
                        Student student = new Student();
                        student.setCompanyName(temp.getString("companyName"));
                        student.setCompanyLoc(temp.getString("location"));
                        student.setCompanyDate(temp.getString("recruitmentDate"));
                        student.setAllowedDepartment(temp.getString("allowedDepartment"));
                        student.setStudentName(temp.getString("studentName"));
                        student.setStudentEmail(temp.getString("studentEmail"));
                        student.setStudentPhoneNumber(temp.getString("studentPhoneNumber"));
                        student.setStudentUsn(temp.getString("studentUSN"));
                        students.add(student);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        registeredStudentsActivity.onRegisterStudentDataReceived(students);
    }
    public void fetchPlacement(PlacedStudentsActivity placedStudentsActivity){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,PLACEMENT_DRIVE,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                processPlacement(response,placedStudentsActivity);
            }
        },null);
        VolleySingleton.getInstance(context).getRequestQueue().add(jsonObjectRequest);

    }

    private void processPlacement(JSONObject response,PlacedStudentsActivity placedStudentsActivity) {
        List<Student> students = new ArrayList<>();
        if(null != response){
            Iterator<String> keys = response.keys();

            while(keys.hasNext()) {
                String key = keys.next();
                try {
                    if (response.get(key) instanceof JSONObject) {
                        // do something with jsonObject here
                        JSONObject temp = response.getJSONObject(key);
                        Student student = new Student();
                        student.setCompanyName(temp.getString("companyName"));
                        student.setCompanyLoc(temp.getString("location"));
                        student.setCompanyDate(temp.getString("recruitmentDate"));
                        student.setAllowedDepartment(temp.getString("Department"));
                        student.setStudentName(temp.getString("studentName"));
                        student.setStudentEmail(temp.getString("studentEmail"));
                        student.setStudentPhoneNumber(temp.getString("studentPhoneNumber"));
                        student.setStudentUsn(temp.getString("studentUSN"));
                        students.add(student);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        placedStudentsActivity.onPlacementDataReceived(students);
    }
}
