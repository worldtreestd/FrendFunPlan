package com.legend.ffpmvp.circle.model;

import android.os.AsyncTask;

import com.legend.ffpmvp.circle.contract.CircleCommonContract;
import com.legend.ffpmvp.circle.contract.CreateContract;
import com.legend.ffpmvp.common.http.IHttpClient;
import com.legend.ffpmvp.common.http.IRequest;
import com.legend.ffpmvp.common.http.IResponse;
import com.legend.ffpmvp.common.http.impl.OkHttpClientImpl;
import com.legend.ffpmvp.common.http.impl.RequestImpl;
import com.legend.ffpmvp.common.utils.ApiUtils;
import com.legend.ffpmvp.common.utils.MyApplication;
import com.legend.ffpmvp.common.utils.SharedPreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.legend.ffpmvp.common.bean.Status.JOIN_OK;
import static com.legend.ffpmvp.common.bean.Status.UNKNOW_ERROR;
import static com.legend.ffpmvp.common.bean.Status.UN_LOGIN;

/**
 * @author Legend
 * @data by on 2018/2/4.
 * @description
 */

public class CreateCircleModelImpl implements CreateContract.ICreateModel {

    private CreateContract.CallBack callBack;
    private File file;
    @Override
    public void requestCreate(File file,CreateContract.CallBack callBack, String... params) {
        this.file = file;
        this.callBack = callBack;
        new CreateCircleAsyncTask().execute(ApiUtils.CIRCLES,params[0],params[1],params[2]);
    }

    /**
     *  异步请求上传文件
     */
    class CreateCircleAsyncTask extends AsyncTask<String,Void,IResponse> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callBack.start();
        }

        @Override
        protected IResponse doInBackground(String... strings) {
            IRequest request = new RequestImpl(strings[0]);
            SharedPreferenceUtils shared = new SharedPreferenceUtils(MyApplication.getInstance(),SharedPreferenceUtils.COOKIE);
            String jwt = shared.get(SharedPreferenceUtils.ACCOUNTJWT);
            request.setHeader("Authorization","JWT "+jwt);
            Map<String,Object> map = new HashMap<>(16);
            map.put("name",strings[1]);
            map.put("address",strings[2]);
            map.put("desc",strings[3]);
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse response = mHttpClient.upload_image_post(request,map,file);
            return response;
        }

        @Override
        protected void onPostExecute(IResponse iResponse) {
            super.onPostExecute(iResponse);
            if (iResponse.getCode() == JOIN_OK) {
                joinCircle(iResponse.getData());
                callBack.success();
            } else if (iResponse.getCode() == UN_LOGIN){
                callBack.showError(UN_LOGIN);
            } else {
                callBack.showError(UNKNOW_ERROR);
            }
            callBack.end();
        }
    }

    /**
     *  创建成功后加入圈子
     * @param data
     */
    private void joinCircle(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int id = Integer.parseInt(jsonObject.getString("id"));
            new CircleContentModelImpl().getData(id, new CircleCommonContract.RequestCallBack() {
                @Override
                public void onSuccess(List list) {

                }

                @Override
                public void onFailure() {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
