package com.example.classchat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.classchat.Activity.Activity_MyCourse;
import com.example.classchat.Activity.Activity_SignSituation;
import com.example.classchat.Object.Course;
import com.example.classchat.Object.MySubject;
import com.example.classchat.R;

import java.util.List;

public class Adapter_Course extends RecyclerView.Adapter<Adapter_Course.ViewHolder> {
    private Context mcontext;
    private List<Course> mySubjectList;

    public Adapter_Course(List<Course> mySubjects, Context mcontext) {
        this.mcontext = mcontext;
        mySubjectList = mySubjects;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView courseName;
        TextView courseWeeks;
        TextView coursePlace;
        TextView courseTeacher;
        TextView courseSignTimes;
        RelativeLayout relativeLayout;

        public ViewHolder(View view) {
            super(view);
            courseName = view.findViewById(R.id.courseitem_coursename);
//            courseWeeks = view.findViewById(R.id.courseitem_courseweeks);
//            coursePlace = view.findViewById(R.id.courseitem_courseplace);
//            courseTeacher = view.findViewById(R.id.courseitem_courseteacher);
            courseSignTimes = view.findViewById(R.id.courseitem_coursesigntimes);
            relativeLayout = view.findViewById(R.id.identity);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.course_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Course myCourse = mySubjectList.get(position);
        viewHolder.courseName.setText("课程名：" + myCourse.getCourseName());
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, Activity_SignSituation.class);
                intent.putExtra("groupId", myCourse.getCourseId());
                intent.putExtra("proUni", myCourse.getProUni());
                intent.putExtra("shouldSignTime", myCourse.getShouldSignTime());
                mcontext.startActivity(intent);
            }
        });
//        viewHolder.coursePlace.setText(mySubject.getRoom());
//        viewHolder.courseTeacher.setText(mySubject.getTeacher());
//        viewHolder.courseSignTimes.setText("签到次数"+myCourse.getSignTime());
//        viewHolder.courseWeeks.setText(mySubject.getWeekList().size());
    }

    @Override
    public int getItemCount() {
        return mySubjectList.size();
    }
}
