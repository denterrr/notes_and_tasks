package ter.den.feature_notes.di

import androidx.lifecycle.ViewModel
import dagger.Component
import ter.den.core.di.annotation.FeatureScope
import ter.den.feature_notes.di.modules.DataModule
import ter.den.feature_notes.di.modules.DomainModule
import ter.den.feature_notes.di.modules.PresentationModule
import ter.den.feature_notes.ui.AddNoteFragment
import ter.den.feature_notes.ui.NotesFragment

@[FeatureScope
Component(
    dependencies = [NoteDepends::class],
    modules = [DataModule::class, DomainModule::class, PresentationModule::class]
)]
internal interface NoteComponent {

    fun inject(fragment: NotesFragment)
    fun inject(fragment: AddNoteFragment)

    @Component.Factory
    interface Factory {
        fun create(authDepends: NoteDepends): NoteComponent
    }
}

internal class NoteComponentViewModel : ViewModel() {

    val noteComponent =
        DaggerNoteComponent.factory().create(NoteDependsProvider.dependencies)
}