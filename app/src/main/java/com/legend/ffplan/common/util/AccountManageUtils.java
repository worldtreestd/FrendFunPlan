package com.legend.ffplan.common.util;

import android.text.TextUtils;

import com.legend.ffplan.common.http.IHttpClient;
import com.legend.ffplan.common.http.IRequest;
import com.legend.ffplan.common.http.IResponse;
import com.legend.ffplan.common.http.impl.BaseRequest;
import com.legend.ffplan.common.http.impl.OkHttpClientImpl;

import org.json.JSONException;
import org.json.JSONObject;

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

    public AccountManageUtils(String token, String openId) {
        mHttpClient = new OkHttpClientImpl();
        sharedPreferenceUtils = new SharedPreferenceUtils(MyApplication.getInstance(),sharedPreferenceUtils.COOKIE);
        this.jwt = sharedPreferenceUtils.get(SharedPreferenceUtils.ACCOUNTJWT);
        this.token = token;
        this.openId = openId;
    }
    public AccountManageUtils(String url) {
        sharedPreferenceUtils = new SharedPreferenceUtils(MyApplication.getInstance(),sharedPreferenceUtils.COOKIE);
        this.jwt = sharedPreferenceUtils.get(SharedPreferenceUtils.ACCOUNTJWT);
        mHttpClient = new OkHttpClientImpl();
        this.url = url;
    }

    /**
     *  通过本地JWT方式完成验证
     */
    public void loginByJWT() {
        if (!TextUtils.isEmpty(jwt)) {
            return;
        } else {
            jwt = getJwt(openId);
            if (TextUtils.isEmpty(jwt)) {
                register();
            } else {
                loginByJWT();
            }
        }
    }
    /**
     *  注册账号
     * @param
     * @param
     */
    public void register() {
        IRequest request = new BaseRequest(ApiUtils.USERS);
        request.setBody(SharedPreferenceUtils.ACCESSTOKEN,token);
        request.setBody(SharedPreferenceUtils.OPENID, openId);

        IResponse response = mHttpClient.post(request);
        if (response.getCode() == 200) {
            getJwt(openId);
        }
    }
    /**
     *  获取服务器端返回的JWT Token
     * @param openId
     * @return
     */
    public String getJwt(String openId) {
        IRequest request = new BaseRequest(ApiUtils.LOGIN);
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
