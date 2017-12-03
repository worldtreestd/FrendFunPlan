package com.legend.ffplan.common.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.legend.ffplan.R;

/**
 * @author Legend
 * @data by on 2017/11/30.
 * @description 继承SwipeRefreshLayout自定义支持上拉加载的view
 */

public class SwipeRefreshView extends SwipeRefreshLayout{

    private static final String TAG = SwipeRefreshView.class.getSimpleName();
    private final int mScaledTouchSlop;
    private final View mFooterView;
    private RecyclerView mRecyclerView;
    private OnLoadMoreListener mListener;
    private boolean isLoading;
    private int mItemCount;
    private LinearLayoutManager manager;


    public SwipeRefreshView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        // 填充底部加载布局
        mFooterView = View.inflate(context, R.layout.foot_view,null);
        // 控件移动的最小距离 手移动的距离大于这个距离才能拖动控件
        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        manager = new LinearLayoutManager(mRecyclerView.getContext());
        isLoading = false;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mRecyclerView == null) {
            if (getChildCount() > 0) {
                if (getChildAt(0) instanceof RecyclerView) {
                    mRecyclerView = (RecyclerView)getChildAt(0);
                    // 设置滑动监听
                    setRecyclerViewOnScroll();
                }
            }
        }
    }
    private float mDownY,mUpY;

    /**
     *  处理触摸事件
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch(ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 移动的起点
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 移动过程中判断能否继续加载item
                if (canLoadMore()) {
                    loadData();
                }
                break;
            case MotionEvent.ACTION_UP:
                // 移动的终点
                mUpY = getY();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     *  判断能否满足加载条件
     * @return
     */
    private boolean canLoadMore() {
        // 是上拉状态
        boolean condition1 = (mDownY - mUpY) >= mScaledTouchSlop;
        if (condition1) {
            Log.d(TAG,"----------->上拉状态");
        }
        boolean condition2 = false;
        if (mRecyclerView != null && mRecyclerView.getAdapter() != null) {
            if (mItemCount > 0) {
                if (mRecyclerView.getAdapter().getItemCount() < mItemCount) {
                    // 第一页未满 禁止下拉
                    condition2 = false;
                } else {
                    condition2 = manager.findLastVisibleItemPosition() == (mRecyclerView.getAdapter().getItemCount()-1);
                }
            } else {
                condition2 = manager.findLastVisibleItemPosition() == (mRecyclerView.getAdapter().getItemCount()-1) ;
            }
        }

        // 不是正在加载状态
        boolean condition3 = !isLoading;
        return condition1 && condition2 && condition3;
    }

    /**
     *  处理加载数据
     */
    private void loadData() {
        if (mRecyclerView != null) {
            setLoading(true);
            mListener.onLoadMore();
        }
    }

    /**
     *  设置加载状态
     * @param loading
     */
    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
            mRecyclerView.addView(mFooterView);
        } else {
            mRecyclerView.removeView(mFooterView);
            // 重置滑动的坐标
            mDownY = 0;
            mUpY = 0;
        }
    }

    /**
     *  设置RecyclerView的滑动监听
     */
    private void setRecyclerViewOnScroll() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (canLoadMore()) {
                    loadData();
                }
            }
        });
    }
    /**
     *  上拉加载的接口回调
     */
    public interface OnLoadMoreListener {
        void onLoadMore();
    }
    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mListener = listener;
    }
}
