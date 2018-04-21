package com.lany.cropper.sample;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lany.cropper.CropOptions;
import com.lany.cropper.CropResult;
import com.lany.cropper.RxCropper;
import com.lany.picker.RxPicker;
import com.lany.picker.bean.ImageItem;
import com.lany.picker.utils.ImageLoader;

import java.io.File;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class SampleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        RxPicker.init(new ImageLoader() {

            @Override
            public void display(ImageView imageView, String path, int width, int height) {
                Glide.with(imageView.getContext()).load(path).error(R.drawable.ic_preview_image).centerCrop().override(width, height).into(imageView);
            }
        });
        findViewById(R.id.my_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disposable disposable = RxPicker.of()
                        .single(true)
                        .start(SampleActivity.this)
                        .subscribe(new Consumer<List<ImageItem>>() {
                            @Override
                            public void accept(@NonNull List<ImageItem> imageItems) {
                                String path = imageItems.get(0).getPath();
                                startCropActivity(path);
                            }
                        });
            }
        });
    }

    private void startCropActivity(String path) {
        Disposable disposable = RxCropper.of()
                .setSourceUri(Uri.fromFile(new File(path)))
                .setBackgroundColor(Color.parseColor("#90000000"))
                .start(this)
                .subscribe(new Consumer<CropResult>() {
                    @Override
                    public void accept(CropResult result) {
                        handleCropResult(result);
                    }
                });
    }

    private void handleCropResult(CropResult result) {
        if (result.getError() == null) {
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("SAMPLE_SIZE", result.getSampleSize());
            intent.putExtra("URI", result.getUri());
            startActivity(intent);
        } else {
            Log.e("AIC", "Failed to crop image", result.getError());
            Toast.makeText(this,
                    "Image crop failed: " + result.getError().getMessage(),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }
}
