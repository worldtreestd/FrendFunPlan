package com.legend.ffpmvp.main.personalcenter.model;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.legend.ffpmvp.common.bean.HomePlanBean;
import com.legend.ffpmvp.common.http.IHttpClient;
import com.legend.ffpmvp.common.http.IRequest;
import com.legend.ffpmvp.common.http.IResponse;
import com.legend.ffpmvp.common.http.impl.RequestImpl;
import com.legend.ffpmvp.common.http.impl.OkHttpClientImpl;
import com.legend.ffpmvp.common.utils.ApiUtils;
import com.legend.ffpmvp.common.utils.DateUtils;
import com.legend.ffpmvp.common.utils.MyApplication;
import com.legend.ffpmvp.common.utils.SharedPreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class BackLogModelImpl implements IBackLogModel {

    private RequestResult<HomePlanBean> result;
    private Handler handler;
    private JSONArray jsonArray;
    private List<String> plan_list = new ArrayList<>();


    @Override
    public void getData(int pageIndex, RequestResult result) {
        this.result = result;
        new BackLogAsyncTask().execute(ApiUtils.PARTPLANS+"?search=0");
    }

    @Override
    public void getCircleBanner(RequestResult result) {

    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public void deletePlan(int position) {
        new DeletePlanAsyncTask().execute(ApiUtils.PARTPLANS+position+"/");
    }

    @Override
    public void updatePlan(int position,int id) {
        new UpdatePlanAsyncTask().execute(ApiUtils.PLANS+position+"/", String.valueOf(id));

    }

    @Override
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    class BackLogAsyncTask extends AsyncTask<String,Void,List<HomePlanBean>> {

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
            Log.d("scscsczcddsdscdsdcdx",homePlanBeans.toString());
            if (homePlanBeans != null) {
                result.onSuccess(homePlanBeans);
            } else {
                result.onFailure();
            }
            result.onEnd();
        }
    }

    class DeletePlanAsyncTask extends AsyncTask<String,Void,IResponse> {

        @Override
        protected IResponse doInBackground(String... strings) {
            IRequest request = new RequestImpl(strings[0]);
            SharedPreferenceUtils shared = new SharedPreferenceUtils(MyApplication.getInstance(),SharedPreferenceUtils.COOKIE);
            request.setHeader("Authorization","JWT "+shared.get(SharedPreferenceUtils.ACCOUNTJWT));
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse response = mHttpClient.delete(request);
            return response;
        }

        @Override
        protected void onPostExecute(IResponse iResponse) {
            super.onPostExecute(iResponse);
            if (iResponse.getCode() == DELETE_OK) {
                handler.sendEmptyMessage(DELETE_OK);
            } else if (iResponse.getCode() == NO_EXIT) {
                handler.sendEmptyMessage(NO_EXIT);
            } else {
                handler.sendEmptyMessage(UNKNOW_ERROR);
            }
        }
    }
    class UpdatePlanAsyncTask extends AsyncTask<String,Void,IResponse> {

        @Override
        protected IResponse doInBackground(String... strings) {
            IRequest request = new RequestImpl(strings[0]);
            SharedPreferenceUtils shared = new SharedPreferenceUtils(MyApplication.getInstance(),SharedPreferenceUtils.COOKIE);
            request.setHeader("Authorization","JWT "+shared.get(SharedPreferenceUtils.ACCOUNTJWT));
            request.setBody("is_finished",true);
            request.setBody("add_time", DateUtils.getDate().replace("/","-"));
            request.setBody("from_circle",strings[1]);
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse response = mHttpClient.put(request);
            return response;
        }
        @Override
        protected void onPostExecute(IResponse iResponse) {
            super.onPostExecute(iResponse);
            if (iResponse.getCode() == UPDATE_OK) {
                handler.sendEmptyMessage(UPDATE_OK);
            } else if (iResponse.getCode() == NO_PERMISSION) {
                handler.sendEmptyMessage(NO_PERMISSION);
            } else {
                Log.d("Error -------》",iResponse.getCode() + iResponse.getData());
                handler.sendEmptyMessage(UNKNOW_ERROR);
            }
        }
    }
}
