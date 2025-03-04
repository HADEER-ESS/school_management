package com.hadeer.schoolapp.ui.app.home

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.hadeer.domain.entities.home.events.EventResponseModal
import com.hadeer.schoolapp.R
import com.hadeer.schoolapp.databinding.FragmentHomeMainBinding
import com.hadeer.schoolapp.ui.app.home.announcements.AnnouncementAdaptor
import com.hadeer.schoolapp.ui.app.home.category.CategoryAdaptor
import com.hadeer.schoolapp.ui.app.home.category.CategoryData
import com.hadeer.schoolapp.ui.app.home.events.EventAdaptor
import com.hadeer.schoolapp.ui.app.home.homeMain.HomeIntent
import com.hadeer.schoolapp.ui.app.home.homeMain.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
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
        viewMoreCategoryHandle()
    }

    private fun viewMoreCategoryHandle() {
        binding.eventTitleViewmoreInclude.fieldViewmoreTv.setOnClickListener{
            findNavController().navigate(R.id.action_homeMainFragment_to_eventFragment)
        }
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
                        isLoading(it.state.events_isLoading)
                    }
                    is HomeIntent.Event_Success -> {
                        isLoading(it.state.events_isLoading)
                        binding.eventRecyclerView.adapter = EventAdaptor(it.state.eventsData,"section")
                    }
                    is HomeIntent.Event_Failed -> {
                        isLoading(it.state.events_isLoading)
                        Toast.makeText(requireContext(),it.state.events_error_message, Toast.LENGTH_SHORT).show()
                    }
                    is HomeIntent.Idleal -> {
                        println("announcement Idle.000")
                        isLoading(it.state.announcement_isLoading)
                    }
                    is HomeIntent.Announcement_Success -> {
                        isLoading(it.state.announcement_isLoading)
                        println("the announcement data is from state ${it.state.announcementData}")
                        binding.announcementRecyclerView.adapter = AnnouncementAdaptor(it.state.announcementData)
                    }
                    is HomeIntent.Announcement_Failed -> {
                        isLoading(it.state.announcement_isLoading)
                        Toast.makeText(requireContext(),it.state.announcement_error_message, Toast.LENGTH_SHORT).show()
                    }
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

    override fun onResume() {
        super.onResume()
        viewModel.fetchHomeData()
    }
}