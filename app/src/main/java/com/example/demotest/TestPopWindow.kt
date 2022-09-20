package com.example.demotest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demotest.recycler.ChangeAdapter
import com.example.demotest.recycler.ItemBean
import com.example.demotest.recycler.ItemDragCallback
import com.ypx.imagepicker.ImagePicker
import com.ypx.imagepicker.data.OnImagePickCompleteListener
import razerdp.basepopup.BasePopupWindow
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.Direction
import razerdp.util.animation.TranslationConfig

/**
 * @name TestPopWindow
 * @package com.example.demotest
 * @author 345 QQ:1831712732
 * @time 2022/02/22 14:54
 * @description
 */
class TestPopWindow(private val activity: Activity) : BasePopupWindow(activity) {


    init {
        contentView = createPopupById(R.layout.layout_pop)
        contentView.findViewById<View>(R.id.text1).setOnClickListener {
            activity.startActivity(Intent(context, VideoActivity::class.java))
            activity.overridePendingTransition(R.anim.login_start_in, R.anim.login_start_out)
        }

        val list = arrayListOf<ItemBean>()
        list.add(ItemBean("推荐", false))
        list.add(ItemBean("视频", false))
        list.add(ItemBean("直播", false))
        list.add(ItemBean("时尚穿搭"))
        list.add(ItemBean("数码"))
        list.add(ItemBean("科技"))
        list.add(ItemBean("校园"))
        list.add(ItemBean("箱包"))
        list.add(ItemBean("搞笑"))
        list.add(ItemBean("萌宠"))
        list.add(ItemBean("知识科普"))
        list.add(ItemBean("Vlog"))
        list.add(ItemBean("潮流艺术"))
        list.add(ItemBean("体育"))
        list.add(ItemBean("影视"))
        list.add(ItemBean("手机游戏"))
        list.add(ItemBean("汽车"))
        list.add(ItemBean("动漫"))
        list.add(ItemBean("旅行"))
        list.add(ItemBean("食谱"))
        list.add(ItemBean("情感"))
        list.add(ItemBean("篮球"))
        list.add(ItemBean("餐厅"))
        list.add(ItemBean("跑步"))
        list.add(ItemBean("职场"))
        list.add(ItemBean("足球"))
        list.add(ItemBean("明星"))
        val recycler = contentView.findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = GridLayoutManager(activity, 3)
        val adapter = ChangeAdapter(list)
        recycler.adapter = adapter

        val callback = ItemDragCallback(adapter, activity)
        ItemTouchHelper(callback).attachToRecyclerView(recycler)
    }

    override fun onCreateShowAnimation(): Animation {
        return AnimationHelper.asAnimation()
            .withTranslation(TranslationConfig().from(Direction.TOP))
            .toShow()
    }

    override fun onCreateDismissAnimation(): Animation {
        return AnimationHelper.asAnimation()
            .withTranslation(TranslationConfig().to(Direction.TOP))
            .toShow()
    }
}