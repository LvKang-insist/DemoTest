package com.example.demotest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.buildins.ArgbEvaluatorHolder
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData
import java.util.*
import kotlin.math.abs

/**
 * @name CustomLinePagerIndicator
 * @package com.genuine.cloud.core.ui.view
 * @author 345 QQ:1831712732
 * @time 2021/06/26 22:51
 * @description
 */
@Suppress("PrivatePropertyName")
class CustomLinePagerIndicator : View, IPagerIndicator {

    val value by lazy (LazyThreadSafetyMode.PUBLICATION) {  }

    private val MODE_MATCH_EDGE = 0 // 直线宽度 == title宽度 - 2 * mXOffset

    private val MODE_WRAP_CONTENT = 1 // 直线宽度 == title内容宽度 - 2 * mXOffset

    private val MODE_EXACTLY = 2 // 直线宽度 == mLineWidth


    var mMode // 默认为MODE_MATCH_EDGE模式
            = 0

    // 控制动画
    var mStartInterpolator: Interpolator? = LinearInterpolator()
    var mEndInterpolator: Interpolator? = LinearInterpolator()

    var mYOffset // 相对于底部的偏移量，如果你想让直线位于title上方，设置它即可
            = 0f
    var mLineHeight = 0f
    var mXOffset = 0f
    var mLineWidth = 0f
    var mRoundRadius = 0f

    var mPaint: Paint? = null
    var mPositionDataList: List<PositionData>? = null
    var mColors: List<Int>? = null

    val mLineRect = RectF()

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    fun init(context: Context) {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint!!.style = Paint.Style.FILL
        mLineHeight = UIUtil.dip2px(context, 3.0).toFloat()
        mLineWidth = UIUtil.dip2px(context, 10.0).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(mLineRect, mRoundRadius, mRoundRadius, mPaint!!)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (mPositionDataList == null || mPositionDataList!!.isEmpty()) {
            return
        }

        // 计算颜色
        if (mColors != null && mColors!!.isNotEmpty()) {
            val currentColor = mColors!![abs(position) % mColors!!.size]
            val nextColor = mColors!![abs(position + 1) % mColors!!.size]
            val color = ArgbEvaluatorHolder.eval(positionOffset, currentColor, nextColor)
            mPaint!!.color = color
        }

        // 计算锚点位置
        val current = FragmentContainerHelper.getImitativePositionData(mPositionDataList, position)
        val next = FragmentContainerHelper.getImitativePositionData(mPositionDataList, position + 1)
        val leftX: Float
        val nextLeftX: Float
        val rightX: Float
        val nextRightX: Float
        if (mMode == MODE_MATCH_EDGE) {
            leftX = current.mLeft + mXOffset
            nextLeftX = next.mLeft + mXOffset
            rightX = current.mRight - mXOffset
            nextRightX = next.mRight - mXOffset
        } else if (mMode == MODE_WRAP_CONTENT) {
            leftX = current.mContentLeft + mXOffset
            nextLeftX = next.mContentLeft + mXOffset
            rightX = current.mContentRight - mXOffset
            nextRightX = next.mContentRight - mXOffset
        } else {    // MODE_EXACTLY
            leftX = current.mLeft + (current.width() - mLineWidth) / 2
            nextLeftX = next.mLeft + (next.width() - mLineWidth) / 2
            rightX = current.mLeft + (current.width() + mLineWidth) / 2
            nextRightX = next.mLeft + (next.width() + mLineWidth) / 2
        }
        mLineRect.left = leftX + (nextLeftX - leftX) * mStartInterpolator!!.getInterpolation(positionOffset)
        mLineRect.right = rightX + (nextRightX - rightX) * mEndInterpolator!!.getInterpolation(positionOffset)
        mLineRect.top = getHeight() - mLineHeight - mYOffset
        mLineRect.bottom = getHeight() - mYOffset
        invalidate()
    }

    override fun onPageSelected(position: Int) {}

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPositionDataProvide(dataList: List<PositionData>?) {
        mPositionDataList = dataList
    }

    fun getYOffset(): Float {
        return mYOffset
    }

    fun setYOffset(yOffset: Float) {
        mYOffset = yOffset
    }

    fun getXOffset(): Float {
        return mXOffset
    }

    fun setXOffset(xOffset: Float) {
        mXOffset = xOffset
    }

    fun getLineHeight(): Float {
        return mLineHeight
    }

    fun setLineHeight(lineHeight: Float) {
        mLineHeight = lineHeight
    }

    fun getLineWidth(): Float {
        return mLineWidth
    }

    fun setLineWidth(lineWidth: Float) {
        mLineWidth = lineWidth
    }

    fun getRoundRadius(): Float {
        return mRoundRadius
    }

    fun setRoundRadius(roundRadius: Float) {
        mRoundRadius = roundRadius
    }

    fun getMode(): Int {
        return mMode
    }

    fun setMode(mode: Int) {
        mMode = if (mode == MODE_EXACTLY || mode == MODE_MATCH_EDGE || mode == MODE_WRAP_CONTENT) {
            mode
        } else {
            throw IllegalArgumentException("mode $mode not supported.")
        }
    }

    fun getPaint(): Paint? {
        return mPaint
    }

    fun getColors(): List<Int>? {
        return mColors
    }

    fun setColors(vararg colors: Int?) {
        mColors = Arrays.asList<Int>(*colors)
    }

    fun getStartInterpolator(): Interpolator? {
        return mStartInterpolator
    }

    fun setStartInterpolator(startInterpolator: Interpolator?) {
        mStartInterpolator = startInterpolator
        if (mStartInterpolator == null) {
            mStartInterpolator = LinearInterpolator()
        }
    }

    fun getEndInterpolator(): Interpolator? {
        return mEndInterpolator
    }

    fun setEndInterpolator(endInterpolator: Interpolator?) {
        mEndInterpolator = endInterpolator
        if (mEndInterpolator == null) {
            mEndInterpolator = LinearInterpolator()
        }
    }
}
