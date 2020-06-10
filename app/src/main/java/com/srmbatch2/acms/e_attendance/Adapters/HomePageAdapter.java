package com.srmbatch2.acms.e_attendance.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.srmbatch2.acms.e_attendance.ModelClasses.UserSubjects;
import com.srmbatch2.acms.e_attendance.R;

import java.util.ArrayList;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.HomePageViewHolder> {

    private ArrayList<UserSubjects> arrayList;
    private OnClickListener onClickListener;

    public HomePageAdapter(ArrayList<UserSubjects> arrayList, OnClickListener onClickListener) {
        this.arrayList = arrayList;
        this.onClickListener = onClickListener;
    }


    @NonNull
    @Override
    public HomePageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.home_cardview_row, parent,false);
        return new HomePageViewHolder(view,onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePageViewHolder holder, int position) {

        UserSubjects subject = arrayList.get(position);
        //Log.i("info", "Obj " + subject);
        //Log.i("info", "Sub " + subject.getSubjectUser());
        //Log.i("info", "Fac " + subject.getFacultyUser());
        //Log.i("info", "Atten " + subject.getAttendanceUser());

        holder.subjectHome.setText(subject.getSubjectUser());
        holder.facultyHome.setText(subject.getFacultyUser());
        holder.attendanceHome.setText(subject.getAttendanceUser() + "%");


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class HomePageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView subjectHome, facultyHome, attendanceHome;
        OnClickListener onClickListener;

        public HomePageViewHolder(@NonNull View itemView, OnClickListener onClickListener) {
            super(itemView);

            subjectHome = itemView.findViewById(R.id.subjectHomeCardView);
            facultyHome = itemView.findViewById(R.id.facultyHomeCardView);
            attendanceHome = itemView.findViewById(R.id.attendanceHomeCardView);
            this.onClickListener = onClickListener;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onClickListener.onItemClick(getAdapterPosition());

        }
    }

    public interface OnClickListener{
        void onItemClick(int position);
    }

}
