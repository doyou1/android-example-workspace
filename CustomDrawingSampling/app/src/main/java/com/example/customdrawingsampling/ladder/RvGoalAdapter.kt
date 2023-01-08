package com.example.customdrawingsampling.ladder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.customdrawingsampling.FLAG_GOAL
import com.example.customdrawingsampling.databinding.RvItemEditTextBinding

class RvGoalAdapter(private val list: List<String>, private val activity: InitLadderActivity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _list = list
    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemEditTextBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GoalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _list[position]
        (holder as GoalViewHolder).bind(item, position)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class GoalViewHolder(
        private val binding: RvItemEditTextBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String, position: Int) {
            binding.etText.setText(item)
            binding.etText.hint = position.toString()

            binding.etText.addTextChangedListener {
                activity.changeListItem(it.toString(), position, FLAG_GOAL)
            }
        }
    }
}