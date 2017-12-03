package com.legend.ffplan.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.legend.ffplan.fragment.personalcenter.BackLogFragment;
import com.legend.ffplan.fragment.personalcenter.FinishedTaskFragment;
import com.legend.ffplan.fragment.personalcenter.MyCircleFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Legend
 * @data by on 2017/12/3.
 * @description 个人中心fragment适配器
 */

public class PersonalCenterFragmentAdapter extends FragmentPagerAdapter  {

    private List<Fragment> mFragments;

    public PersonalCenterFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        mFragments = new ArrayList<Fragment>();
        MyCircleFragment myCircleFragment = new MyCircleFragment();
        BackLogFragment backLogFragment = new BackLogFragment();
        FinishedTaskFragment finishedTaskFragment = new FinishedTaskFragment();
        mFragments.add(myCircleFragment);
        mFragments.add(backLogFragment);
        mFragments.add(finishedTaskFragment);
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
