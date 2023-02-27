package ter.den.feature_notes.data

import kotlinx.coroutines.flow.Flow
import ter.den.feature_notes.data.db.NoteDao
import ter.den.feature_notes.data.db.model.NoteDB
import javax.inject.Inject

class NoteDataSourceImpl @Inject constructor(
    private val dao: NoteDao
) : NoteDataSource {
    override suspend fun insert(noteDB: NoteDB) {
        dao.insert(noteDB)
    }

    override suspend fun delete(ids: List<Long>) {
        dao.deleteAll(ids)
    }

    override fun getAllNotes(): Flow<List<NoteDB>> = dao.getAllNotes()

    override suspend fun getNote(id: Long): NoteDB? = dao.getNote(id)
}