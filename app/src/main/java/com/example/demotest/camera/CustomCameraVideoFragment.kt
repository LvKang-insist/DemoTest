package com.example.demotest.camera

import android.os.Bundle
import androidx.fragment.app.Fragment


import android.widget.Toast

import android.content.res.Configuration
import android.util.Log

import android.widget.RelativeLayout

import android.view.*
import androidx.annotation.Nullable
import androidx.lifecycle.lifecycleScope
import com.luck.lib.camerax.CustomCameraConfig
import com.luck.lib.camerax.CustomCameraView
import com.luck.lib.camerax.listener.CameraListener
import com.luck.lib.camerax.SimpleCameraX
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import com.bumptech.glide.Glide
import com.example.demotest.utils.PathUtils


/**
 * @name CustomCameraFragment
 * @package com.example.demotest.camera
 * @author 345 QQ:1831712732
 * @time 2022/02/24 16:14
 * @description
 */
class CustomCameraVideoFragment : BaseCameraFragment() {

    override fun setCameraMode(): Int = CustomCameraConfig.BUTTON_STATE_ONLY_RECORDER

    override fun outputPathDir(): String = PathUtils.getCacheVideoPath(requireContext())

    override fun handleCameraSuccess(url: String) {
        Log.e("---345--->", url)
    }

    override fun handleCameraCancel() {
        super.handleCameraCancel()
    }
}