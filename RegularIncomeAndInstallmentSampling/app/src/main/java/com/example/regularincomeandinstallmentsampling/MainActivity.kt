package com.example.regularincomeandinstallmentsampling


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.regularincomeandinstallmentsampling.databinding.ActivityMainBinding
import com.example.regularincomeandinstallmentsampling.installment.InstallmentActivity
import com.example.regularincomeandinstallmentsampling.installment.InstallmentRVAdapter
import com.example.regularincomeandinstallmentsampling.regularincome.RegularIncomeActivity
import com.example.regularincomeandinstallmentsampling.regularincome.RegularIncomeRVAdapter
import com.example.regularincomeandinstallmentsampling.room.BaseApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setRecyclerView()
        setClickEvent()
    }

    private fun setRecyclerView() {
        setLayoutManager()
        lifecycleScope.launch(Dispatchers.IO) {
            val regularIncomes = (application as BaseApplication).regularIncomeDao.getAll()
            val installments = (application as BaseApplication).installmentDao.getAll()
            lifecycleScope.launch(Dispatchers.Main) {
                binding.rvRegularIncome.adapter = RegularIncomeRVAdapter(regularIncomes)
                binding.rvInstallment.adapter = InstallmentRVAdapter(installments)
            }
        }

    }

    private fun setClickEvent() {
        binding.btnRegularIncome.setOnClickListener {
            startActivity(Intent(this, RegularIncomeActivity::class.java))
        }
        binding.btnInstallment.setOnClickListener {
            startActivity(Intent(this, InstallmentActivity::class.java))
        }
    }

    private fun setLayoutManager() {
        val regularIncomeLayoutManager = LinearLayoutManager(this)
        regularIncomeLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvRegularIncome.layoutManager = regularIncomeLayoutManager
        val installmentLayoutManager = LinearLayoutManager(this)
        installmentLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvInstallment.layoutManager = installmentLayoutManager
    }
}