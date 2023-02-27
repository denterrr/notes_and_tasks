package ter.den.feature_notes.di.modules

import dagger.Binds
import dagger.Module
import ter.den.core.di.annotation.FeatureScope
import ter.den.feature_notes.data.NoteRepositoryImpl
import ter.den.feature_notes.domain.NoteRepository

@Module
interface DomainModule {
    @[Binds FeatureScope]
    fun bindNoteRepository(impl: NoteRepositoryImpl): NoteRepository
}