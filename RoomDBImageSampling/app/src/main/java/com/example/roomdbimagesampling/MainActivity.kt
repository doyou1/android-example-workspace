package com.example.roomdbimagesampling

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore.Images.Media.*
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.roomdbimagesampling.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName
    private val imageDtos = arrayListOf<ImageDto>()

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
            processSaveImages()
        }

        binding.btnShow.setOnClickListener {
            val intent = Intent(this, ImageActivity::class.java)
            startActivity(intent)
        }
    }

    private fun processSaveImages() {
        val parts = arrayListOf<MultipartBody.Part>()
        for (item in imageDtos) {
            val bytes =
                RequestBody.create(MediaType.parse("image/jpg; charset=utf-8"), item.value)
            val part = MultipartBody.Part.createFormData("image", item.name, bytes)
            parts.add(part)
        }
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val imageService = retrofit.create(ImageService::class.java)
        val call: Call<Int> = imageService.upload(parts)
        call.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                saveImages()
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                //Handle failure
                Log.e(TAG, "fail")
                t.printStackTrace()
            }
        })
    }

    private fun saveImages() {

        val images = arrayListOf<Image>()
        for (item in imageDtos) {
            images.add(Image(0, item.name))
        }

        CoroutineScope(Dispatchers.IO).launch {
            (application as BaseApplication).imageDao.insertAll(images)
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(baseContext, "Complete Insert Images!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(intent, IMAGE_CHOOSE)
    }

    // image pick response
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            data?.let { it ->
                it.clipData?.let { clipData ->
                    imageDtos.clear()
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri

                        val projection = arrayOf(DATA)
                        val cursor = contentResolver.query(
                            uri, projection, null, null, null
                        )
                        if (cursor != null) {
                            val columnIndex = cursor.getColumnIndexOrThrow(DATA)
                            cursor.moveToFirst()
                            val filePath = cursor.getString(columnIndex)
                            val bytes = File(filePath).readBytes()
                            val item = ImageDto("${getRandomFileName()}.jpg", bytes)
                            imageDtos.add(item)
                            cursor.close()
                        }

                    }
                }
            }
        }
    }

    private fun getRandomFileName(): String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyyMMddHHmmss")
        val date = sdf.format(cal.time)
        val unixTime = cal.timeInMillis
        return "$date$unixTime"
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