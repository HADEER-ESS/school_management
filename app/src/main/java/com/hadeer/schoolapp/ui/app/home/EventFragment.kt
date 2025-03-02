package com.hadeer.schoolapp.ui.app.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hadeer.schoolapp.databinding.FragmentEventBinding
import com.hadeer.schoolapp.ui.app.home.events.EventAdaptor
import com.hadeer.schoolapp.ui.app.home.homeMain.HomeIntent
import com.hadeer.schoolapp.ui.app.home.homeMain.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EventFragment : Fragment() {
    private lateinit var binding: FragmentEventBinding
    private val viewModel : HomePageViewModel by viewModels()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        Log.e("TAG", "onCreateView: EVENTS SCreen page...")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.eventsMainRcyv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        handleEventListener()
    }

    private fun handleEventListener() {
        coroutineScope.launch {
            viewModel.homeIntent.collect{
                when(it){
                    is HomeIntent.Idleal -> {
                        isLoading(it.state.isLoading)
                    }
                    is HomeIntent.Event_Success->{
                        isLoading(it.state.isLoading)
                        println("success now events is ${it.state.eventsData}")
                        val adaptor = EventAdaptor(it.state.eventsData, "screen")
                        binding.eventsMainRcyv.adapter = adaptor
                    }
                    is HomeIntent.Event_Failed -> {
                        isLoading(it.state.isLoading)
                        Toast.makeText(requireContext(), it.state.events_error_message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun isLoading(load : Boolean){
        println("loading is loader $load")
        if (load){
            binding.progressBarLy.visibility = View.VISIBLE
        }else{
            binding.progressBarLy.visibility = View.GONE
        }
    }
}