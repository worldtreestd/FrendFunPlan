package com.legend.ffpmvp.circle.search.model;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.legend.ffpmvp.circle.search.ISearchContract;
import com.legend.ffpmvp.common.bean.HomeCircleBean;
import com.legend.ffpmvp.common.http.IHttpClient;
import com.legend.ffpmvp.common.http.IRequest;
import com.legend.ffpmvp.common.http.IResponse;
import com.legend.ffpmvp.common.http.impl.RequestImpl;
import com.legend.ffpmvp.common.http.impl.OkHttpClientImpl;
import com.legend.ffpmvp.common.utils.ApiUtils;
import com.legend.ffpmvp.common.utils.DateUtils;
import com.legend.ffpmvp.main.model.ICommonModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.legend.ffpmvp.common.bean.Status.UPDATE_OK;

/**
 * @author Legend
 * @data by on 2018/1/30.
 * @description
 */

public class SearchModelImpl implements ISearchContract.ISearchModel {

    private List<KeyWordBean> wordBeans;
    private ICommonModel.RequestResult<HomeCircleBean> result;
    private ICommonModel.RequestResult<KeyWordBean> resultKeyWord;
    private JSONArray results;
    private JSONArray keywordsArray;
    private static int count;

    @Override
    public void getSearchResult(String keyword, ICommonModel.RequestResult result) {
        this.result = result;
        new CircleSearchAsyncTask().execute(ApiUtils.SEARCH + keyword);
    }

    @Override
    public void requestKeyWord(String keyword, ICommonModel.RequestResult result) {
        this.resultKeyWord = result;
        if (TextUtils.isEmpty(keyword)) {
            synchronized(this) {
                new GetKeyWordAsyncTask().execute(ApiUtils.KEY_WORDS);
            }
        } else {
            new SaveKeyWordAsyncTask().execute(ApiUtils.KEY_WORDS,keyword);
        }
    }

    /**
     *  保存关键字
     */
    class SaveKeyWordAsyncTask extends AsyncTask<String,Void,IResponse> {
        @Override
        protected IResponse doInBackground(String... strings) {

            IRequest request = new RequestImpl(strings[0]);
            request.setBody("keyword",strings[1]);
            request.setBody("add_time", DateUtils.getDate().replace("/","-"));
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse iResponse = mHttpClient.post(request);
            return iResponse;
        }
    }
    /**
     *  得到关键字列表
     */
    class GetKeyWordAsyncTask extends AsyncTask<String,Void,List<KeyWordBean>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            resultKeyWord.onStart();
        }

        @Override
        protected List<KeyWordBean> doInBackground(String... strings) {
            IRequest request = new RequestImpl(strings[0]);
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse response = mHttpClient.get(request);
            if (response.getCode() == UPDATE_OK) {
                String data = response.getData().toString();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    keywordsArray = jsonObject.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                wordBeans =
                        gson.fromJson(keywordsArray.toString(),new TypeToken<List<KeyWordBean>>(){}.getType());
            } else {
                wordBeans = new ArrayList<>();
            }

            return wordBeans;
        }

        @Override
        protected void onPostExecute(List<KeyWordBean> keyWordBeans) {
            super.onPostExecute(keyWordBeans);
            Log.d("keyeyeeyey",keyWordBeans.toString());
            if (keyWordBeans != null) {
                resultKeyWord.onSuccess(keyWordBeans);
            } else {
                resultKeyWord.onFailure();
            }
            resultKeyWord.onEnd();
        }
    }
    class CircleSearchAsyncTask extends AsyncTask<String, Void, List<HomeCircleBean>> {

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
                String data = response.getData().toString();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    count = jsonObject.getInt("count");
                    results = jsonObject.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                List<HomeCircleBean> circleList =
                        gson.fromJson(results.toString(), new TypeToken<List<HomeCircleBean>>() {
                        }.getType());
                return circleList;
            }

            @Override
            protected void onPostExecute(List<HomeCircleBean> list) {
                super.onPostExecute(list);
                if (list.size() > 0) {
                    result.onSuccess(list);
                } else {
                    result.onFailure();
                }
                result.onEnd();
            }
        }
}
