[![](https://jitpack.io/v/lany192/RxCropper.svg)](https://jitpack.io/#lany192/RxCropper)

# RxCropper

To get a Git project into your build:

### Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }

### Add the dependency

    dependencies {
        implementation 'com.github.lany192:RxCropper:1.0.2'
    }

### Usage
        String path = "...picture file path...";
        Disposable disposable = RxCropper.of()
                .setSourceUri(Uri.fromFile(new File(path)))
                //.setCropShape(CropShape.RECTANGLE)
                //.setGuidelines(Guidelines.ON_TOUCH)
                //.setBorderCornerColor(Color.GREEN)
                //.setBorderLineColor(Color.RED)
                //.setGuidelinesColor(Color.BLUE)
                //.setScaleType(ScaleType.CENTER)
                //.setInitialCropWindowPaddingRatio(0.1f)
                //.setFlipHorizontally(true)
                //.setRotationDegrees(90)
                //.setFixAspectRatio(false)
                .setAspectRatio(1, 1)
                //.setBackgroundColor(Color.parseColor("#90000000"))
                .start(this)
                .subscribe(new Consumer<CropResult>() {
                    @Override
                    public void accept(CropResult result) {
                        Log.i(TAG, "crop result: " + result);
                        handleCropResult(result);
                    }
                });
                    
# License

Originally forked from [ArthurHub/Android-Image-Cropper](https://github.com/ArthurHub/Android-Image-Cropper).

Copyright 2018 - YG.Lan ,2016 - Arthur Teplitzki, 2013 - Edmodo, Inc.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the License. You may obtain a copy of the License in the LICENSE file, or at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.