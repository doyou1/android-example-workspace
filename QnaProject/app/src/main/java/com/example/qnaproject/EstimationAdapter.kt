package com.example.qnaproject
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.qnaproject.databinding.ListEstimationItemBinding
import com.example.qnaproject.domain.Estimation

class EstimationAdapter(val estimationList: ArrayList<Estimation>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val tag = "EstimationAdapter"

    private lateinit var binding: ListEstimationItemBinding
    private lateinit var mContext: Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.list_estimation_item,
            viewGroup,
            false
        )
        mContext = viewGroup.context

        return EstimationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EstimationViewHolder) {
            val estimation: Estimation = estimationList[position]
            holder.bind(estimation)
        }
    }

    override fun getItemCount(): Int {
        return estimationList.size
    }

    inner class EstimationViewHolder(val binding: ListEstimationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(estimation: Estimation) {
            binding.estimation = estimation
        }
    }
}