package com.legend.ffplan.fragment.main;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.legend.ffplan.GuideAnimation.GuidePageActivity;
import com.legend.ffplan.MainActivity;
import com.legend.ffplan.R;
import com.legend.ffplan.common.adapter.PersonalCenterFragmentAdapter;
import com.legend.ffplan.common.util.SharedPreferenceUtils;
import com.legend.ffplan.common.util.ToastUtils;
import com.legend.ffplan.fragment.BaseFragment;
import com.thinkcool.circletextimageview.CircleTextImageView;

/**
 * @author Legend
 * @data by on 2017/11/30.
 * @description 个人中心fragment
 */

public class PersonalCenterFragment extends BaseFragment{

    private View mView;
    private ViewPager mViewPager;
    private PersonalCenterFragmentAdapter adapter;
    private TabLayout tabLayout;
    private TextView logout;
    private CircleTextImageView user_image;
    private TextView user_name;
    private Intent intent;

    @Override
    public int setResourceLayoutId() {
        return R.layout.personalcenter_layout;
    }

    @Override
    public int setRecyclerViewId() {
        return 0;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void initView() {
        mView = getmView();
        adapter = new PersonalCenterFragmentAdapter(getChildFragmentManager());

        logout = $(R.id.logout);
        logout.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        String user_image_url = MainActivity.user_image_url;
        String user_nick_name = MainActivity.user_nick_name;

        user_image = $(R.id.user_image);
        user_name = $(R.id.nick_name);

        user_name.setText(user_nick_name);
        Glide.with(getActivity().getApplicationContext())
                .load(user_image_url).error(R.drawable.loading_01).into(user_image);

        tabLayout = $(R.id.tab_layout);
        // 设置分割线
        LinearLayout linearLayout = (LinearLayout)tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(mView.getContext(),R.drawable.divider));
        linearLayout.setDividerPadding(dip2px(12));

        mViewPager = $(R.id.center_viewpager);
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager,true);
    }

    @Override
    public void initListener() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mView.getContext());
                builder.setTitle("温馨提示");
                builder.setMessage("您确定要退出登录吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GuidePageActivity.mTencent.logout(getContext());
                        ToastUtils.showToast(mView.getContext(),"感谢您的使用");
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences(SharedPreferenceUtils.OPENID,getContext().MODE_PRIVATE);
                        sharedPreferences.edit().clear().commit();
                        getActivity().finish();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public void refreshData() {

    }

    //像素单位转换
    public int dip2px(int dip) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }
}
