package com.legend.ffpmvp.main.personalcenter.model;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.legend.ffpmvp.common.bean.HomePlanBean;
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

public class FinishedTaskModelImpl implements ICommonModel {

    private RequestResult<HomePlanBean> result;
    private JSONArray jsonArray;
    private List<String> plan_list = new ArrayList<>();

    @Override
    public void getData(int pageIndex, RequestResult result) {
        this.result = result;
        new FinishedAsyncTask().execute(ApiUtils.PARTPLANS+"?search=1");
    }

    @Override
    public void getCircleBanner(RequestResult result) {

    }

    @Override
    public int count() {
        return 0;
    }

    class FinishedAsyncTask extends AsyncTask<String,Void,List<HomePlanBean>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            result.onStart();
        }

        @Override
        protected List<HomePlanBean> doInBackground(String... strings) {
            IRequest request = new RequestImpl(strings[0]);
            SharedPreferenceUtils shared =
                    new SharedPreferenceUtils(MyApplication.getInstance(),SharedPreferenceUtils.COOKIE);
            request.setHeader("Authorization","JWT "+shared.get(SharedPreferenceUtils.ACCOUNTJWT));
            IHttpClient mHttpclient = new OkHttpClientImpl();
            IResponse response = mHttpclient.get(request);
            String data = response.getData().toString();
            // 添加记录之前先进行清除
            plan_list.clear();
            try {
                jsonArray = new JSONArray(data);
                for (int i=0;i < jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String plan = jsonObject.getString("plan" );
                    plan_list.add(plan);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            List<HomePlanBean> homePlanBeans =
                    gson.fromJson(plan_list.toString(),new TypeToken<List<HomePlanBean>>(){}.getType());
            return homePlanBeans;
        }

        @Override
        protected void onPostExecute(List<HomePlanBean> homePlanBeans) {
            super.onPostExecute(homePlanBeans);
            if (homePlanBeans.size() > 0) {
                result.onSuccess(homePlanBeans);
            }
        }
    }
}
