package com.example.regularincomeandinstallmentsampling


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.regularincomeandinstallmentsampling.databinding.ActivityInstallmentBinding

class InstallmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInstallmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstallmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        setClickEvent()
    }

    private fun setClickEvent() {

    }
}