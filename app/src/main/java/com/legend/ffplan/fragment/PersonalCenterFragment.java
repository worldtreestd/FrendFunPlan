package com.legend.ffplan.fragment;

import android.annotation.SuppressLint;
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

import com.legend.ffplan.R;
import com.legend.ffplan.common.adapter.PersonalCenterFragmentAdapter;
import com.legend.ffplan.common.viewimplement.IPersonalCenter;

/**
 * @author Legend
 * @data by on 2017/11/30.
 * @description 个人中心fragment
 */

public class PersonalCenterFragment extends Fragment implements IPersonalCenter{

    private View mView;
    private ViewPager mViewPager;
    private PersonalCenterFragmentAdapter adapter;
    private LinearLayout mycircle,backlog,finishedtask;
    private TextView logout;
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
                mycircle.setBackground(getResources().getDrawable(R.color.light_blue));
                backlog.setBackground(getResources().getDrawable(R.color.unselected));
                finishedtask.setBackground(getResources().getDrawable(R.color.unselected));
                break;
            case 1:
                backlog.setBackground(getResources().getDrawable(R.color.light_blue));
                mycircle.setBackground(getResources().getDrawable(R.color.unselected));
                finishedtask.setBackground(getResources().getDrawable(R.color.unselected));
                break;
            case 2:
                finishedtask.setBackground(getResources().getDrawable(R.color.light_blue));
                backlog.setBackground(getResources().getDrawable(R.color.unselected));
                mycircle.setBackground(getResources().getDrawable(R.color.unselected));
                break;
            default:
                break;
        }
    }
}
