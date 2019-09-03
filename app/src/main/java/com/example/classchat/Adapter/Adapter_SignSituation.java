package com.example.classchat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.classchat.Activity.Activity_MyCourse;
import com.example.classchat.Object.Course;
import com.example.classchat.Object.SignObject;
import com.example.classchat.R;

import java.util.List;

public class Adapter_SignSituation extends RecyclerView.Adapter<Adapter_SignSituation.ViewHolder>{
    private List<SignObject> signList;
    private Context mContext;

    public Adapter_SignSituation(List<SignObject> mySubjects, Context context) {
        mContext = context;
        signList = mySubjects;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentName;
        TextView studentId;
        TextView signTime;

        public ViewHolder(View view) {
            super(view);
            studentName = view.findViewById(R.id.signitem_studentname);
            studentId = view.findViewById(R.id.signitem_studentid);
            signTime = view.findViewById(R.id.signitem_signtime);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.sign_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Adapter_SignSituation.ViewHolder viewHolder, int position) {
        final SignObject signObject = signList.get(position);
        viewHolder.studentName.setText("学生名：" + signObject.getStudentName());
        viewHolder.studentId.setText("学生ID：" + signObject.getStudentId());
        viewHolder.signTime.setText("实签：" + signObject.getSignTime() + " / " + "应签：" + signObject.getShouldSignTime());
    }

    @Override
    public int getItemCount() {
        return signList.size();
    }


}
