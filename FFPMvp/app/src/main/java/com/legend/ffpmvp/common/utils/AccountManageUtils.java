package com.legend.ffpmvp.common.utils;

import android.text.TextUtils;
import android.util.Log;

import com.legend.ffpmvp.common.http.IHttpClient;
import com.legend.ffpmvp.common.http.IRequest;
import com.legend.ffpmvp.common.http.IResponse;
import com.legend.ffpmvp.common.http.impl.RequestImpl;
import com.legend.ffpmvp.common.http.impl.OkHttpClientImpl;

import org.json.JSONException;
import org.json.JSONObject;

import static com.legend.ffpmvp.common.bean.Status.JOIN_OK;
import static com.legend.ffpmvp.common.utils.SharedPreferenceUtils.COOKIE;


/**
 * @author Legend
 * @data by on 2018/1/3.
 * @description 账号管理工具类
 */

public class AccountManageUtils {

    private SharedPreferenceUtils sharedPreferenceUtils;
    private IHttpClient mHttpClient;
    private String jwt;
    private String token;
    private String openId;
    private String url;
    private String oldOpenId;

    public AccountManageUtils(String token, String openId) {
        mHttpClient = new OkHttpClientImpl();
        sharedPreferenceUtils = new SharedPreferenceUtils(MyApplication.getInstance(),COOKIE);
        this.oldOpenId = sharedPreferenceUtils.get(SharedPreferenceUtils.OPENID);
        this.jwt = sharedPreferenceUtils.get(SharedPreferenceUtils.ACCOUNTJWT);
        this.token = token;
        this.openId = openId;
    }

    /**
     *  通过本地JWT方式完成验证
     */
    public void loginByJWT() {
        if (!TextUtils.isEmpty(jwt) && oldOpenId.equals(openId)) {
            return;
        } else {
            jwt = getJwt(openId);
            if (TextUtils.isEmpty(jwt)) {
                register();
            }
        }
    }
    /**
     *  注册账号
     * @param
     * @param
     */
    public void register() {
        IRequest request = new RequestImpl(ApiUtils.USERS);
        request.setBody(SharedPreferenceUtils.ACCESSTOKEN,token);
        request.setBody(SharedPreferenceUtils.OPENID, openId);

        IResponse response = mHttpClient.post(request);
        Log.d("responseresponse", String.valueOf(response.getCode()));
        if (response.getCode() == JOIN_OK) {
            getJwt(openId);
        }
    }
    /**
     *  获取服务器端返回的JWT Token
     * @param openId
     * @return
     */
    public String getJwt(String openId) {
        IRequest request = new RequestImpl(ApiUtils.LOGIN);
        request.setBody("username", openId);
        request.setBody("password",openId);

        IResponse response = mHttpClient.post(request);
        String data = response.getData().toString();
        try {
            JSONObject jsonObject = new JSONObject(data);
            String jwt = jsonObject.getString("token");
            sharedPreferenceUtils.save(SharedPreferenceUtils.ACCOUNTJWT, jwt);
            return jwt;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
