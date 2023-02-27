package ter.den.feature_notes.domain.usecases

import ter.den.feature_notes.domain.NoteRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Long) = repository.getNote(id)
}