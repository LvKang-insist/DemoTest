package com.example.demotest.camera

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import com.example.demotest.R
import android.content.res.TypedArray
import android.graphics.Point
import android.os.Handler
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import java.lang.RuntimeException

/**
 * 对焦View
 *
 *
 */
class FocusImageView : AppCompatImageView {
    private var mFocusImg = NO_ID
    private var mFocusSucceedImg = NO_ID
    private var mFocusFailedImg = NO_ID
    private var mAnimation: Animation

    constructor(context: Context?) : super(context!!) {
        mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.focusview_show)
        visibility = GONE
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.focusview_show)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FocusImageView)
        mFocusImg = typedArray.getResourceId(R.styleable.FocusImageView_focus_focusing_id, NO_ID)
        mFocusSucceedImg =
            typedArray.getResourceId(R.styleable.FocusImageView_focus_success_id, NO_ID)
        mFocusFailedImg = typedArray.getResourceId(R.styleable.FocusImageView_focus_fail_id, NO_ID)
        typedArray.recycle()

        //聚焦图片不能为空
        if (mFocusImg == NO_ID || mFocusSucceedImg == NO_ID || mFocusFailedImg == NO_ID) {
            throw RuntimeException("mFocusImg,mFocusSucceedImg,mFocusFailedImg is null")
        }
    }

    /**
     * 显示对焦图案
     */
    fun startFocus(point: Point) {
        if (mFocusImg == NO_ID || mFocusSucceedImg == NO_ID || mFocusFailedImg == NO_ID) {
            throw RuntimeException("focus image is null")
        }
        //根据触摸的坐标设置聚焦图案的位置
        val params = layoutParams as RelativeLayout.LayoutParams
        params.topMargin = point.y - measuredHeight / 2
        params.leftMargin = point.x - measuredWidth / 2
        layoutParams = params
        //设置控件可见，并开始动画
        visibility = VISIBLE
        setImageResource(mFocusImg)
        startAnimation(mAnimation)
    }

    /**
     * 聚焦成功回调
     */
    fun onFocusSuccess() {
        setImageResource(mFocusSucceedImg)
        postDelayed({
            setImageResource(0)
        }, 1000)
    }

    /**
     * 聚焦失败回调
     */
    fun onFocusFailed() {
        setImageResource(mFocusFailedImg)
        postDelayed({
            setImageResource(0)
        }, 1000)
    }

    /**
     * 设置开始聚焦时的图片
     *
     * @param focus
     */
    fun setFocusImg(focus: Int) {
        mFocusImg = focus
    }

    /**
     * 设置聚焦成功显示的图片
     *
     * @param focusSucceed
     */
    fun setFocusSucceedImg(focusSucceed: Int) {
        mFocusSucceedImg = focusSucceed
    }

    companion object {
        private const val NO_ID = -1
    }
}