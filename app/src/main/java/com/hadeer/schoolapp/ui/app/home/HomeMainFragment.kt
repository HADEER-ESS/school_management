package com.hadeer.schoolapp.ui.app.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hadeer.schoolapp.R
import com.hadeer.schoolapp.databinding.FragmentHomeMainBinding
import com.hadeer.schoolapp.ui.app.home.category.CategoryAdaptor
import com.hadeer.schoolapp.ui.app.home.category.CategoryData
import com.hadeer.schoolapp.ui.app.home.events.EventAdaptor
import com.hadeer.schoolapp.ui.app.home.homeMain.HomeIntent
import com.hadeer.schoolapp.ui.app.home.homeMain.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeMainFragment : Fragment() {
    private lateinit var binding: FragmentHomeMainBinding
    private val viewModel : HomePageViewModel by viewModels()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
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
        screenListener()
    }

    private fun linkCategoryRecyclerView() {
        binding.categoryRecyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.eventRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.announcementRecyclerView.layoutManager = LinearLayoutManager(context)
        val adaptor = CategoryAdaptor(CategoryData.DATA)
        binding.categoryRecyclerView.adapter = adaptor
    }

    private fun linkTitleViewMoreFieldData() {
        binding.eventTitleViewmoreInclude.fieldTitleTv.text = getString(R.string.events_title)
        binding.announcementTitleViewmoreInclude.fieldTitleTv.text = getString(R.string.announcement_title)
        binding.categoryTitleViewmoreInclude.fieldTitleTv.text = getString(R.string.categories_title)
        binding.categoryTitleViewmoreInclude.fieldViewmoreTv.visibility = View.GONE
    }

    private fun screenListener(){
        coroutineScope.launch {
            viewModel.homeIntent.collect{
                when(it){
                    is HomeIntent.Idleal ->{
                        isLoading(it.state.isLoading)
                    }
                    is HomeIntent.Event_Success -> {
                        isLoading(it.state.isLoading)
                        binding.eventRecyclerView.adapter = EventAdaptor(it.state.eventsData)

                    }
                    is HomeIntent.Event_Failed -> {
                        isLoading(it.state.isLoading)
                    }
                    else -> {}
                }
            }
        }
    }


    private fun isLoading(load : Boolean){
        if (load){
            binding.progressBarLy.visibility = View.VISIBLE
        }else{
            binding.progressBarLy.visibility = View.GONE
        }
    }
}