package com.lany.cropper.sample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lany.cropper.CropImage;
import com.lany.cropper.CropImageView;
import com.lany.picker.RxPicker;
import com.lany.picker.bean.ImageItem;
import com.lany.picker.utils.ImageLoader;

import java.io.File;
import java.util.List;

import io.reactivex.functions.Consumer;

public class SampleActivity extends AppCompatActivity {
    private CropImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        mImageView =  findViewById(R.id.cropImageView);
        RxPicker.init(new ImageLoader() {

            @Override
            public void display(ImageView imageView, String path, int width, int height) {
                Glide.with(imageView.getContext()).load(path).error(R.drawable.ic_preview_image).centerCrop().override(width, height).into(imageView);
            }
        });
        findViewById(R.id.my_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPicker.of().single(true).start(SampleActivity.this).subscribe(new Consumer<List<ImageItem>>() {
                    @Override
                    public void accept(@NonNull List<ImageItem> imageItems) {
                        String path = imageItems.get(0).getPath();
                        mImageView.setImageUriAsync(Uri.fromFile(new File(path)));
                    }
                });
            }
        });
        findViewById(R.id.crop_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageView.getCroppedImageAsync();
            }
        });
        mImageView.setOnSetImageUriCompleteListener(new CropImageView.OnSetImageUriCompleteListener() {
            @Override
            public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {

            }
        });
        mImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
            @Override
            public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
                handleCropResult(result);
            }
        });
    }


    private void handleCropResult(CropImageView.CropResult result) {
        if (result.getError() == null) {
            Intent intent = new Intent(this, CropResultActivity.class);
            intent.putExtra("SAMPLE_SIZE", result.getSampleSize());
            if (result.getUri() != null) {
                intent.putExtra("URI", result.getUri());
            } else {
                CropResultActivity.mImage =
                        mImageView.getCropShape() == CropImageView.CropShape.OVAL
                                ? CropImage.toOvalBitmap(result.getBitmap())
                                : result.getBitmap();
            }
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
