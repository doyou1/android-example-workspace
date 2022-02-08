package com.example.qnaproject

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.qnaproject.activity.QnaActivity
import com.example.qnaproject.activity.QnaDetailActivity
import com.example.qnaproject.domain.Qna
import com.example.qnaproject.databinding.ListQnaItemBinding
import com.example.qnaproject.domain.Product
import com.example.qnaproject.module.GlideApp

/**
 * Qna화면의 RecyclerView Adapter
 * 데이터 바인딩, 클릭 이벤트 처리
 */
class QnaAdapter(val qnaList: ArrayList<Qna>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // DataBinding object
    private lateinit var binding: ListQnaItemBinding
    private lateinit var mContext: Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // list_qna_item.xml과 DataBinding
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.list_qna_item,
            viewGroup,
            false
        )
        mContext = viewGroup.context
        return QnaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is QnaViewHolder) {
            val qna = qnaList[position]
            holder.bind(qna)
        }
    }

    override fun getItemCount(): Int {
        return qnaList.size
    }

    inner class QnaViewHolder(val binding: ListQnaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(qna: Qna) {
            this.itemView.setOnClickListener {
                val intent = Intent(mContext, QnaDetailActivity::class.java)
                intent.putExtra("QNA_ID", qna.QNA_ID)

                val activity: QnaActivity = mContext as QnaActivity
                // 액티비티 종료하는 코드
                activity.startActivity(intent)
            }
            binding.qna = qna

        }
    }
}