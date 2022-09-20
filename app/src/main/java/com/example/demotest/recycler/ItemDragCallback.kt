package com.example.demotest.recycler

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.graphics.Color
import android.os.Vibrator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_IDLE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * @name ItemDragCallback
 * @package com.example.demotest.recycler
 * @author 345 QQ:1831712732
 * @time 2022/02/25 11:07
 * @description
 */
 class ItemDragCallback(private val mAdapter: ChangeAdapter, val context: Context) :
    ItemTouchHelper.Callback() {


    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val adapter = recyclerView.adapter as ChangeAdapter
        val holder = viewHolder as ChangeAdapter.ChangeViewHolder

        val position = holder.layoutPosition
        if (!adapter.list[position].isChange) return 0


        var dragFlags: Int = 0

        if (recyclerView.layoutManager is GridLayoutManager) {
            dragFlags =
                ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT;
        } else if (recyclerView.layoutManager is LinearLayoutManager) {
            dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        }
        val swipeFlags = 0
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val from = viewHolder.layoutPosition //拖动的 position
        val to = target.layoutPosition // 拖动到的 item position

        if (!mAdapter.list[to].isChange) return false

        if (from < to) {
            for (i in from until to) {
                Collections.swap(mAdapter.list, i, i + 1)
            }
        } else {
            for (i in from downTo to + 1) {
                Collections.swap(mAdapter.list, i, i - 1)
            }
        }

        mAdapter.notifyItemMoved(from, to)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        //侧滑删除
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ACTION_STATE_IDLE) {
            if (!mAdapter.isEdit) {
                mAdapter.isEdit = true
                mAdapter.notifyDataSetChanged()
            }
            ///长按时调用
            val holder = viewHolder as ChangeAdapter.ChangeViewHolder
            holder.itemView.setBackgroundColor(Color.parseColor("#FF0000"))

            (context.getSystemService(Service.VIBRATOR_MANAGER_SERVICE) as? Vibrator)?.vibrate(
                70
            )
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        val holder = viewHolder as ChangeAdapter.ChangeViewHolder
        holder.itemView.setBackgroundColor(Color.parseColor("#666666"))
        mAdapter.notifyDataSetChanged()
    }

}