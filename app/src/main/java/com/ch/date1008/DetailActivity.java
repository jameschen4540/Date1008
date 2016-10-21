package com.ch.date1008;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView recyclerview;
    private List<DataBean> list;
    private MyAdapter adapter;
    private TextView text;
    private Button btn_commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_detail);
        text = ((TextView) findViewById(R.id.tv_detail));
        btn_commit = ((Button) findViewById(R.id.btn_commit));
        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // text.setText(list.get(0).getCurrent());
                text.setText(adapter.getEditText());
            }
        });
        recyclerview = (RecyclerView)findViewById(R.id.recycler);

        list = new ArrayList<>();
        initData();
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this,list);
        recyclerview.setAdapter(adapter);

    }

    private void initData() {
        DataBean dataBean = new DataBean("滴速", "次/分", "20", "60", "70");
        list.add(dataBean);
        DataBean dataBean1 = new DataBean("脉搏", "次/分", "20", "30", "50");
        list.add(dataBean1);
        DataBean dataBean2 = new DataBean("呼吸", "次/分", "20", "40", "80");
        list.add(dataBean2);
        DataBean dataBean3 = new DataBean("体温", "℃", "37", "38.0", "42");
        list.add(dataBean3);
        DataBean dataBean4 = new DataBean("血压", "次/分", "20", "25", "60");
        dataBean4.setMinDefault("20");
        dataBean4.setMaxDefault("60");
        list.add(dataBean4);
        DataBean dataBean5 = new DataBean("滴速", "次/分", "20", "30", "70");
        list.add(dataBean5);
        DataBean dataBean6 = new DataBean("脉搏", "次/分", "20", "40", "50");
        list.add(dataBean6);
        DataBean dataBean7 = new DataBean("呼吸", "次/分", "20", "60", "80");
        list.add(dataBean7);
        DataBean dataBean8 = new DataBean("体温", "℃", "37", "37.2", "42");
        list.add(dataBean8);
    }
}
