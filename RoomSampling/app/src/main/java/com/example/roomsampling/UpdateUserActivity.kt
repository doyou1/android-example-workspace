package com.example.roomsampling

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.roomsampling.databinding.ActivityMainBinding
import com.example.roomsampling.databinding.ActivityUpdateUserBinding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateUserBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateUserBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }


    override fun onResume() {
        super.onResume()
        setData()
        setClickEvent()
    }

    private fun setData() {
        val uid = intent.getIntExtra("uid", -1)
        if (uid == -1) finish()

        lifecycleScope.launch(Dispatchers.IO) {
            val item = (application as BaseApplication).userDao.getByUid(uid)
            lifecycleScope.launch(Dispatchers.Main) {
                binding.model = UserViewModel(item.uid.toString(), item.id, item.pwd, item.name)
            }
        }
    }

    private fun setClickEvent() {
        binding.btnUpdate.setOnClickListener {
            val uid = binding.tvUid.text.toString().toInt()
            val id = binding.etId.text.toString()
            val pwd = binding.etPwd.text.toString()
            val name = binding.etName.text.toString()

            val user = User(uid, id, pwd, name)
            lifecycleScope.launch(Dispatchers.IO) {
                (application as BaseApplication).userDao.update(user)
                lifecycleScope.launch(Dispatchers.Main) {
                    finish()
                }
            }
        }
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}