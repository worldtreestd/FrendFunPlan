package com.legend.ffpmvp.circle.model;

import android.os.AsyncTask;

import com.legend.ffpmvp.circle.contract.CircleCommonContract;
import com.legend.ffpmvp.common.bean.MessageBean;
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

import static com.legend.ffpmvp.common.bean.Status.ALREADY_JOIN;

/**
 * @author Legend
 * @data by on 2018/1/31.
 * @description
 */

public class CircleContentModelImpl implements CircleCommonContract.ICircleContentModel {

    private CircleCommonContract.RequestCallBack<MessageBean> callBack;
    @Override
    public void getData(int id, CircleCommonContract.RequestCallBack callBack) {
        this.callBack = callBack;
        new PartCircleAsyncTask().execute(ApiUtils.PARTCIRCLE, String.valueOf(id));
    }

    class PartCircleAsyncTask extends AsyncTask<String,Void,IResponse> {

        @Override
        protected IResponse doInBackground(String... strings) {
            IRequest request = new RequestImpl(strings[0]);
            SharedPreferenceUtils shared = new SharedPreferenceUtils(MyApplication.getInstance(),SharedPreferenceUtils.COOKIE);
            request.setHeader("Authorization","JWT "+shared.get(SharedPreferenceUtils.ACCOUNTJWT));
            request.setBody("circle",strings[1]);
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse response = mHttpClient.post(request);
            return response;
        }

        @Override
        protected void onPostExecute(IResponse iResponse) {
            super.onPostExecute(iResponse);
            if (iResponse.getCode() == ALREADY_JOIN) {
                String data = iResponse.getData().toString();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    callBack.onFailure();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
