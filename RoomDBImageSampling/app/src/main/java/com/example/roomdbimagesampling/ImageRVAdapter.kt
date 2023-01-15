package com.example.roomdbimagesampling

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PixelFormat.RGBA_8888
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.roomdbimagesampling.databinding.RvItemImageBinding

class ImageRVAdapter(private val list: List<Image>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = this::class.java.simpleName
    private val _list = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _list[position]
        (holder as ImageViewHolder).bind(item)

    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class ImageViewHolder(private val binding: RvItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Image) {
            binding.tvId.text = item.id.toString()
            binding.tvName.text = item.name
            val fileUrl = "${BASE_URL}api/get/${item.name}"

            Log.e(TAG, "fileUrl: $fileUrl")

            Glide.with(itemView.context)
                .load(fileUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.ivResult)
        }
    }


}