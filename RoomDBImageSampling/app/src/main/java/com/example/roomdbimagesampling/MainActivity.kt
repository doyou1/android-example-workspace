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
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName
//    private var currentUri: Uri? = null

    private val images = arrayListOf<File>()

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

            
        }

//        binding.btnShow.setOnClickListener {
//            val intent = Intent(this, ImageActivity::class.java)
//            startActivity(intent)
//        }
    }

//    private fun getBytes(uri: Uri): ByteArray? {
//        return contentResolver.openInputStream(uri)?.buffered()?.use { it.readBytes() }
//    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
//    intent.clipData
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(intent, IMAGE_CHOOSE)
    }

    // image pick response
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            data?.let { it ->
                it.clipData?.let { clipData ->
                    images.clear()
                    for(i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        val image = File(uri.path)
                        images.add(image)
                    }
                }
            }
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