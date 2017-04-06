package com.guoyi.cosutmui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guoyi.cosutmui.demo1.View1Acitvity;
import com.guoyi.cosutmui.demo2.View2Activity;
import com.guoyi.cosutmui.demo3.MiMine2Activity;
import com.guoyi.cosutmui.demo3.MiMineActivity;
import com.guoyi.cosutmui.demo4.UcMain2Activity;
import com.guoyi.cosutmui.demo4.UcMainPageActivity;
import com.guoyi.cosutmui.demo5.ZFBActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.recylerView)
    RecyclerView recylerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        List<String> strings = new ArrayList<>();
        strings.add("彩虹进度条");
        strings.add("仿微信9张图片布局");
        strings.add("我的信息实现方式1");
        strings.add("我的信息实现方式2");
        strings.add("仿UC首页头部可下拉");
        strings.add("仿UC首页头部不可下拉");
        strings.add("仿支付宝首页");

        recylerView.setLayoutManager(new LinearLayoutManager(this));
        recylerView.setAdapter(new MainAdapter(strings));
    }


    private class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<String> datas;

        public MainAdapter(List<String> datas) {
            this.datas = datas;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView view = new TextView(MainActivity.this);
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
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (position) {
                        case 0:
                            startActivity(new Intent(MainActivity.this, View1Acitvity.class));
                            break;
                        case 1:
                            startActivity(new Intent(MainActivity.this, View2Activity.class));
                            break;
                        case 2:
                            startActivity(new Intent(MainActivity.this, MiMineActivity.class));
                            break;
                        case 3:
                            startActivity(new Intent(MainActivity.this, MiMine2Activity.class));
                            break;
                        case 4:
                            startActivity(new Intent(MainActivity.this, UcMainPageActivity.class));
                            break;
                        case 5:
                            startActivity(new Intent(MainActivity.this, UcMain2Activity.class));
                            break;
                        case 6:
                            startActivity(new Intent(MainActivity.this, ZFBActivity.class));
                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }
}
