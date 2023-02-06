package com.example.pedometerclone

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.pedometerclone.util.DEFAULT_STEP_SIZE
import com.example.pedometerclone.util.DEFAULT_STEP_UNIT
import java.text.DateFormat

class SplitDialog {

    companion object {
        var splitActive = false

        fun getDialog(context: Context, totalSteps: Int): Dialog {
            val dialog = Dialog(context)
            dialog.setTitle("Split Count")
            dialog.setContentView(R.layout.dialog_split)

            val prefs = context.getSharedPreferences("pedometer", Context.MODE_MULTI_PROCESS)
            val splitDate = prefs.getLong("split_date", -1)
            val splitSteps = prefs.getInt("split_steps", totalSteps)
            dialog.findViewById<TextView>(R.id.steps).text =
                OverviewFragment.formatter.format(totalSteps - splitSteps)
            val stepSize = prefs.getFloat("stepsize_value", DEFAULT_STEP_SIZE)
            var distance = (totalSteps - splitSteps) * stepSize
            if (prefs.getString("stepsize_unit", DEFAULT_STEP_UNIT).equals("cm")) {
                distance /= 100000
                dialog.findViewById<TextView>(R.id.distanceunit).text = "km"
            } else {
                distance /= 5280
                dialog.findViewById<TextView>(R.id.distanceunit).text = "mi"
            }
            dialog.findViewById<TextView>(R.id.distance).text =
                OverviewFragment.formatter.format(distance)
            dialog.findViewById<TextView>(R.id.date).text =
                "Since ${DateFormat.getDateTimeInstance().format(splitDate)}"

            val started = dialog.findViewById<View>(R.id.started)
            val stopped = dialog.findViewById<View>(R.id.stopped)

            splitActive = splitDate > 0

            started.visibility = if (splitActive) View.VISIBLE else View.GONE
            stopped.visibility = if (splitActive) View.GONE else View.VISIBLE

            val startstop = dialog.findViewById<Button>(R.id.start)
            startstop.text = if (splitActive) "Stop" else "Start"
            startstop.setOnClickListener {
                if (!splitActive) {
                    prefs.edit().putLong("split_date", System.currentTimeMillis())
                        .putInt("split_steps", totalSteps).apply()
                    splitActive = true
                    dialog.dismiss()
                } else {
                    started.visibility = View.GONE
                    stopped.visibility = View.VISIBLE
                    prefs.edit().remove("split_date").remove("split_steps").apply()
                    splitActive = false
                }
                startstop.text = if (splitActive) "Stop" else "Start"
            }

            dialog.findViewById<Button>(R.id.close).setOnClickListener {
                dialog.dismiss()
            }

            return dialog
        }
    }
}