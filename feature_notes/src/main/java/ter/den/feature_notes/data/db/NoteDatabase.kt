package ter.den.feature_notes.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ter.den.feature_notes.data.db.model.NoteDB

@Database(entities = [NoteDB::class], version = 1, exportSchema = false)
internal abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private const val NOTE_DB_NAME = "note_db"
        fun getInstance(context: Context): NoteDao =
            Room.databaseBuilder(context, NoteDatabase::class.java, NOTE_DB_NAME)
                .build()
                .noteDao()
    }
}