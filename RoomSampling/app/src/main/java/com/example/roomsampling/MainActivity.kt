package com.example.roomsampling

import android.app.Activity
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.roomsampling.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val TAG = this::class.java.simpleName
    private var userList: List<User> = arrayListOf()
    private lateinit var _activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        _activity = this
        setContentView(binding.root)
    }


    override fun onResume() {
        super.onResume()
        setRecyclerView()
        setClickEvent()
        setData()
    }

    private fun setData() {
        lifecycleScope.launch(Dispatchers.IO) {
            userList = (application as BaseApplication).userDao.getAll()
            binding.num = userList[userList.size-1].uid + 1
            lifecycleScope.launch(Dispatchers.Main) {
                binding.recyclerView.adapter = UserRvAdapter(userList, _activity)
            }
        }
    }

    private fun setClickEvent() {
        binding.btnSend.setOnClickListener {
            save()
        }
    }

    private fun setRecyclerView() {
        binding.recyclerView.layoutManager = getLayoutManager()
        binding.recyclerView.adapter = UserRvAdapter(userList, _activity)
    }


    private fun save() {
        val id = binding.etId.text.toString()
        val pwd = binding.etPwd.text.toString()
        val name = binding.etName.text.toString()
        val user = User(binding.num, id, pwd, name)

        lifecycleScope.launch(Dispatchers.IO) {
            (application as BaseApplication).userDao.insert(user)
            setData()
        }
    }

    private fun getLayoutManager() : GridLayoutManager {
        return when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val layoutManager = GridLayoutManager(this, 1)
                layoutManager.orientation =
                    LinearLayoutManager.HORIZONTAL
                layoutManager
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val layoutManager = GridLayoutManager(this, 1)
                layoutManager.orientation =
                    LinearLayoutManager.VERTICAL
                layoutManager
            }
            else -> throw NotImplementedError()
        }
    }
}

//data class User(val )