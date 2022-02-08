package com.example.activityforresultproject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val launcher = getSubActivityResultLauncher()

        findViewById<Button>(R.id.btn_move).setOnClickListener {
            openSubActivityResultLauncher(launcher)
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

    private fun openSubActivityResultLauncher(launcher: ActivityResultLauncher<Intent>) {
        launcher.launch(Intent(this, SubActivity::class.java))
    }
}