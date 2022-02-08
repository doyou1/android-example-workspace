package com.example.qnaproject

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.qnaproject.activity.QnaActivity
import com.example.qnaproject.activity.QnaDetailActivity
import com.example.qnaproject.databinding.ListProductItemBinding
import com.example.qnaproject.databinding.ListQnaItemBinding
import com.example.qnaproject.domain.Product
import com.example.qnaproject.domain.Qna

class ProductAdapter(val productList: ArrayList<Product>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val tag = "ProductAdapter"
    private lateinit var binding: ListProductItemBinding
    private lateinit var mContext: Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.list_product_item,
            viewGroup,
            false
        )
        mContext = viewGroup.context

        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductViewHolder) {
            val product: Product = productList.get(position)
            holder.bind(product)

            holder.itemView
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(val binding: ListProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(product:Product) {
                this.itemView.setOnClickListener {
                    Log.e(tag, product.toString())
                    Toast.makeText(mContext, product.toString(), Toast.LENGTH_SHORT).show()
                }
                binding.product = product
            }
    }
}