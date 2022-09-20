package com.example.demotest.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.Toast

/**
 * @name DragGridGroupView
 * @package com.example.demotest.view
 * @author 345 QQ:1831712732
 * @time 2022/09/20 17:31
 * @description
 */
class DragGridGroupView : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init()
    }

    constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    lateinit var array: Array<Array<View>>

    //初始位置
    private val initPoint: Point = Point(dpToPx(30f), dpToPx(50f))

    //格子大小
    private val sizePoint: Point = Point(dpToPx(30f), dpToPx(30f))

    val view by lazy {
        View(context).apply {
            val layoutParams = FrameLayout.LayoutParams(sizePoint.x, sizePoint.y)
            layoutParams.marginStart = initPoint.x
            layoutParams.topMargin = initPoint.y
            this.layoutParams = layoutParams
            setBackgroundColor(Color.RED)
        }
    }

    val gridLayout by lazy {
        GridLayout(context).apply {
            val layoutParams = FrameLayout.LayoutParams(dpToPx(30f), dpToPx(30f))
            layoutParams.marginStart = dpToPx(30f)
            layoutParams.topMargin = dpToPx(100f)
            layoutParams.width = dpToPx(150f)
            layoutParams.height = dpToPx(500f)
            this.layoutParams = layoutParams
            setBackgroundColor(Color.BLUE)
            orientation = GridLayout.HORIZONTAL
            columnCount = 5
            rowCount = 10
        }
    }

    private fun init() {
        addView(view)
        addView(gridLayout)
        initData()
        initDrag()
    }

    private fun initDrag() {
        view.setOnTouchListener(TouchListener())
    }

    private fun initData() {
        array = Array(10, init = {
            Array(5, init = {
                View(context).apply {
                    val layoutParams = FrameLayout.LayoutParams(dpToPx(30f), dpToPx(30f))
                    this.layoutParams = layoutParams
                    setBackgroundColor(Color.GRAY)
                }
            })
        })
        for (i in array.indices) {
            for (j in 0 until array[i].size) {
                val v = array[i][j]
                v.setOnClickListener {
                    Toast.makeText(context, it.y.toString(), Toast.LENGTH_SHORT).show()
                }
                gridLayout.addView(v)
            }
        }
    }

    private fun dpToPx(dpValue: Float): Int {
        val scale: Float = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


    inner class TouchListener constructor(private val delay: Long = 0) :
        View.OnTouchListener {
        private var downX = 0f
        private var downY = 0f
        private var downTime: Long = 0
        private var isMove = false
        private var canDrag = false
        private fun haveDelay(): Boolean {
            return delay > 0
        }

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downX = event.x
                    downY = event.y
                    isMove = false
                    downTime = System.currentTimeMillis()
                    canDrag = !haveDelay()
                }
                MotionEvent.ACTION_MOVE -> {
                    Log.e("---345--->", "--------- ${canDrag}");
                    if (haveDelay() && !canDrag) {
                        val currMillis = System.currentTimeMillis()
                        if (currMillis - downTime >= delay) {
                            canDrag = true
                        }
                    }
                    if (canDrag) {
                        val xDistance = event.x - downX
                        val yDistance = event.y - downY
                        if (xDistance != 0f && yDistance != 0f) {
                            val l = (v.left + xDistance).toInt()
                            val t = (v.top + yDistance).toInt()
                            //                        v.layout(l, t, r, b);
                            v.left = l
                            v.top = t
                            v.right = (l + v.width)
                            v.bottom = (t + v.height)
                            isMove = true
                        }
                    }
                }
                else -> {}
            }
            return isMove
        }
    }

}

