package com.example.componentsizeandpositionproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.componentsizeandpositionproject.databinding.ActivityMainBinding
import com.example.componentsizeandpositionproject.databinding.ListItemLayoutBinding

class MainActivity : AppCompatActivity() {

    private val TAG = this::class.simpleName.toString()
    private lateinit var binding: ActivityMainBinding
    private val list = TempData.list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initRecyclerView()
        setScrollViewScrollChangedListener()
    }

    private fun initRecyclerView() {
        setRecyclerViewStyle()
        binding.recyclerView.adapter = SimpleRVAdapter(list)
        binding.recyclerView.isNestedScrollingEnabled = false   // scrollview와 중복 스크롤 방지
    }

    private fun setRecyclerViewStyle() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager(this).orientation, false)

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager(this).orientation
            )
        )
    }


    private fun setScrollViewScrollChangedListener() {
        binding.scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

            // child view size of scroll view
            val childCount = binding.scrollView.childCount
            var count = 0

            binding.scrollView.children.iterator().forEach {
                Log.e(TAG, "${it.id}: ${count++}, $childCount")
            }

            // most bottom position view of scroll view's child view
            val lastView = binding.scrollView.getChildAt(binding.scrollView.childCount - 1)
            // bottom position of most bottom position view
            val lastViewBottom = lastView.bottom
            // scroll view height = bottom position of most bottom position view
            Log.e(TAG, "lastViewBottom: $lastViewBottom")

            // scroll view height of display layout (if 'fillViewport=true' device height, always same)
            val scrollViewHeight =
                binding.scrollView.height
            // The extent to which it is currently scrolled.
            val scrollViewScrollY = binding.scrollView.scrollY
            Log.e(TAG, "scrollViewHeight: $scrollViewHeight")
            Log.e(TAG, "scrollViewScrollY: $scrollViewScrollY")

            val bottomDetector = lastViewBottom - (scrollViewHeight + scrollViewScrollY)
//            scrollViewHeight + scrollViewScrollY : how much the screen is down.
//            lastViewBottom =< scrollViewHeight + scrollViewScrollY : Lowest positioned

            Log.e(TAG, "bottomDetector: $bottomDetector \n\n")

            if(bottomDetector == 0) {
                binding.tvBottomDetect.text = "Reach lowest positioned"
            } else {
                binding.tvBottomDetect.text = bottomDetector.toString()
            }


        }
    }
}

data class Model(val id: String, val nickName: String, val imageUrl: String)

class SimpleRVAdapter(private val list: ArrayList<Model>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var binding: ListItemLayoutBinding
    private lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_layout,
            parent,
            false
        )

        mContext = parent.context
        return SimpleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SimpleViewHolder) {
            val item: Model = list[position]
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class SimpleViewHolder(private val binding: ListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Model) {
            binding.model = item

            Glide.with(mContext)
                .load(R.drawable.iu)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(binding.ivImage)
        }
    }
}