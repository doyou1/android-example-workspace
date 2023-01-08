package com.example.customdrawingsampling.roulette

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.customdrawingsampling.FLAG_GOAL
import com.example.customdrawingsampling.databinding.RvItemEditTextBinding

class RvRouletteAdapter(private val list: List<Roulette>, private val activity: RouletteActivity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = this::class.java.simpleName
    private val _list = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemEditTextBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RouletteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _list[position]
        (holder as RouletteViewHolder).bind(item, position)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class RouletteViewHolder(
        private val binding: RvItemEditTextBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Roulette, position: Int) {
            binding.etText.setText(item.text)
            binding.etText.hint = position.toString()

            binding.etText.addTextChangedListener {
                activity.changeListItem(it.toString(), position)
            }
        }
    }
}