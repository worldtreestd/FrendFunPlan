package com.legend.ffpmvp.circle.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.legend.ffpmvp.R;
import com.legend.ffpmvp.circle.contract.CreateContract;
import com.legend.ffpmvp.circle.model.CreateCircleModelImpl;
import com.legend.ffpmvp.circle.presenter.CreateCirclePresenter;
import com.legend.ffpmvp.common.utils.ImageUtils;
import com.legend.ffpmvp.common.utils.ToastUtils;
import com.legend.ffpmvp.common.view.BaseActivity;
import com.legend.ffpmvp.main.view.MainActivity;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.citywheel.CityPickerView;

import java.io.File;

import me.james.biuedittext.BiuEditText;

import static com.legend.ffpmvp.common.bean.Status.JOIN_OK;
import static com.legend.ffpmvp.common.bean.Status.UN_LOGIN;

/**
 * @author Legend
 * @data by on 2018/2/4.
 * @description
 */

public class CreateCircleView extends BaseActivity implements CreateContract.ICreateView {

    private Toolbar toolbar;
    private CreateContract.ICreateModel model;
    private CreateContract.ICreatePresenter presenter;
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
    public Object setResourceLayout() {
        return R.layout.add_circle_layout;
    }

    @Override
    public void initView() {
        model = new CreateCircleModelImpl();
        presenter = new CreateCirclePresenter(model,this);
        circle_image = $(R.id.circle_image);
        circle_name = $(R.id.circle_name);
        circle_address = $(R.id.circle_address);
        address_picker = $(R.id.address_picker);
        circle_content = $(R.id.circle_content);
        create_circle = $(R.id.add_circle);
    }

    @Override
    public void initListener() {
        circle_image.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,IMAGE_REQUEST_CODE);
        });
        address_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityConfig cityConfig = new CityConfig.Builder(CreateCircleView.this)
                        .itemPadding(8)
                        .build();
                CityPickerView cityPicker = new CityPickerView(cityConfig);
                cityPicker.show();
                cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        super.onSelected(province, city, district);
                        if (district == null) {
                            return;
                        }
                        String current_province = province.getName();
                        String current_city = ""+city.getName();
                        String current_district = ""+district.getName().toString();
                        if (TextUtils.isEmpty(current_province) || TextUtils.isEmpty(current_city)) {
                            ToastUtils.showToast(CreateCircleView.this,"当前输入非法");
                            return;
                        }
                        address_picker.setText(current_province+current_city+current_district+" ");

                    }
                });
            }
        });

        create_circle.setOnClickListener(view -> {

            address = address_picker.getText().toString()+circle_address.getText().toString();
            circlename = circle_name.getText().toString();
            desc = circle_content.getText().toString();
            if (TextUtils.isEmpty(address) || TextUtils.isEmpty(desc)) {
                ToastUtils.showToast(CreateCircleView.this,"请填写完整的圈子信息");
            } else {
                /**
                 *  请求创建圈子
                 */
                if (!TextUtils.isEmpty(path)) {
                    file = new File(path);
                    presenter.createExecute(file,circlename,address,desc);
                } else {
                    ToastUtils.showToast(CreateCircleView.this,"图片不能为空");
                }
            }
        });
    }

    @Override
    public boolean showHomeAsUp() {
        return true;
    }

    @Override
    public void showLoading() {
        dialog = ToastUtils.createLoadingDialog(this, getString(R.string.create_circle_loading));
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @Override
    public void showError(int code) {
        switch (code) {
            case JOIN_OK:
                ToastUtils.showToast(CreateCircleView.this,"恭喜您成功创建一个圈子");
                startActivity(new Intent(CreateCircleView.this, MainActivity.class).putExtra("id","0"));
                break;
            case UN_LOGIN:
                ToastUtils.showToast(CreateCircleView.this,"请登录后再进行操作");
                break;
            default:
                break;
        }
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
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1.2);
        // outputX outputY 是裁剪图片宽高
        //清晰度够的情况下还是用360比较好
        intent.putExtra("outputX", 360);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
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
}
