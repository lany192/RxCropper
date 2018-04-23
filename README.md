# RxCropper
To get a Git project into your build:

### Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }

### Step 2. Add the dependency

    dependencies {
        compile 'com.github.lany192:RxCropper:1.0.0'
    }

### Step 3. use

            Disposable disposable = RxCropper.of()
                    .setSourceUri(Uri.fromFile(new File(path)))
                    //.setCropShape(CropShape.OVAL)
                    //自由模式
    //                .setAspectRatio(1,1)
    //                .setFixAspectRatio(false)
                    //固定模式
                    .setAspectRatio(1,1)

                    .setBackgroundColor(Color.parseColor("#90000000"))
                    .start(this)
                    .subscribe(new Consumer<CropResult>() {
                        @Override
                        public void accept(CropResult result) {
                            //todo something
                        }
                    });
                    
### Step 4. Thanks

Fork from[ArthurHub/Android-Image-Cropper](https://github.com/ArthurHub/Android-Image-Cropper)