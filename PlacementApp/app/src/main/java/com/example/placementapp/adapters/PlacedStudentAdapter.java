package com.example.placementapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.placementapp.R;
import com.example.placementapp.dto.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlacedStudentAdapter extends RecyclerView.Adapter<PlacedStudentAdapter.PlaceAdapterViewHolder>{
    Context context;
    List<Student> data;
    List<Student> dataAll;

    public PlacedStudentAdapter(Context context, List<Student> data) {
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
    public PlaceAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.placement_recycler_item_view,parent,false);
        return new PlaceAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAdapterViewHolder holder, int position) {
        holder.companyName.setText(data.get(position).getCompanyName());
        holder.companyDate.setText(data.get(position).getCompanyDate());
        holder.companyLoc.setText(data.get(position).getCompanyLoc());
        holder.department.setText(data.get(position).getAllowedDepartment());
        holder.studentName.setText(data.get(position).getStudentName());
        holder.studentEmail.setText(data.get(position).getStudentEmail());
        holder.studentUsn.setText(data.get(position).getStudentUsn());
        holder.studentPhone.setText(data.get(position).getStudentPhoneNumber());
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
                        } else if(searchedSubject.getAllowedDepartment().toLowerCase().contains(charSequence.toString().toLowerCase())){
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

    public class PlaceAdapterViewHolder  extends RecyclerView.ViewHolder {
        TextView companyName,companyLoc,companyDate, studentName, studentEmail, studentPhone, studentUsn, department;
        View view;
        public PlaceAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            companyName=(TextView) itemView.findViewById(R.id.companyName);
            companyDate=(TextView) itemView.findViewById(R.id.reqrementdate);
            companyLoc=(TextView) itemView.findViewById(R.id.companyLocation);
            studentName=(TextView) itemView.findViewById(R.id.studentName);
            studentEmail=(TextView) itemView.findViewById(R.id.studentEmail);
            studentPhone=(TextView) itemView.findViewById(R.id.studentPhone);
            studentUsn=(TextView) itemView.findViewById(R.id.studentUsn);
            department=(TextView) itemView.findViewById(R.id.studentDepart);
            view = itemView;
        }
    }
}
