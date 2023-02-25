package ter.den.feature_notes.di.modules

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import ter.den.core.di.annotation.FeatureScope
import ter.den.core.di.annotation.IoDispatcherQualifier
import ter.den.feature_notes.data.NoteDataSource
import ter.den.feature_notes.data.NoteDataSourceImpl
import ter.den.feature_notes.data.db.NoteDao
import ter.den.feature_notes.data.db.NoteDatabase

@Module
internal interface DataModule {

    @[Binds FeatureScope]
    fun bindNoteDataSource(noteDataSourceImpl: NoteDataSourceImpl): NoteDataSource

    companion object {
        @[Provides FeatureScope]
        fun provideNoteDao(context: Context): NoteDao = NoteDatabase.getInstance(context)

        @[Provides IoDispatcherQualifier]
        fun provideCoroutineDispatcher() = Dispatchers.IO
    }
}