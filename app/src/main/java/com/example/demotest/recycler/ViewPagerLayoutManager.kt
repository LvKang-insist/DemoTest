package com.petterp.navigationdrawertest.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*

/**
 * @author Wenchieh.Lu  2018/8/2
 *
 * A layoutManager like douyin. you can set orientation [LinearLayoutManager.HORIZONTAL] or [LinearLayoutManager.VERTICAL]
 */
private const val TAG = "ViewPagerLayoutManager"

@SuppressLint("WrongConstant")
class ViewPagerLayoutManager @JvmOverloads constructor(
    context: Context,
    orientation: Int = VERTICAL,
    reverseLayout: Boolean = false,
    var extraLayoutSize: Int = 0
) : LinearLayoutManager(context, orientation, reverseLayout) {
    private val pagerSnapHelper = PagerSnapHelper()
    private var mRecyclerView: RecyclerView? = null
    private var mOnViewPagerListener: OnViewPagerListener? = null
    var mCurrentSelectedPosition = -1
    private var mDriftDirection = 0
    private var mSmoothPosition = -1

    var pos = 0

    override fun getExtraLayoutSpace(state: State?): Int {
        return super.getExtraLayoutSpace(state)
    }

    override fun canScrollVertically(): Boolean {
        return super.canScrollVertically()
    }

//    override fun getExtraLayoutSpace(state: State?): Int {
//        return if (orientation == HORIZONTAL) LCApp.appContext.screenWidth * extraLayoutSize else LCApp.appContext.screenHeight * extraLayoutSize
//    }

    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)
        mRecyclerView = view
        mRecyclerView?.onFlingListener = null
        pagerSnapHelper.attachToRecyclerView(view)
        mRecyclerView?.addOnChildAttachStateChangeListener(object :
            RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(v: View) {
                Log.d(
                    TAG,
                    "childCount = $childCount mOnViewPagerListener = $mOnViewPagerListener"
                )
                if (childCount == 1) {
                    mOnViewPagerListener?.onInitComplete(v)
                    mCurrentSelectedPosition = 0
                }
            }

            override fun onChildViewDetachedFromWindow(v: View) {
                val position = getPosition(v)
                Log.d(TAG, "onChildViewDetachedFromWindow position = $position")
                mOnViewPagerListener?.onPageRelease(v, true, position)
                if (mDriftDirection >= 0)
                else
                    mOnViewPagerListener?.onPageRelease(v, false, position)
            }
        })
    }

    override fun scrollToPosition(position: Int) {
        super.scrollToPosition(position)
        mOnViewPagerListener?.onScrollTo(position)
        mCurrentSelectedPosition = position
    }

    /**
     *  when state changed
     * @param state  缓慢拖拽-> SCROLL_STATE_DRAGGING
     *               快速滚动-> SCROLL_STATE_SETTLING
     *               空闲状态-> SCROLL_STATE_IDLE
     */
    override fun onScrollStateChanged(state: Int) {
        when (state) {
            RecyclerView.SCROLL_STATE_IDLE -> {
                val viewIdle = pagerSnapHelper.findSnapView(this)
                Log.d(TAG, "SCROLL_STATE_IDLE viewIdle = $viewIdle ")
                viewIdle?.let {
                    val positionIdle = getPosition(viewIdle)
                    Log.d(
                        TAG,
                        "onScrollStateChanged-- positionIdle = $positionIdle , mSmoothPosition = $mSmoothPosition"
                    )
                    // fix smoothScroll 滑动后 滑的position不准
                    if (positionIdle != mSmoothPosition && mSmoothPosition != -1) {
                        mRecyclerView?.smoothScrollToPosition(mSmoothPosition)
                        return
                    }
                    Log.d(TAG, "childCount = $childCount positionIdle = $positionIdle")
//          if (childCount == 2 ) {
                    pos = positionIdle
                    mOnViewPagerListener?.onPageSelected(
                        viewIdle, positionIdle, mCurrentSelectedPosition,
                        positionIdle == itemCount - 1, mCurrentSelectedPosition == positionIdle
                    )
                    mCurrentSelectedPosition = positionIdle
//          }
                }
            }
            RecyclerView.SCROLL_STATE_DRAGGING -> {
                val viewDrag = pagerSnapHelper.findSnapView(this)
//        val positionDrag = getPosition(viewDrag)
                mSmoothPosition = -1
                Log.d(TAG, "SCROLL_STATE_DRAGGING")
            }
            RecyclerView.SCROLL_STATE_SETTLING -> {
                val viewSettling = pagerSnapHelper.findSnapView(this)
                var settingPosition = -1
                val visibleItemPosition = findFirstCompletelyVisibleItemPosition()
                viewSettling?.let { v ->
                    settingPosition = getPosition(v)
                }
                Log.d(
                    TAG,
                    "SCROLL_STATE_SETTLING viewIdle = $viewSettling settingPosition = $settingPosition visibleItemPosition = $visibleItemPosition"
                )
            }
            else -> {
            }
        }
    }

    override fun scrollVerticallyBy(dy: Int, recycler: Recycler?, state: State?): Int {
        mDriftDirection = dy
        return super.scrollVerticallyBy(dy, recycler, state)
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: Recycler?, state: State?): Int {
        mDriftDirection = dx
        return super.scrollHorizontallyBy(dx, recycler, state)
    }

    fun setOnViewPagerListener(listener: OnViewPagerListener?) {
        mOnViewPagerListener = listener
    }

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: State?, position: Int) {
        val linearSmoothScroller = createSmoothScroller(recyclerView.context, position)
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
        mSmoothPosition = position
    }

    fun createSmoothScroller(context: Context, position: Int): LinearSmoothScroller {
        return object : LinearSmoothScroller(context) {
            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return 10F / displayMetrics.densityDpi
            }
        }
    }
}

abstract class OnViewPagerListener {

    /*初始化完成*/
    open fun onInitComplete(v: View) {}

    /*释放的监听*/
    open fun onPageRelease(v: View, isNext: Boolean, position: Int) {}

    /*选中的监听以及判断是否滑动到底部*/
    open fun onPageSelected(
        v: View,
        position: Int,
        prePosition: Int,
        isBottom: Boolean,
        reselected: Boolean
    ) {
    }

    open fun onScrollTo(position: Int) {}
}
