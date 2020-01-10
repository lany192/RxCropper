package com.lany.cropper.sample;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.ImageEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class RxPicker {
    private int maxSize = 9;
    private static final int REQUEST_CODE = 0x00100;

    private RxPicker() {
    }

    public static RxPicker get() {
        return new RxPicker();
    }

    public RxPicker maxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    public Observable<List<String>> start(FragmentActivity activity) {
        return start(activity.getSupportFragmentManager());
    }

    public Observable<List<String>> start(Fragment fragment) {
        return start(fragment.getFragmentManager());
    }

    private Observable<List<String>> start(FragmentManager fragmentManager) {
        CallbackFragment fragment = (CallbackFragment) fragmentManager.findFragmentByTag(CallbackFragment.class.getSimpleName());
        if (fragment == null) {
            fragment = new CallbackFragment();
            fragmentManager.beginTransaction().add(fragment, fragment.getClass().getSimpleName()).commitAllowingStateLoss();
        } else if (fragment.isDetached()) {
            fragmentManager.beginTransaction().attach(fragment).commitAllowingStateLoss();
        }
        return getResult(fragment);
    }

    private Observable<List<String>> getResult(CallbackFragment fragment) {
        return fragment.getAttachSubject()
                .filter(aBoolean -> aBoolean)
                .flatMap((Function<Boolean, ObservableSource<List<String>>>) aBoolean -> {
                    Matisse.from(fragment)
                            .choose(MimeType.of(MimeType.JPEG, MimeType.PNG), false)
                            .countable(true)
                            .capture(true)
                            .captureStrategy(new CaptureStrategy(true, BuildConfig.APPLICATION_ID + ".FileProvider", "test"))
                            .maxSelectable(maxSize)
                            .gridExpectedSize(dp2px(120))
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                            .thumbnailScale(0.85f)
                            .imageEngine(new GlideEngine())
                            .setOnSelectedListener((uriList, pathList) -> {
                                Log.e("onSelected", "onSelected: pathList=" + pathList);
                            })
                            .showSingleMediaType(true)
                            .theme(R.style.Matisse_Dracula)
                            .originalEnable(true)
                            .maxOriginalSize(10)
                            .autoHideToolbarOnSingleTap(true)
                            .setOnCheckedListener(isChecked -> {
                                Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                            })
                            .forResult(REQUEST_CODE);
                    return fragment.getResultSubject();
                }).take(1);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, Resources.getSystem().getDisplayMetrics());
    }

    class GlideEngine implements ImageEngine {

        @Override
        public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
            Glide.with(context)
                    .load(uri)
                    .placeholder(placeholder)
                    .override(resize, resize)
                    .centerCrop()
                    .into(imageView);
        }

        @Override
        public void loadGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
            Glide.with(context)
                    .load(uri)
                    .placeholder(placeholder)
                    .override(resize, resize)
                    .centerCrop()
                    .into(imageView);
        }

        @Override
        public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
            Glide.with(context)
                    .load(uri)
                    .override(resizeX, resizeY)
                    .priority(Priority.HIGH)
                    .fitCenter()
                    .into(imageView);
        }

        @Override
        public void loadGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
            Glide.with(context)
                    .load(uri)
                    .override(resizeX, resizeY)
                    .priority(Priority.HIGH)
                    .into(imageView);
        }

        @Override
        public boolean supportAnimatedGif() {
            return false;
        }

    }

    public static class CallbackFragment extends Fragment {
        private PublishSubject<List<String>> resultSubject = PublishSubject.create();
        private BehaviorSubject<Boolean> attachSubject = BehaviorSubject.create();

        public PublishSubject<List<String>> getResultSubject() {
            return resultSubject;
        }

        public BehaviorSubject<Boolean> getAttachSubject() {
            return attachSubject;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
                resultSubject.onNext(Matisse.obtainPathResult(data));
            }
        }

        @TargetApi(23)
        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            attachSubject.onNext(true);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            if (Build.VERSION.SDK_INT < 23) {
                attachSubject.onNext(true);
            }
        }
    }
}
