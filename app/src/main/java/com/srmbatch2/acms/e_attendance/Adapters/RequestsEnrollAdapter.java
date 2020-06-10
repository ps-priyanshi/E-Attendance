package com.srmbatch2.acms.e_attendance.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.srmbatch2.acms.e_attendance.ModelClasses.RequestStatus;
import com.srmbatch2.acms.e_attendance.R;

import java.util.ArrayList;

import static android.graphics.Color.rgb;

public class RequestsEnrollAdapter extends RecyclerView.Adapter<RequestsEnrollAdapter.RequestsEnrollViewHolder>{

    public ArrayList<RequestStatus> arrayList;

    public RequestsEnrollAdapter(ArrayList<RequestStatus> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RequestsEnrollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.requestsenroll_cardview_row, parent,false);
        return new RequestsEnrollViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestsEnrollViewHolder holder, int position) {

        RequestStatus subject = arrayList.get(position);
        Log.i("info", "Obj " + subject);
        Log.i("info", "Fac " + subject.getStudentFaculty());
        Log.i("info", "Reg No " + subject.getStudentRegNo());
        Log.i("info", "Status " + subject.getStatus());

        holder.subjectRequestsEnroll.setText(subject.getStudentSubject());
        holder.facultyRequestsEnroll.setText(subject.getStudentFaculty());
        if (subject.getStatus().equals("0")) {
            holder.status.setTextColor(rgb(255,0,0));
            holder.status.setText("Pending..");
        } else if (subject.getStatus().equals("1")) {
            holder.status.setTextColor(rgb(34,139,34));
            holder.status.setText("Approved!");
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RequestsEnrollViewHolder extends RecyclerView.ViewHolder{

        public TextView subjectRequestsEnroll, facultyRequestsEnroll, status;

        public RequestsEnrollViewHolder(@NonNull View itemView) {
            super(itemView);

            subjectRequestsEnroll = itemView.findViewById(R.id.subjectRequestsEnrollCardView);
            facultyRequestsEnroll = itemView.findViewById(R.id.facultyRequestsEnrollCardView);
            status = itemView.findViewById(R.id.statusRequestsEnrollCardView);
        }
    }
}