package ter.den.notesandtasks.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ter.den.feature_notes.di.NoteDepends
import ter.den.notesandtasks.di.annotation.AppScope

@AppScope
@Component
interface AppComponent : NoteDepends {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): AppComponent
    }

}

