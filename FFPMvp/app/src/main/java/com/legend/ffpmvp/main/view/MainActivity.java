package com.legend.ffpmvp.main.view;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.legend.ffpmvp.GuideAnimation.GuidePageActivity;
import com.legend.ffpmvp.R;
import com.legend.ffpmvp.circle.search.view.SearchView;
import com.legend.ffpmvp.circle.view.CircleConversationView;
import com.legend.ffpmvp.circle.view.CreateCircleView;
import com.legend.ffpmvp.common.adapter.MainFragmentAdapter;
import com.legend.ffpmvp.common.utils.MyApplication;
import com.legend.ffpmvp.common.utils.SharedPreferenceUtils;
import com.legend.ffpmvp.common.view.BaseActivity;
import com.legend.ffpmvp.plan.view.ReleasePlanView;
import com.lilei.springactionmenu.ActionMenu;
import com.lilei.springactionmenu.OnActionItemClickListener;

/**
 * @author HP
 */
public class MainActivity extends BaseActivity implements IMainView {

    private MainFragmentAdapter mainFragmentAdapter;
    private ViewPager mViewPager;
    private BottomNavigationView navigation;
    private ActionMenu actionMenu;
    public static String user_image_url;
    public static String openId;
    public static String user_nick_name = "WorldTree";


    @Override
    public Object setResourceLayout() {
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
            case R.id.app_bar_search:
                startActivity(new Intent(this, SearchView.class));
                break;
            case R.id.share:
                String urlQQ = "mqqwpa://im/chat?chat_type=wpa&uin=qqnumber&version=1";
                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.putExtra(Intent.EXTRA_TEXT, "友趣计划 一款颠覆传统的计划提醒类App");
                intent1.putExtra(Intent.ACTION_VIEW,Uri.parse(urlQQ));
                intent1.setType("text/plain");
                startActivity(intent1);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void initView() {

        mViewPager = $(R.id.mViewPager);
        SharedPreferenceUtils shared = new SharedPreferenceUtils(MyApplication.getInstance(),SharedPreferenceUtils.COOKIE);
        user_image_url = shared.get(CircleConversationView.USER_IMAGE_URL);
        user_nick_name = shared.get(CircleConversationView.USER_NAME);
        openId = shared.get(SharedPreferenceUtils.OPENID);

        if (actionMenu == null) {
            actionMenu = $(R.id.float_button);
        }
        actionMenu.addView(R.drawable.like, getItemColor(R.color.colorAccent), getItemColor(R.color.blue_5));
        actionMenu.addView(R.drawable.add,getItemColor(R.color.colorPrimary), getItemColor(R.color.light_blue));
        actionMenu.addView(R.drawable.write);
        mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mainFragmentAdapter);
        mViewPager.setOffscreenPageLimit(3);
        navigation = $(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        switchIntent();
    }
    @Override
    public void initListener() {
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
                        startActivity(new Intent(MainActivity.this, CreateCircleView.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, ReleasePlanView.class));
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
    public boolean showHomeAsUp() {
        return false;
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewPager.setCurrentItem(0,true);
                    setToolbarTitle("友趣计划");
                    return true;
                case R.id.navigation_dashboard:
                    mViewPager.setCurrentItem(1,false);
                    setToolbarTitle("计划中心");
                    return true;
                case R.id.navigation_notifications:
                    mViewPager.setCurrentItem(2,false);
                    setToolbarTitle("个人中心");
                    return true;
                default:
                    break;
            }
            return false;
        }
    };

    @Override
    public void switchIntent() {
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        actionMenu = null;
    }

    private int getItemColor(int colorID) {
        return getResources().getColor(colorID);
    }


}
