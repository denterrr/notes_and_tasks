package ter.den.feature_notes.domain.usecases

import ter.den.feature_notes.domain.NoteRepository
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke() = repository.getAllNotes()
}