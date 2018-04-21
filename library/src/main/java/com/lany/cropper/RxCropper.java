package com.lany.cropper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.lany.cropper.ui.ResultHandlerFragment;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class RxCropper {
    private CropOptions mOptions;
    @Nullable
    private Uri mSourceUri;

    public static RxCropper of() {
        return new RxCropper();
    }

    public RxCropper setUri(Uri uri) {
        this.mSourceUri = uri;
        return this;
    }

    public RxCropper setOptions(CropOptions options) {
        this.mOptions = options;
        return this;
    }

    public Observable<CropResult> start(FragmentActivity activity) {
        return start(activity.getSupportFragmentManager());
    }

    public Observable<CropResult> start(Fragment fragment) {
        return start(fragment.getFragmentManager());
    }

    private Observable<CropResult> start(FragmentManager fragmentManager) {
        ResultHandlerFragment fragment = (ResultHandlerFragment) fragmentManager.findFragmentByTag(ResultHandlerFragment.class.getSimpleName());
        if (fragment == null) {
            fragment = ResultHandlerFragment.newInstance();
            fragmentManager.beginTransaction().add(fragment, fragment.getClass().getSimpleName()).commitAllowingStateLoss();
        } else if (fragment.isDetached()) {
            fragmentManager.beginTransaction().attach(fragment).commitAllowingStateLoss();
        }
        return getListItem(fragment);
    }

    private Observable<CropResult> getListItem(final ResultHandlerFragment finalFragment) {
        return finalFragment.getAttachSubject().filter(new Predicate<Boolean>() {
            @Override
            public boolean test(@NonNull Boolean aBoolean) {
                return aBoolean;
            }
        }).flatMap(new Function<Boolean, ObservableSource<CropResult>>() {
            @Override
            public ObservableSource<CropResult> apply(@NonNull Boolean aBoolean) {
                Intent intent = new Intent(finalFragment.getActivity(), CropImageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(CropImage.CROP_IMAGE_EXTRA_SOURCE, mSourceUri);
                bundle.putParcelable(CropImage.CROP_IMAGE_EXTRA_OPTIONS, mOptions);
                intent.putExtra(CropImage.CROP_IMAGE_EXTRA_BUNDLE, bundle);
                finalFragment.startActivityForResult(intent, ResultHandlerFragment.REQUEST_CODE);
                return finalFragment.getResultSubject();
            }
        }).take(1);
    }
}
