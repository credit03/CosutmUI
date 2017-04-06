package com.guoyi.cosutmui.demo4;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Credit on 2017/1/15.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<UcFragment> mFragments;
    private FragmentManager fragmentManager;

    public List<String> titles;


    public FragmentAdapter(FragmentManager fm, Context context, List<UcFragment> mFragments, List<String> titles) {
        super(fm);
        this.fragmentManager = fm;
        this.context = context;
        this.mFragments = mFragments;
        this.titles = titles;
    }


    @Override
    public int getCount() {
        return this.mFragments.size();
    }

    @Override
    public UcFragment getItem(int paramInt) {
        return mFragments.get(paramInt);
    }

    @Override
    public CharSequence getPageTitle(int paramInt) {
        return this.titles.get(paramInt);
    }


}
