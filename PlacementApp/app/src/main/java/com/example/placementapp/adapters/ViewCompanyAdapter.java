package com.example.placementapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.placementapp.R;
import com.example.placementapp.acitivties.UploadStudentDetailsActivity;
import com.example.placementapp.dto.Company;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ViewCompanyAdapter extends RecyclerView.Adapter<ViewCompanyAdapter.AdapterViewHolder> {
    Context context;
    List<Company> data;
    List<Company> dataAll;

    public ViewCompanyAdapter(Context context, List<Company> data) {
        this.context = context;
        this.data = data;
        this.dataAll= new ArrayList<>();
        this.dataAll.addAll(data);
    }
    public List<Company> getData() {
        return data;
    }

    public void setData(List<Company> data) {
        this.data.clear();
        this.data.addAll(data);
        this.dataAll.clear();
        this.dataAll.addAll(data) ;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.view_company_item_view,parent,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        holder.companyName.setText(data.get(position).getName());
        holder.companyDate.setText(data.get(position).getDate());
        holder.companyLoc.setText(data.get(position).getLoc());
        holder.allowedDepart.setText(data.get(position).getDepartment());
        holder.uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UploadStudentDetailsActivity.class);
                intent.putExtra("name",data.get(position).getName());
                intent.putExtra("date",data.get(position).getDate());
                intent.putExtra("loc",data.get(position).getLoc());
                intent.putExtra("dept",data.get(position).getDepartment());
                intent.putExtra("docUrl",data.get(position).getDocUrl());
                intent.putExtra("uid",data.get(position).getUid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Company> filteredList=new ArrayList<>();
                if(charSequence.toString().isEmpty()){
                    filteredList.addAll(dataAll);
                } else {
                    for (Company searchedSubject: dataAll){
                        if(searchedSubject.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                            filteredList.add(searchedSubject);
                        } else if(searchedSubject.getDepartment().toLowerCase().contains(charSequence.toString().toLowerCase())){
                            filteredList.add(searchedSubject);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values=filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                data.clear();
                data.addAll((Collection<? extends Company>) results.values);
                notifyDataSetChanged();

            }
        };
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView companyName,companyDate, companyLoc, allowedDepart;
        Button uploadBtn;
        View view;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            companyName=(TextView) itemView.findViewById(R.id.companyName);
            companyDate=(TextView) itemView.findViewById(R.id.date);
            companyLoc=(TextView) itemView.findViewById(R.id.loaction);
            allowedDepart=(TextView) itemView.findViewById(R.id.allowedDepartment);
            uploadBtn=(Button) itemView.findViewById(R.id.uploadBtn);
            view = itemView;
        }
    }
}
