package com.legend.ffpmvp.main.personalcenter.presenter;

import android.os.Handler;
import android.os.Message;

import com.legend.ffpmvp.common.bean.HomePlanBean;
import com.legend.ffpmvp.main.model.ICommonModel;
import com.legend.ffpmvp.main.personalcenter.model.BackLogAdapter;
import com.legend.ffpmvp.main.personalcenter.model.IBackLogModel;
import com.legend.ffpmvp.main.personalcenter.view.IBackLogView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.legend.ffpmvp.common.bean.Status.DELETE_OK;
import static com.legend.ffpmvp.common.bean.Status.NO_EXIT;
import static com.legend.ffpmvp.common.bean.Status.NO_PERMISSION;
import static com.legend.ffpmvp.common.bean.Status.UNKNOW_ERROR;
import static com.legend.ffpmvp.common.bean.Status.UPDATE_OK;

/**
 * @author Legend
 * @data by on 2018/1/30.
 * @description
 */

public class BackLogPresenter implements IBackLogPresenter {

    public static BackLogAdapter adapter;
    private IBackLogModel backLogModel;
    private IBackLogView backLogView;
    private List<HomePlanBean> homePlanBeanLists;

    public BackLogPresenter(IBackLogModel backLogModel,IBackLogView backLogView) {
        this.backLogModel = backLogModel;
        this.backLogView = backLogView;
        backLogModel.setHandler(new BackLogHandler(this));
        homePlanBeanLists = new ArrayList<>();
        adapter = new BackLogAdapter(homePlanBeanLists);
    }


    private static class BackLogHandler extends Handler {

        WeakReference<BackLogPresenter> reference;
        public BackLogHandler(BackLogPresenter context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BackLogPresenter presenter = reference.get();
            if (presenter == null) {
                return;
            }
            switch (msg.what) {
                case DELETE_OK:
                    presenter.backLogView.operateResult(DELETE_OK);
                    break;
                case UPDATE_OK:
                    presenter.backLogView.operateResult(UPDATE_OK);
                    break;
                case NO_EXIT:
                    presenter.backLogView.operateResult(NO_EXIT);
                    break;
                case NO_PERMISSION:
                    presenter.backLogView.operateResult(NO_PERMISSION);
                    break;
                case UNKNOW_ERROR:
                    presenter.backLogView.operateResult(UNKNOW_ERROR);
                    break;
                default:
                    break;
            }
        }
    }
    @Override
    public void onRefresh() {
        backLogModel.getData(0, new ICommonModel.RequestResult() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List beanList) {
                homePlanBeanLists.clear();
                homePlanBeanLists.addAll(beanList);
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onEnd() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void deletePlan(int position) {
        backLogModel.deletePlan(position);
    }

    @Override
    public void updatePlan(int position, int id) {
        backLogModel.updatePlan(position,id);
    }

    @Override
    public int getListSize() {
        return homePlanBeanLists.size();
    }
}
