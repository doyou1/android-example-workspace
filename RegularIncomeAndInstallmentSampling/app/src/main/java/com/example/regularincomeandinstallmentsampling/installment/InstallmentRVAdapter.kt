package com.example.regularincomeandinstallmentsampling.installment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.regularincomeandinstallmentsampling.databinding.RvItemInstallmentBinding

class InstallmentRVAdapter(private val list: List<Installment>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _list = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemInstallmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return InstallmentViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _list[position]
        (holder as InstallmentViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class InstallmentViewHolder(binding: RvItemInstallmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Installment) {

        }
    }
}