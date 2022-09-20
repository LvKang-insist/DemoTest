package com.example.demotest.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.petterp.navigationdrawertest.ui.ViewPagerLayoutManager

/**
 *
 * @author petterp To 2022/6/13
 */
class CustomRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {
    //    canScrollVertically
    private var initialX = 0f
    private var initialY = 0f

    private val lp: ViewPagerLayoutManager
        get() = layoutManager as ViewPagerLayoutManager

    private val parentViewPager: ViewPager2?
        get() {
            var v: View? = parent as? View
            while (v != null && v !is ViewPager2) {
                v = v.parent as? View
            }
            return v as? ViewPager2
        }


    override fun dispatchTouchEvent(e: MotionEvent): Boolean {
        if (e.action == MotionEvent.ACTION_DOWN) {
            initialX = e.x
            initialY = e.y
            parent?.requestDisallowInterceptTouchEvent(true)
            Log.e("petterp", "拦截")
        } else if (e.action == MotionEvent.ACTION_MOVE) {
            val dx = e.x - initialX
            val dy = e.y - initialY
            initialX = e.x
            initialY = e.y
            val isNext = dy < 0
            val childAt = getChildAt(0)
            val childAdapterPosition = getChildAdapterPosition(childAt)
            Log.e("petterp", "------$childAdapterPosition")
            Log.e("---345--->", "$dy      ${lp.pos}   ${lp.itemCount}");
            if (isNext && childAdapterPosition + 1 >= lp.itemCount) {
                Log.e("petterp", "无法滑动下---${lp.pos}")
                parent?.requestDisallowInterceptTouchEvent(false)
                parentViewPager?.apply {
//                    beginFakeDrag()
                    val layoutManager = (
                            getChildAt(0) as? RecyclerView
                                ?: return@apply
                            ).layoutManager as? LinearLayoutManager ?: return@apply
                    layoutManager.scrollToPositionWithOffset(currentItem, dy.toInt())
                }
                return false
//                val curPos = parentViewPager?.currentItem
//                val pos =
//                    if (curPos!! < parentViewPager?.adapter?.itemCount!!) curPos else return false
//                parentViewPager?.setCurrentItem(curPos + 1, true)
//                return false
            } else if (!isNext && lp.pos == 0) {
//                val curPos = parentViewPager?.currentItem
//                val pos =
//                    if (curPos!! > 0 && parentViewPager?.adapter?.itemCount!! >= 1) curPos else return false
//                parentViewPager?.setCurrentItem(curPos - 1, true)
//                Log.e("petterp", "无法滑动上")
                parent?.requestDisallowInterceptTouchEvent(false)
                parentViewPager?.apply {
//                    beginFakeDrag()
                    val layoutManager = (
                            getChildAt(0) as? RecyclerView
                                ?: return@apply
                            ).layoutManager as? LinearLayoutManager ?: return@apply
                    layoutManager.scrollToPositionWithOffset(currentItem, dy.toInt())
                }
                return false
            }
            Log.e("petterp", "继续滑动")
            parent?.requestDisallowInterceptTouchEvent(true)
        }
        return super.dispatchTouchEvent(e)
    }
}
