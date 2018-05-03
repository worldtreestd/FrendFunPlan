package com.legend.ffpmvp.main.view;

import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import com.legend.ffpmvp.R;
import com.legend.ffpmvp.common.view.BaseActivity;

/**
 * @author Legend
 * @data by on 2018/2/8.
 * @description
 */

public class AboutView extends BaseActivity {
    @Override
    public Object setResourceLayout() {
        return R.layout.about_view;
    }

    @Override
    public void initView() {
        TextView tv_content = $(R.id.tv_content);
        tv_content.setAutoLinkMask(Linkify.ALL);
        tv_content.setMovementMethod(LinkMovementMethod
                .getInstance());
    }

    @Override
    public void initListener() {

    }

    @Override
    public boolean showHomeAsUp() {
        return true;
    }
}
