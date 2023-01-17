package com.example.contentproviderprojecta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.contentproviderprojecta.databinding.ActivityMainBinding

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

        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnLoad.setOnClickListener {
            loadSelfData()
        }
    }

    private fun loadSelfData() {
        var str = ""
        val row: ArrayList<ItemRow> = (application as BaseApplication).mDatabase.getItem()
        str += "Total count : ${row.size} \n\n\n"
        for (item in row) {
            str += "${item._id}, ${item.content} , ${item.name}, ${item.num} \n"
        }
        binding.tvResult.text = str
    }
}

data class ItemRow(val _id: Int?, val content: String, val name: String, val num: Int) {

}