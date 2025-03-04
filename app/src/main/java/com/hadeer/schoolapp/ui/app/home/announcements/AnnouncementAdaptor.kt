package com.hadeer.schoolapp.ui.app.home.announcements

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.hadeer.domain.entities.home.announcements.AnnouncementResponseModel
import com.hadeer.schoolapp.databinding.AnnouncementCardItemBinding
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
class AnnouncementAdaptor(
    private val data : List<AnnouncementResponseModel>
): RecyclerView.Adapter<AnnouncementAdaptor.AnnouncementViewModel>() {

    class AnnouncementViewModel(
        private val binding : AnnouncementCardItemBinding
    ): RecyclerView.ViewHolder(binding.root){
        val announcement_content = binding.announcementContentTv
        val announcement_date = binding.announcementDateTv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementViewModel {
        val inflater = LayoutInflater.from(parent.context)
        val view = AnnouncementCardItemBinding.inflate(inflater, parent, false)
        return AnnouncementViewModel(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: AnnouncementViewModel, position: Int) {
        val render_data = data[position]

        holder.announcement_content.text = render_data.details
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'").withZone(
            ZoneId.of("UTC"))
        val dateTime = ZonedDateTime.parse(render_data.date, formatter)
        val nowDate = ZonedDateTime.now(ZoneId.systemDefault()) // Get current time in local timezone
        val daysBetween = ChronoUnit.DAYS.between(dateTime.toLocalDate(), nowDate.toLocalDate())
//        println("days between is $daysBetween")
        when(daysBetween){
            0L -> {
                holder.announcement_date.text = "Today ${dateTime.format(DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH))}"
            }
            1L -> {
                holder.announcement_date.text = "Yestserday ${DateTimeFormatter.ofPattern("h:mm a" , Locale.ENGLISH)}"
            }
            in 2..6 -> {
                holder.announcement_date.text = "$daysBetween days ago"
            }
            else -> {
                holder.announcement_date.text = dateTime.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
            }
        }

    }
}