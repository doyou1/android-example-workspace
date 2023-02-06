package com.example.pedometerclone.widget

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import androidx.room.Room
import com.example.pedometerclone.util.Util
import com.example.pedometerclone.room.DataBase
import kotlin.math.max

class WidgetUpdateService : JobIntentService() {

    companion object {
        val JOB_ID = 42
        fun enqueueUpdate(context: Context) {
            enqueueWork(context, WidgetUpdateService::class.java, JOB_ID, Intent())
        }
    }

    override fun onHandleWork(intent: Intent) {
        val db = Room.databaseBuilder(this, DataBase::class.java, "steps").build().stepsDao()
        val steps = max(db.getCurrentSteps() + db.getSteps(Util.getToday()), 0)

        val appWidgetManager = AppWidgetManager.getInstance(this)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(ComponentName(this, Widget::class.java))

        for (appWidgetId in appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetId,
                Widget.updateWidget(appWidgetId, this, steps)
            )
        }
    }

}
