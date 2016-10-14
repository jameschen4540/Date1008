package com.ch.date1008;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 项目名称：Date1008
 * 类描述：
 * 创建人：陈志平
 * 创建时间：2016/10/13  11:35
 */
public class CountDownView extends View {

    private static final String TAG = CountDownView.class.getSimpleName();
    private static final int BACKGROUND_COLOR = 0x50555555;
    private static final float BORDER_WIDTH = 15f;
    private static final int BORDER_COLOR = 0xFF6ADBFE;
    private static final String TEXT = "跳过广告";
    private static final float TEXT_SIZE = 50f;
    private static final int TEXT_COLOR = 0xFFFFFFFF;

    private int backgroundColor;
    private float borderWidth;
    private int borderColor;
    private String text;
    private int textColor;
    private float textSize;

    private Paint cyclerPaint;
    private Paint borderPaint;
    private TextPaint textPaint;
    private Canvas cyclerCanvas;
    private StaticLayout staticLayout;
    private float progress;
    private CountDownViewListener listener;
    private CountDownTimer countDownTimer;


    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountDownView);
        backgroundColor = typedArray.getColor(R.styleable.CountDownView_background_color, BACKGROUND_COLOR);
        borderColor = typedArray.getColor(R.styleable.CountDownView_border_color, BORDER_COLOR);
        borderWidth = typedArray.getDimension(R.styleable.CountDownView_border_width, BORDER_WIDTH);
        text = typedArray.getString(R.styleable.CountDownView_text);
        textColor = typedArray.getColor(R.styleable.CountDownView_textColor, TEXT_COLOR);
        textSize = typedArray.getDimension(R.styleable.CountDownView_textSize, TEXT_SIZE);
        if (text == null) {
            text = TEXT;
        }

        typedArray.recycle();

        init();


    }

    private void init() {

        cyclerPaint = new Paint();
        cyclerPaint.setAntiAlias(true);
        cyclerPaint.setColor(backgroundColor);
        cyclerPaint.setStyle(Paint.Style.FILL);
        cyclerPaint.setDither(true);
        // cyclerPaint.setAlpha(60);


        textPaint = new TextPaint();
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);

        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setDither(true);
        // borderPaint.setAlpha(90);

        int textWidth = (int) textPaint.measureText(text, 0, (text.length() + 1) / 2);


        staticLayout = new StaticLayout(text, textPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1f, 0, false);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

//        int widthPixels = getResources().getDisplayMetrics().widthPixels;
//        int heightPixels = getResources().getDisplayMetrics().heightPixels;


//        if (widthMode != MeasureSpec.EXACTLY) {
//            widthSize = staticLayout.getWidth()*2;
//        }
        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = (int) ((staticLayout.getWidth() + borderWidth) * 2);
        }

//        if (heightMode != MeasureSpec.EXACTLY) {
//            heightSize = staticLayout.getHeight()*2;
//        }

        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) ((staticLayout.getHeight() + borderWidth) * 2);
        }

        setMeasuredDimension(widthSize, heightSize);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        int min = Math.min(width, height);
        //画圆
        canvas.drawCircle(width / 2, height / 2, (float) (min / 2 - (1.5 * borderWidth)), cyclerPaint);

        RectF rectF;
//        if (width > height) {
//            rectF = new RectF((width - min) / 2 + borderWidth / 2, borderWidth / 2, (width + min) / 2 - borderWidth / 2, height - borderWidth / 2);
//        } else {
//            rectF = new RectF(borderWidth / 2, height / 2 - min / 2 + borderWidth / 2, width - borderWidth / 2, height / 2 + min / 2 - borderWidth / 2);
//        }

        if (width > height) {
            rectF = new RectF((width - min) / 2 - 2 * borderWidth, 0, (width + min) / 2 + borderWidth * 2, height + borderWidth);
        } else {
            rectF = new RectF(borderWidth / 2, height / 2 - min / 2 + borderWidth / 2, width - borderWidth / 2, height / 2 + min / 2 - borderWidth / 2);
        }


        //画边缘线
        canvas.drawArc(rectF, -90, progress, false, borderPaint);
        // canvas.drawText("稍等片刻", width / 2, height / 2 - textPaint.descent() + textPaint.getTextSize() / 2, textPaint);
        canvas.translate(width / 2, height / 2 - staticLayout.getHeight() / 2);


        staticLayout.draw(canvas);

        invalidate();
    }

    public void start(final int time) {

       countDownTimer = new CountDownTimer(time, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                progress = ((time - millisUntilFinished) / (float) time) * 360;
            }

            @Override
            public void onFinish() {
                progress = 360;
                listener.onFinshCount();
                invalidate();
            }
        };
        countDownTimer.start();
    }

    public void stop() {
        if (countDownTimer != null) {

            countDownTimer.cancel();
        }
    }


    public void setCountDownViewListener(CountDownViewListener listener) {
        this.listener = listener;
    }

    interface CountDownViewListener {

        void onStartCount();

        void onFinshCount();


    }

}
