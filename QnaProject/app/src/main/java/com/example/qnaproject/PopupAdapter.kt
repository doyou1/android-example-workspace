package com.example.qnaproject
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.qnaproject.databinding.ListPopupItemBinding
import com.example.qnaproject.domain.Popup

private val baseUrlWithoutLastSlash = "http://211.254.212.85:8080"

class PopupAdapter(val popupList: ArrayList<Popup>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val tag = "PopupAdater"

    private lateinit var binding: ListPopupItemBinding
    private lateinit var mContext: Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.list_popup_item,
            viewGroup,
            false
        )
        mContext = viewGroup.context

        return PopupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PopupViewHolder) {
            val pop: Popup = popupList[position]
            holder.bind(pop)
        }
    }

    override fun getItemCount(): Int {
        return popupList.size
    }

    inner class PopupViewHolder(val binding: ListPopupItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(popup:Popup) {
            binding.popup = popup

            // Glide 이미지 라이브러리 사용
            Glide.with(mContext)
                .load("${baseUrlWithoutLastSlash}${popup.POP_URL}")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.company_product_default)
                .error(R.drawable.company_product_default)
                .into(binding.ivPopupImage)
        }
    }
}