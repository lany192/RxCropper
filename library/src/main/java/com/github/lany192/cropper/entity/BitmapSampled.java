package com.github.lany192.cropper.entity;

import android.graphics.Bitmap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Holds bitmap instance and the sample size that the bitmap was loaded/cropped with.
 */
@Getter
@Setter
@AllArgsConstructor
public class BitmapSampled {
    /**
     * The bitmap instance
     */
    private Bitmap bitmap;

    /**
     * The sample size used to lower the size of the bitmap (1,2,4,8,...)
     */
    private int sampleSize;
}
