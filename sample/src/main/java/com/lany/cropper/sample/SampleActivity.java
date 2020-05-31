package com.lany.cropper.sample;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.lany192.cropper.CropImage;
import com.github.lany192.cropper.RxCropper;
import com.github.lany192.cropper.entity.CropResult;
import com.github.lany192.cropper.enums.CropShape;
import com.github.lany192.cropper.enums.Guidelines;
import com.lany.cropper.sample.permission.RxPermissions;

import java.io.File;
import java.io.IOException;

import io.reactivex.rxjava3.disposables.Disposable;

public class SampleActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private ImageView imageView1;
    private ImageView imageView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        imageView1 = findViewById(R.id.image_view_1);
        imageView2 = findViewById(R.id.image_view_2);
        findViewById(R.id.button).setOnClickListener(v -> checkPermissions());
    }

    private void checkPermissions() {
        Disposable disposable = new RxPermissions(SampleActivity.this)
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        pick();
                    } else {
                        Toast.makeText(SampleActivity.this, "权限不足", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void pick() {
        Disposable disposable2 = RxPicker
                .get()
                .maxSize(1)
                .start(SampleActivity.this)
                .subscribe(paths -> {
                    String path = paths.get(0);
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

    private void cropper(String path) {
        Disposable disposable = RxCropper.of()
                .setSourceUri(Uri.fromFile(new File(path)))
                .setCropShape(CropShape.OVAL)
                .setGuidelines(Guidelines.ON_TOUCH)
                .setBorderCornerColor(Color.GREEN)
                .setBorderLineColor(Color.RED)
                .setGuidelinesColor(Color.BLUE)
                //.setScaleType(ScaleType.CENTER)
                //.setInitialCropWindowPaddingRatio(0.1f)
                //.setFlipHorizontally(true)
                //.setRotationDegrees(90)
                //自由模式
                //.setAspectRatio(1,1)
                //.setFixAspectRatio(false)
                //固定模式
                .setAspectRatio(1, 1)
                .setBackgroundColor(Color.TRANSPARENT)
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
