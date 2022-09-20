package com.example.demotest.utils

import android.content.Context
import java.io.File

/**
 * @name Path
 * @package com.example.demotest.utils
 * @author 345 QQ:1831712732
 * @time 2022/02/24 22:03
 * @description
 */
object PathUtils {

    fun getCacheImagePath(context: Context): String {
        return "${context.cacheDir.path}${File.separator}ImageCache"
    }


    fun getCacheVideoPath(context: Context): String {
        return "${context.cacheDir.path}${File.separator}VideoCache"
    }
}