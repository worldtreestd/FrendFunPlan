package com.legend.ffplan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.legend.ffplan.GuideAnimation.GuidePageActivity;
import com.legend.ffplan.activity.CircleSearchActivity;
import com.legend.ffplan.activity.CreateCircleActivity;
import com.legend.ffplan.activity.ReleasePlanActivity;
import com.legend.ffplan.common.adapter.MainFragmentAdapter;
import com.legend.ffplan.common.view.DepthPageTransformer;
import com.legend.ffplan.common.viewimplement.ICommonView;
import com.legend.ffplan.fragment.HomeCircleFragment;
import com.legend.ffplan.fragment.circlecenter.CircleConversationFragment;
import com.lilei.springactionmenu.ActionMenu;
import com.lilei.springactionmenu.OnActionItemClickListener;

import org.json.JSONArray;

import me.james.biuedittext.BiuEditText;


public class MainActivity extends AppCompatActivity implements ICommonView {

    private TextView mTextMessage;
    private BiuEditText biuEditText;
    private MainFragmentAdapter mainFragmentAdapter;
    private ViewPager mViewPager;
    private BottomNavigationView navigation;
    private Toolbar toolbar;
    private View mView;
    private ActionMenu actionMenu;
    private JSONArray result;
    private int count;
    private AutoCompleteTextView mSearchAutoComplete;
    private SearchView searchView;
    private HomeCircleFragment homeCircleFragment;
    public static String user_image_url;
    public static String user_nick_name;
    private DrawerLayout mDrawerLayout;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.activity_main,null);
        setContentView(mView);
        initView();
        initListener();
        switchIntent();
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
//                LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
//                LinearLayout editTextLayout = (LinearLayout) inflater.inflate(R.layout.biuedittext_layout,null);
//                biuEditText = editTextLayout.findViewById(R.id.biuEditText);
//                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//                dialog.setIcon(R.drawable.ic_search_black_24dp);
//                dialog.setTitle("请您输入圈子的ID号或简介");
//                dialog.setView(editTextLayout);
//                dialog.setPositiveButton("查找", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        if (asyncTask == null) {
//                            asyncTask = (CircleSearchAsyncTask) new CircleSearchAsyncTask()
//                                    .execute(ApiUtils.SEARCH + biuEditText.getText().toString());
//                        }
//                        mViewPager.setCurrentItem(0);
//                        Toast.makeText(MainActivity.this, "查找成功！", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Snackbar.make(mView, "您要取消查找吗？", Snackbar.LENGTH_LONG).setAction("取消", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                ToastUtils.showToast(MainActivity.this,"取消成功");
//                            }
//                        }).show();
//                    }
//                });
//                dialog.create().show();
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
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("让你的计划不再枯燥");
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        mViewPager = findViewById(R.id.mViewPager);
        Intent intent = getIntent();
        user_image_url = intent.getStringExtra(CircleConversationFragment.USER_IMAGE_URL);
        user_nick_name = intent.getStringExtra(CircleConversationFragment.USER_NAME);
        if (actionMenu == null) {
            actionMenu = mView.findViewById(R.id.float_button);
        }
        actionMenu.addView(R.drawable.like, getItemColor(R.color.colorAccent), getItemColor(R.color.blue_5));
        actionMenu.addView(R.drawable.add,getItemColor(R.color.colorPrimary), getItemColor(R.color.light_blue));
        actionMenu.addView(R.drawable.write);
        mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mainFragmentAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageTransformer(true,new DepthPageTransformer());
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
    }
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
            Snackbar.make(mView, "亲 您不想再多看一会儿了吗？", Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
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
