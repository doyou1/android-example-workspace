package com.example.regularincomeandinstallmentsampling


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.regularincomeandinstallmentsampling.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

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
        binding.btnRegularIncome.setOnClickListener {
            startActivity(Intent(this, RegularIncomeActivity::class.java))
        }

        binding.btnInstallment.setOnClickListener {
            startActivity(Intent(this, InstallmentActivity::class.java))
        }
    }
}