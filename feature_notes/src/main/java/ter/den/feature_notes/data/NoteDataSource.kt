package ter.den.feature_notes.data

import kotlinx.coroutines.flow.Flow
import ter.den.feature_notes.data.db.model.NoteDB

interface NoteDataSource {

    suspend fun insert(noteDB: NoteDB)
    suspend fun delete(id: Long)
    fun getAllNotes(): Flow<List<NoteDB>>
    suspend fun getNote(id: Long): NoteDB

}