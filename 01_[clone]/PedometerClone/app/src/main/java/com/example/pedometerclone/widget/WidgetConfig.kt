package com.example.pedometerclone.widget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.example.pedometerclone.R
import de.j4velin.lib.colorpicker.ColorPickerDialog
import de.j4velin.lib.colorpicker.ColorPreviewButton

class WidgetConfig : Activity(), View.OnClickListener {

    companion object {
        var widgetId = 0
    }

    override fun onPause() {
        super.onPause()
        WidgetUpdateService.enqueueUpdate(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras
        if (extras != null) {
            setContentView(R.layout.widgetconfig)
            val textcolor = findViewById<ColorPreviewButton>(R.id.textcolor)
            textcolor.setOnClickListener(this)
            textcolor.color = Color.WHITE

            val bgcolor = findViewById<ColorPreviewButton>(R.id.bgcolor)
            bgcolor.setOnClickListener(this)
            bgcolor.color = Color.TRANSPARENT

            widgetId = extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            )

            val resultValue = Intent()
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
            setResult(RESULT_OK, resultValue)
        } else {
            finish()
        }
    }

    override fun onClick(v: View?) {
        val tag = if (findViewById<ColorPreviewButton>(v!!.id).tag != null) {
            findViewById<ColorPreviewButton>(v.id).tag as Int
        } else {
            -1
        }
        val dialog = ColorPickerDialog(this, tag)

        dialog.hexValueEnabled = true
        dialog.alphaSliderVisible = true
        dialog.setOnColorChangedListener { color ->
            (v as ColorPreviewButton).color = color
            v.setTag(color)
            val key = if (v.id == R.id.bgcolor) {
                "background_$widgetId"
            } else {
                "color_$widgetId"
            }
            getSharedPreferences("Widgets", Context.MODE_PRIVATE).edit()
                .putInt(key, color).apply()
        }

        dialog.show()
    }
}