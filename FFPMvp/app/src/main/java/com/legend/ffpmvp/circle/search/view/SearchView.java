package com.legend.ffpmvp.circle.search.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.legend.ffpmvp.R;
import com.legend.ffpmvp.circle.search.ISearchContract;
import com.legend.ffpmvp.circle.search.model.SearchModelImpl;
import com.legend.ffpmvp.circle.search.presenter.SearchPresenter;
import com.legend.ffpmvp.common.utils.ImageUtils;
import com.legend.ffpmvp.common.utils.ToastUtils;
import com.legend.ffpmvp.common.view.BaseActivity;

import java.lang.ref.WeakReference;
import java.util.Random;

import static com.legend.ffpmvp.circle.search.presenter.SearchPresenter.adapter;
import static com.legend.ffpmvp.circle.search.presenter.SearchPresenter.keyWordBeanLists;

/**
 * @author Legend
 * @data by on 2018/1/30.
 * @description
 */

public class SearchView extends BaseActivity implements ISearchContract.ISearchView {

    private Dialog dialog;
    private android.support.v7.widget.Toolbar toolbar;
    private android.support.v7.widget.SearchView searchView;
    private XRecyclerView mRecyclerView;
    private AppCompatTextView mHintText,keyword_tv,recent_tv;
    private ISearchContract.ISearchModel model;
    private ISearchContract.ISearchPresenter presenter;
    private FlexboxLayout flexboxLayout;

    @Override
    public Object setResourceLayout() {
        return R.layout.search_layout;
    }

    @SuppressLint({"ResourceAsColor", "NewApi"})
    @Override
    public void initView() {
        model = new SearchModelImpl();
        WeakReference<SearchView> reference = new WeakReference<>(this);
        presenter = new SearchPresenter(model,reference.get());
        mHintText = $(R.id.hint_text);
        mRecyclerView = $(R.id.mRecyclerView);
        recent_tv = $(R.id.recent_tv);
        mRecyclerView.setVisibility(View.GONE);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotateMultiple);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLoadingMoreEnabled(false);
        presenter.onSearch("");
        flexboxLayout = $(R.id.flex_layout);
    }

    /**
     *  加载关键字
     */
    private void initKeyWord() {
        recent_tv.setVisibility(View.VISIBLE);
        flexboxLayout.setVisibility(View.VISIBLE);
        // 移除所有keyword
        flexboxLayout.removeAllViews();
        for (int i=0;i < keyWordBeanLists.size();i++) {
//            final AppCompatTextView keyword_tv = new AppCompatTextView(this);
            keyword_tv = (AppCompatTextView) LayoutInflater.from(this)
                    .inflate(R.layout.search_keyword_item,null);
            FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(ImageUtils.dip2px(this,10), ImageUtils.dip2px(this,15), 6, 0);
            /**
             *  设置关键字随机颜色
             */
            Random random = new Random();
            Integer[] colors = new Integer[]{R.color.colorAccent,R.color.red,
                    R.color.colorPrimary,R.color.green};
            keyword_tv.setTextColor(getResources().getColor(colors[random.nextInt(colors.length)]));
            Log.d("cddcdsvfvfv",keyword_tv.getText().toString());
            keyword_tv.setText(keyWordBeanLists.get(i).getKeyword());
            keyword_tv.setLayoutParams(layoutParams);
            flexboxLayout.addView(keyword_tv);
            final int finalI = i;
            keyword_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("keywords keywocsrds",keyword_tv.getText().toString());
                    presenter.onSearch(keyWordBeanLists.get(finalI).getKeyword());
                }
            });
        }
    }

    @Override
    public void initListener() {
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                presenter.onSearch("");
                mRecyclerView.setVisibility(View.GONE);
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    @Override
    public boolean showHomeAsUp() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.onActionViewExpanded();
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("输入圈子的ID号或简介");
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.onSearch(query);
                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public void showLoading() {
        dialog = ToastUtils.createLoadingDialog(this,"正在努力..搜寻中~");
        flexboxLayout.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        dialog.show();
    }

    @Override
    public void showNoResult() {
        mRecyclerView.setVisibility(View.GONE);
        Toast.makeText(this, "搜寻未果，换个关键词再查找一下吧！", Toast.LENGTH_SHORT).show();
        initKeyWord();
    }

    @Override
    public void showKeyWord() {
        initKeyWord();
    }

    @Override
    public void hideLoading() {
        mHintText.setVisibility(View.GONE);
        recent_tv.setVisibility(View.INVISIBLE);
        flexboxLayout.setVisibility(View.INVISIBLE);
        mRecyclerView.refreshComplete();
        dialog.dismiss();
    }
}
