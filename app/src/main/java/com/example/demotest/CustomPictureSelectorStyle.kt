package com.example.demotest

import android.content.Context
import androidx.core.content.ContextCompat
import com.luck.picture.lib.style.BottomNavBarStyle
import com.luck.picture.lib.style.PictureSelectorStyle
import com.luck.picture.lib.style.TitleBarStyle

/**
 * @name CustomPictureSelectorStyle
 * @package com.example.demotest
 * @author 345 QQ:1831712732
 * @time 2022/02/23 15:32
 * @description
 */
class CustomPictureSelectorStyle(val context: Context) : PictureSelectorStyle() {


    init {
        bottomBarStyle = BottomNavBarStyle()
        bottomBarStyle.bottomPreviewNormalTextColor =
            ContextCompat.getColor(context, R.color.ps_color_white)
        bottomBarStyle.bottomPreviewSelectTextColor =
            ContextCompat.getColor(context, R.color.ps_color_white)

        titleBarStyle = TitleBarStyle()
        titleBarStyle.titleBackgroundColor = ContextCompat.getColor(context, R.color.ps_color_black)

    }
}