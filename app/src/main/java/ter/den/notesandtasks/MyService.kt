package ter.den.notesandtasks

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.work.*
import ter.den.notesandtasks.ui.MainActivity
import java.util.concurrent.TimeUnit


class MyService(context: Context, workerParameters: WorkerParameters) : Worker(context,workerParameters){
    override fun doWork(): Result {

        val count = inputData.getInt("int", 0)

        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notificationManager = applicationContext.getSystemService(NotificationManager::class.java)

        val builder = Notification.Builder(applicationContext, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("IS TITLE")
            .setContentText(count.toString())
            .setContentIntent(pendingIntent)
        notificationManager.notify(count, builder.build())

        val work = OneTimeWorkRequestBuilder<MyService>()
            .setInitialDelay(15, TimeUnit.SECONDS)
            .setInputData(Data.Builder().putInt("int", count+1).build())
            .build()
        val manager = WorkManager.getInstance(applicationContext)
        manager.beginUniqueWork("work$count", ExistingWorkPolicy.KEEP, work).enqueue()

        return Result.success()
    }
}