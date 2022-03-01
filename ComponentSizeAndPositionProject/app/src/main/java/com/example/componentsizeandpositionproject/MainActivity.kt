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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initUI()
    }

    private fun initUI() {
        initRecyclerView()
        setScrollViewScrollChangedListener()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager(this).orientation, false)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager(this).orientation))
        binding.recyclerView.adapter = SimpleRVAdapter(list)
        
        binding.recyclerView.isNestedScrollingEnabled = false   // scrollview와 중복 스크롤 방지
    }

    private fun setScrollViewScrollChangedListener() {
        binding.scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

            val childCount = binding.scrollView.childCount  // scroll view 하위 뷰의 개수
            var count = 0
            binding.scrollView.children.iterator().forEach {
                Log.e(TAG, "${it.id}: ${count++}, $childCount")
            }

            // scrollView의 하위 뷰 중 가장 하단에 위치하는 뷰
            val lastView = binding.scrollView.getChildAt(binding.scrollView.childCount - 1)
            val lastViewBottom = lastView.bottom
            // 하단에 위치하는 뷰의 바텀 포지션 (현재 화면의 가장 하단(bottom),
            // 정확하게 스크롤뷰가 적용된 뷰의 최하단 혹은 스크롤뷰의 전체 높이(height)을 의미)

            Log.e(TAG, "lastViewBottom: ${lastViewBottom}")

            val scrollViewHeight = binding.scrollView.height    // 화면상에 보여지는 높이 (기기 스크린 height, 항상 일정)
            val scrollViewScrollY = binding.scrollView.scrollY  // 현재 스크롤이 된 정도

            Log.e(TAG, "scrollViewHeight: $scrollViewHeight")
            Log.e(TAG, "scrollViewScrollY: $scrollViewScrollY")

            val bottomDetector = lastViewBottom - (scrollViewHeight + scrollViewScrollY)
//            scrollViewHeight + scrollViewScrollY = 화면이 얼마나 내려왔는지를 의미
//            lastViewBottom 과 scrollViewHeight + scrollViewScrollY이 같거나 오른쪽이 더 크다면 최하단에 위치했음을 의미

            Log.e(TAG, "bottomDetector: $bottomDetector \n\n")
        }
    }
}

data class Model(val id: String, val nickName: String, val imageUrl: String)

class SimpleRVAdapter(private val list: ArrayList<Model>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var binding: ListItemLayoutBinding
    private lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
        R.layout.list_item_layout,
        parent,
        false)

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

    inner class SimpleViewHolder(private val binding: ListItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
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