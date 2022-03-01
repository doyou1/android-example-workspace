package com.example.qnaproject

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.qnaproject.activity.ProductActivity
import com.example.qnaproject.databinding.ListProductItemBinding
import com.example.qnaproject.domain.Product
import com.example.qnaproject.module.GlideApp

/**
 * ProductActivity - HomeFragment - RecyclerView
 * 상품리스트(recyclerview)의 어댑터
 */
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
            val product: Product = productList[position]
            holder.bind(product)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    /**
     * 레이아웃과 바인딩
     */
    inner class ProductViewHolder(val binding: ListProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(product:Product) {
                this.itemView.setOnClickListener {
                    val mActivity = mContext as ProductActivity
                    mActivity.changeFragment("update", product.ITM_ID)
                }
                binding.product = product

                Log.e(tag, "product ${product.toString()}")
                // Glide 이미지 라이브러리 사용
                GlideApp.with(mContext)
                    .load(product.ITM_IMG1)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.company_product_default)
                    .error(R.drawable.company_product_default)
                    .into(binding.ivProductImg1)
            }
    }
}