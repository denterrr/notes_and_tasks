package ter.den.notesandtasks

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ter.den.notesandtasks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException()

    private val navController: NavController by lazy {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navFragmentContainer) as NavHostFragment
        navHostFragment.navController
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val notificationManager = getSystemService(NotificationManager::class.java)
        val notificationChannel =
            NotificationChannel("CHANNEL_ID", "description", NotificationManager.IMPORTANCE_DEFAULT)
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

//        val calendar = Calendar.getInstance()
//        calendar.add(Calendar.SECOND, 15)
//        val intent = Intent(this, MyReceiver::class.java)
//        intent.putExtra("id", 3)
//        val pIntent = PendingIntent.getBroadcast(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
//        val manager = getSystemService(AlarmManager::class.java)
//        manager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,pIntent )
    }

    override fun onStart() {
        super.onStart()
        val navhost =
            supportFragmentManager.findFragmentById(R.id.navFragmentContainer) as NavHostFragment
        binding.bottomNavigation.setupWithNavController(navhost.navController)
    }


}