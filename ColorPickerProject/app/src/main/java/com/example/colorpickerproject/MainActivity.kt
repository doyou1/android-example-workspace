package com.example.colorpickerproject

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.colorpickerproject.databinding.ActivityMainBinding
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape


class MainActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.colorPicker.setColorListener { color, colorHex ->
            Log.e(TAG, "color: $color, colorHex: $colorHex")
            binding.root.setBackgroundColor(color)
        }

        binding.btnColorPick.setOnClickListener {
            Log.e(TAG, "Color Pick")
            // Kotlin Code
            ColorPickerDialog
                .Builder(this)                        // Pass Activity Instance
                .setTitle("Pick Color")            // Default "Choose Color"
                .setColorShape(ColorShape.SQAURE)   // Default ColorShape.CIRCLE
                .setColorListener { color, colorHex ->
                    // Handle Color Selection
                    Log.e(TAG, "color: $color, colorHex: $colorHex")

                    binding.btnColorPick.setBackgroundColor(color)
                }
                .setPositiveButton("Ok")
                .setNegativeButton("Cancel")
                .show()
        }

        binding.btnCustomColorPick.setOnClickListener {
            MaterialColorPickerDialog
                .Builder(this)                        // Pass Activity Instance
                .setTitle("Pick Custom Color")            // Default "Choose Color"
                .setColors(resources.getStringArray(R.array.themeColorHex))
                .setColorListener { color, colorHex ->
                    // Handle Color Selection
                    Log.e(TAG, "color: $color, colorHex: $colorHex")

                    binding.btnCustomColorPick.setBackgroundColor(color)
                }
                .setPositiveButton("Ok")
                .setNegativeButton("Cancel")
                .show()
        }
    }
}