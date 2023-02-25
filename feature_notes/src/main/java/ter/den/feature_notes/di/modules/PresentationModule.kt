package ter.den.feature_notes.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ter.den.core.di.annotation.ViewModelKey
import ter.den.feature_notes.presentation.NoteViewModel

@Module
internal interface PresentationModule {
    @IntoMap
    @ViewModelKey(NoteViewModel::class)
    @Binds
    fun bindNoteViewModel(impl: NoteViewModel): ViewModel
}