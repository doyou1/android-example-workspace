package com.example.qnaproject

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.qnaproject.databinding.ListProductItemBinding
import com.example.qnaproject.domain.Product

class ProductAdapter(val productList: ArrayList<Product>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val tag = "ProductAdapter"
    private lateinit var binding: ListProductItemBinding
    private lateinit var mContext: Context
    var count = 0


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

            Log.e(tag, "count: ${count++}")

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

                // Glide 이미지 라이브러리 사용
                Glide.with(mContext)
                    .load(product.ITM_IMG1)
                    .transform(RoundedCorners(100))
                    .error(R.drawable.company_product_default)
                    .into(binding.ivProductImg1)

            }
    }
}