package com.example.qnaproject

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.qnaproject.databinding.ActivityQnaBinding
import com.example.qnaproject.databinding.ListQnaItemBinding

/**
 * Qna화면의 RecyclerView Adapter
 * 데이터 바인딩, 클릭 이벤트 처리
 */
class QnaAdapter(val qnaList: ArrayList<Qna>?, val MEM_ID: Int?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // DataBinding object
    private lateinit var binding: ListQnaItemBinding
    val list = mutableListOf<Qna>()

    // 인터페이스로부터 받아온 QnaList 추가
    init {
        if (qnaList != null) {
            list.addAll(qnaList)
        }
    }

    fun setList(newList: ArrayList<Qna>) {
        list.addAll(newList)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // list_qna_item.xml과 DataBinding
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.list_qna_item,
            viewGroup,
            false
        )

        return QnaViewHolder(binding).apply {   // RecyclerViewItem 클릭 이벤트 설정
            itemView.setOnClickListener {
                val curPos: Int = adapterPosition
                val qna: Qna = list.get(curPos)
                val intent = Intent(viewGroup.context, QnaDetailActivity::class.java)
                intent.putExtra("QNA_ID", qna.QNA_ID)
                intent.putExtra("MEM_ID", MEM_ID)

                val activity: QnaActivity = viewGroup.context as QnaActivity
                // 액티비티 종료하는 코드
                activity.startActivity(intent)
                activity.finish()
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is QnaViewHolder) {
            val item = list[position]
            viewHolder.binding.tvQnaTitle.text = item.QNA_TITLE
            viewHolder.binding.tvQnaConDt.text = item.QNA_CON_DT
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class QnaViewHolder(val binding: ListQnaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}