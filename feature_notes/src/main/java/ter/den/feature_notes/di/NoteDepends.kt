package ter.den.feature_notes.di

import android.content.Context
import androidx.annotation.RestrictTo
import kotlin.properties.Delegates

interface NoteDepends {
    val context: Context
}

interface NoteDependsProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val dependencies: NoteDepends

    companion object : NoteDependsProvider by NoteDependsStore
}

object NoteDependsStore : NoteDependsProvider {

    override var dependencies: NoteDepends by Delegates.notNull()
}