package com.legend.ffplan;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.legend.ffplan.GuideAnimation.GuidePageActivity;
import com.legend.ffplan.activity.BaseActivity;
import com.legend.ffplan.activity.CircleSearchActivity;
import com.legend.ffplan.activity.CreateCircleActivity;
import com.legend.ffplan.activity.ReleasePlanActivity;
import com.legend.ffplan.common.adapter.MainFragmentAdapter;
import com.legend.ffplan.common.view.DepthPageTransformer;
import com.legend.ffplan.common.view.SnowAnimationView;
import com.legend.ffplan.fragment.circlecenter.CircleConversationFragment;
import com.legend.ffplan.fragment.main.HomeCircleFragment;
import com.lilei.springactionmenu.ActionMenu;
import com.lilei.springactionmenu.OnActionItemClickListener;

public class MainActivity extends BaseActivity{

    private MainFragmentAdapter mainFragmentAdapter;
    private ViewPager mViewPager;
    private BottomNavigationView navigation;
    private Toolbar toolbar;
    private View mView;
    private ActionMenu actionMenu;
    private int count;
    private HomeCircleFragment homeCircleFragment;
    public static String user_image_url;
    public static String user_nick_name;
    private DrawerLayout mDrawerLayout;
    private SnowAnimationView snow_animation;
    private Handler handler;
    private Runnable runnable;
    private NavigationView navigationView;
    private boolean isChecked = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewPager.setCurrentItem(0,false);
                    return true;
                case R.id.navigation_dashboard:
                    mViewPager.setCurrentItem(1,false);
                    return true;
                case R.id.navigation_notifications:
                    mViewPager.setCurrentItem(2,false);
                    return true;
                default:
                    break;
            }
            return false;
        }
    };

    @Override
    public int setResourceLayout() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.app_bar_search:
                startActivity(new Intent(this, CircleSearchActivity.class));
                break;
            case R.id.share:
                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.putExtra(Intent.EXTRA_TEXT, "友趣计划 一款颠覆传统的计划提醒类App");
                intent1.setType("text/plain");
                startActivity(Intent.createChooser(intent1, "把我分享给更多小伙伴"));
                break;
            case R.id.personal:
                Toast.makeText(MainActivity.this, "进入个人中心", Toast.LENGTH_SHORT).show();
                mViewPager.setCurrentItem(2);
                break;
            case R.id.settings:
                Toast.makeText(MainActivity.this, "进入设置", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void initView() {
        mView = $(R.layout.activity_main);
        toolbar = $(R.id.toolbar);
        snow_animation = $(R.id.snow_view);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("让你的计划不再枯燥");
        handler = new Handler();
        mDrawerLayout = $(R.id.drawer_layout);
        NavigationView navigationView = $(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setCheckedItem(R.id.snow_anim);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                switch(item.getItemId()) {
                    case R.id.snow_anim:
                        if (isChecked) {
                            handler.postDelayed(runnable,100);
                            isChecked = false;
                        } else {
                            handler.removeCallbacks(runnable);
                            isChecked = true;
                        }
                        break;
                    default:
                        handler.removeCallbacks(runnable);
                }
                return true;
            }
        });
        mViewPager = $(R.id.mViewPager);
        Intent intent = getIntent();
        user_image_url = intent.getStringExtra(CircleConversationFragment.USER_IMAGE_URL);
        user_nick_name = intent.getStringExtra(CircleConversationFragment.USER_NAME);
        if (actionMenu == null) {
            actionMenu = $(R.id.float_button);
        }
        actionMenu.addView(R.drawable.like, getItemColor(R.color.colorAccent), getItemColor(R.color.blue_5));
        actionMenu.addView(R.drawable.add,getItemColor(R.color.colorPrimary), getItemColor(R.color.light_blue));
        actionMenu.addView(R.drawable.write);
        mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mainFragmentAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageTransformer(true,new DepthPageTransformer());
        navigation = $(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
        switchIntent();
    }
    public void initListener() {
        runnable = new Runnable() {
            @Override
            public void run() {
                snow_animation.addSnow();
                handler.postDelayed(this,100);
            }
        };
        handler.postDelayed(runnable,100);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int currentItem = mViewPager.getCurrentItem();
                switch (currentItem) {
                    case 0:
                        navigation.setSelectedItemId(R.id.navigation_home);
                        break;
                    case 1:
                        navigation.setSelectedItemId(R.id.navigation_dashboard);
                        break;
                    case 2:
                        navigation.setSelectedItemId(R.id.navigation_notifications);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        actionMenu.setItemClickListener(new OnActionItemClickListener() {
            @Override
            public void onItemClick(int index) {
                switch (index) {
                    case 0:
                        break;
                    case 1:
                        mViewPager.setCurrentItem(2);
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, CreateCircleActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, ReleasePlanActivity.class));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onAnimationEnd(boolean b) {

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Snackbar.make(navigation, "亲 您不想再多看一会儿了吗？", Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, GuidePageActivity.class);
                    intent.putExtra("exit","0");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     *  接收Intent传来的参数
     *   跳转至相应的页面
     */
    private void switchIntent() {
        String s = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(s)) {
            int id = Integer.parseInt(s);
            switch (id) {
                case 0:
                    mViewPager.setCurrentItem(0);
                    break;
                case 1:
                    mViewPager.setCurrentItem(1);
                    break;
                case 2:
                    mViewPager.setCurrentItem(2);
                    break;
                default:
                    break;
            }
        }
    }
    private int getItemColor(int colorID) {
        return getResources().getColor(colorID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        actionMenu = null;
    }
}
