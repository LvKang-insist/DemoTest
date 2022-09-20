package com.example.demotest

import android.content.Context
import android.graphics.Typeface
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.content.res.ResourcesCompat
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * @name IndicatorNavAdapter
 * @package com.genuine.cloud.core.ui.adapter
 * @author 345 QQ:1831712732
 * @time 2021/06/26 22:49
 * @description
 */
class IndicatorNavAdapter(
    private val titles: MutableList<String>,
    private val textSize: Float = 16F,
    private val isIndicator: Boolean = true,
    private val color: Int = R.color.red,
    val onClick: (Int) -> Unit
) : CommonNavigatorAdapter() {

    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        val titleView = ColorFlipPagerTitleView(context)
        titleView.mSelectedColor = ResourcesCompat.getColor(
            context.resources, color, null
        )
        titleView.mNormalColor =
            ResourcesCompat.getColor(context.resources, R.color.gray, null)
        titleView.textSize = textSize
        titleView.text = titles[index]
        titleView.setOnClickListener {
            onClick(index)
        }
        return titleView
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getIndicator(context: Context): IPagerIndicator? {
        return null
//        if (!isIndicator) return null
        val indicator = CustomLinePagerIndicator(context)
//        indicator.mLineWidth = dip2px(context, 24f).toFloat()
//        indicator.mLineHeight = dip2px(context, 4f).toFloat()
//        indicator.mStartInterpolator = AccelerateInterpolator()
//        indicator.mEndInterpolator = DecelerateInterpolator(1.0f)
//        indicator.setColors(ResourcesCompat.getColor(context.resources, R.color.black, null))
//        indicator.mRoundRadius = 10f
//        indicator.mYOffset = dip2px(context, 3f).toFloat()
//        indicator.mMode = LinePagerIndicator.MODE_EXACTLY
//        return indicator
    }

    /**
     * 将dp值转化为像素px值
     * @param mContext
     * @param convertValue
     * @return px像素值
     */
    private fun dip2px(mContext: Context, convertValue: Float): Int {
        val density = mContext.resources.displayMetrics.density
        return (convertValue * density + 0.5f).toInt()
    }
}
