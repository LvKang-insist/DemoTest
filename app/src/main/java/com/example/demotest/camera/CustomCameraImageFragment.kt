package com.example.demotest.camera

import android.os.Bundle
import androidx.fragment.app.Fragment

import com.luck.picture.lib.permissions.PermissionResultCallback

import android.widget.Toast

import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.util.Log

import android.widget.RelativeLayout

import android.view.*
import androidx.annotation.Nullable
import com.example.demotest.GlideEngine
import com.luck.lib.camerax.CustomCameraConfig
import com.luck.lib.camerax.CustomCameraView
import com.luck.lib.camerax.listener.CameraListener
import com.luck.lib.camerax.SimpleCameraX
import com.luck.picture.lib.config.SelectMimeType
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import com.bumptech.glide.Glide
import com.example.demotest.utils.PathUtils
import com.gyf.immersionbar.ktx.hideStatusBar
import com.gyf.immersionbar.ktx.immersionBar

import com.luck.lib.camerax.CameraImageEngine


/**
 * @name CustomCameraFragment
 * @package com.example.demotest.camera
 * @author 345 QQ:1831712732
 * @time 2022/02/24 16:14
 * @description
 */
class CustomCameraImageFragment : BaseCameraFragment() {

    override fun setCameraMode(): Int = CustomCameraConfig.BUTTON_STATE_ONLY_CAPTURE

    override fun outputPathDir(): String = PathUtils.getCacheImagePath(requireContext())

    override fun handleCameraSuccess(url: String) {
        Log.e("---345--->", url)
        requireActivity().finish()
    }

    override fun handleCameraCancel() {
        super.handleCameraCancel()
    }
}