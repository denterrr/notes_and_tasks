package ter.den.feature_notes.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ter.den.feature_notes.data.db.model.NoteDB

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteDB)

    @Query("DELETE from ${NoteDB.TABLE_NAME} WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM ${NoteDB.TABLE_NAME} ORDER BY time DESC")
    fun getAllNotes(): Flow<List<NoteDB>>

    @Query("SELECT * FROM ${NoteDB.TABLE_NAME} WHERE id = :id")
    suspend fun getNote(id: Long): NoteDB
}