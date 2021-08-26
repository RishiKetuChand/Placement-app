package com.example.placementapp.utilites;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.placementapp.R;

public class ConnectivityCheck extends BroadcastReceiver {
    Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {
        mContext=context;
        if (isConnected(context)){

        }else{
            showDialog();
        }
    }
    public  boolean isConnected(Context context){
        try {
            ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            return (networkInfo!=null && networkInfo.isConnected());
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void showDialog(){

        Dialog dialog= new Dialog(mContext);
        dialog.setContentView(R.layout.coonectivity_check);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        Button okButton= dialog.findViewById(R.id.ok);
        Button tryButton= dialog.findViewById(R.id.tryAgain);
        dialog.show();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "OK", Toast.LENGTH_SHORT).show();
                System.exit(0);

            }
        });
        tryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "TryAgain", Toast.LENGTH_SHORT).show();
                //need to include this feature
            }
        });

    }

}