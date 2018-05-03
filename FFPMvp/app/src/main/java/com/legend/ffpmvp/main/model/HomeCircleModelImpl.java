package com.legend.ffpmvp.main.model;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.legend.ffpmvp.common.bean.HomeCircleBean;
import com.legend.ffpmvp.common.http.IHttpClient;
import com.legend.ffpmvp.common.http.IRequest;
import com.legend.ffpmvp.common.http.IResponse;
import com.legend.ffpmvp.common.http.impl.OkHttpClientImpl;
import com.legend.ffpmvp.common.http.impl.RequestImpl;
import com.legend.ffpmvp.common.utils.ApiUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.legend.ffpmvp.common.bean.Status.UPDATE_OK;

/**
 * @author Legend
 * @data by on 2018/1/27.
 * @description
 */

public class HomeCircleModelImpl implements ICommonModel {

    private static int count = 0;
    private JSONArray results;
    private ICommonModel.RequestResult<HomeCircleBean> result;
    private RequestResult<HomeCircleBean> bannerResult;
    private List<HomeCircleBean> circleBeanList;
    private List<String> circle_list = new ArrayList<>();
    private JSONArray jsonArray1;

    @Override
    public void getData(int pageIndex, RequestResult result) {
        this.result = result;
        synchronized (this) {
            new HomeCircleAsyncTask().execute(ApiUtils.CIRCLES+"?page="+pageIndex);
        }
    }

    @Override
    public void getCircleBanner(RequestResult result) {
        this.bannerResult = result;
        synchronized (this){
            new CircleBannerAsyncTask().execute(ApiUtils.CIRCLE_BANNER);
        }
    }


    @Override
    public int count() {
        return count;
    }

    /**
     *  异步加载Json数据
     */
    class HomeCircleAsyncTask extends AsyncTask<String, Void, List<HomeCircleBean>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            result.onStart();
        }

        @Override
        protected List<HomeCircleBean> doInBackground(String... strings) {
            IRequest request = new RequestImpl(strings[0]);
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse response = mHttpClient.get(request);
            if (response.getCode() == UPDATE_OK) {
                String data = response.getData().toString();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    count = jsonObject.getInt("count");
                    results = jsonObject.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                circleBeanList =
                        gson.fromJson(results.toString(), new TypeToken<List<HomeCircleBean>>() {
                        }.getType());
            }

            return circleBeanList;
        }

        @Override
        protected void onPostExecute(List<HomeCircleBean> homeCircleBeans) {
            super.onPostExecute(homeCircleBeans);
            if (homeCircleBeans != null) {
                result.onSuccess(homeCircleBeans);
            } else {
                result.onFailure();
            }
            result.onEnd();
        }
    }

    class CircleBannerAsyncTask extends AsyncTask<String,Void,List<HomeCircleBean>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bannerResult.onStart();
        }

        @Override
        protected List<HomeCircleBean> doInBackground(String... strings) {
            // 添加记录之前先进行清除
            circle_list.clear();
            IRequest request = new RequestImpl(strings[0]);
            IHttpClient mHttpclient = new OkHttpClientImpl();
            IResponse response = mHttpclient.get(request);
            String data = response.getData().toString();
            try {
                jsonArray1 = new JSONArray(data);
                for (int i=0;i < jsonArray1.length();i++) {
                    JSONObject jsonObject = jsonArray1.getJSONObject(i);
                    String circle = jsonObject.getString("circle");

                    circle_list.add(circle);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            List<HomeCircleBean> homeCircleBeans =
                    gson.fromJson(circle_list.toString(), new TypeToken<List<HomeCircleBean>>() {
                    }.getType());
            return homeCircleBeans;
        }

        @Override
        protected void onPostExecute(List<HomeCircleBean> homeCircleBeans) {
            super.onPostExecute(homeCircleBeans);
            Log.d("cdcdscbannnnnnnnnnner",homeCircleBeans.toString());
            if (homeCircleBeans != null) {
                bannerResult.onSuccess(homeCircleBeans);
            } else {
                bannerResult.onFailure();
            }
            bannerResult.onEnd();
        }
    }
}