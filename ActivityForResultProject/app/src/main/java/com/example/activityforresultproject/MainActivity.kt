package com.example.activityforresultproject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    private val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val subLauncher = getSubActivityResultLauncher()
        val galleryLauncher = getGalleryActivityResultLauncher()

        findViewById<Button>(R.id.btn_sub_move).setOnClickListener {
            openSubActivityResultLauncher(subLauncher)
        }
        findViewById<Button>(R.id.btn_gallery_move).setOnClickListener {
            openGalleryActivityResultLauncher(galleryLauncher)
        }
    }

    private fun getSubActivityResultLauncher() : ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts
            .StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                data.let { data ->
                    findViewById<TextView>(R.id.textView).text = data?.getStringExtra("text") ?: "Empty"
                }
            }
        }
    }

    private fun getGalleryActivityResultLauncher() : ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data

                data?.let { data ->
                    val selectedImage = data.data
                    Log.e(tag, selectedImage.toString())
                }
            }
        }
    }

    private fun openSubActivityResultLauncher(launcher: ActivityResultLauncher<Intent>) {
        launcher.launch(Intent(this, SubActivity::class.java))
    }

    private fun openGalleryActivityResultLauncher(launcher: ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_PICK).also {
            it.setType("image/*")
            val mimeTypes = arrayOf("image/jpg")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }
        launcher.launch(intent)
    }
}