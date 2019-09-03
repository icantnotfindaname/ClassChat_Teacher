package com.example.classchat.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.classchat.Adapter.Adapter_Course;
import com.example.classchat.Fragment.Fragment_ClassBox;
import com.example.classchat.Object.Course;
import com.example.classchat.Object.MySubject;
import com.example.classchat.R;
import com.example.classchat.Util.Util_NetUtil;
import com.example.library_cache.Cache;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 这是我的课程界面
 */
public class Activity_MyCourse extends AppCompatActivity {

    private List<Course> courseList;
    private List<MySubject> mySubjects;

    private RecyclerView recyclerView;
    private ImageView back;

    private JSONObject classes = new JSONObject();
    private String mClassBoxData;

    private String proUni;

    private static final String TAG = "Activity_MyCourse";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__my_wallet);

        Intent intent = getIntent();
        proUni = intent.getStringExtra("proUni");

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            //After LOLLIPOP not translucent status bar
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //Then call setStatusBarColor.
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.theme));
        }

        courseList = new ArrayList<>();
        mySubjects = new ArrayList<>();

        initData();

        recyclerView = findViewById(R.id.rl_course);
        back = findViewById(R.id.iv_mywallet_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Adapter_Course adapter_course = new Adapter_Course(courseList, Activity_MyCourse.this);
        recyclerView.setAdapter(adapter_course);
    }

    private void initData() {
        courseList.clear();

        mClassBoxData = Cache.with(Activity_MyCourse.this)
                .path(getCacheDir(Activity_MyCourse.this))
                .getCache("classBox", String.class);

        // 转化为具体的对象列表
        List<String> jsonlist = JSON.parseArray(mClassBoxData, String.class);

        for(String s : jsonlist) {
            MySubject mySubject = JSON.parseObject(s, MySubject.class);
            mySubjects.add(mySubject);
            classes.put(mySubject.getId(), mySubject.getName());
        }


        for (String key : classes.keySet()) {
            int time = 0;
            for (MySubject subject : mySubjects) {
                if (subject.getId() == key){
                    time = time + subject.getWeekList().size();
                }
            }
            courseList.add(new Course(classes.getString(key), key, proUni, String.valueOf(time)));
        }
    }

    /*
     * 获得缓存地址
     * */
    public String getCacheDir(Context context) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }
}
