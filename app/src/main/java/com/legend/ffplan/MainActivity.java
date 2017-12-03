package com.legend.ffplan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.legend.ffplan.common.adapter.MainFragmentAdapter;
import com.legend.ffplan.common.view.RotateDownPageTransformer;
import com.legend.ffplan.common.viewimplement.ICommonView;


public class MainActivity extends AppCompatActivity implements ICommonView {

    private TextView mTextMessage;
    private MainFragmentAdapter mainFragmentAdapter;
    private ViewPager mViewPager;
    private BottomNavigationView navigation;
    private Toolbar toolar;
    private View mView;

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
                final EditText editText = new EditText(this);
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setIcon(R.drawable.ic_search_black_24dp);
                dialog.setTitle("请您输入圈子的ID号");
                dialog.setView(editText);
                dialog.setPositiveButton("查找", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this,"查找成功！",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Snackbar.make(mView, "您要取消查找吗？", Snackbar.LENGTH_LONG).setAction("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,"取消成功",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                    }
                });
                dialog.create().show();
                break;
            case R.id.share:
                Intent intent1=new Intent(Intent.ACTION_SEND);
                intent1.putExtra(Intent.EXTRA_TEXT,"友趣计划 一款颠覆传统的计划提醒类App");
                intent1.setType("text/plain");
                startActivity(Intent.createChooser(intent1,"share"));
                break;
            case R.id.personal:
                Toast.makeText(MainActivity.this,"进入个人中心",Toast.LENGTH_SHORT).show();
                mViewPager.setCurrentItem(2);
                break;
            case R.id.settings:
                Toast.makeText(MainActivity.this,"进入设置",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    public void initView() {
        toolar = findViewById(R.id.toolbar);
        setSupportActionBar(toolar);
        toolar.setSubtitle("让你的计划不再枯燥");

        mViewPager = findViewById(R.id.mViewPager);
        mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mainFragmentAdapter);
        mViewPager.setPageTransformer(true,new RotateDownPageTransformer());
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

    }
}
