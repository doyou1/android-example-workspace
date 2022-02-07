package com.example.recyclerviewscollproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewscollproject.databinding.ListItemQnaBinding

class QnaAdapter: RecyclerView.Adapter<QnaAdapter.QnaViewHolder>(){

    // DataBinding object
    private lateinit var binding: ListItemQnaBinding
    var list = mutableListOf<Qna>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): QnaViewHolder {
        // list_qna_item.xml과 DataBinding
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.list_item_qna,
            viewGroup,
            false
        )

        return QnaViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: QnaViewHolder, position: Int) {
        if (viewHolder is QnaViewHolder) {
            val item = list[position]
            viewHolder.binding.qna = item
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class QnaViewHolder(val binding: ListItemQnaBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}