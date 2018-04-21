package com.lany.cropper;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CropResult {

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

}
