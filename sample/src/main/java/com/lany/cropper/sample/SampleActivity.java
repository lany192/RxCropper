package com.lany.cropper.sample;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;
import com.lany.box.activity.BaseActivity;
import com.lany.box.utils.ToastUtils;
import com.lany.cropper.RxCropper;
import com.lany.cropper.entity.CropResult;
import com.lany.cropper.enums.CropShape;
import com.lany.cropper.enums.Guidelines;
import com.lany.cropper.enums.ScaleType;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class SampleActivity extends BaseActivity {
    @BindView(R.id.image_view_1)
    ImageView imageView1;
    @BindView(R.id.image_view_2)
    ImageView imageView2;

    @Override
    protected boolean hasBackBtn() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sample;
    }

    @Override
    protected void init(Bundle bundle) {

    }

    @OnClick(R.id.button)
    public void pickerClicked() {
        Disposable disposable = RxPicker.of()
                .single(true)
                .start(self)
                .subscribe(new Consumer<List<ImageItem>>() {
                    @Override
                    public void accept(@NonNull List<ImageItem> imageItems) {
                        String path = imageItems.get(0).getPath();
                        RequestOptions options = new RequestOptions()
                                .centerInside()
                                .diskCacheStrategy(DiskCacheStrategy.ALL);
                        Glide.with(self)
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
                .setCropShape(CropShape.RECTANGLE)
                .setGuidelines(Guidelines.ON_TOUCH)
                .setBorderCornerColor(Color.GREEN)
                .setBorderLineColor(Color.RED)
                .setGuidelinesColor(Color.BLUE)
                .setScaleType(ScaleType.CENTER)
                .setInitialCropWindowPaddingRatio(0.1f)
                .setFlipHorizontally(true)
                .setRotationDegrees(90)
                //自由模式
                //                .setAspectRatio(1,1)
                //                .setFixAspectRatio(false)
                //固定模式
                .setAspectRatio(1, 1)

                .setBackgroundColor(Color.parseColor("#90000000"))
                .start(this)
                .subscribe(new Consumer<CropResult>() {
                    @Override
                    public void accept(CropResult result) {
                        Log.i(TAG, "crop result: " + result);
                        handleCropResult(result);
                    }
                });
    }

    private void handleCropResult(CropResult result) {
        if (result.getError() == null) {
            RequestOptions options = new RequestOptions()
                    .centerInside()
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
            Glide.with(self)
                    .load(result.getUri())
                    .apply(options)
                    .into(imageView2);
        } else {
            Log.e(TAG, "Failed to crop image", result.getError());
            ToastUtils.show("Image crop failed: " + result.getError().getMessage());
        }
    }
}
