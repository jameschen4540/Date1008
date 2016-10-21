package com.ch.date1008;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 项目名称：Date1008
 * 类描述：
 * 创建人：陈志平
 * 创建时间：2016/10/17  15:36
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.BaseHolder> {

    private static final String TAG = "qq";
    private Context context;
    private List<DataBean> list;
    private final int TYPE_I = 0;
    private final int TYPE_II = 1;
    private Map<Integer,String> dataMap=new TreeMap<>();


    public MyAdapter(Context context, List<DataBean> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getItemViewType(int position) {
        DataBean dataBean = list.get(position);
        if (null == (dataBean.getMinDefault()) && null == (dataBean.getMaxDefault())) {
            return TYPE_I;
        } else {
            return TYPE_II;
        }
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_I) {

            View inflate = LayoutInflater.from(context).inflate(R.layout.item_recycler_collect, parent, false);
            return new HolderTYpe_I(inflate);
        } else if (viewType == TYPE_II) {

            View inflate = LayoutInflater.from(context).inflate(R.layout.item_type_ii, parent, false);
            return new HolderType_II(inflate);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, final int position) {
        final DataBean dataBean = list.get(position);
        int viewType = getItemViewType(position);
        if (viewType == TYPE_I) {
            final HolderTYpe_I myHolder = (HolderTYpe_I) holder;
            myHolder.et_parameters.setText(dataBean.getCurrent());
            myHolder.et_parameters.setHint("请输入" + dataBean.getName());
            myHolder.tv_parameter.setText(dataBean.getName() + "(" + dataBean.getUnit() + ")");


//            myHolder.et_parameters.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    myHolder.et_parameters.setText(s.toString());
//                }
//            });

            final DataController controller = new DataController(myHolder, dataBean);

            myHolder.btn_minus.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                   // dataMap.put(position,myHolder.et_parameters.getText().toString());

                    controller.setData(myHolder,dataBean);
                    //  Log.e(TAG, "onTouch: "+myHolder.et_parameters.getText().toString());
                    controller.onTouchChange("minus", event.getAction(), v);
                    return false;
                }
            });

            myHolder.btn_plus.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // controller.onTouchChange("plus", event.getAction(), v);
                  //  dataMap.put(position,myHolder.et_parameters.getText().toString());
                    controller.setData(myHolder,dataBean);
                    controller.onTouchChange("plus", event.getAction(), v);
                    return false;
                }
            });

            dataMap.put(position,myHolder.et_parameters.getText().toString());
           // Log.e(TAG, "onTouch: "+myHolder.et_parameters.getText().toString());
        } else if (viewType == TYPE_II) {

            final HolderType_II holderType_ii = (HolderType_II) holder;
            holderType_ii.tv_name.setText(dataBean.getName() + "(" + dataBean.getUnit() + ")");
            holderType_ii.et_min.setText(dataBean.getMinDefault());
            holderType_ii.et_max.setText(dataBean.getMaxDefault());
            //通过设置tag,防止position紊乱

            holderType_ii.et_min.setTag(position);


            //添加editText的监听事件
            holderType_ii.et_min.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    holderType_ii.et_min.setText(s.toString());

                }
            });

            holderType_ii.et_max.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    holderType_ii.et_max.setText(s.toString());
                }
            });

        }

    }


    public class BaseHolder extends RecyclerView.ViewHolder {
        public BaseHolder(View itemView) {
            super(itemView);
        }
    }


    public class HolderTYpe_I extends BaseHolder {

        public final TextView tv_parameter;
        public final ImageView btn_minus;
        public final ImageView btn_plus;
        public final EditText et_parameters;

        public HolderTYpe_I(View itemView) {
            super(itemView);
            tv_parameter = ((TextView) itemView.findViewById(R.id.tv_parameter));
            btn_minus = ((ImageView) itemView.findViewById(R.id.btn_minus));
            btn_plus = ((ImageView) itemView.findViewById(R.id.btn_plus));
            et_parameters = ((EditText) itemView.findViewById(R.id.et_parameter));
        }
    }

    public class HolderType_II extends BaseHolder {

        private TextView tv_name;
        private EditText et_min, et_max;

        public HolderType_II(View itemView) {
            super(itemView);
            tv_name = ((TextView) itemView.findViewById(R.id.tv_item_collect_name));
            et_min = (EditText) itemView.findViewById(R.id.et_collect_press_min);
            et_max = (EditText) itemView.findViewById(R.id.et_collect_press_max);

        }
    }

    public interface SaveEditListener {
        void saveEdit(int position, String value);
    }

    public String getEditText() {

        StringBuffer buffer=new StringBuffer();
        Iterator iter = dataMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String s = entry.getValue().toString();
            buffer.append(","+s);
        }


      //  String s = dataMap.get(0);
       // Log.e(TAG, "getEditText: "+s );
        return buffer.toString();
    }

//    public class TextSwatch implements TextWatcher {
//
//        private BaseHolder baseHolder;
//
//        public TextSwatch(BaseHolder baseHolder) {
//            this.baseHolder = baseHolder;
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//            //用户输入完毕后，处理输入数据，回调给主界面处理
//            SaveEditListener listener = (SaveEditListener) context;
//
//
//            if (s != null) {
//                listener.saveEdit(Integer.parseInt(mHolder.c_name_et.getTag().toString()), s.toString());
//            }
//
//
//        }
//    }
}


