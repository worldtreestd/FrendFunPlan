package com.legend.ffpmvp.main.model;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author Legend
 * @data by on 2018/1/29.
 * @description
 */

public class HomePlanModelImpl implements ICommonModel {

    private static int count = 0;
    private JSONArray results = new JSONArray();
    private ICommonModel.RequestResult<HomePlanBean> result;
    private List<HomePlanBean> planBeanList;

    @Override
    public void getData(int pageIndex, RequestResult result) {
        this.result = result;
        new HomePlanAsyncTask().execute(ApiUtils.PLANS+"?page="+pageIndex);
    }

    @Override
    public void getCircleBanner(RequestResult result) {

    }

    @Override
    public int count() {
        return count;
    }

    class HomePlanAsyncTask extends AsyncTask<String,Void,List<HomePlanBean>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            result.onStart();
        }

        @Override
        protected List<HomePlanBean> doInBackground(String... strings) {
            IRequest request = new RequestImpl(strings[0]);
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse response = mHttpClient.get(request);
            String data = response.getData().toString();
            try {
                JSONObject jsonObject = new JSONObject(data);
                count = jsonObject.getInt("count");
                results = jsonObject.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            List<HomePlanBean> planBeanList =
                    gson.fromJson(results.toString(),new TypeToken<List<HomePlanBean>>(){}.getType());
            return planBeanList;
        }

        @Override
        protected void onPostExecute(List<HomePlanBean> homePlanBeans) {
            super.onPostExecute(homePlanBeans);
            if (homePlanBeans != null) {
                result.onSuccess(homePlanBeans);
            } else {
                result.onFailure();
            }
            result.onEnd();
        }
    }

}
