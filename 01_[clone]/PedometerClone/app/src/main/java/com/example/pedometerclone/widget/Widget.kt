package com.example.pedometerclone.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.widget.RemoteViews
import com.example.pedometerclone.MainActivity
import com.example.pedometerclone.R

class Widget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        WidgetUpdateService.enqueueUpdate(context!!)
    }

    companion object {
        fun updateWidget(appWidgetId: Int, context: Context, steps: Int) : RemoteViews {
            val prefs = context.getSharedPreferences("Widgets", Context.MODE_PRIVATE)
            val pendingIntent = PendingIntent.getActivity(context, appWidgetId, Intent(context, MainActivity::class.java), 0)
            val views = RemoteViews(context.packageName, R.layout.widget)
            views.setOnClickPendingIntent(R.id.widget, pendingIntent)
            views.setTextColor(R.id.widgetsteps, prefs.getInt("color_$appWidgetId", Color.WHITE))
            views.setTextViewText(R.id.widgetsteps, "$steps")
            views.setTextColor(R.id.widgettext, prefs.getInt("color_$appWidgetId", Color.WHITE))
            views.setInt(R.id.widget, "setBackgroundColor", prefs.getInt("background_$appWidgetId", Color.TRANSPARENT))

            return views
        }
    }
}