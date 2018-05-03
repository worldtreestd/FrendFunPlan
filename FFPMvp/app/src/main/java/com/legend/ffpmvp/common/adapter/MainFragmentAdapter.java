package com.legend.ffpmvp.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.legend.ffpmvp.main.view.HomeCircleView;
import com.legend.ffpmvp.main.view.HomePlanView;
import com.legend.ffpmvp.main.view.PersonalCenterView;

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
        Fragment homeCircleFragment = new HomeCircleView();
        Fragment homePlanFragment = new HomePlanView();
        Fragment personalCenterFragment = new PersonalCenterView();
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
