package com.example.placementapp.utilites;


import android.content.Context;
import android.content.SharedPreferences;


import static android.content.Context.MODE_PRIVATE;

public class PreferenceManager {

    Context context;
    public PreferenceManager(Context context){
        this.context = context;
    }

    public void saveUserData(String userName,String userEmail,String userPhoneNumber, String userUSN, String userPassword){
        SharedPreferences sharedPreferences =context.getSharedPreferences("UserSharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEditer = sharedPreferences.edit();
        myEditer.putString("userName",userName);
        myEditer.putString("userEmail",userEmail);
        myEditer.putString("userUSN",userUSN);
        myEditer.putString("userPhoneNumber",userPhoneNumber);
        myEditer.putString("userPassword",userPassword);
        myEditer.commit();
    }
    public String getUserName(){
        SharedPreferences sharedPreferences =context.getSharedPreferences("UserSharedPref",MODE_PRIVATE);
        return sharedPreferences.getString("userName","");
    }
    public String getUserEmail(){
        SharedPreferences sharedPreferences =context.getSharedPreferences("UserSharedPref",MODE_PRIVATE);
        return sharedPreferences.getString("userEmail","");
    }
    public String getUserUSN(){
        SharedPreferences sharedPreferences =context.getSharedPreferences("UserSharedPref",MODE_PRIVATE);
        return sharedPreferences.getString("userUSN","");
    }
    public String getUserPhoneNumber(){
        SharedPreferences sharedPreferences =context.getSharedPreferences("UserSharedPref",MODE_PRIVATE);
        return sharedPreferences.getString("userPhoneNumber","");
    }
    public String getUserPassword(){
        SharedPreferences sharedPreferences =context.getSharedPreferences("UserSharedPref",MODE_PRIVATE);
        return sharedPreferences.getString("userPassword","");
    }

    public void saveProfUserData(String userName,String userEmail,String userPhoneNumber, String userPassword){
        SharedPreferences sharedPreferences =context.getSharedPreferences("ProfUserSharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEditer = sharedPreferences.edit();
        myEditer.putString("userName",userName);
        myEditer.putString("userEmail",userEmail);
        myEditer.putString("userPhoneNumber",userPhoneNumber);
        myEditer.putString("userPassword",userPassword);
        myEditer.commit();
    }
    public String getPrefUserName(){
        SharedPreferences sharedPreferences =context.getSharedPreferences("ProfUserSharedPref",MODE_PRIVATE);
        return sharedPreferences.getString("userName","");
    }
    public String getPrefUserEmail(){
        SharedPreferences sharedPreferences =context.getSharedPreferences("ProfUserSharedPref",MODE_PRIVATE);
        return sharedPreferences.getString("userEmail","");
    }
    public String getPrefUserPhoneNumber(){
        SharedPreferences sharedPreferences =context.getSharedPreferences("ProfUserSharedPref",MODE_PRIVATE);
        return sharedPreferences.getString("userPhoneNumber","");
    }
    public String getPrefUserPassword(){
        SharedPreferences sharedPreferences =context.getSharedPreferences("ProfUserSharedPref",MODE_PRIVATE);
        return sharedPreferences.getString("userPassword","");
    }
    public void saveUserRole(String userRole){
        SharedPreferences sharedPreferences =context.getSharedPreferences("UserRoleSharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEditer = sharedPreferences.edit();
        myEditer.putString("userRole",userRole);
        myEditer.commit();
    }
    public String getUserRole(){
        SharedPreferences sharedPreferences =context.getSharedPreferences("UserRoleSharedPref",MODE_PRIVATE);
        return sharedPreferences.getString("userRole","");
    }
}
