package com.example.regularincomeandinstallmentsampling

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.regularincomeandinstallmentsampling.databinding.RvItemRepeatBinding

class RepeatRVAdapter(list: Array<String>, private val fragment: Fragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = this::class.java.simpleName
    private val _list = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = RvItemRepeatBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RepeatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _list[position]
        (holder as RepeatViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class RepeatViewHolder(private val binding: RvItemRepeatBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.tvRepeat.text = item
            binding.layoutWrap.setOnClickListener {
                (fragment as RepeatDialog).itemClick(item)
            }
        }
    }
}