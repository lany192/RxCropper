package com.lany.cropper.sample;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.lany.box.activity.BaseActivity;
import com.lany.cropper.RxCropper;
import com.lany.cropper.entity.CropResult;
import com.lany.cropper.enums.CropShape;
import com.lany.cropper.enums.Guidelines;
import com.lany.cropper.enums.ScaleType;
import com.lany.picker.RxPicker;
import com.lany.picker.bean.ImageItem;

import java.io.File;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class SampleActivity extends BaseActivity {

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
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disposable disposable = RxPicker.of()
                        .single(true)
                        .start(self)
                        .subscribe(new Consumer<List<ImageItem>>() {
                            @Override
                            public void accept(@NonNull List<ImageItem> imageItems) {
                                String path = imageItems.get(0).getPath();
                                cropper(path);
                            }
                        });
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
                .setInitialCropWindowPaddingRatio(1.0f)
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
                        Log.i(TAG, "剪切结果: " + result);
                        handleCropResult(result);
                    }
                });
    }


    private void handleCropResult(CropResult result) {
//        if (result.getError() == null) {
//            Intent intent = new Intent(self, ResultActivity.class);
//            intent.putExtra("SAMPLE_SIZE", result.getSampleSize());
//            if (result.getUri() != null) {
//                intent.putExtra("URI", result.getUri());
//            } else {
//                ResultActivity.mImage =
//                        result.getCropShape() == CropShape.OVAL
//                                ? CropImage.toOvalBitmap(result.getBitmap())
//                                : result.getBitmap();
//            }
//            startActivity(intent);
//        } else {
//            Log.e("AIC", "Failed to crop image", result.getError());
//            Toast.makeText(self,
//                    "Image crop failed: " + result.getError().getMessage(),
//                    Toast.LENGTH_LONG)
//                    .show();
//        }
    }

}
