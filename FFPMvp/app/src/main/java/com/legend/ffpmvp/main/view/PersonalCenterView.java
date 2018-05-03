package com.legend.ffpmvp.main.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.internal.BottomNavigationItemView;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.legend.ffpmvp.GuideAnimation.GuidePageActivity;
import com.legend.ffpmvp.R;
import com.legend.ffpmvp.common.utils.MyApplication;
import com.legend.ffpmvp.common.utils.SharedPreferenceUtils;
import com.legend.ffpmvp.common.utils.ToastUtils;
import com.legend.ffpmvp.common.view.BaseFragment;
import com.legend.ffpmvp.common.view.CircleImageView;
import com.legend.ffpmvp.main.model.ICommonModel;
import com.legend.ffpmvp.main.personalcenter.model.BackLogModelImpl;
import com.legend.ffpmvp.main.personalcenter.view.BackLogActivity;
import com.legend.ffpmvp.main.personalcenter.view.FinishedTaskActivity;
import com.legend.ffpmvp.main.personalcenter.view.MyCircleActivity;
import com.legend.ffpmvp.main.personalcenter.view.SettingActivity;

import java.io.File;
import java.util.List;

import static com.legend.ffpmvp.GuideAnimation.GuidePageActivity.isFirstLogin;
import static com.legend.ffpmvp.main.view.MainActivity.user_image_url;
import static com.legend.ffpmvp.main.view.MainActivity.user_nick_name;

/**
 * @author Legend
 * @data by on 2017/11/30.
 * @description 个人中心fragment
 */

public class PersonalCenterView extends BaseFragment {

    private View mView;
    private AppCompatButton logout, my_circle_btn, backlog_btn, finished_task_btn,setting_btn;
    private CircleImageView user_image;
    private TextView user_name;
    private SharedPreferenceUtils shared;
    private View badge;
    private TextView msg_count;
    private BackLogModelImpl backLogModel;

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
        logout = $(R.id.logout);
        shared = new SharedPreferenceUtils(MyApplication.getInstance(), SharedPreferenceUtils.COOKIE);
        if (TextUtils.isEmpty(shared.get(user_image_url))) {
            shared.save(user_image_url,user_nick_name);
        }
        user_image = $(R.id.user_image);
        user_name = $(R.id.nick_name);
        my_circle_btn = $(R.id.my_circle_tv);
        backlog_btn = $(R.id.backlog_tv);
        finished_task_btn = $(R.id.finished_task_tv);
        setting_btn = $(R.id.setting_tv);

        user_name.setText(user_nick_name+"");
        // 获取bottom的圆标
        BottomNavigationItemView itemView = getActivity().findViewById(R.id.navigation_notifications);
        badge = LayoutInflater.from(mView.getContext()).inflate(R.layout.bottom_badge, itemView,false);
        itemView.addView(badge);
        msg_count = badge.findViewById(R.id.msg_count);
        badge.setVisibility(View.GONE);

        Log.d("imageimageimageimhae",user_image_url+ "-------"+shared.get(user_image_url));
        Glide.with(MyApplication.getInstance())
                .load(TextUtils.isEmpty(user_image_url)?shared.get(user_image_url):user_image_url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.worldtreestd)
                .into(user_image);
    }


    @Override
    public void initListener() {
        logout.setOnClickListener(view -> {

            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mView.getContext());
            builder.setTitle("温馨提示");
            builder.setMessage("您确定要退出登录吗？");
            builder.setPositiveButton("确定", (dialog, which) -> {
                GuidePageActivity.mTencent.logout(getContext());
                ToastUtils.showToast(getContext(),"感谢您的使用");
//                        synchronized (this) {
//                            //清除缓存
//                            deleteFilesByDirectory(new File("/data/data/"
//                                    + MyApplication.getInstance().getPackageName() + "/shared_prefs"));
//                        }
                isFirstLogin = true;
                startActivity(new Intent(getActivity(),GuidePageActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
            });
            builder.show();
        });
        my_circle_btn.setOnClickListener(v -> startActivity(new Intent(getActivity(), MyCircleActivity.class)));
        backlog_btn.setOnClickListener(v -> startActivity(new Intent(getActivity(), BackLogActivity.class)));
        finished_task_btn.setOnClickListener(v -> startActivity(new Intent(getActivity(), FinishedTaskActivity.class)));
        setting_btn.setOnClickListener(v -> startActivity(new Intent(getActivity(), SettingActivity.class)));
    }

    @Override
    public void refreshData() {
        if (backLogModel == null) {
            backLogModel = new BackLogModelImpl();
            backLogModel.getData(0, new ICommonModel.RequestResult() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(List beanList) {
                    if (beanList.size() > 0) {
                        msg_count.setText(String.valueOf(beanList.size()));
                        badge.setVisibility(View.VISIBLE);
                    } else {
                        badge.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure() {

                }

                @Override
                public void onEnd() {
                }
            });
        }
    }
}
