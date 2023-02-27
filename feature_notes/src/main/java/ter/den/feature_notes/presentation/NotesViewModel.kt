package ter.den.feature_notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ter.den.feature_notes.domain.usecases.DeleteNoteUseCase
import ter.den.feature_notes.domain.usecases.GetAllNotesUseCase
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val deleteNoteUseCase: DeleteNoteUseCase,
    getAllNotesUseCase: GetAllNotesUseCase,
) : ViewModel() {
    private val selectedIDs = mutableListOf<Long>()
    private val _selectIsEnabled = MutableStateFlow(false)
    val selectIsEnabled: StateFlow<Boolean> = _selectIsEnabled


    fun onLongClick(id: Long) = viewModelScope.launch {
        if (selectedIDs.contains(id)) {
            selectedIDs.remove(id)
        } else selectedIDs.add(id)
        _selectIsEnabled.value = selectedIDs.isNotEmpty()
    }

    val notesFlow = getAllNotesUseCase()
}