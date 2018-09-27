package com.lany.cropper.sample;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.utils.ImageLoader;
import com.lany.box.BaseApp;

public class Application extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        RxPicker.init(new ImageLoader() {
            @Override
            public void display(ImageView imageView, String path, int width, int height) {
                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.ic_preview_image)
                        .error(R.drawable.ic_preview_image)
                        .override(width, height)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE);
                Glide.with(imageView.getContext())
                        .load(path)
                        .apply(options)
                        .into(imageView);
            }
        });
    }
}
