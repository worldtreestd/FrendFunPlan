package com.legend.ffpmvp.circle.model;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.legend.ffpmvp.circle.contract.CircleCommonContract;
import com.legend.ffpmvp.common.bean.HomePlanBean;
import com.legend.ffpmvp.common.http.IHttpClient;
import com.legend.ffpmvp.common.http.IRequest;
import com.legend.ffpmvp.common.http.IResponse;
import com.legend.ffpmvp.common.http.impl.RequestImpl;
import com.legend.ffpmvp.common.http.impl.OkHttpClientImpl;
import com.legend.ffpmvp.common.utils.ApiUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author Legend
 * @data by on 2018/1/30.
 * @description
 */

public class CirclePlanModelImpl implements CircleCommonContract.ICircleContentModel {

    private CircleCommonContract.RequestCallBack<HomePlanBean> callBack;
    private String plan_data;

    @Override
    public void getData(int id, CircleCommonContract.RequestCallBack callBack) {
        this.callBack = callBack;
        new CirclePlanAsyncTask().execute(ApiUtils.CIRCLES + id);
    }
    class CirclePlanAsyncTask extends AsyncTask<String,Void,List<HomePlanBean>> {

        @Override
        protected List<HomePlanBean> doInBackground(String... strings) {
            IRequest request = new RequestImpl(strings[0]);
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse response = mHttpClient.get(request);
            String circle_data = response.getData();
            try {
                JSONObject jsonObject = new JSONObject(circle_data);
                plan_data =
                        jsonObject.getString("plan_list");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            List<HomePlanBean> homePlanBeans =
                    gson.fromJson(plan_data,new TypeToken<List<HomePlanBean>>(){}.getType());
            return homePlanBeans;
        }

        @Override
        protected void onPostExecute(List<HomePlanBean> homePlanBeans) {
            super.onPostExecute(homePlanBeans);
            if (homePlanBeans != null) {
                callBack.onSuccess(homePlanBeans);
            } else {
                callBack.onFailure();
            }
        }
    }
}
