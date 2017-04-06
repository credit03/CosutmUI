package com.guoyi.cosutmui.demo4;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guoyi.cosutmui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Created by Credit on 2017/3/31  15:32.
 * Email:credit_yi@163.com
 * Description:{一句话描述该类的作用}
 */

public class UcFragment extends Fragment {

    protected RecyclerView mRecylerView;
    protected LinearLayoutManager mManager;
    protected TestAdapter adapter;

    public UcFragment() {
        super();
    }

    protected View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_uc, container, false);

        mRecylerView = (RecyclerView) rootView.findViewById(R.id.recylerView);

        mManager = new LinearLayoutManager(getActivity());
        mRecylerView.setLayoutManager(mManager);
        mRecylerView.setItemAnimator(new DefaultItemAnimator());
        mRecylerView.setAdapter(adapter = new TestAdapter(getActivity()));
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            strings.add("data" + i);
        }
        adapter.setDatas(strings);

        return rootView;
    }

    protected class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<String> datas;
        private Context mContext;

        public TestAdapter(Context context) {
            mContext = context;
        }

        public void setDatas(List<String> datas) {
            this.datas = datas;
            this.notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView view = new TextView(mContext);
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, 100);
            view.setLayoutParams(params);
            view.setTextSize(18);
            view.setGravity(Gravity.CENTER);
            view.setTextColor(Color.BLACK);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            TextView view = (TextView) holder.itemView;
            view.setText(datas.get(position));
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

    }


    public void setHeadState(boolean canScroll) {
        if (!canScroll) {
            if (mRecylerView != null) {
                mRecylerView.getLayoutManager().scrollToPosition(0);
            }
        }
    }
}
