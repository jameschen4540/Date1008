package com.ch.date1008;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * 项目名称：Date1008
 * 类描述：自定义圆形水波纹
 * 创建人：陈志平
 * 创建时间：2016/10/8  16:57
 */
public class WaveLoadingView extends View {
    //绘制波纹
    private Paint mWavePaint;
    //绘制圆
    private Paint mCyclePaint;
    private PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private int mWidth;
    private int mHeight;
    private int x, y;
    private Path mPath;
    private Paint mTextPaint;
    private boolean isLeft;
    private int mPercent;


    public WaveLoadingView(Context context) {
        this(context, null);
    }

    public WaveLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mWavePaint = new Paint();
        mWavePaint.setColor(Color.parseColor("#33b5e5"));
        mCyclePaint = new Paint();
        mCyclePaint.setColor(Color.parseColor("#99cc00"));
        mBitmap = Bitmap.createBitmap(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels, Bitmap.Config.ARGB_8888);

        mCanvas = new Canvas(mBitmap);
        mPath = new Path();
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.parseColor("#000000"));
        mTextPaint.setStrokeWidth(10);
        mCyclePaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }

        y = mHeight;
        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (x > 50) {
            isLeft = true;
        } else if (x < 0) {
            isLeft = false;
        }

//        if (y > -50) {
//            y--;
//        }

        if (isLeft) {
            x = x - 1;
        } else {
            x = x + 1;
        }
        mPath.reset();
        y = (int) ((1 - mPercent / 100f) * mHeight);
        mPath.moveTo(0, y);
        mPath.cubicTo(200 + x * 2, 70 + y, 300 + x * 2, y - 60, mWidth, y);//前两个参数是辅助点
        mPath.lineTo(mWidth, mHeight);//充满整个画布
        mPath.lineTo(0, mHeight);//充满整个画布
        mPath.close();

        mBitmap.eraseColor(Color.parseColor("#00000000"));
        // mCyclePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));

        mCanvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, mCyclePaint);
        mWavePaint.setXfermode(xfermode);
        mCanvas.drawPath(mPath, mWavePaint);
        //  mCyclePaint.setXfermode(xfermode);

        canvas.drawBitmap(mBitmap, 0, 0, null);

        String str = mPercent + "";
//        float txtLength = mTextPaint.measureText(str);
//        canvas.drawText(mPercent + "%", mWidth / 2-txtLength/2, mHeight / 2, mTextPaint);

        mTextPaint.setTextSize(80);
        float txtLength = mTextPaint.measureText(str);

        canvas.drawText(str, mWidth / 2 - txtLength / 2, mHeight / 2 + 15, mTextPaint);

        postInvalidateDelayed(0);
        mTextPaint.setTextSize(40);
        canvas.drawText(" %", mWidth / 2 + 70, mHeight / 2 - 20, mTextPaint);
    }

    public void setPercent(int percent) {
        mPercent = percent;
    }
}
