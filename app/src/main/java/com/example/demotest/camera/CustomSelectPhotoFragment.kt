package com.example.demotest.camera

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.demotest.CustomPictureSelectorStyle
import com.example.demotest.GlideEngine
import com.example.demotest.ImageCropEngine
import com.example.demotest.R
import com.example.demotest.databinding.CustomFragmentBinding
import com.gyf.immersionbar.ktx.hideStatusBar
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.showStatusBar
import com.luck.picture.lib.config.SelectMimeType

import com.luck.picture.lib.basic.PictureSelector

import com.luck.picture.lib.basic.IBridgePictureBehavior
import com.luck.picture.lib.basic.PictureCommonFragment
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import top.zibin.luban.Luban
import java.util.*


/**
 * @name CustomFragment
 * @package com.example.demotest
 * @author 345 QQ:1831712732
 * @time 2022/02/22 22:56
 * @description
 */
class CustomSelectPhotoFragment : Fragment(), IBridgePictureBehavior {
    lateinit var binding: CustomFragmentBinding


    private var isLazyLoad = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.custom_fragment, container, false)
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        if (!isLazyLoad) {
            isLazyLoad = true
            bindView()
        }
    }


    private fun bindView() {
        immersionBar {
            showStatusBar()
        }
        PictureSelector.create(this)
            .openGallery(SelectMimeType.ofAll())
            .setSelectorUIStyle(CustomPictureSelectorStyle(requireContext()))
            .setImageEngine(GlideEngine.createGlideEngine())
            .isDisplayCamera(false)
            .isPreviewAudio(true)
            .isPreviewImage(true)
            .isPreviewVideo(true)
            .setFilterVideoMaxSecond(15)
            .setCropEngine(ImageCropEngine())
            .isWithSelectVideoImage(true)//支持同时选择图片和视频
            .isPreviewFullScreenMode(true)
            .isEmptyResultReturn(true)
            .buildLaunch(R.id.fragment_container, object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: ArrayList<LocalMedia>?) {
                    result?.forEach {
                        if (PictureMimeType.isHasVideo(it.mimeType)) {
                            Toast.makeText(
                                context,
                                "视频 大小 ${(it.size / 1024.0 / 1024.0)}",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "图片 大小 ${(it.size / 1024.0 / 1024.0)}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    requireActivity().finish()
                }

                override fun onCancel() {
                    requireActivity().finish()
                }
            })
    }

    override fun onSelectFinish(result: PictureCommonFragment.SelectorResult?) {

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        bindView()
    }
}