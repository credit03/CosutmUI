package com.guoyi.cosutmui.demo5;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.guoyi.cosutmui.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.guoyi.cosutmui.R.id.iv;

public class ZFBActivity extends AppCompatActivity {

    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.iv1)
    ImageView mIv1;
    @BindView(R.id.iv2)
    ImageView mIv2;
    @BindView(iv)
    ImageView mIv;
    @BindView(R.id.et_tool)
    EditText mEtTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zfb);
        ButterKnife.bind(this);
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));

        mRecylerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                TextView view = new TextView(ZFBActivity.this);

                return new RecyclerView.ViewHolder(view) {
                };
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                TextView view = (TextView) holder.itemView;
                RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, 100);
                view.setLayoutParams(params);
                view.setTextSize(14);
                view.setGravity(Gravity.CENTER);
                view.setTextColor(Color.BLACK);
                view.setText("Item" + position);
            }

            @Override
            public int getItemCount() {
                return 50;
            }
        });
    }

    @OnClick({R.id.iv, R.id.iv1, R.id.iv2, R.id.et_tool})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv:
                Toast.makeText(this, "点击了搜索icon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv1:
                Toast.makeText(this, "点击了LOGO ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv2:
                Toast.makeText(this, "点击了分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.et_tool:
                Toast.makeText(this, "点击了输入框", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
