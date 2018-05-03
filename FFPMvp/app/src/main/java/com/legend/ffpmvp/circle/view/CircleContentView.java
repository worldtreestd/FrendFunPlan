package com.legend.ffpmvp.circle.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.legend.ffpmvp.R;
import com.legend.ffpmvp.circle.contract.CircleCommonContract;
import com.legend.ffpmvp.circle.model.CircleContentModelImpl;
import com.legend.ffpmvp.circle.model.adapter.CircleFragmentAdapter;
import com.legend.ffpmvp.circle.presenter.CircleContentPresenter;
import com.legend.ffpmvp.circle.search.view.SearchView;
import com.legend.ffpmvp.common.utils.ToastUtils;
import com.legend.ffpmvp.common.view.BaseActivity;
import com.legend.ffpmvp.common.view.HeartAnimationView;

import java.lang.ref.WeakReference;

/**
 * @author Legend
 * @data by on 2017/12/9.
 * @description 圈子详情界面
 */

public class CircleContentView extends BaseActivity implements CircleCommonContract.ICircleContentView{

    public static final String CIRCLE_NAME ="circle_name";
    public static final String CIRCLE_IMAGE_URL= "circle_image_URL";
    public static final String CIRCLE_INTRODUCE = "circle_introduce";
    public static final String CIRCLE_CREATED = "circle_created";
    public static final String CIRCLE_ADDRESS = "circle_address";
    public static final String CIRCLE_ID = "circle_id";
    public static final String CIRCLE_ADD_TIME = "circle_add_time";

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private CollapsingToolbarLayout collaspingtoolbar;
    private FloatingActionButton join_circle_btn;
    private ImageView circle_image;
    private int circle_id;
    private HeartAnimationView heartAnimationView;
    private CircleFragmentAdapter adapter;
    private CircleCommonContract.ICircleContentModel model;
    private CircleCommonContract.ICircleContentPresenter presenter;


    @Override
    protected void init() {
        super.init();
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        // 分解
//        getWindow().setEnterTransition(new Explode().setDuration(500));
//        getWindow().setExitTransition(new Explode().setDuration(500));
//        // 滑动进出
//        getWindow().setEnterTransition(new Slide().setDuration(500));
//        getWindow().setExitTransition(new Slide().setDuration(500));
        // 淡入淡出
        getWindow().setEnterTransition(new Fade().setDuration(1000));
        getWindow().setExitTransition(new Fade().setDuration(1000));
    }

    @Override
    public Object setResourceLayout() {

        return R.layout.circle_contenet;
    }
    @Override
    public void initView() {

        model = new CircleContentModelImpl();
        WeakReference<CircleContentView> reference = new WeakReference<>(this);
        presenter = new CircleContentPresenter(model,reference.get());
        Intent intent = getIntent();
        String circle_image_url = intent.getStringExtra(CIRCLE_IMAGE_URL);
        String circle_name = intent.getStringExtra(CIRCLE_NAME);
        circle_id = intent.getIntExtra(CircleContentView.CIRCLE_ID,100000);
        join_circle_btn = $(R.id.join_circle);
        heartAnimationView = $(R.id.heart_view);
        collaspingtoolbar = $(R.id.collapsing);
        collaspingtoolbar.setTitle(circle_name);
        // 设置收缩时title的位置
        collaspingtoolbar.setCollapsedTitleGravity(Gravity.TOP);
        // 设置展开时title的位置
        collaspingtoolbar.setExpandedTitleGravity(Gravity.CENTER_VERTICAL);
        adapter = new CircleFragmentAdapter(getSupportFragmentManager());

        circle_image = $(R.id.circle_content_image);


        Glide.with(this).load(circle_image_url)
                .placeholder(R.drawable.ic_launcher_background)
                .into(circle_image);

        tabLayout = $(R.id.tab_layout);
        mViewPager = $(R.id.mViewPager);
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager,true);
    }
    @Override
    public void initListener() {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                heartAnimationView.addHeart();
                handler.postDelayed(this,800);
            }
        };
        handler.postDelayed(runnable,800);
        join_circle_btn.setOnClickListener(view -> presenter.getData(circle_id));
    }

    @Override
    public boolean showHomeAsUp() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_search:
                startActivity(new Intent(this, SearchView.class));
                break;
            case R.id.share:
                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.putExtra(Intent.EXTRA_TEXT, "友趣计划 一款颠覆传统的计划提醒类App");
                intent1.setType("text/plain");
                startActivity(Intent.createChooser(intent1, "把我分享给更多小伙伴"));
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void showResult() {
        ToastUtils.showToast(this,"已经加入该圈子");
    }

    public AppCompatActivity get() {
        return this;
    }
}
