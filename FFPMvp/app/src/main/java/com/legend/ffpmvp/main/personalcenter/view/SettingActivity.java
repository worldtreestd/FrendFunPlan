package com.legend.ffpmvp.main.personalcenter.view;


import com.legend.ffpmvp.R;
import com.legend.ffpmvp.common.view.BaseActivity;

/**
 * @author HP
 */
public class SettingActivity extends BaseActivity {

    @Override
    public Object setResourceLayout() {
        return R.layout.activity_settings;
    }

    @Override
     public void initView() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_fragment, SettingFragment.newInstance())
                .commit();
    }

    @Override
    public void initListener() {

    }

    @Override
    public boolean showHomeAsUp() {
        return true;
    }
}
