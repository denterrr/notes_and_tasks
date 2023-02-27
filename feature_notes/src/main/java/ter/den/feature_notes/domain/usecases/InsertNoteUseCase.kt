package ter.den.feature_notes.domain.usecases

import ter.den.feature_notes.domain.NoteRepository
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Long, title: String, desc: String, time: Long) {
        repository.insert(id, title, desc, time)
    }
}