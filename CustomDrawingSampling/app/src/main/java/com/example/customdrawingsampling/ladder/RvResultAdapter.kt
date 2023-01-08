package com.example.customdrawingsampling.ladder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.customdrawingsampling.databinding.RvItemButtonBinding

class RvResultAdapter(private val list: List<String>, private val activity: LadderActivity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = this::class.java.simpleName
    private val _list = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemButtonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _list[position]
        (holder as ResultViewHolder).bind(item, position)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class ResultViewHolder(
        private val binding: RvItemButtonBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String, position: Int) {
            binding.btnText.text = item
            binding.btnText.setOnClickListener {
                activity.showResult(position)
            }
        }
    }
}