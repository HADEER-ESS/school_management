package com.hadeer.schoolapp.ui.app.home.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hadeer.domain.entities.home.events.EventResponseModal
import com.hadeer.schoolapp.databinding.EventCardItemBinding
import java.text.SimpleDateFormat
import java.util.Locale

class EventAdaptor(
    val data: List<EventResponseModal>,
    val from : String?
) : RecyclerView.Adapter<EventAdaptor.EventViewModel>() {
    class EventViewModel(
        binding: EventCardItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val event_img = binding.eventCardImg
        val event_name = binding.eventTitleTv
        val event_desc = binding.eventDescTv
        val event_date = binding.eventDateTv
        val event_dis = binding.eventLocationTv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewModel {
        val inflater = LayoutInflater.from(parent.context)
        val view = EventCardItemBinding.inflate(inflater, parent, false)
        return EventViewModel(view)
    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: EventViewModel, position: Int) {
        val render_item = data[position]
        if(from == "screen"){
            val layoutParam = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParam.bottomMargin = 20
            layoutParam.topMargin = if(position == 0) 20 else 0
            holder.itemView.layoutParams = layoutParam
        }
        if(render_item.image.isNullOrEmpty()){
            holder.event_img.visibility = View.GONE
        }
        else{
            Glide.with(holder.itemView.context)
                .load(render_item.image)
                .into(holder.event_img)
        }
        holder.event_name.text = render_item.name
        holder.event_desc.text = render_item.desc
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss" , Locale.getDefault())
        val outputFormate = SimpleDateFormat("d/M/yyyy", Locale.getDefault())
        val date = inputFormat.parse(render_item.date)
        holder.event_date.text = outputFormate.format(date)
        holder.event_dis.text = render_item.place
    }
}