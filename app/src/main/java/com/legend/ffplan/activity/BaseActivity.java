package com.legend.ffplan.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.legend.ffplan.common.util.ToastUtils;

/**
 * @author Legend
 * @data by on 2018/1/13.
 * @description
 */

public abstract class BaseActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private NetWorkCheckReceiver netWorkCheckReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setResourceLayout());
        intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        netWorkCheckReceiver = new NetWorkCheckReceiver();
        registerReceiver(netWorkCheckReceiver,intentFilter);
        initView();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netWorkCheckReceiver);
    }

    public abstract int setResourceLayout();

    public abstract void initView();

    public abstract void initListener();

    protected <T extends View> T $(int id) {
        return (T)super.findViewById(id);
    }

    class NetWorkCheckReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!ToastUtils.checkNetState(context)) {
                ToastUtils.showToast(context,"网络出错 请检查您的网络是否正常连接");
            }
        }
    }
}
