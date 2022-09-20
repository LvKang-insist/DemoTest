package com.example.demotest

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView

/**
 * @author 345 QQ:1831712732
 * @name ColorFlipPagerTitleView
 * @package com.genuine.cloud.core.ui.view
 * @time 2021/06/27 21:44
 * @description
 */
class ColorFlipPagerTitleView(context: Context?) : AppCompatTextView(
    context!!
), IPagerTitleView {
    var mNormalColor = 0
    var mSelectedColor = 0
    private val mChangePercent = 0.45f
    override fun onSelected(index: Int, totalCount: Int) = Unit
    override fun onDeselected(index: Int, totalCount: Int) = Unit
    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
        if (leavePercent >= mChangePercent) {
            setTextColor(mNormalColor)
        } else {
            setTextColor(mSelectedColor)
        }
    }

    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
        if (enterPercent >= mChangePercent) {
            setTextColor(mSelectedColor)
        } else {
            setTextColor(mNormalColor)
        }
    }

    init {
        gravity = Gravity.CENTER
        val padding = UIUtil.dip2px(context, 10.0)
        setPadding(padding, 0, padding, 0)
        setSingleLine()
        ellipsize = TextUtils.TruncateAt.END
    }
}