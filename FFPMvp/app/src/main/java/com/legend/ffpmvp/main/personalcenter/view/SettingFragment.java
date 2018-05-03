package com.legend.ffpmvp.main.personalcenter.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.bumptech.glide.Glide;
import com.legend.ffpmvp.R;
import com.legend.ffpmvp.main.view.AboutView;

/**
 * @author HP
 */
public class SettingFragment extends PreferenceFragmentCompat {

    private Preference mSettingAutoUpdate, mCheckUpdate, mAbout, mClearCache;

    public static final int CLEAR_GLIDE_CACHE_DONE = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case CLEAR_GLIDE_CACHE_DONE:
                    Snackbar.make(getActivity().getWindow().getDecorView(),"清除成功！", Snackbar.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_preference_fragment);
        mSettingAutoUpdate = findPreference("settingAutoUpdate");
        mCheckUpdate = findPreference("checkUpdate");
        mAbout = findPreference("about");
        mClearCache = findPreference("clearCache");
        mAbout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(getActivity(), AboutView.class));
                return false;
            }
        });
        mClearCache.setOnPreferenceClickListener(preference -> {
            new Thread(() -> {
                Glide.get(getContext()).clearDiskCache();
                Message msg = new Message();
                msg.what = CLEAR_GLIDE_CACHE_DONE;
                handler.sendMessage(msg);
            }).start();
            Glide.get(getContext()).clearMemory();
            return false;
        });

    }

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }
}
