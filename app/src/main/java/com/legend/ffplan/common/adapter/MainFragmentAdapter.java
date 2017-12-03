package com.legend.ffplan.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.legend.ffplan.fragment.HomeFragment;
import com.legend.ffplan.fragment.JoinPlanFragment;
import com.legend.ffplan.fragment.PersonalCenterFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Legend
 * @data by on 2017/11/30.
 * @description 主Fragment适配器
 */

public class MainFragmentAdapter extends FragmentPagerAdapter{

    private List<Fragment> mFragments ;

    public MainFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        mFragments = new ArrayList<>();
        Fragment homeFragment = new HomeFragment();
        Fragment joinPlanFragment = new JoinPlanFragment();
        PersonalCenterFragment personalCenterFragment = new PersonalCenterFragment();
        mFragments.add(homeFragment);
        mFragments.add(joinPlanFragment);
        mFragments.add(personalCenterFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
