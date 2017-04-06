package com.guoyi.cosutmui.demo4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guoyi.cosutmui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Created by Credit on 2017/3/31  15:32.
 * Email:credit_yi@163.com
 * Description:{一句话描述该类的作用}
 */

public class Uc2Fragment extends UcFragment implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout mRefreshLayout;

    public Uc2Fragment() {
        super();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_uc2, container, false);

        mRecylerView = (RecyclerView) rootView.findViewById(R.id.recylerView);
        mRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);

        mManager = new LinearLayoutManager(getActivity());
        mRecylerView.setLayoutManager(mManager);
        mRecylerView.setItemAnimator(new DefaultItemAnimator());
        mRecylerView.setAdapter(adapter = new TestAdapter(getActivity()));
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            strings.add("data" + i);
        }

        adapter.setDatas(strings);
        mRefreshLayout.setEnabled(false);
        mRefreshLayout.setOnRefreshListener(this);
        return rootView;
    }

    @Override
    public void onRefresh() {

        this.mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    public void setHeadState(boolean canScroll) {
        if (!canScroll) {
            if (mRecylerView != null) {
                //要禁用，scrollToPosition才有效
                mRefreshLayout.setEnabled(false);
                mRecylerView.getLayoutManager().scrollToPosition(0);
            }
        } else {
            mRefreshLayout.setEnabled(true);
        }
    }
}
