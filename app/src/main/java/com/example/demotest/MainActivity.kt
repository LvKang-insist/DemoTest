package com.example.demotest

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.demotest.databinding.ActivityMainBinding
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var activity: MainActivity? = null

    private lateinit var binding: ActivityMainBinding

//    private val tabList =
//        arrayListOf(
//            "推荐"
//        )

    private val tabList =
        arrayListOf(
            "推荐", "视频", "直播", "箱包", "校园", "搞笑", "萌宠", "知识科普",
            "时尚穿搭", "发型", "体育", "Vlog", "旅行", "食谱", "情感", "汽车", "动漫"
        )
    private val fragments = arrayListOf<Fragment>()

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        initBar()
//        initTabIndicator()
//        setTab()
//
//        Dialog(this)
        lifecycleScope.launch{
            launch (Dispatchers.IO){
                binding.downImage.setImageResource(R.drawable.blur_bg)
            }
        }
        binding.downImage.post {

        }
        binding.downImage.setOnClickListener {
            val pop = TestPopWindow(this)
            pop.setAlignBackground(true)
            pop.setAlignBackgroundGravity(Gravity.TOP)
            pop.showPopupWindow(binding.title)

            System.gc()
//            Toast.makeText(this, "哈哈哈", Toast.LENGTH_SHORT).show()

        }


//
//        tabList.forEach { _ ->
//            fragments.add(PageFragment())
//        }
//        initPaNormalView()
    }


    override fun onResume() {
        super.onResume()
    }

    fun initPaNormalView() {
//        val vr = findViewById<>(R.id.vr)
//        val paNormalOptions = VrPanoramaView.Options();
//        paNormalOptions.inputType = VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;
//
//
////      mVrPanoramaView.setFullscreenButtonEnabled (false); //隐藏全屏模式按钮
//        vr.setInfoButtonEnabled(false); //设置隐藏最左边信息的按钮
//        vr.setStereoModeButtonEnabled(false); //设置隐藏立体模型的按钮
//        vr.setEventListener(object : VrPanoramaEventListener() {
//
//        }); //设置监听
        //加载本地的图片源
//        vr.loadImageFromBitmap(
//            BitmapFactory.decodeResource(resources, R.drawable.v1),
//            paNormalOptions
//        );
        //设置网络图片源
//        panoWidgetView.loadImageFromByteArray();
    }


    private fun initBar() {
        immersionBar {
            statusBarColor(R.color.transparent)
            statusBarDarkFont(false)
        }
    }


    private fun initTabIndicator() {
//        val commonNavigator = CommonNavigator(this)
//        commonNavigator.adapter = IndicatorNavAdapter(tabList, onClick = {
//            binding.viewpager.currentItem = it
//        })
//        binding.indicator.navigator = commonNavigator
//        ViewPager2Helper.bind(binding.indicator, binding.viewpager)
//        binding.viewpager.orientation = ViewPager2.ORIENTATION_VERTICAL
//        setTab()
    }

    private fun setTab() {
//        binding.viewpager.offscreenPageLimit = 4
//        binding.viewpager.orientation = ViewPager2.ORIENTATION_VERTICAL
//        binding.viewpager.adapter = MainPageAdapter(tabList.size)

    }
}
//
//class VpAdapter(private val layoutResId: Int, val data:String) :
//    RecyclerView.Adapter<VpAdapter.ViewHolder>() {
//
//    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
//        val mRecycler: RecyclerViewPager2 = view.findViewById(R.id.layout_page_rv)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(LayoutInflater.from(parent.context).inflate(layoutResId, parent, false))
//    }
//
//    override fun getItemCount(): Int {
//        return data.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.mRecycler.layoutManager =
//            LinearLayoutManager(holder.view.context, LinearLayoutManager.VERTICAL, false)
//
//        val converter = RvConverter(data[position].children)
//        val adapter = RvRightAdapterContent(converter.convert())
//        holder.mRecycler.adapter = adapter
//    }
//}

