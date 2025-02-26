package com.hadeer.schoolapp.ui.app.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hadeer.schoolapp.R
import com.hadeer.schoolapp.databinding.FragmentHomeMainBinding
import com.hadeer.schoolapp.ui.app.home.category.CategoryAdaptor
import com.hadeer.schoolapp.ui.app.home.category.CategoryData

class HomeMainFragment : Fragment() {
    private lateinit var binding: FragmentHomeMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeMainBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linkTitleViewMoreFieldData()
        linkCategoryRecyclerView()
    }

    private fun linkCategoryRecyclerView() {
        binding.categoryRecyclerView.layoutManager = GridLayoutManager(context, 3)
        val adaptor = CategoryAdaptor(CategoryData.DATA)
        binding.categoryRecyclerView.adapter = adaptor
    }

    private fun linkTitleViewMoreFieldData() {
        binding.eventTitleViewmoreInclude.fieldTitleTv.text = getString(R.string.events_title)
        binding.announcementTitleViewmoreInclude.fieldTitleTv.text = getString(R.string.announcement_title)
        binding.categoryTitleViewmoreInclude.fieldTitleTv.text = getString(R.string.categories_title)
        binding.categoryTitleViewmoreInclude.fieldViewmoreTv.visibility = View.GONE
    }
}