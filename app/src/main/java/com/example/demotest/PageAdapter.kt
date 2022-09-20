package com.example.demotest

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

/**
 * @name PageAdapter
 * @package com.example.demotest
 * @author 345 QQ:1831712732
 * @time 2022/02/22 15:40
 * @description
 */
class PageAdapter(val position: Int) : RecyclerView.Adapter<PageAdapter.PageHolder>() {

    val str =
        "过个年直接胖了10斤！！还有谁能比我还胖的多的[石化r]都不知道要多久才能减掉这10斤，管不住嘴的结果就是这样，我爸为啥炒菜那么好吃啊[抓狂r][抓狂r]，明天一定得开始减肥了，再不行动广东的夏天就要来了，这次绝对不能半途而废，绝对不能减个几天就不减了，每"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder {
        return PageHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        holder.pos = position
        holder.text.text = "${this.position}  +  $position"
    }

    override fun getItemCount(): Int = 10


    class PageHolder(val view: View) : RecyclerView.ViewHolder(view) {

        var pos = 0

        val text: AppCompatTextView by lazy { view.findViewById(R.id.text) }

        init {
            view.setOnClickListener {
                Toast.makeText(view.context, pos.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}