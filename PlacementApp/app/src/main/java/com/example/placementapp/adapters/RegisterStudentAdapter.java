package com.example.placementapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.placementapp.R;
import com.example.placementapp.acitivties.StudentDetailsActivity;
import com.example.placementapp.dto.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RegisterStudentAdapter extends RecyclerView.Adapter<RegisterStudentAdapter.RegisterAdapterViewHolder> {
    Context context;
    List<Student> data;
    List<Student> dataAll;

    public RegisterStudentAdapter(Context context, List<Student> data) {
        this.context = context;
        this.data = data;
        this.dataAll= new ArrayList<>();
        this.dataAll.addAll(data);
    }
    public List<Student> getData() {
        return data;
    }

    public void setData(List<Student> data) {
        this.data.clear();
        this.data.addAll(data);
        this.dataAll.clear();
        this.dataAll.addAll(data) ;
    }

    @NonNull
    @Override
    public RegisterAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.register_student_item_view,parent,false);
        return new RegisterAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegisterAdapterViewHolder holder, int position) {
        holder.companyName.setText(data.get(position).getCompanyName());
        holder.studentName.setText(data.get(position).getStudentName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StudentDetailsActivity.class);
                intent.putExtra("companyName",data.get(position).getCompanyName());
                intent.putExtra("location",data.get(position).getCompanyLoc());
                intent.putExtra("recruitmentDate",data.get(position).getCompanyDate());
                intent.putExtra("allowedDepartment",data.get(position).getAllowedDepartment());
                intent.putExtra("studentName",data.get(position).getStudentName());
                intent.putExtra("studentEmail",data.get(position).getStudentEmail());
                intent.putExtra("studentPhoneNumber",data.get(position).getStudentPhoneNumber());
                intent.putExtra("studentUSN",data.get(position).getStudentUsn());
                context.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return data.size();
    }
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Student> filteredList=new ArrayList<>();
                if(charSequence.toString().isEmpty()){
                    filteredList.addAll(dataAll);
                } else {
                    for (Student searchedSubject: dataAll){
                        if(searchedSubject.getCompanyName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                            filteredList.add(searchedSubject);
                        } else if(searchedSubject.getStudentName().toLowerCase().contains(charSequence.toString().toLowerCase())){
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
                data.addAll((Collection<? extends Student>) results.values);
                notifyDataSetChanged();

            }
        };
    }

    public class RegisterAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView companyName,studentName;
        View view;
        public RegisterAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            companyName=(TextView) itemView.findViewById(R.id.companyName);
            studentName=(TextView) itemView.findViewById(R.id.studentName);
            view = itemView;
        }
    }
}
