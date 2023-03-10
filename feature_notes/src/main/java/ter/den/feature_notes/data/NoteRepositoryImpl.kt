package ter.den.feature_notes.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ter.den.core.di.annotation.IoDispatcherQualifier
import ter.den.core.domain.model.CustomThrowable
import ter.den.feature_notes.data.db.model.NoteDB
import ter.den.feature_notes.data.db.model.toNote
import ter.den.feature_notes.data.db.model.toNotes
import ter.den.feature_notes.domain.NoteRepository
import ter.den.feature_notes.domain.model.Note
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
    private val dataSource: NoteDataSource
) : NoteRepository {
    override suspend fun insert(id: Long, title: String, desc: String, time: Long) =
        withContext(ioDispatcher) {
            val oldNote = dataSource.getNote(id)
            if (oldNote != null && oldNote.title == title && oldNote.description == desc)
                return@withContext
            dataSource.insert(
                NoteDB(
                    id = id,
                    title = title.trim(),
                    description = desc.trim(),
                    time = time,
                )
            )
        }

    override suspend fun delete(ids: List<Long>) = withContext(ioDispatcher) {
        dataSource.delete(ids)
    }

    override fun getAllNotes(): Flow<List<Note>> = dataSource.getAllNotes().map { it.toNotes() }

    override suspend fun getNote(id: Long): Note = withContext(ioDispatcher) {
        dataSource.getNote(id)?.toNote() ?: throw CustomThrowable.NoteNull
    }
}