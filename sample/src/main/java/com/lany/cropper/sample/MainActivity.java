package com.lany.cropper.sample;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lany.cropper.enums.CropShape;
import com.lany.cropper.enums.Guidelines;
import com.lany.cropper.enums.ScaleType;
import com.lany.picker.RxPicker;
import com.lany.picker.bean.ImageItem;

import java.io.File;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private MainFragment mCurrentFragment;
    private ConfigOptions mOptions = new ConfigOptions();

    public void setCurrentFragment(MainFragment fragment) {
        mCurrentFragment = fragment;
    }

    public void setCurrentOptions(ConfigOptions options) {
        mOptions = options;
        updateDrawerTogglesByOptions(options);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.main_drawer_open, R.string.main_drawer_close);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (savedInstanceState == null) {
            setMainFragmentByPreset(CropDemoPreset.RECT);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
        mCurrentFragment.updateCurrentCropViewOptions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (mCurrentFragment != null && mCurrentFragment.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NewApi")
    public void onDrawerOptionClicked(View view) {
        switch (view.getId()) {
            case R.id.drawer_option_load:
                Disposable disposable = RxPicker.of()
                        .single(true)
                        .start(MainActivity.this)
                        .subscribe(new Consumer<List<ImageItem>>() {
                            @Override
                            public void accept(@NonNull List<ImageItem> imageItems) {
                                String path = imageItems.get(0).getPath();
                                mCurrentFragment.setImageUri(Uri.fromFile(new File(path)));
                            }
                        });
                mDrawerLayout.closeDrawers();
                break;
            case R.id.drawer_option_oval:
                setMainFragmentByPreset(CropDemoPreset.CIRCULAR);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.drawer_option_rect:
                setMainFragmentByPreset(CropDemoPreset.RECT);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.drawer_option_customized_overlay:
                setMainFragmentByPreset(CropDemoPreset.CUSTOMIZED_OVERLAY);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.drawer_option_min_max_override:
                setMainFragmentByPreset(CropDemoPreset.MIN_MAX_OVERRIDE);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.drawer_option_scale_center:
                setMainFragmentByPreset(CropDemoPreset.SCALE_CENTER_INSIDE);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.drawer_option_toggle_scale:
                mOptions.scaleType =
                        mOptions.scaleType == ScaleType.FIT_CENTER
                                ? ScaleType.CENTER_INSIDE
                                : mOptions.scaleType == ScaleType.CENTER_INSIDE
                                ? ScaleType.CENTER
                                : mOptions.scaleType == ScaleType.CENTER
                                ? ScaleType.CENTER_CROP
                                : ScaleType.FIT_CENTER;
                mCurrentFragment.setCropImageViewOptions(mOptions);
                updateDrawerTogglesByOptions(mOptions);
                break;
            case R.id.drawer_option_toggle_shape:
                mOptions.cropShape =
                        mOptions.cropShape == CropShape.RECTANGLE
                                ? CropShape.OVAL
                                : CropShape.RECTANGLE;
                mCurrentFragment.setCropImageViewOptions(mOptions);
                updateDrawerTogglesByOptions(mOptions);
                break;
            case R.id.drawer_option_toggle_guidelines:
                mOptions.guidelines =
                        mOptions.guidelines == Guidelines.OFF
                                ? Guidelines.ON
                                : mOptions.guidelines == Guidelines.ON
                                ? Guidelines.ON_TOUCH
                                : Guidelines.OFF;
                mCurrentFragment.setCropImageViewOptions(mOptions);
                updateDrawerTogglesByOptions(mOptions);
                break;
            case R.id.drawer_option_toggle_aspect_ratio:
                if (!mOptions.fixAspectRatio) {
                    mOptions.fixAspectRatio = true;
                    mOptions.aspectRatio = new Pair<>(1, 1);
                } else {
                    if (mOptions.aspectRatio.first == 1
                            && mOptions.aspectRatio.second == 1) {
                        mOptions.aspectRatio = new Pair<>(4, 3);
                    } else if (mOptions.aspectRatio.first == 4
                            && mOptions.aspectRatio.second == 3) {
                        mOptions.aspectRatio = new Pair<>(16, 9);
                    } else if (mOptions.aspectRatio.first == 16
                            && mOptions.aspectRatio.second == 9) {
                        mOptions.aspectRatio = new Pair<>(9, 16);
                    } else {
                        mOptions.fixAspectRatio = false;
                    }
                }
                mCurrentFragment.setCropImageViewOptions(mOptions);
                updateDrawerTogglesByOptions(mOptions);
                break;
            case R.id.drawer_option_toggle_auto_zoom:
                mOptions.autoZoomEnabled = !mOptions.autoZoomEnabled;
                mCurrentFragment.setCropImageViewOptions(mOptions);
                updateDrawerTogglesByOptions(mOptions);
                break;
            case R.id.drawer_option_toggle_max_zoom:
                mOptions.maxZoomLevel =
                        mOptions.maxZoomLevel == 4
                                ? 8
                                : mOptions.maxZoomLevel == 8 ? 2 : 4;
                mCurrentFragment.setCropImageViewOptions(mOptions);
                updateDrawerTogglesByOptions(mOptions);
                break;
            case R.id.drawer_option_set_initial_crop_rect:
                mCurrentFragment.setInitialCropRect();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.drawer_option_reset_crop_rect:
                mCurrentFragment.resetCropRect();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.drawer_option_toggle_multitouch:
                mOptions.multitouch = !mOptions.multitouch;
                mCurrentFragment.setCropImageViewOptions(mOptions);
                updateDrawerTogglesByOptions(mOptions);
                break;
            case R.id.drawer_option_toggle_show_overlay:
                mOptions.showCropOverlay = !mOptions.showCropOverlay;
                mCurrentFragment.setCropImageViewOptions(mOptions);
                updateDrawerTogglesByOptions(mOptions);
                break;
            case R.id.drawer_option_toggle_show_progress_bar:
                mOptions.showProgressBar = !mOptions.showProgressBar;
                mCurrentFragment.setCropImageViewOptions(mOptions);
                updateDrawerTogglesByOptions(mOptions);
                break;
            default:
                Toast.makeText(this, "Unknown drawer option clicked", Toast.LENGTH_LONG).show();
        }
    }

    private void setMainFragmentByPreset(CropDemoPreset demoPreset) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, MainFragment.newInstance(demoPreset))
                .commit();
    }

    private void updateDrawerTogglesByOptions(ConfigOptions options) {
        ((TextView) findViewById(R.id.drawer_option_toggle_scale))
                .setText(
                        getResources()
                                .getString(R.string.drawer_option_toggle_scale, options.scaleType.name()));
        ((TextView) findViewById(R.id.drawer_option_toggle_shape))
                .setText(
                        getResources()
                                .getString(R.string.drawer_option_toggle_shape, options.cropShape.name()));
        ((TextView) findViewById(R.id.drawer_option_toggle_guidelines))
                .setText(
                        getResources()
                                .getString(R.string.drawer_option_toggle_guidelines, options.guidelines.name()));
        ((TextView) findViewById(R.id.drawer_option_toggle_multitouch))
                .setText(
                        getResources()
                                .getString(
                                        R.string.drawer_option_toggle_multitouch,
                                        Boolean.toString(options.multitouch)));
        ((TextView) findViewById(R.id.drawer_option_toggle_show_overlay))
                .setText(
                        getResources()
                                .getString(
                                        R.string.drawer_option_toggle_show_overlay,
                                        Boolean.toString(options.showCropOverlay)));
        ((TextView) findViewById(R.id.drawer_option_toggle_show_progress_bar))
                .setText(
                        getResources()
                                .getString(
                                        R.string.drawer_option_toggle_show_progress_bar,
                                        Boolean.toString(options.showProgressBar)));

        String aspectRatio = "FREE";
        if (options.fixAspectRatio) {
            aspectRatio = options.aspectRatio.first + ":" + options.aspectRatio.second;
        }
        ((TextView) findViewById(R.id.drawer_option_toggle_aspect_ratio))
                .setText(getResources().getString(R.string.drawer_option_toggle_aspect_ratio, aspectRatio));

        ((TextView) findViewById(R.id.drawer_option_toggle_auto_zoom))
                .setText(
                        getResources()
                                .getString(
                                        R.string.drawer_option_toggle_auto_zoom,
                                        options.autoZoomEnabled ? "Enabled" : "Disabled"));
        ((TextView) findViewById(R.id.drawer_option_toggle_max_zoom))
                .setText(
                        getResources().getString(R.string.drawer_option_toggle_max_zoom, options.maxZoomLevel));
    }
}
