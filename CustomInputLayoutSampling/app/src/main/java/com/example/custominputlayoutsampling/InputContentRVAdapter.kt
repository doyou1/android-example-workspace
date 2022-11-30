package com.example.custominputlayoutsampling

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.custominputlayoutsampling.databinding.ActivityMainBinding
import com.example.custominputlayoutsampling.databinding.FragmentInputBinding
import com.example.custominputlayoutsampling.databinding.RvItemInputContentBinding

class InputContentRVAdapter(list: ArrayList<String>, private val activityBinding: ActivityMainBinding, private val inputFragment: InputFragment, private val flag: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

    inner class InputContentViewHolder(private val binding: RvItemInputContentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.btnInput.text = item
            binding.btnInput.setOnClickListener {
                when(flag) {
                    FLAG_ASSET -> {
                        activityBinding.etAsset.setText(item)
                        activityBinding.etAsset.clearFocus()
                        activityBinding.etCategory.requestFocus()
                    }
                    FLAG_CATEGORY -> {
                        activityBinding.etCategory.setText(item)
                        activityBinding.etCategory.clearFocus()
                        activityBinding.etAmount.requestFocus()
                    }
                    FLAG_AMOUNT -> {
                        activityBinding.etAmount.setText(item)
                        activityBinding.etAmount.clearFocus()
                    }
                }
                inputFragment.dismiss()
            }
        }
    }


}
