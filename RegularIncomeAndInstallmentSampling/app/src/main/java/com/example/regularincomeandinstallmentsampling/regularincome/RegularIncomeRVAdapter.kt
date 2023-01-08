package com.example.regularincomeandinstallmentsampling.regularincome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.regularincomeandinstallmentsampling.databinding.RvItemRegularIncomeBinding
import com.example.regularincomeandinstallmentsampling.util.Util

class RegularIncomeRVAdapter(private val list: List<RegularIncome>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _list = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemRegularIncomeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RegularIncomeViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _list[position]
        (holder as RegularIncomeViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class RegularIncomeViewHolder(private val binding: RvItemRegularIncomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RegularIncome) {
            binding.tvId.text = item.id.toString()
            binding.tvPeriod.text = Util.getPeriod(item.period)
            binding.tvDate.text = item.date
            binding.tvName.text = item.name
            binding.tvAmount.text = item.amount.toString()
            binding.tvIsNewFlag.text = item.isNewFlag.toString()
        }
    }
}