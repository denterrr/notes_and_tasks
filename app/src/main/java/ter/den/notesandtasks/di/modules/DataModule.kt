package ter.den.notesandtasks.di.modules

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import ter.den.core.di.annotation.IoDispatcherQualifier

@Module
interface DataModule {

    companion object {
        @[Provides IoDispatcherQualifier]
        fun provideCoroutineDispatcher() = Dispatchers.IO

    }
}