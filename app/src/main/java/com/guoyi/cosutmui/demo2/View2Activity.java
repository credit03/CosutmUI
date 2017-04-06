package com.guoyi.cosutmui.demo2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.guoyi.cosutmui.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class View2Activity extends AppCompatActivity {

    @BindView(R.id.nine)
    NinePhotoView nine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view2);
        ButterKnife.bind(this);
    }
}
