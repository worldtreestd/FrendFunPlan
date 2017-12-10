package com.legend.ffplan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.legend.ffplan.R;
import com.legend.ffplan.common.adapter.CircleFragmentAdapter;
import com.legend.ffplan.common.viewimplement.ICommonView;

/**
 * @author Legend
 * @data by on 2017/12/9.
 * @description 圈子主界面
 */

public class CircleContentActivity extends AppCompatActivity implements ICommonView {

    public static final String CIRCLE_NAME ="circle_name";
    public static final String CIRCLE_IMAGE_ID = "circle_image_id";
    public static final String CIRCLE_INTRODUCE = "circle_introduce";

    private View mView;
    private ViewPager mViewPager;
    private CircleFragmentAdapter adapter;
    private TabLayout tabLayout;
    private android.support.v7.widget.Toolbar toolbar;
    private CollapsingToolbarLayout collaspingtoolbar;
    private ImageView circle_image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.circle_contenet,null);
        setContentView(mView);
        initView();
        initListener();
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        String circle_name = intent.getStringExtra(CIRCLE_NAME);
        int circle_image_id = intent.getIntExtra(CIRCLE_IMAGE_ID,0);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        collaspingtoolbar = findViewById(R.id.collapsing);
        collaspingtoolbar.setTitle(circle_name);
        // 设置收缩时title的位置
        collaspingtoolbar.setCollapsedTitleGravity(Gravity.TOP);
        // 设置展开时title的位置
        collaspingtoolbar.setExpandedTitleGravity(Gravity.CENTER_VERTICAL);

        adapter = new CircleFragmentAdapter(getSupportFragmentManager());

        circle_image = findViewById(R.id.circle_content_image);

        circle_image.setImageResource(circle_image_id);

        tabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.mViewPager);
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager,true);

    }

    @Override
    public void initListener() {
    }
}
