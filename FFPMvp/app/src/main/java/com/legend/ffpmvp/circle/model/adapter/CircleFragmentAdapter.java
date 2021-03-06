package com.legend.ffpmvp.circle.model.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.legend.ffpmvp.circle.view.CircleConversationView;
import com.legend.ffpmvp.circle.view.CircleInfoView;
import com.legend.ffpmvp.circle.view.CirclePlanView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Legend
 * @data by on 2017/12/9.
 * @description 圈子详情Fragment适配器
 */

public class CircleFragmentAdapter extends FragmentPagerAdapter{

    private List<Fragment> mFragments;
    private String titles[] = new String[]{"圈子信息","圈内计划","圈友会话"};

    public CircleFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        mFragments = new ArrayList<>();
        CircleInfoView circleInfoFragment = new CircleInfoView();
        CirclePlanView circlePlanFragment = new CirclePlanView();
        CircleConversationView circleConversationFragment = new CircleConversationView();
        mFragments.add(circleInfoFragment);
        mFragments.add(circlePlanFragment);
        mFragments.add(circleConversationFragment);
    }
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
