package com.example.demotest.view

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.PointF
import android.util.AttributeSet
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
    private val gridRowCount = 10 //竖向数量
    private val gridColumnCount = 5 //横向数量

    private var preView: View? = null

    //初始位置
    private val initPoint: Point = Point(dpToPx(30f), dpToPx(50f))

    //格子大小
    private val sizePoint: Point = Point(dpToPx(30f), dpToPx(30f))

    //按下位置
    private val downPoint: PointF = PointF(0f, 0f)
    private val viewPoint: PointF = PointF(0f, 0f)


    private val defaultGridColor = Color.parseColor("#8A8C8E")
    private val scrollSelectColor = Color.parseColor("#2F3132")
    private val selectColor = Color.parseColor("#00BD2F")

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
            val layoutParams =
                FrameLayout.LayoutParams(sizePoint.x * gridColumnCount, sizePoint.y * gridRowCount)

            layoutParams.marginStart = initPoint.x
            layoutParams.topMargin = initPoint.y + (sizePoint.y * 2)

            this.layoutParams = layoutParams
            setBackgroundColor(Color.BLUE)
            orientation = GridLayout.HORIZONTAL
            columnCount = gridColumnCount
            rowCount = gridRowCount
        }
    }

    private fun init() {
        addView(gridLayout)
        addView(view)
        initData()
        initDrag()
    }

    private fun initDrag() {

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downPoint.x = event.x
                downPoint.y = event.y
                viewPoint.x = view.x
                viewPoint.y = view.y
            }
            MotionEvent.ACTION_MOVE -> {
                val x = (event.x - downPoint.x) + viewPoint.x
                val y = (event.y - downPoint.y) + viewPoint.y
                if (boundaryJudge(x, y)) return true
                println("$x -- $y---------- ${gridLayout.width}      ${gridLayout.height}")
                view.x = x
                view.y = y
                if (x > gridLayout.x && y > gridLayout.y && x < (gridLayout.width + gridLayout.x) && y < (gridLayout.height + gridLayout.y)) {
                    fillingGrid(x, y)
                }
            }
            MotionEvent.ACTION_UP -> {
                val x = (event.x - downPoint.x) + viewPoint.x
                val y = (event.y - downPoint.y) + viewPoint.y
                preView?.let {
                    if (x > gridLayout.x && y > gridLayout.y && x < (gridLayout.width + gridLayout.x) && y < (gridLayout.height + gridLayout.y)) {
                        it.tag = 1
                        it.setBackgroundColor(selectColor)
                    } else {
                        it.tag = 0
                        it.setBackgroundColor(defaultGridColor)
                    }
                }
                view.x = initPoint.x.toFloat()
                view.y = initPoint.y.toFloat()
                preView = null
            }

        }

        return true;
    }

    private fun setBg(view: View, color: Int) {

    }

    private fun boundaryJudge(x: Float, y: Float): Boolean {
        if (x < 0) return true
        if (y < 0) return true
        if (x > (width - sizePoint.x)) return true
        if (y > (height - sizePoint.y)) return true
        return false
    }

    private fun fillingGrid(x: Float, y: Float) {
        //计算框内位置，+ 格子的一半，等于中心点距离边上的位置
        val nx = x - gridLayout.x + (sizePoint.x / 2)
        val ny = y - gridLayout.y + (sizePoint.y / 2)

        //计算索引位置
        val i = (nx / sizePoint.x).toInt()
        val j = (ny / sizePoint.y).toInt()

        if (j >= gridRowCount || i >= gridColumnCount) return
//        println("$nx   $ny     $i  $j")
//
        val v = array[j][i]
        if (preView != null && preView == v) {
            return
        }
        if (v.tag == 1) {
            return
        }

        preView?.tag = 0
        preView?.setBackgroundColor(defaultGridColor)

        preView = v
        v.tag = 1
        v.setBackgroundColor(scrollSelectColor)
    }

    private fun initData() {
        array = Array(gridRowCount, init = {
            Array(gridColumnCount, init = {
                View(context).apply {
                    val layoutParams = FrameLayout.LayoutParams(dpToPx(30f), dpToPx(30f))
                    this.layoutParams = layoutParams
                    setBackgroundColor(defaultGridColor)
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




}

