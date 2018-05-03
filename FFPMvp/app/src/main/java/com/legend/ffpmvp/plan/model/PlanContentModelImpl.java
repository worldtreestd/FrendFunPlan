package com.legend.ffpmvp.plan.model;

import android.os.AsyncTask;

import com.legend.ffpmvp.common.http.IHttpClient;
import com.legend.ffpmvp.common.http.IRequest;
import com.legend.ffpmvp.common.http.IResponse;
import com.legend.ffpmvp.common.http.impl.RequestImpl;
import com.legend.ffpmvp.common.http.impl.OkHttpClientImpl;
import com.legend.ffpmvp.common.utils.ApiUtils;
import com.legend.ffpmvp.common.utils.MyApplication;
import com.legend.ffpmvp.common.utils.SharedPreferenceUtils;

import org.json.JSONObject;

import static com.legend.ffpmvp.common.bean.Status.ALREADY_JOIN;
import static com.legend.ffpmvp.common.bean.Status.JOIN_OK;
import static com.legend.ffpmvp.common.bean.Status.NO_EXIT;
import static com.legend.ffpmvp.common.bean.Status.UN_LOGIN;
import static com.legend.ffpmvp.common.bean.Status.UPDATE_OK;

/**
 * @author Legend
 * @data by on 2018/1/30.
 * @description
 */

public class PlanContentModelImpl implements IPlanContentModel{

    private RequestResult result;
    private JSONObject jsonObject;
    private String error_message;

    @Override
    public void partPlan(int id,int status,RequestResult result) {
        this.result = result;
        if (status == 0) {
            new PartPlanAsyncTask().execute(ApiUtils.PARTPLANS+id, String.valueOf(id));
        } else {
            new PartPlanAsyncTask().execute(ApiUtils.PARTPLANS, String.valueOf(id));
        }
    }

    class PartPlanAsyncTask extends AsyncTask<String,Void,IResponse> {

        @Override
        protected IResponse doInBackground(String... strings) {
            IRequest request = new RequestImpl(strings[0]);
            SharedPreferenceUtils shared = new SharedPreferenceUtils(MyApplication.getInstance(),SharedPreferenceUtils.COOKIE);
            request.setHeader("Authorization", "JWT "+shared.get(SharedPreferenceUtils.ACCOUNTJWT));
            request.setBody("plan",strings[1]);
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse response = mHttpClient.post(request);
            return response;
        }

        @Override
        protected void onPostExecute(IResponse iResponse) {
            super.onPostExecute(iResponse);
            String data = iResponse.getData().toString();
            if (iResponse.getCode() == ALREADY_JOIN || iResponse.getCode() == UPDATE_OK) {
                result.onFailure(ALREADY_JOIN);
            } else if (iResponse.getCode() == JOIN_OK){
                result.onSuccess();
            } else if (iResponse.getCode() == UN_LOGIN) {
                result.onFailure(UN_LOGIN);
            } else {
                result.onFailure(NO_EXIT);
            }
        }
    }
}
