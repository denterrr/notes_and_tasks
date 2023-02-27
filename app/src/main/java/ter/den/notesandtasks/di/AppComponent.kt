package ter.den.notesandtasks.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ter.den.feature_notes.di.NoteDepends
import ter.den.notesandtasks.di.annotation.AppScope
import ter.den.notesandtasks.di.modules.AppModule

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent : NoteDepends {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): AppComponent
    }

}

