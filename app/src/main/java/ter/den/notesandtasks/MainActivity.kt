package ter.den.notesandtasks

import android.app.*
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.work.*
import java.time.Duration
import java.util.Calendar
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val notificationManager = getSystemService(NotificationManager::class.java)
        val notificationChannel = NotificationChannel("CHANNEL_ID", "description", NotificationManager.IMPORTANCE_DEFAULT)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.GREEN
        notificationManager.createNotificationChannel(notificationChannel)



//        val workk = OneTimeWorkRequestBuilder<MyService>()
//            .setInputData(Data.Builder().putInt("int", 0).build())
//            .setInitialDelay(15, TimeUnit.SECONDS)
//            .build()
//
//        val workkk = OneTimeWorkRequestBuilder<MyService>()
//            .setInputData(Data.Builder().putInt("int", 1).build())
//            .setInitialDelay(15, TimeUnit.SECONDS)
//            .build()
//        val work = PeriodicWorkRequest.Builder(MyService::class.java, 15, TimeUnit.MINUTES,30, TimeUnit.SECONDS)
//            .build()
//        val manager = WorkManager.getInstance(applicationContext)
////        manager.enqueueUniquePeriodicWork("work.2",ExistingPeriodicWorkPolicy.KEEP, work)
//        manager.beginUniqueWork("work", ExistingWorkPolicy.KEEP, workk).enqueue()

//        val alarm = getSystemService(AlarmManager::class.java)
//        val intent = Intent(this, MyReceiver::class.java)
//        val pIntent = PendingIntent.getBroadcast(this, 0,
//            intent, PendingIntent.FLAG_IMMUTABLE)
//        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 59000, pIntent)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, 15)
        val intent = Intent(this, MyReceiver::class.java)
        intent.putExtra("id", 3)
        val pIntent = PendingIntent.getBroadcast(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val manager = getSystemService(AlarmManager::class.java)
        manager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,pIntent )
    }

    fun createNotif(){
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notificationManager = getSystemService(NotificationManager::class.java)

        val builder = Notification.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_foreground))
            .setContentTitle("IS TITLE")
            .setContentText(count.toString())
            .setContentIntent(pendingIntent)
        notificationManager.notify(count, builder.build())
        count++
    }
}