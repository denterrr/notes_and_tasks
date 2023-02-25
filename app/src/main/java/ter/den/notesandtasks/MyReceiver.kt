package ter.den.notesandtasks

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ter.den.notesandtasks.ui.MainActivity

class MyReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val intentt = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intentt, PendingIntent.FLAG_IMMUTABLE)
        val notificationManager = context?.getSystemService(NotificationManager::class.java)
        intent?.getIntExtra("id", -1)
        val builder = Notification.Builder(context, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("IS TITLE")
            .setContentText("1")
            .setContentIntent(pendingIntent)
        notificationManager?.notify(1, builder.build())

        val a = intent?.getBooleanArrayExtra("a")
    }
}
