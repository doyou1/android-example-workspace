package com.example.roomdbimagesampling

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.roomdbimagesampling.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName
    private var currentUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    private fun init() {
        setClickEvent()
        setPermissionText()
    }

    private fun setClickEvent() {
        binding.btnGallery.setOnClickListener {
            if (hasPermission()) {
                openGallery()
            } else {
                requestPermission()
            }
        }
        binding.btnSave.setOnClickListener {
            currentUri?.let {
                val split = it.toString().split("/")
                val bytes = getBytes(it)
                bytes?.let {
                    val item = Image(0, split[split.size - 1], bytes)
                    lifecycleScope.launch(Dispatchers.IO) {
                        val result = (application as BaseApplication).imageDao.insert(item)
                        lifecycleScope.launch(Dispatchers.Main) {
                            Log.e(TAG, "save result: $result")
                        }
                    }
                }
            }
        }

        binding.btnShow.setOnClickListener {
            val intent = Intent(this, ImageActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getBytes(uri: Uri): ByteArray? {
        return contentResolver.openInputStream(uri)?.buffered()?.use { it.readBytes() }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CHOOSE)
    }

    // image pick response
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            currentUri = data?.data
            binding.ivResult.setImageURI(currentUri)
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                setPermissionText()
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()
                }
            }
        }
    }

    private fun setPermissionText() {
        if (hasPermission()) {
            binding.tvPermission.text = "permission O"
        } else {
            binding.tvPermission.text = "permission X"
        }
    }

    private fun hasPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        } else {
            return true
        }
    }

    companion object {
        const val PERMISSION_CODE = 1001
        const val IMAGE_CHOOSE = 1000
    }
}