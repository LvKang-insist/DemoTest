package com.example.demotest.camera

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.demotest.utils.PathUtils
import com.gyf.immersionbar.ktx.hideStatusBar
import com.gyf.immersionbar.ktx.immersionBar
import com.luck.lib.camerax.CustomCameraConfig
import com.luck.lib.camerax.CustomCameraView
import com.luck.lib.camerax.SimpleCameraX
import com.luck.lib.camerax.listener.CameraListener
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * @name BaseCameraFragment
 * @package com.example.demotest.camera
 * @author 345 QQ:1831712732
 * @time 2022/02/24 22:41
 * @description
 */
abstract class BaseCameraFragment : Fragment() {

    private lateinit var mCameraView: CustomCameraView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mCameraView = CustomCameraView(requireContext())
        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        mCameraView.layoutParams = layoutParams
        mCameraView.post {
            val camera = SimpleCameraX.of()
            camera.setCameraMode(setCameraMode())
            camera.setVideoFrameRate(30)
            camera.setVideoBitRate(3 * 1024 * 1024)
            camera.isDisplayRecordChangeTime(true)
            camera.isManualFocusCameraPreview(true)
            camera.setRecordVideoMaxSecond(15)
            camera.isZoomCameraPreview(true)
            if (CustomCameraConfig.imageEngine == null) {
                camera.setImageEngine { context, url, imageView ->
                    Glide.with(context).load(url)
                        .into(imageView)
                }
            }
            camera.setOutputPathDir(outputPathDir())
            mCameraView.setCameraConfig(camera.getIntent(requireContext()))
        }
        return mCameraView
    }

    private fun initCamera() {
        immersionBar {
            hideStatusBar()
        }
        mCameraView.buildUseCameraCases()
        mCameraView.setImageCallbackListener { url, imageView ->
            CustomCameraConfig.imageEngine.loadImage(
                imageView?.context,
                url,
                imageView
            )
        }
        mCameraView.setCameraListener(object : CameraListener {
            override fun onPictureSuccess(url: String) = handleCameraSuccess(url)

            override fun onRecordSuccess(url: String) = handleCameraSuccess(url)

            override fun onError(
                videoCaptureError: Int, message: String,
                @Nullable cause: Throwable?
            ) {
                Log.e("---345--->", "BaseCameraFragment：$message")
            }
        })
        mCameraView.setOnCancelClickListener { handleCameraCancel() }

    }

    override fun onResume() {
        super.onResume()
        initCamera()
    }

    override fun onPause() {
        super.onPause()
        mCameraView.onStopPreview()
        mCameraView.onDestroy()
    }


    override fun onDestroy() {
        mCameraView.onDestroy()
        super.onDestroy()
    }

    /** 取消拍摄相关 */
    fun onCancelMedia() {
        mCameraView.onCancelMedia()
    }

    /**
     * 相机模式
     * @[CustomCameraConfig]
     * */
    abstract fun setCameraMode(): Int

    /** 保存地址 */
    abstract fun outputPathDir(): String

    /** 成功回调 */
    abstract fun handleCameraSuccess(url: String)

    /** 返回回调 */
    open fun handleCameraCancel() {
        requireActivity().finish()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
//        mCameraView.onConfigurationChanged(newConfig)
        initCamera()
    }

}