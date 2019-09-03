package com.example.classchat.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.classchat.Adapter.Adapter_Course;
import com.example.classchat.Adapter.Adapter_SignSituation;
import com.example.classchat.Object.SignObject;
import com.example.classchat.R;
import com.example.classchat.Util.Util_NetUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Activity_SignSituation extends AppCompatActivity {

    private static final String TAG = "Activity_SignSituation";

    private String groupId;
    private String school;
    private String shouldSignTime;
    private List<SignObject> signList;

    private RecyclerView recyclerView;
    private ImageView back;

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Adapter_SignSituation adapter_course = new Adapter_SignSituation(signList, Activity_SignSituation.this);
                    recyclerView.setAdapter(adapter_course);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__sign_situation);
        Intent intent = getIntent();
        groupId = intent.getStringExtra("groupId");
        shouldSignTime = intent.getStringExtra("shouldSignTime");
        school = intent.getStringExtra("proUni").split("_")[0];
        signList = new ArrayList<>();


        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            //After LOLLIPOP not translucent status bar
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //Then call setStatusBarColor.
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.theme));
        }

        recyclerView = findViewById(R.id.rl_signsituation);
        back = findViewById(R.id.iv_sign_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_SignSituation.this);
        recyclerView.setLayoutManager(layoutManager);

        initData();
    }

    private void initData() {
        Log.d(TAG, "打印当前课程的groupId：" + groupId);
        Log.d(TAG, "打印当前老师所属的学校：" + school);
        
        
        RequestBody request = new FormBody.Builder()
                .add("groupId", groupId)
                .add("university", school)
                .build();

        Util_NetUtil.sendOKHTTPRequest("http://106.12.105.160:8081/teacher", request, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();

                Log.d(TAG, "打印收到返回这门课的学生信息" + responseData);

                List<String> jsonlist = JSON.parseArray(responseData, String.class);

                for (String object : jsonlist) {
                    JSONObject jsonObject = JSON.parseObject(object);
                    signList.add(new SignObject(jsonObject.getString("name"), jsonObject.getString("studentId"), jsonObject.getString("times"), shouldSignTime));
                }

                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        });
    }
}
