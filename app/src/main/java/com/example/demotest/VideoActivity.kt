package com.example.demotest

import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.demotest.camera.CustomCameraImageFragment
import com.example.demotest.camera.CustomCameraVideoFragment
import com.example.demotest.camera.CustomSelectPhotoFragment
import com.example.demotest.databinding.ActivityVideoBinding
import com.example.demotest.utils.ZipUtils
import com.gyf.immersionbar.ktx.immersionBar
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import java.io.File
import kotlin.math.min

/**
 * @name videoActivity
 * @package com.example.demotest
 * @author 345 QQ:1831712732
 * @time 2022/02/22 16:53
 * @description
 */
class VideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoBinding

    private var videoFragment: CustomCameraVideoFragment? = null
    private var imageFragment: CustomCameraImageFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video)
        initBar()
        initTabIndicator()
        setTab()


//        lifecycleScope.launch(Dispatchers.IO) {
//            ZipUtils.zipUtils(
//                this@VideoActivity,
//                "/data/user/0/com.example.demotest/cache/VideoCache/VID_20220225162449146.mp4",
//                process = {
//                    Log.e("---345--->", "process ${(it * 100f).toInt()}%");
//                },
//                success = {
//                    Log.e("---345--->", "成功 $it");
//                },
//                error = {
//                    Log.e("---345--->", "失败 $it");
//                }
//            )
//        }
    }

    fun initTabIndicator() {
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter =
            IndicatorNavAdapter(arrayListOf("相册", "拍视频", "拍照"), color = R.color.white, onClick = {
                binding.viewpager.currentItem = it
            })
        binding.indicator.navigator = commonNavigator
        ViewPager2Helper.bind(binding.indicator, binding.viewpager)
        setTab()
    }

    private fun initBar() {
        immersionBar {
            statusBarColor(R.color.transparent)
            statusBarDarkFont(false)
        }
    }


    private fun setTab() {
        binding.viewpager.isUserInputEnabled = false
        binding.viewpager.adapter =
            object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
                override fun getItemCount(): Int = 3

                override fun createFragment(position: Int): Fragment = when (position) {
                    0 -> CustomSelectPhotoFragment()
                    1 -> {
                        videoFragment = CustomCameraVideoFragment()
                        videoFragment!!
                    }
                    else -> {
                        imageFragment = CustomCameraImageFragment()
                        imageFragment!!
                    }
                }
            }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        Log.e("---345--->", "---- ${supportFragmentManager.backStackEntryCount}");
        if (binding.viewpager.currentItem == 0) {
            if (supportFragmentManager.backStackEntryCount == 1) {
                finish()
                return false
            }
        } else {
            //释放资源
            videoFragment?.onCancelMedia()
            imageFragment?.onCancelMedia()
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }

    //
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.login_finish_in, R.anim.login_finish_out)
    }

}