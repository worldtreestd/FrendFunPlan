package com.legend.ffpmvp.main.personalcenter.model;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.legend.ffpmvp.common.bean.HomeCircleBean;
import com.legend.ffpmvp.common.http.IHttpClient;
import com.legend.ffpmvp.common.http.IRequest;
import com.legend.ffpmvp.common.http.IResponse;
import com.legend.ffpmvp.common.http.impl.RequestImpl;
import com.legend.ffpmvp.common.http.impl.OkHttpClientImpl;
import com.legend.ffpmvp.common.utils.ApiUtils;
import com.legend.ffpmvp.common.utils.MyApplication;
import com.legend.ffpmvp.common.utils.SharedPreferenceUtils;
import com.legend.ffpmvp.main.model.ICommonModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Legend
 * @data by on 2018/1/29.
 * @description
 */

public class MyCircleModelImpl implements ICommonModel {


    private JSONArray jsonArray1;
    private List<String> circle_list = new ArrayList<>();
    private RequestResult<HomeCircleBean> result;

    @Override
    public void getData(int pageIndex, RequestResult result) {
        this.result = result;
        new MyCircleAsyncTask().execute(ApiUtils.PARTCIRCLE);
    }

    @Override
    public void getCircleBanner(RequestResult result) {

    }

    @Override
    public int count() {
        return 0;
    }

    class MyCircleAsyncTask extends AsyncTask<String,Void,List<HomeCircleBean>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            result.onStart();
        }

        @Override
        protected List<HomeCircleBean> doInBackground(String... strings) {
            IRequest request = new RequestImpl(strings[0]);
            SharedPreferenceUtils shared =
                    new SharedPreferenceUtils(MyApplication.getInstance(), SharedPreferenceUtils.COOKIE);
            request.setHeader("Authorization", "JWT " + shared.get(SharedPreferenceUtils.ACCOUNTJWT));

            IHttpClient mHttpclient = new OkHttpClientImpl();
            IResponse response = mHttpclient.get(request);
            String data = response.getData().toString();
            // 添加记录之前先进行清除
            circle_list.clear();
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
            if (homeCircleBeans != null) {
                result.onSuccess(homeCircleBeans);
            } else {
                result.onFailure();
            }
            result.onEnd();
        }
    }
}
