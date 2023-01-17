package com.example.contentproviderprojectb

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val TAG = this::class.java.simpleName
    private val authority = "com.example.contentproviderprojecta.MyContentProvider"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cursor = contentResolver.query(
            Uri.parse("content://$authority/getdataall"),
            null,
            null,
            null,
            null
        )
            ?: return
        Log.e(TAG, "cursor.count: ${cursor.count}")
        var str = ""
        while (cursor.moveToNext()) {
            str += "${cursor.getInt(0)}, ${cursor.getString(1)}, ${cursor.getString(2)}, ${
                cursor.getInt(
                    3
                )
            }\n"
        }
        cursor.close()
        findViewById<TextView>(R.id.tv_result).text = str
    }
}