package com.legend.ffplan.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.legend.ffplan.MainActivity;
import com.legend.ffplan.R;
import com.legend.ffplan.common.util.ImageUtils;
import com.legend.ffplan.common.viewimplement.ICommonView;

import java.io.File;

/**
 * @author Legend
 * @data by on 2017/12/6.
 * @description 创建圈子Activity
 */

public class CreateCircleActivity extends AppCompatActivity implements ICommonView{

    private Toolbar toolbar;
    private CardView create_circle;
    private AppCompatImageView circle_image;

    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int RESIZE_REQUEST_CODE = 2;
    private String IMAGE_FILE_NAME = System.currentTimeMillis() + "new.jpg";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_circle_layout);
        initView();
        initListener();
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("创建一个圈子！");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        circle_image = findViewById(R.id.circle_image);
        create_circle = findViewById(R.id.add_circle);
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
        create_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CreateCircleActivity.this,"恭喜您成功创建圈子！",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CreateCircleActivity.this, MainActivity.class).putExtra("id","2"));
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
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
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
    private void showResizeImage(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            String path = getFilesDir().getPath() + File.separator + IMAGE_FILE_NAME;
            ImageUtils.saveImage(photo, path);
            new BitmapDrawable();
            Drawable drawable = new BitmapDrawable(photo);
            circle_image.setImageDrawable(drawable);
        }
    }
}
