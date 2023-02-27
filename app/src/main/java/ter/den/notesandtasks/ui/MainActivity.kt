package ter.den.notesandtasks.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import ter.den.core.domain.extensions.toInvisible
import ter.den.core.domain.extensions.toVisible
import ter.den.core.domain.interfaces.SelectableOperations
import ter.den.notesandtasks.R
import ter.den.notesandtasks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SelectableOperations {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigation()


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

    private fun setUpNavigation() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.navFragmentContainer) as NavHostFragment
        binding.bottomNavigation.setupWithNavController(navHost.navController)
        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigation.isVisible =
                destination.id !in listOf(ter.den.feature_notes.R.id.addNoteFragment)
            binding.divider.isVisible = binding.bottomNavigation.isVisible
        }

        binding.bottomNavigation.setOnItemReselectedListener { menuItem ->
            val recyclerView =
                if (menuItem.itemId == R.id.notes_tab_navigation) navHost.requireView()
                    .findViewById<RecyclerView>(
                        ter.den.feature_notes.R.id.rvNotes
                    ) else navHost.requireView().findViewById(
                    ter.den.feature_notes.R.id.rvNotes
                )
            val toolBar =
                if (menuItem.itemId == R.id.notes_tab_navigation) navHost.requireView()
                    .findViewById<AppBarLayout>(
                        ter.den.feature_notes.R.id.appBar
                    ) else navHost.requireView().findViewById(
                    ter.den.feature_notes.R.id.appBar
                )
            recyclerView.smoothScrollToPosition(0)
            toolBar.setExpanded(true, true)
        }

    }

    private fun initNotificationChannel() {
        val notificationManager = getSystemService(NotificationManager::class.java)
        val notificationChannel =
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.GREEN
        notificationManager.createNotificationChannel(notificationChannel)
    }


    //SELECTABLE OPERATIONS IMPL
    override fun hide() {
        binding.clSelect.toInvisible()
        binding.bottomNavigation.toVisible()
    }

    override fun show() {
        binding.bottomNavigation.toInvisible()
        binding.clSelect.toVisible()
    }

    override fun onClickDelete(callBack: () -> Unit) {
        binding.cvDelete.setOnClickListener { callBack.invoke() }
    }

    override fun onClickSelectAll(callBack: () -> Unit) {
        binding.cvSelectAll.setOnClickListener { callBack.invoke() }
    }

    companion object {
        const val CHANNEL_ID = "CHANNEL ID TER"
        const val CHANNEL_NAME = "DEN TER CHANNEL"
    }


}