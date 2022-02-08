package com.example.qnaproject

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.qnaproject.activity.QnaActivity
import com.example.qnaproject.activity.QnaDetailActivity
import com.example.qnaproject.domain.Qna
import com.example.qnaproject.databinding.ListQnaItemBinding

/**
 * Qna화면의 RecyclerView Adapter
 * 데이터 바인딩, 클릭 이벤트 처리
 */
class QnaAdapter(val qnaList: ArrayList<Qna>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // DataBinding object
    private lateinit var binding: ListQnaItemBinding
    var list = mutableListOf<Qna>()

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

                val activity: QnaActivity = viewGroup.context as QnaActivity
                // 액티비티 종료하는 코드
                activity.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is QnaViewHolder) {
            val item = list[position]
            viewHolder.binding.qna = item
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class QnaViewHolder(val binding: ListQnaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}