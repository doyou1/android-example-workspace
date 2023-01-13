package com.example.roomdbimagesampling

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
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
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName
//    private var currentUri: Uri? = null

    private val images = arrayListOf<Image>()

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
            val parts = arrayListOf<MultipartBody.Part>()
            for (item in images) {
                val requestFile = RequestBody.create(MediaType.parse("image/jpg"), item.value)
                val formData = MultipartBody.Part.createFormData(
                    item.name,
                    "${item.value.name}.jpg",
                    requestFile
                )
                parts.add(formData)
            }

//            val item = images[0]
//            val requestFile = RequestBody.create(MediaType.parse("image/jpg"), item.name)
//            val formData = MultipartBody.Part.createFormData(
//                item.name,
//                "${item.value.name}.jpg",
//                requestFile
//            )

            val fileName = "FileName"
            val body = RequestBody.create(MediaType.parse("multipart/form-data"), fileName)

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val imageService = retrofit.create(ImageService::class.java)

            val call: Call<Int> = imageService.upload(
                body,
                parts.toArray(arrayOf<MultipartBody.Part>())
            )
            call.enqueue(object : Callback<Int> {
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    val result = response.body()
                    Log.e(TAG, "result: $result")
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    //Handle failure
                    Log.e(TAG, "fail")
                    t.printStackTrace()
                }
            })
        }

//        binding.btnShow.setOnClickListener {
//            val intent = Intent(this, ImageActivity::class.java)
//            startActivity(intent)
//        }
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
                    images.clear()
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri

                        val name = "${Calendar.getInstance().timeInMillis}${
                            String.format(
                                "%05d",
                                Random.nextInt(0, 10000)
                            )
                        }"

                        getBitmap(this, uri)?.let { bitmap ->
                            val file = File(
                                getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                                "$name.jpg"
                            )
                            convertBitmapToFile(file, bitmap)
                            images.add(Image(name, file))
                        }
                    }
                    Log.e(TAG, "images: $images")
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

    private fun getBitmap(context: Context, imageUri: Uri): Bitmap? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(
                    context.contentResolver,
                    imageUri
                )
            )
        } else {
            context
                .contentResolver
                .openInputStream(imageUri)?.use { inputStream ->
                    BitmapFactory.decodeStream(inputStream)
                }
        }
    }

    fun convertBitmapToFile(destinationFile: File, bitmap: Bitmap) {
        CoroutineScope(Dispatchers.IO).launch {
            //create a file to write bitmap data
            destinationFile.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos)
            val bitmapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(destinationFile)
            fos.write(bitmapData)
            fos.flush()
            fos.close()
        }
    }

    companion object {
        const val PERMISSION_CODE = 1001
        const val IMAGE_CHOOSE = 1000
    }
}