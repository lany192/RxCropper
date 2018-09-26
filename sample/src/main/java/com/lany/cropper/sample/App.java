package com.lany.cropper.sample;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lany.box.BaseApp;
import com.lany.picker.RxPicker;
import com.lany.picker.utils.ImageLoader;

public class App extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        RxPicker.init(new ImageLoader() {

            @Override
            public void display(ImageView imageView, String path, int width, int height) {
                Glide.with(imageView.getContext())
                        .load(path)
                        .error(R.drawable.ic_preview_image)
                        .centerCrop()
                        .override(width, height)
                        .into(imageView);
            }
        });
    }
}
