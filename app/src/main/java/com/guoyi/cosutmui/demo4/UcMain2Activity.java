package com.guoyi.cosutmui.demo4;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.guoyi.cosutmui.R;
import com.guoyi.cosutmui.demo4.behavior.UcBehaviorHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UcMain2Activity extends AppCompatActivity implements UcBehaviorHelper.onHeadstateListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.uc_tab_pager)
    TabLayout mTab;
    @BindView(R.id.uc_content_pager)
    HeadStateViewPager mViewpager;

    private List<UcFragment> mFragments;
    private List<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         *先初始化UcBehaviorHelper
         */
        UcBehaviorHelper.initInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uc_main_page);
        ButterKnife.bind(this);
        mFragments = new ArrayList<>();
        titles = new ArrayList<>();
        for (int j = 0; j < 5; j++) {
            titles.add("tab" + j);
            mFragments.add(new Uc2Fragment());
        }
        mViewpager.setOffscreenPageLimit(titles.size());
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), this, mFragments, titles);
        mViewpager.setAdapter(adapter);
        mTab.setupWithViewPager(mViewpager);

        setSupportActionBar(mToolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowTitleEnabled(true);
        bar.setTitle("UC头条");
        UcBehaviorHelper.getInstance().setListener(this);
        /**
         *       设置上推后，不能下拉
         */

        UcBehaviorHelper.getInstance().setCloseAfterEndabled(false);
    }

    @Override
    public void onBackPressed() {
        if (UcBehaviorHelper.getInstance().isOpen()) {
            super.onBackPressed();
        } else {
            UcBehaviorHelper.getInstance().openHeadPager();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.uc_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onHeadIsOpen(boolean state) {
        for (UcFragment u : mFragments) {
            u.setHeadState(!state);
        }

    }
}
