package com.example.roomdbstringjsonsampling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.roomdbstringjsonsampling.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        val string = Gson().toJson(TEMP)
        Log.d(TAG, "string: $string")
        binding.tvTemp.text = string
        binding.tvGet.text = "get: "

        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnInsert.setOnClickListener {
            val text = binding.tvTemp.text.toString()
            Log.d(TAG, "text: $text")
            lifecycleScope.launch(Dispatchers.IO) {
                val db =
                    Room.databaseBuilder(applicationContext, AppDataBase::class.java, "test")
                        .build()
                        .itemDao()
                db.insert(
                    Item(
                        0,
                        text
                    )
                )
            }
        }

        binding.btnGet.setOnClickListener {

            lifecycleScope.launch(Dispatchers.IO) {
                val db =
                    Room.databaseBuilder(applicationContext, AppDataBase::class.java, "test")
                        .build()
                        .itemDao()
                val ids = db.getIds()
                lifecycleScope.launch(Dispatchers.Main) {
                    val popup = PopupMenu(applicationContext, binding.btnGet)
                    popup.setOnMenuItemClickListener { item ->
                        val id = item.toString().toLong()
                        lifecycleScope.launch(Dispatchers.IO) {
                            val item = db.getById(id)
                            lifecycleScope.launch(Dispatchers.Main) {
                                binding.tvGet.text = item.data

                                val json = Gson().fromJson(item.data, Steps::class.java)
                                Log.d(TAG, "json: $json")
                                Toast.makeText(applicationContext, "json: $json", Toast.LENGTH_LONG).show()
                            }
                        }
                        false
                    }
                    for (id in ids) {
                        popup.menu.add("$id")
                    }
                    popup.show()
                }
            }
        }

    }
}