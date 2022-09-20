package com.example.demotest.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.demotest.PageAdapter
import com.example.demotest.R
import com.example.demotest.databinding.FragmentPageBinding

/**
 * @name PageFragment
 * @package com.example.demotest
 * @author 345 QQ:1831712732
 * @time 2022/02/22 15:35
 * @description
 */
class PageFragment : Fragment() {

    lateinit var binding: FragmentPageBinding

    private var isLazyLoad = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("---345--->", "   onCreateView     " + this)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_page, container, false)
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        Log.e("---345--->", "   onResume     " + this)
        if (!isLazyLoad) {
            isLazyLoad = true
            bindView()
        }
    }


    private fun bindView() {
//        binding.viewpager.layoutManager =
//            ViewPagerLayoutManager(requireContext())
//        binding.viewpager.adapter = adapter


//        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
//        val recycler = PageRecyclerView()
//        recycler.setUpRecycleView(binding.recycler)
//        binding.recycler.adapter = adapter

//        binding.recycler.addItemDecoration(
//            RecycleViewDivider(
//                requireContext(),
//                LinearLayoutManager.HORIZONTAL,
//                3,
//                R.color.gray,
//                0,
//                0
//            )
//        )
//        binding.recycler.addItemDecoration(
//            GridDivider(
//                20,
//                Color.parseColor("#3563FF")
//            )
//        )
    }

    override fun onDestroy() {
        Log.e("---345--->", "   onDestroy     " + this)
        super.onDestroy()
    }

    override fun onDestroyView() {
        Log.e("---345--->", "   onDestroyView     " + this)
        super.onDestroyView()
    }
}