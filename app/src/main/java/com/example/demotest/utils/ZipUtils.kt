package com.example.demotest.utils

import android.content.Context
import android.media.MediaMetadataRetriever
import android.util.Log
import com.hw.videoprocessor.VideoProcessor
import java.io.File
import kotlin.math.min

/**
 * @name ZipUtils
 * @package com.example.demotest.utils
 * @author 345 QQ:1831712732
 * @time 2022/02/25 17:53
 * @description
 */
object ZipUtils {

    fun zipUtils(
        context: Context,
        inputPath: String,
        process: (Float) -> Unit,
        success: (String) -> Unit,
        error: (String) -> Unit
    ) {
//        val url = "/data/user/0/com.example.demotest/cache/VideoCache/VID_20220225162449146.mp4"
        try {
            val inputName = inputPath.subSequence(inputPath.lastIndexOf('/'), inputPath.length)
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(inputPath)
            var width =
                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
                    ?.toFloat()
                    ?: 0f
            var height =
                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
                    ?.toFloat()
                    ?: 0f
            val min = min(width, height)
            if (min >= 1080) {
                width /= 2
                height /= 2
            } else if (min >= 720) {
                width /= 1.5f
                height /= 1.5f
            }

            val outputFile = File(context.cacheDir.path + File.separator + "zipVideo")
            if (!outputFile.exists()) {
                outputFile.mkdir()
            }
            var outputPath = outputFile.path + File.separator + inputName
            VideoProcessor.processor(context)
                .input(inputPath)
                .output(outputPath)
                .outWidth(width.toInt())
                .outHeight(height.toInt())
                .bitrate((3 * 1024 * 1024) / 2)
                .progressListener {
                    process.invoke(it)
                    if (it == 1f && outputPath.isNotEmpty()) {
                        success.invoke(outputPath)
                        outputPath = ""
                    }
                }
                .process()
        } catch (e: Exception) {
            error.invoke(e.message ?: "视频错误")
        }
    }

}