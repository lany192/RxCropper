
package com.lany.cropper.sample;

import android.util.Pair;

import com.lany.cropper.enums.CropShape;
import com.lany.cropper.enums.Guidelines;
import com.lany.cropper.enums.ScaleType;

/**
 * The crop image view options that can be changed live.
 */
final class ConfigOptions {

    public ScaleType scaleType = ScaleType.CENTER_INSIDE;

    public CropShape cropShape = CropShape.RECTANGLE;

    public Guidelines guidelines = Guidelines.ON_TOUCH;

    public Pair<Integer, Integer> aspectRatio = new Pair<>(1, 1);

    public boolean autoZoomEnabled;

    public int maxZoomLevel;

    public boolean fixAspectRatio;

    public boolean multitouch;

    public boolean showCropOverlay;

    public boolean showProgressBar;

    public boolean flipHorizontally;

    public boolean flipVertically;
}
