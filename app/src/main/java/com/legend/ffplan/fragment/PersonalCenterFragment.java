package com.legend.ffplan.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.legend.ffplan.GuideAnimation.GuidePageActivity;
import com.legend.ffplan.R;
import com.legend.ffplan.common.adapter.PersonalCenterFragmentAdapter;
import com.legend.ffplan.common.util.SharedPreferenceUtils;
import com.legend.ffplan.common.util.ToastUtils;
import com.legend.ffplan.common.viewimplement.IPersonalCenter;
import com.legend.ffplan.fragment.circlecenter.CircleConversationFragment;
import com.thinkcool.circletextimageview.CircleTextImageView;

/**
 * @author Legend
 * @data by on 2017/11/30.
 * @description 个人中心fragment
 */

public class PersonalCenterFragment extends Fragment implements IPersonalCenter{

    private View mView;
    private ViewPager mViewPager;
    // 定义为静态变量 传递到圈子页面
    public static String user_image_url = "http://q1.qlogo.cn/g?b=qq&nk=2414605975&s=40";
    public static String user_nick_name = "Legend";
    private PersonalCenterFragmentAdapter adapter;
    private LinearLayout mycircle,backlog,finishedtask;
    private TextView logout;
    private CircleTextImageView user_image;
    private TextView user_name;
    private Intent intent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mView == null) {
            mView = inflater.inflate(R.layout.personalcenter_layout,container,false);
        }
        initView();
        initListener();
        return mView;
    }
    @SuppressLint("WrongViewCast")
    @Override
    public void initView() {
        adapter = new PersonalCenterFragmentAdapter(getChildFragmentManager());
        mViewPager = mView.findViewById(R.id.center_viewpager);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(adapter);
        logout = mView.findViewById(R.id.logout);
        logout.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        intent = getActivity().getIntent();
        user_image_url = intent.getStringExtra(CircleConversationFragment.USER_IMAGE_URL);
        user_nick_name = intent.getStringExtra(CircleConversationFragment.USER_NAME);

        user_image = mView.findViewById(R.id.user_image);
        user_name = mView.findViewById(R.id.nick_name);

        user_name.setText(user_nick_name);
        Glide.with(getActivity().getApplicationContext())
                .load(user_image_url).error(R.drawable.loading_01).into(user_image);
        mycircle = mView.findViewById(R.id.task_1);
        backlog = mView.findViewById(R.id.task_2);
        finishedtask = mView.findViewById(R.id.task_3);
        selectedItem(0);
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
                selectedItem(currentItem);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mycircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedItem(0);
            }
        });
        backlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedItem(1);
            }
        });
        finishedtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedItem(2);
            }
        });
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
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPreferenceUtils.OPENID,getActivity().MODE_PRIVATE);
                        sharedPreferences.edit().clear().commit();
                        getActivity().finish();
                    }
                });
                builder.show();
            }
        });
    }

    /**
     * 设置当前被选中的fragment
     * @param position
     */
    @Override
    public void selectedItem(int position) {
        mViewPager.setCurrentItem(position);
        switch (position) {
            case 0:
                mycircle.setBackground(getResources().getDrawable(R.color.green));
                backlog.setBackground(getResources().getDrawable(R.color.transparency));
                finishedtask.setBackground(getResources().getDrawable(R.color.transparency));
                break;
            case 1:
                backlog.setBackground(getResources().getDrawable(R.color.green));
                mycircle.setBackground(getResources().getDrawable(R.color.transparency));
                finishedtask.setBackground(getResources().getDrawable(R.color.transparency));
                break;
            case 2:
                finishedtask.setBackground(getResources().getDrawable(R.color.green));
                backlog.setBackground(getResources().getDrawable(R.color.transparency));
                mycircle.setBackground(getResources().getDrawable(R.color.transparency));
                break;
            default:
                break;
        }
    }
}
