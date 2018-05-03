package com.legend.ffpmvp.common.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.legend.ffpmvp.GuideAnimation.GuidePageActivity;
import com.legend.ffpmvp.R;
import com.legend.ffpmvp.common.utils.MyApplication;
import com.legend.ffpmvp.common.utils.PlanNotifyService;
import com.legend.ffpmvp.common.utils.SharedPreferenceUtils;
import com.legend.ffpmvp.common.utils.ToastUtils;

import static com.legend.ffpmvp.GuideAnimation.GuidePageActivity.isLogin;


/**
 * @author Legend
 * @data by on 2018/1/27.
 * @description
 */

public abstract class BaseActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private NetWorkCheckReceiver netWorkCheckReceiver;
    private Intent intent1;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        if (setResourceLayout() instanceof Integer) {
            setContentView((Integer) setResourceLayout());
        } else {
            setContentView((View) setResourceLayout());
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (intent1 == null) {
            intent1 = new Intent(this,PlanNotifyService.class);
        }
        if (intentFilter == null) {
            intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        }
        if (netWorkCheckReceiver == null) {
            netWorkCheckReceiver = new NetWorkCheckReceiver();
        }
        registerReceiver(netWorkCheckReceiver,intentFilter);
        initToolbar();
        initView();
        initListener();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent1);
        unregisterReceiver(netWorkCheckReceiver);
    }

    protected void init(){}

    public abstract Object setResourceLayout();

    public abstract void initView();

    public abstract void initListener();

    protected <T extends View> T $(int id) {
        return (T)super.findViewById(id);
    }

    private void initToolbar() {
        toolbar = $(R.id.toolbar);
        if (toolbar == null) {
            return;
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp());
        /**toolbar除掉阴影*/
        getSupportActionBar().setElevation(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(0);
        }
        if (showHomeAsUp()) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

    }

    protected void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    /**
     *  是否显示返回图标
     * @return
     */
    public abstract boolean showHomeAsUp();

    class NetWorkCheckReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            startService(intent1);
            final SharedPreferenceUtils sharedPreferenceUtils = new SharedPreferenceUtils(MyApplication.getInstance(),SharedPreferenceUtils.COOKIE);
            final String jwt_expires = sharedPreferenceUtils.get(SharedPreferenceUtils.JWT_EXPIRES);
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            if (TextUtils.isEmpty(jwt_expires) || !isLogin) {
                Log.d("LOGIGOGIIGIGOOG",""+isLogin+" "+jwt_expires);
                alertDialog.setMessage("您的当前身份信息已过期，请重新登录");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("重新登录", (dialogInterface, i) -> startActivity(new Intent(MyApplication.getInstance(), GuidePageActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK)));
                alertDialog.create().show();
            }

            if (!ToastUtils.checkNetState(context)) {
                ToastUtils.showToast(context,"当前网络出错，请检查一下您的连接状态");
            }
        }
    }
}
