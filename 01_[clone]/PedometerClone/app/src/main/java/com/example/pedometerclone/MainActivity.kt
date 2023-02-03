package com.example.pedometerclone

import android.content.Intent
import android.hardware.SensorListener
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pedometerclone.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startService(Intent(this, SensorListener::class.java))


        if(savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(binding.frameLayout.id, OverviewFragment())
            transaction.commit()
        }

//        if (Build.VERSION.SDK_INT >= 23 && PermissionChecker
//                .checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
//            PermissionChecker.PERMISSION_GRANTED) {
//            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0);
//        }
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            finish()
        }
    }
}