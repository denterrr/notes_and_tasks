package ter.den.feature_notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ter.den.feature_notes.domain.model.Note
import ter.den.feature_notes.domain.usecases.DeleteNotesUseCase
import ter.den.feature_notes.domain.usecases.GetAllNotesUseCase
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val deleteNotesUseCase: DeleteNotesUseCase,
    getAllNotesUseCase: GetAllNotesUseCase,
) : ViewModel() {
    private val selectedIDs = mutableSetOf<Long>()
    private val _selectedListFlow = MutableStateFlow(0)
    val selectedListFlow: StateFlow<Int> = _selectedListFlow

    private var currentNotes = emptyList<Note>()

    fun onSelect(id: Long) = viewModelScope.launch {
        if (selectedIDs.contains(id)) {
            selectedIDs.remove(id)
        } else selectedIDs.add(id)
        _selectedListFlow.tryEmit(selectedIDs.size)
    }

    fun selectAll() {
        if (selectedIDs.size != currentNotes.size) {
            currentNotes.forEach {
                selectedIDs.add(it.id)
            }
        } else {
            selectedIDs.clear()
        }
    }

    fun deleteSelected() = viewModelScope.launch {
        deleteNotesUseCase(selectedIDs.toList())
        selectedIDs.clear()
        _selectedListFlow.tryEmit(selectedIDs.size)
    }

    val notesFlow = getAllNotesUseCase().apply {
        viewModelScope.launch {
            collectLatest { currentNotes = it }
        }
    }
}