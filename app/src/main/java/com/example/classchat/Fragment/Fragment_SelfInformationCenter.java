package com.example.classchat.Fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v4.provider.DocumentFile;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.example.classchat.Activity.Activity_AboutUs;
import com.example.classchat.Activity.Activity_AccountInfo;
import com.example.classchat.Activity.Activity_HelpAndFeedback;
import com.example.classchat.Activity.Activity_IdAuthentation;
import com.example.classchat.Activity.Activity_MyCourse;
import com.example.classchat.Activity.Activity_Option;
import com.example.classchat.Activity.MainActivity;
import com.example.classchat.R;
import com.example.classchat.Util.Util_NetUtil;
import com.example.classchat.Util.Util_ToastUtils;
import com.sdsmdg.tastytoast.TastyToast;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Fragment_SelfInformationCenter extends Fragment {

    //控件
    private ImageView avatarImageView;

    private LinearLayout linearLayoutforAnquan;
    private LinearLayout linearLayoutforKecheng;
    private LinearLayout linearLayoutforShoucang;
    private LinearLayout linearLayoutforShezhi;
//    private LinearLayout linearLayoutforRenzheng;
    private LinearLayout linearLayoutforBangzhu;
    private LinearLayout linearLayoutforGuanyu;

    private TextView textViewforName;
    private TextView textViewforId;
//    private TextView textViewforMoney;

    private String correctId;
    private String name;
    private Double money;
    private String imageUrl;
    private Boolean isAuthentation;
    private String proUni;

    private final static int UPDATE = 100;

    //handler处理反应回来的信息
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE:
                    textViewforId.setText(correctId);
                    textViewforName.setText(name);
                    Glide.with(Fragment_SelfInformationCenter.this).load(imageUrl).into(avatarImageView);
                    break;
            }
        }
    };

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        avatarImageView = view.findViewById(R.id.user_head);

        linearLayoutforAnquan = view.findViewById(R.id.anquan);
        linearLayoutforBangzhu = view.findViewById(R.id.bangzhuyufankui);
        linearLayoutforGuanyu = view.findViewById(R.id.aboutus);
        linearLayoutforKecheng = view.findViewById(R.id.kecheng);
//        linearLayoutforRenzheng = view.findViewById(R.id.shimingrenzheng);
        linearLayoutforShezhi = view.findViewById(R.id.shezhi);
        linearLayoutforShoucang = view.findViewById(R.id.shoucang);

        textViewforId = view.findViewById(R.id.user_stuID);
//        textViewforMoney = view.findViewById(R.id.user_money);
        textViewforName = view.findViewById(R.id.user_name);

        //获得用户ID
        MainActivity activity = (MainActivity) getActivity();
        correctId = activity.getId();
        name = activity.getNickName();
        imageUrl = activity.getImageUrl();
        isAuthentation = activity.getAuthentation();
        proUni = activity.getProUni();

        //加载数据和图片
        textViewforId.setText(correctId);
        textViewforName.setText(name);
        Glide.with(this).load(imageUrl).into(avatarImageView);

        // 注册广播监听器
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        // 注册更新界面所需的广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.theclasschat_UPDATE_ACCOUNTINFO");
        UpdateAccountInfoReceiver updateAccountInfoReceiver = new UpdateAccountInfoReceiver();
        localBroadcastManager.registerReceiver(updateAccountInfoReceiver, intentFilter);


        //加入点击事件
        linearLayoutforAnquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_AccountInfo.class);
                intent.putExtra("userId", correctId);
                intent.putExtra("userName", name);
                intent.putExtra("headUrl", imageUrl);
                startActivity(intent);
            }
        });
        linearLayoutforBangzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_HelpAndFeedback.class);
                startActivity(intent);
            }
        });
        linearLayoutforGuanyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_AboutUs.class);
                startActivity(intent);
            }
        });
        linearLayoutforKecheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_MyCourse.class);
                intent.putExtra("userId", correctId);
                intent.putExtra("proUni", proUni);
                startActivity(intent);
            }
        });
//        linearLayoutforRenzheng.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), Activity_IdAuthentation.class);
//                intent.putExtra("userId", correctId);
//                if (!isAuthentation)
//                    startActivity(intent);
//                else
//                    Util_ToastUtils.showToast(getActivity(), "您已经实名认证过了");
//            }
//        });
        linearLayoutforShoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFile();
            }
        });
        linearLayoutforShezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_Option.class);
                startActivity(intent);
            }
        });


    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment__self_information_center, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /*
    接收到更新广播之后，界面如何处理
     */
    class UpdateAccountInfoReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            getUserInfo();
        }
    }

    // 取得用户信息方法
    private


    void getUserInfo() {
        RequestBody requestBody = new FormBody.Builder()
                .add("userId", correctId)
                .build();

        Util_NetUtil.sendOKHTTPRequest("http://106.12.105.160:8081/getuserinfo/teacher", requestBody, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                JSONObject jsonObject = JSON.parseObject(response.body().string());
                name = jsonObject.getString("nickname");
                imageUrl = jsonObject.getString("ico");
                isAuthentation = Boolean.parseBoolean(jsonObject.getString("authentationstatus"));
                proUni = jsonObject.getString("university") + "_" + jsonObject.getString("school");
                // 向handler发送信息更新你的课程
                Message message = new Message();
                message.what = UPDATE;
                handler.sendMessage(message);

            }
        });
    }

    private void openFile() {
        String path = Environment.getExternalStorageDirectory() + "/RongCloud/Media";
        File file= new File(path);
        Uri fileURI = FileProvider.getUriForFile(getActivity(), "com.example.classchat.FileProvider", file);
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setDataAndType(fileURI, "file/*");
        Uri originalUri = Uri.parse("content://com.android.externalstorage.documents/tree/primary%3ARongCloud%2FMedia");
        DocumentFile docFile = DocumentFile.fromTreeUri(getActivity(), originalUri);
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, docFile.getUri());
        startActivity(intent);
    }
}
