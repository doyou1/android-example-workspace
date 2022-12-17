package com.example.transparentviewsampling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.transparentviewsampling.databinding.ActivityMainBinding

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

        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnShowFragment.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(binding.frameLayout.id, TransparentFragment())
            transaction.commit()

            if (binding.frameLayout.visibility == View.GONE) binding.frameLayout.visibility =
                View.VISIBLE
        }
        binding.btnShowActivity.setOnClickListener {
            Log.e(TAG, "btn Show Activity")
            val intent = Intent(this, TransparentActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if(binding.frameLayout.visibility == View.VISIBLE) {
            binding.frameLayout.visibility = View.GONE
        } else {
            super.onBackPressed()
        }

    }
}