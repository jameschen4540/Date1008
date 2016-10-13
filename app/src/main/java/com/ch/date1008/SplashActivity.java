package com.ch.date1008;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * 项目名称：Date1008
 * 类描述：
 * 创建人：陈志平
 * 创建时间：2016/10/9  15:45
 */
public class SplashActivity extends AppCompatActivity {

    private TextView btn_time;
    private Handler handler=new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);
        btn_time = ((TextView) findViewById(R.id.btn_time));


        // 判断是否是第一次开启应用
        boolean isFirstOpen = Utils.getBoolean(this, AppCons.FIRSTOPEN);
        // 如果是第一次启动，则先进入功能引导页
        if (!isFirstOpen) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        final CountDownTimer downTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btn_time.setText("跳过广告");
            }

            @Override
            public void onFinish() {
               // btn_time.setText("倒计时"+0+"s");
                Intent i=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        };
        downTimer.start();

        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(i);
                finish();
                downTimer.cancel();
//                if (runnable != null) {
//                    handler.removeCallbacks(runnable);
//                }
            }
        });

//
//        handler.postDelayed(runnable=new Runnable() {
//            @Override
//            public void run() {
//                Intent i=new Intent(SpashActivity.this,MainActivity.class);
//                startActivity(i);
//                finish();
//            }
//        },3000);

    }
}