package ter.den.notesandtasks

import android.app.Application
import android.content.Context
import ter.den.feature_notes.di.NoteDependsStore
import ter.den.notesandtasks.di.AppComponent
import ter.den.notesandtasks.di.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        NoteDependsStore.dependencies = appComponent
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }
