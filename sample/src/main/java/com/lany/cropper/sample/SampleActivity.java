package com.lany.cropper.sample;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.lany192.rxpicker.RxPicker;
import com.lany.cropper.CropImage;
import com.lany.cropper.RxCropper;
import com.lany.cropper.entity.CropResult;
import com.lany.cropper.enums.CropShape;
import com.lany.cropper.enums.Guidelines;

import java.io.File;
import java.io.IOException;

import io.reactivex.disposables.Disposable;

public class SampleActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    ImageView imageView1;
    ImageView imageView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        imageView1 = findViewById(R.id.image_view_1);
        imageView2 = findViewById(R.id.image_view_2);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disposable disposable = RxPicker.of()
                        .single(true)
                        .start(SampleActivity.this)
                        .subscribe(imageItems -> {
                            String path = imageItems.get(0).getPath();
                            RequestOptions options = new RequestOptions()
                                    .centerInside()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL);
                            Glide.with(SampleActivity.this)
                                    .load(path)
                                    .apply(options)
                                    .into(imageView1);
                            cropper(path);
                        });
            }
        });
    }

    private void cropper(String path) {
        Disposable disposable = RxCropper.of()
                .setSourceUri(Uri.fromFile(new File(path)))
                .setCropShape(CropShape.OVAL)
                .setGuidelines(Guidelines.ON_TOUCH)
                //.setBorderCornerColor(Color.GREEN)
                //.setBorderLineColor(Color.RED)
                //.setGuidelinesColor(Color.BLUE)
                //.setScaleType(ScaleType.CENTER)
                //.setInitialCropWindowPaddingRatio(0.1f)
                //.setFlipHorizontally(true)
                //.setRotationDegrees(90)
                //自由模式
                //.setAspectRatio(1,1)
                //.setFixAspectRatio(false)
                //固定模式
                .setAspectRatio(1, 1)
                //.setBackgroundColor(Color.parseColor("#90000000"))
                .start(this)
                .subscribe(result -> {
                    Log.i(TAG, "crop result: " + result);
                    handleCropResult(result);
                });
    }

    private void handleCropResult(CropResult result) {
        if (result.getError() == null) {
            RequestOptions options = new RequestOptions()
                    .centerInside()
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
            if (result.getCropShape() == CropShape.OVAL) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(SampleActivity.this)
                        .load(CropImage.toOvalBitmap(bitmap))
                        .apply(options)
                        .into(imageView2);
            } else {
                Glide.with(SampleActivity.this)
                        .load(result.getUri())
                        .apply(options)
                        .into(imageView2);
            }
        } else {
            Log.e(TAG, "Failed to crop image", result.getError());
        }
    }
}
