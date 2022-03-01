package com.example.contentproviderprojecta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var mDatabase: DBHelper
    private val list = arrayListOf<ItemRow>(
        ItemRow(null, "식사", "미역국", 5000),
        ItemRow(null, "간식", "우유", 3000),
        ItemRow(null, "간식", "바나나", 8000),
        ItemRow(null, "식사", "오이", 2000),
        ItemRow(null, "식사", "당근", 7000),

        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDatabase = DBHelper(this, "newdb.db", 1)

        // 데이터 입력
        mDatabase.setDelete()   // 전체 삭제
        for (item in list) {
            mDatabase.setItem(item.content, item.name, item.num)
        }

        findViewById<Button>(R.id.btn_load).setOnClickListener {
            // 데이터 불러오기
            loadData()
        }
    }

    private fun loadData() {
        var str = ""
        val row: ArrayList<ItemRow> = mDatabase.getItem()
        str += "Total count : ${row.size} \n\n\n"
        for (item in row) {
            str += "${item._id}, ${item.content} , ${item.name}, ${item.num} \n"
        }
        findViewById<TextView>(R.id.tv_result).text = str
    }
}

data class ItemRow(val _id: Int?, val content: String, val name: String, val num: Int) {

}