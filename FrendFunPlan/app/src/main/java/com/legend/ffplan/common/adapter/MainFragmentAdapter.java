package com.legend.ffplan.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.legend.ffplan.fragment.main.HomePlanFragment;
import com.legend.ffplan.fragment.main.HomeCircleFragment;
import com.legend.ffplan.fragment.main.PersonalCenterFragment;

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
        Fragment homeCircleFragment = new HomeCircleFragment();
        Fragment homePlanFragment = new HomePlanFragment();
        PersonalCenterFragment personalCenterFragment = new PersonalCenterFragment();
        mFragments.add(homeCircleFragment);
        mFragments.add(homePlanFragment);
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
