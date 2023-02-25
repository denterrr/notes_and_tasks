package ter.den.feature_notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ter.den.feature_notes.data.NoteDataSource
import ter.den.feature_notes.data.db.model.toNoteDB
import ter.den.feature_notes.data.db.model.toNotes
import ter.den.feature_notes.domain.model.Note
import javax.inject.Inject

class NoteViewModel @Inject constructor(
    private val dataSource: NoteDataSource
) : ViewModel() {
    private val selectedIDs = mutableListOf<Long>()
    private val _selectIsEnabled = MutableStateFlow(false)
    val selectIsEnabled: StateFlow<Boolean> = _selectIsEnabled

    fun insert(note: Note) = viewModelScope.launch {
        dataSource.insert(note.toNoteDB())
    }

    fun onLongClick(id: Long) = viewModelScope.launch {
        if (selectedIDs.contains(id)) {
            selectedIDs.remove(id)
        } else selectedIDs.add(id)
        _selectIsEnabled.value = selectedIDs.isNotEmpty()
    }

    val notesFlow = dataSource.getAllNotes().map { it.toNotes() }
}