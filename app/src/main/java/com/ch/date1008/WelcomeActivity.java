package com.ch.date1008;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：Date1008
 * 类描述：
 * 创建人：陈志平
 * 创建时间：2016/10/9  16:51
 */
public class WelcomeActivity extends AppCompatActivity {


    private List<ImageView> list = new ArrayList<>();
    private ViewPager viewPager;
    private int[] imageIds = new int[]{R.mipmap.pic_ad_1, R.mipmap.pic_ad_2, R.mipmap.pic_ad_3, R.mipmap.pic_ad_4};
    private GestureDetector gestureDetector; // 用户滑动
    /**
     * 记录当前分页ID
     */
    private int flaggingWidth;// 互动翻页所需滚动的长度是当前屏幕宽度的1/3
    private int currentItem = 0; // 当前图片的索引号


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        viewPager = ((ViewPager) findViewById(R.id.viewpager));
        gestureDetector = new GestureDetector(new GuideViewTouch());

        // 获取分辨率
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        flaggingWidth = dm.widthPixels / 3;

        for (int i = 0; i < imageIds.length; i++) {

            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imageIds[i]);
//            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
//            layoutParams.height=getResources().getDisplayMetrics().heightPixels;
//            layoutParams.width=getResources().getDisplayMetrics().widthPixels;
//            imageView.setLayoutParams(layoutParams);
            list.add(imageView);


        }

        viewPager.setAdapter(new ViewPagerAdapter(list));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//                if (position == (list.size()-1)) {
//                    Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
//                    startActivity(intent);
//                    Utils.putBoolean(WelcomeActivity.this, AppCons.FIRSTOPEN, true);
//                    finish();
//                }

            }

            @Override
            public void onPageSelected(int position) {
                currentItem = position;


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(event);
        // return false;
    }

    private class GuideViewTouch extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            if (currentItem == list.size() - 1) {
                if (Math.abs(e1.getX() - e2.getX()) > Math.abs(e1.getY() - e2.getY())
                        && (e1.getX() - e2.getX() <= (-flaggingWidth) || e1.getX() - e2.getX() >= flaggingWidth)) {
                    if (e1.getX() - e2.getX() >= flaggingWidth) {
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        Utils.putBoolean(WelcomeActivity.this, AppCons.FIRSTOPEN, true);
                        finish();
                        return true;
                    }
                }
            }
            return false;
        }
    }


}
