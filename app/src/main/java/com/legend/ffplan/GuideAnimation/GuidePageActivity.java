package com.legend.ffplan.GuideAnimation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.legend.ffplan.MainActivity;
import com.legend.ffplan.R;
import com.legend.ffplan.common.Bean.MessageBean;
import com.nightonke.wowoviewpager.Animation.WoWoAlphaAnimation;
import com.nightonke.wowoviewpager.Animation.WoWoElevationAnimation;
import com.nightonke.wowoviewpager.Animation.WoWoPathAnimation;
import com.nightonke.wowoviewpager.Animation.WoWoRotationAnimation;
import com.nightonke.wowoviewpager.Animation.WoWoScaleAnimation;
import com.nightonke.wowoviewpager.Animation.WoWoShapeColorAnimation;
import com.nightonke.wowoviewpager.Animation.WoWoTextViewColorAnimation;
import com.nightonke.wowoviewpager.Animation.WoWoTextViewTextAnimation;
import com.nightonke.wowoviewpager.Animation.WoWoTranslationAnimation;
import com.nightonke.wowoviewpager.Enum.Ease;
import com.nightonke.wowoviewpager.WoWoPathView;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * @author HP
 */
public class GuidePageActivity extends WoWoActivity {

    public static Tencent mTencent;
    public static UserInfo userInfo;
    public static int APP_ID = 222222;
    private String token;
    private String openId;

    private int r;
    private boolean animationAdded = false;
    private ImageView targetPlanet;
    private View loginLayout;
    private Button login_button;

    @Override
    protected int contentViewRes() {
        return R.layout.activity_guide_page;
    }

    @Override
    protected int fragmentNumber() {
        return 4;
    }

    @Override
    protected Integer[] fragmentColorsRes() {
        return new Integer[]{
                R.color.white,
                R.color.white,
                R.color.white,
                R.color.white,
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        switchIntent();
        super.onCreate(savedInstanceState);
        r = (int) Math.sqrt(screenW * screenW + screenH * screenH) + 10;

        ImageView earth = (ImageView) findViewById(R.id.earth);
        targetPlanet = (ImageView) findViewById(R.id.planet_target);
        loginLayout = findViewById(R.id.login_layout);
        login_button = findViewById(R.id.button);
        mTencent = Tencent.createInstance(String.valueOf(APP_ID),getApplicationContext());

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mTencent.isSessionValid()) {
                    mTencent.login(GuidePageActivity.this, "all", loginListener);

                } else {
                    mTencent.logout(GuidePageActivity.this);
                    mTencent.login(GuidePageActivity.this, "all", loginListener);
                    return;
                }
            }
        });
        earth.setY(screenH / 2);
        targetPlanet.setY(-screenH / 2 - screenW / 2);
        targetPlanet.setScaleX(0.25f);
        targetPlanet.setScaleY(0.25f);
        tokenlogin();
        wowo.addTemporarilyInvisibleViews(0, earth, findViewById(R.id.cloud_blue), findViewById(R.id.cloud_red));
        wowo.addTemporarilyInvisibleViews(0, targetPlanet);
        wowo.addTemporarilyInvisibleViews(2, loginLayout, findViewById(R.id.button));
    }

    /**
     *  使用token自动登录
     */
    private void tokenlogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("cookie",MODE_PRIVATE);
        token = sharedPreferences.getString("token",null);
        openId = sharedPreferences.getString("openId",null);
        String expires = sharedPreferences.getString("expires",null);
        if ( token != null &&  openId != null && expires != null) {
            mTencent.setAccessToken(token, expires);
            mTencent.setOpenId(openId);
            userInfo = new UserInfo(GuidePageActivity.this,mTencent.getQQToken());
            userInfo.getUserInfo(userInfoListener);
        }
    }
    public void initOpenidAndToken(JSONObject jsonObject) {
        try {
            token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
                /**
                 *  第一次登录时保持token
                 */
                SharedPreferences sharedPreferences = getSharedPreferences("cookie",MODE_PRIVATE);
                sharedPreferences.edit().putString("token",token)
                        .putString("openId",openId)
                        .putString("expires",expires).commit();
                userInfo = new UserInfo(GuidePageActivity.this,mTencent.getQQToken());
                userInfo.getUserInfo(userInfoListener);
            }
        } catch(Exception e) {
        }
    }
    public IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject jsonObject) {
            super.doComplete(jsonObject);
            initOpenidAndToken(jsonObject);
        }
    };
    IUiListener userInfoListener = new IUiListener() {

        @Override
        public void onError(UiError arg0) {
            // TODO Auto-generated method stub

        }
        @Override
        public void onComplete(Object arg0) {
            // TODO Auto-generated method stub
            if(arg0 == null){
                return;
            }
            try {
                JSONObject jo = (JSONObject) arg0;
                int ret = jo.getInt("ret");
                System.out.println("json=" + String.valueOf(jo));
                final String nickName = jo.getString("nickname");
                String gender = jo.getString("gender");
                String qq_image = jo.getString("figureurl_qq_2");
                Intent intent = new Intent();
                intent.putExtra(MessageBean.USER_NAME,nickName);
                intent.putExtra(MessageBean.USER_IMAGE_URL,qq_image);
                intent.putExtra("TOKEN",token);
                intent.putExtra("OPENID",openId);
                intent.setClass(GuidePageActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(GuidePageActivity.this, "亲爱的【" + nickName+"】你好 欢迎来到这里",
                        Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                // TODO: handle exception
            }


        }

        @Override
        public void onCancel() {
            // TODO Auto-generated method stub

        }
    };

    //QQ登录后的回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("TAG", "-->onActivityResult " + requestCode  + " resultCode=" + resultCode);
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response ) {
                Toast.makeText(GuidePageActivity.this,"返回为空 登录失败",Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject jsonObject = (JSONObject) response;
            if (response != null && jsonObject.length() == 0) {
                Toast.makeText(GuidePageActivity.this,"返回为空 登录失败",Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(GuidePageActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
            doComplete((JSONObject) response);
        }


        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
        // 提供回调的方法
        protected void doComplete(JSONObject jsonObject) {

        }
    }

    /**
     *  退出程序
     */
    private void switchIntent() {
        String s = getIntent().getStringExtra("exit");
        if (!TextUtils.isEmpty(s)) {
            int id = Integer.parseInt(s);
            switch (id) {
                case 0:
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        addAnimations();
    }

    private void addAnimations() {
        if (animationAdded) return;
        animationAdded = true;

        addEarthAnimation();
        addCloudAnimation();
        addTextAnimation();
        addRocketAnimation();
        addCircleAnimation();
        addMeteorAnimation();
        addPlanetAnimation();
        addPlanetTargetAnimation();
        addLoginLayoutAnimation();
        addButtonAnimation();
        addEditTextAnimation();

        wowo.ready();

        // Do this the prevent the edit-text and button views on login layout
        // to intercept the drag event.
        wowo.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                loginLayout.setEnabled(position == 3);
                loginLayout.setVisibility(position + positionOffset <= 2 ? View.INVISIBLE : View.VISIBLE);
            }
        });
    }

    private void addEarthAnimation() {
        View earth = findViewById(R.id.earth);
        wowo.addAnimation(earth)
                .add(WoWoRotationAnimation.builder().page(0).keepX(0).keepY(0).fromZ(0).toZ(180).ease(Ease.OutBack).build())
                .add(WoWoRotationAnimation.builder().page(1).keepX(0).keepY(0).fromZ(180).toZ(720).ease(Ease.OutBack).build())
                .add(WoWoRotationAnimation.builder().page(2).keepX(0).keepY(0).fromZ(720).toZ(1260).ease(Ease.OutBack).build())
                .add(WoWoScaleAnimation.builder().page(1).fromXY(1).toXY(0.5).ease(Ease.OutBack).build())
                .add(WoWoScaleAnimation.builder().page(2).fromXY(0.5).toXY(0.25).ease(Ease.OutBack).build());
    }

    private void addCloudAnimation() {
        wowo.addAnimation(findViewById(R.id.cloud_blue))
                .add(WoWoTranslationAnimation.builder().page(0).fromX(screenW).toX(0).keepY(0).ease(Ease.OutBack).sameEaseBack(false).build())
                .add(WoWoTranslationAnimation.builder().page(1).fromX(0).toX(screenW).keepY(0).ease(Ease.InBack).sameEaseBack(false).build());

        wowo.addAnimation(findViewById(R.id.cloud_red))
                .add(WoWoTranslationAnimation.builder().page(0).fromX(-screenW).toX(0).keepY(0).ease(Ease.OutBack).sameEaseBack(false).build())
                .add(WoWoTranslationAnimation.builder().page(1).fromX(0).toX(-screenW).keepY(0).ease(Ease.InBack).sameEaseBack(false).build());

        wowo.addAnimation(findViewById(R.id.cloud_yellow))
                .add(WoWoTranslationAnimation.builder().page(0).keepX(0).fromY(0).toY(dp2px(50)).ease(Ease.OutBack).sameEaseBack(false).build())
                .add(WoWoTranslationAnimation.builder().page(1).fromX(0).toX(-screenW).keepY(dp2px(50)).ease(Ease.InBack).sameEaseBack(false).build());
    }

    private void addTextAnimation() {
        View text = findViewById(R.id.text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            text.setZ(50);
        }
        String[] texts = new String[]{
                "你是否有这样的情景?",
                "经常给自己安排计划?",
                "又不能约束自己完成?",
                "让你的计划不再枯燥",
        };
        wowo.addAnimation(text)
                .add(WoWoTextViewTextAnimation.builder().page(0).from(texts[0]).to(texts[1]).build())
                .add(WoWoTextViewTextAnimation.builder().page(1).from(texts[1]).to(texts[2]).build())
                .add(WoWoTextViewTextAnimation.builder().page(2).from(texts[2]).to(texts[3]).build())
                .add(WoWoTextViewColorAnimation.builder().page(1).from("#05502f").to(Color.WHITE).build());
    }

    private void addRocketAnimation() {
        WoWoPathView pathView = (WoWoPathView) findViewById(R.id.path_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pathView.setZ(50);
        }

        // For different screen size, try to adjust the scale values to see the airplane.
        float xScale = 1;
        float yScale = 1;

        pathView.newPath()
                .pathMoveTo(xScale * (-100), screenH - 100)
                .pathCubicTo(screenW / 2, screenH - 100,
                        screenW / 2, screenH - 100,
                        screenW / 2, yScale * (-100));
        wowo.addAnimation(pathView)
                .add(WoWoPathAnimation.builder().page(0).from(0).to(0.50).path(pathView).build())
                .add(WoWoPathAnimation.builder().page(1).from(0.50).to(0.75).path(pathView).build())
                .add(WoWoPathAnimation.builder().page(2).from(0.75).to(1).path(pathView).build())
                .add(WoWoAlphaAnimation.builder().page(2).from(1).to(0).build());
    }

    private void addCircleAnimation() {
        View circle = findViewById(R.id.circle);
        wowo.addAnimation(circle)
                .add(WoWoScaleAnimation.builder().page(1).fromXY(1).toXY(r * 2 / circle.getWidth()).build())
                .add(WoWoShapeColorAnimation.builder().page(1).from("#f9dc0a").to("#05502f").build());
    }

    private void addMeteorAnimation() {
        View meteor = findViewById(R.id.meteor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            meteor.setZ(50);
        }
        float fullOffset = screenW + meteor.getWidth();
        float offset = fullOffset / 2;
        wowo.addAnimation(meteor)
                .add(WoWoTranslationAnimation.builder().page(1)
                        .fromX(0).fromY(0)
                        .toX(offset).toY(offset).ease(Ease.OutBack).sameEaseBack(false).build())
                .add(WoWoTranslationAnimation.builder().page(2)
                        .fromX(offset).fromY(offset)
                        .toX(fullOffset).toY(fullOffset).ease(Ease.InBack).sameEaseBack(false).build());
    }

    private void addPlanetAnimation() {
        View planet0 = findViewById(R.id.planet_0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            planet0.setZ(50);
        }
        wowo.addAnimation(planet0)
                .add(WoWoTranslationAnimation.builder().page(1)
                        .keepX(0)
                        .fromY(0).toY(planet0.getHeight() + 200)
                        .ease(Ease.OutBack).sameEaseBack(false).build())
                .add(WoWoTranslationAnimation.builder().page(2)
                        .fromX(0).toX(screenW)
                        .keepY(planet0.getHeight() + 200)
                        .ease(Ease.InBack).sameEaseBack(false).build());

        View planet1 = findViewById(R.id.planet_1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) planet1.setZ(50);
        wowo.addAnimation(planet1)
                .add(WoWoTranslationAnimation.builder().page(1)
                        .fromX(0).toX(-planet1.getWidth())
                        .keepY(0)
                        .ease(Ease.OutBack).sameEaseBack(false).build())
                .add(WoWoTranslationAnimation.builder().page(2)
                        .fromX(-planet1.getWidth()).toX(-screenW - planet1.getWidth())
                        .keepY(0)
                        .ease(Ease.InBack).sameEaseBack(false).build());
    }

    private void addPlanetTargetAnimation() {
        wowo.addAnimation(targetPlanet)
                .add(WoWoRotationAnimation.builder().page(1).keepX(0).keepY(0).fromZ(0).toZ(180).ease(Ease.OutBack).build())
                .add(WoWoRotationAnimation.builder().page(2).keepX(0).keepY(0).fromZ(180).toZ(360).ease(Ease.OutBack).build())
                .add(WoWoTranslationAnimation.builder().page(0).keepX(0)
                        .fromY(-screenH / 2 - screenW / 2)
                        .toY(-screenH / 2).ease(Ease.OutBack).sameEaseBack(false).build())
                .add(WoWoScaleAnimation.builder().page(1).fromXY(0.25).toXY(0.5).ease(Ease.OutBack).build())
                .add(WoWoScaleAnimation.builder().page(2).fromXY(0.5).toXY(1).ease(Ease.OutBack).build());
    }

    private void addLoginLayoutAnimation() {
        View layout = findViewById(R.id.login_layout);
        wowo.addAnimation(layout)
                .add(WoWoAlphaAnimation.builder().page(1).start(1).end(1).from(0).to(1).build())
                .add(WoWoShapeColorAnimation.builder().page(2).from("#05502f").to("#0aa05f").build())
                .add(WoWoElevationAnimation.builder().page(2).from(0).to(40).build());
    }

    private void addButtonAnimation() {
        View button = findViewById(R.id.button);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            button.setZ(50);
        }
        wowo.addAnimation(button)
                .add(WoWoAlphaAnimation.builder().page(2).from(0).to(1).build());
    }

    private void addEditTextAnimation() {
        wowo.addAnimation(findViewById(R.id.app_logo))
                .add(WoWoAlphaAnimation.builder().page(2).from(0).to(1).build());
//        wowo.addAnimation(findViewById(R.id.password))
//                .add(WoWoAlphaAnimation.builder().page(2).from(0).to(1).build());
    }
}
