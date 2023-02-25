package ter.den.feature_notes.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = NoteDB.TABLE_NAME)
data class NoteDB(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val description: String,
    @ColumnInfo
    val time: Long,
) {
    companion object {
        const val TABLE_NAME = "NOTE_TABLE"
    }
}