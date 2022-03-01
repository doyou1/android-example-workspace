package com.example.colorpickerproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.colorpickerproject.databinding.ActivityMainBinding
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape

class MainActivity : AppCompatActivity() {

    private val TAG = "Activity"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.colorPicker.setColorListener { color, colorHex ->
            Log.e(TAG, "color: $color, colorHex: $colorHex")
            binding.root.setBackgroundColor(color)
        }

        binding.btnColorPick.setOnClickListener {
            Log.e(TAG, "Color Pick")
            // Kotlin Code
            ColorPickerDialog
                .Builder(this)        				// Pass Activity Instance
                .setTitle("Pick Color")           	// Default "Choose Color"
                .setColorShape(ColorShape.SQAURE)   // Default ColorShape.CIRCLE
//            .setDefaultColor(mDefaultColor)     // Pass Default Color
                .setColorListener { color, colorHex ->
                    // Handle Color Selection
                    Log.e(TAG, "color: $color, colorHex: $colorHex")

                    binding.btnColorPick.setBackgroundColor(color)
                }
                .setDismissListener {
                    Toast.makeText(this, "didn't pick color", Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        binding.btnCustomColorPick.setOnClickListener {
            MaterialColorPickerDialog
                .Builder(this)        				// Pass Activity Instance
                .setTitle("Pick Custom Color")           	// Default "Choose Color"
                .setColors(resources.getStringArray(R.array.themeColorHex))
                .setColorListener { color, colorHex ->
                    // Handle Color Selection
                    Log.e(TAG, "color: $color, colorHex: $colorHex")

                    binding.btnCustomColorPick.setBackgroundColor(color)
                }
                .setDismissListener {
                    Toast.makeText(this, "didn't pick color", Toast.LENGTH_SHORT).show()
                }
                .show()
        }


    }
}