package com.ch.date1008;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 项目名称：Date1008
 * 类描述：
 * 创建人：陈志平
 * 创建时间：2016/10/17  16:58
 */
public class DataController {

    private int currentNum, min, max;
    private double currentTem, minTem, maxTem, tem;
    private MyAdapter.HolderTYpe_I holder;
    private DataBean dataBean;
    private MyAdapter adapter;
    private String name;
    private MiusThread miusThread;
    private boolean isOnLongClick;
    private PlusThread plusThread;
    private SaveEditListener listener;
    private int currentPosition;


    //更新文本框的值
    private Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            View view = (View) msg.obj;
            switch (msg.arg1) {
                case 1:
                    switch (view.getId()) {
                        case R.id.btn_minus://温度减
                            if (name.equals("体温")) {
                                if (tem > 10 * minTem && tem <= 10 * maxTem) {
                                    --tem;
                                } else {
                                    tem = 10 * minTem;
                                }
                                listener.saveEdit(holder, tem / 10 + "");
                                holder.et_parameters.setText(tem / 10 + "");

                            } else {
                                if (currentNum > min && currentNum <= max) {
                                    --currentNum;
                                } else {
                                    currentNum = min;
                                }

                                listener.saveEdit(holder, currentNum + "");
                                holder.et_parameters.setText(currentNum + "");

                            }


                            break;

                    }
                    break;
                case 2:
                    switch (view.getId()) {
                        case R.id.btn_plus://温度加

                            if (name.equals("体温")) {
                                if (tem >= 10 * minTem && tem < 10 * maxTem) {
                                    ++tem;
                                } else {
                                    tem = 10 * maxTem;
                                }

                                listener.saveEdit(holder, tem / 10 + "");
                                holder.et_parameters.setText(tem / 10 + "");
                            } else {
                                if (currentNum >= min && currentNum < max) {
                                    ++currentNum;
                                } else {
                                    currentNum = max;
                                }

                                listener.saveEdit(holder, currentNum + "");
                                holder.et_parameters.setText(currentNum + "");

                            }

                            //   Log.e("TAG", "当前数值: " + tem/10);
                            break;

                    }
                    break;


            }

            Log.e("TAG", "当前数值: " + holder.et_parameters.getText().toString());
        }


    };

    public DataController(MyAdapter.HolderTYpe_I holder, DataBean dataBean, MyAdapter adapter) {
        this.holder = holder;
        this.dataBean = dataBean;
        this.listener = adapter;
        int position= (int) holder.et_parameters.getTag();
        currentPosition=position;
        name = dataBean.getName();

        if (name != null) {
            if (name.equals("体温")) {
                currentTem = Double.valueOf(holder.et_parameters.getText().toString());
                tem = 10 * currentTem;
                minTem = Double.valueOf(dataBean.getMin());
                maxTem = Double.valueOf(dataBean.getMax());
            } else {
                currentNum = Integer.parseInt(dataBean.getCurrent());
                min = Integer.parseInt(dataBean.getMin());
                max = Integer.parseInt(dataBean.getMax());
                Log.e("TAG", "DataController: " + currentNum);
            }
        }
    }

    public void setData(MyAdapter.HolderTYpe_I holder, DataBean dataBean) {
        this.holder = holder;
        this.dataBean = dataBean;
        name = dataBean.getName();

        if (name != null) {
            if (name.equals("体温")) {
                currentTem = Double.valueOf(holder.et_parameters.getText().toString());
                tem = 10 * currentTem;
                minTem = Double.valueOf(dataBean.getMin());
                maxTem = Double.valueOf(dataBean.getMax());
            } else {
                currentNum = Integer.parseInt(holder.et_parameters.getText().toString());
                min = Integer.parseInt(dataBean.getMin());
                max = Integer.parseInt(dataBean.getMax());
            }
        }
    }


    public void onTouchChange(String methodName, int eventAction, View v) {

        if (holder.getAdapterPosition() == currentPosition) {
            //按下松开分别对应启动停止减线程方法
            if ("minus".equals(methodName)) {
                miusThread = new MiusThread(v);

                if (eventAction == MotionEvent.ACTION_DOWN) {
                    isOnLongClick = true;
                    miusThread.start();

                } else if (eventAction == MotionEvent.ACTION_UP) {
                    if (miusThread != null) {
                        isOnLongClick = false;
                        miusThread.interrupt();
                    }
                } else if (eventAction == MotionEvent.ACTION_MOVE) {
                    if (miusThread != null) {
                        isOnLongClick = true;
                    }
                }
            } else if ("plus".equals(methodName)) {//按下松开分别对应启动停止加线程方法
                plusThread = new PlusThread(v);
                if (eventAction == MotionEvent.ACTION_DOWN) {
                    isOnLongClick = true;
                    plusThread.start();
                    //isOnLongClick=false;
                    Log.e("11111", "onTouchChange:eventAction == MotionEvent.ACTION_DOWN::::::::::::: "+isOnLongClick);
                } else if (eventAction == MotionEvent.ACTION_UP) {
                    if (plusThread != null) {
                        plusThread.interrupt();
                        isOnLongClick = false;

                        Log.e("11111", "onTouchChange:eventAction == MotionEvent.ACTION_UP::::::::::::: "+isOnLongClick);
                    }
                } else if (eventAction == MotionEvent.ACTION_MOVE) {
                    if (plusThread != null) {
                        isOnLongClick = true;

                        Log.e("11111", "onTouchChange:eventAction == MotionEvent.ACTION_UP::::::::::::: "+isOnLongClick);
                    }
                }
            }
        } else {
            isOnLongClick=false;
        }
    }

    //减操作线程
    public class MiusThread extends Thread {
        public View v;

        public MiusThread(View view) {
            this.v = view;
        }

        @Override
        public void run() {
            while (isOnLongClick) {
                try {
                    Thread.sleep(100);
                    Message msg = new Message();
                    msg.obj = v;
                    msg.arg1 = 1;
                    myHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }
    }

    //加操作线程
    public class PlusThread extends Thread {
        private View view;

        public PlusThread(View v) {
            this.view = v;
        }

        @Override
        public void run() {
            while (isOnLongClick) {
                try {
                    Thread.sleep(100);
                    Message msg = new Message();
                    msg.obj = view;
                    msg.arg1 = 2;
                    myHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }
    }

    public interface SaveEditListener {
        void saveEdit(MyAdapter.BaseHolder holderTYpe_i, String value);
    }
}
