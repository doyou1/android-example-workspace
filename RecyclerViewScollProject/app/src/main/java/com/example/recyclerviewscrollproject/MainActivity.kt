package com.example.recyclerviewscrollproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.devspark.appmsg.AppMsg
import com.example.recyclerviewscrollproject.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        binding.recyclerView.adapter = RvAdapter()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager(this).getOrientation()))
        addItems()
        binding.recyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
            if (!binding.recyclerView.canScrollVertically(-1)) {
                // the top
                Log.e(TAG, "the top")
            } else if (!binding.recyclerView.canScrollVertically(1)) {
                // the bottom
                Log.e(TAG, "the bottom")
                showAlert()
                addItems()
            } else {
                Log.e(TAG, "~_~")
            }
        }
    }

    private fun addItems() {
        val items = getRandomItems()
        (binding.recyclerView.adapter as RvAdapter).addItems(items)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun showAlert() {
        val appMsg =
            AppMsg.makeText(
                this,
                "Add Item! arrived at the lowest point.",
                AppMsg.Style(AppMsg.LENGTH_LONG, R.color.alert)
            )
        appMsg.duration = 1000
        appMsg.show()
    }

    private fun getRandomItems(): List<Item> {
        val items = arrayListOf<Item>()
        for (i in 0..10) {
            val id = Random.nextInt(0, 100)
            val name = "name$id"
            val pwd = "pwd$id"
            val memo = if (Random.nextDouble() > 0.5) "memo$id"
            else null
            val item = Item(id, name, pwd, memo)
            items.add(item)
        }
        return items
    }

}