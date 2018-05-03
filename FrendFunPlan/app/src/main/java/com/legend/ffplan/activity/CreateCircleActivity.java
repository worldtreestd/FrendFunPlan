package com.legend.ffplan.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.legend.ffplan.MainActivity;
import com.legend.ffplan.R;
import com.legend.ffplan.common.http.IHttpClient;
import com.legend.ffplan.common.http.IRequest;
import com.legend.ffplan.common.http.IResponse;
import com.legend.ffplan.common.http.impl.BaseRequest;
import com.legend.ffplan.common.http.impl.OkHttpClientImpl;
import com.legend.ffplan.common.util.ApiUtils;
import com.legend.ffplan.common.util.ImageUtils;
import com.legend.ffplan.common.util.MyApplication;
import com.legend.ffplan.common.util.SharedPreferenceUtils;
import com.legend.ffplan.common.util.ToastUtils;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.citywheel.CityPickerView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import me.james.biuedittext.BiuEditText;

/**
 * @author Legend
 * @data by on 2017/12/6.
 * @description 创建圈子Activity
 */

public class CreateCircleActivity extends BaseActivity{

    private View mView;
    private Toolbar toolbar;
    private CardView create_circle;
    private AppCompatImageView circle_image;
    private Button address_picker;
    private BiuEditText circle_address,circle_name,circle_content;
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int RESIZE_REQUEST_CODE = 2;
    private String address;
    private String path;
    private String IMAGE_FILE_NAME = System.currentTimeMillis() + "new.jpg";
    private Dialog dialog;
    private File file;
    private String circlename;
    private String desc;

    @Override
    public int setResourceLayout() {
        return R.layout.add_circle_layout;
    }

    @Override
    public void initView() {
        toolbar = $(R.id.toolbar);
        toolbar.setTitle("创建一个圈子！");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        circle_image = $(R.id.circle_image);
        circle_name = $(R.id.circle_name);
        circle_address = $(R.id.circle_address);
        address_picker = $(R.id.address_picker);
        circle_content = $(R.id.circle_content);
        create_circle = $(R.id.add_circle);
        dialog = ToastUtils.createLoadingDialog(this, getString(R.string.create_circle_loading));
    }

    @Override
    public void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        circle_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,IMAGE_REQUEST_CODE);
            }
        });
        address_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityConfig cityConfig = new CityConfig.Builder(CreateCircleActivity.this)
                        .itemPadding(8)
                        .build();
                CityPickerView cityPicker = new CityPickerView(cityConfig);
                cityPicker.show();
                cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        super.onSelected(province, city, district);
                        String current_province = province.getName();
                        String current_city = ""+city.getName();
                        String current_district = ""+district.getName().toString();
                        if (current_city == null || current_city == null || current_city == null) {
                            ToastUtils.showToast(CreateCircleActivity.this,"当前输入非法");
                            return;
                        }
                        address_picker.setText(current_province+current_city+current_district+" ");

                    }
                });
            }
        });

        create_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(path)) {
                    file = new File(path);
                }
                address = address_picker.getText().toString()+circle_address.getText().toString();
                circlename = circle_name.getText().toString();
                desc = circle_content.getText().toString();
                if (TextUtils.isEmpty(address) || TextUtils.isEmpty(desc)) {
                    ToastUtils.showToast(CreateCircleActivity.this,"还有字段为空哦");
                } else {
                    new CreateCircleAsyncTask().execute(ApiUtils.CIRCLES);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        } else {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    resizeImage(data.getData());
                    break;

                case RESIZE_REQUEST_CODE:
                    if (data != null) {
                        showResizeImage(data);
                    }
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     *  调整图片大小
     * @param uri
     */
    public void resizeImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1.5);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 440);
        intent.putExtra("outputY", 280);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }

    /**
     *  显示裁剪后的图片
     * @param data
     */
    private void showResizeImage(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            path = getFilesDir().getPath() + File.separator + IMAGE_FILE_NAME;
            ImageUtils.saveImage(photo, path);
            Drawable drawable = new BitmapDrawable(photo);
            circle_image.setImageDrawable(drawable);
        }
    }

    /**
     *  异步请求上传文件
     */
    class CreateCircleAsyncTask extends AsyncTask<String,Void,IResponse> {

        @Override
        protected IResponse doInBackground(String... strings) {
            IRequest request = new BaseRequest(strings[0]);
            SharedPreferenceUtils shared = new SharedPreferenceUtils(MyApplication.getInstance(),SharedPreferenceUtils.COOKIE);
            String jwt = shared.get(SharedPreferenceUtils.ACCOUNTJWT);
            request.setHeader("Authorization","JWT "+jwt);
            Map<String,Object> map = new HashMap<>();
            map.put("name",circlename);
            map.put("address",address);
            map.put("desc",desc);
            IHttpClient mHttpClient = new OkHttpClientImpl();
            IResponse response = mHttpClient.upload_image_post(request,map,file);
            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected void onPostExecute(IResponse iResponse) {
            super.onPostExecute(iResponse);
            if (iResponse.getCode() == 201) {
                dialog.dismiss();
                ToastUtils.showToast(CreateCircleActivity.this,"恭喜您成功创建一个圈子");
                startActivity(new Intent(CreateCircleActivity.this, MainActivity.class).putExtra("id","0"));
            } else {
                ToastUtils.showToast(CreateCircleActivity.this,"网络出错");
                dialog.dismiss();
            }
        }
    }
}
