package com.example.custominputlayoutsampling

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.custominputlayoutsampling.databinding.ActivityMainBinding
import com.example.custominputlayoutsampling.databinding.RvItemInputContentBinding

class InputContentRVAdapter(
    list: List<String>,
    private val activityBinding: ActivityMainBinding,
    private val inputFragment: InputFragment,
    private val flag: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _list = list
    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemInputContentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return InputContentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _list[position]
        (holder as InputContentViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class InputContentViewHolder(private val binding: RvItemInputContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.btnInput.text = item
            binding.btnInput.setOnClickListener {
                when (flag) {
                    FLAG_INPUT_1 -> {
                        activityBinding.etInput1.setText(item)
                        activityBinding.etInput1.clearFocus()
                        activityBinding.etInput2.requestFocus()
                    }
                    FLAG_INPUT_2 -> {
                        activityBinding.etInput2.setText(item)
                        activityBinding.etInput2.clearFocus()
                        activityBinding.etInput3.requestFocus()
                    }
                    FLAG_INPUT_3 -> {
                        activityBinding.etInput3.setText(item)
                        activityBinding.etInput3.clearFocus()
                    }
                }
                inputFragment.dismiss()
            }
        }
    }


}
