package com.example.demotest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.demotest.camera.BaseCameraFragment;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.engine.CropEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.style.PictureSelectorStyle;

import com.luck.picture.lib.utils.DateUtils;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropImageEngine;

import java.io.File;
import java.util.ArrayList;

/**
 * @author 345 QQ:1831712732
 * @name ImageCropEngine
 * @package com.example.demotest
 * @time 2022/02/23 14:29
 * @description
 */
public class ImageCropEngine implements CropEngine {

    @Override
    public void onStartCrop(Fragment fragment, LocalMedia currentLocalMedia,
                            ArrayList<LocalMedia> dataSource, int requestCode) {
        String currentCropPath = currentLocalMedia.getAvailablePath();
        Uri inputUri;
        if (PictureMimeType.isContent(currentCropPath) || PictureMimeType.isHasHttp(currentCropPath)) {
            inputUri = Uri.parse(currentCropPath);
        } else {
            inputUri = Uri.fromFile(new File(currentCropPath));
        }
        String fileName = DateUtils.getCreateFileName("CROP_") + ".jpg";
        Uri destinationUri = Uri.fromFile(new File(getSandboxPath(fragment.requireContext()), fileName));
        UCrop.Options options = buildOptions(fragment.requireContext());
        ArrayList<String> dataCropSource = new ArrayList<>();
        for (int i = 0; i < dataSource.size(); i++) {
            LocalMedia media = dataSource.get(i);
            dataCropSource.add(media.getAvailablePath());
        }
        UCrop uCrop = UCrop.of(inputUri, destinationUri, dataCropSource);
        //options.setMultipleCropAspectRatio(buildAspectRatios(dataSource.size()));
        uCrop.withOptions(options);
        uCrop.setImageEngine(new UCropImageEngine() {
            @Override
            public void loadImage(Context context, String url, ImageView imageView) {
                if (!ImageLoaderUtils.assertValidRequest(context)) {
                    return;
                }
                Glide.with(context).load(url).into(imageView);
            }

            @Override
            public void loadImage(Context context, Uri url, int maxWidth, int maxHeight, OnCallbackListener<Bitmap> call) {
                if (!ImageLoaderUtils.assertValidRequest(context)) {
                    return;
                }
                Glide.with(context).asBitmap().load(url).into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        if (call != null) {
                            call.onCall(resource);
                        }
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        if (call != null) {
                            call.onCall(null);
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
            }
        });
        uCrop.start(fragment.requireActivity(), fragment, requestCode);
    }

    private String getSandboxPath(Context context) {
        File externalFilesDir = context.getExternalFilesDir("");
        File customFile = new File(externalFilesDir.getAbsolutePath(), "Sandbox");
        if (!customFile.exists()) {
            customFile.mkdirs();
        }
        return customFile.getAbsolutePath() + File.separator;
    }

    PictureSelectorStyle selectorStyle = new PictureSelectorStyle();

    private UCrop.Options buildOptions(Context context) {
        UCrop.Options options = new UCrop.Options();

        options.setHideBottomControls(true);//隐藏底部控制
        options.setFreeStyleCropEnabled(false);//设置为 true 让用户调整裁剪范围
        options.setShowCropFrame(true);//如果您想在图像顶部看到裁剪框矩形，则设置为 true
        options.setShowCropGrid(true);//如果您想在图像顶部看到裁剪网格/指南，请设置为 true
        options.setCircleDimmedLayer(false);//如果你想让暗色图层在里面有一个圆圈，请将其设置为 true
//        options.withAspectRatio(aspect_ratio_x, aspect_ratio_y);//设置裁剪范围的纵横比。用户不会看到带有其他比率选项的菜单
//        options.setCropOutputPathDir(getSandboxPath());//裁剪多张图片时将用于保存的路径 多张图片裁剪时有效
        options.isCropDragSmoothToCenter(true);//自动裁剪和拖动居中
        options.isUseCustomLoaderBitmap(true);//使用自定义加载器获取 uCrop 资源的位图
        options.setSkipCropMimeType(getNotSupportCrop());//跳过裁剪 mimeType
        options.isForbidCropGifWebp(true);// 需要支持裁剪动态图gif还是webp
        options.isForbidSkipMultipleCrop(false);//切割多张图纸时禁止跳过
//        options.setMaxScaleMultiplier(100);//此方法设置乘数，用于从最小图像比例计算最大图像比例

        options.setStatusBarColor(ContextCompat.getColor(context, R.color.ps_color_black));
        options.setRootViewBackgroundColor(ContextCompat.getColor(context, R.color.ps_color_black));
        options.setToolbarColor(ContextCompat.getColor(context, R.color.ps_color_black));
        options.setToolbarWidgetColor(ContextCompat.getColor(context, R.color.ps_color_white));

//        if (selectorStyle != null && selectorStyle.getSelectMainStyle().getStatusBarColor() != 0) {
//            SelectMainStyle mainStyle = selectorStyle.getSelectMainStyle();
//            boolean isDarkStatusBarBlack = mainStyle.isDarkStatusBarBlack();
//            int statusBarColor = mainStyle.getStatusBarColor();
//            options.isDarkStatusBarBlack(isDarkStatusBarBlack);
//            if (StyleUtils.checkStyleValidity(statusBarColor)) {
//                options.setStatusBarColor(statusBarColor);
//                options.setToolbarColor(statusBarColor);
//            } else {
//                options.setStatusBarColor(ContextCompat.getColor(context, R.color.ps_color_grey));
//                options.setToolbarColor(ContextCompat.getColor(context, R.color.ps_color_grey));
//            }
//            TitleBarStyle titleBarStyle = selectorStyle.getTitleBarStyle();
//            if (StyleUtils.checkStyleValidity(titleBarStyle.getTitleTextColor())) {
//                options.setToolbarWidgetColor(titleBarStyle.getTitleTextColor());
//            } else {
//                options.setToolbarWidgetColor(ContextCompat.getColor(context, R.color.ps_color_white));
//            }
//        } else {
//            options.setStatusBarColor(ContextCompat.getColor(context, R.color.ps_color_grey));
//            options.setToolbarColor(ContextCompat.getColor(context, R.color.ps_color_grey));
//            options.setToolbarWidgetColor(ContextCompat.getColor(context, R.color.ps_color_white));
//        }
        return options;
    }

    private String[] getNotSupportCrop() {
        if (false) {
            return new String[]{PictureMimeType.ofGIF(), PictureMimeType.ofWEBP()};
        }
        return null;
    }

}


