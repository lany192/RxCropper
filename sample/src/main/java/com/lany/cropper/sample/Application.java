package com.lany.cropper.sample;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.lany192.rxpicker.RxPicker;
import com.github.lany192.rxpicker.utils.ImageLoader;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RxPicker.of().init((imageView, path, width, height) -> {
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
        });
    }
}
