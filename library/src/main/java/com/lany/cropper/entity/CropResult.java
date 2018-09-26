package com.lany.cropper.entity;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.lany.cropper.enums.CropShape;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public final class CropResult implements Parcelable {

    /**
     * The image bitmap of the original image loaded for cropping.<br>
     * Null if uri used to load image or activity result is used.
     */
    private Bitmap originalBitmap;

    /**
     * The Android uri of the original image loaded for cropping.<br>
     * Null if bitmap was used to load image.
     */
    private Uri originalUri;

    /**
     * The cropped image bitmap result.<br>
     * Null if save cropped image was executed, no output requested or failure.
     */
    private Bitmap bitmap;

    /**
     * The Android uri of the saved cropped image result.<br>
     * Null if get cropped image was executed, no output requested or failure.
     */
    private Uri uri;

    /**
     * The error that failed the loading/cropping (null if successful)
     */
    private Exception error;

    /**
     * The 4 points of the cropping window in the source image
     */
    private float[] cropPoints;

    /**
     * The rectangle of the cropping window in the source image
     */
    private Rect cropRect;

    /**
     * The rectangle of the source image dimensions
     */
    private Rect wholeImageRect;

    /**
     * The final rotation of the cropped image relative to source
     */
    private int rotation;

    /**
     * sample size used creating the crop bitmap to lower its size
     */
    private int sampleSize;

    /**
     * The shape of the cropping area - rectangle/circular.
     */
    private CropShape cropShape;

    protected CropResult(Parcel in) {
        originalBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        originalUri = in.readParcelable(Uri.class.getClassLoader());
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        uri = in.readParcelable(Uri.class.getClassLoader());
        cropPoints = in.createFloatArray();
        cropRect = in.readParcelable(Rect.class.getClassLoader());
        wholeImageRect = in.readParcelable(Rect.class.getClassLoader());
        rotation = in.readInt();
        sampleSize = in.readInt();
        cropShape = in.readParcelable(CropShape.class.getClassLoader());
    }

    public static final Creator<CropResult> CREATOR = new Creator<CropResult>() {
        @Override
        public CropResult createFromParcel(Parcel in) {
            return new CropResult(in);
        }

        @Override
        public CropResult[] newArray(int size) {
            return new CropResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(originalBitmap, flags);
        dest.writeParcelable(originalUri, flags);
        dest.writeParcelable(bitmap, flags);
        dest.writeParcelable(uri, flags);
        dest.writeFloatArray(cropPoints);
        dest.writeParcelable(cropRect, flags);
        dest.writeParcelable(wholeImageRect, flags);
        dest.writeInt(rotation);
        dest.writeInt(sampleSize);
        dest.writeParcelable(cropShape, flags);
    }
}
