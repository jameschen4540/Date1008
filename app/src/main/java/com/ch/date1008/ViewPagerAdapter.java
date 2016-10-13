package com.ch.date1008;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * 项目名称：Date1008
 * 类描述：
 * 创建人：陈志平
 * 创建时间：2016/10/9  17:08
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<ImageView> list;
    public ViewPagerAdapter(List<ImageView> list) {
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(this.list.get(position));
        return this.list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(this.list.get(position));

    }
}
