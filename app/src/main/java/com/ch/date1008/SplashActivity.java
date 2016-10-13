package com.ch.date1008;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * 项目名称：Date1008
 * 类描述：
 * 创建人：陈志平
 * 创建时间：2016/10/9  15:45
 */
public class SplashActivity extends AppCompatActivity implements CountDownView.CountDownViewListener, View.OnClickListener {

    //  private TextView btn_time;
    private CountDownView countDownView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);
        //  btn_time = ((TextView) findViewById(R.id.btn_time));
        countDownView = ((CountDownView) findViewById(R.id.count_down_view));
        //countDownView.setAlpha(0.5f);
        countDownView.start(10000);
        countDownView.setCountDownViewListener(this);
        countDownView.setOnClickListener(this);


        // 判断是否是第一次开启应用
        boolean isFirstOpen = Utils.getBoolean(this, AppCons.FIRSTOPEN);
        // 如果是第一次启动，则先进入功能引导页
        if (!isFirstOpen) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
            return;
        }

//        final CountDownTimer downTimer = new CountDownTimer(3000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                btn_time.setText("跳过广告");
//            }
//
//            @Override
//            public void onFinish() {
//               // btn_time.setText("倒计时"+0+"s");
//                Intent i=new Intent(SplashActivity.this,MainActivity.class);
//                startActivity(i);
//                finish();
//            }
//        };
//        downTimer.start();

//        btn_time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(SplashActivity.this,MainActivity.class);
//                startActivity(i);
//                finish();
//                downTimer.cancel();
////                if (runnable != null) {
////                    handler.removeCallbacks(runnable);
////                }
//            }
//        });


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

    @Override
    public void onStartCount() {

    }

    @Override
    public void onFinshCount() {
        Intent i = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(i);
        finish();

    }

    @Override
    public void onClick(View v) {
        countDownView.stop();
        Intent i = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
