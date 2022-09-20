package com.example.demotest.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.demotest.R
import java.util.*

/**
 * @name ChangeAdapter
 * @package com.example.demotest.recycler
 * @author 345 QQ:1831712732
 * @time 2022/02/25 11:08
 * @description
 */
class ChangeAdapter(val list: MutableList<ItemBean>) :
    RecyclerView.Adapter<ChangeAdapter.ChangeViewHolder>() {

    var isEdit = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChangeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return ChangeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChangeViewHolder, position: Int) {
        setChannel(holder, list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setChannel(holder: ChangeViewHolder, bean: ItemBean) {
        holder.itemView.isEnabled = bean.isChange
        holder.text.text = bean.text
        if (bean.isChange) {
            holder.itemView.setBackgroundResource(R.color.gray)
            holder.image.isVisible = isEdit == true
        } else {
            holder.itemView.setBackgroundResource(R.color.blue)
            holder.image.isVisible = false
        }
    }


    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    inner class ChangeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text = view.findViewById<AppCompatTextView>(R.id.text)
        val image = view.findViewById<AppCompatImageView>(R.id.image)
    }


}