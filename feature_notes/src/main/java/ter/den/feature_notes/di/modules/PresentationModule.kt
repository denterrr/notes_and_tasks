package ter.den.feature_notes.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ter.den.core.di.annotation.ViewModelKey
import ter.den.feature_notes.presentation.AddNoteViewModel
import ter.den.feature_notes.presentation.NotesViewModel

@Module
internal interface PresentationModule {
    @IntoMap
    @ViewModelKey(NotesViewModel::class)
    @Binds
    fun bindNoteViewModel(impl: NotesViewModel): ViewModel

    @IntoMap
    @ViewModelKey(AddNoteViewModel::class)
    @Binds
    fun bindAddNoteViewModel(impl: AddNoteViewModel): ViewModel
}