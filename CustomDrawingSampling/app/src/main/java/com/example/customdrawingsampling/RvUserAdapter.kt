package com.example.customdrawingsampling

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.customdrawingsampling.databinding.RvItemEditTextBinding

class RvUserAdapter(private val list: List<String>, private val activity: MainActivity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = this::class.java.simpleName
    private val _list = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemEditTextBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _list[position]
        (holder as UserViewHolder).bind(item, position)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class UserViewHolder(
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