package com.lany.cropper.sample;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

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
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

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
        findViewById(R.id.button).setOnClickListener(v -> pick());
    }

    private void pick() {
        Matisse.from(SampleActivity.this)
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, BuildConfig.APPLICATION_ID + ".fileProvider", "images"))
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .setOnSelectedListener((uriList, pathList) -> {
                    Log.e("onSelected", "onSelected: pathList=" + pathList);
                })
                .imageEngine(new GlideEngine())
                .showSingleMediaType(true)
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(isChecked -> {
                    Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                })
                .forResult(result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                        mAdapter.setData(Matisse.obtainResult(result.getData()), Matisse.obtainPathResult(result.getData()));
                        Log.e("OnActivityResult ", String.valueOf(Matisse.obtainOriginalState(result.getData())));

                        String path = Matisse.obtainPathResult(result.getData()).get(0);
                        RequestOptions options = new RequestOptions()
                                .centerInside()
                                .diskCacheStrategy(DiskCacheStrategy.ALL);
                        Glide.with(SampleActivity.this)
                                .load(path)
                                .apply(options)
                                .into(imageView1);
                        cropper(path);
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
