package com.example.activityforresultproject

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.provider.MediaStore.Images.Media.*
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.activityforresultproject.databinding.ActivityMainBinding
import java.io.File


class MainActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    private val subLauncher = getSubActivityResultLauncher()
    private val galleryLauncher = getGalleryActivityResultLauncher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.btnSubMove.setOnClickListener {
            openSubActivityResultLauncher(subLauncher)
        }

        binding.btnGalleryMove.setOnClickListener {
            openGalleryActivityResultLauncher(galleryLauncher)
        }
    }

    private fun getSubActivityResultLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()
        ) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                data.let { data ->
                    binding.tvResult.visibility = View.VISIBLE
                    binding.ivResult.visibility = View.GONE
                    binding.progressCircular.visibility = View.GONE
                    binding.tvResult.text =
                        data?.getStringExtra("text") ?: "Empty"
                }
            }
        }
    }

    private fun getGalleryActivityResultLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()
        ) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data

                result.data?.data?.let { uri ->

                    val projection = arrayOf(DATA)
                    val cursor = contentResolver.query(uri, projection, null, null, null)

                    if (cursor != null) {
                        val columnIndex = cursor.getColumnIndexOrThrow(DATA)
                        cursor.moveToFirst()
                        val filePath = cursor.getString(columnIndex)
                        val bytes = File(filePath).readBytes()
                        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                        binding.ivResult.setImageBitmap(bitmap)
                        binding.tvResult.visibility = View.GONE
                        binding.ivResult.visibility = View.VISIBLE
                        binding.progressCircular.visibility = View.GONE
                        cursor.close()
                    }

                }
            }
        }
    }

    private fun openSubActivityResultLauncher(launcher: ActivityResultLauncher<Intent>) {
        launcher.launch(Intent(this, SubActivity::class.java))
    }

    private fun openGalleryActivityResultLauncher(launcher: ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpg")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }
        launcher.launch(intent)
    }
}