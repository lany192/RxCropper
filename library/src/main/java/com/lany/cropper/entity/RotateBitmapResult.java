package com.lany.cropper.entity;

import android.graphics.Bitmap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RotateBitmapResult {
    /**
     * The loaded bitmap
     */
    private Bitmap bitmap;

    /**
     * The degrees the image was rotated
     */
    private int degrees;
}
