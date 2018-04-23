package com.lany.cropper.entity;

import android.graphics.Rect;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Result data of Crop Image Activity.
 */
public final class ActivityResult extends CropResult implements Parcelable {

    public static final Creator<ActivityResult> CREATOR =
            new Creator<ActivityResult>() {
                @Override
                public ActivityResult createFromParcel(Parcel in) {
                    return new ActivityResult(in);
                }

                @Override
                public ActivityResult[] newArray(int size) {
                    return new ActivityResult[size];
                }
            };

    public ActivityResult(
            Uri originalUri,
            Uri uri,
            Exception error,
            float[] cropPoints,
            Rect cropRect,
            int rotation,
            Rect wholeImageRect,
            int sampleSize) {
        super(
                null,
                originalUri,
                null,
                uri,
                error,
                cropPoints,
                cropRect,
                wholeImageRect,
                rotation,
                sampleSize);
    }

    protected ActivityResult(Parcel in) {
        super(
                null,
                (Uri) in.readParcelable(Uri.class.getClassLoader()),
                null,
                (Uri) in.readParcelable(Uri.class.getClassLoader()),
                (Exception) in.readSerializable(),
                in.createFloatArray(),
                (Rect) in.readParcelable(Rect.class.getClassLoader()),
                (Rect) in.readParcelable(Rect.class.getClassLoader()),
                in.readInt(),
                in.readInt());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(getOriginalUri(), flags);
        dest.writeParcelable(getUri(), flags);
        dest.writeSerializable(getError());
        dest.writeFloatArray(getCropPoints());
        dest.writeParcelable(getCropRect(), flags);
        dest.writeParcelable(getWholeImageRect(), flags);
        dest.writeInt(getRotation());
        dest.writeInt(getSampleSize());
    }

    @Override
    public int describeContents() {
        return 0;
    }
}