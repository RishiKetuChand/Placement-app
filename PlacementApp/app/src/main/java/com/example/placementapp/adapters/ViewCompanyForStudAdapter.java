package com.example.placementapp.adapters;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.placementapp.R;
import com.example.placementapp.acitivties.RegisteredStudentsActivity;
import com.example.placementapp.acitivties.StdentRegisterActivity;
import com.example.placementapp.acitivties.UploadStudentDetailsActivity;
import com.example.placementapp.constants.AppConstants;
import com.example.placementapp.dto.Company;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ViewCompanyForStudAdapter extends RecyclerView.Adapter<ViewCompanyForStudAdapter.AdapterForStudViewHolder> {
    Context context;
    List<Company> data;
    List<Company> dataAll;

    public ViewCompanyForStudAdapter(Context context, List<Company> data) {
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
    public AdapterForStudViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.view_company_for_students_item_view,parent,false);
        return new AdapterForStudViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForStudViewHolder holder, int position) {
        holder.companyName.setText(data.get(position).getName());
        holder.companyDate.setText(data.get(position).getDate());
        holder.companyLoc.setText(data.get(position).getLoc());
        holder.allowedDepart.setText(data.get(position).getDepartment());
        holder.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StdentRegisterActivity.class);
                intent.putExtra("name",data.get(position).getName());
                intent.putExtra("date",data.get(position).getDate());
                intent.putExtra("loc",data.get(position).getLoc());
                intent.putExtra("dept",data.get(position).getDepartment());
                intent.putExtra("docUrl",data.get(position).getDocUrl());
                intent.putExtra("uid",data.get(position).getUid());
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
        if (data.get(position).getDocUrl().equals("null")){
            holder.downloadBtn.setAlpha(0.5f);
            holder.downloadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "No data uploaded yet", Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            holder.downloadBtn.setAlpha(1f);
            holder.downloadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File resourceLocation = new File(context.getExternalFilesDir(AppConstants.APP_ROOT_FOLDER),AppConstants.DOC_FOLDER_NAME);
                    if (!resourceLocation.exists()){
                        resourceLocation.mkdir();
                    }
                    DownloadManager.Request request = new DownloadManager.Request( Uri.parse(data.get(position).getDocUrl()));
                    request.setTitle(data.get(position).getName()+" Placed Students");
                    request.setDescription("File Downloading.....");
                    request.setAllowedOverRoaming(true);
                    //request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.allowScanningByMediaScanner();
                    File resource = new File(resourceLocation,data.get(position).getName()+" Placed Students"+".pdf");
                    Uri destination = Uri.fromFile(resource);
                    request.setDestinationUri(destination);
                    request.setMimeType(getMimeType(data.get(position).getDocUrl()));

                    DownloadManager manager = (DownloadManager) v.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                    manager.enqueue(request);
                }
            });

        }
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
    public class AdapterForStudViewHolder extends RecyclerView.ViewHolder {
        TextView companyName,companyDate, companyLoc, allowedDepart;
        Button registerBtn,downloadBtn;
        View view;
        public AdapterForStudViewHolder(@NonNull View itemView) {
            super(itemView);
            companyName=(TextView) itemView.findViewById(R.id.companyName);
            companyDate=(TextView) itemView.findViewById(R.id.date);
            companyLoc=(TextView) itemView.findViewById(R.id.loaction);
            allowedDepart=(TextView) itemView.findViewById(R.id.allowedDepartment);
            registerBtn=(Button) itemView.findViewById(R.id.registerBtn);
            downloadBtn=(Button) itemView.findViewById(R.id.downloadBtn);
            view = itemView;
        }
    }
    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
        }
        return type;
    }
}
