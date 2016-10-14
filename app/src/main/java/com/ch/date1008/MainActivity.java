package com.ch.date1008;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.SeekBar;

import com.ch.fqnumedittext.FQNumEditText;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private WaveLoadingView wave;
    private FQNumEditText ed_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        seekBar = ((SeekBar) findViewById(R.id.seekBar));
        wave = ((WaveLoadingView) findViewById(R.id.wave));
        ed_text = ((FQNumEditText) findViewById(R.id.ed_text));

        ed_text.setHint("请输入内容")
                .setLength(100)
                .setLineColor(Color.BLUE)
                .setTextColor(Color.RED)
                .setTextSize(16)
                .setMinHeight(100)
                .setType(FQNumEditText.PERCENTAGE)
                .show();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                wave.setPercent(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
