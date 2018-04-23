

package com.lany.cropper.entity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.lany.cropper.enums.CropShape;
import com.lany.cropper.enums.Guidelines;
import com.lany.cropper.enums.RequestSizeOptions;
import com.lany.cropper.enums.ScaleType;
import com.lany.cropper.ui.CropImageActivity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CropOptions implements Parcelable {

    public static final Creator<CropOptions> CREATOR =
            new Creator<CropOptions>() {
                @Override
                public CropOptions createFromParcel(Parcel in) {
                    return new CropOptions(in);
                }

                @Override
                public CropOptions[] newArray(int size) {
                    return new CropOptions[size];
                }
            };

    /**
     * The shape of the cropping window.
     */
    private CropShape cropShape;

    /**
     * An edge of the crop window will snap to the corresponding edge of a specified bounding box when
     * the crop window edge is less than or equal to this distance (in pixels) away from the bounding
     * box edge. (in pixels)
     */
    private float snapRadius;

    /**
     * The radius of the touchable area around the handle. (in pixels)<br>
     * We are basing this value off of the recommended 48dp Rhythm.<br>
     * See: http://developer.android.com/design/style/metrics-grids.html#48dp-rhythm
     */
    private float touchRadius;

    /**
     * whether the guidelines should be on, off, or only showing when resizing.
     */
    private Guidelines guidelines;

    /**
     * The initial scale type of the image in the crop image view
     */
    private ScaleType scaleType;

    /**
     * if to show crop overlay UI what contains the crop window UI surrounded by background over the
     * cropping image.<br>
     * default: true, may disable for animation or frame transition.
     */
    private boolean showCropOverlay;

    /**
     * if to show progress bar when image async loading/cropping is in progress.<br>
     * default: true, disable to provide custom progress bar UI.
     */
    private boolean showProgressBar;

    /**
     * if auto-zoom functionality is enabled.<br>
     * default: true.
     */
    private boolean autoZoomEnabled;

    /**
     * if multi-touch should be enabled on the crop box default: false
     */
    private boolean multiTouchEnabled;

    /**
     * The max zoom allowed during cropping.
     */
    private int maxZoom;

    /**
     * The initial crop window padding from image borders in percentage of the cropping image
     * dimensions.
     */
    private float initialCropWindowPaddingRatio;

    /**
     * whether the width to height aspect ratio should be maintained or free to change.
     */
    private boolean fixAspectRatio;

    /**
     * the X value of the aspect ratio.
     */
    private int aspectRatioX;

    /**
     * the Y value of the aspect ratio.
     */
    private int aspectRatioY;

    /**
     * the thickness of the guidelines lines in pixels. (in pixels)
     */
    private float borderLineThickness;

    /**
     * the color of the guidelines lines
     */
    private int borderLineColor;

    /**
     * thickness of the corner line. (in pixels)
     */
    private float borderCornerThickness;

    /**
     * the offset of corner line from crop window border. (in pixels)
     */
    private float borderCornerOffset;

    /**
     * the length of the corner line away from the corner. (in pixels)
     */
    private float borderCornerLength;

    /**
     * the color of the corner line
     */
    private int borderCornerColor;

    /**
     * the thickness of the guidelines lines. (in pixels)
     */
    private float guidelinesThickness;

    /**
     * the color of the guidelines lines
     */
    private int guidelinesColor;

    /**
     * the color of the overlay background around the crop window cover the image parts not in the
     * crop window.
     */
    private int backgroundColor;

    /**
     * the min width the crop window is allowed to be. (in pixels)
     */
    private int minCropWindowWidth;

    /**
     * the min height the crop window is allowed to be. (in pixels)
     */
    private int minCropWindowHeight;

    /**
     * the min width the resulting cropping image is allowed to be, affects the cropping window
     * limits. (in pixels)
     */
    private int minCropResultWidth;

    /**
     * the min height the resulting cropping image is allowed to be, affects the cropping window
     * limits. (in pixels)
     */
    private int minCropResultHeight;

    /**
     * the max width the resulting cropping image is allowed to be, affects the cropping window
     * limits. (in pixels)
     */
    private int maxCropResultWidth;

    /**
     * the max height the resulting cropping image is allowed to be, affects the cropping window
     * limits. (in pixels)
     */
    private int maxCropResultHeight;

    /**
     * the title of the {@link CropImageActivity}
     */
    private CharSequence activityTitle;

    /**
     * the color to use for action bar items icons
     */
    private int activityMenuIconColor;

    /**
     * the Android Uri to save the cropped image to
     */
    private Uri outputUri;

    /**
     * the compression format to use when writing the image
     */
    private Bitmap.CompressFormat outputCompressFormat;

    /**
     * the quality (if applicable) to use when writing the image (0 - 100)
     */
    private int outputCompressQuality;

    /**
     * the width to resize the cropped image to (see options)
     */
    private int outputRequestWidth;

    /**
     * the height to resize the cropped image to (see options)
     */
    private int outputRequestHeight;

    /**
     * the resize method to use on the cropped bitmap (see options documentation)
     */
    private RequestSizeOptions outputRequestSizeOptions;

    /**
     * if the result of crop image activity should not save the cropped image bitmap
     */
    private boolean noOutputImage;

    /**
     * the initial rectangle to set on the cropping image after loading
     */
    private Rect initialCropWindowRectangle;

    /**
     * the initial rotation to set on the cropping image after loading (0-360 degrees clockwise)
     */
    private int initialRotation;

    /**
     * if to allow (all) rotation during cropping (activity)
     */
    private boolean allowRotation;

    /**
     * if to allow (all) flipping during cropping (activity)
     */
    private boolean allowFlipping;

    /**
     * if to allow counter-clockwise rotation during cropping (activity)
     */
    private boolean allowCounterRotation;

    /**
     * the amount of degrees to rotate clockwise or counter-clockwise
     */
    private int rotationDegrees;

    /**
     * whether the image should be flipped horizontally
     */
    private boolean flipHorizontally;

    /**
     * whether the image should be flipped vertically
     */
    private boolean flipVertically;

    /**
     * optional, the text of the crop menu crop button
     */
    private CharSequence cropMenuCropButtonTitle;

    /**
     * optional image resource to be used for crop menu crop icon instead of text
     */
    private int cropMenuCropButtonIcon;

    /**
     * Init options with defaults.
     */
    public CropOptions() {

        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();

        cropShape = CropShape.RECTANGLE;
        snapRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, dm);
        touchRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, dm);
        guidelines = Guidelines.ON_TOUCH;
        scaleType = ScaleType.FIT_CENTER;
        showCropOverlay = true;
        showProgressBar = true;
        autoZoomEnabled = true;
        multiTouchEnabled = false;
        maxZoom = 4;
        initialCropWindowPaddingRatio = 0.1f;

        fixAspectRatio = false;
        aspectRatioX = 1;
        aspectRatioY = 1;

        borderLineThickness = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, dm);
        borderLineColor = Color.argb(170, 255, 255, 255);
        borderCornerThickness = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, dm);
        borderCornerOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, dm);
        borderCornerLength = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, dm);
        borderCornerColor = Color.WHITE;

        guidelinesThickness = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, dm);
        guidelinesColor = Color.argb(170, 255, 255, 255);
        backgroundColor = Color.argb(119, 0, 0, 0);

        minCropWindowWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 42, dm);
        minCropWindowHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 42, dm);
        minCropResultWidth = 40;
        minCropResultHeight = 40;
        maxCropResultWidth = 99999;
        maxCropResultHeight = 99999;

        activityTitle = "";
        activityMenuIconColor = 0;

        outputUri = Uri.EMPTY;
        outputCompressFormat = Bitmap.CompressFormat.JPEG;
        outputCompressQuality = 90;
        outputRequestWidth = 0;
        outputRequestHeight = 0;
        outputRequestSizeOptions = RequestSizeOptions.NONE;
        noOutputImage = false;

        initialCropWindowRectangle = null;
        initialRotation = -1;
        allowRotation = true;
        allowFlipping = true;
        allowCounterRotation = false;
        rotationDegrees = 90;
        flipHorizontally = false;
        flipVertically = false;
        cropMenuCropButtonTitle = null;

        cropMenuCropButtonIcon = 0;
    }

    /**
     * Create object from parcel.
     */
    protected CropOptions(Parcel in) {
        cropShape = CropShape.values()[in.readInt()];
        snapRadius = in.readFloat();
        touchRadius = in.readFloat();
        guidelines = Guidelines.values()[in.readInt()];
        scaleType = ScaleType.values()[in.readInt()];
        showCropOverlay = in.readByte() != 0;
        showProgressBar = in.readByte() != 0;
        autoZoomEnabled = in.readByte() != 0;
        multiTouchEnabled = in.readByte() != 0;
        maxZoom = in.readInt();
        initialCropWindowPaddingRatio = in.readFloat();
        fixAspectRatio = in.readByte() != 0;
        aspectRatioX = in.readInt();
        aspectRatioY = in.readInt();
        borderLineThickness = in.readFloat();
        borderLineColor = in.readInt();
        borderCornerThickness = in.readFloat();
        borderCornerOffset = in.readFloat();
        borderCornerLength = in.readFloat();
        borderCornerColor = in.readInt();
        guidelinesThickness = in.readFloat();
        guidelinesColor = in.readInt();
        backgroundColor = in.readInt();
        minCropWindowWidth = in.readInt();
        minCropWindowHeight = in.readInt();
        minCropResultWidth = in.readInt();
        minCropResultHeight = in.readInt();
        maxCropResultWidth = in.readInt();
        maxCropResultHeight = in.readInt();
        activityTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        activityMenuIconColor = in.readInt();
        outputUri = in.readParcelable(Uri.class.getClassLoader());
        outputCompressFormat = Bitmap.CompressFormat.valueOf(in.readString());
        outputCompressQuality = in.readInt();
        outputRequestWidth = in.readInt();
        outputRequestHeight = in.readInt();
        outputRequestSizeOptions = RequestSizeOptions.values()[in.readInt()];
        noOutputImage = in.readByte() != 0;
        initialCropWindowRectangle = in.readParcelable(Rect.class.getClassLoader());
        initialRotation = in.readInt();
        allowRotation = in.readByte() != 0;
        allowFlipping = in.readByte() != 0;
        allowCounterRotation = in.readByte() != 0;
        rotationDegrees = in.readInt();
        flipHorizontally = in.readByte() != 0;
        flipVertically = in.readByte() != 0;
        cropMenuCropButtonTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        cropMenuCropButtonIcon = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cropShape.ordinal());
        dest.writeFloat(snapRadius);
        dest.writeFloat(touchRadius);
        dest.writeInt(guidelines.ordinal());
        dest.writeInt(scaleType.ordinal());
        dest.writeByte((byte) (showCropOverlay ? 1 : 0));
        dest.writeByte((byte) (showProgressBar ? 1 : 0));
        dest.writeByte((byte) (autoZoomEnabled ? 1 : 0));
        dest.writeByte((byte) (multiTouchEnabled ? 1 : 0));
        dest.writeInt(maxZoom);
        dest.writeFloat(initialCropWindowPaddingRatio);
        dest.writeByte((byte) (fixAspectRatio ? 1 : 0));
        dest.writeInt(aspectRatioX);
        dest.writeInt(aspectRatioY);
        dest.writeFloat(borderLineThickness);
        dest.writeInt(borderLineColor);
        dest.writeFloat(borderCornerThickness);
        dest.writeFloat(borderCornerOffset);
        dest.writeFloat(borderCornerLength);
        dest.writeInt(borderCornerColor);
        dest.writeFloat(guidelinesThickness);
        dest.writeInt(guidelinesColor);
        dest.writeInt(backgroundColor);
        dest.writeInt(minCropWindowWidth);
        dest.writeInt(minCropWindowHeight);
        dest.writeInt(minCropResultWidth);
        dest.writeInt(minCropResultHeight);
        dest.writeInt(maxCropResultWidth);
        dest.writeInt(maxCropResultHeight);
        TextUtils.writeToParcel(activityTitle, dest, flags);
        dest.writeInt(activityMenuIconColor);
        dest.writeParcelable(outputUri, flags);
        dest.writeString(outputCompressFormat.name());
        dest.writeInt(outputCompressQuality);
        dest.writeInt(outputRequestWidth);
        dest.writeInt(outputRequestHeight);
        dest.writeInt(outputRequestSizeOptions.ordinal());
        dest.writeInt(noOutputImage ? 1 : 0);
        dest.writeParcelable(initialCropWindowRectangle, flags);
        dest.writeInt(initialRotation);
        dest.writeByte((byte) (allowRotation ? 1 : 0));
        dest.writeByte((byte) (allowFlipping ? 1 : 0));
        dest.writeByte((byte) (allowCounterRotation ? 1 : 0));
        dest.writeInt(rotationDegrees);
        dest.writeByte((byte) (flipHorizontally ? 1 : 0));
        dest.writeByte((byte) (flipVertically ? 1 : 0));
        TextUtils.writeToParcel(cropMenuCropButtonTitle, dest, flags);
        dest.writeInt(cropMenuCropButtonIcon);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Validate all the options are withing valid range.
     *
     * @throws IllegalArgumentException if any of the options is not valid
     */
    public void validate() {
        if (maxZoom < 0) {
            throw new IllegalArgumentException("Cannot set max zoom to a number < 1");
        }
        if (touchRadius < 0) {
            throw new IllegalArgumentException("Cannot set touch radius value to a number <= 0 ");
        }
        if (initialCropWindowPaddingRatio < 0 || initialCropWindowPaddingRatio >= 0.5) {
            throw new IllegalArgumentException(
                    "Cannot set initial crop window padding value to a number < 0 or >= 0.5");
        }
        if (aspectRatioX <= 0) {
            throw new IllegalArgumentException(
                    "Cannot set aspect ratio value to a number less than or equal to 0.");
        }
        if (aspectRatioY <= 0) {
            throw new IllegalArgumentException(
                    "Cannot set aspect ratio value to a number less than or equal to 0.");
        }
        if (borderLineThickness < 0) {
            throw new IllegalArgumentException(
                    "Cannot set line thickness value to a number less than 0.");
        }
        if (borderCornerThickness < 0) {
            throw new IllegalArgumentException(
                    "Cannot set corner thickness value to a number less than 0.");
        }
        if (guidelinesThickness < 0) {
            throw new IllegalArgumentException(
                    "Cannot set guidelines thickness value to a number less than 0.");
        }
        if (minCropWindowHeight < 0) {
            throw new IllegalArgumentException(
                    "Cannot set min crop window height value to a number < 0 ");
        }
        if (minCropResultWidth < 0) {
            throw new IllegalArgumentException("Cannot set min crop result width value to a number < 0 ");
        }
        if (minCropResultHeight < 0) {
            throw new IllegalArgumentException(
                    "Cannot set min crop result height value to a number < 0 ");
        }
        if (maxCropResultWidth < minCropResultWidth) {
            throw new IllegalArgumentException(
                    "Cannot set max crop result width to smaller value than min crop result width");
        }
        if (maxCropResultHeight < minCropResultHeight) {
            throw new IllegalArgumentException(
                    "Cannot set max crop result height to smaller value than min crop result height");
        }
        if (outputRequestWidth < 0) {
            throw new IllegalArgumentException("Cannot set request width value to a number < 0 ");
        }
        if (outputRequestHeight < 0) {
            throw new IllegalArgumentException("Cannot set request height value to a number < 0 ");
        }
        if (rotationDegrees < 0 || rotationDegrees > 360) {
            throw new IllegalArgumentException(
                    "Cannot set rotation degrees value to a number < 0 or > 360");
        }
    }
}
