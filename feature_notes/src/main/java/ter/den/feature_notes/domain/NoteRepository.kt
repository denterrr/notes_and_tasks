package ter.den.feature_notes.domain

import kotlinx.coroutines.flow.Flow
import ter.den.feature_notes.domain.model.Note

interface NoteRepository {
    suspend fun insert(id: Long, title: String, desc: String, time: Long)
    suspend fun delete(id: Long)
    fun getAllNotes(): Flow<List<Note>>
    suspend fun getNote(id: Long): Note
}