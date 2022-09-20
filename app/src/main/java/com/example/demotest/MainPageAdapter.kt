package com.example.demotest

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.demotest.recycler.PageRecyclerView
import com.petterp.navigationdrawertest.ui.ViewPagerLayoutManager

/**
 * @name PageAdapter
 * @package com.example.demotest
 * @author 345 QQ:1831712732
 * @time 2022/02/22 15:40
 * @description
 */
class MainPageAdapter(val count: Int) : RecyclerView.Adapter<MainPageAdapter.PageHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder {
        return PageHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.main_item_page, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        Log.e("---345--->", "+++  $position");
        val adapter = PageAdapter(position)
//        holder.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
//        holder.viewPager.offscreenPageLimit = 4

//        holder.viewPager.adapter = adapter
//        holder.recycler.layoutManager = LinearLayoutManager(holder.recycler.context)
//        holder.recycler.adapter = adapter
        holder.recycler.layoutManager = ViewPagerLayoutManager(holder.recycler.context)
//        val recycler = PageRecyclerView()
//        recycler.setUpRecycleView(holder.recycler)
        holder.recycler.adapter = adapter
    }

    override fun getItemCount(): Int = count


    class PageHolder(val view: View) : RecyclerView.ViewHolder(view) {

        var pos = 0

        //        val viewPager: ViewPager2 by lazy { view.findViewById(R.id.viewpager) }
        val recycler: RecyclerView by lazy { view.findViewById(R.id.recycler) }

    }
}