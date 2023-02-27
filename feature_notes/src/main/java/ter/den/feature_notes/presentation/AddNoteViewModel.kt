package ter.den.feature_notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ter.den.feature_notes.domain.model.Note
import ter.den.feature_notes.domain.usecases.GetNoteUseCase
import ter.den.feature_notes.domain.usecases.InsertNoteUseCase
import javax.inject.Inject

class AddNoteViewModel @Inject constructor(
    private val insertNoteUseCase: InsertNoteUseCase,
    private val getNoteUseCase: GetNoteUseCase,
) : ViewModel() {

    private val _note = MutableSharedFlow<Note?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val note: Flow<Note?> = _note.distinctUntilChanged()

    private var noteId = 0L

    fun setNote(noteId: Long?) = viewModelScope.launch {
        val note = if (noteId != null) getNoteUseCase(noteId)
        else null
        _note.tryEmit(note)
        this@AddNoteViewModel.noteId = noteId ?: 0
    }

    fun insertNote(title: String, desc: String, timeMillis: Long) = viewModelScope.launch {
        insertNoteUseCase(noteId, title, desc, timeMillis)
    }
}