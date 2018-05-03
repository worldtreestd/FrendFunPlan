package com.legend.ffpmvp.plan.model;

import android.os.AsyncTask;
import android.util.Log;

import com.legend.ffpmvp.circle.contract.CreateContract;
import com.legend.ffpmvp.common.http.IHttpClient;
import com.legend.ffpmvp.common.http.IRequest;
import com.legend.ffpmvp.common.http.IResponse;
import com.legend.ffpmvp.common.http.impl.RequestImpl;
import com.legend.ffpmvp.common.http.impl.OkHttpClientImpl;
import com.legend.ffpmvp.common.utils.ApiUtils;
import com.legend.ffpmvp.common.utils.MyApplication;
import com.legend.ffpmvp.common.utils.SharedPreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.legend.ffpmvp.common.bean.Status.ALREADY_JOIN;
import static com.legend.ffpmvp.common.bean.Status.JOIN_OK;
import static com.legend.ffpmvp.common.bean.Status.UNKNOW_ERROR;
import static com.legend.ffpmvp.common.bean.Status.UN_LOGIN;

/**
 * @author Legend
 * @data by on 2018/2/4.
 * @description
 */

public class ReleasePlanModelImpl implements CreateContract.ICreateModel {

    private CreateContract.CallBack callBack;
    @Override
    public void requestCreate(File file, CreateContract.CallBack callBack, String... params) {
        this.callBack = callBack;
        new ReleasePlanAsyncTask().execute(ApiUtils.PLANS,params[0],params[1],params[2]);
    }

    class ReleasePlanAsyncTask extends AsyncTask<String,Void,IResponse> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callBack.start();
        }

        @Override
        protected IResponse doInBackground(String... strings) {
            IRequest request = new RequestImpl(strings[0]);
            SharedPreferenceUtils shared = new SharedPreferenceUtils(MyApplication.getInstance(),SharedPreferenceUtils.COOKIE);
            request.setHeader("Authorization","JWT "+shared.get(SharedPreferenceUtils.ACCOUNTJWT));
            Map<String,Object> map = new HashMap<>(16);
            map.put("end_time",strings[1]);
            map.put("content",strings[2]);
            map.put("from_circle",strings[3]);
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse response = mHttpClient.upload_image_post(request,map,null);
            return response;
        }

        @Override
        protected void onPostExecute(IResponse iResponse) {
            super.onPostExecute(iResponse);
            if (iResponse.getCode() == ALREADY_JOIN) {
                Log.d("4000000",iResponse.getData());
                callBack.showError(UNKNOW_ERROR);
            } else if(iResponse.getCode() == UN_LOGIN) {
                callBack.showError(UN_LOGIN);
            } else if (iResponse.getCode() == JOIN_OK) {
                joinPlan(iResponse.getData());
                callBack.success();
            } else {
                callBack.showError(UNKNOW_ERROR);
            }
            callBack.end();
        }
    }

    /**
     *  发布的同时加入这条计划
     * @param data
     */
    private void joinPlan(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data.toString());
            String plan_id = jsonObject.getString("id");
            new PlanContentModelImpl().partPlan(Integer.parseInt(plan_id), 1, new IPlanContentModel.RequestResult() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(int code) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
