package ter.den.feature_notes.domain.usecases

import ter.den.feature_notes.domain.NoteRepository
import javax.inject.Inject

class DeleteNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(ids: List<Long>) {
        repository.delete(ids)
    }
}