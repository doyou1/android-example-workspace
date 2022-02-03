package com.example.qnaproject.qna

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.qnaproject.R

class QnaAdapter(val qnaList: ArrayList<Qna>) :
    RecyclerView.Adapter<QnaAdapter.QnaViewHolder>() {

    class QnaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val qna_title: TextView
        val qna_con_dt: TextView

        init {
            qna_title = view.findViewById(R.id.tv_qna_title)
            qna_con_dt = view.findViewById(R.id.tv_qna_con_dt)
        }

        fun bind(item: Qna) {
            qna_title.text = item.QNA_TITLE
            qna_con_dt.text = item.QNA_CON_DT
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): QnaViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_qna_item, viewGroup, false)

        return QnaViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos: Int = adapterPosition
                val qna: Qna = qnaList.get(curPos)
                Toast.makeText(viewGroup.context, "Qna-id: ${qna.QNA_ID}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBindViewHolder(viewHolder: QnaViewHolder, position: Int) {
        viewHolder.bind(qnaList.get(position))
    }

    override fun getItemCount() = qnaList.size
}