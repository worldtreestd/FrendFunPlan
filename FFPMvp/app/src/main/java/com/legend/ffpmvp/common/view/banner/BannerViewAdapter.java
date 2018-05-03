package com.legend.ffpmvp.common.view.banner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.legend.ffpmvp.R;
import com.legend.ffpmvp.circle.view.CircleContentView;
import com.legend.ffpmvp.circle.view.CircleConversationView;
import com.legend.ffpmvp.common.bean.HomeCircleBean;
import com.legend.ffpmvp.main.view.MainActivity;

import java.util.List;

/**
 * @author Legend
 * @data by on 2018/2/6.
 * @description
 */

public class BannerViewAdapter extends BannerViewBaseAdapter {

    private List<HomeCircleBean> mBeansList;
    private Context mContext;

    public BannerViewAdapter(List<HomeCircleBean> circleBannerBeans) {
        this.mBeansList = circleBannerBeans;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        final AppCompatImageView imageView;
        TextView title;

        if (mContext == null) {
            mContext = container.getContext();
        }
        View mView = LayoutInflater.from(mContext).inflate(R.layout.banner_item_layout,null);

        HomeCircleBean bean = mBeansList.get(position);
        imageView = mView.findViewById(R.id.image);
        title = mView.findViewById(R.id.banner_title);
        Glide.with(mContext).load(bean.getImage())
                .skipMemoryCache(true)
                .placeholder(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .error(R.drawable.worldtreestd)
                .into(imageView);
        title.setText(bean.getName());
        final Intent intent = createIntent(mContext,bean);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair<View, String> imagePair = Pair.create((View)imageView, "circle_image");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) mContext, imagePair);
                mContext.startActivity(intent, options.toBundle());
            }
        });
        return mView;
    }

    @Override
    public int getSize() {
        return mBeansList.size();
    }

    public static Intent createIntent(Context context, HomeCircleBean bean) {
        final Intent intent = new Intent(context, CircleContentView.class);
        intent.putExtra(CircleContentView.CIRCLE_NAME,bean.getName());
        intent.putExtra(CircleContentView.CIRCLE_IMAGE_URL,bean.getImage());
        intent.putExtra(CircleContentView.CIRCLE_INTRODUCE,bean.getDesc());
        intent.putExtra(CircleContentView.CIRCLE_ADDRESS,bean.getAddress());
        intent.putExtra(CircleContentView.CIRCLE_CREATED,bean.getUser());
        intent.putExtra(CircleContentView.CIRCLE_ID,bean.getId());
        intent.putExtra(CircleContentView.CIRCLE_ADD_TIME,bean.getAdd_time());
        intent.putExtra(CircleConversationView.USER_NAME, MainActivity.user_nick_name);
        intent.putExtra(CircleConversationView.USER_IMAGE_URL,MainActivity.user_image_url);
        intent.putExtra(CircleConversationView.USER_ID,MainActivity.openId);
        return intent;
    }
}
