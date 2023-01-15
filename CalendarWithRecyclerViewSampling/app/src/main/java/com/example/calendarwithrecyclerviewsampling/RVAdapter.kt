package com.example.calendarwithrecyclerviewsampling

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarwithrecyclerviewsampling.databinding.RvCalendarItemContentBinding
import com.example.calendarwithrecyclerviewsampling.databinding.RvCalendarItemHeadBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class RVAdapter(private val list: ArrayList<CalendarItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _list = list
    private val TAG = this::class.java.simpleName
    private var sequence = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val headHeight = parent.measuredHeight / 8
        val contentHeight = (parent.measuredHeight - headHeight) / 5

        when (viewType) {
            TYPE_CALENDAR_HEAD -> {
                val binding = RvCalendarItemHeadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                val layoutParams =  binding.root.layoutParams
                layoutParams.height = headHeight
                binding.root.layoutParams = layoutParams
                return HeadViewHolder(binding)
            }
            TYPE_CALENDAR_CONTENT -> {
                val binding = RvCalendarItemContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                val layoutParams =  binding.root.layoutParams
                layoutParams.height = contentHeight
                binding.root.layoutParams = layoutParams
                return ContentViewHolder(binding)
            }
            else -> {
                throw NotImplementedError()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val calendarItem = _list[position]

        when (calendarItem.viewType) {
            TYPE_CALENDAR_HEAD -> {
                (holder as HeadViewHolder).bind(calendarItem)
            }
            TYPE_CALENDAR_CONTENT -> {
                Log.e(TAG, "onBindViewHolder: ${holder.itemView.minimumHeight}")

                (holder as ContentViewHolder).bind(calendarItem)
            }
            else -> throw NotImplementedError()
        }

    }

    override fun getItemCount(): Int {
        return _list.size
    }

    override fun getItemViewType(position: Int): Int {
        return _list[position].viewType
    }

    inner class HeadViewHolder(private val binding: RvCalendarItemHeadBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(calendarItem: CalendarItem)
        {
            if(calendarItem.viewType == TYPE_CALENDAR_HEAD) {
                binding.strDayOfWeek = when(sequence++) {
                    0 -> "SUN"
                    1 -> "MON"
                    2 -> "TUE"
                    3 -> "WED"
                    4 -> "THUR"
                    5 -> "FRI"
                    6 -> "SAT"
                    else -> ""
                }
            } else {
                binding.strDayOfWeek = ""
            }
        }
    }

    inner class ContentViewHolder(private val binding: RvCalendarItemContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(calendarItem: CalendarItem) {
            val model = formatToViewModel(calendarItem)
            binding.isToday = model.day == getToday()
            binding.model = model
        }

        private fun formatToViewModel(calendarItem: CalendarItem): CalendarItemViewModel {
            return CalendarItemViewModel(calendarItem.date, calendarItem.consumption.toString(), calendarItem.income.toString(), calendarItem.result.toString())
        }

        private fun getToday() : String {
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyyMMdd")
            return sdf.format(cal.time)
        }
    }


}